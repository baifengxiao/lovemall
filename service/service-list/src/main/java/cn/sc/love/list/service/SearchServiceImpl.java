package cn.sc.love.list.service;

import cn.sc.love.list.repository.GoodsRepository;
import cn.sc.love.model.list.Goods;
import cn.sc.love.model.list.SearchAttr;
import cn.sc.love.model.product.BaseAttrInfo;
import cn.sc.love.model.product.BaseCategoryView;
import cn.sc.love.model.product.BaseTrademark;
import cn.sc.love.model.product.SkuInfo;
import cn.sc.love.product.client.ProductFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author yupengtao
 * @Date 2023/6/10 16:35
 **/
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private ProductFeignClient productFeignClient;


    /**
     * 商品上架
     *
     * @param skuId
     */
    @Override
    public void upperGoods(Long skuId) {

        Goods goods = new Goods();
        //设置4信息
//        sku信息
        SkuInfo skuInfo = productFeignClient.getSkuInfo(skuId);
        if (skuInfo != null) {
            goods.setId(skuId);
            goods.setDefaultImg(skuInfo.getSkuDefaultImg());
            goods.setTitle(skuInfo.getSkuName());
            goods.setPrice(productFeignClient.getSkuPrice(skuId).doubleValue());
            goods.setCreateTime(new Date());
            goods.setTmId(skuInfo.getTmId());

            //        品牌信息
            BaseTrademark trademark = productFeignClient.getTrademark(skuId);
            if (trademark != null) {
                goods.setTmId(trademark.getId());
                goods.setTmName(trademark.getTmName());
                goods.setTmLogoUrl(trademark.getLogoUrl());
            }

            //        三级分类信息
            BaseCategoryView categoryView = productFeignClient.getCategoryView(skuId);
            if (categoryView != null) {
                goods.setCategory1Id(categoryView.getCategory1Id());
                goods.setCategory1Name(categoryView.getCategory1Name());
                goods.setCategory2Id(categoryView.getCategory2Id());
                goods.setCategory2Name(categoryView.getCategory2Name());
                goods.setCategory3Id(categoryView.getCategory3Id());
                goods.setCategory3Name(categoryView.getCategory3Name());
            }


        }


//        平台属性信息
        List<BaseAttrInfo> attrList = productFeignClient.getAttrList(skuId);
        if (!CollectionUtils.isEmpty(attrList)) {

            //把List<BaseAttrInfo>-->List<SearchAttr>
            List<SearchAttr> searchAttrList = attrList.stream().map(baseAttrInfo -> {
                SearchAttr searchAttr = new SearchAttr();
                searchAttr.setAttrId(baseAttrInfo.getId());
                searchAttr.setAttrName(baseAttrInfo.getAttrName());
                //TODO
                searchAttr.setAttrValue(baseAttrInfo.getAttrValueList().get(0).getValueName());
                return searchAttr;
            }).collect(Collectors.toList());

            goods.setAttrs(searchAttrList);


        }
//        封装

        goodsRepository.save(goods);
    }

    @Override
    public void lowerGoods(Long skuId) {
        goodsRepository.deleteById(skuId);
    }
}
