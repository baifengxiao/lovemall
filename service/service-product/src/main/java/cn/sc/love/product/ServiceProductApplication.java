package cn.sc.love.product;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author YPT
 * @create ${YEAR}-${MONTH}-${DAY}-${TIME}
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan({"cn.sc.love"})
public class ServiceProductApplication {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}