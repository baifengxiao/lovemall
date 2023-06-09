package cn.sc.love.product.controller;


import cn.sc.love.common.result.Result;
import cn.sc.love.model.product.BaseSaleAttr;
import cn.sc.love.model.product.SpuInfo;
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
 * @create 2023-04-27-23:25
 */
@RequestMapping("/admin/product")
@RestController
@Api(tags = "SPU管理接口")
public class SpuManageController {

    @Autowired
    ManagerService managerService;


    @GetMapping("{page}/{limit}")
    @ApiOperation("三级分类下的spu分页列表")
    public Result getSpuInfoPage(@PathVariable Long page, @PathVariable Long limit, SpuInfo spuInfo) {

        Page<SpuInfo> infoPage = new Page<>(page, limit);
        IPage<SpuInfo> infoIPage = managerService.getSpuInfoPage(spuInfo, infoPage);
        return Result.ok(infoIPage);
    }

    @ApiOperation("销售属性列表")
    @GetMapping("/baseSaleAttrList")
    public Result baseSaleAttrList() {
        List<BaseSaleAttr> baseSaleAttrList = managerService.baseSaleAttrList();
        return Result.ok(baseSaleAttrList);
    }

    @ApiOperation("保存Spuinfo信息")
    @PostMapping("/saveSpuInfo")
    public Result saveSpuInfo(@RequestBody SpuInfo spuInfo) {


        managerService.saveSpuInfo(spuInfo);
        return Result.ok();

    }

}
