package cn.sc.love.product.service;

import cn.sc.love.model.product.*;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author YPT
 * @create 2023-04-24-15:25
 */
//后台管理service
public interface ManagerService {
    /**
     * 各级分类单表查询
     * @return
     */
    List<BaseCategory1> getCategory1();
    List<BaseCategory2> getCategory2(Long category1Id);
    List<BaseCategory3> getCategory3(Long category2Id);

    /**
     * 根据三级分类查询平台属性
     * @param category1Id
     * @param category2Id
     * @param category3Id
     * @return
     */
    List<BaseAttrInfo> getAttrInfoList(Long category1Id, Long category2Id, Long category3Id);

    void saveAttrInfo(BaseAttrInfo baseAttrInfo);

    List<BaseAttrValue> getAttrValueList(Long attrId);


    IPage<SpuInfo> getSpuInfoPage(SpuInfo spuInfo, Page<SpuInfo> infoPage);

    List<BaseSaleAttr> baseSaleAttrList();

    void saveSpuInfo(SpuInfo spuInfo);

    List<SpuSaleAttr> spuSaleAttrList(Long spuId);

    List<SpuImage> spuImageList(Long spuId);

    void saveSkuInfo(SkuInfo skuInfo);

    IPage<SkuInfo> skuListPage(Page<SkuInfo> skuInfoPage);

    void onSale(Long skuId);

    void cancelSale(Long skuId);

    SkuInfo getSkuInfo(Long skuId);

    BaseCategoryView getCategoryView(Long category3Id);

    BigDecimal getSkuPrice(Long skuId);

    List<SpuSaleAttr> getSpuSaleAttrListCheckBySku(Long skuId, Long spuId);

    Map getSkuValueIdsMap(Long spuId);

    List<SpuPoster> findSpuPosterBySpuId(Long spuId);

    List<BaseAttrInfo> getAttrList(Long skuId);

    List<JSONObject> getBaseCategoryList();
}
