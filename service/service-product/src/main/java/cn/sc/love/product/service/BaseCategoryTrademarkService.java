package cn.sc.love.product.service;

import cn.sc.love.model.product.BaseTrademark;

import java.util.List;

/**
 * @author YPT
 * @create 2023-05-04-21:53
 */
public interface BaseCategoryTrademarkService {
    List<BaseTrademark> findTrademarkList(Long category3Id);

    void remove(Long category3Id, Long trademarkId);
}
