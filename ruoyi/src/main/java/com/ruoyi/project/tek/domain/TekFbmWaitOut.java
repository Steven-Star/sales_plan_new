package com.ruoyi.project.tek.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * FBM待出货
 * </p>
 *
 * @author steven.guo
 * @since 2020-05-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TekFbmWaitOut implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 数据序号
     */
    private String serialNo;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品sku
     */
    private String goodsSku;

    /**
     * 库位-国家
     */
    private String country;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 购买时间
     */
    private LocalDateTime purchasingDt;

    /**
     * 最后更新时间
     */
    private LocalDateTime lastUpdateDt;

    /**
     * 配送地址
     */
    private String deliveryAddress;

    /**
     * 数量
     */
    private BigDecimal counts;

    /**
     * 配送出货时间
     */
    private LocalDateTime outDt;

    /**
     * 运单号
     */
    private String waybillNo;

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

    /**
     * FBM待出货类型
     */
    private String fbmOutType;


}
