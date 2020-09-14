package com.ruoyi.project.tek.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 销售计划
 * </p>
 *
 * @author steven.guo
 * @since 2020-05-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TekSalesPlanning implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 商品型号
     */
    private String goodsMarque;

    /**
     * SAPID
     */
    private String sapId;

    /**
     * 站点code
     */
    private String siteCode;

    /**
     * 预计时间
     */
    private LocalDateTime estimateTime;

    /**
     * 预计销售数量
     */
    private BigDecimal expectedSalesQuantity;

    /**
     * 创建时间
     */
    private LocalDateTime creationTime;

    /**
     * 要求出运时间
     */
    private LocalDateTime requestShippingTime;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 状态：1新创建、2已创建采购(成功)、3已取消、  4已创建采购(失败)
     */
    private Integer planningStatus;

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
