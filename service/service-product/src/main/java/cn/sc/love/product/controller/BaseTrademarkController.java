package cn.sc.love.product.controller;

import cn.sc.love.common.result.Result;
import cn.sc.love.model.product.BaseTrademark;
import cn.sc.love.product.service.BaseTrademarkService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author YPT
 * @create 2023-04-27-23:24
 */

@Api("品牌管理接口")
@RestController
@RequestMapping("/admin/product/baseTrademark")
public class BaseTrademarkController {

    @Autowired
    private BaseTrademarkService baseTrademarkService;

    @ApiOperation("品牌分页列表")
    @GetMapping("{page}/{limit}")
    public Result getBaseTrademarkPage(@PathVariable Long page, @PathVariable Long limit) {

        Page<BaseTrademark> baseTrademarkPage = new Page<>(page, limit);
        IPage<BaseTrademark> baseTrademarkIPage = baseTrademarkService.getBaseTrademarkPage(baseTrademarkPage);

        return Result.ok(baseTrademarkIPage);

    }


    @PostMapping("/save")
    public Result save(@RequestBody BaseTrademark baseTrademark) {
        baseTrademarkService.save(baseTrademark);
        return Result.ok();
    }


    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        baseTrademarkService.removeById(id);
        return Result.ok();
    }


    @PutMapping("/update")
    public Result update(@RequestBody BaseTrademark baseTrademark) {
        baseTrademarkService.updateById(baseTrademark);
        return Result.ok();
    }

    @GetMapping("/get/{id}")
    public Result get(@PathVariable Long id) {
        BaseTrademark baseTrademark = baseTrademarkService.getById(id);
        return Result.ok(baseTrademark);
    }
}
