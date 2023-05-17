package cn.sc.love.product.controller;

import cn.sc.love.model.product.BaseCategoryView;
import cn.sc.love.model.product.SkuInfo;
import cn.sc.love.product.service.ManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @ApiOperation("根据skuId查询sku名称和图片列表")
    @GetMapping("/getSkuInfo/{skuId}")
    public SkuInfo getSkuInfo(@PathVariable Long skuId) {
        SkuInfo skuInfo = managerService.getSkuInfo(skuId);
        return skuInfo;
    }

    @ApiOperation("根据三级分类id查询各级分类id")
    @GetMapping("/getCategoryView/{category3Id}")
    public BaseCategoryView getCategoryView(@PathVariable Long category3Id){
        return managerService.getCategoryView(category3Id);
    }
}
