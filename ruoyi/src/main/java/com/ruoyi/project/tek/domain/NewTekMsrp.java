package com.ruoyi.project.tek.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 销售计划项目-建议零售价表
 * </p>
 *
 * @author steven.guo
 * @since 2020-06-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class NewTekMsrp implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 关联的客户ID
     */
    private Long customerId;

    /***
     *  对应的产品ID
     */
    private Long productId;

    /**
     * 产品sku
     */
    @Excel(name = "SKU")
    private String sku;

    /**
     * 建议零售价
     */
    @Excel(name = "MSRP")
    private BigDecimal msrp;

    /**
     * 币种
     */
    private String currencyType;

    @Excel(name = "Country")
    private String country;

    /**
     * 年份（默认填写时当年）
     */
    private String year;

    /**
     * 有效期开始日期
     * */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @Excel(name = "Valid From")
    private Date validFrom;

    /**
     * 有效期结束日期
     * */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @Excel(name = "Valid To")
    private Date validTo;

    /**
     * 15%折扣的价格
     */
    private BigDecimal msrp_15;

    /**
     * 20%折扣的价格
     */
    private BigDecimal msrp_20;

    /**
     * 25%折扣的价格
     */
    private BigDecimal msrp_25;

    /**
     * 30%折扣的价格
     */
    private BigDecimal msrp_30;

    /**
     * 其他折扣的建议零售价（给用户下拉框选择）
     */
    private BigDecimal msrpOther;

    /**
     * 根据msrpOther计算出来的折扣价格
     * */
    private BigDecimal msrpOtherPrice;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private char delFlag;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 备注
     */
    @Excel(name = "Remark")
    private String remark;

    private Integer selectedWeek;


}
