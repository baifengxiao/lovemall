package cn.sc.love.product.controller;


import cn.sc.love.common.result.Result;
import cn.sc.love.model.product.BaseTrademark;
import cn.sc.love.model.product.CategoryTrademarkVo;
import cn.sc.love.product.service.BaseCategoryTrademarkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author YPT
 * @create 2023-04-27-23:18
 */
@Api(tags = "分类品牌接口")
@RestController
@RequestMapping("admin/product/baseCategoryTrademark")
public class BaseCategoryTrademarkController {

    @Autowired
    private BaseCategoryTrademarkService baseCategoryTrademarkService;

    @ApiOperation("查询该三级分类下所有品牌")
    @GetMapping("findTrademarkList/{category3Id}")
    public Result findTrademarkList(@PathVariable Long category3Id) {

        List<BaseTrademark> baseTrademarkList = baseCategoryTrademarkService.findTrademarkList(category3Id);

        return Result.ok(baseTrademarkList);

    }


    @ApiOperation("删除该三级分类所关联品牌")
    @DeleteMapping("remove/{category3Id}/{trademarkId}")
    public Result remove(@PathVariable Long category3Id, @PathVariable Long trademarkId) {
        baseCategoryTrademarkService.remove(category3Id, trademarkId);
        return Result.ok();
    }

    @ApiOperation("查询分类未关联品牌列表")
    @GetMapping("findCurrentTrademarkList/{category3Id}")
    public Result findCurrentTrademarkList(@PathVariable Long category3Id) {
        List<BaseTrademark> list = baseCategoryTrademarkService.findCurrentTrademarkList(category3Id);
        return Result.ok(list);
    }

    @ApiOperation("保存分类品牌关联")
    @PostMapping("/save")
    public Result save(@RequestBody CategoryTrademarkVo categoryTrademarkVo) {
        baseCategoryTrademarkService.save(categoryTrademarkVo);
        return Result.ok();

    }
}
