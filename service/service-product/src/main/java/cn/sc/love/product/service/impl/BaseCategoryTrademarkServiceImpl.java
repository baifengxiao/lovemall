package cn.sc.love.product.service.impl;

import cn.sc.love.model.product.BaseCategoryTrademark;
import cn.sc.love.model.product.BaseTrademark;
import cn.sc.love.product.mapper.BaseCategoryTrademarkMapper;
import cn.sc.love.product.mapper.BaseTrademarkMapper;
import cn.sc.love.product.service.BaseCategoryTrademarkService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
public class BaseCategoryTrademarkServiceImpl implements BaseCategoryTrademarkService {

    @Autowired
    private BaseCategoryTrademarkMapper baseCategoryTrademarkMapper;

    @Autowired
    private BaseTrademarkMapper baseTrademarkMapper;

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

        List<BaseTrademark> trademarks = new ArrayList<>();

        //所有品牌
        List<BaseTrademark> allTrademarklist = baseTrademarkMapper.selectList(null);
        List<Long> allIdList = allTrademarklist.stream().map(a -> a.getId()).collect(Collectors.toList());

        QueryWrapper<BaseCategoryTrademark> wrapper = new QueryWrapper<>();
        wrapper.eq("category3_id", category3Id);

        //已关联的品牌
        List<BaseCategoryTrademark> categoryTrademarkList = baseCategoryTrademarkMapper.selectList(wrapper);

        if (!CollectionUtils.isEmpty(categoryTrademarkList)) {


            //已被关联品牌id集合
            List<Long> tradMarkList = categoryTrademarkList.stream().filter(list -> {

                return !allIdList.contains(list.getTrademarkId());
            }).map(a -> {
                System.out.println("这句话出不来？"+a.getTrademarkId());
                return a.getTrademarkId();
            }).collect(Collectors.toList());
            System.out.println("------" + tradMarkList);
            QueryWrapper<BaseTrademark> queryWrapper = new QueryWrapper<>();
            queryWrapper.in("id", tradMarkList);
            trademarks = baseTrademarkMapper.selectList(queryWrapper);
        }
        return trademarks;
    }

//    @Override
//    public List<BaseTrademark> findCurrentTrademarkList(Long category3Id) {
//
//        //先获取所有关联了的id集合
//        QueryWrapper<BaseCategoryTrademark> wrapper = new QueryWrapper<>();
//        wrapper.eq("category3_id", category3Id);
//        List<BaseCategoryTrademark> categoryTrademarkList = baseCategoryTrademarkMapper.selectList(wrapper);
//
//        if (!CollectionUtils.isEmpty(categoryTrademarkList)) {
//            List<Long> list = categoryTrademarkList.stream().map(a -> {
//                return a.getTrademarkId();
//            }).collect(Collectors.toList());
//
//            //再过滤
//            List<BaseTrademark> trademarks = baseTrademarkMapper.selectList(null).stream()
//                    .filter(b -> {
//                        return !list.contains(b.getId());
//                    }).collect(Collectors.toList());
//
//            return trademarks;
//
//        }
//        //没有被关联的，返回空
//        return baseTrademarkMapper.selectList(null);
//
//    }
}
