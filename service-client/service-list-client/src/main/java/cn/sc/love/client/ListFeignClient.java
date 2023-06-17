package cn.sc.love.client;

import cn.sc.love.client.impl.ListDegradeFeignClient;
import cn.sc.love.common.result.Result;
import cn.sc.love.model.list.SearchParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * @author YPT
 * @create 2023-05-20-22:41
 */
@FeignClient(value = "service-list", fallback = ListDegradeFeignClient.class)
public interface ListFeignClient {

    @GetMapping("/api/list/inner/incrHotScore/{skuId}")
    public Result incrHotScore(Long skuId);
    /**
     * 搜索商品
     * @param listParam
     * @return
     */
    @PostMapping("/api/list")
    Result list(@RequestBody SearchParam listParam);

    /**
     * 上架商品
     * @param skuId
     * @return
     */
    @GetMapping("/api/list/inner/upperGoods/{skuId}")
    Result upperGoods(@PathVariable("skuId") Long skuId);

    /**
     * 下架商品
     * @param skuId
     * @return
     */
    @GetMapping("/api/list/inner/lowerGoods/{skuId}")
    Result lowerGoods(@PathVariable("skuId") Long skuId);

}