package cn.sc.love.product.client.impl;

import cn.sc.love.common.result.Result;
import cn.sc.love.model.product.*;
import cn.sc.love.product.client.ProductFeignClient;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author YPT
 * @create 2023-05-20-4:38
 */
@Component
//降级方法
public class ProductDegradeFeignClient implements ProductFeignClient {
    @Override
    public Result getBaseCategoryList() {
        return Result.fail();
    }

    @Override
    public List<BaseAttrInfo> getAttrList(Long skuId) {
        return null;
    }

    @ApiOperation("根据spuid获取海报集合")
    @Override
    public List<SpuPoster> findSpuPosterBySpuId(Long spuId) {
        return null;
    }

    @Override
    public Map getSkuValueIdsMap(Long spuId) {
        return null;
    }

    @Override
    public List<SpuSaleAttr> getSpuSaleAttrListCheckBySku(Long skuId, Long spuId) {
        return null;
    }

    @Override
    public SkuInfo getSkuInfo(Long skuId) {
        return null;
    }

    @Override
    public BaseCategoryView getCategoryView(Long category3Id) {
        return null;
    }

    @Override
    public BigDecimal getSkuPrice(Long skuId) {
        return null;
    }
}
