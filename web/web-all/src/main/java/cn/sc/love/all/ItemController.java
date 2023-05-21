package cn.sc.love.all;

import cn.sc.love.common.result.Result;
import cn.sc.love.item.client.ItemFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

/**
 * @author YPT
 * @create 2023-05-20-23:32
 */
@Controller
@SuppressWarnings("all")
public class ItemController {

    @Autowired
    private ItemFeignClient itemFeignClient;

    @GetMapping("{skuId}.html")
    public String getItem(@PathVariable Long skuId, Model model) {
        Result<Map<String, Object>> item = itemFeignClient.getItem(skuId);
        model.addAllAttributes(item.getData());

        //设置数据
        return "item/item";
    }
}
