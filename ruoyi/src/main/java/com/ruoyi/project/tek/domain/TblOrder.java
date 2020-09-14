package com.ruoyi.project.tek.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author steven.guo
 * @since 2020-05-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TblOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 站点
     */
    private String site;

    /**
     * 	亚马逊所定义的订单编码
     */
    private String amazonOrderId;

    /**
     * 卖家所定义的订单编码
     */
    private String sellerOrderId;

    /**
     * 创建订单的日期
     */
    private LocalDateTime purchaseDate;

    /**
     * 订单的最后更新日期。
     */
    private LocalDateTime lastUpdateDate;

    /**
     * 当前的订单状态
     */
    private String orderStatus;

    /**
     * 订单配送方式：亚马逊配送 (AFN) 或卖家自行配送 (MFN)
     */
    private String fulfillmentChannel;

    /**
     * 订单中第一件商品的销售渠道
     */
    private String salesChannel;

    /**
     * 订单中第一件商品的订单渠道。
     */
    private String orderChannel;

    /**
     * 货件服务水平
     */
    private String shipServiceLevel;

    /**
     * 订单的配送地址。
     */
    private String shippingAddress;

    /**
     * 订单的总费用
     */
    private BigDecimal orderTotal;

    /**
     * 货币单位
     */
    private String currencyCode;

    /**
     * 已配送的商品数量。
     */
    private Integer numberOfItemsShipped;

    /**
     * 未配送的商品数量
     */
    private Integer numberOfItemsUnshipped;

    /**
     * 货到付款 (COD) 订单的次级付款方式的相关信息
     */
    private String paymentExecutionDetail;

    /**
     * 订单的主要付款方式
     */
    private String paymentMethod;

    /**
     * 订单生成所在商城的匿名编码
     */
    private String marketplaceId;

    /**
     * 买家的匿名电子邮件地址
     */
    private String buyerEmail;

    /**
     * 买家姓名
     */
    private String buyerName;

    /**
     * 订单的配送服务级别分类：Expedited
            FreeEconomy
            NextDay
            SameDay
            SecondDay
            Scheduled
            Standard
     */
    private String shipmentServiceLevelCategory;

    /**
     * 指明订单配送方是否是亚马逊配送 (Amazon TFM) 服务。
     */
    private String shippedByAmazonTfm;

    /**
     * 亚马逊 TFM订单的状态
     */
    private String tfmShipmenTstatus;

    /**
     * 卖家自定义的配送方式
     */
    private String cbaDisplayableShippingLabel;

    /**
     * 订单类型
     */
    private String orderType;

    /**
     * 	您承诺的订单发货时间范围的第一天
     */
    private LocalDateTime earliestShipDate;

    /**
     * 您承诺的订单发货时间范围的最后一天
     */
    private LocalDateTime latestShipDate;

    /**
     * 	您承诺的订单送达时间范围的第一天
     */
    private LocalDateTime earliestDeliveryDate;

    /**
     * 您承诺的订单送达时间范围的最后一天
     */
    private LocalDateTime latestDeliveryDate;

    /**
     * 城市
     */
    private String city;

    /**
     * 区
     */
    private String stateOrRegion;

    /**
     * 国家
     */
    private String countryCode;

    /**
     * 邮编
     */
    private String postalCode;

    private Integer isBusinessOrder;

    private Integer isPrime;

    private Integer isPremiumOrder;

    private Integer isReplacementOrder;

    private String year;

    private String month;

    private String day;

    private String hour;

    /**
     * 店铺编号
     */
    private String shopNo;


}
