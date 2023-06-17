package cn.sc.love.client.impl;


import cn.sc.love.client.ListFeignClient;
import cn.sc.love.common.result.Result;
import cn.sc.love.model.list.SearchParam;
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

    @Override
    public Result list(SearchParam listParam) {
        return null;
    }

    @Override
    public Result upperGoods(Long skuId) {
        return null;
    }

    @Override
    public Result lowerGoods(Long skuId) {
        return null;
    }
}
