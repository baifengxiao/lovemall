package cn.sc.love.product.mapper;

import cn.sc.love.model.product.BaseAttrInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author YPT
 * @create 2023-04-25-6:34
 */
@Mapper
public interface BaseAttrInfoMapper extends BaseMapper<BaseAttrInfo> {

    /**
     * 根据分类id查询平台属性集合
     *
     * @param category1Id
     * @param category2Id
     * @param category3Id
     */
    List<BaseAttrInfo> selectAttrInfoList(Long category1Id, Long category2Id, Long category3Id);


}
