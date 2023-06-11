package cn.sc.love.client.impl;


import cn.sc.love.client.ListFeignClient;
import cn.sc.love.common.result.Result;
import org.springframework.stereotype.Component;

/**
 * @author YPT
 * @create 2023-05-20-22:42
 */
@Component
public class ListDegradeFeignClient implements ListFeignClient {

    @Override
    public Result incrHotScore(Long skuId) {
        return Result.fail();
    }
}
