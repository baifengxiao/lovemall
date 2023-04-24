package cn.sc.love.product.controller;

import cn.sc.love.common.result.Result;
import cn.sc.love.model.product.BaseCategory1;
import cn.sc.love.product.service.ManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

        List<BaseCategory1> baseCategory1List= managerService.getCategory1();
        return Result.ok(baseCategory1List);

    }


}

