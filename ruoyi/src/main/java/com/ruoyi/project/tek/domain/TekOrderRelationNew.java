package com.ruoyi.project.tek.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 订单关联表（新）
 * </p>
 *
 * @author steven.guo
 * @since 2020-05-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TekOrderRelationNew implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 订单id 
     */
    private Long orderId;

    /**
     * 订单编码
     */
    private String orderNo;

    /**
     * 订单行编号
     */
    private String orderLineNo;

    /**
     * 运单号
     */
    private String freightBillNo;

    /**
     * 附件
     */
    private String enclosure;

    /**
     * 订单sap状态
     */
    private Integer amazonNormalState;

    /**
     * 订单类型 0.正常 1.退货
     */
    private Integer orderType;

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
