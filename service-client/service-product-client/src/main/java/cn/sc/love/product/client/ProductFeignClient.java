package cn.sc.love.product.client;

import cn.sc.love.common.result.Result;
import cn.sc.love.model.product.*;
import cn.sc.love.product.client.impl.ProductDegradeFeignClient;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author YPT
 * @create 2023-05-20-4:35
 */

@FeignClient(value = "service-product", fallback = ProductDegradeFeignClient.class)
public interface ProductFeignClient {

    @GetMapping("/api/product/inner/getTrademark/{tmId}")
    public BaseTrademark getTrademark(Long tmId);

    @GetMapping("/api/product/inner/getBaseCategoryList")
    public Result getBaseCategoryList();

    @ApiOperation("根据skuId获取选中销售属性")
    @GetMapping("/api/product/inner/getAttrList/{skuId}")
    public List<BaseAttrInfo> getAttrList(@PathVariable Long skuId);

    @ApiOperation("根据spuid获取海报集合")
    @GetMapping("/api/product/inner/findSpuPosterBySpuId/{spuId}")
    public List<SpuPoster> findSpuPosterBySpuId(@PathVariable Long spuId);

    @ApiOperation("根据spuId 获取到销售属性值Id 与skuId 组成的数据集")
    @GetMapping("/api/product/inner/getSkuValueIdsMap/{spuId}")
    public Map getSkuValueIdsMap(@PathVariable Long spuId);

    @ApiOperation("根据skuid，spuid获取销售属性选中状态信息")
    @GetMapping("/api/product/inner/getSpuSaleAttrListCheckBySku/{skuId}/{spuId}")

    public List<SpuSaleAttr> getSpuSaleAttrListCheckBySku(@PathVariable Long skuId, @PathVariable Long spuId);

    @ApiOperation("根据skuId查询sku名称和图片列表")
    @GetMapping("/api/product/inner/getSkuInfo/{skuId}")
    public SkuInfo getSkuInfo(@PathVariable Long skuId);

    @ApiOperation("根据三级分类id查询各级分类id")
    @GetMapping("/api/product/inner/getCategoryView/{category3Id}")
    public BaseCategoryView getCategoryView(@PathVariable Long category3Id);

    @ApiOperation("根据skuId查询价格")
    @GetMapping("/api/product/inner/getSkuPrice/{skuId}")
    public BigDecimal getSkuPrice(@PathVariable Long skuId);

}
