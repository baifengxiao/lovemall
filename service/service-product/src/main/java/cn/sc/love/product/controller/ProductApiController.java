package cn.sc.love.product.controller;

import cn.sc.love.common.result.Result;
import cn.sc.love.model.product.*;
import cn.sc.love.product.service.ManagerService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author YPT
 * @create 2023-05-17-14:17
 */
@Api(tags = "product-api-controller")
@RestController
@RequestMapping("/api/product/inner")
public class ProductApiController {

    @Autowired
    private ManagerService managerService;


    @GetMapping("/getBaseCategoryList")
    public Result getBaseCategoryList() {

        List<JSONObject> result = managerService.getBaseCategoryList();

        return Result.ok(result);
    }

    //这七个不是最终结果，不用包装一层result
    @ApiOperation("根据skuId获取选中销售属性")
    @GetMapping("/getAttrList/{skuId}")
    public List<BaseAttrInfo> getAttrList(@PathVariable Long skuId) {
        List<BaseAttrInfo> list = managerService.getAttrList(skuId);
        return list;
    }

    @ApiOperation("根据spuid获取海报集合")
    @GetMapping("/findSpuPosterBySpuId/{spuId}")
    public List<SpuPoster> findSpuPosterBySpuId(@PathVariable Long spuId) {
        List<SpuPoster> list = managerService.findSpuPosterBySpuId(spuId);
        return list;
    }

    @ApiOperation("根据spuId 获取到销售属性值Id 与skuId 组成的数据集")
    @GetMapping("/getSkuValueIdsMap/{spuId}")
    public Map getSkuValueIdsMap(@PathVariable Long spuId) {
        //查询对应关系集合
        Map valueIdsMap = managerService.getSkuValueIdsMap(spuId);
        return valueIdsMap;


    }

    @ApiOperation("根据skuid，spuid获取销售属性选中状态信息")
    @GetMapping("/getSpuSaleAttrListCheckBySku/{skuId}/{spuId}")

    public List<SpuSaleAttr> getSpuSaleAttrListCheckBySku(@PathVariable Long skuId, @PathVariable Long spuId) {
        List<SpuSaleAttr> list = managerService.getSpuSaleAttrListCheckBySku(skuId, spuId);
        return list;
    }

    @ApiOperation("根据skuId查询sku名称和图片列表")
    @GetMapping("/getSkuInfo/{skuId}")
    public SkuInfo getSkuInfo(@PathVariable Long skuId) {
        SkuInfo skuInfo = managerService.getSkuInfo(skuId);
        return skuInfo;
    }

    @ApiOperation("根据三级分类id查询各级分类id")
    @GetMapping("/getCategoryView/{category3Id}")
    public BaseCategoryView getCategoryView(@PathVariable Long category3Id) {
        return managerService.getCategoryView(category3Id);
    }

    @ApiOperation("根据skuId查询价格")
    @GetMapping("/getSkuPrice/{skuId}")
    public BigDecimal getSkuPrice(@PathVariable Long skuId) {
        return managerService.getSkuPrice(skuId);
    }

}
