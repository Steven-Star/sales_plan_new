package com.ruoyi.project.tek.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 和stock关联的800仓库货物
 * </p>
 *
 * @author steven.guo
 * @since 2020-05-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TekTinecoDelivery implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 托书号来源
     */
    private Long attorneyDetailId;

    /**
     * 关联stock_detail的id
     */
    private Long stockDetailId;

    /**
     * 800号(交货单)
     */
    private String vbeln;

    /**
     * 是否系统创建
     */
    private Boolean systemCreateFlag;

    /**
     * 状态(1可用)
     */
    private Integer status;

    /**
     * 数量
     */
    private BigDecimal counts;

    /**
     * 入库时间
     */
    private LocalDateTime putInTime;

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
