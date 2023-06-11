package cn.sc.love.item.client;

import cn.sc.love.common.result.Result;
import cn.sc.love.item.client.impl.ItemDegradeFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author YPT
 * @create 2023-05-20-22:41
 */
@FeignClient(value = "service-item", fallback = ItemDegradeFeignClient.class)
public interface ItemFeignClient {


    @GetMapping("/api/item/{skuId}")
    public Result getItem(@PathVariable Long skuId);
}
