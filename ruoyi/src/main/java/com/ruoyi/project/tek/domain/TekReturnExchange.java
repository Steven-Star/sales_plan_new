package com.ruoyi.project.tek.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 退换货表
 * </p>
 *
 * @author steven.guo
 * @since 2020-05-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TekReturnExchange implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 商品sku
     */
    private String sku;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 内部id
     */
    private String insideId;

    /**
     * 关联订单
     */
    private String relationOrder;

    /**
     * 补发时间
     */
    private LocalDateTime replaceTime;

    /**
     * 订单状态
     */
    private Integer orderStatus;

    /**
     * 配送地址
     */
    private String deliveryAddress;

    /**
     * 关联记录跨境平台单号
     */
    private String relationNo;

    /**
     * 数量
     */
    private BigDecimal counts;

    /**
     * 退换原因
     */
    private String reasons;

    /**
     * 运单号
     */
    private String freightBillNo;

    /**
     * 附件
     */
    private String enclosure;

    /**
     * 关联fbm类型 0.待出库 1.待收货
     */
    private Integer releationFbmType;

    /**
     * 关联fbm待出库或待收货表
     */
    private Long releationFbmOrder;

    /**
     * 删除标志[默认false]
     */
    private Boolean delFlag;

    /**
     * 创建id
     */
    private String createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createDt;

    /**
     * 更新id
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateDt;


}
