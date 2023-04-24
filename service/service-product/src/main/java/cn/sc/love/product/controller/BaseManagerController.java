package cn.sc.love.product.controller;

import cn.sc.love.common.result.Result;
import cn.sc.love.model.product.BaseCategory1;
import cn.sc.love.model.product.BaseCategory2;
import cn.sc.love.model.product.BaseCategory3;
import cn.sc.love.product.service.ManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author YPT
 * @create 2023-04-24-15:05
 */
@Api(tags = "商品基础开发接口")
@RestController
@RequestMapping("/admin/product")
public class BaseManagerController {

    @Autowired
    private ManagerService managerService;

    @GetMapping("/getCategory1")
    @ApiOperation("查询一级分类")
    public Result getCategory1() {

        List<BaseCategory1> baseCategory1List = managerService.getCategory1();
        return Result.ok(baseCategory1List);

    }

    @GetMapping("/getCategory2/{category1Id}")
    @ApiOperation("查询二级分类")
    public Result getCategory2(@PathVariable Long category1Id) {

        List<BaseCategory2> baseCategory2List = managerService.getCategory2(category1Id);
        return Result.ok(baseCategory2List);
    }

    @GetMapping("/getCategory3/{category2Id}")
    @ApiOperation("查询三级分类")
    public Result getCategory3(@PathVariable Long category2Id) {

        List<BaseCategory3> baseCategory3List = managerService.getCategory3(category2Id);
        return Result.ok(baseCategory3List);
    }


}

