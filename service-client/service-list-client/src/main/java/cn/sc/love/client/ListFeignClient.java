package cn.sc.love.client;

import cn.sc.love.client.impl.ListDegradeFeignClient;
import cn.sc.love.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * @author YPT
 * @create 2023-05-20-22:41
 */
@FeignClient(value = "service-list", fallback = ListDegradeFeignClient.class)
public interface ListFeignClient {

    @GetMapping("/api/list/inner/incrHotScore/{skuId}")
    public Result incrHotScore(Long skuId);
}
