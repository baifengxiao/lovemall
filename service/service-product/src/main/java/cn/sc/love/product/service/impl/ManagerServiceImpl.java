package cn.sc.love.product.service.impl;


import cn.sc.love.common.cache.GmallCache;
import cn.sc.love.common.constant.RedisConst;
import cn.sc.love.model.product.*;
import cn.sc.love.product.mapper.*;
import cn.sc.love.product.service.ManagerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author YPT
 * @create 2023-04-24-15:24
 */
@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private BaseCategory1Mapper baseCategory1Mapper;

    @Autowired
    private BaseCategory2Mapper baseCategory2Mapper;
    @Autowired
    private BaseCategory3Mapper baseCategory3Mapper;
    @Autowired
    private BaseAttrInfoMapper baseAttrInfoMapper;
    @Autowired
    private BaseAttrValueMapper baseAttrValueMapper;

    @Autowired
    private SpuInfoMapper spuInfoMapper;

    @Autowired
    private BaseSaleAttrMapper baseSaleAttrMapper;

    @Autowired
    private SpuImageMapper spuImageMapper;
    @Autowired
    private SpuSaleAttrMapper spuSaleAttrMapper;
    @Autowired
    private SpuSaleAttrValueMapper spuSaleAttrValueMapper;
    @Autowired
    private SpuPosterMapper spuPosterMapper;

    @Autowired
    private SkuInfoMapper skuInfoMapper;

    @Autowired
    private SkuAttrValueMapper skuAttrValueMapper;

    @Autowired
    private SkuSaleAttrValueMapper skuSaleAttrValueMapper;

    @Autowired
    private SkuImageMapper skuImageMapper;

    @Autowired
    private BaseCategoryViewMapper baseCategoryViewMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public List<BaseCategory1> getCategory1() {

        List<BaseCategory1> category1List = baseCategory1Mapper.selectList(null);

        return category1List;
    }

    @Override
    public List<BaseCategory2> getCategory2(Long category1Id) {

        QueryWrapper<BaseCategory2> wrapper = new QueryWrapper<>();
        QueryWrapper<BaseCategory2> queryWrapper = wrapper.eq("category1_id", category1Id);
        List<BaseCategory2> category2List = baseCategory2Mapper.selectList(queryWrapper);

        return category2List;
    }

    @Override
    public List<BaseCategory3> getCategory3(Long category2Id) {
        QueryWrapper<BaseCategory3> wrapper = new QueryWrapper<>();
        QueryWrapper<BaseCategory3> queryWrapper = wrapper.eq("category2_id", category2Id);
        List<BaseCategory3> category3List = baseCategory3Mapper.selectList(queryWrapper);

        return category3List;
    }

    @Override
    public List<BaseAttrInfo> attrInfoList(Long category1Id, Long category2Id, Long category3Id) {

        //TODO 可以优化
        return baseAttrInfoMapper.selectAttrInfoList(category1Id, category2Id, category3Id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveAttrInfo(BaseAttrInfo baseAttrInfo) {
//
//
//        //1判断是保存还是修改
//        if (baseAttrInfo.getId() != null) {
//
//            //修改    先插入baseAttrInfo基本属性，再插入平台属性
//            baseAttrInfoMapper.updateById(baseAttrInfo);
//
//            //不采用修改，采用“先删除，重新添加”，因为修改操作复杂
//            //先删除
//            QueryWrapper<BaseAttrValue> wrapper = new QueryWrapper<>();
//            wrapper.eq("attr_id", baseAttrInfo.getId());
//            baseAttrValueMapper.delete(wrapper);
//
//            //后添加
//            List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();
//            if (!CollectionUtils.isEmpty(attrValueList)) {
//
//                for (BaseAttrValue baseAttrValue : attrValueList) {
//                    baseAttrValue.setAttrId(baseAttrInfo.getId());
//                    baseAttrValueMapper.insert(baseAttrValue);
//                }
//
//            }
//        } else {
//
//
//            //新增，直接添加
//            baseAttrInfoMapper.insert(baseAttrInfo);
//
//            List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();
//            if (!CollectionUtils.isEmpty(attrValueList)) {
//
//                for (BaseAttrValue baseAttrValue : attrValueList) {
//                    baseAttrValue.setAttrId(baseAttrInfo.getId());
//                    baseAttrValueMapper.insert(baseAttrValue);
//                }
//
//
//            }
//
//        }
//
//
//
//
//
//


        //1判断是保存还是修改
        if (baseAttrInfo.getId() != null) {

            //修改    先插入baseAttrInfo基本属性，再插入平台属性
            baseAttrInfoMapper.updateById(baseAttrInfo);

            //不采用修改，采用“先删除，重新添加”，因为修改操作复杂
            //先删除
            QueryWrapper<BaseAttrValue> wrapper = new QueryWrapper<>();
            wrapper.eq("attr_id", baseAttrInfo.getId());
            baseAttrValueMapper.delete(wrapper);

        } else {

            //新增，直接添加
            baseAttrInfoMapper.insert(baseAttrInfo);
        }

        //都需要添加，写到外面
        List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();
        if (!CollectionUtils.isEmpty(attrValueList)) {

            for (BaseAttrValue baseAttrValue : attrValueList) {
                baseAttrValue.setAttrId(baseAttrInfo.getId());
                baseAttrValueMapper.insert(baseAttrValue);
            }


        }


        //1.3注意加事务


    }

    @Override
    public List<BaseAttrValue> getAttrValueList(Long attrId) {

//        //不严谨，可能出现进入查询，baseattrInfo刚好删除的情况
//        QueryWrapper<BaseAttrValue> wrapper = new QueryWrapper<>();
//        wrapper.eq("attr_id", attrId);
//        List<BaseAttrValue> baseAttrValueList = baseAttrValueMapper.selectList(wrapper);
//        return baseAttrValueList;


        //1,先找到实体，
        BaseAttrInfo baseAttrInfo = baseAttrInfoMapper.selectById(attrId);
        Long id = baseAttrInfo.getId();

        //2，再用实体的id查数据（实体被删了就没有id，就不会查）
        QueryWrapper<BaseAttrValue> wrapper = new QueryWrapper<>();
        wrapper.eq("attr_id", id);
        List<BaseAttrValue> baseAttrValueList = baseAttrValueMapper.selectList(wrapper);


        return baseAttrValueList;
    }

    @Override
    public IPage<SpuInfo> getSpuInfoPage(SpuInfo spuInfo, Page<SpuInfo> infoPage) {

        QueryWrapper<SpuInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("category3_id", spuInfo.getCategory3Id());
        return spuInfoMapper.selectPage(infoPage, wrapper);
    }

    @Override
    public List<BaseSaleAttr> baseSaleAttrList() {

        return baseSaleAttrMapper.selectList(null);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveSpuInfo(SpuInfo spuInfo) {

        spuInfoMapper.insert(spuInfo);

        //2，保存图片列表
        List<SpuImage> spuImageList = spuInfo.getSpuImageList();
        if (!CollectionUtils.isEmpty(spuImageList)) {

            for (SpuImage spuImage : spuImageList) {
                spuImage.setSpuId(spuInfo.getId());
                spuImageMapper.insert(spuImage);
            }
        }

        //3.1保存销售属性
        List<SpuSaleAttr> saleAttrList = spuInfo.getSpuSaleAttrList();
        for (SpuSaleAttr spuSaleAttr : saleAttrList) {
            spuSaleAttr.setSpuId(spuInfo.getId());
            spuSaleAttrMapper.insert(spuSaleAttr);
            //3.2保存销售属性值

            List<SpuSaleAttrValue> spuSaleAttrValueList = spuSaleAttr.getSpuSaleAttrValueList();
            if (!CollectionUtils.isEmpty(spuSaleAttrValueList)) {

                for (SpuSaleAttrValue attrValue : spuSaleAttrValueList) {
                    attrValue.setSpuId(spuInfo.getId());
                    attrValue.setSaleAttrName(spuSaleAttr.getSaleAttrName());
                    spuSaleAttrValueMapper.insert(attrValue);
                }
            }

        }

        //4，保存海报
        List<SpuPoster> spuPosterList = spuInfo.getSpuPosterList();
        if (!CollectionUtils.isEmpty(spuPosterList)) {

            for (SpuPoster spuPoster : spuPosterList) {
                spuPoster.setSpuId(spuInfo.getId());
                spuPosterMapper.insert(spuPoster);
            }

        }


    }

    @Override
    public List<SpuSaleAttr> spuSaleAttrList(Long spuId) {

        return spuSaleAttrMapper.selectSpuSaleAttrList(spuId);
    }

    /**
     * 通过spuid查询到skuid（skuinfo表），
     * 将spuimage的图片，地址，赋给skuimage中的图片，地址
     *
     * @param spuId
     * @return
     */
    @Override
    public List<SpuImage> spuImageList(Long spuId) {

        QueryWrapper<SpuImage> wrapper = new QueryWrapper<>();
        wrapper.eq("spu_id", spuId);
        return spuImageMapper.selectList(wrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveSkuInfo(SkuInfo skuInfo) {

        //1,保存skuinfo
        skuInfo.setIsSale(0);
        skuInfoMapper.insert(skuInfo);

        //2，保存sku属性值集合
        List<SkuAttrValue> skuAttrValueList = skuInfo.getSkuAttrValueList();

        if (!CollectionUtils.isEmpty(skuAttrValueList)) {
            for (SkuAttrValue skuAttrValue : skuAttrValueList) {
                skuAttrValue.setSkuId(skuInfo.getId());
                skuAttrValueMapper.insert(skuAttrValue);
            }
        }


        //3，保存sku销售属性值集合
        List<SkuSaleAttrValue> saleAttrValueList = skuInfo.getSkuSaleAttrValueList();
        if (!CollectionUtils.isEmpty(saleAttrValueList)) {
            for (SkuSaleAttrValue saleAttrValue : saleAttrValueList) {

                saleAttrValue.setSpuId(skuInfo.getSpuId());
                saleAttrValue.setSkuId(skuInfo.getId());
                skuSaleAttrValueMapper.insert(saleAttrValue);
            }
        }

        //4，保存sku图片集合

        List<SkuImage> skuImageList = skuInfo.getSkuImageList();
        if (!CollectionUtils.isEmpty(skuImageList)) {
            for (SkuImage skuImage : skuImageList) {

                skuImage.setSkuId(skuInfo.getId());
                skuImageMapper.insert(skuImage);
            }
        }

        //添加数据的时候，加上布隆过滤器
        RBloomFilter<Object> bloomFilter = redissonClient.getBloomFilter(RedisConst.SKU_BLOOM_FILTER);
        //添加数据
        bloomFilter.add(skuInfo.getId());
    }

    @Override
    public IPage<SkuInfo> skuListPage(Page<SkuInfo> skuInfoPage) {
        QueryWrapper<SkuInfo> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        return skuInfoMapper.selectPage(skuInfoPage, wrapper);

    }

    @Override
    public void onSale(Long skuId) {
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setIsSale(1);
        skuInfo.setId(skuId);
        skuInfoMapper.updateById(skuInfo);
    }

    @Override
    public void cancelSale(Long skuId) {
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setIsSale(0);
        skuInfo.setId(skuId);
        skuInfoMapper.updateById(skuInfo);
    }

    @Override
    @GmallCache(prefix = "sku:")  //key   sku:1314:info
    public SkuInfo getSkuInfo(Long skuId) {


        return getSkuInfoDB(skuId);
//        return getSkuInfoRedis(skuId);
//        return getSkuInfoRedisson(skuId);

    }

    /**
     * redisson改造skuinfo信息获取
     *
     * @param skuId
     * @return
     */
    private SkuInfo getSkuInfoRedisson(Long skuId) {
        try {
            String skuKey = RedisConst.SKUKEY_PREFIX + skuId + RedisConst.SKUKEY_SUFFIX;
            SkuInfo skuInfo = (SkuInfo) redisTemplate.opsForValue().get(skuKey);

            //是否获取了数据
            if (skuInfo == null) {


                //定义锁的key
                String skuLock = RedisConst.SKUKEY_PREFIX + skuKey + RedisConst.SKULOCK_SUFFIX;

                RLock lock = redissonClient.getLock(skuLock);
                /**
                 * 尝试获取锁的最大等待时间。   RedisConst.SKULOCK_EXPIRE_PX1
                 * RedisConst.SKULOCK_EXPIRE_PX2   过期时间
                 *
                 */
                boolean res = lock.tryLock(RedisConst.SKULOCK_EXPIRE_PX1, RedisConst.SKULOCK_EXPIRE_PX2, TimeUnit.SECONDS);
                if (res) {

                    try {
                        //获取到了锁，查询数据库
                        skuInfo = getSkuInfoDB(skuId);

                        //判断  数据库里的值是不是本来就是空
                        if (skuInfo == null) {

                            //数据库是空，存储null到redis
                            skuInfo = new SkuInfo();

                            //TODO 这里不直接存储null，防止上面将null强转成skuinfo报错：
                            // SkuInfo skuInfo = (SkuInfo) redisTemplate.opsForValue().get(skuKey);
                            redisTemplate.opsForValue().set(skuKey, skuInfo, RedisConst.SKUKEY_TEMPORARY_TIMEOUT, TimeUnit.SECONDS);
                            return skuInfo;
                        } else {

                            //存储
                            redisTemplate.opsForValue().set(skuKey, skuInfo, RedisConst.SKUKEY_TIMEOUT, TimeUnit.SECONDS);
                            return skuInfo;
                        }
                    } finally {
                        lock.unlock();
                    }


                } else {
                    //没有获取到锁
                    //手动等待100毫秒自旋（不设置也会自旋）
                    Thread.sleep(100);
                    return getSkuInfoRedisson(skuId);

                }

            } else {

                //缓存中有数据
                return skuInfo;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //兜底方法
        return getSkuInfoDB(skuId);
    }


    /**
     * 获取skuInfo从缓存中获取数据
     * redis实现分布式锁
     *
     * @param skuId
     * @return 实现步骤:
     * 1．定义存储skuinfo的key
     * 2.根据skuKey获取skuinfo的缓存数据
     * 3判断
     * 有︰直
     * 按返回结束
     * 没有
     * 定义锁的key
     * 尝试加锁
     * 失败
     * 睡眠，重试自旋
     * 成功
     * 查询数据库
     * 判断是否有值
     * 有，直接返回，缓存到数据库
     * 没有，创建空值，返回数裾
     * 释放锁
     */
    private SkuInfo getSkuInfoRedis(Long skuId) {

        try {
            //存储skuKey  sku:1314:info
            String skuKey = RedisConst.SKUKEY_PREFIX + skuId + RedisConst.SKUKEY_SUFFIX;

            SkuInfo skuInfo = (SkuInfo) redisTemplate.opsForValue().get(skuKey);
            if (skuInfo == null) {
                //缓存中没有数据
                //定义锁的key
                String lockKey = RedisConst.SKUKEY_PREFIX + skuId + RedisConst.SKULOCK_SUFFIX;

                //生成uuid标识
                String uuid = UUID.randomUUID().toString().replaceAll("-", "");

                //获取锁
                Boolean flag = redisTemplate.opsForValue().setIfAbsent(lockKey, uuid, RedisConst.SKULOCK_EXPIRE_PX2, TimeUnit.SECONDS);

                if (flag) {
                    //获取到了锁
                    System.out.println("获取到分布式锁");
                    SkuInfo skuInfoDB = getSkuInfoDB(skuId);

                    if (skuInfoDB == null) {

                        //避免缓存穿透，空值放入缓存
                        SkuInfo skuInfo1 = new SkuInfo();

                        redisTemplate.opsForValue().set(skuKey, skuInfo1);
                        return skuInfo1;
                    }
                    //数据库查询数据不为空

                    //存储到缓存
                    redisTemplate.opsForValue().set(skuKey, skuInfoDB);


                    //Lua脚本
                    String script = "if redis.call(\"get\",KEYS[1]) == ARGV[1]\n" + "then\n" + "    return redis.call(\"del\",KEYS[1])\n" + "else\n" + "    return 0\n" + "end";
                    DefaultRedisScript<Long> defaultRedisScript = new DefaultRedisScript<>();
                    defaultRedisScript.setScriptText(script);
                    defaultRedisScript.setResultType(Long.class);


                    //key 和value匹配时才删除
                    redisTemplate.execute(defaultRedisScript, Arrays.asList(lockKey), uuid);
                    //返回数据
                    return skuInfoDB;

                } else {
                    Thread.sleep(100);
                    return getSkuInfoRedis(skuId);
                }


            } else {
                return skuInfo;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //兜底，在上面从缓存中获取的过程中出现异常，这行代码也必须要执行
        return getSkuInfoDB(skuId);
    }

    /**
     * 查询数据库获取sku数据
     *
     * @param skuId
     * @return
     */

    private SkuInfo getSkuInfoDB(Long skuId) {
        SkuInfo skuInfo = skuInfoMapper.selectById(skuId);

        QueryWrapper<SkuImage> imageQueryWrapper = new QueryWrapper<>();
        imageQueryWrapper.eq("sku_id", skuId);
        List<SkuImage> skuImages = skuImageMapper.selectList(imageQueryWrapper);
        skuInfo.setSkuImageList(skuImages);

//        QueryWrapper<SkuAttrValue> attrValueQueryWrapper = new QueryWrapper<>();
//        attrValueQueryWrapper.eq("sku_id", skuId);
//        List<SkuAttrValue> skuAttrValueList = skuAttrValueMapper.selectList(attrValueQueryWrapper);
//        skuInfo.setSkuAttrValueList(skuAttrValueList);
//
//        QueryWrapper<SkuSaleAttrValue> skuSaleAttrValueQueryWrapper = new QueryWrapper<>();
//        skuSaleAttrValueQueryWrapper.eq("sku_id", skuId);
//        List<SkuSaleAttrValue> skuSaleAttrValueList = skuSaleAttrValueMapper.selectList(skuSaleAttrValueQueryWrapper);
//        skuInfo.setSkuSaleAttrValueList(skuSaleAttrValueList);

        return skuInfo;
    }

    @Override
    public BaseCategoryView getCategoryView(Long category3Id) {

        /**
         *
         * 优化sql，建视图：
         * CREATE VIEW base_category_view AS
         * SELECT
         *   c3.id AS id,
         *   c1.id AS category1_id,
         *   c1.name AS category1_name,
         *   c2.id AS category2_id,
         *   c2.name AS category2_name,
         *   c3.id AS category3_id,
         *   c3.name AS category3_name
         * FROM
         *   base_category1 c1
         *   INNER JOIN base_category2 c2
         *     ON c1.id = c2.category1_id
         *   INNER JOIN base_category3 c3
         *     ON c2.id = c3.category2_id
         *
         */

        return baseCategoryViewMapper.selectById(category3Id);

    }

    @Override
    public BigDecimal getSkuPrice(Long skuId) {

        SkuInfo skuInfo = skuInfoMapper.selectById(skuId);
        if (skuInfo != null) {
            return skuInfo.getPrice();
        }
        return null;
    }

    @Override
    public List<SpuSaleAttr> getSpuSaleAttrListCheckBySku(Long skuId, Long spuId) {

        return spuSaleAttrMapper.getSpuSaleAttrListCheckBySku(skuId, spuId);
    }

    @Override
    public Map getSkuValueIdsMap(Long spuId) {
        List<Map> mapList = skuSaleAttrValueMapper.getSkuValueIdsMap(spuId);

        Map<Object, Object> resultMap = new HashMap<>();

        if (!CollectionUtils.isEmpty(mapList)) {
            for (Map map : mapList) {
                resultMap.put(map.get("value_ids"), map.get("sku_id"));
            }
        }
        return resultMap;
    }

    @Override
    public List<SpuPoster> findSpuPosterBySpuId(Long spuId) {
        QueryWrapper<SpuPoster> wrapper = new QueryWrapper<>();
        wrapper.eq("spu_id", spuId);
        List<SpuPoster> spuPosterList = spuPosterMapper.selectList(wrapper);
        return spuPosterList;
    }

    @Override
    public List<BaseAttrInfo> getAttrList(Long skuId) {

        List<BaseAttrInfo> baseAttrInfoList = baseAttrInfoMapper.getAttrList(skuId);

        return baseAttrInfoList;
    }


}
