package cn.sc.love.product.service;

import cn.sc.love.model.product.BaseCategoryTrademark;
import cn.sc.love.model.product.BaseTrademark;
import cn.sc.love.model.product.CategoryTrademarkVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author YPT
 * @create 2023-05-04-21:53
 */
public interface BaseCategoryTrademarkService  extends IService<BaseCategoryTrademark> {
    List<BaseTrademark> findTrademarkList(Long category3Id);

    void remove(Long category3Id, Long trademarkId);

    List<BaseTrademark> findCurrentTrademarkList(Long category3Id);

    void save(CategoryTrademarkVo categoryTrademarkVo);
}
