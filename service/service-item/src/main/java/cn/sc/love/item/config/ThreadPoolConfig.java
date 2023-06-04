package cn.sc.love.item.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author yupengtao
 * @Date 2023/6/4 22:40
 **/
@Configuration
public class ThreadPoolConfig {

    /**
     * 核心线程数
     * 最大线程数
     * 空闲存活时间
     * 时间单位
     * 阻塞对象
     * 默认：
     * 线程工厂
     * 拒绝策略
     * @return
     */
    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        return new ThreadPoolExecutor(
                50,
                500,
                30,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10000)
        );
    }


}
