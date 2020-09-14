package com.ruoyi.project.tek.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 订单总表
 * </p>
 *
 * @author steven.guo
 * @since 2020-05-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TekOrderTotal implements Serializable {

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
     * 消息类型: S 成功,E 错误,W 警告,I 信息,A 中断
     */
    private String type;

    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 商品销量
     */
    private BigDecimal salesVolume;

    /**
     * 售价
     */
    private BigDecimal price;

    /**
     * 国家code
     */
    private String siteCode;

    /**
     * 退换货原因
     */
    private String reason;

    /**
     * 订单来源 0.线上 1.线下
     */
    private Integer orderSource;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 订单编号_行号
     */
    private String orderNoLine;

    /**
     * 订单类型 0.补发货 1.补差价 2.反补差价 3.退货单 4.换货单 5.正常订单 6.线上退货
     */
    private Integer orderType;

    /**
     * 收货库位
     */
    private String zlgort2;

    /**
     * 备注
     */
    private String ztdline;

    /**
     * 补发时间/购买时间
     */
    private LocalDateTime sendTime;

    /**
     * 配送地址
     */
    private String deliveryAddress;

    /**
     * 关联fbm类型 0.待出库 1.待收货
     */
    private Integer releationFbmType;

    /**
     * 关联fbm待出库或待收货表
     */
    private String releationFbmOrder;

    /**
     * 订单对应的800
     */
    private String stockAjust;

    /**
     * 附件
     */
    private String enclosure;

    /**
     * 运单号
     */
    private String freightBillNo;

    /**
     * 订单状态 0.已收货 1.已发货 2.已补差价
     */
    private Integer status;

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
