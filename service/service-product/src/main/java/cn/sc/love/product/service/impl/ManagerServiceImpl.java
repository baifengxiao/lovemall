package cn.sc.love.product.service.impl;

import cn.sc.love.model.product.BaseCategory1;
import cn.sc.love.product.mapper.BaseCategory1Mapper;
import cn.sc.love.product.service.ManagerService;
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

    @Override
    public List<BaseCategory1> getCategory1() {

        List<BaseCategory1> category1List = baseCategory1Mapper.selectList(null);

        return category1List;
    }
}
