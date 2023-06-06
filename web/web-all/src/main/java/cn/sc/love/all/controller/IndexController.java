package cn.sc.love.all.controller;

import cn.sc.love.common.result.Result;
import cn.sc.love.product.client.ProductFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @Author yupengtao
 * @Date 2023/6/6 17:28
 **/
@Controller
@SuppressWarnings("all")
public class IndexController {

    @Autowired
    private ProductFeignClient productFeignClient;

    @Autowired
    private TemplateEngine templateEngine;

    /**
     * 第一种方式：页面模板渲染
     */
    @GetMapping({"/", "index.html"})
    public String index(Model model) {

        //远程获取三级分类的数据
        Result result = productFeignClient.getBaseCategoryList();
        //设置数据
        model.addAttribute("list", result.getData());
        return "index/index";
    }

    /**
     * Nginx：
     * 反向代理
     * 负载均衡
     * 动静分离
     *
     * @param model
     * @return
     */

    @GetMapping("/createIndex")
    @ResponseBody
    public Result createIndex() {

        //远程获取三级分类的数据
        Result result = productFeignClient.getBaseCategoryList();
        //创建对象封装数据
        Context context = new Context();
        context.setVariable("list", result.getData());

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("D:\\aaa\\index.html");
        } catch (IOException e) {
            e.printStackTrace();
        }

//        生成页面
        /**
         * 参数一:模板的位置 使用哪个模板
         * 参数二：数据，上下文对象
         * 参数三：生成页面的位置  流对象
         */

        templateEngine.process("index/index.html", context, fileWriter);
        return Result.ok();
    }
}
