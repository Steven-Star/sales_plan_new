package com.ruoyi.project.tek.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author steven.guo
 * @since 2020-06-02
 */
@Data
public class NewTekMsrpVO {

    private String productName;

    private String customerName;

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
    @Excel(name = "SKU", type = Excel.Type.EXPORT)
    private String sku;

    @Excel(name = "Country", type = Excel.Type.EXPORT)
    private String country;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date validFrom;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date validTo;

    @Excel(name = "ValidFrom", type = Excel.Type.EXPORT)
    private String validFromStr;

    @Excel(name = "ValidTo", type = Excel.Type.EXPORT)
    private String validToStr;

    /**
     * 建议零售价
     */
    @Excel(name = "MSRP", type = Excel.Type.EXPORT)
    private BigDecimal msrp;

    @Excel(name = "createTime", type = Excel.Type.EXPORT)
    private String createTimeStr;

    @Excel(name = "updateTime", type = Excel.Type.EXPORT)
    private String updateTimeStr;

    @Excel(name = "creator", type = Excel.Type.EXPORT)
    private String createBy;

    @Excel(name = "updator", type = Excel.Type.EXPORT)
    private String updateBy;

    private String channelType;

    /**
     * 币种
     */
    private String currencyType;

    private String year;

    private BigDecimal sellingPrice;

    private BigDecimal sellingPriceUSD;

    private String validFromWK;

    private String validToWK;

    private String promotionType;

    private String modelInfo;

    /**
     * msrpWK表的币别
     * */
    private String currencyTypeMsrpWK;

    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;

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
     * 其他折扣的建议零售价（给用户下拉框选择的）
     */
    private BigDecimal msrpOther;

    private BigDecimal msrpOtherPrice;

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

}
