package com.ruoyi.project.tek.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 采购订单
 * </p>
 *
 * @author steven.guo
 * @since 2020-05-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TekPurchaseOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 计划id
     */
    private Long planningId;

    /**
     * 公司代码
     */
    private String bukrs;

    /**
     * 供应商帐户号
     */
    private String lifnr;

    /**
     * 采购凭证类型
     */
    private String bsart;

    /**
     * 采购凭证号
     */
    private String ebeln;

    /**
     * 采购凭证项目更改日期
     */
    private LocalDateTime aedat;

    /**
     * 采购组织
     */
    private String ekorg;

    /**
     * 汇总号
     */
    private String submi;

    /**
     * 采购凭证的项目编号
     */
    private Integer ebelp;

    /**
     * 采购凭证中的删除标识
     */
    private String loekz;

    /**
     * 物料号
     */
    private String matnr;

    /**
     * 工厂
     */
    private String werks;

    /**
     * 库存地点
     */
    private String lgort;

    /**
     * 需求跟踪号
     */
    private String bednr;

    /**
     * 物料组 
     */
    private String matkl;

    /**
     * 采购订单数量
     */
    private BigDecimal menge;

    /**
     * 交货已完成标识
     */
    private String elikz;

    /**
     * 拒绝标识
     */
    private String abskz;

    /**
     * 退货项目 
     */
    private String retpo;

    /**
     * 短文本
     */
    private String txz01;

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
