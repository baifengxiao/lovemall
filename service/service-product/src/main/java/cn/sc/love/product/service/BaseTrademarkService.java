package cn.sc.love.product.service;

import cn.sc.love.model.product.BaseTrademark;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author YPT
 * @create 2023-05-02-21:56
 */
public interface BaseTrademarkService extends IService<BaseTrademark> {


    IPage<BaseTrademark> getBaseTrademarkPage(Page<BaseTrademark> baseTrademarkPage);
}
