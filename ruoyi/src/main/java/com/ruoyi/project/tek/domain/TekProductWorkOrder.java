package com.ruoyi.project.tek.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 生产工单
 * </p>
 *
 * @author steven.guo
 * @since 2020-05-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TekProductWorkOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 生产订单号 
     */
    private String aufnr;

    /**
     * 生产订单项目编号
     */
    private Integer posnr;

    /**
     * 销售订单号
     */
    private String kdauf;

    /**
     * 销售订单行项 
     */
    private Integer kdpos;

    /**
     * 工厂
     */
    private String dwerk;

    /**
     * 订单类型
     */
    private String dauat;

    /**
     * 交货已完成标识
     */
    private String elikz;

    /**
     * 删除标志
     */
    private String xloek;

    /**
     * 订单的物料编号 
     */
    private String matnr;

    /**
     * 订单项数量 
     */
    private BigDecimal psmng;

    /**
     * 此订单项的收货数量 
     */
    private BigDecimal wemng;

    /**
     * 基本计量单位
     */
    private String meins;

    /**
     * 批号
     */
    private String charg;

    /**
     * 基本完成日期
     */
    private LocalDateTime gltrp;

    /**
     * 订单开始日期 
     */
    private LocalDateTime gstrp;

    /**
     * 计划下达日期
     */
    private LocalDateTime ftrms;

    /**
     * 计划完工
     */
    private LocalDateTime gltrs;

    /**
     * 排产开始 
     */
    private LocalDateTime gstrs;

    /**
     * 实际开始日期 
     */
    private LocalDateTime gstri;

    /**
     * 确认订单完成日期
     */
    private LocalDateTime getri;

    /**
     * 实际结束日期
     */
    private LocalDateTime gltri;

    /**
     * 实际下达日期
     */
    private LocalDateTime ftrmi;

    /**
     * 计划下达日期
     */
    private LocalDateTime ftrmp;

    /**
     * 创建日期
     */
    private LocalDateTime erdat;

    /**
     * 更改日期
     */
    private LocalDateTime aedat;

    /**
     * 售达方
     */
    private String kunnr;

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
