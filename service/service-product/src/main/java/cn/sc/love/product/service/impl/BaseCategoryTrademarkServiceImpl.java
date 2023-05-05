package cn.sc.love.product.service.impl;

import cn.sc.love.model.product.BaseCategoryTrademark;
import cn.sc.love.model.product.BaseTrademark;
import cn.sc.love.model.product.CategoryTrademarkVo;
import cn.sc.love.product.mapper.BaseCategoryTrademarkMapper;
import cn.sc.love.product.mapper.BaseTrademarkMapper;
import cn.sc.love.product.service.BaseCategoryTrademarkService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author YPT
 * @create 2023-05-04-21:54
 */
@Service
public class BaseCategoryTrademarkServiceImpl extends ServiceImpl<BaseCategoryTrademarkMapper, BaseCategoryTrademark> implements BaseCategoryTrademarkService {

    @Autowired
    private BaseCategoryTrademarkMapper baseCategoryTrademarkMapper;

    @Autowired
    private BaseTrademarkMapper baseTrademarkMapper;

    @Autowired
    private BaseCategoryTrademarkService baseCategoryTrademarkService;

    @Override
    public List<BaseTrademark> findTrademarkList(Long category3Id) {
        List<BaseTrademark> baseTrademarkList = new ArrayList<>();

        QueryWrapper<BaseCategoryTrademark> wrapper = new QueryWrapper<>();
        wrapper.eq("category3_id", category3Id);
        //获取该分类下分类-品牌集合
        List<BaseCategoryTrademark> baseCategoryTrademarkList = baseCategoryTrademarkMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(baseCategoryTrademarkList)) {
            //根据分类品牌中的品牌id，遍历查询品牌集合
            baseTrademarkList = baseCategoryTrademarkList.stream().map(baseCategoryTrademark -> baseTrademarkMapper.selectById(baseCategoryTrademark.getTrademarkId())).collect(Collectors.toList());

        }

        return baseTrademarkList;
    }

    @Override
    public void remove(Long category3Id, Long trademarkId) {


        QueryWrapper<BaseCategoryTrademark> wrapper = new QueryWrapper<>();
        wrapper.eq("category3_id", category3Id).eq("trademark_id", trademarkId);


        baseCategoryTrademarkMapper.delete(wrapper);


    }

    @Override
    public List<BaseTrademark> findCurrentTrademarkList(Long category3Id) {

        //已关联的品牌
        QueryWrapper<BaseCategoryTrademark> wrapper = new QueryWrapper<>();
        wrapper.eq("category3_id", category3Id);
        List<BaseCategoryTrademark> categoryTrademarkList = baseCategoryTrademarkMapper.selectList(wrapper);

        if (!CollectionUtils.isEmpty(categoryTrademarkList)) {

            //已被关联品牌id集合
            List<Long> tradMarkList = categoryTrademarkList.stream().map(a -> {
                return a.getTrademarkId();
            }).collect(Collectors.toList());

            //未被关联的品牌集合
            QueryWrapper<BaseTrademark> queryWrapper = new QueryWrapper<>();
            queryWrapper.notIn("id", tradMarkList);
            return baseTrademarkMapper.selectList(queryWrapper);
        }

        //没有已关联的品牌，就查出所有品牌
        return baseTrademarkMapper.selectList(null);
    }

    /**
     * 向某个分类添加品牌集合
     *
     * @param categoryTrademarkVo
     */
    @Override
    public void save(CategoryTrademarkVo categoryTrademarkVo) {

        Long category3Id = categoryTrademarkVo.getCategory3Id();
        List<BaseCategoryTrademark> categoryTrademarkList = categoryTrademarkVo.getTrademarkIdList().stream().map(tradmarkid -> {

            //添加分类id
            BaseCategoryTrademark baseCategoryTrademark = new BaseCategoryTrademark();
            baseCategoryTrademark.setCategory3Id(category3Id);

            //添加分类品牌
            baseCategoryTrademark.setTrademarkId(tradmarkid);
            return baseCategoryTrademark;

        }).collect(Collectors.toList());
        baseCategoryTrademarkService.saveBatch(categoryTrademarkList);


    }


}
