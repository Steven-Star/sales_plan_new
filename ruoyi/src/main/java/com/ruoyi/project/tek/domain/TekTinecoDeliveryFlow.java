package com.ruoyi.project.tek.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 800库存记录
 * </p>
 *
 * @author steven.guo
 * @since 2020-05-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TekTinecoDeliveryFlow implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * stock关联的800库存id
     */
    private Long tinecoDeliveryId;

    /**
     * stoc流水的id
     */
    private Long stockFlowId;

    /**
     * 800号(交货单)
     */
    private String vbeln;

    /**
     * 数量（关联到800中的数目，正数增加，负数减少）
     */
    private BigDecimal counts;

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
