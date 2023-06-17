package cn.sc.love.list.controller;

import cn.sc.love.common.result.Result;
import cn.sc.love.list.service.SearchService;
import cn.sc.love.model.list.Goods;
import cn.sc.love.model.list.SearchParam;
import cn.sc.love.model.list.SearchResponseVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @Author yupengtao
 * @Date 2023/6/10 02:56
 **/

@RestController
@RequestMapping("/api/list")
public class ListApiController {

    @Autowired
    private ElasticsearchRestTemplate restTemplate;

    @Autowired
    private SearchService searchService;

    /**
     * 热度排名
     * @param skuId
     * @return
     */
    @GetMapping("/inner/incrHotScore/{skuId}")
    public Result incrHotScore(Long skuId) {

        searchService.incrHotScore(skuId);
        return Result.ok();
    }

    @ApiOperation("商品下架")
    @GetMapping("/inner/lowerGoods/{skuId}")
    public Result lowerGoods(Long skuId) {
        searchService.lowerGoods(skuId);
        return Result.ok();
    }

    @ApiOperation("商品上架")
    @GetMapping("/inner/upperGoods/{skuId}")
    public Result upperGoods(@PathVariable Long skuId) {
        searchService.upperGoods(skuId);
        return Result.ok();

    }

    @GetMapping("createIndex")
    public Result createIndex() {

        //创建索引库
        restTemplate.createIndex(Goods.class);

        //建立mapping
        restTemplate.putMapping(Goods.class);
        return Result.ok();
    }

    /**
     * 搜索商品
     * @param searchParam
     * @return
     * @throws IOException
     */
    @PostMapping
    public Result list(@RequestBody SearchParam searchParam) throws IOException {
        SearchResponseVo response = searchService.search(searchParam);
        return Result.ok(response);
    }

}
