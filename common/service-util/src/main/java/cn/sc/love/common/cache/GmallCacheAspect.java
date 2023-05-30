package cn.sc.love.common.cache;

import cn.sc.love.common.constant.RedisConst;
import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @author YPT
 * @create 2023-05-30-17:02
 */
@Component
@Aspect
public class GmallCacheAspect {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedissonClient redissonClient;


    /**
     * 使用aop实现分布式锁和缓存
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     * @Around 环绕通知
     * @value：表示声明切入的位置 1，定义获取数据的key
     * 例如：获取skuInfo  key===sku:skuId
     * 步骤：(1)获取添加了@Gmallcache注解的方法
     * -->获取注解-->获取注解的属性-->获取方法的参数
     * (2)可以尝试获取数据
     */
    @Around("@annotation(cn.sc.love.common.cache.GmallCache)")
    public Object cacheGmallAspect(ProceedingJoinPoint joinPoint) throws Throwable {

        //创建返回对象
        Object object = new Object();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        //获取注解签名
        GmallCache gmallCache = signature.getMethod().getAnnotation(GmallCache.class);
        String prefix = gmallCache.prefix();
        String suffix = gmallCache.suffix();
        Object[] args = joinPoint.getArgs();

        //获取key
        String key = prefix + Arrays.asList(args).toString() + suffix;

        //从缓存中获取数据
        object = cacheHit(key, signature);
        try {
            if (object == null) {

                //缓存中没有数据，需要从数据库查询
                //定义锁的key
                String lockKey = prefix + ":lock";

                //准备上锁redis redisson
                RLock lock = redissonClient.getLock(lockKey);

                //上锁
                boolean flag = lock.tryLock(RedisConst.SKULOCK_EXPIRE_PX1, RedisConst.SKULOCK_EXPIRE_PX2, TimeUnit.SECONDS);
                if (flag) {

                    try {
                        //查询数据库,执行切入的方法的方法体
                        object = joinPoint.proceed(args);
                        if (object == null) {

                            //创建空值
                            object = new Object();
                            //存储        (按字符串取的，存也要存成字符串)
                            redisTemplate.opsForValue().set(key, JSON.toJSONString(object), RedisConst.SKUKEY_TEMPORARY_TIMEOUT, TimeUnit.SECONDS);

                            //TODO 这里类型转换有个报错
                            return object;
                        } else {
                            //存储        (按字符串取的，存也要存成字符串)
                            redisTemplate.opsForValue().set(key, JSON.toJSONString(object), RedisConst.SKUKEY_TEMPORARY_TIMEOUT, TimeUnit.SECONDS);
                            return object;
                        }
                    } finally {
                        lock.unlock();

                    }

                } else {

                    //睡眠
                    Thread.sleep(100);
                    cacheGmallAspect(joinPoint);
                }

            } else {

                //从缓存中获取到了数据
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }


        //兜底的方法----查询数据库（实际上就是在执行方法体）
        return joinPoint.proceed(args);


    }


    private Object cacheHit(String key, MethodSignature signature) {
        String strJson = (String) redisTemplate.opsForValue().get(key);

        //判断
        if (!StringUtils.isEmpty(strJson)) {

            //获取当前方法的返回值类型
            Class returnType = signature.getReturnType();


            //转换成指定类型  //TODO (原来是什么类型就转换成什么类型)
            return JSON.parseObject(strJson, returnType);
        }

        return null;
    }
}
