package cn.sc.love.item.controller;

import cn.sc.love.common.result.Result;
import cn.sc.love.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author YPT
 * @create 2023-05-17-13:42
 */
@RestController
@RequestMapping("api/item")
public class ItemApiController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/{skuId}")
    public Result getItem(@PathVariable Long skuId) {
        HashMap<String, Object> resultMap = itemService.getItem(skuId);
        return Result.ok(resultMap);
    }

}
