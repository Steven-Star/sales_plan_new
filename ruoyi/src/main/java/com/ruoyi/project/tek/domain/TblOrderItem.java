package com.ruoyi.project.tek.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 订单详情
 * </p>
 *
 * @author steven.guo
 * @since 2020-05-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TblOrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 站点
     */
    private String site;

    private String amazonOrderId;

    /**
     * 亚马逊定义的订单商品识别号。
     */
    private String orderItemId;

    /**
     * 商品的亚马逊标准识别号 (ASIN)。
     */
    private String asin;

    /**
     * 商品的卖家 SKU。
     */
    private String sellerSku;

    /**
     * 商品名称
     */
    private String title;

    /**
     * 下面两个子元素的父元素
     */
    private Integer quantityOrdered;

    /**
     * 已配送的商品数量
     */
    private Integer quantityShipped;

    /**
     * 商品的售价
     */
    private String itemPriceAmount;

    /**
     * 运费
     */
    private String shippingPrice;

    /**
     * 商品的礼品包装金额
     */
    private String giftWrapPrice;

    /**
     * 商品的价格的税费
     */
    private String itemTaxAmount;

    /**
     * 运费的税费
     */
    private String shippingTax;

    /**
     * 礼品包装金额的税费
     */
    private String giftWrapTax;

    /**
     * 运费的折扣
     */
    private String shippingDiscount;

    /**
     * 报价中的全部促销折扣总计。
     */
    private String promotionDiscountAmount;

    private String promotionDiscountTaxAmount;

    /**
     * USD ItemPrice 的价值等于商品售价乘以订购数量
     */
    private String itemPriceCurrencyCode;

    /**
     * PromotionId 元素列表
     */
    private String promotionIdsPromotionId;

    /**
     * COD 服务费用。
     */
    private String codFee;

    /**
     * 货到付款费用的折扣。
     */
    private String codFeeDiscount;

    /**
     * 买家提供的礼品消息
     */
    private String giftMessageText;

    /**
     * 买家指定的礼品包装等级
     */
    private String giftWrapLevel;

    /**
     * 发票信息（仅适用于中国
     */
    private String invoiceData;

    /**
     * 卖家描述的商品状况
     */
    private String conditionNote;

    /**
     * 商品的状况。
     */
    private String conditionId;

    /**
     * 商品的子状况
     */
    private String conditionSubtypeId;

    /**
     * 订单预约送货上门的开始日期（目的地时区）。日期格式为 ISO 8601。
     */
    private String scheduledDeliveryStartDate;

    /**
     * 订单预约送货上门的终止日期（目的地时区）。日期格式为 ISO 8601。
     */
    private String scheduledDeliveryEndDate;

    private Integer productInfoNumberOfItems;

    private String createBy;

    private LocalDateTime createDate;

    private String updateBy;

    private LocalDateTime updateDate;

    private String year;

    private String month;

    private String day;

    /**
     * 店铺编号
     */
    private String shopNo;

    /**
     * 订单对应的800号
     */
    private String deliveryNo;

    /**
     * 调整过后的新sku（针对特殊订单）
     */
    private String skuNew;

    /**
     * 调整过后的新订单数量（针对特殊订单）
     */
    private Integer numberNew;


}
