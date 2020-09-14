package com.ruoyi.project.tek.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 订单物料表
 * </p>
 *
 * @author steven.guo
 * @since 2020-05-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TekOrderMaterial implements Serializable {

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
     * 物料号 
     */
    private String matnr;

    /**
     * 以销售单位表示的累计订单数量
     */
    private BigDecimal kwmeng;

    /**
     * 销售单位 
     */
    private String vrkme;

    /**
     * 净价 
     */
    private BigDecimal netpr;

    /**
     * 票折
     */
    private BigDecimal z002;

    /**
     * 运费
     */
    private BigDecimal z003;

    /**
     * 发货库位
     */
    private String zlgort1;

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
