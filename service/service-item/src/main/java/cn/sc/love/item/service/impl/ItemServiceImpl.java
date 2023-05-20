package cn.sc.love.item.service.impl;

import cn.sc.love.item.service.ItemService;
import cn.sc.love.model.product.*;
import cn.sc.love.product.client.ProductFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author YPT
 * @create 2023-05-17-13:53
 */
@Service
@SuppressWarnings("all")
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ProductFeignClient productFeignClient;

    @Override
    public HashMap<String, Object> getItem(Long skuId) {
        HashMap<String, Object> resultMap = new HashMap<>();


        //获取sku信息和图片列表
        SkuInfo skuInfo = productFeignClient.getSkuInfo(skuId);
        resultMap.put("skuInfo", skuInfo);
        //获取实时价格
        BigDecimal skuPrice = productFeignClient.getSkuPrice(skuId);

        resultMap.put("price", skuPrice);

        if (skuInfo != null) {
            //获取三级分类
            BaseCategoryView categoryView = productFeignClient.getCategoryView(skuInfo.getCategory3Id());
            resultMap.put("categoryView", categoryView);
            //获取销售属性和选中状态
            List<SpuSaleAttr> spuSaleAttrList = productFeignClient.getSpuSaleAttrListCheckBySku(skuId, skuInfo.getSpuId());

            resultMap.put("spuSaleAttrList", spuSaleAttrList);
            //获取商品切换数据
            Map valuesSkuJson = productFeignClient.getSkuValueIdsMap(skuInfo.getSpuId());
            resultMap.put("valuesSkuJson", valuesSkuJson);

            //获取海报信息
            List<SpuPoster> spuPosterList = productFeignClient.findSpuPosterBySpuId(skuInfo.getSpuId());
            resultMap.put("spuPosterList", spuPosterList);
        }


        //获取平台信息
        List<BaseAttrInfo> skuAttrList = productFeignClient.getAttrList(skuId);
        resultMap.put("skuAttrList", skuAttrList);

        return resultMap;
    }
}
