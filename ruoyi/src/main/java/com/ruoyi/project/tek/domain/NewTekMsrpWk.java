package com.ruoyi.project.tek.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 销售计划项目-周建议零售价表-一个sku对应多个客户，多个周的价格
 * </p>
 *
 * @author steven.guo
 * @since 2020-06-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class NewTekMsrpWk implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 关联的客户ID
     */
    private Long customerId;

    /***
     *  对应的建议零售价ID
     */
    private Long msrpId;

    /**
     * 产品sku
     */
    @Excel(name = "SKU")
    private String sku;

    /**
     * 年份（默认填写时当年）
     */
    private String year;

    /**
     * 有效期开始周
     * */
    @Excel(name = "ValidFrom")
    private String validFromWK;

    /**
     * 有效期结束周
     * */
    @Excel(name = "ValidTo")
    private String validToWK;

    /**
     * 币别
     * */
    @Excel(name = "Currency")
    private String currencyType;

    /**
     * 对应国家
     * */
    @Excel(name = "Country")
    private String country;

    @Excel(name = "SellingPrice")
    private BigDecimal sellingPrice;

    private BigDecimal sellingPriceUSD;

    @Excel(name = "CustomerName")
    private String customerName;

    @Excel(name = "PromotionType")
    private String promotionType;

    private List<String> countryNameList;

    private List<String> customerList;

    /**
     * 第1周价格
     */
    private BigDecimal wk1Msrp;

    /**
     * 第2周价格
     */
    private BigDecimal wk2Msrp;

    /**
     * 第3周价格
     */
    private BigDecimal wk3Msrp;

    /**
     * 第4周价格
     */
    private BigDecimal wk4Msrp;

    /**
     * 第5周价格
     */
    private BigDecimal wk5Msrp;

    /**
     * 第6周价格
     */
    private BigDecimal wk6Msrp;

    /**
     * 第7周价格
     */
    private BigDecimal wk7Msrp;

    /**
     * 第8周价格
     */
    private BigDecimal wk8Msrp;

    /**
     * 第9周价格
     */
    private BigDecimal wk9Msrp;

    /**
     * 第10周价格
     */
    private BigDecimal wk10Msrp;

    /**
     * 第11周价格
     */
    private BigDecimal wk11Msrp;

    /**
     * 第12周价格
     */
    private BigDecimal wk12Msrp;

    /**
     * 第13周价格
     */
    private BigDecimal wk13Msrp;

    /**
     * 第14周价格
     */
    private BigDecimal wk14Msrp;

    /**
     * 第15周价格
     */
    private BigDecimal wk15Msrp;

    /**
     * 第16周价格
     */
    private BigDecimal wk16Msrp;

    /**
     * 第17周价格
     */
    private BigDecimal wk17Msrp;

    /**
     * 第18周价格
     */
    private BigDecimal wk18Msrp;

    /**
     * 第19周价格
     */
    private BigDecimal wk19Msrp;

    /**
     * 第20周价格
     */
    private BigDecimal wk20Msrp;

    /**
     * 第21周价格
     */
    private BigDecimal wk21Msrp;

    /**
     * 第22周价格
     */
    private BigDecimal wk22Msrp;

    /**
     * 第23周价格
     */
    private BigDecimal wk23Msrp;

    /**
     * 第24周价格
     */
    private BigDecimal wk24Msrp;

    /**
     * 第25周价格
     */
    private BigDecimal wk25Msrp;

    /**
     * 第26周价格
     */
    private BigDecimal wk26Msrp;

    /**
     * 第27周价格
     */
    private BigDecimal wk27Msrp;

    /**
     * 第28周价格
     */
    private BigDecimal wk28Msrp;

    /**
     * 第29周价格
     */
    private BigDecimal wk29Msrp;

    /**
     * 第30周价格
     */
    private BigDecimal wk30Msrp;

    /**
     * 第31周价格
     */
    private BigDecimal wk31Msrp;

    /**
     * 第32周价格
     */
    private BigDecimal wk32Msrp;

    /**
     * 第33周价格
     */
    private BigDecimal wk33Msrp;

    /**
     * 第34周价格
     */
    private BigDecimal wk34Msrp;

    /**
     * 第35周价格
     */
    private BigDecimal wk35Msrp;

    /**
     * 第36周价格
     */
    private BigDecimal wk36Msrp;

    /**
     * 第37周价格
     */
    private BigDecimal wk37Msrp;

    /**
     * 第38周价格
     */
    private BigDecimal wk38Msrp;

    /**
     * 第39周价格
     */
    private BigDecimal wk39Msrp;

    /**
     * 第40周价格
     */
    private BigDecimal wk40Msrp;

    /**
     * 第41周价格
     */
    private BigDecimal wk41Msrp;

    /**
     * 第42周价格
     */
    private BigDecimal wk42Msrp;

    /**
     * 第43周价格
     */
    private BigDecimal wk43Msrp;

    /**
     * 第44周价格
     */
    private BigDecimal wk44Msrp;

    /**
     * 第45周价格
     */
    private BigDecimal wk45Msrp;

    /**
     * 第46周价格
     */
    private BigDecimal wk46Msrp;

    /**
     * 第47周价格
     */
    private BigDecimal wk47Msrp;

    /**
     * 第48周价格
     */
    private BigDecimal wk48Msrp;

    /**
     * 第49周价格
     */
    private BigDecimal wk49Msrp;

    /**
     * 第50周价格
     */
    private BigDecimal wk50Msrp;

    /**
     * 第51周价格
     */
    private BigDecimal wk51Msrp;

    /**
     * 第52周价格
     */
    private BigDecimal wk52Msrp;

    /**
     * 第53周价格
     */
    private BigDecimal wk53Msrp;

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
    private String remark;

    private Integer selectedWeek;

    private String validFromWKGroup;

    private String validToWKGroup;

    private String modelName;

    private String disposeName;

}
