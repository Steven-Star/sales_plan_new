package com.ruoyi.project.tek.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 创建订单记录
 * </p>
 *
 * @author steven.guo
 * @since 2020-05-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TekPurchaseCreateRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 跨境平台单号
     */
    private String zbstkd;

    /**
     * 跨境平台项目号 
     */
    private Integer zbstno;

    /**
     * 业务类型
     */
    private String zmodel;

    /**
     * 退货标识:X
     */
    private String zreject;

    /**
     * 业务单据编号
     */
    private String zbreln;

    /**
     * 销售工厂 
     */
    private String zwerks1;

    /**
     * 客户编号
     */
    private String kunnr;

    /**
     * 物料号 
     */
    private String matnr;

    /**
     * 销售凭证类型 
     */
    private String auart;

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
     * 运达方的PO日期
     */
    private LocalDateTime bstdkE;

    /**
     * 客户采购订单编号
     */
    private String bstkd;

    /**
     * 请求交货日期
     */
    private LocalDateTime vdatu;

    /**
     * 客户组 1
     */
    private String kvgr1;

    /**
     * 装运类型
     */
    private String vsart;

    /**
     * 销售凭证项目类别 
     */
    private String pstyv;

    /**
     * SD 凭证货币
     */
    private String waerk;

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
     * 消息类型: S 成功,E 错误,W 警告,I 信息,A 中断
     */
    private String type;

    /**
     * 消息文本
     */
    private String message;

    /**
     * 业务步骤
     */
    private Integer zstep;

    /**
     * 创建者 
     */
    private String aenam;

    /**
     * 传入时间
     */
    private LocalDateTime aedat;

    /**
     * 收货库位
     */
    private String zlgortS1;

    /**
     * 备注
     */
    private String ztdline;

    /**
     * 订单类型 0.采购单 1.销售单 2.退货单 3.换货单
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
