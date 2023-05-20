package cn.sc.love.item.client.impl;

import cn.sc.love.common.result.Result;
import cn.sc.love.item.client.ItemFeignClient;
import org.springframework.stereotype.Component;

/**
 * @author YPT
 * @create 2023-05-20-22:42
 */
@Component
public class ItemDegradeFeignClient implements ItemFeignClient {
    @Override
    public Result getItem(Long skuId) {
        return Result.fail();
    }
}
