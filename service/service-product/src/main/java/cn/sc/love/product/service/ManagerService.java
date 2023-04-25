package cn.sc.love.product.service;

import cn.sc.love.model.product.BaseAttrInfo;
import cn.sc.love.model.product.BaseCategory1;
import cn.sc.love.model.product.BaseCategory2;
import cn.sc.love.model.product.BaseCategory3;

import java.util.List;

/**
 * @author YPT
 * @create 2023-04-24-15:25
 */
public interface ManagerService {
    List<BaseCategory1> getCategory1();

    List<BaseCategory2> getCategory2(Long category1Id);

    List<BaseCategory3> getCategory3(Long category2Id);

    List<BaseAttrInfo> attrInfoList(Long category1Id, Long category2Id, Long category3Id);

    void saveAttrInfo(BaseAttrInfo baseAttrInfo);
}
