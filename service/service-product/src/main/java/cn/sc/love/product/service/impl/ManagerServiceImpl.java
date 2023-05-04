package cn.sc.love.product.service.impl;


import cn.sc.love.model.product.*;
import cn.sc.love.product.mapper.*;
import cn.sc.love.product.service.ManagerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
    @Autowired
    private BaseAttrInfoMapper baseAttrInfoMapper;
    @Autowired
    private BaseAttrValueMapper baseAttrValueMapper;

    @Autowired
    private SpuInfoMapper spuInfoMapper;


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

    @Override
    public List<BaseAttrInfo> attrInfoList(Long category1Id, Long category2Id, Long category3Id) {

        //TODO 可以优化
        return baseAttrInfoMapper.selectAttrInfoList(category1Id, category2Id, category3Id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveAttrInfo(BaseAttrInfo baseAttrInfo) {
//
//
//        //1判断是保存还是修改
//        if (baseAttrInfo.getId() != null) {
//
//            //修改    先插入baseAttrInfo基本属性，再插入平台属性
//            baseAttrInfoMapper.updateById(baseAttrInfo);
//
//            //不采用修改，采用“先删除，重新添加”，因为修改操作复杂
//            //先删除
//            QueryWrapper<BaseAttrValue> wrapper = new QueryWrapper<>();
//            wrapper.eq("attr_id", baseAttrInfo.getId());
//            baseAttrValueMapper.delete(wrapper);
//
//            //后添加
//            List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();
//            if (!CollectionUtils.isEmpty(attrValueList)) {
//
//                for (BaseAttrValue baseAttrValue : attrValueList) {
//                    baseAttrValue.setAttrId(baseAttrInfo.getId());
//                    baseAttrValueMapper.insert(baseAttrValue);
//                }
//
//            }
//        } else {
//
//
//            //新增，直接添加
//            baseAttrInfoMapper.insert(baseAttrInfo);
//
//            List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();
//            if (!CollectionUtils.isEmpty(attrValueList)) {
//
//                for (BaseAttrValue baseAttrValue : attrValueList) {
//                    baseAttrValue.setAttrId(baseAttrInfo.getId());
//                    baseAttrValueMapper.insert(baseAttrValue);
//                }
//
//
//            }
//
//        }
//
//
//
//
//
//


        //1判断是保存还是修改
        if (baseAttrInfo.getId() != null) {

            //修改    先插入baseAttrInfo基本属性，再插入平台属性
            baseAttrInfoMapper.updateById(baseAttrInfo);

            //不采用修改，采用“先删除，重新添加”，因为修改操作复杂
            //先删除
            QueryWrapper<BaseAttrValue> wrapper = new QueryWrapper<>();
            wrapper.eq("attr_id", baseAttrInfo.getId());
            baseAttrValueMapper.delete(wrapper);

        } else {

            //新增，直接添加
            baseAttrInfoMapper.insert(baseAttrInfo);
        }

        //都需要添加，写到外面
        List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();
        if (!CollectionUtils.isEmpty(attrValueList)) {

            for (BaseAttrValue baseAttrValue : attrValueList) {
                baseAttrValue.setAttrId(baseAttrInfo.getId());
                baseAttrValueMapper.insert(baseAttrValue);
            }


        }


        //1.3注意加事务


    }

    @Override
    public List<BaseAttrValue> getAttrValueList(Long attrId) {

//        //不严谨，可能出现进入查询，baseattrInfo刚好删除的情况
//        QueryWrapper<BaseAttrValue> wrapper = new QueryWrapper<>();
//        wrapper.eq("attr_id", attrId);
//        List<BaseAttrValue> baseAttrValueList = baseAttrValueMapper.selectList(wrapper);
//        return baseAttrValueList;


        //1,先找到实体，
        BaseAttrInfo baseAttrInfo = baseAttrInfoMapper.selectById(attrId);
        Long id = baseAttrInfo.getId();

        //2，再用实体的id查数据（实体被删了就没有id，就不会查）
        QueryWrapper<BaseAttrValue> wrapper = new QueryWrapper<>();
        wrapper.eq("attr_id", id);
        List<BaseAttrValue> baseAttrValueList = baseAttrValueMapper.selectList(wrapper);


        return baseAttrValueList;
    }

    @Override
    public IPage<SpuInfo> getSpuInfoPage(SpuInfo spuInfo,Page<SpuInfo> infoPage ) {

        QueryWrapper<SpuInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("category3_id", spuInfo.getCategory3Id());
        return spuInfoMapper.selectPage(infoPage, wrapper );
    }


}
