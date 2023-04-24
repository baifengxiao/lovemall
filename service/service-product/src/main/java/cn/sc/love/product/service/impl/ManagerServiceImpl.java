package cn.sc.love.product.service.impl;

import cn.sc.love.model.product.BaseCategory1;
import cn.sc.love.model.product.BaseCategory2;
import cn.sc.love.model.product.BaseCategory3;
import cn.sc.love.product.mapper.BaseCategory1Mapper;
import cn.sc.love.product.mapper.BaseCategory2Mapper;
import cn.sc.love.product.mapper.BaseCategory3Mapper;
import cn.sc.love.product.service.ManagerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author YPT
 * @create 2023-04-24-15:24
 */
@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private BaseCategory1Mapper baseCategory1Mapper;

    @Autowired
    private BaseCategory2Mapper baseCategory2Mapper;
    @Autowired
    private BaseCategory3Mapper baseCategory3Mapper;

    @Override
    public List<BaseCategory1> getCategory1() {

        List<BaseCategory1> category1List = baseCategory1Mapper.selectList(null);

        return category1List;
    }

    @Override
    public List<BaseCategory2> getCategory2(Long category1Id) {

        QueryWrapper<BaseCategory2> wrapper = new QueryWrapper<>();
        QueryWrapper<BaseCategory2> queryWrapper = wrapper.eq("category1_id", category1Id);
        List<BaseCategory2> category2List = baseCategory2Mapper.selectList(queryWrapper);

        return category2List;
    }

    @Override
    public List<BaseCategory3> getCategory3(Long category2Id) {
        QueryWrapper<BaseCategory3> wrapper = new QueryWrapper<>();
        QueryWrapper<BaseCategory3> queryWrapper = wrapper.eq("category2_id", category2Id);
        List<BaseCategory3> category3List = baseCategory3Mapper.selectList(queryWrapper);

        return category3List;
    }
}
