package cn.sc.love.item.service.impl;

import cn.sc.love.client.ListFeignClient;
import cn.sc.love.item.service.ItemService;
import cn.sc.love.model.product.*;
import cn.sc.love.product.client.ProductFeignClient;
import com.alibaba.fastjson.JSON;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author YPT
 * @create 2023-05-17-13:53
 */
@Service
@SuppressWarnings("all")
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ProductFeignClient productFeignClient;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private ThreadPoolExecutor executor;
    @Autowired
    private ListFeignClient listFeignClient;


    @Override
    public HashMap<String, Object> getItem(Long skuId) {
        HashMap<String, Object> resultMap = new HashMap<>();

        //判断数据是否存在布隆过滤器
//        RBloomFilter<Object> bloomFilter = redissonClient.getBloomFilter(RedisConst.SKU_BLOOM_FILTER);
//        if (!bloomFilter.contains(skuId)){
//
//            //不存在的skuId,直接返回空值,终止方法执行
//            return resultMap;
//        }

        CompletableFuture<SkuInfo> skuInfoCompletableFuture = CompletableFuture.supplyAsync(new Supplier<SkuInfo>() {
            @Override
            public SkuInfo get() {
                //获取sku信息和图片列表
                SkuInfo skuInfo = productFeignClient.getSkuInfo(skuId);
                resultMap.put("skuInfo", skuInfo);
                return skuInfo;
            }
        }, executor);


        CompletableFuture<Void> skuPriceCompletableFuture = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                //获取实时价格
                BigDecimal skuPrice = productFeignClient.getSkuPrice(skuId);
                resultMap.put("price", skuPrice);
            }
        }, executor);

        //和上一个操作 skuInfoCompletableFuture是一组操作，拿到结果categoryView
        CompletableFuture<Void> categoryViewCompletableFuture = skuInfoCompletableFuture.thenAcceptAsync(new Consumer<SkuInfo>() {
            @Override
            public void accept(SkuInfo skuInfo) {
                //获取三级分类
                BaseCategoryView categoryView = productFeignClient.getCategoryView(skuInfo.getCategory3Id());
                resultMap.put("categoryView", categoryView);
            }
        }, executor);

        CompletableFuture<Void> spuSaleAttrListCheckBySkuCompletableFuture = skuInfoCompletableFuture.thenAcceptAsync(new Consumer<SkuInfo>() {

            @Override
            public void accept(SkuInfo skuInfo) {
                //获取销售属性和选中状态
                List<SpuSaleAttr> spuSaleAttrList = productFeignClient.getSpuSaleAttrListCheckBySku(skuId, skuInfo.getSpuId());
                resultMap.put("spuSaleAttrList", spuSaleAttrList);
            }
        }, executor);
        CompletableFuture<Void> skuValueIdsMapCompletableFuture = skuInfoCompletableFuture.thenAcceptAsync(new Consumer<SkuInfo>() {

            @Override
            public void accept(SkuInfo skuInfo) {
                //获取商品切换数据
                Map skuValueIdsMap = productFeignClient.getSkuValueIdsMap(skuInfo.getSpuId());
                resultMap.put("valuesSkuJson", JSON.toJSONString(skuValueIdsMap));
            }
        }, executor);
        CompletableFuture<Void> findSpuPosterBySpuIdCompletableFuture = skuInfoCompletableFuture.thenAcceptAsync(new Consumer<SkuInfo>() {

            @Override
            public void accept(SkuInfo skuInfo) {
                //获取海报信息
                List<SpuPoster> spuPosterList = productFeignClient.findSpuPosterBySpuId(skuInfo.getSpuId());
                resultMap.put("spuPosterList", spuPosterList);
            }
        }, executor);


        CompletableFuture<Void> attrListCompletableFuture = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                //获取平台信息
                List<BaseAttrInfo> skuAttrList = productFeignClient.getAttrList(skuId);
                List<Map<String, String>> spuAttrList = skuAttrList.stream().map(baseAttrInfo -> {

                    Map<String, String> map = new HashMap<>();
                    map.put("attrName", baseAttrInfo.getAttrName());
                    map.put("attrValue", baseAttrInfo.getAttrValueList().get(0).getValueName());
                    return map;
                }).collect(Collectors.toList());
                resultMap.put("spuAttrList", spuAttrList);
            }
        }, executor);

        //更新商品热度
        CompletableFuture<Void> incrHotScoreCompletableFuture = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                listFeignClient.incrHotScore(skuId);

            }
        }, executor);

//多任务组合，所有的异步任务完成才是完成
        CompletableFuture.allOf(skuInfoCompletableFuture, skuPriceCompletableFuture, categoryViewCompletableFuture, findSpuPosterBySpuIdCompletableFuture, attrListCompletableFuture, skuValueIdsMapCompletableFuture, skuValueIdsMapCompletableFuture,incrHotScoreCompletableFuture).join();
        return resultMap;
    }
}
