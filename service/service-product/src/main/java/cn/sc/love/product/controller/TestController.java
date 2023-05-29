package cn.sc.love.product.controller;

import cn.sc.love.common.result.Result;
import cn.sc.love.product.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author YPT
 * @create 2023-05-27-22:39
 */

@RestController
@RequestMapping("/admin/product/test")
public class TestController {
    @Autowired
    private TestService testService;

    @GetMapping("/testLock")
    public Result testLock() {

        testService.testLock();
        return Result.ok();
    }

    @GetMapping("/read")
    public Result read() {

        String msg = testService.readLock();
        return Result.ok(msg);
    }

    @GetMapping("/write")
    public Result writeLock() {

        String msg = testService.writeLock();
        return Result.ok(msg);
    }

}
