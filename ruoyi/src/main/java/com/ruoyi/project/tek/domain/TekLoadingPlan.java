package com.ruoyi.project.tek.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * loading plan
 * </p>
 *
 * @author steven.guo
 * @since 2020-05-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TekLoadingPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 送达方 
     */
    private String kunnr;

    /**
     * 交货
     */
    private String vbeln;

    /**
     * 交货项目
     */
    private Integer posnr;

    /**
     * 参考单据的单据编号
     */
    private String vgbel;

    /**
     * 参考项目的项目号 （明确航项目号是否是800号）
     */
    private Integer vgpos;

    /**
     * 交货单数量
     */
    private BigDecimal lfimg;

    /**
     * LOADING合并后项目
     */
    private Integer zposnr;

    /**
     * 合并系列型号
     */
    private String zxlxh;

    /**
     * 合并客户型号
     */
    private String zkhxh;

    /**
     * 贸易方式
     */
    private String zmyfs;

    /**
     * 序号
     */
    private String zxh;

    /**
     * 商检号 
     */
    private String zsjh;

    /**
     * 报关单号
     */
    private String zbgdh;

    /**
     * 报关金额
     */
    private BigDecimal zbgje;

    /**
     * TEK代理
     */
    private String ztekdl;

    /**
     * 发票号码
     */
    private String zfphm;

    /**
     * 商标
     */
    private String normt;

    /**
     * 数量
     */
    private BigDecimal zsl;

    /**
     * CNTS
     */
    private String zctns;

    /**
     * LCL
     */
    private String zlcl;

    /**
     * AIR
     */
    private String zair;

    /**
     * 45’
     */
    private String z45;

    /**
     * 40’H
     */
    private String z40h;

    /**
     * 40’
     */
    private String z40;

    /**
     * 20’
     */
    private String z20;

    /**
     * 目的港 
     */
    private String zmdg;

    /**
     * 船期
     */
    private LocalDateTime zcq;

    /**
     * 验货期 
     */
    private String zyhq;

    /**
     * 实际装期
     */
    private LocalDateTime zsjzq;

    /**
     * 提单号 
     */
    private String ztdh;

    /**
     * 羊山港
     */
    private String zysg;

    /**
     * 航名航次
     */
    private String zhmhc;

    /**
     * 备注1
     */
    private String zbz1;

    /**
     * 备注2
     */
    private String zbz2;

    /**
     * 运费
     */
    private String zyf;

    /**
     * 支付日期
     */
    private LocalDateTime zzfrq;

    /**
     * 功率
     */
    private String zgl;

    /**
     * 运输公司
     */
    private String zysgs;

    /**
     * 业务员 
     */
    private String zywy;

    /**
     * 货代
     */
    private String zhdai;

    /**
     * SD 凭证货币
     */
    private String zhb;

    /**
     * 工厂
     */
    private String werks;

    /**
     * 物料号 
     */
    private String matnr;

    /**
     * 物料描述（短文本） 
     */
    private String maktx;

    /**
     * 净价 
     */
    private BigDecimal mddqgje;

    /**
     * 货币码 
     */
    private String mddqgbb;

    /**
     * 自制/外购
     */
    private String zzzwg;

    /**
     * 创建者 -tek
     */
    private String aenam;

    /**
     * 创建时间-tek
     */
    private LocalDateTime aedat;

    /**
     * 更新时间-tek
     */
    private LocalDateTime zcsrq;

    /**
     * 更新用户-tek
     */
    private String zuname;

    /**
     * 附件
     */
    private String enclosure;

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
