package cn.sc.love.product.controller;

import cn.sc.love.common.result.Result;
import cn.sc.love.model.product.SkuInfo;
import cn.sc.love.model.product.SpuSaleAttr;
import cn.sc.love.product.service.ManagerService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author YPT
 * @create 2023-05-10-18:22
 */
@Api(tags = "Sku管理接口")
@RestController
@RequestMapping("/admin/product")
public class SkuManagerController {

    @Autowired
    private ManagerService managerService;

    @ApiOperation("根据spuid查询销售属性和销售属性值集合")
    @GetMapping("spuSaleAttrList/{spuId}")
    public Result spuSaleAttrList(@PathVariable Long spuId) {
        List<SpuSaleAttr> spuSaleAttrList = managerService.spuSaleAttrList(spuId);

        return Result.ok(spuSaleAttrList);
    }

    @ApiOperation("根据spuid查询spu图片列表")
    @GetMapping("spuImageList/{spuId}")
    public Result spuImageList(@PathVariable Long spuId) {

        return Result.ok(managerService.spuImageList(spuId));
    }

    @ApiOperation("保存sku信息")
    @PostMapping("saveSkuInfo")
    public Result saveSkuInfo(@RequestBody SkuInfo skuInfo) {

        managerService.saveSkuInfo(skuInfo);
        return Result.ok();
    }

    @ApiOperation("sku列表")
    @GetMapping("list/{page}/{limit}")
    public Result skuListPage(@PathVariable Long page, @PathVariable Long limit) {
        Page<SkuInfo> skuInfoPage = new Page<>(page, limit);

        IPage<SkuInfo> skuInfoIPage = managerService.skuListPage(skuInfoPage);
        return Result.ok(skuInfoIPage);

    }
}
