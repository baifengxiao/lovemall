package cn.sc.love.model.list;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.List;

// Index = goods , Type = info  es 7.8.0 逐渐淡化type！  修改！
@Data
//一，解释Document
//集群下shard（分片）才有效（把数据分成几个部分存储），
//replicas，备份

@Document(indexName = "goods", shards = 3, replicas = 2)

/**
 *包含：sku数据  根据id查询skuINfo
 *  三级分类信息
 * 品牌信息
 *平台属性信息 根据skuid查询平台属性和平台属性值
 *
 *
 */
public class Goods {


    //二，解释注解，@Id,id，@Field，文档中的字段
    // 商品Id skuId
    @Id
    private Long id;

    /**
     * 三，讲解Field使用
     * 1,type:字符串内容的类型
     * 1.1,keyword:不分词  我是一个好人
     * 1.2，Text：分词    我  是  一个  好人
     * 2,analyzer:指定分词时使用的分词器
     * 3,searchAnalyzer:搜索时的分词器
     *
     *
     *
     */

    @Field(type = FieldType.Keyword, index = false)
    private String defaultImg;

    //  es 中能分词的字段，这个字段数据类型必须是 text！keyword 不分词！
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title;

    @Field(type = FieldType.Double)
    private Double price;

    //  @Field(type = FieldType.Date)   6.8.1
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime; // 新品

    @Field(type = FieldType.Long)
    private Long tmId;

    @Field(type = FieldType.Keyword)
    private String tmName;

    @Field(type = FieldType.Keyword)
    private String tmLogoUrl;

    @Field(type = FieldType.Long)
    private Long category1Id;

    @Field(type = FieldType.Keyword)
    private String category1Name;

    @Field(type = FieldType.Long)
    private Long category2Id;

    @Field(type = FieldType.Keyword)
    private String category2Name;

    @Field(type = FieldType.Long)
    private Long category3Id;

    @Field(type = FieldType.Keyword)
    private String category3Name;

    //  商品的热度！ 我们将商品被用户点查看的次数越多，则说明热度就越高！
    @Field(type = FieldType.Long)
    private Long hotScore = 0L;

    // 平台属性集合对象
    // Nested 支持嵌套查询
    @Field(type = FieldType.Nested)
    private List<SearchAttr> attrs;

}
