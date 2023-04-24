package cn.sc.love.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author YPT
 * @create ${YEAR}-${MONTH}-${DAY}-${TIME}
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan({"cn.sc.love"})
public class ServiceProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceProductApplication.class,args);
    }
}