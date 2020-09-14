package com.ruoyi.project.tek.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 公司间销售订单
 * </p>
 *
 * @author steven.guo
 * @since 2020-05-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TekCompanySaleOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 销售凭证类型 
     */
    private String auart;

    /**
     * 销售凭证 
     */
    private String vbeln;

    /**
     * 销售组织 
     */
    private String vkorg;

    /**
     * 分销渠道
     */
    private String vtweg;

    /**
     * 产品组 
     */
    private String spart;

    /**
     * 请求交货日期
     */
    private LocalDateTime vdatu;

    /**
     * 客户采购订单编号
     */
    private String bstnk;

    /**
     * 售达方 
     */
    private String kunnr;

    /**
     * 付款条件码
     */
    private String zterm;

    /**
     * 客户组
     */
    private String kvgr1;

    /**
     * 运达方的PO日期
     */
    private LocalDateTime bstdkE;

    /**
     * 装运类型
     */
    private String vsart;

    /**
     * 记录的创建日期 
     */
    private LocalDateTime erdat;

    /**
     * 创建对象的人员名称 
     */
    private String ernam;

    /**
     * 销售凭证项目 
     */
    private Integer posnr;

    /**
     * 销售凭证项目类别 
     */
    private String pstyv;

    /**
     * 物料号 
     */
    private String matnr;

    /**
     * 工厂(自有或外部)
     */
    private String werks;

    /**
     * 库存地点
     */
    private String lgort;

    /**
     * 订单数量
     */
    private BigDecimal kwmeng;

    /**
     * 销售单位 
     */
    private String vrkme;

    /**
     * 报价和销售订单的拒绝原因
     */
    private String abgru;

    /**
     * 销售订单项目短文本
     */
    private String arktx;

    /**
     * 订单类型 0.公司间销售单 1.退货单 2.线上订单
     */
    private Integer type;

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
