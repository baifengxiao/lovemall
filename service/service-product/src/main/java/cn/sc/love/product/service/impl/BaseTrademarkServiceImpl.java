package cn.sc.love.product.service.impl;

import cn.sc.love.model.product.BaseTrademark;
import cn.sc.love.product.mapper.BaseTrademarkMapper;
import cn.sc.love.product.service.BaseTrademarkService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author YPT
 * @create 2023-05-02-22:23
 */
@Service
public class BaseTrademarkServiceImpl extends ServiceImpl<BaseTrademarkMapper,BaseTrademark> implements BaseTrademarkService {

//    @Autowired
//    private BaseTrademarkMapper baseTrademarkMapper;


    @Override
    public IPage<BaseTrademark> getBaseTrademarkPage(Page<BaseTrademark> baseTrademarkPage) {
        QueryWrapper<BaseTrademark> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("id");
        Page<BaseTrademark> trademarkPage = baseMapper.selectPage(baseTrademarkPage, wrapper);
        return trademarkPage;
    }
}
