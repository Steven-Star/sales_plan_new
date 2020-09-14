package com.ruoyi.project.tek.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * FBM待收货
 * </p>
 *
 * @author steven.guo
 * @since 2020-05-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TekFbaWaitReceiving implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 数据序号
     */
    private String serialNo;

    /**
     * 托书号来源(发货时候有)
     */
    private Long attorneyDetailId;

    /**
     * 订舱号
     */
    private String vesselBookNo;

    /**
     * 销售单
     */
    private String orderId;

    /**
     * 商品sku
     */
    private String goodsSku;

    /**
     * 数量
     */
    private BigDecimal counts;

    /**
     * 库位-国家
     */
    private String country;

    /**
     * 库位-服务商
     */
    private Integer serverProvider;

    /**
     * 类型 （1.发货 2.退货）
     */
    private Integer type;

    /**
     * 预计到货
     */
    private LocalDateTime expectedArrivalDt;

    /**
     * 船期
     */
    private LocalDateTime shipDt;

    /**
     * 预计到港时间
     */
    private LocalDateTime expectedArrivalN1;

    /**
     * 预计到仓时间
     */
    private LocalDateTime expectedArrivalN2;

    /**
     * 收货时间
     */
    private LocalDateTime receivingDt;

    /**
     * 确认收货(0或null未收货 1.正常收货 2.异常收货)
     */
    private Integer confirmReceipt;

    /**
     * 实际收货数量
     */
    private BigDecimal factCounts;

    /**
     * 备注
     */
    private String remark;

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
     * matnr_id
     */
    private Long matnrId;

    /**
     * 运单号
     */
    private String freightBillNo;


}
