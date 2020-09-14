package com.ruoyi.project.tek.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 商品
 * </p>
 *
 * @author steven.guo
 * @since 2020-05-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TblGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 销售平台
     */
    private String salesPlatform;

    /**
     * 站点
     */
    private String site;

    /**
     * 包含商品变体信息
     */
    private String relationships;

    /**
     * 商品销量排行信息。
     */
    private Integer salesRankings;

    /**
     * 商城编码
     */
    private String identifiersMarketplaceAsinMarketplaceId;

    /**
     * SKU
     */
    private String sku;

    /**
     * 商品ASIN
     */
    private String identifiersMarketplaceAsinAsin;

    /**
     * 标题
     */
    private String itemAttributesLabel;

    /**
     * 制造商
     */
    private String itemAttributesManufacturer;

    /**
     * 编号
     */
    private String itemAttributesPartNumber;

    /**
     * 可售数量
     */
    private Long itemAttributesNumberOfitems;

    /**
     * 发布日期
     */
    private LocalDate itemAttributesReleaseDate;

    /**
     * 修改日期
     */
    private LocalDate itemAttributesUpdateDate;

    /**
     * 材料类型
     */
    private String itemAttributesMaterialType;

    /**
     * 产品分类
     */
    private String itemAttributesProductTypeName;

    /**
     * 商品封面图
     */
    private String itemAttributesSmallImageUrl;

    /**
     * 价格
     */
    private BigDecimal itemAttributesListPriceAmount;

    /**
     * 单位  USD
     */
    private String itemAttributesListPriceCurrencyCode;

    /**
     * N79S
     */
    private String itemAttributesModel;

    /**
     * 商品名称
     */
    private String itemAttributesTitle;

    /**
     * 保修说明
     */
    private String itemAttributesWarranty;

    /**
     * 成本
     */
    private BigDecimal costOf;

    /**
     * 差评
     */
    private String badReview;

    /**
     * 好评
     */
    private String goodReview;

    /**
     * 当日事件
     */
    private String dayOfEvent;

    /**
     * 状况
     */
    private String goodsCondition;

    /**
     * 库存 不包括当前在入库 货件中的商品数量，也不包括在亚马逊物流的亚马逊配送中心之间转移的商品数量
     */
    private Integer inStockSupplyQuantity;

    /**
     * 所属分类的id
     */
    private String productCategoryId;

    /**
     * 所属分类的名称
     */
    private String productCategoryName;

    /**
     * 店铺编号
     */
    private String shopNo;

    /**
     * 是不是零件[0:不是零件, 1是零件]
     */
    private Boolean partFlag;

    /**
     * 下架时间
     */
    private LocalDateTime offDate;

    /**
     * 上架时间
     */
    private LocalDateTime onDate;

    /**
     * 简称
     */
    private String briefName;


}
