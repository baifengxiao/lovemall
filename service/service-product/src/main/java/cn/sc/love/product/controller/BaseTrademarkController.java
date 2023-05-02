package cn.sc.love.product.controller;

import cn.sc.love.common.result.Result;
import cn.sc.love.model.product.BaseTrademark;
import cn.sc.love.product.service.BaseTrademarkService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author YPT
 * @create 2023-04-27-23:24
 */

@RestController
@RequestMapping("/admin/product/baseTrademark")
public class BaseTrademarkController {

    @Autowired
    private BaseTrademarkService baseTrademarkService;

    @GetMapping("{page}/{limit}")
    public Result getBaseTrademarkPage(@PathVariable Long page, @PathVariable Long limit) {

        Page<BaseTrademark> baseTrademarkPage = new Page<>(page, limit);
        IPage<BaseTrademark> baseTrademarkIPage = baseTrademarkService.getBaseTrademarkPage(baseTrademarkPage);

        return Result.ok(baseTrademarkIPage);

    }


//    @PostMapping("/save")
//
//
//    @DeleteMapping("remove/{id}")
//
//
//    @PutMapping("/update")
//
//    @GetMapping("/get/{id}")


}
