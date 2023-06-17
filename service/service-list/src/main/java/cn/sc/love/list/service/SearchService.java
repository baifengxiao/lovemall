package cn.sc.love.list.service;

import cn.sc.love.model.list.SearchParam;
import cn.sc.love.model.list.SearchResponseVo;

import java.io.IOException;

public interface SearchService {

    /**
     * 搜索列表
     * @param searchParam
     * @return
     * @throws IOException
     */
    SearchResponseVo search(SearchParam searchParam) throws IOException;
    void upperGoods(Long skuId);

    void lowerGoods(Long skuId);

    void incrHotScore(Long skuId);
}
