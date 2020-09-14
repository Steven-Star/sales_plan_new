package com.ruoyi.project.tek.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author steven.guo
 * @version 1.0
 * @description:
 * @date 2020/6/3 16:03
 */
public class NewTekCalendarVO {

    private String sku;

    private String promotionType;

    private String productName;

    private Long disposeGroupId;

    private String disposeGroupName;

    private Long modelId;

    private String modelName;

    private Long categoryId;

    private String categoryName;

    private Long disposeId;

    private String disposeName;

    private Integer modelSortId;

    private Integer disposeSortId;

    private String customerName;

    //用来返回查询列表中所有的客户名称
    private List<String> customerList;

    //用来返回查询列表中所有的sku名称
    private List<String> skuList;

    private Long msrpId;

    private Long customerId;

    private String validFromWK;

    private String validToWK;

    /**
     * sku+customerId+country+year确定的唯一键
     * */
    private String key;

    private BigDecimal msrp;

    private BigDecimal msrpCountry;

    private String currencyType;

    private String year;

    private String currencyTypeWK;

    private String country;

    private BigDecimal sellingPrice;

    private BigDecimal sellingPriceUSD;

    private BigDecimal wk1Msrp;

    private BigDecimal wk1MsrpUSD;

    /**
     * 第1周价格是否合理状态
     * 0-正常 -1-异常
     * */
    private Integer wk1Status;

    private BigDecimal wk2Msrp;

    private BigDecimal wk2MsrpUSD;

    private Integer wk2Status;

    private BigDecimal wk3Msrp;

    private BigDecimal wk3MsrpUSD;

    private Integer wk3Status;

    private BigDecimal wk4Msrp;

    private BigDecimal wk4MsrpUSD;

    private Integer wk4Status;

    private BigDecimal wk5Msrp;

    private BigDecimal wk5MsrpUSD;

    private Integer wk5Status;

    private BigDecimal wk6Msrp;

    private BigDecimal wk6MsrpUSD;

    private Integer wk6Status;

    private BigDecimal wk7Msrp;

    private BigDecimal wk7MsrpUSD;

    private Integer wk7Status;

    private BigDecimal wk8Msrp;

    private BigDecimal wk8MsrpUSD;

    private Integer wk8Status;

    private BigDecimal wk9Msrp;

    private BigDecimal wk9MsrpUSD;

    private Integer wk9Status;

    private BigDecimal wk10Msrp;

    private BigDecimal wk10MsrpUSD;

    private Integer wk10Status;

    private BigDecimal wk11Msrp;

    private BigDecimal wk11MsrpUSD;

    private Integer wk11Status;

    private BigDecimal wk12Msrp;

    private BigDecimal wk12MsrpUSD;

    private Integer wk12Status;

    private BigDecimal wk13Msrp;

    private BigDecimal wk13MsrpUSD;

    private Integer wk13Status;

    private BigDecimal wk14Msrp;

    private BigDecimal wk14MsrpUSD;

    private Integer wk14Status;

    private BigDecimal wk15Msrp;

    private BigDecimal wk15MsrpUSD;

    private Integer wk15Status;

    private BigDecimal wk16Msrp;

    private BigDecimal wk16MsrpUSD;

    private Integer wk16Status;

    private BigDecimal wk17Msrp;

    private BigDecimal wk17MsrpUSD;

    private Integer wk17Status;

    private BigDecimal wk18Msrp;

    private BigDecimal wk18MsrpUSD;

    private Integer wk18Status;

    private BigDecimal wk19Msrp;

    private BigDecimal wk19MsrpUSD;

    private Integer wk19Status;

    private BigDecimal wk20Msrp;

    private BigDecimal wk20MsrpUSD;

    private Integer wk20Status;

    private BigDecimal wk21Msrp;

    private BigDecimal wk21MsrpUSD;

    private Integer wk21Status;

    private BigDecimal wk22Msrp;

    private BigDecimal wk22MsrpUSD;

    private Integer wk22Status;

    private BigDecimal wk23Msrp;

    private BigDecimal wk23MsrpUSD;

    private Integer wk23Status;

    private BigDecimal wk24Msrp;

    private BigDecimal wk24MsrpUSD;

    private Integer wk24Status;

    private BigDecimal wk25Msrp;

    private BigDecimal wk25MsrpUSD;

    private Integer wk25Status;

    private BigDecimal wk26Msrp;

    private BigDecimal wk26MsrpUSD;

    private Integer wk26Status;

    private BigDecimal wk27Msrp;

    private BigDecimal wk27MsrpUSD;

    private Integer wk27Status;

    private BigDecimal wk28Msrp;

    private BigDecimal wk28MsrpUSD;

    private Integer wk28Status;

    private BigDecimal wk29Msrp;

    private BigDecimal wk29MsrpUSD;

    private Integer wk29Status;

    private BigDecimal wk30Msrp;

    private BigDecimal wk30MsrpUSD;

    private Integer wk30Status;

    private BigDecimal wk31Msrp;

    private BigDecimal wk31MsrpUSD;

    private Integer wk31Status;

    private BigDecimal wk32Msrp;

    private BigDecimal wk32MsrpUSD;

    private Integer wk32Status;

    private BigDecimal wk33Msrp;

    private BigDecimal wk33MsrpUSD;

    private Integer wk33Status;

    private BigDecimal wk34Msrp;

    private BigDecimal wk34MsrpUSD;

    private Integer wk34Status;

    private BigDecimal wk35Msrp;

    private BigDecimal wk35MsrpUSD;

    private Integer wk35Status;

    private BigDecimal wk36Msrp;

    private BigDecimal wk36MsrpUSD;

    private Integer wk36Status;

    private BigDecimal wk37Msrp;

    private BigDecimal wk37MsrpUSD;

    private Integer wk37Status;

    private BigDecimal wk38Msrp;

    private BigDecimal wk38MsrpUSD;

    private Integer wk38Status;

    private BigDecimal wk39Msrp;

    private BigDecimal wk39MsrpUSD;

    private Integer wk39Status;

    private BigDecimal wk40Msrp;

    private BigDecimal wk40MsrpUSD;

    private Integer wk40Status;

    private BigDecimal wk41Msrp;

    private BigDecimal wk41MsrpUSD;

    private Integer wk41Status;

    private BigDecimal wk42Msrp;

    private BigDecimal wk42MsrpUSD;

    private Integer wk42Status;

    private BigDecimal wk43Msrp;

    private BigDecimal wk43MsrpUSD;

    private Integer wk43Status;

    private BigDecimal wk44Msrp;

    private BigDecimal wk44MsrpUSD;

    private Integer wk44Status;

    private BigDecimal wk45Msrp;

    private BigDecimal wk45MsrpUSD;

    private Integer wk45Status;

    private BigDecimal wk46Msrp;

    private BigDecimal wk46MsrpUSD;

    private Integer wk46Status;

    private BigDecimal wk47Msrp;

    private BigDecimal wk47MsrpUSD;

    private Integer wk47Status;

    private BigDecimal wk48Msrp;

    private BigDecimal wk48MsrpUSD;

    private Integer wk48Status;

    private BigDecimal wk49Msrp;

    private BigDecimal wk49MsrpUSD;

    private Integer wk49Status;

    private BigDecimal wk50Msrp;

    private BigDecimal wk50MsrpUSD;

    private Integer wk50Status;

    private BigDecimal wk51Msrp;

    private BigDecimal wk51MsrpUSD;

    private Integer wk51Status;

    private BigDecimal wk52Msrp;

    private BigDecimal wk52MsrpUSD;

    private Integer wk52Status;

    private BigDecimal wk53Msrp;

    private BigDecimal wk53MsrpUSD;

    private Integer wk53Status;

    private String wk1Day1Date;

    private String wk1Day2Date;

    private String wk1Day3Date;

    private String wk1Day4Date;

    private String wk1Day5Date;

    private String wk1Day6Date;

    private String wk1Day7Date;

    private String wk2Day1Date;

    private String wk2Day2Date;

    private String wk2Day3Date;

    private String wk2Day4Date;

    private String wk2Day5Date;

    private String wk2Day6Date;

    private String wk2Day7Date;

    private String wk3Day1Date;

    private String wk3Day2Date;

    private String wk3Day3Date;

    private String wk3Day4Date;

    private String wk3Day5Date;

    private String wk3Day6Date;

    private String wk3Day7Date;

    private String wk4Day1Date;

    private String wk4Day2Date;

    private String wk4Day3Date;

    private String wk4Day4Date;

    private String wk4Day5Date;

    private String wk4Day6Date;

    private String wk4Day7Date;

    private String wk5Day1Date;

    private String wk5Day2Date;

    private String wk5Day3Date;

    private String wk5Day4Date;

    private String wk5Day5Date;

    private String wk5Day6Date;

    private String wk5Day7Date;

    private String wk6Day1Date;

    private String wk6Day2Date;

    private String wk6Day3Date;

    private String wk6Day4Date;

    private String wk6Day5Date;

    private String wk6Day6Date;

    private String wk6Day7Date;

    private String wk7Day1Date;

    private String wk7Day2Date;

    private String wk7Day3Date;

    private String wk7Day4Date;

    private String wk7Day5Date;

    private String wk7Day6Date;

    private String wk7Day7Date;

    private String wk8Day1Date;

    private String wk8Day2Date;

    private String wk8Day3Date;

    private String wk8Day4Date;

    private String wk8Day5Date;

    private String wk8Day6Date;

    private String wk8Day7Date;

    private String wk9Day1Date;

    private String wk9Day2Date;

    private String wk9Day3Date;

    private String wk9Day4Date;

    private String wk9Day5Date;

    private String wk9Day6Date;

    private String wk9Day7Date;

    private String wk10Day1Date;

    private String wk10Day2Date;

    private String wk10Day3Date;

    private String wk10Day4Date;

    private String wk10Day5Date;

    private String wk10Day6Date;

    private String wk10Day7Date;

    private String wk11Day1Date;

    private String wk11Day2Date;

    private String wk11Day3Date;

    private String wk11Day4Date;

    private String wk11Day5Date;

    private String wk11Day6Date;

    private String wk11Day7Date;

    private String wk12Day1Date;

    private String wk12Day2Date;

    private String wk12Day3Date;

    private String wk12Day4Date;

    private String wk12Day5Date;

    private String wk12Day6Date;

    private String wk12Day7Date;

    private String wk13Day1Date;

    private String wk13Day2Date;

    private String wk13Day3Date;

    private String wk13Day4Date;

    private String wk13Day5Date;

    private String wk13Day6Date;

    private String wk13Day7Date;

    private String wk14Day1Date;

    private String wk14Day2Date;

    private String wk14Day3Date;

    private String wk14Day4Date;

    private String wk14Day5Date;

    private String wk14Day6Date;

    private String wk14Day7Date;

    private String wk15Day1Date;

    private String wk15Day2Date;

    private String wk15Day3Date;

    private String wk15Day4Date;

    private String wk15Day5Date;

    private String wk15Day6Date;

    private String wk15Day7Date;

    private String wk16Day1Date;

    private String wk16Day2Date;

    private String wk16Day3Date;

    private String wk16Day4Date;

    private String wk16Day5Date;

    private String wk16Day6Date;

    private String wk16Day7Date;

    private String wk17Day1Date;

    private String wk17Day2Date;

    private String wk17Day3Date;

    private String wk17Day4Date;

    private String wk17Day5Date;

    private String wk17Day6Date;

    private String wk17Day7Date;

    private String wk18Day1Date;

    private String wk18Day2Date;

    private String wk18Day3Date;

    private String wk18Day4Date;

    private String wk18Day5Date;

    private String wk18Day6Date;

    private String wk18Day7Date;

    private String wk19Day1Date;

    private String wk19Day2Date;

    private String wk19Day3Date;

    private String wk19Day4Date;

    private String wk19Day5Date;

    private String wk19Day6Date;

    private String wk19Day7Date;

    private String wk20Day1Date;

    private String wk20Day2Date;

    private String wk20Day3Date;

    private String wk20Day4Date;

    private String wk20Day5Date;

    private String wk20Day6Date;

    private String wk20Day7Date;

    private String wk21Day1Date;

    private String wk21Day2Date;

    private String wk21Day3Date;

    private String wk21Day4Date;

    private String wk21Day5Date;

    private String wk21Day6Date;

    private String wk21Day7Date;

    private String wk22Day1Date;

    private String wk22Day2Date;

    private String wk22Day3Date;

    private String wk22Day4Date;

    private String wk22Day5Date;

    private String wk22Day6Date;

    private String wk22Day7Date;

    private String wk23Day1Date;

    private String wk23Day2Date;

    private String wk23Day3Date;

    private String wk23Day4Date;

    private String wk23Day5Date;

    private String wk23Day6Date;

    private String wk23Day7Date;

    private String wk24Day1Date;

    private String wk24Day2Date;

    private String wk24Day3Date;

    private String wk24Day4Date;

    private String wk24Day5Date;

    private String wk24Day6Date;

    private String wk24Day7Date;

    private String wk25Day1Date;

    private String wk25Day2Date;

    private String wk25Day3Date;

    private String wk25Day4Date;

    private String wk25Day5Date;

    private String wk25Day6Date;

    private String wk25Day7Date;

    private String wk26Day1Date;

    private String wk26Day2Date;

    private String wk26Day3Date;

    private String wk26Day4Date;

    private String wk26Day5Date;

    private String wk26Day6Date;

    private String wk26Day7Date;

    private String wk27Day1Date;

    private String wk27Day2Date;

    private String wk27Day3Date;

    private String wk27Day4Date;

    private String wk27Day5Date;

    private String wk27Day6Date;

    private String wk27Day7Date;

    private String wk28Day1Date;

    private String wk28Day2Date;

    private String wk28Day3Date;

    private String wk28Day4Date;

    private String wk28Day5Date;

    private String wk28Day6Date;

    private String wk28Day7Date;

    private String wk29Day1Date;

    private String wk29Day2Date;

    private String wk29Day3Date;

    private String wk29Day4Date;

    private String wk29Day5Date;

    private String wk29Day6Date;

    private String wk29Day7Date;

    private String wk30Day1Date;

    private String wk30Day2Date;

    private String wk30Day3Date;

    private String wk30Day4Date;

    private String wk30Day5Date;

    private String wk30Day6Date;

    private String wk30Day7Date;

    private String wk31Day1Date;

    private String wk31Day2Date;

    private String wk31Day3Date;

    private String wk31Day4Date;

    private String wk31Day5Date;

    private String wk31Day6Date;

    private String wk31Day7Date;

    private String wk32Day1Date;

    private String wk32Day2Date;

    private String wk32Day3Date;

    private String wk32Day4Date;

    private String wk32Day5Date;

    private String wk32Day6Date;

    private String wk32Day7Date;

    private String wk33Day1Date;

    private String wk33Day2Date;

    private String wk33Day3Date;

    private String wk33Day4Date;

    private String wk33Day5Date;

    private String wk33Day6Date;

    private String wk33Day7Date;

    private String wk34Day1Date;

    private String wk34Day2Date;

    private String wk34Day3Date;

    private String wk34Day4Date;

    private String wk34Day5Date;

    private String wk34Day6Date;

    private String wk34Day7Date;

    private String wk35Day1Date;

    private String wk35Day2Date;

    private String wk35Day3Date;

    private String wk35Day4Date;

    private String wk35Day5Date;

    private String wk35Day6Date;

    private String wk35Day7Date;

    private String wk36Day1Date;

    private String wk36Day2Date;

    private String wk36Day3Date;

    private String wk36Day4Date;

    private String wk36Day5Date;

    private String wk36Day6Date;

    private String wk36Day7Date;

    private String wk37Day1Date;

    private String wk37Day2Date;

    private String wk37Day3Date;

    private String wk37Day4Date;

    private String wk37Day5Date;

    private String wk37Day6Date;

    private String wk37Day7Date;

    private String wk38Day1Date;

    private String wk38Day2Date;

    private String wk38Day3Date;

    private String wk38Day4Date;

    private String wk38Day5Date;

    private String wk38Day6Date;

    private String wk38Day7Date;

    private String wk39Day1Date;

    private String wk39Day2Date;

    private String wk39Day3Date;

    private String wk39Day4Date;

    private String wk39Day5Date;

    private String wk39Day6Date;

    private String wk39Day7Date;

    private String wk40Day1Date;

    private String wk40Day2Date;

    private String wk40Day3Date;

    private String wk40Day4Date;

    private String wk40Day5Date;

    private String wk40Day6Date;

    private String wk40Day7Date;

    private String wk41Day1Date;

    private String wk41Day2Date;

    private String wk41Day3Date;

    private String wk41Day4Date;

    private String wk41Day5Date;

    private String wk41Day6Date;

    private String wk41Day7Date;

    private String wk42Day1Date;

    private String wk42Day2Date;

    private String wk42Day3Date;

    private String wk42Day4Date;

    private String wk42Day5Date;

    private String wk42Day6Date;

    private String wk42Day7Date;

    private String wk43Day1Date;

    private String wk43Day2Date;

    private String wk43Day3Date;

    private String wk43Day4Date;

    private String wk43Day5Date;

    private String wk43Day6Date;

    private String wk43Day7Date;

    private String wk44Day1Date;

    private String wk44Day2Date;

    private String wk44Day3Date;

    private String wk44Day4Date;

    private String wk44Day5Date;

    private String wk44Day6Date;

    private String wk44Day7Date;

    private String wk45Day1Date;

    private String wk45Day2Date;

    private String wk45Day3Date;

    private String wk45Day4Date;

    private String wk45Day5Date;

    private String wk45Day6Date;

    private String wk45Day7Date;

    private String wk46Day1Date;

    private String wk46Day2Date;

    private String wk46Day3Date;

    private String wk46Day4Date;

    private String wk46Day5Date;

    private String wk46Day6Date;

    private String wk46Day7Date;

    private String wk47Day1Date;

    private String wk47Day2Date;

    private String wk47Day3Date;

    private String wk47Day4Date;

    private String wk47Day5Date;

    private String wk47Day6Date;

    private String wk47Day7Date;

    private String wk48Day1Date;

    private String wk48Day2Date;

    private String wk48Day3Date;

    private String wk48Day4Date;

    private String wk48Day5Date;

    private String wk48Day6Date;

    private String wk48Day7Date;

    private String wk49Day1Date;

    private String wk49Day2Date;

    private String wk49Day3Date;

    private String wk49Day4Date;

    private String wk49Day5Date;

    private String wk49Day6Date;

    private String wk49Day7Date;

    private String wk50Day1Date;

    private String wk50Day2Date;

    private String wk50Day3Date;

    private String wk50Day4Date;

    private String wk50Day5Date;

    private String wk50Day6Date;

    private String wk50Day7Date;

    private String wk51Day1Date;

    private String wk51Day2Date;

    private String wk51Day3Date;

    private String wk51Day4Date;

    private String wk51Day5Date;

    private String wk51Day6Date;

    private String wk51Day7Date;

    private String wk52Day1Date;

    private String wk52Day2Date;

    private String wk52Day3Date;

    private String wk52Day4Date;

    private String wk52Day5Date;

    private String wk52Day6Date;

    private String wk52Day7Date;

    private String wk53Day1Date;

    private String wk53Day2Date;

    private String wk53Day3Date;

    private String wk53Day4Date;

    private String wk53Day5Date;

    private String wk53Day6Date;

    private String wk53Day7Date;



    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private char delFlag;

    private String createBy;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String updateBy;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private String remark;



    private BigDecimal wk1Day1;

    private Integer wk1Day1Status;

    private BigDecimal wk1Day2;

    private Integer wk1Day2Status;

    private BigDecimal wk1Day3;

    private Integer wk1Day3Status;

    private BigDecimal wk1Day4;

    private Integer wk1Day4Status;

    private BigDecimal wk1Day5;

    private Integer wk1Day5Status;

    private BigDecimal wk1Day6;

    private Integer wk1Day6Status;

    private BigDecimal wk1Day7;

    private Integer wk1Day7Status;

    private BigDecimal wk2Day1;

    private Integer wk2Day1Status;

    private BigDecimal wk2Day2;

    private Integer wk2Day2Status;

    private BigDecimal wk2Day3;

    private Integer wk2Day3Status;

    private BigDecimal wk2Day4;

    private Integer wk2Day4Status;

    private BigDecimal wk2Day5;

    private Integer wk2Day5Status;

    private BigDecimal wk2Day6;

    private Integer wk2Day6Status;

    private BigDecimal wk2Day7;

    private Integer wk2Day7Status;

    private BigDecimal wk3Day1;

    private Integer wk3Day1Status;

    private BigDecimal wk3Day2;

    private Integer wk3Day2Status;

    private BigDecimal wk3Day3;

    private Integer wk3Day3Status;

    private BigDecimal wk3Day4;

    private Integer wk3Day4Status;

    private BigDecimal wk3Day5;

    private Integer wk3Day5Status;

    private BigDecimal wk3Day6;

    private Integer wk3Day6Status;

    private BigDecimal wk3Day7;

    private Integer wk3Day7Status;

    private BigDecimal wk4Day1;

    private Integer wk4Day1Status;

    private BigDecimal wk4Day2;

    private Integer wk4Day2Status;

    private BigDecimal wk4Day3;

    private Integer wk4Day3Status;

    private BigDecimal wk4Day4;

    private Integer wk4Day4Status;

    private BigDecimal wk4Day5;

    private Integer wk4Day5Status;

    private BigDecimal wk4Day6;

    private Integer wk4Day6Status;

    private BigDecimal wk4Day7;

    private Integer wk4Day7Status;

    private BigDecimal wk5Day1;

    private Integer wk5Day1Status;

    private BigDecimal wk5Day2;

    private Integer wk5Day2Status;

    private BigDecimal wk5Day3;

    private Integer wk5Day3Status;

    private BigDecimal wk5Day4;

    private Integer wk5Day4Status;

    private BigDecimal wk5Day5;

    private Integer wk5Day5Status;

    private BigDecimal wk5Day6;

    private Integer wk5Day6Status;

    private BigDecimal wk5Day7;

    private Integer wk5Day7Status;

    private BigDecimal wk6Day1;

    private Integer wk6Day1Status;

    private BigDecimal wk6Day2;

    private Integer wk6Day2Status;

    private BigDecimal wk6Day3;

    private Integer wk6Day3Status;

    private BigDecimal wk6Day4;

    private Integer wk6Day4Status;

    private BigDecimal wk6Day5;

    private Integer wk6Day5Status;

    private BigDecimal wk6Day6;

    private Integer wk6Day6Status;

    private BigDecimal wk6Day7;

    private Integer wk6Day7Status;

    private BigDecimal wk7Day1;

    private Integer wk7Day1Status;

    private BigDecimal wk7Day2;

    private Integer wk7Day2Status;

    private BigDecimal wk7Day3;

    private Integer wk7Day3Status;

    private BigDecimal wk7Day4;

    private Integer wk7Day4Status;

    private BigDecimal wk7Day5;

    private Integer wk7Day5Status;

    private BigDecimal wk7Day6;

    private Integer wk7Day6Status;

    private BigDecimal wk7Day7;

    private Integer wk7Day7Status;

    private BigDecimal wk8Day1;

    private Integer wk8Day1Status;

    private BigDecimal wk8Day2;

    private Integer wk8Day2Status;

    private BigDecimal wk8Day3;

    private Integer wk8Day3Status;

    private BigDecimal wk8Day4;

    private Integer wk8Day4Status;

    private BigDecimal wk8Day5;

    private Integer wk8Day5Status;

    private BigDecimal wk8Day6;

    private Integer wk8Day6Status;

    private BigDecimal wk8Day7;

    private Integer wk8Day7Status;

    private BigDecimal wk9Day1;

    private Integer wk9Day1Status;

    private BigDecimal wk9Day2;

    private Integer wk9Day2Status;

    private BigDecimal wk9Day3;

    private Integer wk9Day3Status;

    private BigDecimal wk9Day4;

    private Integer wk9Day4Status;

    private BigDecimal wk9Day5;

    private Integer wk9Day5Status;

    private BigDecimal wk9Day6;

    private Integer wk9Day6Status;

    private BigDecimal wk9Day7;

    private Integer wk9Day7Status;

    private BigDecimal wk10Day1;

    private Integer wk10Day1Status;

    private BigDecimal wk10Day2;

    private Integer wk10Day2Status;

    private BigDecimal wk10Day3;

    private Integer wk10Day3Status;

    private BigDecimal wk10Day4;

    private Integer wk10Day4Status;

    private BigDecimal wk10Day5;

    private Integer wk10Day5Status;

    private BigDecimal wk10Day6;

    private Integer wk10Day6Status;

    private BigDecimal wk10Day7;

    private Integer wk10Day7Status;

    private BigDecimal wk11Day1;

    private Integer wk11Day1Status;

    private BigDecimal wk11Day2;

    private Integer wk11Day2Status;

    private BigDecimal wk11Day3;

    private Integer wk11Day3Status;

    private BigDecimal wk11Day4;

    private Integer wk11Day4Status;

    private BigDecimal wk11Day5;

    private Integer wk11Day5Status;

    private BigDecimal wk11Day6;

    private Integer wk11Day6Status;

    private BigDecimal wk11Day7;

    private Integer wk11Day7Status;

    private BigDecimal wk12Day1;

    private Integer wk12Day1Status;

    private BigDecimal wk12Day2;

    private Integer wk12Day2Status;

    private BigDecimal wk12Day3;

    private Integer wk12Day3Status;

    private BigDecimal wk12Day4;

    private Integer wk12Day4Status;

    private BigDecimal wk12Day5;

    private Integer wk12Day5Status;

    private BigDecimal wk12Day6;

    private Integer wk12Day6Status;

    private BigDecimal wk12Day7;

    private Integer wk12Day7Status;

    private BigDecimal wk13Day1;

    private Integer wk13Day1Status;

    private BigDecimal wk13Day2;

    private Integer wk13Day2Status;

    private BigDecimal wk13Day3;

    private Integer wk13Day3Status;

    private BigDecimal wk13Day4;

    private Integer wk13Day4Status;

    private BigDecimal wk13Day5;

    private Integer wk13Day5Status;

    private BigDecimal wk13Day6;

    private Integer wk13Day6Status;

    private BigDecimal wk13Day7;

    private Integer wk13Day7Status;

    private BigDecimal wk14Day1;

    private Integer wk14Day1Status;

    private BigDecimal wk14Day2;

    private Integer wk14Day2Status;

    private BigDecimal wk14Day3;

    private Integer wk14Day3Status;

    private BigDecimal wk14Day4;

    private Integer wk14Day4Status;

    private BigDecimal wk14Day5;

    private Integer wk14Day5Status;

    private BigDecimal wk14Day6;

    private Integer wk14Day6Status;

    private BigDecimal wk14Day7;

    private Integer wk14Day7Status;

    private BigDecimal wk15Day1;

    private Integer wk15Day1Status;

    private BigDecimal wk15Day2;

    private Integer wk15Day2Status;

    private BigDecimal wk15Day3;

    private Integer wk15Day3Status;

    private BigDecimal wk15Day4;

    private Integer wk15Day4Status;

    private BigDecimal wk15Day5;

    private Integer wk15Day5Status;

    private BigDecimal wk15Day6;

    private Integer wk15Day6Status;

    private BigDecimal wk15Day7;

    private Integer wk15Day7Status;

    private BigDecimal wk16Day1;

    private Integer wk16Day1Status;

    private BigDecimal wk16Day2;

    private Integer wk16Day2Status;

    private BigDecimal wk16Day3;

    private Integer wk16Day3Status;

    private BigDecimal wk16Day4;

    private Integer wk16Day4Status;

    private BigDecimal wk16Day5;

    private Integer wk16Day5Status;

    private BigDecimal wk16Day6;

    private Integer wk16Day6Status;

    private BigDecimal wk16Day7;

    private Integer wk16Day7Status;

    private BigDecimal wk17Day1;

    private Integer wk17Day1Status;

    private BigDecimal wk17Day2;

    private Integer wk17Day2Status;

    private BigDecimal wk17Day3;

    private Integer wk17Day3Status;

    private BigDecimal wk17Day4;

    private Integer wk17Day4Status;

    private BigDecimal wk17Day5;

    private Integer wk17Day5Status;

    private BigDecimal wk17Day6;

    private Integer wk17Day6Status;

    private BigDecimal wk17Day7;

    private Integer wk17Day7Status;

    private BigDecimal wk18Day1;

    private Integer wk18Day1Status;

    private BigDecimal wk18Day2;

    private Integer wk18Day2Status;

    private BigDecimal wk18Day3;

    private Integer wk18Day3Status;

    private BigDecimal wk18Day4;

    private Integer wk18Day4Status;

    private BigDecimal wk18Day5;

    private Integer wk18Day5Status;

    private BigDecimal wk18Day6;

    private Integer wk18Day6Status;

    private BigDecimal wk18Day7;

    private Integer wk18Day7Status;

    private BigDecimal wk19Day1;

    private Integer wk19Day1Status;

    private BigDecimal wk19Day2;

    private Integer wk19Day2Status;

    private BigDecimal wk19Day3;

    private Integer wk19Day3Status;

    private BigDecimal wk19Day4;

    private Integer wk19Day4Status;

    private BigDecimal wk19Day5;

    private Integer wk19Day5Status;

    private BigDecimal wk19Day6;

    private Integer wk19Day6Status;

    private BigDecimal wk19Day7;

    private Integer wk19Day7Status;

    private BigDecimal wk20Day1;
    private Integer wk20Day1Status;

    private BigDecimal wk20Day2;
    private Integer wk20Day2Status;

    private BigDecimal wk20Day3;
    private Integer wk20Day3Status;

    private BigDecimal wk20Day4;
    private Integer wk20Day4Status;

    private BigDecimal wk20Day5;
    private Integer wk20Day5Status;

    private BigDecimal wk20Day6;
    private Integer wk20Day6Status;

    private BigDecimal wk20Day7;
    private Integer wk20Day7Status;

    private BigDecimal wk21Day1;
    private Integer wk21Day1Status;

    private BigDecimal wk21Day2;
    private Integer wk21Day2Status;

    private BigDecimal wk21Day3;
    private Integer wk21Day3Status;

    private BigDecimal wk21Day4;
    private Integer wk21Day4Status;

    private BigDecimal wk21Day5;
    private Integer wk21Day5Status;

    private BigDecimal wk21Day6;
    private Integer wk21Day6Status;

    private BigDecimal wk21Day7;
    private Integer wk21Day7Status;

    private BigDecimal wk22Day1;
    private Integer wk22Day1Status;

    private BigDecimal wk22Day2;
    private Integer wk22Day2Status;

    private BigDecimal wk22Day3;
    private Integer wk22Day3Status;

    private BigDecimal wk22Day4;
    private Integer wk22Day4Status;

    private BigDecimal wk22Day5;
    private Integer wk22Day5Status;

    private BigDecimal wk22Day6;
    private Integer wk22Day6Status;

    private BigDecimal wk22Day7;
    private Integer wk22Day7Status;

    private BigDecimal wk23Day1;
    private Integer wk23Day1Status;

    private BigDecimal wk23Day2;
    private Integer wk23Day2Status;

    private BigDecimal wk23Day3;
    private Integer wk23Day3Status;

    private BigDecimal wk23Day4;
    private Integer wk23Day4Status;

    private BigDecimal wk23Day5;
    private Integer wk23Day5Status;

    private BigDecimal wk23Day6;
    private Integer wk23Day6Status;

    private BigDecimal wk23Day7;
    private Integer wk23Day7Status;

    private BigDecimal wk24Day1;
    private Integer wk24Day1Status;

    private BigDecimal wk24Day2;
    private Integer wk24Day2Status;

    private BigDecimal wk24Day3;
    private Integer wk24Day3Status;

    private BigDecimal wk24Day4;
    private Integer wk24Day4Status;

    private BigDecimal wk24Day5;
    private Integer wk24Day5Status;

    private BigDecimal wk24Day6;
    private Integer wk24Day6Status;

    private BigDecimal wk24Day7;
    private Integer wk24Day7Status;

    private BigDecimal wk25Day1;
    private Integer wk25Day1Status;

    private BigDecimal wk25Day2;
    private Integer wk25Day2Status;

    private BigDecimal wk25Day3;
    private Integer wk25Day3Status;

    private BigDecimal wk25Day4;
    private Integer wk25Day4Status;

    private BigDecimal wk25Day5;
    private Integer wk25Day5Status;

    private BigDecimal wk25Day6;
    private Integer wk25Day6Status;

    private BigDecimal wk25Day7;
    private Integer wk25Day7Status;

    private BigDecimal wk26Day1;
    private Integer wk26Day1Status;

    private BigDecimal wk26Day2;
    private Integer wk26Day2Status;

    private BigDecimal wk26Day3;
    private Integer wk26Day3Status;

    private BigDecimal wk26Day4;
    private Integer wk26Day4Status;

    private BigDecimal wk26Day5;
    private Integer wk26Day5Status;

    private BigDecimal wk26Day6;
    private Integer wk26Day6Status;

    private BigDecimal wk26Day7;
    private Integer wk26Day7Status;

    private BigDecimal wk27Day1;
    private Integer wk27Day1Status;

    private BigDecimal wk27Day2;
    private Integer wk27Day2Status;

    private BigDecimal wk27Day3;
    private Integer wk27Day3Status;

    private BigDecimal wk27Day4;
    private Integer wk27Day4Status;

    private BigDecimal wk27Day5;
    private Integer wk27Day5Status;

    private BigDecimal wk27Day6;
    private Integer wk27Day6Status;

    private BigDecimal wk27Day7;
    private Integer wk27Day7Status;

    private BigDecimal wk28Day1;
    private Integer wk28Day1Status;

    private BigDecimal wk28Day2;
    private Integer wk28Day2Status;

    private BigDecimal wk28Day3;
    private Integer wk28Day3Status;

    private BigDecimal wk28Day4;
    private Integer wk28Day4Status;

    private BigDecimal wk28Day5;
    private Integer wk28Day5Status;

    private BigDecimal wk28Day6;
    private Integer wk28Day6Status;

    private BigDecimal wk28Day7;
    private Integer wk28Day7Status;

    private BigDecimal wk29Day1;
    private Integer wk29Day1Status;

    private BigDecimal wk29Day2;
    private Integer wk29Day2Status;

    private BigDecimal wk29Day3;
    private Integer wk29Day3Status;

    private BigDecimal wk29Day4;
    private Integer wk29Day4Status;

    private BigDecimal wk29Day5;
    private Integer wk29Day5Status;

    private BigDecimal wk29Day6;
    private Integer wk29Day6Status;

    private BigDecimal wk29Day7;
    private Integer wk29Day7Status;

    private BigDecimal wk30Day1;
    private Integer wk30Day1Status;

    private BigDecimal wk30Day2;
    private Integer wk30Day2Status;

    private BigDecimal wk30Day3;
    private Integer wk30Day3Status;

    private BigDecimal wk30Day4;
    private Integer wk30Day4Status;

    private BigDecimal wk30Day5;
    private Integer wk30Day5Status;

    private BigDecimal wk30Day6;
    private Integer wk30Day6Status;

    private BigDecimal wk30Day7;
    private Integer wk30Day7Status;

    private BigDecimal wk31Day1;
    private Integer wk31Day1Status;

    private BigDecimal wk31Day2;
    private Integer wk31Day2Status;

    private BigDecimal wk31Day3;
    private Integer wk31Day3Status;

    private BigDecimal wk31Day4;
    private Integer wk31Day4Status;

    private BigDecimal wk31Day5;
    private Integer wk31Day5Status;

    private BigDecimal wk31Day6;
    private Integer wk31Day6Status;

    private BigDecimal wk31Day7;
    private Integer wk31Day7Status;

    private BigDecimal wk32Day1;
    private Integer wk32Day1Status;

    private BigDecimal wk32Day2;
    private Integer wk32Day2Status;

    private BigDecimal wk32Day3;
    private Integer wk32Day3Status;

    private BigDecimal wk32Day4;
    private Integer wk32Day4Status;

    private BigDecimal wk32Day5;
    private Integer wk32Day5Status;

    private BigDecimal wk32Day6;
    private Integer wk32Day6Status;

    private BigDecimal wk32Day7;
    private Integer wk32Day7Status;

    private BigDecimal wk33Day1;
    private Integer wk33Day1Status;

    private BigDecimal wk33Day2;
    private Integer wk33Day2Status;

    private BigDecimal wk33Day3;
    private Integer wk33Day3Status;

    private BigDecimal wk33Day4;
    private Integer wk33Day4Status;

    private BigDecimal wk33Day5;
    private Integer wk33Day5Status;

    private BigDecimal wk33Day6;
    private Integer wk33Day6Status;

    private BigDecimal wk33Day7;
    private Integer wk33Day7Status;

    private BigDecimal wk34Day1;
    private Integer wk34Day1Status;

    private BigDecimal wk34Day2;
    private Integer wk34Day2Status;

    private BigDecimal wk34Day3;
    private Integer wk34Day3Status;

    private BigDecimal wk34Day4;
    private Integer wk34Day4Status;

    private BigDecimal wk34Day5;
    private Integer wk34Day5Status;

    private BigDecimal wk34Day6;
    private Integer wk34Day6Status;

    private BigDecimal wk34Day7;
    private Integer wk34Day7Status;

    private BigDecimal wk35Day1;
    private Integer wk35Day1Status;

    private BigDecimal wk35Day2;
    private Integer wk35Day2Status;

    private BigDecimal wk35Day3;
    private Integer wk35Day3Status;

    private BigDecimal wk35Day4;
    private Integer wk35Day4Status;

    private BigDecimal wk35Day5;
    private Integer wk35Day5Status;

    private BigDecimal wk35Day6;
    private Integer wk35Day6Status;

    private BigDecimal wk35Day7;
    private Integer wk35Day7Status;

    private BigDecimal wk36Day1;
    private Integer wk36Day1Status;

    private BigDecimal wk36Day2;
    private Integer wk36Day2Status;

    private BigDecimal wk36Day3;
    private Integer wk36Day3Status;

    private BigDecimal wk36Day4;
    private Integer wk36Day4Status;

    private BigDecimal wk36Day5;
    private Integer wk36Day5Status;

    private BigDecimal wk36Day6;
    private Integer wk36Day6Status;

    private BigDecimal wk36Day7;
    private Integer wk36Day7Status;

    private BigDecimal wk37Day1;
    private Integer wk37Day1Status;

    private BigDecimal wk37Day2;
    private Integer wk37Day2Status;

    private BigDecimal wk37Day3;
    private Integer wk37Day3Status;

    private BigDecimal wk37Day4;
    private Integer wk37Day4Status;

    private BigDecimal wk37Day5;
    private Integer wk37Day5Status;

    private BigDecimal wk37Day6;
    private Integer wk37Day6Status;

    private BigDecimal wk37Day7;
    private Integer wk37Day7Status;

    private BigDecimal wk38Day1;
    private Integer wk38Day1Status;

    private BigDecimal wk38Day2;
    private Integer wk38Day2Status;

    private BigDecimal wk38Day3;
    private Integer wk38Day3Status;

    private BigDecimal wk38Day4;
    private Integer wk38Day4Status;

    private BigDecimal wk38Day5;
    private Integer wk38Day5Status;

    private BigDecimal wk38Day6;
    private Integer wk38Day6Status;

    private BigDecimal wk38Day7;
    private Integer wk38Day7Status;

    private BigDecimal wk39Day1;
    private Integer wk39Day1Status;

    private BigDecimal wk39Day2;
    private Integer wk39Day2Status;

    private BigDecimal wk39Day3;
    private Integer wk39Day3Status;

    private BigDecimal wk39Day4;
    private Integer wk39Day4Status;

    private BigDecimal wk39Day5;
    private Integer wk39Day5Status;

    private BigDecimal wk39Day6;
    private Integer wk39Day6Status;

    private BigDecimal wk39Day7;
    private Integer wk39Day7Status;

    private BigDecimal wk40Day1;
    private Integer wk40Day1Status;

    private BigDecimal wk40Day2;
    private Integer wk40Day2Status;

    private BigDecimal wk40Day3;
    private Integer wk40Day3Status;

    private BigDecimal wk40Day4;
    private Integer wk40Day4Status;

    private BigDecimal wk40Day5;
    private Integer wk40Day5Status;

    private BigDecimal wk40Day6;
    private Integer wk40Day6Status;

    private BigDecimal wk40Day7;
    private Integer wk40Day7Status;

    private BigDecimal wk41Day1;
    private Integer wk41Day1Status;

    private BigDecimal wk41Day2;
    private Integer wk41Day2Status;

    private BigDecimal wk41Day3;
    private Integer wk41Day3Status;

    private BigDecimal wk41Day4;
    private Integer wk41Day4Status;

    private BigDecimal wk41Day5;
    private Integer wk41Day5Status;

    private BigDecimal wk41Day6;
    private Integer wk41Day6Status;

    private BigDecimal wk41Day7;
    private Integer wk41Day7Status;

    private BigDecimal wk42Day1;
    private Integer wk42Day1Status;

    private BigDecimal wk42Day2;
    private Integer wk42Day2Status;

    private BigDecimal wk42Day3;
    private Integer wk42Day3Status;

    private BigDecimal wk42Day4;
    private Integer wk42Day4Status;

    private BigDecimal wk42Day5;
    private Integer wk42Day5Status;

    private BigDecimal wk42Day6;
    private Integer wk42Day6Status;

    private BigDecimal wk42Day7;
    private Integer wk42Day7Status;

    private BigDecimal wk43Day1;
    private Integer wk43Day1Status;

    private BigDecimal wk43Day2;
    private Integer wk43Day2Status;

    private BigDecimal wk43Day3;
    private Integer wk43Day3Status;

    private BigDecimal wk43Day4;
    private Integer wk43Day4Status;

    private BigDecimal wk43Day5;
    private Integer wk43Day5Status;

    private BigDecimal wk43Day6;
    private Integer wk43Day6Status;

    private BigDecimal wk43Day7;
    private Integer wk43Day7Status;

    private BigDecimal wk44Day1;
    private Integer wk44Day1Status;

    private BigDecimal wk44Day2;
    private Integer wk44Day2Status;

    private BigDecimal wk44Day3;
    private Integer wk44Day3Status;

    private BigDecimal wk44Day4;
    private Integer wk44Day4Status;

    private BigDecimal wk44Day5;
    private Integer wk44Day5Status;

    private BigDecimal wk44Day6;
    private Integer wk44Day6Status;

    private BigDecimal wk44Day7;
    private Integer wk44Day7Status;

    private BigDecimal wk45Day1;
    private Integer wk45Day1Status;

    private BigDecimal wk45Day2;
    private Integer wk45Day2Status;

    private BigDecimal wk45Day3;
    private Integer wk45Day3Status;

    private BigDecimal wk45Day4;
    private Integer wk45Day4Status;

    private BigDecimal wk45Day5;
    private Integer wk45Day5Status;

    private BigDecimal wk45Day6;
    private Integer wk45Day6Status;

    private BigDecimal wk45Day7;
    private Integer wk45Day7Status;

    private BigDecimal wk46Day1;
    private Integer wk46Day1Status;

    private BigDecimal wk46Day2;
    private Integer wk46Day2Status;

    private BigDecimal wk46Day3;
    private Integer wk46Day3Status;

    private BigDecimal wk46Day4;
    private Integer wk46Day4Status;

    private BigDecimal wk46Day5;
    private Integer wk46Day5Status;

    private BigDecimal wk46Day6;
    private Integer wk46Day6Status;

    private BigDecimal wk46Day7;
    private Integer wk46Day7Status;

    private BigDecimal wk47Day1;
    private Integer wk47Day1Status;

    private BigDecimal wk47Day2;
    private Integer wk47Day2Status;

    private BigDecimal wk47Day3;
    private Integer wk47Day3Status;

    private BigDecimal wk47Day4;
    private Integer wk47Day4Status;

    private BigDecimal wk47Day5;
    private Integer wk47Day5Status;

    private BigDecimal wk47Day6;
    private Integer wk47Day6Status;

    private BigDecimal wk47Day7;
    private Integer wk47Day7Status;

    private BigDecimal wk48Day1;
    private Integer wk48Day1Status;

    private BigDecimal wk48Day2;
    private Integer wk48Day2Status;

    private BigDecimal wk48Day3;
    private Integer wk48Day3Status;

    private BigDecimal wk48Day4;
    private Integer wk48Day4Status;

    private BigDecimal wk48Day5;
    private Integer wk48Day5Status;

    private BigDecimal wk48Day6;
    private Integer wk48Day6Status;

    private BigDecimal wk48Day7;
    private Integer wk48Day7Status;

    private BigDecimal wk49Day1;
    private Integer wk49Day1Status;

    private BigDecimal wk49Day2;
    private Integer wk49Day2Status;

    private BigDecimal wk49Day3;
    private Integer wk49Day3Status;

    private BigDecimal wk49Day4;
    private Integer wk49Day4Status;

    private BigDecimal wk49Day5;
    private Integer wk49Day5Status;

    private BigDecimal wk49Day6;
    private Integer wk49Day6Status;

    private BigDecimal wk49Day7;
    private Integer wk49Day7Status;

    private BigDecimal wk50Day1;
    private Integer wk50Day1Status;

    private BigDecimal wk50Day2;
    private Integer wk50Day2Status;

    private BigDecimal wk50Day3;
    private Integer wk50Day3Status;

    private BigDecimal wk50Day4;
    private Integer wk50Day4Status;

    private BigDecimal wk50Day5;
    private Integer wk50Day5Status;

    private BigDecimal wk50Day6;
    private Integer wk50Day6Status;

    private BigDecimal wk50Day7;
    private Integer wk50Day7Status;

    private BigDecimal wk51Day1;
    private Integer wk51Day1Status;

    private BigDecimal wk51Day2;
    private Integer wk51Day2Status;

    private BigDecimal wk51Day3;
    private Integer wk51Day3Status;

    private BigDecimal wk51Day4;
    private Integer wk51Day4Status;

    private BigDecimal wk51Day5;
    private Integer wk51Day5Status;

    private BigDecimal wk51Day6;
    private Integer wk51Day6Status;

    private BigDecimal wk51Day7;
    private Integer wk51Day7Status;

    private BigDecimal wk52Day1;
    private Integer wk52Day1Status;

    private BigDecimal wk52Day2;
    private Integer wk52Day2Status;

    private BigDecimal wk52Day3;
    private Integer wk52Day3Status;

    private BigDecimal wk52Day4;
    private Integer wk52Day4Status;

    private BigDecimal wk52Day5;
    private Integer wk52Day5Status;

    private BigDecimal wk52Day6;
    private Integer wk52Day6Status;

    private BigDecimal wk52Day7;
    private Integer wk52Day7Status;

    private BigDecimal wk53Day1;
    private Integer wk53Day1Status;

    private BigDecimal wk53Day2;
    private Integer wk53Day2Status;

    private BigDecimal wk53Day3;
    private Integer wk53Day3Status;

    private BigDecimal wk53Day4;
    private Integer wk53Day4Status;

    private BigDecimal wk53Day5;
    private Integer wk53Day5Status;

    private BigDecimal wk53Day6;
    private Integer wk53Day6Status;

    private BigDecimal wk53Day7;
    private Integer wk53Day7Status;

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public String getCurrencyTypeWK() {
        return currencyTypeWK;
    }

    public void setCurrencyTypeWK(String currencyTypeWK) {
        this.currencyTypeWK = currencyTypeWK;
    }

    public void setWk3Msrp(BigDecimal wk3Msrp) {
        this.wk3Msrp = wk3Msrp;
    }

    public BigDecimal getWk10Msrp() {
        return wk10Msrp;
    }

    public void setWk10Msrp(BigDecimal wk10Msrp) {
        this.wk10Msrp = wk10Msrp;
    }

    public BigDecimal getWk10MsrpUSD() {
        return wk10MsrpUSD;
    }

    public void setWk10MsrpUSD(BigDecimal wk10MsrpUSD) {
        this.wk10MsrpUSD = wk10MsrpUSD;
    }

    public Integer getWk10Status() {
        return wk10Status;
    }

    public void setWk10Status(Integer wk10Status) {
        this.wk10Status = wk10Status;
    }

    public BigDecimal getWk11Msrp() {
        return wk11Msrp;
    }

    public void setWk11Msrp(BigDecimal wk11Msrp) {
        this.wk11Msrp = wk11Msrp;
    }

    public BigDecimal getWk11MsrpUSD() {
        return wk11MsrpUSD;
    }

    public void setWk11MsrpUSD(BigDecimal wk11MsrpUSD) {
        this.wk11MsrpUSD = wk11MsrpUSD;
    }

    public Integer getWk11Status() {
        return wk11Status;
    }

    public void setWk11Status(Integer wk11Status) {
        this.wk11Status = wk11Status;
    }

    public BigDecimal getWk12Msrp() {
        return wk12Msrp;
    }

    public void setWk12Msrp(BigDecimal wk12Msrp) {
        this.wk12Msrp = wk12Msrp;
    }

    public BigDecimal getWk12MsrpUSD() {
        return wk12MsrpUSD;
    }

    public void setWk12MsrpUSD(BigDecimal wk12MsrpUSD) {
        this.wk12MsrpUSD = wk12MsrpUSD;
    }

    public Integer getWk12Status() {
        return wk12Status;
    }

    public void setWk12Status(Integer wk12Status) {
        this.wk12Status = wk12Status;
    }

    public BigDecimal getWk13Msrp() {
        return wk13Msrp;
    }

    public void setWk13Msrp(BigDecimal wk13Msrp) {
        this.wk13Msrp = wk13Msrp;
    }

    public BigDecimal getWk13MsrpUSD() {
        return wk13MsrpUSD;
    }

    public void setWk13MsrpUSD(BigDecimal wk13MsrpUSD) {
        this.wk13MsrpUSD = wk13MsrpUSD;
    }

    public Integer getWk13Status() {
        return wk13Status;
    }

    public void setWk13Status(Integer wk13Status) {
        this.wk13Status = wk13Status;
    }

    public BigDecimal getWk14Msrp() {
        return wk14Msrp;
    }

    public void setWk14Msrp(BigDecimal wk14Msrp) {
        this.wk14Msrp = wk14Msrp;
    }

    public BigDecimal getWk14MsrpUSD() {
        return wk14MsrpUSD;
    }

    public void setWk14MsrpUSD(BigDecimal wk14MsrpUSD) {
        this.wk14MsrpUSD = wk14MsrpUSD;
    }

    public Integer getWk14Status() {
        return wk14Status;
    }

    public void setWk14Status(Integer wk14Status) {
        this.wk14Status = wk14Status;
    }

    public BigDecimal getWk15Msrp() {
        return wk15Msrp;
    }

    public void setWk15Msrp(BigDecimal wk15Msrp) {
        this.wk15Msrp = wk15Msrp;
    }

    public BigDecimal getWk15MsrpUSD() {
        return wk15MsrpUSD;
    }

    public void setWk15MsrpUSD(BigDecimal wk15MsrpUSD) {
        this.wk15MsrpUSD = wk15MsrpUSD;
    }

    public Integer getWk15Status() {
        return wk15Status;
    }

    public void setWk15Status(Integer wk15Status) {
        this.wk15Status = wk15Status;
    }

    public BigDecimal getWk16Msrp() {
        return wk16Msrp;
    }

    public void setWk16Msrp(BigDecimal wk16Msrp) {
        this.wk16Msrp = wk16Msrp;
    }

    public BigDecimal getWk16MsrpUSD() {
        return wk16MsrpUSD;
    }

    public void setWk16MsrpUSD(BigDecimal wk16MsrpUSD) {
        this.wk16MsrpUSD = wk16MsrpUSD;
    }

    public Integer getWk16Status() {
        return wk16Status;
    }

    public void setWk16Status(Integer wk16Status) {
        this.wk16Status = wk16Status;
    }

    public BigDecimal getWk17Msrp() {
        return wk17Msrp;
    }

    public void setWk17Msrp(BigDecimal wk17Msrp) {
        this.wk17Msrp = wk17Msrp;
    }

    public BigDecimal getWk17MsrpUSD() {
        return wk17MsrpUSD;
    }

    public void setWk17MsrpUSD(BigDecimal wk17MsrpUSD) {
        this.wk17MsrpUSD = wk17MsrpUSD;
    }

    public Integer getWk17Status() {
        return wk17Status;
    }

    public void setWk17Status(Integer wk17Status) {
        this.wk17Status = wk17Status;
    }

    public BigDecimal getWk18Msrp() {
        return wk18Msrp;
    }

    public void setWk18Msrp(BigDecimal wk18Msrp) {
        this.wk18Msrp = wk18Msrp;
    }

    public BigDecimal getWk18MsrpUSD() {
        return wk18MsrpUSD;
    }

    public void setWk18MsrpUSD(BigDecimal wk18MsrpUSD) {
        this.wk18MsrpUSD = wk18MsrpUSD;
    }

    public Integer getWk18Status() {
        return wk18Status;
    }

    public void setWk18Status(Integer wk18Status) {
        this.wk18Status = wk18Status;
    }

    public BigDecimal getWk19Msrp() {
        return wk19Msrp;
    }

    public void setWk19Msrp(BigDecimal wk19Msrp) {
        this.wk19Msrp = wk19Msrp;
    }

    public BigDecimal getWk19MsrpUSD() {
        return wk19MsrpUSD;
    }

    public void setWk19MsrpUSD(BigDecimal wk19MsrpUSD) {
        this.wk19MsrpUSD = wk19MsrpUSD;
    }

    public Integer getWk19Status() {
        return wk19Status;
    }

    public void setWk19Status(Integer wk19Status) {
        this.wk19Status = wk19Status;
    }

    public BigDecimal getWk20Msrp() {
        return wk20Msrp;
    }

    public void setWk20Msrp(BigDecimal wk20Msrp) {
        this.wk20Msrp = wk20Msrp;
    }

    public BigDecimal getWk20MsrpUSD() {
        return wk20MsrpUSD;
    }

    public void setWk20MsrpUSD(BigDecimal wk20MsrpUSD) {
        this.wk20MsrpUSD = wk20MsrpUSD;
    }

    public Integer getWk20Status() {
        return wk20Status;
    }

    public void setWk20Status(Integer wk20Status) {
        this.wk20Status = wk20Status;
    }

    public BigDecimal getWk21Msrp() {
        return wk21Msrp;
    }

    public void setWk21Msrp(BigDecimal wk21Msrp) {
        this.wk21Msrp = wk21Msrp;
    }

    public BigDecimal getWk21MsrpUSD() {
        return wk21MsrpUSD;
    }

    public void setWk21MsrpUSD(BigDecimal wk21MsrpUSD) {
        this.wk21MsrpUSD = wk21MsrpUSD;
    }

    public Integer getWk21Status() {
        return wk21Status;
    }

    public void setWk21Status(Integer wk21Status) {
        this.wk21Status = wk21Status;
    }

    public BigDecimal getWk22Msrp() {
        return wk22Msrp;
    }

    public void setWk22Msrp(BigDecimal wk22Msrp) {
        this.wk22Msrp = wk22Msrp;
    }

    public BigDecimal getWk22MsrpUSD() {
        return wk22MsrpUSD;
    }

    public void setWk22MsrpUSD(BigDecimal wk22MsrpUSD) {
        this.wk22MsrpUSD = wk22MsrpUSD;
    }

    public Integer getWk22Status() {
        return wk22Status;
    }

    public void setWk22Status(Integer wk22Status) {
        this.wk22Status = wk22Status;
    }

    public BigDecimal getWk23Msrp() {
        return wk23Msrp;
    }

    public void setWk23Msrp(BigDecimal wk23Msrp) {
        this.wk23Msrp = wk23Msrp;
    }

    public BigDecimal getWk23MsrpUSD() {
        return wk23MsrpUSD;
    }

    public void setWk23MsrpUSD(BigDecimal wk23MsrpUSD) {
        this.wk23MsrpUSD = wk23MsrpUSD;
    }

    public Integer getWk23Status() {
        return wk23Status;
    }

    public void setWk23Status(Integer wk23Status) {
        this.wk23Status = wk23Status;
    }

    public BigDecimal getWk24Msrp() {
        return wk24Msrp;
    }

    public void setWk24Msrp(BigDecimal wk24Msrp) {
        this.wk24Msrp = wk24Msrp;
    }

    public BigDecimal getWk24MsrpUSD() {
        return wk24MsrpUSD;
    }

    public void setWk24MsrpUSD(BigDecimal wk24MsrpUSD) {
        this.wk24MsrpUSD = wk24MsrpUSD;
    }

    public Integer getWk24Status() {
        return wk24Status;
    }

    public void setWk24Status(Integer wk24Status) {
        this.wk24Status = wk24Status;
    }

    public BigDecimal getWk25Msrp() {
        return wk25Msrp;
    }

    public void setWk25Msrp(BigDecimal wk25Msrp) {
        this.wk25Msrp = wk25Msrp;
    }

    public BigDecimal getWk25MsrpUSD() {
        return wk25MsrpUSD;
    }

    public void setWk25MsrpUSD(BigDecimal wk25MsrpUSD) {
        this.wk25MsrpUSD = wk25MsrpUSD;
    }

    public Integer getWk25Status() {
        return wk25Status;
    }

    public void setWk25Status(Integer wk25Status) {
        this.wk25Status = wk25Status;
    }

    public BigDecimal getWk26Msrp() {
        return wk26Msrp;
    }

    public void setWk26Msrp(BigDecimal wk26Msrp) {
        this.wk26Msrp = wk26Msrp;
    }

    public BigDecimal getWk26MsrpUSD() {
        return wk26MsrpUSD;
    }

    public void setWk26MsrpUSD(BigDecimal wk26MsrpUSD) {
        this.wk26MsrpUSD = wk26MsrpUSD;
    }

    public Integer getWk26Status() {
        return wk26Status;
    }

    public void setWk26Status(Integer wk26Status) {
        this.wk26Status = wk26Status;
    }

    public BigDecimal getWk27Msrp() {
        return wk27Msrp;
    }

    public void setWk27Msrp(BigDecimal wk27Msrp) {
        this.wk27Msrp = wk27Msrp;
    }

    public BigDecimal getWk27MsrpUSD() {
        return wk27MsrpUSD;
    }

    public void setWk27MsrpUSD(BigDecimal wk27MsrpUSD) {
        this.wk27MsrpUSD = wk27MsrpUSD;
    }

    public Integer getWk27Status() {
        return wk27Status;
    }

    public void setWk27Status(Integer wk27Status) {
        this.wk27Status = wk27Status;
    }

    public BigDecimal getWk28Msrp() {
        return wk28Msrp;
    }

    public void setWk28Msrp(BigDecimal wk28Msrp) {
        this.wk28Msrp = wk28Msrp;
    }

    public BigDecimal getWk28MsrpUSD() {
        return wk28MsrpUSD;
    }

    public void setWk28MsrpUSD(BigDecimal wk28MsrpUSD) {
        this.wk28MsrpUSD = wk28MsrpUSD;
    }

    public Integer getWk28Status() {
        return wk28Status;
    }

    public void setWk28Status(Integer wk28Status) {
        this.wk28Status = wk28Status;
    }

    public BigDecimal getWk29Msrp() {
        return wk29Msrp;
    }

    public void setWk29Msrp(BigDecimal wk29Msrp) {
        this.wk29Msrp = wk29Msrp;
    }

    public BigDecimal getWk29MsrpUSD() {
        return wk29MsrpUSD;
    }

    public void setWk29MsrpUSD(BigDecimal wk29MsrpUSD) {
        this.wk29MsrpUSD = wk29MsrpUSD;
    }

    public Integer getWk29Status() {
        return wk29Status;
    }

    public void setWk29Status(Integer wk29Status) {
        this.wk29Status = wk29Status;
    }

    public BigDecimal getWk30Msrp() {
        return wk30Msrp;
    }

    public void setWk30Msrp(BigDecimal wk30Msrp) {
        this.wk30Msrp = wk30Msrp;
    }

    public BigDecimal getWk30MsrpUSD() {
        return wk30MsrpUSD;
    }

    public void setWk30MsrpUSD(BigDecimal wk30MsrpUSD) {
        this.wk30MsrpUSD = wk30MsrpUSD;
    }

    public Integer getWk30Status() {
        return wk30Status;
    }

    public void setWk30Status(Integer wk30Status) {
        this.wk30Status = wk30Status;
    }

    public BigDecimal getWk31Msrp() {
        return wk31Msrp;
    }

    public void setWk31Msrp(BigDecimal wk31Msrp) {
        this.wk31Msrp = wk31Msrp;
    }

    public BigDecimal getWk31MsrpUSD() {
        return wk31MsrpUSD;
    }

    public void setWk31MsrpUSD(BigDecimal wk31MsrpUSD) {
        this.wk31MsrpUSD = wk31MsrpUSD;
    }

    public Integer getWk31Status() {
        return wk31Status;
    }

    public void setWk31Status(Integer wk31Status) {
        this.wk31Status = wk31Status;
    }

    public BigDecimal getWk32Msrp() {
        return wk32Msrp;
    }

    public void setWk32Msrp(BigDecimal wk32Msrp) {
        this.wk32Msrp = wk32Msrp;
    }

    public BigDecimal getWk32MsrpUSD() {
        return wk32MsrpUSD;
    }

    public void setWk32MsrpUSD(BigDecimal wk32MsrpUSD) {
        this.wk32MsrpUSD = wk32MsrpUSD;
    }

    public Integer getWk32Status() {
        return wk32Status;
    }

    public void setWk32Status(Integer wk32Status) {
        this.wk32Status = wk32Status;
    }

    public BigDecimal getWk33Msrp() {
        return wk33Msrp;
    }

    public void setWk33Msrp(BigDecimal wk33Msrp) {
        this.wk33Msrp = wk33Msrp;
    }

    public BigDecimal getWk33MsrpUSD() {
        return wk33MsrpUSD;
    }

    public void setWk33MsrpUSD(BigDecimal wk33MsrpUSD) {
        this.wk33MsrpUSD = wk33MsrpUSD;
    }

    public Integer getWk33Status() {
        return wk33Status;
    }

    public void setWk33Status(Integer wk33Status) {
        this.wk33Status = wk33Status;
    }

    public BigDecimal getWk34Msrp() {
        return wk34Msrp;
    }

    public void setWk34Msrp(BigDecimal wk34Msrp) {
        this.wk34Msrp = wk34Msrp;
    }

    public BigDecimal getWk34MsrpUSD() {
        return wk34MsrpUSD;
    }

    public void setWk34MsrpUSD(BigDecimal wk34MsrpUSD) {
        this.wk34MsrpUSD = wk34MsrpUSD;
    }

    public Integer getWk34Status() {
        return wk34Status;
    }

    public void setWk34Status(Integer wk34Status) {
        this.wk34Status = wk34Status;
    }

    public BigDecimal getWk35Msrp() {
        return wk35Msrp;
    }

    public void setWk35Msrp(BigDecimal wk35Msrp) {
        this.wk35Msrp = wk35Msrp;
    }

    public BigDecimal getWk35MsrpUSD() {
        return wk35MsrpUSD;
    }

    public void setWk35MsrpUSD(BigDecimal wk35MsrpUSD) {
        this.wk35MsrpUSD = wk35MsrpUSD;
    }

    public Integer getWk35Status() {
        return wk35Status;
    }

    public void setWk35Status(Integer wk35Status) {
        this.wk35Status = wk35Status;
    }

    public BigDecimal getWk36Msrp() {
        return wk36Msrp;
    }

    public void setWk36Msrp(BigDecimal wk36Msrp) {
        this.wk36Msrp = wk36Msrp;
    }

    public BigDecimal getWk36MsrpUSD() {
        return wk36MsrpUSD;
    }

    public void setWk36MsrpUSD(BigDecimal wk36MsrpUSD) {
        this.wk36MsrpUSD = wk36MsrpUSD;
    }

    public Integer getWk36Status() {
        return wk36Status;
    }

    public void setWk36Status(Integer wk36Status) {
        this.wk36Status = wk36Status;
    }

    public BigDecimal getWk37Msrp() {
        return wk37Msrp;
    }

    public void setWk37Msrp(BigDecimal wk37Msrp) {
        this.wk37Msrp = wk37Msrp;
    }

    public BigDecimal getWk37MsrpUSD() {
        return wk37MsrpUSD;
    }

    public void setWk37MsrpUSD(BigDecimal wk37MsrpUSD) {
        this.wk37MsrpUSD = wk37MsrpUSD;
    }

    public Integer getWk37Status() {
        return wk37Status;
    }

    public void setWk37Status(Integer wk37Status) {
        this.wk37Status = wk37Status;
    }

    public BigDecimal getWk38Msrp() {
        return wk38Msrp;
    }

    public void setWk38Msrp(BigDecimal wk38Msrp) {
        this.wk38Msrp = wk38Msrp;
    }

    public BigDecimal getWk38MsrpUSD() {
        return wk38MsrpUSD;
    }

    public void setWk38MsrpUSD(BigDecimal wk38MsrpUSD) {
        this.wk38MsrpUSD = wk38MsrpUSD;
    }

    public Integer getWk38Status() {
        return wk38Status;
    }

    public void setWk38Status(Integer wk38Status) {
        this.wk38Status = wk38Status;
    }

    public BigDecimal getWk39Msrp() {
        return wk39Msrp;
    }

    public void setWk39Msrp(BigDecimal wk39Msrp) {
        this.wk39Msrp = wk39Msrp;
    }

    public BigDecimal getWk39MsrpUSD() {
        return wk39MsrpUSD;
    }

    public void setWk39MsrpUSD(BigDecimal wk39MsrpUSD) {
        this.wk39MsrpUSD = wk39MsrpUSD;
    }

    public Integer getWk39Status() {
        return wk39Status;
    }

    public void setWk39Status(Integer wk39Status) {
        this.wk39Status = wk39Status;
    }

    public BigDecimal getWk40Msrp() {
        return wk40Msrp;
    }

    public void setWk40Msrp(BigDecimal wk40Msrp) {
        this.wk40Msrp = wk40Msrp;
    }

    public BigDecimal getWk40MsrpUSD() {
        return wk40MsrpUSD;
    }

    public void setWk40MsrpUSD(BigDecimal wk40MsrpUSD) {
        this.wk40MsrpUSD = wk40MsrpUSD;
    }

    public Integer getWk40Status() {
        return wk40Status;
    }

    public void setWk40Status(Integer wk40Status) {
        this.wk40Status = wk40Status;
    }

    public BigDecimal getWk41Msrp() {
        return wk41Msrp;
    }

    public void setWk41Msrp(BigDecimal wk41Msrp) {
        this.wk41Msrp = wk41Msrp;
    }

    public BigDecimal getWk41MsrpUSD() {
        return wk41MsrpUSD;
    }

    public void setWk41MsrpUSD(BigDecimal wk41MsrpUSD) {
        this.wk41MsrpUSD = wk41MsrpUSD;
    }

    public Integer getWk41Status() {
        return wk41Status;
    }

    public void setWk41Status(Integer wk41Status) {
        this.wk41Status = wk41Status;
    }

    public BigDecimal getWk42Msrp() {
        return wk42Msrp;
    }

    public void setWk42Msrp(BigDecimal wk42Msrp) {
        this.wk42Msrp = wk42Msrp;
    }

    public BigDecimal getWk42MsrpUSD() {
        return wk42MsrpUSD;
    }

    public void setWk42MsrpUSD(BigDecimal wk42MsrpUSD) {
        this.wk42MsrpUSD = wk42MsrpUSD;
    }

    public Integer getWk42Status() {
        return wk42Status;
    }

    public void setWk42Status(Integer wk42Status) {
        this.wk42Status = wk42Status;
    }

    public BigDecimal getWk43Msrp() {
        return wk43Msrp;
    }

    public void setWk43Msrp(BigDecimal wk43Msrp) {
        this.wk43Msrp = wk43Msrp;
    }

    public BigDecimal getWk43MsrpUSD() {
        return wk43MsrpUSD;
    }

    public void setWk43MsrpUSD(BigDecimal wk43MsrpUSD) {
        this.wk43MsrpUSD = wk43MsrpUSD;
    }

    public Integer getWk43Status() {
        return wk43Status;
    }

    public void setWk43Status(Integer wk43Status) {
        this.wk43Status = wk43Status;
    }

    public BigDecimal getWk44Msrp() {
        return wk44Msrp;
    }

    public void setWk44Msrp(BigDecimal wk44Msrp) {
        this.wk44Msrp = wk44Msrp;
    }

    public BigDecimal getWk44MsrpUSD() {
        return wk44MsrpUSD;
    }

    public void setWk44MsrpUSD(BigDecimal wk44MsrpUSD) {
        this.wk44MsrpUSD = wk44MsrpUSD;
    }

    public Integer getWk44Status() {
        return wk44Status;
    }

    public void setWk44Status(Integer wk44Status) {
        this.wk44Status = wk44Status;
    }

    public BigDecimal getWk45Msrp() {
        return wk45Msrp;
    }

    public void setWk45Msrp(BigDecimal wk45Msrp) {
        this.wk45Msrp = wk45Msrp;
    }

    public BigDecimal getWk45MsrpUSD() {
        return wk45MsrpUSD;
    }

    public void setWk45MsrpUSD(BigDecimal wk45MsrpUSD) {
        this.wk45MsrpUSD = wk45MsrpUSD;
    }

    public Integer getWk45Status() {
        return wk45Status;
    }

    public void setWk45Status(Integer wk45Status) {
        this.wk45Status = wk45Status;
    }

    public BigDecimal getWk46Msrp() {
        return wk46Msrp;
    }

    public void setWk46Msrp(BigDecimal wk46Msrp) {
        this.wk46Msrp = wk46Msrp;
    }

    public BigDecimal getWk46MsrpUSD() {
        return wk46MsrpUSD;
    }

    public void setWk46MsrpUSD(BigDecimal wk46MsrpUSD) {
        this.wk46MsrpUSD = wk46MsrpUSD;
    }

    public Integer getWk46Status() {
        return wk46Status;
    }

    public void setWk46Status(Integer wk46Status) {
        this.wk46Status = wk46Status;
    }

    public BigDecimal getWk47Msrp() {
        return wk47Msrp;
    }

    public void setWk47Msrp(BigDecimal wk47Msrp) {
        this.wk47Msrp = wk47Msrp;
    }

    public BigDecimal getWk47MsrpUSD() {
        return wk47MsrpUSD;
    }

    public void setWk47MsrpUSD(BigDecimal wk47MsrpUSD) {
        this.wk47MsrpUSD = wk47MsrpUSD;
    }

    public Integer getWk47Status() {
        return wk47Status;
    }

    public void setWk47Status(Integer wk47Status) {
        this.wk47Status = wk47Status;
    }

    public BigDecimal getWk48Msrp() {
        return wk48Msrp;
    }

    public void setWk48Msrp(BigDecimal wk48Msrp) {
        this.wk48Msrp = wk48Msrp;
    }

    public BigDecimal getWk48MsrpUSD() {
        return wk48MsrpUSD;
    }

    public void setWk48MsrpUSD(BigDecimal wk48MsrpUSD) {
        this.wk48MsrpUSD = wk48MsrpUSD;
    }

    public Integer getWk48Status() {
        return wk48Status;
    }

    public void setWk48Status(Integer wk48Status) {
        this.wk48Status = wk48Status;
    }

    public BigDecimal getWk49Msrp() {
        return wk49Msrp;
    }

    public void setWk49Msrp(BigDecimal wk49Msrp) {
        this.wk49Msrp = wk49Msrp;
    }

    public BigDecimal getWk49MsrpUSD() {
        return wk49MsrpUSD;
    }

    public void setWk49MsrpUSD(BigDecimal wk49MsrpUSD) {
        this.wk49MsrpUSD = wk49MsrpUSD;
    }

    public Integer getWk49Status() {
        return wk49Status;
    }

    public void setWk49Status(Integer wk49Status) {
        this.wk49Status = wk49Status;
    }

    public BigDecimal getWk50Msrp() {
        return wk50Msrp;
    }

    public void setWk50Msrp(BigDecimal wk50Msrp) {
        this.wk50Msrp = wk50Msrp;
    }

    public BigDecimal getWk50MsrpUSD() {
        return wk50MsrpUSD;
    }

    public void setWk50MsrpUSD(BigDecimal wk50MsrpUSD) {
        this.wk50MsrpUSD = wk50MsrpUSD;
    }

    public Integer getWk50Status() {
        return wk50Status;
    }

    public void setWk50Status(Integer wk50Status) {
        this.wk50Status = wk50Status;
    }

    public BigDecimal getWk51Msrp() {
        return wk51Msrp;
    }

    public void setWk51Msrp(BigDecimal wk51Msrp) {
        this.wk51Msrp = wk51Msrp;
    }

    public BigDecimal getWk51MsrpUSD() {
        return wk51MsrpUSD;
    }

    public void setWk51MsrpUSD(BigDecimal wk51MsrpUSD) {
        this.wk51MsrpUSD = wk51MsrpUSD;
    }

    public Integer getWk51Status() {
        return wk51Status;
    }

    public void setWk51Status(Integer wk51Status) {
        this.wk51Status = wk51Status;
    }

    public BigDecimal getWk52Msrp() {
        return wk52Msrp;
    }

    public void setWk52Msrp(BigDecimal wk52Msrp) {
        this.wk52Msrp = wk52Msrp;
    }

    public BigDecimal getWk52MsrpUSD() {
        return wk52MsrpUSD;
    }

    public void setWk52MsrpUSD(BigDecimal wk52MsrpUSD) {
        this.wk52MsrpUSD = wk52MsrpUSD;
    }

    public Integer getWk52Status() {
        return wk52Status;
    }

    public void setWk52Status(Integer wk52Status) {
        this.wk52Status = wk52Status;
    }

    public char getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(char delFlag) {
        this.delFlag = delFlag;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getWk29Day1() {
        return wk29Day1;
    }

    public void setWk29Day1(BigDecimal wk29Day1) {
        this.wk29Day1 = wk29Day1;
    }

    public Integer getWk29Day1Status() {
        return wk29Day1Status;
    }

    public void setWk29Day1Status(Integer wk29Day1Status) {
        this.wk29Day1Status = wk29Day1Status;
    }

    public BigDecimal getWk29Day2() {
        return wk29Day2;
    }

    public void setWk29Day2(BigDecimal wk29Day2) {
        this.wk29Day2 = wk29Day2;
    }

    public Integer getWk29Day2Status() {
        return wk29Day2Status;
    }

    public void setWk29Day2Status(Integer wk29Day2Status) {
        this.wk29Day2Status = wk29Day2Status;
    }

    public BigDecimal getWk29Day3() {
        return wk29Day3;
    }

    public void setWk29Day3(BigDecimal wk29Day3) {
        this.wk29Day3 = wk29Day3;
    }

    public Integer getWk29Day3Status() {
        return wk29Day3Status;
    }

    public void setWk29Day3Status(Integer wk29Day3Status) {
        this.wk29Day3Status = wk29Day3Status;
    }

    public BigDecimal getWk29Day4() {
        return wk29Day4;
    }

    public void setWk29Day4(BigDecimal wk29Day4) {
        this.wk29Day4 = wk29Day4;
    }

    public Integer getWk29Day4Status() {
        return wk29Day4Status;
    }

    public void setWk29Day4Status(Integer wk29Day4Status) {
        this.wk29Day4Status = wk29Day4Status;
    }

    public BigDecimal getWk29Day5() {
        return wk29Day5;
    }

    public void setWk29Day5(BigDecimal wk29Day5) {
        this.wk29Day5 = wk29Day5;
    }

    public Integer getWk29Day5Status() {
        return wk29Day5Status;
    }

    public void setWk29Day5Status(Integer wk29Day5Status) {
        this.wk29Day5Status = wk29Day5Status;
    }

    public BigDecimal getWk29Day6() {
        return wk29Day6;
    }

    public void setWk29Day6(BigDecimal wk29Day6) {
        this.wk29Day6 = wk29Day6;
    }

    public Integer getWk29Day6Status() {
        return wk29Day6Status;
    }

    public void setWk29Day6Status(Integer wk29Day6Status) {
        this.wk29Day6Status = wk29Day6Status;
    }

    public BigDecimal getWk29Day7() {
        return wk29Day7;
    }

    public void setWk29Day7(BigDecimal wk29Day7) {
        this.wk29Day7 = wk29Day7;
    }

    public Integer getWk29Day7Status() {
        return wk29Day7Status;
    }

    public void setWk29Day7Status(Integer wk29Day7Status) {
        this.wk29Day7Status = wk29Day7Status;
    }

    public BigDecimal getWk1Day1() {
        return wk1Day1;
    }

    public void setWk1Day1(BigDecimal wk1Day1) {
        this.wk1Day1 = wk1Day1;
    }

    public Integer getWk1Day1Status() {
        return wk1Day1Status;
    }

    public void setWk1Day1Status(Integer wk1Day1Status) {
        this.wk1Day1Status = wk1Day1Status;
    }

    public BigDecimal getWk1Day2() {
        return wk1Day2;
    }

    public void setWk1Day2(BigDecimal wk1Day2) {
        this.wk1Day2 = wk1Day2;
    }

    public Integer getWk1Day2Status() {
        return wk1Day2Status;
    }

    public void setWk1Day2Status(Integer wk1Day2Status) {
        this.wk1Day2Status = wk1Day2Status;
    }

    public BigDecimal getWk1Day3() {
        return wk1Day3;
    }

    public void setWk1Day3(BigDecimal wk1Day3) {
        this.wk1Day3 = wk1Day3;
    }

    public Integer getWk1Day3Status() {
        return wk1Day3Status;
    }

    public void setWk1Day3Status(Integer wk1Day3Status) {
        this.wk1Day3Status = wk1Day3Status;
    }

    public BigDecimal getWk1Day4() {
        return wk1Day4;
    }

    public void setWk1Day4(BigDecimal wk1Day4) {
        this.wk1Day4 = wk1Day4;
    }

    public Integer getWk1Day4Status() {
        return wk1Day4Status;
    }

    public void setWk1Day4Status(Integer wk1Day4Status) {
        this.wk1Day4Status = wk1Day4Status;
    }

    public BigDecimal getWk1Day5() {
        return wk1Day5;
    }

    public void setWk1Day5(BigDecimal wk1Day5) {
        this.wk1Day5 = wk1Day5;
    }

    public Integer getWk1Day5Status() {
        return wk1Day5Status;
    }

    public void setWk1Day5Status(Integer wk1Day5Status) {
        this.wk1Day5Status = wk1Day5Status;
    }

    public BigDecimal getWk1Day6() {
        return wk1Day6;
    }

    public void setWk1Day6(BigDecimal wk1Day6) {
        this.wk1Day6 = wk1Day6;
    }

    public Integer getWk1Day6Status() {
        return wk1Day6Status;
    }

    public void setWk1Day6Status(Integer wk1Day6Status) {
        this.wk1Day6Status = wk1Day6Status;
    }

    public BigDecimal getWk1Day7() {
        return wk1Day7;
    }

    public void setWk1Day7(BigDecimal wk1Day7) {
        this.wk1Day7 = wk1Day7;
    }

    public Integer getWk1Day7Status() {
        return wk1Day7Status;
    }

    public void setWk1Day7Status(Integer wk1Day7Status) {
        this.wk1Day7Status = wk1Day7Status;
    }

    public BigDecimal getWk2Day1() {
        return wk2Day1;
    }

    public void setWk2Day1(BigDecimal wk2Day1) {
        this.wk2Day1 = wk2Day1;
    }

    public Integer getWk2Day1Status() {
        return wk2Day1Status;
    }

    public void setWk2Day1Status(Integer wk2Day1Status) {
        this.wk2Day1Status = wk2Day1Status;
    }

    public BigDecimal getWk2Day2() {
        return wk2Day2;
    }

    public void setWk2Day2(BigDecimal wk2Day2) {
        this.wk2Day2 = wk2Day2;
    }

    public Integer getWk2Day2Status() {
        return wk2Day2Status;
    }

    public void setWk2Day2Status(Integer wk2Day2Status) {
        this.wk2Day2Status = wk2Day2Status;
    }

    public BigDecimal getWk2Day3() {
        return wk2Day3;
    }

    public void setWk2Day3(BigDecimal wk2Day3) {
        this.wk2Day3 = wk2Day3;
    }

    public Integer getWk2Day3Status() {
        return wk2Day3Status;
    }

    public void setWk2Day3Status(Integer wk2Day3Status) {
        this.wk2Day3Status = wk2Day3Status;
    }

    public BigDecimal getWk2Day4() {
        return wk2Day4;
    }

    public void setWk2Day4(BigDecimal wk2Day4) {
        this.wk2Day4 = wk2Day4;
    }

    public Integer getWk2Day4Status() {
        return wk2Day4Status;
    }

    public void setWk2Day4Status(Integer wk2Day4Status) {
        this.wk2Day4Status = wk2Day4Status;
    }

    public BigDecimal getWk2Day5() {
        return wk2Day5;
    }

    public void setWk2Day5(BigDecimal wk2Day5) {
        this.wk2Day5 = wk2Day5;
    }

    public Integer getWk2Day5Status() {
        return wk2Day5Status;
    }

    public void setWk2Day5Status(Integer wk2Day5Status) {
        this.wk2Day5Status = wk2Day5Status;
    }

    public BigDecimal getWk2Day6() {
        return wk2Day6;
    }

    public void setWk2Day6(BigDecimal wk2Day6) {
        this.wk2Day6 = wk2Day6;
    }

    public Integer getWk2Day6Status() {
        return wk2Day6Status;
    }

    public void setWk2Day6Status(Integer wk2Day6Status) {
        this.wk2Day6Status = wk2Day6Status;
    }

    public BigDecimal getWk2Day7() {
        return wk2Day7;
    }

    public void setWk2Day7(BigDecimal wk2Day7) {
        this.wk2Day7 = wk2Day7;
    }

    public Integer getWk2Day7Status() {
        return wk2Day7Status;
    }

    public void setWk2Day7Status(Integer wk2Day7Status) {
        this.wk2Day7Status = wk2Day7Status;
    }

    public BigDecimal getWk3Day1() {
        return wk3Day1;
    }

    public void setWk3Day1(BigDecimal wk3Day1) {
        this.wk3Day1 = wk3Day1;
    }

    public Integer getWk3Day1Status() {
        return wk3Day1Status;
    }

    public void setWk3Day1Status(Integer wk3Day1Status) {
        this.wk3Day1Status = wk3Day1Status;
    }

    public BigDecimal getWk3Day2() {
        return wk3Day2;
    }

    public void setWk3Day2(BigDecimal wk3Day2) {
        this.wk3Day2 = wk3Day2;
    }

    public Integer getWk3Day2Status() {
        return wk3Day2Status;
    }

    public void setWk3Day2Status(Integer wk3Day2Status) {
        this.wk3Day2Status = wk3Day2Status;
    }

    public BigDecimal getWk3Day3() {
        return wk3Day3;
    }

    public void setWk3Day3(BigDecimal wk3Day3) {
        this.wk3Day3 = wk3Day3;
    }

    public Integer getWk3Day3Status() {
        return wk3Day3Status;
    }

    public void setWk3Day3Status(Integer wk3Day3Status) {
        this.wk3Day3Status = wk3Day3Status;
    }

    public BigDecimal getWk3Day4() {
        return wk3Day4;
    }

    public void setWk3Day4(BigDecimal wk3Day4) {
        this.wk3Day4 = wk3Day4;
    }

    public Integer getWk3Day4Status() {
        return wk3Day4Status;
    }

    public void setWk3Day4Status(Integer wk3Day4Status) {
        this.wk3Day4Status = wk3Day4Status;
    }

    public BigDecimal getWk3Day5() {
        return wk3Day5;
    }

    public void setWk3Day5(BigDecimal wk3Day5) {
        this.wk3Day5 = wk3Day5;
    }

    public Integer getWk3Day5Status() {
        return wk3Day5Status;
    }

    public void setWk3Day5Status(Integer wk3Day5Status) {
        this.wk3Day5Status = wk3Day5Status;
    }

    public BigDecimal getWk3Day6() {
        return wk3Day6;
    }

    public void setWk3Day6(BigDecimal wk3Day6) {
        this.wk3Day6 = wk3Day6;
    }

    public Integer getWk3Day6Status() {
        return wk3Day6Status;
    }

    public void setWk3Day6Status(Integer wk3Day6Status) {
        this.wk3Day6Status = wk3Day6Status;
    }

    public BigDecimal getWk3Day7() {
        return wk3Day7;
    }

    public void setWk3Day7(BigDecimal wk3Day7) {
        this.wk3Day7 = wk3Day7;
    }

    public Integer getWk3Day7Status() {
        return wk3Day7Status;
    }

    public void setWk3Day7Status(Integer wk3Day7Status) {
        this.wk3Day7Status = wk3Day7Status;
    }

    public BigDecimal getWk4Day1() {
        return wk4Day1;
    }

    public void setWk4Day1(BigDecimal wk4Day1) {
        this.wk4Day1 = wk4Day1;
    }

    public Integer getWk4Day1Status() {
        return wk4Day1Status;
    }

    public void setWk4Day1Status(Integer wk4Day1Status) {
        this.wk4Day1Status = wk4Day1Status;
    }

    public BigDecimal getWk4Day2() {
        return wk4Day2;
    }

    public void setWk4Day2(BigDecimal wk4Day2) {
        this.wk4Day2 = wk4Day2;
    }

    public Integer getWk4Day2Status() {
        return wk4Day2Status;
    }

    public void setWk4Day2Status(Integer wk4Day2Status) {
        this.wk4Day2Status = wk4Day2Status;
    }

    public BigDecimal getWk4Day3() {
        return wk4Day3;
    }

    public void setWk4Day3(BigDecimal wk4Day3) {
        this.wk4Day3 = wk4Day3;
    }

    public Integer getWk4Day3Status() {
        return wk4Day3Status;
    }

    public void setWk4Day3Status(Integer wk4Day3Status) {
        this.wk4Day3Status = wk4Day3Status;
    }

    public BigDecimal getWk4Day4() {
        return wk4Day4;
    }

    public void setWk4Day4(BigDecimal wk4Day4) {
        this.wk4Day4 = wk4Day4;
    }

    public Integer getWk4Day4Status() {
        return wk4Day4Status;
    }

    public void setWk4Day4Status(Integer wk4Day4Status) {
        this.wk4Day4Status = wk4Day4Status;
    }

    public BigDecimal getWk4Day5() {
        return wk4Day5;
    }

    public void setWk4Day5(BigDecimal wk4Day5) {
        this.wk4Day5 = wk4Day5;
    }

    public Integer getWk4Day5Status() {
        return wk4Day5Status;
    }

    public void setWk4Day5Status(Integer wk4Day5Status) {
        this.wk4Day5Status = wk4Day5Status;
    }

    public BigDecimal getWk4Day6() {
        return wk4Day6;
    }

    public void setWk4Day6(BigDecimal wk4Day6) {
        this.wk4Day6 = wk4Day6;
    }

    public Integer getWk4Day6Status() {
        return wk4Day6Status;
    }

    public void setWk4Day6Status(Integer wk4Day6Status) {
        this.wk4Day6Status = wk4Day6Status;
    }

    public BigDecimal getWk4Day7() {
        return wk4Day7;
    }

    public void setWk4Day7(BigDecimal wk4Day7) {
        this.wk4Day7 = wk4Day7;
    }

    public Integer getWk4Day7Status() {
        return wk4Day7Status;
    }

    public void setWk4Day7Status(Integer wk4Day7Status) {
        this.wk4Day7Status = wk4Day7Status;
    }

    public BigDecimal getWk52Day1() {
        return wk52Day1;
    }

    public void setWk52Day1(BigDecimal wk52Day1) {
        this.wk52Day1 = wk52Day1;
    }

    public Integer getWk52Day1Status() {
        return wk52Day1Status;
    }

    public void setWk52Day1Status(Integer wk52Day1Status) {
        this.wk52Day1Status = wk52Day1Status;
    }

    public BigDecimal getWk52Day2() {
        return wk52Day2;
    }

    public void setWk52Day2(BigDecimal wk52Day2) {
        this.wk52Day2 = wk52Day2;
    }

    public Integer getWk52Day2Status() {
        return wk52Day2Status;
    }

    public void setWk52Day2Status(Integer wk52Day2Status) {
        this.wk52Day2Status = wk52Day2Status;
    }

    public BigDecimal getWk52Day3() {
        return wk52Day3;
    }

    public void setWk52Day3(BigDecimal wk52Day3) {
        this.wk52Day3 = wk52Day3;
    }

    public Integer getWk52Day3Status() {
        return wk52Day3Status;
    }

    public void setWk52Day3Status(Integer wk52Day3Status) {
        this.wk52Day3Status = wk52Day3Status;
    }

    public BigDecimal getWk52Day4() {
        return wk52Day4;
    }

    public void setWk52Day4(BigDecimal wk52Day4) {
        this.wk52Day4 = wk52Day4;
    }

    public Integer getWk52Day4Status() {
        return wk52Day4Status;
    }

    public void setWk52Day4Status(Integer wk52Day4Status) {
        this.wk52Day4Status = wk52Day4Status;
    }

    public BigDecimal getWk52Day5() {
        return wk52Day5;
    }

    public void setWk52Day5(BigDecimal wk52Day5) {
        this.wk52Day5 = wk52Day5;
    }

    public Integer getWk52Day5Status() {
        return wk52Day5Status;
    }

    public void setWk52Day5Status(Integer wk52Day5Status) {
        this.wk52Day5Status = wk52Day5Status;
    }

    public BigDecimal getWk52Day6() {
        return wk52Day6;
    }

    public void setWk52Day6(BigDecimal wk52Day6) {
        this.wk52Day6 = wk52Day6;
    }

    public Integer getWk52Day6Status() {
        return wk52Day6Status;
    }

    public void setWk52Day6Status(Integer wk52Day6Status) {
        this.wk52Day6Status = wk52Day6Status;
    }

    public BigDecimal getWk52Day7() {
        return wk52Day7;
    }

    public void setWk52Day7(BigDecimal wk52Day7) {
        this.wk52Day7 = wk52Day7;
    }

    public Integer getWk52Day7Status() {
        return wk52Day7Status;
    }

    public void setWk52Day7Status(Integer wk52Day7Status) {
        this.wk52Day7Status = wk52Day7Status;
    }

    public BigDecimal getWk51Day1() {
        return wk51Day1;
    }

    public void setWk51Day1(BigDecimal wk51Day1) {
        this.wk51Day1 = wk51Day1;
    }

    public Integer getWk51Day1Status() {
        return wk51Day1Status;
    }

    public void setWk51Day1Status(Integer wk51Day1Status) {
        this.wk51Day1Status = wk51Day1Status;
    }

    public BigDecimal getWk51Day2() {
        return wk51Day2;
    }

    public void setWk51Day2(BigDecimal wk51Day2) {
        this.wk51Day2 = wk51Day2;
    }

    public Integer getWk51Day2Status() {
        return wk51Day2Status;
    }

    public void setWk51Day2Status(Integer wk51Day2Status) {
        this.wk51Day2Status = wk51Day2Status;
    }

    public BigDecimal getWk51Day3() {
        return wk51Day3;
    }

    public void setWk51Day3(BigDecimal wk51Day3) {
        this.wk51Day3 = wk51Day3;
    }

    public Integer getWk51Day3Status() {
        return wk51Day3Status;
    }

    public void setWk51Day3Status(Integer wk51Day3Status) {
        this.wk51Day3Status = wk51Day3Status;
    }

    public BigDecimal getWk51Day4() {
        return wk51Day4;
    }

    public void setWk51Day4(BigDecimal wk51Day4) {
        this.wk51Day4 = wk51Day4;
    }

    public Integer getWk51Day4Status() {
        return wk51Day4Status;
    }

    public void setWk51Day4Status(Integer wk51Day4Status) {
        this.wk51Day4Status = wk51Day4Status;
    }

    public BigDecimal getWk51Day5() {
        return wk51Day5;
    }

    public void setWk51Day5(BigDecimal wk51Day5) {
        this.wk51Day5 = wk51Day5;
    }

    public Integer getWk51Day5Status() {
        return wk51Day5Status;
    }

    public void setWk51Day5Status(Integer wk51Day5Status) {
        this.wk51Day5Status = wk51Day5Status;
    }

    public BigDecimal getWk51Day6() {
        return wk51Day6;
    }

    public void setWk51Day6(BigDecimal wk51Day6) {
        this.wk51Day6 = wk51Day6;
    }

    public Integer getWk51Day6Status() {
        return wk51Day6Status;
    }

    public void setWk51Day6Status(Integer wk51Day6Status) {
        this.wk51Day6Status = wk51Day6Status;
    }

    public BigDecimal getWk51Day7() {
        return wk51Day7;
    }

    public void setWk51Day7(BigDecimal wk51Day7) {
        this.wk51Day7 = wk51Day7;
    }

    public Integer getWk51Day7Status() {
        return wk51Day7Status;
    }

    public void setWk51Day7Status(Integer wk51Day7Status) {
        this.wk51Day7Status = wk51Day7Status;
    }

    public BigDecimal getWk50Day1() {
        return wk50Day1;
    }

    public void setWk50Day1(BigDecimal wk50Day1) {
        this.wk50Day1 = wk50Day1;
    }

    public Integer getWk50Day1Status() {
        return wk50Day1Status;
    }

    public void setWk50Day1Status(Integer wk50Day1Status) {
        this.wk50Day1Status = wk50Day1Status;
    }

    public BigDecimal getWk50Day2() {
        return wk50Day2;
    }

    public void setWk50Day2(BigDecimal wk50Day2) {
        this.wk50Day2 = wk50Day2;
    }

    public Integer getWk50Day2Status() {
        return wk50Day2Status;
    }

    public void setWk50Day2Status(Integer wk50Day2Status) {
        this.wk50Day2Status = wk50Day2Status;
    }

    public BigDecimal getWk50Day3() {
        return wk50Day3;
    }

    public void setWk50Day3(BigDecimal wk50Day3) {
        this.wk50Day3 = wk50Day3;
    }

    public Integer getWk50Day3Status() {
        return wk50Day3Status;
    }

    public void setWk50Day3Status(Integer wk50Day3Status) {
        this.wk50Day3Status = wk50Day3Status;
    }

    public BigDecimal getWk50Day4() {
        return wk50Day4;
    }

    public void setWk50Day4(BigDecimal wk50Day4) {
        this.wk50Day4 = wk50Day4;
    }

    public Integer getWk50Day4Status() {
        return wk50Day4Status;
    }

    public void setWk50Day4Status(Integer wk50Day4Status) {
        this.wk50Day4Status = wk50Day4Status;
    }

    public BigDecimal getWk50Day5() {
        return wk50Day5;
    }

    public void setWk50Day5(BigDecimal wk50Day5) {
        this.wk50Day5 = wk50Day5;
    }

    public Integer getWk50Day5Status() {
        return wk50Day5Status;
    }

    public void setWk50Day5Status(Integer wk50Day5Status) {
        this.wk50Day5Status = wk50Day5Status;
    }

    public BigDecimal getWk50Day6() {
        return wk50Day6;
    }

    public void setWk50Day6(BigDecimal wk50Day6) {
        this.wk50Day6 = wk50Day6;
    }

    public Integer getWk50Day6Status() {
        return wk50Day6Status;
    }

    public void setWk50Day6Status(Integer wk50Day6Status) {
        this.wk50Day6Status = wk50Day6Status;
    }

    public BigDecimal getWk50Day7() {
        return wk50Day7;
    }

    public void setWk50Day7(BigDecimal wk50Day7) {
        this.wk50Day7 = wk50Day7;
    }

    public Integer getWk50Day7Status() {
        return wk50Day7Status;
    }

    public void setWk50Day7Status(Integer wk50Day7Status) {
        this.wk50Day7Status = wk50Day7Status;
    }

    public BigDecimal getWk49Day1() {
        return wk49Day1;
    }

    public void setWk49Day1(BigDecimal wk49Day1) {
        this.wk49Day1 = wk49Day1;
    }

    public Integer getWk49Day1Status() {
        return wk49Day1Status;
    }

    public void setWk49Day1Status(Integer wk49Day1Status) {
        this.wk49Day1Status = wk49Day1Status;
    }

    public BigDecimal getWk49Day2() {
        return wk49Day2;
    }

    public void setWk49Day2(BigDecimal wk49Day2) {
        this.wk49Day2 = wk49Day2;
    }

    public Integer getWk49Day2Status() {
        return wk49Day2Status;
    }

    public void setWk49Day2Status(Integer wk49Day2Status) {
        this.wk49Day2Status = wk49Day2Status;
    }

    public BigDecimal getWk49Day3() {
        return wk49Day3;
    }

    public void setWk49Day3(BigDecimal wk49Day3) {
        this.wk49Day3 = wk49Day3;
    }

    public Integer getWk49Day3Status() {
        return wk49Day3Status;
    }

    public void setWk49Day3Status(Integer wk49Day3Status) {
        this.wk49Day3Status = wk49Day3Status;
    }

    public BigDecimal getWk49Day4() {
        return wk49Day4;
    }

    public void setWk49Day4(BigDecimal wk49Day4) {
        this.wk49Day4 = wk49Day4;
    }

    public Integer getWk49Day4Status() {
        return wk49Day4Status;
    }

    public void setWk49Day4Status(Integer wk49Day4Status) {
        this.wk49Day4Status = wk49Day4Status;
    }

    public BigDecimal getWk49Day5() {
        return wk49Day5;
    }

    public void setWk49Day5(BigDecimal wk49Day5) {
        this.wk49Day5 = wk49Day5;
    }

    public Integer getWk49Day5Status() {
        return wk49Day5Status;
    }

    public void setWk49Day5Status(Integer wk49Day5Status) {
        this.wk49Day5Status = wk49Day5Status;
    }

    public BigDecimal getWk49Day6() {
        return wk49Day6;
    }

    public void setWk49Day6(BigDecimal wk49Day6) {
        this.wk49Day6 = wk49Day6;
    }

    public Integer getWk49Day6Status() {
        return wk49Day6Status;
    }

    public void setWk49Day6Status(Integer wk49Day6Status) {
        this.wk49Day6Status = wk49Day6Status;
    }

    public BigDecimal getWk49Day7() {
        return wk49Day7;
    }

    public void setWk49Day7(BigDecimal wk49Day7) {
        this.wk49Day7 = wk49Day7;
    }

    public Integer getWk49Day7Status() {
        return wk49Day7Status;
    }

    public void setWk49Day7Status(Integer wk49Day7Status) {
        this.wk49Day7Status = wk49Day7Status;
    }

    public BigDecimal getWk48Day1() {
        return wk48Day1;
    }

    public void setWk48Day1(BigDecimal wk48Day1) {
        this.wk48Day1 = wk48Day1;
    }

    public Integer getWk48Day1Status() {
        return wk48Day1Status;
    }

    public void setWk48Day1Status(Integer wk48Day1Status) {
        this.wk48Day1Status = wk48Day1Status;
    }

    public BigDecimal getWk48Day2() {
        return wk48Day2;
    }

    public void setWk48Day2(BigDecimal wk48Day2) {
        this.wk48Day2 = wk48Day2;
    }

    public Integer getWk48Day2Status() {
        return wk48Day2Status;
    }

    public void setWk48Day2Status(Integer wk48Day2Status) {
        this.wk48Day2Status = wk48Day2Status;
    }

    public BigDecimal getWk48Day3() {
        return wk48Day3;
    }

    public void setWk48Day3(BigDecimal wk48Day3) {
        this.wk48Day3 = wk48Day3;
    }

    public Integer getWk48Day3Status() {
        return wk48Day3Status;
    }

    public void setWk48Day3Status(Integer wk48Day3Status) {
        this.wk48Day3Status = wk48Day3Status;
    }

    public BigDecimal getWk48Day4() {
        return wk48Day4;
    }

    public void setWk48Day4(BigDecimal wk48Day4) {
        this.wk48Day4 = wk48Day4;
    }

    public Integer getWk48Day4Status() {
        return wk48Day4Status;
    }

    public void setWk48Day4Status(Integer wk48Day4Status) {
        this.wk48Day4Status = wk48Day4Status;
    }

    public BigDecimal getWk48Day5() {
        return wk48Day5;
    }

    public void setWk48Day5(BigDecimal wk48Day5) {
        this.wk48Day5 = wk48Day5;
    }

    public Integer getWk48Day5Status() {
        return wk48Day5Status;
    }

    public void setWk48Day5Status(Integer wk48Day5Status) {
        this.wk48Day5Status = wk48Day5Status;
    }

    public BigDecimal getWk48Day6() {
        return wk48Day6;
    }

    public void setWk48Day6(BigDecimal wk48Day6) {
        this.wk48Day6 = wk48Day6;
    }

    public Integer getWk48Day6Status() {
        return wk48Day6Status;
    }

    public void setWk48Day6Status(Integer wk48Day6Status) {
        this.wk48Day6Status = wk48Day6Status;
    }

    public BigDecimal getWk48Day7() {
        return wk48Day7;
    }

    public void setWk48Day7(BigDecimal wk48Day7) {
        this.wk48Day7 = wk48Day7;
    }

    public Integer getWk48Day7Status() {
        return wk48Day7Status;
    }

    public void setWk48Day7Status(Integer wk48Day7Status) {
        this.wk48Day7Status = wk48Day7Status;
    }

    public BigDecimal getWk47Day1() {
        return wk47Day1;
    }

    public void setWk47Day1(BigDecimal wk47Day1) {
        this.wk47Day1 = wk47Day1;
    }

    public Integer getWk47Day1Status() {
        return wk47Day1Status;
    }

    public void setWk47Day1Status(Integer wk47Day1Status) {
        this.wk47Day1Status = wk47Day1Status;
    }

    public BigDecimal getWk47Day2() {
        return wk47Day2;
    }

    public void setWk47Day2(BigDecimal wk47Day2) {
        this.wk47Day2 = wk47Day2;
    }

    public Integer getWk47Day2Status() {
        return wk47Day2Status;
    }

    public void setWk47Day2Status(Integer wk47Day2Status) {
        this.wk47Day2Status = wk47Day2Status;
    }

    public BigDecimal getWk47Day3() {
        return wk47Day3;
    }

    public void setWk47Day3(BigDecimal wk47Day3) {
        this.wk47Day3 = wk47Day3;
    }

    public Integer getWk47Day3Status() {
        return wk47Day3Status;
    }

    public void setWk47Day3Status(Integer wk47Day3Status) {
        this.wk47Day3Status = wk47Day3Status;
    }

    public BigDecimal getWk47Day4() {
        return wk47Day4;
    }

    public void setWk47Day4(BigDecimal wk47Day4) {
        this.wk47Day4 = wk47Day4;
    }

    public Integer getWk47Day4Status() {
        return wk47Day4Status;
    }

    public void setWk47Day4Status(Integer wk47Day4Status) {
        this.wk47Day4Status = wk47Day4Status;
    }

    public BigDecimal getWk47Day5() {
        return wk47Day5;
    }

    public void setWk47Day5(BigDecimal wk47Day5) {
        this.wk47Day5 = wk47Day5;
    }

    public Integer getWk47Day5Status() {
        return wk47Day5Status;
    }

    public void setWk47Day5Status(Integer wk47Day5Status) {
        this.wk47Day5Status = wk47Day5Status;
    }

    public BigDecimal getWk47Day6() {
        return wk47Day6;
    }

    public void setWk47Day6(BigDecimal wk47Day6) {
        this.wk47Day6 = wk47Day6;
    }

    public Integer getWk47Day6Status() {
        return wk47Day6Status;
    }

    public void setWk47Day6Status(Integer wk47Day6Status) {
        this.wk47Day6Status = wk47Day6Status;
    }

    public BigDecimal getWk47Day7() {
        return wk47Day7;
    }

    public void setWk47Day7(BigDecimal wk47Day7) {
        this.wk47Day7 = wk47Day7;
    }

    public Integer getWk47Day7Status() {
        return wk47Day7Status;
    }

    public void setWk47Day7Status(Integer wk47Day7Status) {
        this.wk47Day7Status = wk47Day7Status;
    }

    public BigDecimal getWk46Day1() {
        return wk46Day1;
    }

    public void setWk46Day1(BigDecimal wk46Day1) {
        this.wk46Day1 = wk46Day1;
    }

    public Integer getWk46Day1Status() {
        return wk46Day1Status;
    }

    public void setWk46Day1Status(Integer wk46Day1Status) {
        this.wk46Day1Status = wk46Day1Status;
    }

    public BigDecimal getWk46Day2() {
        return wk46Day2;
    }

    public void setWk46Day2(BigDecimal wk46Day2) {
        this.wk46Day2 = wk46Day2;
    }

    public Integer getWk46Day2Status() {
        return wk46Day2Status;
    }

    public void setWk46Day2Status(Integer wk46Day2Status) {
        this.wk46Day2Status = wk46Day2Status;
    }

    public BigDecimal getWk46Day3() {
        return wk46Day3;
    }

    public void setWk46Day3(BigDecimal wk46Day3) {
        this.wk46Day3 = wk46Day3;
    }

    public Integer getWk46Day3Status() {
        return wk46Day3Status;
    }

    public void setWk46Day3Status(Integer wk46Day3Status) {
        this.wk46Day3Status = wk46Day3Status;
    }

    public BigDecimal getWk46Day4() {
        return wk46Day4;
    }

    public void setWk46Day4(BigDecimal wk46Day4) {
        this.wk46Day4 = wk46Day4;
    }

    public Integer getWk46Day4Status() {
        return wk46Day4Status;
    }

    public void setWk46Day4Status(Integer wk46Day4Status) {
        this.wk46Day4Status = wk46Day4Status;
    }

    public BigDecimal getWk46Day5() {
        return wk46Day5;
    }

    public void setWk46Day5(BigDecimal wk46Day5) {
        this.wk46Day5 = wk46Day5;
    }

    public Integer getWk46Day5Status() {
        return wk46Day5Status;
    }

    public void setWk46Day5Status(Integer wk46Day5Status) {
        this.wk46Day5Status = wk46Day5Status;
    }

    public BigDecimal getWk46Day6() {
        return wk46Day6;
    }

    public void setWk46Day6(BigDecimal wk46Day6) {
        this.wk46Day6 = wk46Day6;
    }

    public Integer getWk46Day6Status() {
        return wk46Day6Status;
    }

    public void setWk46Day6Status(Integer wk46Day6Status) {
        this.wk46Day6Status = wk46Day6Status;
    }

    public BigDecimal getWk46Day7() {
        return wk46Day7;
    }

    public void setWk46Day7(BigDecimal wk46Day7) {
        this.wk46Day7 = wk46Day7;
    }

    public Integer getWk46Day7Status() {
        return wk46Day7Status;
    }

    public void setWk46Day7Status(Integer wk46Day7Status) {
        this.wk46Day7Status = wk46Day7Status;
    }

    public BigDecimal getWk45Day1() {
        return wk45Day1;
    }

    public void setWk45Day1(BigDecimal wk45Day1) {
        this.wk45Day1 = wk45Day1;
    }

    public Integer getWk45Day1Status() {
        return wk45Day1Status;
    }

    public void setWk45Day1Status(Integer wk45Day1Status) {
        this.wk45Day1Status = wk45Day1Status;
    }

    public BigDecimal getWk45Day2() {
        return wk45Day2;
    }

    public void setWk45Day2(BigDecimal wk45Day2) {
        this.wk45Day2 = wk45Day2;
    }

    public Integer getWk45Day2Status() {
        return wk45Day2Status;
    }

    public void setWk45Day2Status(Integer wk45Day2Status) {
        this.wk45Day2Status = wk45Day2Status;
    }

    public BigDecimal getWk45Day3() {
        return wk45Day3;
    }

    public void setWk45Day3(BigDecimal wk45Day3) {
        this.wk45Day3 = wk45Day3;
    }

    public Integer getWk45Day3Status() {
        return wk45Day3Status;
    }

    public void setWk45Day3Status(Integer wk45Day3Status) {
        this.wk45Day3Status = wk45Day3Status;
    }

    public BigDecimal getWk45Day4() {
        return wk45Day4;
    }

    public void setWk45Day4(BigDecimal wk45Day4) {
        this.wk45Day4 = wk45Day4;
    }

    public Integer getWk45Day4Status() {
        return wk45Day4Status;
    }

    public void setWk45Day4Status(Integer wk45Day4Status) {
        this.wk45Day4Status = wk45Day4Status;
    }

    public BigDecimal getWk45Day5() {
        return wk45Day5;
    }

    public void setWk45Day5(BigDecimal wk45Day5) {
        this.wk45Day5 = wk45Day5;
    }

    public Integer getWk45Day5Status() {
        return wk45Day5Status;
    }

    public void setWk45Day5Status(Integer wk45Day5Status) {
        this.wk45Day5Status = wk45Day5Status;
    }

    public BigDecimal getWk45Day6() {
        return wk45Day6;
    }

    public void setWk45Day6(BigDecimal wk45Day6) {
        this.wk45Day6 = wk45Day6;
    }

    public Integer getWk45Day6Status() {
        return wk45Day6Status;
    }

    public void setWk45Day6Status(Integer wk45Day6Status) {
        this.wk45Day6Status = wk45Day6Status;
    }

    public BigDecimal getWk45Day7() {
        return wk45Day7;
    }

    public void setWk45Day7(BigDecimal wk45Day7) {
        this.wk45Day7 = wk45Day7;
    }

    public Integer getWk45Day7Status() {
        return wk45Day7Status;
    }

    public void setWk45Day7Status(Integer wk45Day7Status) {
        this.wk45Day7Status = wk45Day7Status;
    }

    public BigDecimal getWk44Day1() {
        return wk44Day1;
    }

    public void setWk44Day1(BigDecimal wk44Day1) {
        this.wk44Day1 = wk44Day1;
    }

    public Integer getWk44Day1Status() {
        return wk44Day1Status;
    }

    public void setWk44Day1Status(Integer wk44Day1Status) {
        this.wk44Day1Status = wk44Day1Status;
    }

    public BigDecimal getWk44Day2() {
        return wk44Day2;
    }

    public void setWk44Day2(BigDecimal wk44Day2) {
        this.wk44Day2 = wk44Day2;
    }

    public Integer getWk44Day2Status() {
        return wk44Day2Status;
    }

    public void setWk44Day2Status(Integer wk44Day2Status) {
        this.wk44Day2Status = wk44Day2Status;
    }

    public BigDecimal getWk44Day3() {
        return wk44Day3;
    }

    public void setWk44Day3(BigDecimal wk44Day3) {
        this.wk44Day3 = wk44Day3;
    }

    public Integer getWk44Day3Status() {
        return wk44Day3Status;
    }

    public void setWk44Day3Status(Integer wk44Day3Status) {
        this.wk44Day3Status = wk44Day3Status;
    }

    public BigDecimal getWk44Day4() {
        return wk44Day4;
    }

    public void setWk44Day4(BigDecimal wk44Day4) {
        this.wk44Day4 = wk44Day4;
    }

    public Integer getWk44Day4Status() {
        return wk44Day4Status;
    }

    public void setWk44Day4Status(Integer wk44Day4Status) {
        this.wk44Day4Status = wk44Day4Status;
    }

    public BigDecimal getWk44Day5() {
        return wk44Day5;
    }

    public void setWk44Day5(BigDecimal wk44Day5) {
        this.wk44Day5 = wk44Day5;
    }

    public Integer getWk44Day5Status() {
        return wk44Day5Status;
    }

    public void setWk44Day5Status(Integer wk44Day5Status) {
        this.wk44Day5Status = wk44Day5Status;
    }

    public BigDecimal getWk44Day6() {
        return wk44Day6;
    }

    public void setWk44Day6(BigDecimal wk44Day6) {
        this.wk44Day6 = wk44Day6;
    }

    public Integer getWk44Day6Status() {
        return wk44Day6Status;
    }

    public void setWk44Day6Status(Integer wk44Day6Status) {
        this.wk44Day6Status = wk44Day6Status;
    }

    public BigDecimal getWk44Day7() {
        return wk44Day7;
    }

    public void setWk44Day7(BigDecimal wk44Day7) {
        this.wk44Day7 = wk44Day7;
    }

    public Integer getWk44Day7Status() {
        return wk44Day7Status;
    }

    public void setWk44Day7Status(Integer wk44Day7Status) {
        this.wk44Day7Status = wk44Day7Status;
    }

    public BigDecimal getWk43Day1() {
        return wk43Day1;
    }

    public void setWk43Day1(BigDecimal wk43Day1) {
        this.wk43Day1 = wk43Day1;
    }

    public Integer getWk43Day1Status() {
        return wk43Day1Status;
    }

    public void setWk43Day1Status(Integer wk43Day1Status) {
        this.wk43Day1Status = wk43Day1Status;
    }

    public BigDecimal getWk43Day2() {
        return wk43Day2;
    }

    public void setWk43Day2(BigDecimal wk43Day2) {
        this.wk43Day2 = wk43Day2;
    }

    public Integer getWk43Day2Status() {
        return wk43Day2Status;
    }

    public void setWk43Day2Status(Integer wk43Day2Status) {
        this.wk43Day2Status = wk43Day2Status;
    }

    public BigDecimal getWk43Day3() {
        return wk43Day3;
    }

    public void setWk43Day3(BigDecimal wk43Day3) {
        this.wk43Day3 = wk43Day3;
    }

    public Integer getWk43Day3Status() {
        return wk43Day3Status;
    }

    public void setWk43Day3Status(Integer wk43Day3Status) {
        this.wk43Day3Status = wk43Day3Status;
    }

    public BigDecimal getWk43Day4() {
        return wk43Day4;
    }

    public void setWk43Day4(BigDecimal wk43Day4) {
        this.wk43Day4 = wk43Day4;
    }

    public Integer getWk43Day4Status() {
        return wk43Day4Status;
    }

    public void setWk43Day4Status(Integer wk43Day4Status) {
        this.wk43Day4Status = wk43Day4Status;
    }

    public BigDecimal getWk43Day5() {
        return wk43Day5;
    }

    public void setWk43Day5(BigDecimal wk43Day5) {
        this.wk43Day5 = wk43Day5;
    }

    public Integer getWk43Day5Status() {
        return wk43Day5Status;
    }

    public void setWk43Day5Status(Integer wk43Day5Status) {
        this.wk43Day5Status = wk43Day5Status;
    }

    public BigDecimal getWk43Day6() {
        return wk43Day6;
    }

    public void setWk43Day6(BigDecimal wk43Day6) {
        this.wk43Day6 = wk43Day6;
    }

    public Integer getWk43Day6Status() {
        return wk43Day6Status;
    }

    public void setWk43Day6Status(Integer wk43Day6Status) {
        this.wk43Day6Status = wk43Day6Status;
    }

    public BigDecimal getWk43Day7() {
        return wk43Day7;
    }

    public void setWk43Day7(BigDecimal wk43Day7) {
        this.wk43Day7 = wk43Day7;
    }

    public Integer getWk43Day7Status() {
        return wk43Day7Status;
    }

    public void setWk43Day7Status(Integer wk43Day7Status) {
        this.wk43Day7Status = wk43Day7Status;
    }

    public BigDecimal getWk42Day1() {
        return wk42Day1;
    }

    public void setWk42Day1(BigDecimal wk42Day1) {
        this.wk42Day1 = wk42Day1;
    }

    public Integer getWk42Day1Status() {
        return wk42Day1Status;
    }

    public void setWk42Day1Status(Integer wk42Day1Status) {
        this.wk42Day1Status = wk42Day1Status;
    }

    public BigDecimal getWk42Day2() {
        return wk42Day2;
    }

    public void setWk42Day2(BigDecimal wk42Day2) {
        this.wk42Day2 = wk42Day2;
    }

    public Integer getWk42Day2Status() {
        return wk42Day2Status;
    }

    public void setWk42Day2Status(Integer wk42Day2Status) {
        this.wk42Day2Status = wk42Day2Status;
    }

    public BigDecimal getWk42Day3() {
        return wk42Day3;
    }

    public void setWk42Day3(BigDecimal wk42Day3) {
        this.wk42Day3 = wk42Day3;
    }

    public Integer getWk42Day3Status() {
        return wk42Day3Status;
    }

    public void setWk42Day3Status(Integer wk42Day3Status) {
        this.wk42Day3Status = wk42Day3Status;
    }

    public BigDecimal getWk42Day4() {
        return wk42Day4;
    }

    public void setWk42Day4(BigDecimal wk42Day4) {
        this.wk42Day4 = wk42Day4;
    }

    public Integer getWk42Day4Status() {
        return wk42Day4Status;
    }

    public void setWk42Day4Status(Integer wk42Day4Status) {
        this.wk42Day4Status = wk42Day4Status;
    }

    public BigDecimal getWk42Day5() {
        return wk42Day5;
    }

    public void setWk42Day5(BigDecimal wk42Day5) {
        this.wk42Day5 = wk42Day5;
    }

    public Integer getWk42Day5Status() {
        return wk42Day5Status;
    }

    public void setWk42Day5Status(Integer wk42Day5Status) {
        this.wk42Day5Status = wk42Day5Status;
    }

    public BigDecimal getWk42Day6() {
        return wk42Day6;
    }

    public void setWk42Day6(BigDecimal wk42Day6) {
        this.wk42Day6 = wk42Day6;
    }

    public Integer getWk42Day6Status() {
        return wk42Day6Status;
    }

    public void setWk42Day6Status(Integer wk42Day6Status) {
        this.wk42Day6Status = wk42Day6Status;
    }

    public BigDecimal getWk42Day7() {
        return wk42Day7;
    }

    public void setWk42Day7(BigDecimal wk42Day7) {
        this.wk42Day7 = wk42Day7;
    }

    public Integer getWk42Day7Status() {
        return wk42Day7Status;
    }

    public void setWk42Day7Status(Integer wk42Day7Status) {
        this.wk42Day7Status = wk42Day7Status;
    }

    public BigDecimal getWk41Day1() {
        return wk41Day1;
    }

    public void setWk41Day1(BigDecimal wk41Day1) {
        this.wk41Day1 = wk41Day1;
    }

    public Integer getWk41Day1Status() {
        return wk41Day1Status;
    }

    public void setWk41Day1Status(Integer wk41Day1Status) {
        this.wk41Day1Status = wk41Day1Status;
    }

    public BigDecimal getWk41Day2() {
        return wk41Day2;
    }

    public void setWk41Day2(BigDecimal wk41Day2) {
        this.wk41Day2 = wk41Day2;
    }

    public Integer getWk41Day2Status() {
        return wk41Day2Status;
    }

    public void setWk41Day2Status(Integer wk41Day2Status) {
        this.wk41Day2Status = wk41Day2Status;
    }

    public BigDecimal getWk41Day3() {
        return wk41Day3;
    }

    public void setWk41Day3(BigDecimal wk41Day3) {
        this.wk41Day3 = wk41Day3;
    }

    public Integer getWk41Day3Status() {
        return wk41Day3Status;
    }

    public void setWk41Day3Status(Integer wk41Day3Status) {
        this.wk41Day3Status = wk41Day3Status;
    }

    public BigDecimal getWk41Day4() {
        return wk41Day4;
    }

    public void setWk41Day4(BigDecimal wk41Day4) {
        this.wk41Day4 = wk41Day4;
    }

    public Integer getWk41Day4Status() {
        return wk41Day4Status;
    }

    public void setWk41Day4Status(Integer wk41Day4Status) {
        this.wk41Day4Status = wk41Day4Status;
    }

    public BigDecimal getWk41Day5() {
        return wk41Day5;
    }

    public void setWk41Day5(BigDecimal wk41Day5) {
        this.wk41Day5 = wk41Day5;
    }

    public Integer getWk41Day5Status() {
        return wk41Day5Status;
    }

    public void setWk41Day5Status(Integer wk41Day5Status) {
        this.wk41Day5Status = wk41Day5Status;
    }

    public BigDecimal getWk41Day6() {
        return wk41Day6;
    }

    public void setWk41Day6(BigDecimal wk41Day6) {
        this.wk41Day6 = wk41Day6;
    }

    public Integer getWk41Day6Status() {
        return wk41Day6Status;
    }

    public void setWk41Day6Status(Integer wk41Day6Status) {
        this.wk41Day6Status = wk41Day6Status;
    }

    public BigDecimal getWk41Day7() {
        return wk41Day7;
    }

    public void setWk41Day7(BigDecimal wk41Day7) {
        this.wk41Day7 = wk41Day7;
    }

    public Integer getWk41Day7Status() {
        return wk41Day7Status;
    }

    public void setWk41Day7Status(Integer wk41Day7Status) {
        this.wk41Day7Status = wk41Day7Status;
    }

    public BigDecimal getWk40Day1() {
        return wk40Day1;
    }

    public void setWk40Day1(BigDecimal wk40Day1) {
        this.wk40Day1 = wk40Day1;
    }

    public Integer getWk40Day1Status() {
        return wk40Day1Status;
    }

    public void setWk40Day1Status(Integer wk40Day1Status) {
        this.wk40Day1Status = wk40Day1Status;
    }

    public BigDecimal getWk40Day2() {
        return wk40Day2;
    }

    public void setWk40Day2(BigDecimal wk40Day2) {
        this.wk40Day2 = wk40Day2;
    }

    public Integer getWk40Day2Status() {
        return wk40Day2Status;
    }

    public void setWk40Day2Status(Integer wk40Day2Status) {
        this.wk40Day2Status = wk40Day2Status;
    }

    public BigDecimal getWk40Day3() {
        return wk40Day3;
    }

    public void setWk40Day3(BigDecimal wk40Day3) {
        this.wk40Day3 = wk40Day3;
    }

    public Integer getWk40Day3Status() {
        return wk40Day3Status;
    }

    public void setWk40Day3Status(Integer wk40Day3Status) {
        this.wk40Day3Status = wk40Day3Status;
    }

    public BigDecimal getWk40Day4() {
        return wk40Day4;
    }

    public void setWk40Day4(BigDecimal wk40Day4) {
        this.wk40Day4 = wk40Day4;
    }

    public Integer getWk40Day4Status() {
        return wk40Day4Status;
    }

    public void setWk40Day4Status(Integer wk40Day4Status) {
        this.wk40Day4Status = wk40Day4Status;
    }

    public BigDecimal getWk40Day5() {
        return wk40Day5;
    }

    public void setWk40Day5(BigDecimal wk40Day5) {
        this.wk40Day5 = wk40Day5;
    }

    public Integer getWk40Day5Status() {
        return wk40Day5Status;
    }

    public void setWk40Day5Status(Integer wk40Day5Status) {
        this.wk40Day5Status = wk40Day5Status;
    }

    public BigDecimal getWk40Day6() {
        return wk40Day6;
    }

    public void setWk40Day6(BigDecimal wk40Day6) {
        this.wk40Day6 = wk40Day6;
    }

    public Integer getWk40Day6Status() {
        return wk40Day6Status;
    }

    public void setWk40Day6Status(Integer wk40Day6Status) {
        this.wk40Day6Status = wk40Day6Status;
    }

    public BigDecimal getWk40Day7() {
        return wk40Day7;
    }

    public void setWk40Day7(BigDecimal wk40Day7) {
        this.wk40Day7 = wk40Day7;
    }

    public Integer getWk40Day7Status() {
        return wk40Day7Status;
    }

    public void setWk40Day7Status(Integer wk40Day7Status) {
        this.wk40Day7Status = wk40Day7Status;
    }

    public BigDecimal getWk39Day1() {
        return wk39Day1;
    }

    public void setWk39Day1(BigDecimal wk39Day1) {
        this.wk39Day1 = wk39Day1;
    }

    public Integer getWk39Day1Status() {
        return wk39Day1Status;
    }

    public void setWk39Day1Status(Integer wk39Day1Status) {
        this.wk39Day1Status = wk39Day1Status;
    }

    public BigDecimal getWk39Day2() {
        return wk39Day2;
    }

    public void setWk39Day2(BigDecimal wk39Day2) {
        this.wk39Day2 = wk39Day2;
    }

    public Integer getWk39Day2Status() {
        return wk39Day2Status;
    }

    public void setWk39Day2Status(Integer wk39Day2Status) {
        this.wk39Day2Status = wk39Day2Status;
    }

    public BigDecimal getWk39Day3() {
        return wk39Day3;
    }

    public void setWk39Day3(BigDecimal wk39Day3) {
        this.wk39Day3 = wk39Day3;
    }

    public Integer getWk39Day3Status() {
        return wk39Day3Status;
    }

    public void setWk39Day3Status(Integer wk39Day3Status) {
        this.wk39Day3Status = wk39Day3Status;
    }

    public BigDecimal getWk39Day4() {
        return wk39Day4;
    }

    public void setWk39Day4(BigDecimal wk39Day4) {
        this.wk39Day4 = wk39Day4;
    }

    public Integer getWk39Day4Status() {
        return wk39Day4Status;
    }

    public void setWk39Day4Status(Integer wk39Day4Status) {
        this.wk39Day4Status = wk39Day4Status;
    }

    public BigDecimal getWk39Day5() {
        return wk39Day5;
    }

    public void setWk39Day5(BigDecimal wk39Day5) {
        this.wk39Day5 = wk39Day5;
    }

    public Integer getWk39Day5Status() {
        return wk39Day5Status;
    }

    public void setWk39Day5Status(Integer wk39Day5Status) {
        this.wk39Day5Status = wk39Day5Status;
    }

    public BigDecimal getWk39Day6() {
        return wk39Day6;
    }

    public void setWk39Day6(BigDecimal wk39Day6) {
        this.wk39Day6 = wk39Day6;
    }

    public Integer getWk39Day6Status() {
        return wk39Day6Status;
    }

    public void setWk39Day6Status(Integer wk39Day6Status) {
        this.wk39Day6Status = wk39Day6Status;
    }

    public BigDecimal getWk39Day7() {
        return wk39Day7;
    }

    public void setWk39Day7(BigDecimal wk39Day7) {
        this.wk39Day7 = wk39Day7;
    }

    public Integer getWk39Day7Status() {
        return wk39Day7Status;
    }

    public void setWk39Day7Status(Integer wk39Day7Status) {
        this.wk39Day7Status = wk39Day7Status;
    }

    public BigDecimal getWk38Day1() {
        return wk38Day1;
    }

    public void setWk38Day1(BigDecimal wk38Day1) {
        this.wk38Day1 = wk38Day1;
    }

    public Integer getWk38Day1Status() {
        return wk38Day1Status;
    }

    public void setWk38Day1Status(Integer wk38Day1Status) {
        this.wk38Day1Status = wk38Day1Status;
    }

    public BigDecimal getWk38Day2() {
        return wk38Day2;
    }

    public void setWk38Day2(BigDecimal wk38Day2) {
        this.wk38Day2 = wk38Day2;
    }

    public Integer getWk38Day2Status() {
        return wk38Day2Status;
    }

    public void setWk38Day2Status(Integer wk38Day2Status) {
        this.wk38Day2Status = wk38Day2Status;
    }

    public BigDecimal getWk38Day3() {
        return wk38Day3;
    }

    public void setWk38Day3(BigDecimal wk38Day3) {
        this.wk38Day3 = wk38Day3;
    }

    public Integer getWk38Day3Status() {
        return wk38Day3Status;
    }

    public void setWk38Day3Status(Integer wk38Day3Status) {
        this.wk38Day3Status = wk38Day3Status;
    }

    public BigDecimal getWk38Day4() {
        return wk38Day4;
    }

    public void setWk38Day4(BigDecimal wk38Day4) {
        this.wk38Day4 = wk38Day4;
    }

    public Integer getWk38Day4Status() {
        return wk38Day4Status;
    }

    public void setWk38Day4Status(Integer wk38Day4Status) {
        this.wk38Day4Status = wk38Day4Status;
    }

    public BigDecimal getWk38Day5() {
        return wk38Day5;
    }

    public void setWk38Day5(BigDecimal wk38Day5) {
        this.wk38Day5 = wk38Day5;
    }

    public Integer getWk38Day5Status() {
        return wk38Day5Status;
    }

    public void setWk38Day5Status(Integer wk38Day5Status) {
        this.wk38Day5Status = wk38Day5Status;
    }

    public BigDecimal getWk38Day6() {
        return wk38Day6;
    }

    public void setWk38Day6(BigDecimal wk38Day6) {
        this.wk38Day6 = wk38Day6;
    }

    public Integer getWk38Day6Status() {
        return wk38Day6Status;
    }

    public void setWk38Day6Status(Integer wk38Day6Status) {
        this.wk38Day6Status = wk38Day6Status;
    }

    public BigDecimal getWk38Day7() {
        return wk38Day7;
    }

    public void setWk38Day7(BigDecimal wk38Day7) {
        this.wk38Day7 = wk38Day7;
    }

    public Integer getWk38Day7Status() {
        return wk38Day7Status;
    }

    public void setWk38Day7Status(Integer wk38Day7Status) {
        this.wk38Day7Status = wk38Day7Status;
    }

    public BigDecimal getWk37Day1() {
        return wk37Day1;
    }

    public void setWk37Day1(BigDecimal wk37Day1) {
        this.wk37Day1 = wk37Day1;
    }

    public Integer getWk37Day1Status() {
        return wk37Day1Status;
    }

    public void setWk37Day1Status(Integer wk37Day1Status) {
        this.wk37Day1Status = wk37Day1Status;
    }

    public BigDecimal getWk37Day2() {
        return wk37Day2;
    }

    public void setWk37Day2(BigDecimal wk37Day2) {
        this.wk37Day2 = wk37Day2;
    }

    public Integer getWk37Day2Status() {
        return wk37Day2Status;
    }

    public void setWk37Day2Status(Integer wk37Day2Status) {
        this.wk37Day2Status = wk37Day2Status;
    }

    public BigDecimal getWk37Day3() {
        return wk37Day3;
    }

    public void setWk37Day3(BigDecimal wk37Day3) {
        this.wk37Day3 = wk37Day3;
    }

    public Integer getWk37Day3Status() {
        return wk37Day3Status;
    }

    public void setWk37Day3Status(Integer wk37Day3Status) {
        this.wk37Day3Status = wk37Day3Status;
    }

    public BigDecimal getWk37Day4() {
        return wk37Day4;
    }

    public void setWk37Day4(BigDecimal wk37Day4) {
        this.wk37Day4 = wk37Day4;
    }

    public Integer getWk37Day4Status() {
        return wk37Day4Status;
    }

    public void setWk37Day4Status(Integer wk37Day4Status) {
        this.wk37Day4Status = wk37Day4Status;
    }

    public BigDecimal getWk37Day5() {
        return wk37Day5;
    }

    public void setWk37Day5(BigDecimal wk37Day5) {
        this.wk37Day5 = wk37Day5;
    }

    public Integer getWk37Day5Status() {
        return wk37Day5Status;
    }

    public void setWk37Day5Status(Integer wk37Day5Status) {
        this.wk37Day5Status = wk37Day5Status;
    }

    public BigDecimal getWk37Day6() {
        return wk37Day6;
    }

    public void setWk37Day6(BigDecimal wk37Day6) {
        this.wk37Day6 = wk37Day6;
    }

    public Integer getWk37Day6Status() {
        return wk37Day6Status;
    }

    public void setWk37Day6Status(Integer wk37Day6Status) {
        this.wk37Day6Status = wk37Day6Status;
    }

    public BigDecimal getWk37Day7() {
        return wk37Day7;
    }

    public void setWk37Day7(BigDecimal wk37Day7) {
        this.wk37Day7 = wk37Day7;
    }

    public Integer getWk37Day7Status() {
        return wk37Day7Status;
    }

    public void setWk37Day7Status(Integer wk37Day7Status) {
        this.wk37Day7Status = wk37Day7Status;
    }

    public BigDecimal getWk36Day1() {
        return wk36Day1;
    }

    public void setWk36Day1(BigDecimal wk36Day1) {
        this.wk36Day1 = wk36Day1;
    }

    public Integer getWk36Day1Status() {
        return wk36Day1Status;
    }

    public void setWk36Day1Status(Integer wk36Day1Status) {
        this.wk36Day1Status = wk36Day1Status;
    }

    public BigDecimal getWk36Day2() {
        return wk36Day2;
    }

    public void setWk36Day2(BigDecimal wk36Day2) {
        this.wk36Day2 = wk36Day2;
    }

    public Integer getWk36Day2Status() {
        return wk36Day2Status;
    }

    public void setWk36Day2Status(Integer wk36Day2Status) {
        this.wk36Day2Status = wk36Day2Status;
    }

    public BigDecimal getWk36Day3() {
        return wk36Day3;
    }

    public void setWk36Day3(BigDecimal wk36Day3) {
        this.wk36Day3 = wk36Day3;
    }

    public Integer getWk36Day3Status() {
        return wk36Day3Status;
    }

    public void setWk36Day3Status(Integer wk36Day3Status) {
        this.wk36Day3Status = wk36Day3Status;
    }

    public BigDecimal getWk36Day4() {
        return wk36Day4;
    }

    public void setWk36Day4(BigDecimal wk36Day4) {
        this.wk36Day4 = wk36Day4;
    }

    public Integer getWk36Day4Status() {
        return wk36Day4Status;
    }

    public void setWk36Day4Status(Integer wk36Day4Status) {
        this.wk36Day4Status = wk36Day4Status;
    }

    public BigDecimal getWk36Day5() {
        return wk36Day5;
    }

    public void setWk36Day5(BigDecimal wk36Day5) {
        this.wk36Day5 = wk36Day5;
    }

    public Integer getWk36Day5Status() {
        return wk36Day5Status;
    }

    public void setWk36Day5Status(Integer wk36Day5Status) {
        this.wk36Day5Status = wk36Day5Status;
    }

    public BigDecimal getWk36Day6() {
        return wk36Day6;
    }

    public void setWk36Day6(BigDecimal wk36Day6) {
        this.wk36Day6 = wk36Day6;
    }

    public Integer getWk36Day6Status() {
        return wk36Day6Status;
    }

    public void setWk36Day6Status(Integer wk36Day6Status) {
        this.wk36Day6Status = wk36Day6Status;
    }

    public BigDecimal getWk36Day7() {
        return wk36Day7;
    }

    public void setWk36Day7(BigDecimal wk36Day7) {
        this.wk36Day7 = wk36Day7;
    }

    public Integer getWk36Day7Status() {
        return wk36Day7Status;
    }

    public void setWk36Day7Status(Integer wk36Day7Status) {
        this.wk36Day7Status = wk36Day7Status;
    }

    public BigDecimal getWk35Day1() {
        return wk35Day1;
    }

    public void setWk35Day1(BigDecimal wk35Day1) {
        this.wk35Day1 = wk35Day1;
    }

    public Integer getWk35Day1Status() {
        return wk35Day1Status;
    }

    public void setWk35Day1Status(Integer wk35Day1Status) {
        this.wk35Day1Status = wk35Day1Status;
    }

    public BigDecimal getWk35Day2() {
        return wk35Day2;
    }

    public void setWk35Day2(BigDecimal wk35Day2) {
        this.wk35Day2 = wk35Day2;
    }

    public Integer getWk35Day2Status() {
        return wk35Day2Status;
    }

    public void setWk35Day2Status(Integer wk35Day2Status) {
        this.wk35Day2Status = wk35Day2Status;
    }

    public BigDecimal getWk35Day3() {
        return wk35Day3;
    }

    public void setWk35Day3(BigDecimal wk35Day3) {
        this.wk35Day3 = wk35Day3;
    }

    public Integer getWk35Day3Status() {
        return wk35Day3Status;
    }

    public void setWk35Day3Status(Integer wk35Day3Status) {
        this.wk35Day3Status = wk35Day3Status;
    }

    public BigDecimal getWk35Day4() {
        return wk35Day4;
    }

    public void setWk35Day4(BigDecimal wk35Day4) {
        this.wk35Day4 = wk35Day4;
    }

    public Integer getWk35Day4Status() {
        return wk35Day4Status;
    }

    public void setWk35Day4Status(Integer wk35Day4Status) {
        this.wk35Day4Status = wk35Day4Status;
    }

    public BigDecimal getWk35Day5() {
        return wk35Day5;
    }

    public void setWk35Day5(BigDecimal wk35Day5) {
        this.wk35Day5 = wk35Day5;
    }

    public Integer getWk35Day5Status() {
        return wk35Day5Status;
    }

    public void setWk35Day5Status(Integer wk35Day5Status) {
        this.wk35Day5Status = wk35Day5Status;
    }

    public BigDecimal getWk35Day6() {
        return wk35Day6;
    }

    public void setWk35Day6(BigDecimal wk35Day6) {
        this.wk35Day6 = wk35Day6;
    }

    public Integer getWk35Day6Status() {
        return wk35Day6Status;
    }

    public void setWk35Day6Status(Integer wk35Day6Status) {
        this.wk35Day6Status = wk35Day6Status;
    }

    public BigDecimal getWk35Day7() {
        return wk35Day7;
    }

    public void setWk35Day7(BigDecimal wk35Day7) {
        this.wk35Day7 = wk35Day7;
    }

    public Integer getWk35Day7Status() {
        return wk35Day7Status;
    }

    public void setWk35Day7Status(Integer wk35Day7Status) {
        this.wk35Day7Status = wk35Day7Status;
    }

    public BigDecimal getWk34Day1() {
        return wk34Day1;
    }

    public void setWk34Day1(BigDecimal wk34Day1) {
        this.wk34Day1 = wk34Day1;
    }

    public Integer getWk34Day1Status() {
        return wk34Day1Status;
    }

    public void setWk34Day1Status(Integer wk34Day1Status) {
        this.wk34Day1Status = wk34Day1Status;
    }

    public BigDecimal getWk34Day2() {
        return wk34Day2;
    }

    public void setWk34Day2(BigDecimal wk34Day2) {
        this.wk34Day2 = wk34Day2;
    }

    public Integer getWk34Day2Status() {
        return wk34Day2Status;
    }

    public void setWk34Day2Status(Integer wk34Day2Status) {
        this.wk34Day2Status = wk34Day2Status;
    }

    public BigDecimal getWk34Day3() {
        return wk34Day3;
    }

    public void setWk34Day3(BigDecimal wk34Day3) {
        this.wk34Day3 = wk34Day3;
    }

    public Integer getWk34Day3Status() {
        return wk34Day3Status;
    }

    public void setWk34Day3Status(Integer wk34Day3Status) {
        this.wk34Day3Status = wk34Day3Status;
    }

    public BigDecimal getWk34Day4() {
        return wk34Day4;
    }

    public void setWk34Day4(BigDecimal wk34Day4) {
        this.wk34Day4 = wk34Day4;
    }

    public Integer getWk34Day4Status() {
        return wk34Day4Status;
    }

    public void setWk34Day4Status(Integer wk34Day4Status) {
        this.wk34Day4Status = wk34Day4Status;
    }

    public BigDecimal getWk34Day5() {
        return wk34Day5;
    }

    public void setWk34Day5(BigDecimal wk34Day5) {
        this.wk34Day5 = wk34Day5;
    }

    public Integer getWk34Day5Status() {
        return wk34Day5Status;
    }

    public void setWk34Day5Status(Integer wk34Day5Status) {
        this.wk34Day5Status = wk34Day5Status;
    }

    public BigDecimal getWk34Day6() {
        return wk34Day6;
    }

    public void setWk34Day6(BigDecimal wk34Day6) {
        this.wk34Day6 = wk34Day6;
    }

    public Integer getWk34Day6Status() {
        return wk34Day6Status;
    }

    public void setWk34Day6Status(Integer wk34Day6Status) {
        this.wk34Day6Status = wk34Day6Status;
    }

    public BigDecimal getWk34Day7() {
        return wk34Day7;
    }

    public void setWk34Day7(BigDecimal wk34Day7) {
        this.wk34Day7 = wk34Day7;
    }

    public Integer getWk34Day7Status() {
        return wk34Day7Status;
    }

    public void setWk34Day7Status(Integer wk34Day7Status) {
        this.wk34Day7Status = wk34Day7Status;
    }

    public BigDecimal getWk33Day1() {
        return wk33Day1;
    }

    public void setWk33Day1(BigDecimal wk33Day1) {
        this.wk33Day1 = wk33Day1;
    }

    public Integer getWk33Day1Status() {
        return wk33Day1Status;
    }

    public void setWk33Day1Status(Integer wk33Day1Status) {
        this.wk33Day1Status = wk33Day1Status;
    }

    public BigDecimal getWk33Day2() {
        return wk33Day2;
    }

    public void setWk33Day2(BigDecimal wk33Day2) {
        this.wk33Day2 = wk33Day2;
    }

    public Integer getWk33Day2Status() {
        return wk33Day2Status;
    }

    public void setWk33Day2Status(Integer wk33Day2Status) {
        this.wk33Day2Status = wk33Day2Status;
    }

    public BigDecimal getWk33Day3() {
        return wk33Day3;
    }

    public void setWk33Day3(BigDecimal wk33Day3) {
        this.wk33Day3 = wk33Day3;
    }

    public Integer getWk33Day3Status() {
        return wk33Day3Status;
    }

    public void setWk33Day3Status(Integer wk33Day3Status) {
        this.wk33Day3Status = wk33Day3Status;
    }

    public BigDecimal getWk33Day4() {
        return wk33Day4;
    }

    public void setWk33Day4(BigDecimal wk33Day4) {
        this.wk33Day4 = wk33Day4;
    }

    public Integer getWk33Day4Status() {
        return wk33Day4Status;
    }

    public void setWk33Day4Status(Integer wk33Day4Status) {
        this.wk33Day4Status = wk33Day4Status;
    }

    public BigDecimal getWk33Day5() {
        return wk33Day5;
    }

    public void setWk33Day5(BigDecimal wk33Day5) {
        this.wk33Day5 = wk33Day5;
    }

    public Integer getWk33Day5Status() {
        return wk33Day5Status;
    }

    public void setWk33Day5Status(Integer wk33Day5Status) {
        this.wk33Day5Status = wk33Day5Status;
    }

    public BigDecimal getWk33Day6() {
        return wk33Day6;
    }

    public void setWk33Day6(BigDecimal wk33Day6) {
        this.wk33Day6 = wk33Day6;
    }

    public Integer getWk33Day6Status() {
        return wk33Day6Status;
    }

    public void setWk33Day6Status(Integer wk33Day6Status) {
        this.wk33Day6Status = wk33Day6Status;
    }

    public BigDecimal getWk33Day7() {
        return wk33Day7;
    }

    public void setWk33Day7(BigDecimal wk33Day7) {
        this.wk33Day7 = wk33Day7;
    }

    public Integer getWk33Day7Status() {
        return wk33Day7Status;
    }

    public void setWk33Day7Status(Integer wk33Day7Status) {
        this.wk33Day7Status = wk33Day7Status;
    }

    public BigDecimal getWk32Day1() {
        return wk32Day1;
    }

    public void setWk32Day1(BigDecimal wk32Day1) {
        this.wk32Day1 = wk32Day1;
    }

    public Integer getWk32Day1Status() {
        return wk32Day1Status;
    }

    public void setWk32Day1Status(Integer wk32Day1Status) {
        this.wk32Day1Status = wk32Day1Status;
    }

    public BigDecimal getWk32Day2() {
        return wk32Day2;
    }

    public void setWk32Day2(BigDecimal wk32Day2) {
        this.wk32Day2 = wk32Day2;
    }

    public Integer getWk32Day2Status() {
        return wk32Day2Status;
    }

    public void setWk32Day2Status(Integer wk32Day2Status) {
        this.wk32Day2Status = wk32Day2Status;
    }

    public BigDecimal getWk32Day3() {
        return wk32Day3;
    }

    public void setWk32Day3(BigDecimal wk32Day3) {
        this.wk32Day3 = wk32Day3;
    }

    public Integer getWk32Day3Status() {
        return wk32Day3Status;
    }

    public void setWk32Day3Status(Integer wk32Day3Status) {
        this.wk32Day3Status = wk32Day3Status;
    }

    public BigDecimal getWk32Day4() {
        return wk32Day4;
    }

    public void setWk32Day4(BigDecimal wk32Day4) {
        this.wk32Day4 = wk32Day4;
    }

    public Integer getWk32Day4Status() {
        return wk32Day4Status;
    }

    public void setWk32Day4Status(Integer wk32Day4Status) {
        this.wk32Day4Status = wk32Day4Status;
    }

    public BigDecimal getWk32Day5() {
        return wk32Day5;
    }

    public void setWk32Day5(BigDecimal wk32Day5) {
        this.wk32Day5 = wk32Day5;
    }

    public Integer getWk32Day5Status() {
        return wk32Day5Status;
    }

    public void setWk32Day5Status(Integer wk32Day5Status) {
        this.wk32Day5Status = wk32Day5Status;
    }

    public BigDecimal getWk32Day6() {
        return wk32Day6;
    }

    public void setWk32Day6(BigDecimal wk32Day6) {
        this.wk32Day6 = wk32Day6;
    }

    public Integer getWk32Day6Status() {
        return wk32Day6Status;
    }

    public void setWk32Day6Status(Integer wk32Day6Status) {
        this.wk32Day6Status = wk32Day6Status;
    }

    public BigDecimal getWk32Day7() {
        return wk32Day7;
    }

    public void setWk32Day7(BigDecimal wk32Day7) {
        this.wk32Day7 = wk32Day7;
    }

    public Integer getWk32Day7Status() {
        return wk32Day7Status;
    }

    public void setWk32Day7Status(Integer wk32Day7Status) {
        this.wk32Day7Status = wk32Day7Status;
    }

    public BigDecimal getWk31Day1() {
        return wk31Day1;
    }

    public void setWk31Day1(BigDecimal wk31Day1) {
        this.wk31Day1 = wk31Day1;
    }

    public Integer getWk31Day1Status() {
        return wk31Day1Status;
    }

    public void setWk31Day1Status(Integer wk31Day1Status) {
        this.wk31Day1Status = wk31Day1Status;
    }

    public BigDecimal getWk31Day2() {
        return wk31Day2;
    }

    public void setWk31Day2(BigDecimal wk31Day2) {
        this.wk31Day2 = wk31Day2;
    }

    public Integer getWk31Day2Status() {
        return wk31Day2Status;
    }

    public void setWk31Day2Status(Integer wk31Day2Status) {
        this.wk31Day2Status = wk31Day2Status;
    }

    public BigDecimal getWk31Day3() {
        return wk31Day3;
    }

    public void setWk31Day3(BigDecimal wk31Day3) {
        this.wk31Day3 = wk31Day3;
    }

    public Integer getWk31Day3Status() {
        return wk31Day3Status;
    }

    public void setWk31Day3Status(Integer wk31Day3Status) {
        this.wk31Day3Status = wk31Day3Status;
    }

    public BigDecimal getWk31Day4() {
        return wk31Day4;
    }

    public void setWk31Day4(BigDecimal wk31Day4) {
        this.wk31Day4 = wk31Day4;
    }

    public Integer getWk31Day4Status() {
        return wk31Day4Status;
    }

    public void setWk31Day4Status(Integer wk31Day4Status) {
        this.wk31Day4Status = wk31Day4Status;
    }

    public BigDecimal getWk31Day5() {
        return wk31Day5;
    }

    public void setWk31Day5(BigDecimal wk31Day5) {
        this.wk31Day5 = wk31Day5;
    }

    public Integer getWk31Day5Status() {
        return wk31Day5Status;
    }

    public void setWk31Day5Status(Integer wk31Day5Status) {
        this.wk31Day5Status = wk31Day5Status;
    }

    public BigDecimal getWk31Day6() {
        return wk31Day6;
    }

    public void setWk31Day6(BigDecimal wk31Day6) {
        this.wk31Day6 = wk31Day6;
    }

    public Integer getWk31Day6Status() {
        return wk31Day6Status;
    }

    public void setWk31Day6Status(Integer wk31Day6Status) {
        this.wk31Day6Status = wk31Day6Status;
    }

    public BigDecimal getWk31Day7() {
        return wk31Day7;
    }

    public void setWk31Day7(BigDecimal wk31Day7) {
        this.wk31Day7 = wk31Day7;
    }

    public Integer getWk31Day7Status() {
        return wk31Day7Status;
    }

    public void setWk31Day7Status(Integer wk31Day7Status) {
        this.wk31Day7Status = wk31Day7Status;
    }

    public BigDecimal getWk30Day1() {
        return wk30Day1;
    }

    public void setWk30Day1(BigDecimal wk30Day1) {
        this.wk30Day1 = wk30Day1;
    }

    public Integer getWk30Day1Status() {
        return wk30Day1Status;
    }

    public void setWk30Day1Status(Integer wk30Day1Status) {
        this.wk30Day1Status = wk30Day1Status;
    }

    public BigDecimal getWk30Day2() {
        return wk30Day2;
    }

    public void setWk30Day2(BigDecimal wk30Day2) {
        this.wk30Day2 = wk30Day2;
    }

    public Integer getWk30Day2Status() {
        return wk30Day2Status;
    }

    public void setWk30Day2Status(Integer wk30Day2Status) {
        this.wk30Day2Status = wk30Day2Status;
    }

    public BigDecimal getWk30Day3() {
        return wk30Day3;
    }

    public void setWk30Day3(BigDecimal wk30Day3) {
        this.wk30Day3 = wk30Day3;
    }

    public Integer getWk30Day3Status() {
        return wk30Day3Status;
    }

    public void setWk30Day3Status(Integer wk30Day3Status) {
        this.wk30Day3Status = wk30Day3Status;
    }

    public BigDecimal getWk30Day4() {
        return wk30Day4;
    }

    public void setWk30Day4(BigDecimal wk30Day4) {
        this.wk30Day4 = wk30Day4;
    }

    public Integer getWk30Day4Status() {
        return wk30Day4Status;
    }

    public void setWk30Day4Status(Integer wk30Day4Status) {
        this.wk30Day4Status = wk30Day4Status;
    }

    public BigDecimal getWk30Day5() {
        return wk30Day5;
    }

    public void setWk30Day5(BigDecimal wk30Day5) {
        this.wk30Day5 = wk30Day5;
    }

    public Integer getWk30Day5Status() {
        return wk30Day5Status;
    }

    public void setWk30Day5Status(Integer wk30Day5Status) {
        this.wk30Day5Status = wk30Day5Status;
    }

    public BigDecimal getWk30Day6() {
        return wk30Day6;
    }

    public void setWk30Day6(BigDecimal wk30Day6) {
        this.wk30Day6 = wk30Day6;
    }

    public Integer getWk30Day6Status() {
        return wk30Day6Status;
    }

    public void setWk30Day6Status(Integer wk30Day6Status) {
        this.wk30Day6Status = wk30Day6Status;
    }

    public BigDecimal getWk30Day7() {
        return wk30Day7;
    }

    public void setWk30Day7(BigDecimal wk30Day7) {
        this.wk30Day7 = wk30Day7;
    }

    public Integer getWk30Day7Status() {
        return wk30Day7Status;
    }

    public void setWk30Day7Status(Integer wk30Day7Status) {
        this.wk30Day7Status = wk30Day7Status;
    }

    public BigDecimal getWk28Day1() {
        return wk28Day1;
    }

    public void setWk28Day1(BigDecimal wk28Day1) {
        this.wk28Day1 = wk28Day1;
    }

    public Integer getWk28Day1Status() {
        return wk28Day1Status;
    }

    public void setWk28Day1Status(Integer wk28Day1Status) {
        this.wk28Day1Status = wk28Day1Status;
    }

    public BigDecimal getWk28Day2() {
        return wk28Day2;
    }

    public void setWk28Day2(BigDecimal wk28Day2) {
        this.wk28Day2 = wk28Day2;
    }

    public Integer getWk28Day2Status() {
        return wk28Day2Status;
    }

    public void setWk28Day2Status(Integer wk28Day2Status) {
        this.wk28Day2Status = wk28Day2Status;
    }

    public BigDecimal getWk28Day3() {
        return wk28Day3;
    }

    public void setWk28Day3(BigDecimal wk28Day3) {
        this.wk28Day3 = wk28Day3;
    }

    public Integer getWk28Day3Status() {
        return wk28Day3Status;
    }

    public void setWk28Day3Status(Integer wk28Day3Status) {
        this.wk28Day3Status = wk28Day3Status;
    }

    public BigDecimal getWk28Day4() {
        return wk28Day4;
    }

    public void setWk28Day4(BigDecimal wk28Day4) {
        this.wk28Day4 = wk28Day4;
    }

    public Integer getWk28Day4Status() {
        return wk28Day4Status;
    }

    public void setWk28Day4Status(Integer wk28Day4Status) {
        this.wk28Day4Status = wk28Day4Status;
    }

    public BigDecimal getWk28Day5() {
        return wk28Day5;
    }

    public void setWk28Day5(BigDecimal wk28Day5) {
        this.wk28Day5 = wk28Day5;
    }

    public Integer getWk28Day5Status() {
        return wk28Day5Status;
    }

    public void setWk28Day5Status(Integer wk28Day5Status) {
        this.wk28Day5Status = wk28Day5Status;
    }

    public BigDecimal getWk28Day6() {
        return wk28Day6;
    }

    public void setWk28Day6(BigDecimal wk28Day6) {
        this.wk28Day6 = wk28Day6;
    }

    public Integer getWk28Day6Status() {
        return wk28Day6Status;
    }

    public void setWk28Day6Status(Integer wk28Day6Status) {
        this.wk28Day6Status = wk28Day6Status;
    }

    public BigDecimal getWk28Day7() {
        return wk28Day7;
    }

    public void setWk28Day7(BigDecimal wk28Day7) {
        this.wk28Day7 = wk28Day7;
    }

    public Integer getWk28Day7Status() {
        return wk28Day7Status;
    }

    public void setWk28Day7Status(Integer wk28Day7Status) {
        this.wk28Day7Status = wk28Day7Status;
    }

    public BigDecimal getWk27Day1() {
        return wk27Day1;
    }

    public void setWk27Day1(BigDecimal wk27Day1) {
        this.wk27Day1 = wk27Day1;
    }

    public Integer getWk27Day1Status() {
        return wk27Day1Status;
    }

    public void setWk27Day1Status(Integer wk27Day1Status) {
        this.wk27Day1Status = wk27Day1Status;
    }

    public BigDecimal getWk27Day2() {
        return wk27Day2;
    }

    public void setWk27Day2(BigDecimal wk27Day2) {
        this.wk27Day2 = wk27Day2;
    }

    public Integer getWk27Day2Status() {
        return wk27Day2Status;
    }

    public void setWk27Day2Status(Integer wk27Day2Status) {
        this.wk27Day2Status = wk27Day2Status;
    }

    public BigDecimal getWk27Day3() {
        return wk27Day3;
    }

    public void setWk27Day3(BigDecimal wk27Day3) {
        this.wk27Day3 = wk27Day3;
    }

    public Integer getWk27Day3Status() {
        return wk27Day3Status;
    }

    public void setWk27Day3Status(Integer wk27Day3Status) {
        this.wk27Day3Status = wk27Day3Status;
    }

    public BigDecimal getWk27Day4() {
        return wk27Day4;
    }

    public void setWk27Day4(BigDecimal wk27Day4) {
        this.wk27Day4 = wk27Day4;
    }

    public Integer getWk27Day4Status() {
        return wk27Day4Status;
    }

    public void setWk27Day4Status(Integer wk27Day4Status) {
        this.wk27Day4Status = wk27Day4Status;
    }

    public BigDecimal getWk27Day5() {
        return wk27Day5;
    }

    public void setWk27Day5(BigDecimal wk27Day5) {
        this.wk27Day5 = wk27Day5;
    }

    public Integer getWk27Day5Status() {
        return wk27Day5Status;
    }

    public void setWk27Day5Status(Integer wk27Day5Status) {
        this.wk27Day5Status = wk27Day5Status;
    }

    public BigDecimal getWk27Day6() {
        return wk27Day6;
    }

    public void setWk27Day6(BigDecimal wk27Day6) {
        this.wk27Day6 = wk27Day6;
    }

    public Integer getWk27Day6Status() {
        return wk27Day6Status;
    }

    public void setWk27Day6Status(Integer wk27Day6Status) {
        this.wk27Day6Status = wk27Day6Status;
    }

    public BigDecimal getWk27Day7() {
        return wk27Day7;
    }

    public void setWk27Day7(BigDecimal wk27Day7) {
        this.wk27Day7 = wk27Day7;
    }

    public Integer getWk27Day7Status() {
        return wk27Day7Status;
    }

    public void setWk27Day7Status(Integer wk27Day7Status) {
        this.wk27Day7Status = wk27Day7Status;
    }

    public BigDecimal getWk26Day1() {
        return wk26Day1;
    }

    public void setWk26Day1(BigDecimal wk26Day1) {
        this.wk26Day1 = wk26Day1;
    }

    public Integer getWk26Day1Status() {
        return wk26Day1Status;
    }

    public void setWk26Day1Status(Integer wk26Day1Status) {
        this.wk26Day1Status = wk26Day1Status;
    }

    public BigDecimal getWk26Day2() {
        return wk26Day2;
    }

    public void setWk26Day2(BigDecimal wk26Day2) {
        this.wk26Day2 = wk26Day2;
    }

    public Integer getWk26Day2Status() {
        return wk26Day2Status;
    }

    public void setWk26Day2Status(Integer wk26Day2Status) {
        this.wk26Day2Status = wk26Day2Status;
    }

    public BigDecimal getWk26Day3() {
        return wk26Day3;
    }

    public void setWk26Day3(BigDecimal wk26Day3) {
        this.wk26Day3 = wk26Day3;
    }

    public Integer getWk26Day3Status() {
        return wk26Day3Status;
    }

    public void setWk26Day3Status(Integer wk26Day3Status) {
        this.wk26Day3Status = wk26Day3Status;
    }

    public BigDecimal getWk26Day4() {
        return wk26Day4;
    }

    public void setWk26Day4(BigDecimal wk26Day4) {
        this.wk26Day4 = wk26Day4;
    }

    public Integer getWk26Day4Status() {
        return wk26Day4Status;
    }

    public void setWk26Day4Status(Integer wk26Day4Status) {
        this.wk26Day4Status = wk26Day4Status;
    }

    public BigDecimal getWk26Day5() {
        return wk26Day5;
    }

    public void setWk26Day5(BigDecimal wk26Day5) {
        this.wk26Day5 = wk26Day5;
    }

    public Integer getWk26Day5Status() {
        return wk26Day5Status;
    }

    public void setWk26Day5Status(Integer wk26Day5Status) {
        this.wk26Day5Status = wk26Day5Status;
    }

    public BigDecimal getWk26Day6() {
        return wk26Day6;
    }

    public void setWk26Day6(BigDecimal wk26Day6) {
        this.wk26Day6 = wk26Day6;
    }

    public Integer getWk26Day6Status() {
        return wk26Day6Status;
    }

    public void setWk26Day6Status(Integer wk26Day6Status) {
        this.wk26Day6Status = wk26Day6Status;
    }

    public BigDecimal getWk26Day7() {
        return wk26Day7;
    }

    public void setWk26Day7(BigDecimal wk26Day7) {
        this.wk26Day7 = wk26Day7;
    }

    public Integer getWk26Day7Status() {
        return wk26Day7Status;
    }

    public void setWk26Day7Status(Integer wk26Day7Status) {
        this.wk26Day7Status = wk26Day7Status;
    }

    public BigDecimal getWk25Day1() {
        return wk25Day1;
    }

    public void setWk25Day1(BigDecimal wk25Day1) {
        this.wk25Day1 = wk25Day1;
    }

    public Integer getWk25Day1Status() {
        return wk25Day1Status;
    }

    public void setWk25Day1Status(Integer wk25Day1Status) {
        this.wk25Day1Status = wk25Day1Status;
    }

    public BigDecimal getWk25Day2() {
        return wk25Day2;
    }

    public void setWk25Day2(BigDecimal wk25Day2) {
        this.wk25Day2 = wk25Day2;
    }

    public Integer getWk25Day2Status() {
        return wk25Day2Status;
    }

    public void setWk25Day2Status(Integer wk25Day2Status) {
        this.wk25Day2Status = wk25Day2Status;
    }

    public BigDecimal getWk25Day3() {
        return wk25Day3;
    }

    public void setWk25Day3(BigDecimal wk25Day3) {
        this.wk25Day3 = wk25Day3;
    }

    public Integer getWk25Day3Status() {
        return wk25Day3Status;
    }

    public void setWk25Day3Status(Integer wk25Day3Status) {
        this.wk25Day3Status = wk25Day3Status;
    }

    public BigDecimal getWk25Day4() {
        return wk25Day4;
    }

    public void setWk25Day4(BigDecimal wk25Day4) {
        this.wk25Day4 = wk25Day4;
    }

    public Integer getWk25Day4Status() {
        return wk25Day4Status;
    }

    public void setWk25Day4Status(Integer wk25Day4Status) {
        this.wk25Day4Status = wk25Day4Status;
    }

    public BigDecimal getWk25Day5() {
        return wk25Day5;
    }

    public void setWk25Day5(BigDecimal wk25Day5) {
        this.wk25Day5 = wk25Day5;
    }

    public Integer getWk25Day5Status() {
        return wk25Day5Status;
    }

    public void setWk25Day5Status(Integer wk25Day5Status) {
        this.wk25Day5Status = wk25Day5Status;
    }

    public BigDecimal getWk25Day6() {
        return wk25Day6;
    }

    public void setWk25Day6(BigDecimal wk25Day6) {
        this.wk25Day6 = wk25Day6;
    }

    public Integer getWk25Day6Status() {
        return wk25Day6Status;
    }

    public void setWk25Day6Status(Integer wk25Day6Status) {
        this.wk25Day6Status = wk25Day6Status;
    }

    public BigDecimal getWk25Day7() {
        return wk25Day7;
    }

    public void setWk25Day7(BigDecimal wk25Day7) {
        this.wk25Day7 = wk25Day7;
    }

    public Integer getWk25Day7Status() {
        return wk25Day7Status;
    }

    public void setWk25Day7Status(Integer wk25Day7Status) {
        this.wk25Day7Status = wk25Day7Status;
    }

    public BigDecimal getWk24Day1() {
        return wk24Day1;
    }

    public void setWk24Day1(BigDecimal wk24Day1) {
        this.wk24Day1 = wk24Day1;
    }

    public Integer getWk24Day1Status() {
        return wk24Day1Status;
    }

    public void setWk24Day1Status(Integer wk24Day1Status) {
        this.wk24Day1Status = wk24Day1Status;
    }

    public BigDecimal getWk24Day2() {
        return wk24Day2;
    }

    public void setWk24Day2(BigDecimal wk24Day2) {
        this.wk24Day2 = wk24Day2;
    }

    public Integer getWk24Day2Status() {
        return wk24Day2Status;
    }

    public void setWk24Day2Status(Integer wk24Day2Status) {
        this.wk24Day2Status = wk24Day2Status;
    }

    public BigDecimal getWk24Day3() {
        return wk24Day3;
    }

    public void setWk24Day3(BigDecimal wk24Day3) {
        this.wk24Day3 = wk24Day3;
    }

    public Integer getWk24Day3Status() {
        return wk24Day3Status;
    }

    public void setWk24Day3Status(Integer wk24Day3Status) {
        this.wk24Day3Status = wk24Day3Status;
    }

    public BigDecimal getWk24Day4() {
        return wk24Day4;
    }

    public void setWk24Day4(BigDecimal wk24Day4) {
        this.wk24Day4 = wk24Day4;
    }

    public Integer getWk24Day4Status() {
        return wk24Day4Status;
    }

    public void setWk24Day4Status(Integer wk24Day4Status) {
        this.wk24Day4Status = wk24Day4Status;
    }

    public BigDecimal getWk24Day5() {
        return wk24Day5;
    }

    public void setWk24Day5(BigDecimal wk24Day5) {
        this.wk24Day5 = wk24Day5;
    }

    public Integer getWk24Day5Status() {
        return wk24Day5Status;
    }

    public void setWk24Day5Status(Integer wk24Day5Status) {
        this.wk24Day5Status = wk24Day5Status;
    }

    public BigDecimal getWk24Day6() {
        return wk24Day6;
    }

    public void setWk24Day6(BigDecimal wk24Day6) {
        this.wk24Day6 = wk24Day6;
    }

    public Integer getWk24Day6Status() {
        return wk24Day6Status;
    }

    public void setWk24Day6Status(Integer wk24Day6Status) {
        this.wk24Day6Status = wk24Day6Status;
    }

    public BigDecimal getWk24Day7() {
        return wk24Day7;
    }

    public void setWk24Day7(BigDecimal wk24Day7) {
        this.wk24Day7 = wk24Day7;
    }

    public Integer getWk24Day7Status() {
        return wk24Day7Status;
    }

    public void setWk24Day7Status(Integer wk24Day7Status) {
        this.wk24Day7Status = wk24Day7Status;
    }

    public BigDecimal getWk23Day1() {
        return wk23Day1;
    }

    public void setWk23Day1(BigDecimal wk23Day1) {
        this.wk23Day1 = wk23Day1;
    }

    public Integer getWk23Day1Status() {
        return wk23Day1Status;
    }

    public void setWk23Day1Status(Integer wk23Day1Status) {
        this.wk23Day1Status = wk23Day1Status;
    }

    public BigDecimal getWk23Day2() {
        return wk23Day2;
    }

    public void setWk23Day2(BigDecimal wk23Day2) {
        this.wk23Day2 = wk23Day2;
    }

    public Integer getWk23Day2Status() {
        return wk23Day2Status;
    }

    public void setWk23Day2Status(Integer wk23Day2Status) {
        this.wk23Day2Status = wk23Day2Status;
    }

    public BigDecimal getWk23Day3() {
        return wk23Day3;
    }

    public void setWk23Day3(BigDecimal wk23Day3) {
        this.wk23Day3 = wk23Day3;
    }

    public Integer getWk23Day3Status() {
        return wk23Day3Status;
    }

    public void setWk23Day3Status(Integer wk23Day3Status) {
        this.wk23Day3Status = wk23Day3Status;
    }

    public BigDecimal getWk23Day4() {
        return wk23Day4;
    }

    public void setWk23Day4(BigDecimal wk23Day4) {
        this.wk23Day4 = wk23Day4;
    }

    public Integer getWk23Day4Status() {
        return wk23Day4Status;
    }

    public void setWk23Day4Status(Integer wk23Day4Status) {
        this.wk23Day4Status = wk23Day4Status;
    }

    public BigDecimal getWk23Day5() {
        return wk23Day5;
    }

    public void setWk23Day5(BigDecimal wk23Day5) {
        this.wk23Day5 = wk23Day5;
    }

    public Integer getWk23Day5Status() {
        return wk23Day5Status;
    }

    public void setWk23Day5Status(Integer wk23Day5Status) {
        this.wk23Day5Status = wk23Day5Status;
    }

    public BigDecimal getWk23Day6() {
        return wk23Day6;
    }

    public void setWk23Day6(BigDecimal wk23Day6) {
        this.wk23Day6 = wk23Day6;
    }

    public Integer getWk23Day6Status() {
        return wk23Day6Status;
    }

    public void setWk23Day6Status(Integer wk23Day6Status) {
        this.wk23Day6Status = wk23Day6Status;
    }

    public BigDecimal getWk23Day7() {
        return wk23Day7;
    }

    public void setWk23Day7(BigDecimal wk23Day7) {
        this.wk23Day7 = wk23Day7;
    }

    public Integer getWk23Day7Status() {
        return wk23Day7Status;
    }

    public void setWk23Day7Status(Integer wk23Day7Status) {
        this.wk23Day7Status = wk23Day7Status;
    }

    public BigDecimal getWk22Day1() {
        return wk22Day1;
    }

    public void setWk22Day1(BigDecimal wk22Day1) {
        this.wk22Day1 = wk22Day1;
    }

    public Integer getWk22Day1Status() {
        return wk22Day1Status;
    }

    public void setWk22Day1Status(Integer wk22Day1Status) {
        this.wk22Day1Status = wk22Day1Status;
    }

    public BigDecimal getWk22Day2() {
        return wk22Day2;
    }

    public void setWk22Day2(BigDecimal wk22Day2) {
        this.wk22Day2 = wk22Day2;
    }

    public Integer getWk22Day2Status() {
        return wk22Day2Status;
    }

    public void setWk22Day2Status(Integer wk22Day2Status) {
        this.wk22Day2Status = wk22Day2Status;
    }

    public BigDecimal getWk22Day3() {
        return wk22Day3;
    }

    public void setWk22Day3(BigDecimal wk22Day3) {
        this.wk22Day3 = wk22Day3;
    }

    public Integer getWk22Day3Status() {
        return wk22Day3Status;
    }

    public void setWk22Day3Status(Integer wk22Day3Status) {
        this.wk22Day3Status = wk22Day3Status;
    }

    public BigDecimal getWk22Day4() {
        return wk22Day4;
    }

    public void setWk22Day4(BigDecimal wk22Day4) {
        this.wk22Day4 = wk22Day4;
    }

    public Integer getWk22Day4Status() {
        return wk22Day4Status;
    }

    public void setWk22Day4Status(Integer wk22Day4Status) {
        this.wk22Day4Status = wk22Day4Status;
    }

    public BigDecimal getWk22Day5() {
        return wk22Day5;
    }

    public void setWk22Day5(BigDecimal wk22Day5) {
        this.wk22Day5 = wk22Day5;
    }

    public Integer getWk22Day5Status() {
        return wk22Day5Status;
    }

    public void setWk22Day5Status(Integer wk22Day5Status) {
        this.wk22Day5Status = wk22Day5Status;
    }

    public BigDecimal getWk22Day6() {
        return wk22Day6;
    }

    public void setWk22Day6(BigDecimal wk22Day6) {
        this.wk22Day6 = wk22Day6;
    }

    public Integer getWk22Day6Status() {
        return wk22Day6Status;
    }

    public void setWk22Day6Status(Integer wk22Day6Status) {
        this.wk22Day6Status = wk22Day6Status;
    }

    public BigDecimal getWk22Day7() {
        return wk22Day7;
    }

    public void setWk22Day7(BigDecimal wk22Day7) {
        this.wk22Day7 = wk22Day7;
    }

    public Integer getWk22Day7Status() {
        return wk22Day7Status;
    }

    public void setWk22Day7Status(Integer wk22Day7Status) {
        this.wk22Day7Status = wk22Day7Status;
    }

    public BigDecimal getWk21Day1() {
        return wk21Day1;
    }

    public void setWk21Day1(BigDecimal wk21Day1) {
        this.wk21Day1 = wk21Day1;
    }

    public Integer getWk21Day1Status() {
        return wk21Day1Status;
    }

    public void setWk21Day1Status(Integer wk21Day1Status) {
        this.wk21Day1Status = wk21Day1Status;
    }

    public BigDecimal getWk21Day2() {
        return wk21Day2;
    }

    public void setWk21Day2(BigDecimal wk21Day2) {
        this.wk21Day2 = wk21Day2;
    }

    public Integer getWk21Day2Status() {
        return wk21Day2Status;
    }

    public void setWk21Day2Status(Integer wk21Day2Status) {
        this.wk21Day2Status = wk21Day2Status;
    }

    public BigDecimal getWk21Day3() {
        return wk21Day3;
    }

    public void setWk21Day3(BigDecimal wk21Day3) {
        this.wk21Day3 = wk21Day3;
    }

    public Integer getWk21Day3Status() {
        return wk21Day3Status;
    }

    public void setWk21Day3Status(Integer wk21Day3Status) {
        this.wk21Day3Status = wk21Day3Status;
    }

    public BigDecimal getWk21Day4() {
        return wk21Day4;
    }

    public void setWk21Day4(BigDecimal wk21Day4) {
        this.wk21Day4 = wk21Day4;
    }

    public Integer getWk21Day4Status() {
        return wk21Day4Status;
    }

    public void setWk21Day4Status(Integer wk21Day4Status) {
        this.wk21Day4Status = wk21Day4Status;
    }

    public BigDecimal getWk21Day5() {
        return wk21Day5;
    }

    public void setWk21Day5(BigDecimal wk21Day5) {
        this.wk21Day5 = wk21Day5;
    }

    public Integer getWk21Day5Status() {
        return wk21Day5Status;
    }

    public void setWk21Day5Status(Integer wk21Day5Status) {
        this.wk21Day5Status = wk21Day5Status;
    }

    public BigDecimal getWk21Day6() {
        return wk21Day6;
    }

    public void setWk21Day6(BigDecimal wk21Day6) {
        this.wk21Day6 = wk21Day6;
    }

    public Integer getWk21Day6Status() {
        return wk21Day6Status;
    }

    public void setWk21Day6Status(Integer wk21Day6Status) {
        this.wk21Day6Status = wk21Day6Status;
    }

    public BigDecimal getWk21Day7() {
        return wk21Day7;
    }

    public void setWk21Day7(BigDecimal wk21Day7) {
        this.wk21Day7 = wk21Day7;
    }

    public Integer getWk21Day7Status() {
        return wk21Day7Status;
    }

    public void setWk21Day7Status(Integer wk21Day7Status) {
        this.wk21Day7Status = wk21Day7Status;
    }

    public BigDecimal getWk20Day1() {
        return wk20Day1;
    }

    public void setWk20Day1(BigDecimal wk20Day1) {
        this.wk20Day1 = wk20Day1;
    }

    public Integer getWk20Day1Status() {
        return wk20Day1Status;
    }

    public void setWk20Day1Status(Integer wk20Day1Status) {
        this.wk20Day1Status = wk20Day1Status;
    }

    public BigDecimal getWk20Day2() {
        return wk20Day2;
    }

    public void setWk20Day2(BigDecimal wk20Day2) {
        this.wk20Day2 = wk20Day2;
    }

    public Integer getWk20Day2Status() {
        return wk20Day2Status;
    }

    public void setWk20Day2Status(Integer wk20Day2Status) {
        this.wk20Day2Status = wk20Day2Status;
    }

    public BigDecimal getWk20Day3() {
        return wk20Day3;
    }

    public void setWk20Day3(BigDecimal wk20Day3) {
        this.wk20Day3 = wk20Day3;
    }

    public Integer getWk20Day3Status() {
        return wk20Day3Status;
    }

    public void setWk20Day3Status(Integer wk20Day3Status) {
        this.wk20Day3Status = wk20Day3Status;
    }

    public BigDecimal getWk20Day4() {
        return wk20Day4;
    }

    public void setWk20Day4(BigDecimal wk20Day4) {
        this.wk20Day4 = wk20Day4;
    }

    public Integer getWk20Day4Status() {
        return wk20Day4Status;
    }

    public void setWk20Day4Status(Integer wk20Day4Status) {
        this.wk20Day4Status = wk20Day4Status;
    }

    public BigDecimal getWk20Day5() {
        return wk20Day5;
    }

    public void setWk20Day5(BigDecimal wk20Day5) {
        this.wk20Day5 = wk20Day5;
    }

    public Integer getWk20Day5Status() {
        return wk20Day5Status;
    }

    public void setWk20Day5Status(Integer wk20Day5Status) {
        this.wk20Day5Status = wk20Day5Status;
    }

    public BigDecimal getWk20Day6() {
        return wk20Day6;
    }

    public void setWk20Day6(BigDecimal wk20Day6) {
        this.wk20Day6 = wk20Day6;
    }

    public Integer getWk20Day6Status() {
        return wk20Day6Status;
    }

    public void setWk20Day6Status(Integer wk20Day6Status) {
        this.wk20Day6Status = wk20Day6Status;
    }

    public BigDecimal getWk20Day7() {
        return wk20Day7;
    }

    public void setWk20Day7(BigDecimal wk20Day7) {
        this.wk20Day7 = wk20Day7;
    }

    public Integer getWk20Day7Status() {
        return wk20Day7Status;
    }

    public void setWk20Day7Status(Integer wk20Day7Status) {
        this.wk20Day7Status = wk20Day7Status;
    }

    public BigDecimal getWk19Day1() {
        return wk19Day1;
    }

    public void setWk19Day1(BigDecimal wk19Day1) {
        this.wk19Day1 = wk19Day1;
    }

    public Integer getWk19Day1Status() {
        return wk19Day1Status;
    }

    public void setWk19Day1Status(Integer wk19Day1Status) {
        this.wk19Day1Status = wk19Day1Status;
    }

    public BigDecimal getWk19Day2() {
        return wk19Day2;
    }

    public void setWk19Day2(BigDecimal wk19Day2) {
        this.wk19Day2 = wk19Day2;
    }

    public Integer getWk19Day2Status() {
        return wk19Day2Status;
    }

    public void setWk19Day2Status(Integer wk19Day2Status) {
        this.wk19Day2Status = wk19Day2Status;
    }

    public BigDecimal getWk19Day3() {
        return wk19Day3;
    }

    public void setWk19Day3(BigDecimal wk19Day3) {
        this.wk19Day3 = wk19Day3;
    }

    public Integer getWk19Day3Status() {
        return wk19Day3Status;
    }

    public void setWk19Day3Status(Integer wk19Day3Status) {
        this.wk19Day3Status = wk19Day3Status;
    }

    public BigDecimal getWk19Day4() {
        return wk19Day4;
    }

    public void setWk19Day4(BigDecimal wk19Day4) {
        this.wk19Day4 = wk19Day4;
    }

    public Integer getWk19Day4Status() {
        return wk19Day4Status;
    }

    public void setWk19Day4Status(Integer wk19Day4Status) {
        this.wk19Day4Status = wk19Day4Status;
    }

    public BigDecimal getWk19Day5() {
        return wk19Day5;
    }

    public void setWk19Day5(BigDecimal wk19Day5) {
        this.wk19Day5 = wk19Day5;
    }

    public Integer getWk19Day5Status() {
        return wk19Day5Status;
    }

    public void setWk19Day5Status(Integer wk19Day5Status) {
        this.wk19Day5Status = wk19Day5Status;
    }

    public BigDecimal getWk19Day6() {
        return wk19Day6;
    }

    public void setWk19Day6(BigDecimal wk19Day6) {
        this.wk19Day6 = wk19Day6;
    }

    public Integer getWk19Day6Status() {
        return wk19Day6Status;
    }

    public void setWk19Day6Status(Integer wk19Day6Status) {
        this.wk19Day6Status = wk19Day6Status;
    }

    public BigDecimal getWk19Day7() {
        return wk19Day7;
    }

    public void setWk19Day7(BigDecimal wk19Day7) {
        this.wk19Day7 = wk19Day7;
    }

    public Integer getWk19Day7Status() {
        return wk19Day7Status;
    }

    public void setWk19Day7Status(Integer wk19Day7Status) {
        this.wk19Day7Status = wk19Day7Status;
    }

    public BigDecimal getWk18Day1() {
        return wk18Day1;
    }

    public void setWk18Day1(BigDecimal wk18Day1) {
        this.wk18Day1 = wk18Day1;
    }

    public Integer getWk18Day1Status() {
        return wk18Day1Status;
    }

    public void setWk18Day1Status(Integer wk18Day1Status) {
        this.wk18Day1Status = wk18Day1Status;
    }

    public BigDecimal getWk18Day2() {
        return wk18Day2;
    }

    public void setWk18Day2(BigDecimal wk18Day2) {
        this.wk18Day2 = wk18Day2;
    }

    public Integer getWk18Day2Status() {
        return wk18Day2Status;
    }

    public void setWk18Day2Status(Integer wk18Day2Status) {
        this.wk18Day2Status = wk18Day2Status;
    }

    public BigDecimal getWk18Day3() {
        return wk18Day3;
    }

    public void setWk18Day3(BigDecimal wk18Day3) {
        this.wk18Day3 = wk18Day3;
    }

    public Integer getWk18Day3Status() {
        return wk18Day3Status;
    }

    public void setWk18Day3Status(Integer wk18Day3Status) {
        this.wk18Day3Status = wk18Day3Status;
    }

    public BigDecimal getWk18Day4() {
        return wk18Day4;
    }

    public void setWk18Day4(BigDecimal wk18Day4) {
        this.wk18Day4 = wk18Day4;
    }

    public Integer getWk18Day4Status() {
        return wk18Day4Status;
    }

    public void setWk18Day4Status(Integer wk18Day4Status) {
        this.wk18Day4Status = wk18Day4Status;
    }

    public BigDecimal getWk18Day5() {
        return wk18Day5;
    }

    public void setWk18Day5(BigDecimal wk18Day5) {
        this.wk18Day5 = wk18Day5;
    }

    public Integer getWk18Day5Status() {
        return wk18Day5Status;
    }

    public void setWk18Day5Status(Integer wk18Day5Status) {
        this.wk18Day5Status = wk18Day5Status;
    }

    public BigDecimal getWk18Day6() {
        return wk18Day6;
    }

    public void setWk18Day6(BigDecimal wk18Day6) {
        this.wk18Day6 = wk18Day6;
    }

    public Integer getWk18Day6Status() {
        return wk18Day6Status;
    }

    public void setWk18Day6Status(Integer wk18Day6Status) {
        this.wk18Day6Status = wk18Day6Status;
    }

    public BigDecimal getWk18Day7() {
        return wk18Day7;
    }

    public void setWk18Day7(BigDecimal wk18Day7) {
        this.wk18Day7 = wk18Day7;
    }

    public Integer getWk18Day7Status() {
        return wk18Day7Status;
    }

    public void setWk18Day7Status(Integer wk18Day7Status) {
        this.wk18Day7Status = wk18Day7Status;
    }

    public BigDecimal getWk17Day1() {
        return wk17Day1;
    }

    public void setWk17Day1(BigDecimal wk17Day1) {
        this.wk17Day1 = wk17Day1;
    }

    public Integer getWk17Day1Status() {
        return wk17Day1Status;
    }

    public void setWk17Day1Status(Integer wk17Day1Status) {
        this.wk17Day1Status = wk17Day1Status;
    }

    public BigDecimal getWk17Day2() {
        return wk17Day2;
    }

    public void setWk17Day2(BigDecimal wk17Day2) {
        this.wk17Day2 = wk17Day2;
    }

    public Integer getWk17Day2Status() {
        return wk17Day2Status;
    }

    public void setWk17Day2Status(Integer wk17Day2Status) {
        this.wk17Day2Status = wk17Day2Status;
    }

    public BigDecimal getWk17Day3() {
        return wk17Day3;
    }

    public void setWk17Day3(BigDecimal wk17Day3) {
        this.wk17Day3 = wk17Day3;
    }

    public Integer getWk17Day3Status() {
        return wk17Day3Status;
    }

    public void setWk17Day3Status(Integer wk17Day3Status) {
        this.wk17Day3Status = wk17Day3Status;
    }

    public BigDecimal getWk17Day4() {
        return wk17Day4;
    }

    public void setWk17Day4(BigDecimal wk17Day4) {
        this.wk17Day4 = wk17Day4;
    }

    public Integer getWk17Day4Status() {
        return wk17Day4Status;
    }

    public void setWk17Day4Status(Integer wk17Day4Status) {
        this.wk17Day4Status = wk17Day4Status;
    }

    public BigDecimal getWk17Day5() {
        return wk17Day5;
    }

    public void setWk17Day5(BigDecimal wk17Day5) {
        this.wk17Day5 = wk17Day5;
    }

    public Integer getWk17Day5Status() {
        return wk17Day5Status;
    }

    public void setWk17Day5Status(Integer wk17Day5Status) {
        this.wk17Day5Status = wk17Day5Status;
    }

    public BigDecimal getWk17Day6() {
        return wk17Day6;
    }

    public void setWk17Day6(BigDecimal wk17Day6) {
        this.wk17Day6 = wk17Day6;
    }

    public Integer getWk17Day6Status() {
        return wk17Day6Status;
    }

    public void setWk17Day6Status(Integer wk17Day6Status) {
        this.wk17Day6Status = wk17Day6Status;
    }

    public BigDecimal getWk17Day7() {
        return wk17Day7;
    }

    public void setWk17Day7(BigDecimal wk17Day7) {
        this.wk17Day7 = wk17Day7;
    }

    public Integer getWk17Day7Status() {
        return wk17Day7Status;
    }

    public void setWk17Day7Status(Integer wk17Day7Status) {
        this.wk17Day7Status = wk17Day7Status;
    }

    public BigDecimal getWk16Day1() {
        return wk16Day1;
    }

    public void setWk16Day1(BigDecimal wk16Day1) {
        this.wk16Day1 = wk16Day1;
    }

    public Integer getWk16Day1Status() {
        return wk16Day1Status;
    }

    public void setWk16Day1Status(Integer wk16Day1Status) {
        this.wk16Day1Status = wk16Day1Status;
    }

    public BigDecimal getWk16Day2() {
        return wk16Day2;
    }

    public void setWk16Day2(BigDecimal wk16Day2) {
        this.wk16Day2 = wk16Day2;
    }

    public Integer getWk16Day2Status() {
        return wk16Day2Status;
    }

    public void setWk16Day2Status(Integer wk16Day2Status) {
        this.wk16Day2Status = wk16Day2Status;
    }

    public BigDecimal getWk16Day3() {
        return wk16Day3;
    }

    public void setWk16Day3(BigDecimal wk16Day3) {
        this.wk16Day3 = wk16Day3;
    }

    public Integer getWk16Day3Status() {
        return wk16Day3Status;
    }

    public void setWk16Day3Status(Integer wk16Day3Status) {
        this.wk16Day3Status = wk16Day3Status;
    }

    public BigDecimal getWk16Day4() {
        return wk16Day4;
    }

    public void setWk16Day4(BigDecimal wk16Day4) {
        this.wk16Day4 = wk16Day4;
    }

    public Integer getWk16Day4Status() {
        return wk16Day4Status;
    }

    public void setWk16Day4Status(Integer wk16Day4Status) {
        this.wk16Day4Status = wk16Day4Status;
    }

    public BigDecimal getWk16Day5() {
        return wk16Day5;
    }

    public void setWk16Day5(BigDecimal wk16Day5) {
        this.wk16Day5 = wk16Day5;
    }

    public Integer getWk16Day5Status() {
        return wk16Day5Status;
    }

    public void setWk16Day5Status(Integer wk16Day5Status) {
        this.wk16Day5Status = wk16Day5Status;
    }

    public BigDecimal getWk16Day6() {
        return wk16Day6;
    }

    public void setWk16Day6(BigDecimal wk16Day6) {
        this.wk16Day6 = wk16Day6;
    }

    public Integer getWk16Day6Status() {
        return wk16Day6Status;
    }

    public void setWk16Day6Status(Integer wk16Day6Status) {
        this.wk16Day6Status = wk16Day6Status;
    }

    public BigDecimal getWk16Day7() {
        return wk16Day7;
    }

    public void setWk16Day7(BigDecimal wk16Day7) {
        this.wk16Day7 = wk16Day7;
    }

    public Integer getWk16Day7Status() {
        return wk16Day7Status;
    }

    public void setWk16Day7Status(Integer wk16Day7Status) {
        this.wk16Day7Status = wk16Day7Status;
    }

    public BigDecimal getWk15Day1() {
        return wk15Day1;
    }

    public void setWk15Day1(BigDecimal wk15Day1) {
        this.wk15Day1 = wk15Day1;
    }

    public Integer getWk15Day1Status() {
        return wk15Day1Status;
    }

    public void setWk15Day1Status(Integer wk15Day1Status) {
        this.wk15Day1Status = wk15Day1Status;
    }

    public BigDecimal getWk15Day2() {
        return wk15Day2;
    }

    public void setWk15Day2(BigDecimal wk15Day2) {
        this.wk15Day2 = wk15Day2;
    }

    public Integer getWk15Day2Status() {
        return wk15Day2Status;
    }

    public void setWk15Day2Status(Integer wk15Day2Status) {
        this.wk15Day2Status = wk15Day2Status;
    }

    public BigDecimal getWk15Day3() {
        return wk15Day3;
    }

    public void setWk15Day3(BigDecimal wk15Day3) {
        this.wk15Day3 = wk15Day3;
    }

    public Integer getWk15Day3Status() {
        return wk15Day3Status;
    }

    public void setWk15Day3Status(Integer wk15Day3Status) {
        this.wk15Day3Status = wk15Day3Status;
    }

    public BigDecimal getWk15Day4() {
        return wk15Day4;
    }

    public void setWk15Day4(BigDecimal wk15Day4) {
        this.wk15Day4 = wk15Day4;
    }

    public Integer getWk15Day4Status() {
        return wk15Day4Status;
    }

    public void setWk15Day4Status(Integer wk15Day4Status) {
        this.wk15Day4Status = wk15Day4Status;
    }

    public BigDecimal getWk15Day5() {
        return wk15Day5;
    }

    public void setWk15Day5(BigDecimal wk15Day5) {
        this.wk15Day5 = wk15Day5;
    }

    public Integer getWk15Day5Status() {
        return wk15Day5Status;
    }

    public void setWk15Day5Status(Integer wk15Day5Status) {
        this.wk15Day5Status = wk15Day5Status;
    }

    public BigDecimal getWk15Day6() {
        return wk15Day6;
    }

    public void setWk15Day6(BigDecimal wk15Day6) {
        this.wk15Day6 = wk15Day6;
    }

    public Integer getWk15Day6Status() {
        return wk15Day6Status;
    }

    public void setWk15Day6Status(Integer wk15Day6Status) {
        this.wk15Day6Status = wk15Day6Status;
    }

    public BigDecimal getWk15Day7() {
        return wk15Day7;
    }

    public void setWk15Day7(BigDecimal wk15Day7) {
        this.wk15Day7 = wk15Day7;
    }

    public Integer getWk15Day7Status() {
        return wk15Day7Status;
    }

    public void setWk15Day7Status(Integer wk15Day7Status) {
        this.wk15Day7Status = wk15Day7Status;
    }

    public BigDecimal getWk14Day1() {
        return wk14Day1;
    }

    public void setWk14Day1(BigDecimal wk14Day1) {
        this.wk14Day1 = wk14Day1;
    }

    public Integer getWk14Day1Status() {
        return wk14Day1Status;
    }

    public void setWk14Day1Status(Integer wk14Day1Status) {
        this.wk14Day1Status = wk14Day1Status;
    }

    public BigDecimal getWk14Day2() {
        return wk14Day2;
    }

    public void setWk14Day2(BigDecimal wk14Day2) {
        this.wk14Day2 = wk14Day2;
    }

    public Integer getWk14Day2Status() {
        return wk14Day2Status;
    }

    public void setWk14Day2Status(Integer wk14Day2Status) {
        this.wk14Day2Status = wk14Day2Status;
    }

    public BigDecimal getWk14Day3() {
        return wk14Day3;
    }

    public void setWk14Day3(BigDecimal wk14Day3) {
        this.wk14Day3 = wk14Day3;
    }

    public Integer getWk14Day3Status() {
        return wk14Day3Status;
    }

    public void setWk14Day3Status(Integer wk14Day3Status) {
        this.wk14Day3Status = wk14Day3Status;
    }

    public BigDecimal getWk14Day4() {
        return wk14Day4;
    }

    public void setWk14Day4(BigDecimal wk14Day4) {
        this.wk14Day4 = wk14Day4;
    }

    public Integer getWk14Day4Status() {
        return wk14Day4Status;
    }

    public void setWk14Day4Status(Integer wk14Day4Status) {
        this.wk14Day4Status = wk14Day4Status;
    }

    public BigDecimal getWk14Day5() {
        return wk14Day5;
    }

    public void setWk14Day5(BigDecimal wk14Day5) {
        this.wk14Day5 = wk14Day5;
    }

    public Integer getWk14Day5Status() {
        return wk14Day5Status;
    }

    public void setWk14Day5Status(Integer wk14Day5Status) {
        this.wk14Day5Status = wk14Day5Status;
    }

    public BigDecimal getWk14Day6() {
        return wk14Day6;
    }

    public void setWk14Day6(BigDecimal wk14Day6) {
        this.wk14Day6 = wk14Day6;
    }

    public Integer getWk14Day6Status() {
        return wk14Day6Status;
    }

    public void setWk14Day6Status(Integer wk14Day6Status) {
        this.wk14Day6Status = wk14Day6Status;
    }

    public BigDecimal getWk14Day7() {
        return wk14Day7;
    }

    public void setWk14Day7(BigDecimal wk14Day7) {
        this.wk14Day7 = wk14Day7;
    }

    public Integer getWk14Day7Status() {
        return wk14Day7Status;
    }

    public void setWk14Day7Status(Integer wk14Day7Status) {
        this.wk14Day7Status = wk14Day7Status;
    }

    public BigDecimal getWk13Day1() {
        return wk13Day1;
    }

    public void setWk13Day1(BigDecimal wk13Day1) {
        this.wk13Day1 = wk13Day1;
    }

    public Integer getWk13Day1Status() {
        return wk13Day1Status;
    }

    public void setWk13Day1Status(Integer wk13Day1Status) {
        this.wk13Day1Status = wk13Day1Status;
    }

    public BigDecimal getWk13Day2() {
        return wk13Day2;
    }

    public void setWk13Day2(BigDecimal wk13Day2) {
        this.wk13Day2 = wk13Day2;
    }

    public Integer getWk13Day2Status() {
        return wk13Day2Status;
    }

    public void setWk13Day2Status(Integer wk13Day2Status) {
        this.wk13Day2Status = wk13Day2Status;
    }

    public BigDecimal getWk13Day3() {
        return wk13Day3;
    }

    public void setWk13Day3(BigDecimal wk13Day3) {
        this.wk13Day3 = wk13Day3;
    }

    public Integer getWk13Day3Status() {
        return wk13Day3Status;
    }

    public void setWk13Day3Status(Integer wk13Day3Status) {
        this.wk13Day3Status = wk13Day3Status;
    }

    public BigDecimal getWk13Day4() {
        return wk13Day4;
    }

    public void setWk13Day4(BigDecimal wk13Day4) {
        this.wk13Day4 = wk13Day4;
    }

    public Integer getWk13Day4Status() {
        return wk13Day4Status;
    }

    public void setWk13Day4Status(Integer wk13Day4Status) {
        this.wk13Day4Status = wk13Day4Status;
    }

    public BigDecimal getWk13Day5() {
        return wk13Day5;
    }

    public void setWk13Day5(BigDecimal wk13Day5) {
        this.wk13Day5 = wk13Day5;
    }

    public Integer getWk13Day5Status() {
        return wk13Day5Status;
    }

    public void setWk13Day5Status(Integer wk13Day5Status) {
        this.wk13Day5Status = wk13Day5Status;
    }

    public BigDecimal getWk13Day6() {
        return wk13Day6;
    }

    public void setWk13Day6(BigDecimal wk13Day6) {
        this.wk13Day6 = wk13Day6;
    }

    public Integer getWk13Day6Status() {
        return wk13Day6Status;
    }

    public void setWk13Day6Status(Integer wk13Day6Status) {
        this.wk13Day6Status = wk13Day6Status;
    }

    public BigDecimal getWk13Day7() {
        return wk13Day7;
    }

    public void setWk13Day7(BigDecimal wk13Day7) {
        this.wk13Day7 = wk13Day7;
    }

    public Integer getWk13Day7Status() {
        return wk13Day7Status;
    }

    public void setWk13Day7Status(Integer wk13Day7Status) {
        this.wk13Day7Status = wk13Day7Status;
    }

    public BigDecimal getWk12Day1() {
        return wk12Day1;
    }

    public void setWk12Day1(BigDecimal wk12Day1) {
        this.wk12Day1 = wk12Day1;
    }

    public Integer getWk12Day1Status() {
        return wk12Day1Status;
    }

    public void setWk12Day1Status(Integer wk12Day1Status) {
        this.wk12Day1Status = wk12Day1Status;
    }

    public BigDecimal getWk12Day2() {
        return wk12Day2;
    }

    public void setWk12Day2(BigDecimal wk12Day2) {
        this.wk12Day2 = wk12Day2;
    }

    public Integer getWk12Day2Status() {
        return wk12Day2Status;
    }

    public void setWk12Day2Status(Integer wk12Day2Status) {
        this.wk12Day2Status = wk12Day2Status;
    }

    public BigDecimal getWk12Day3() {
        return wk12Day3;
    }

    public void setWk12Day3(BigDecimal wk12Day3) {
        this.wk12Day3 = wk12Day3;
    }

    public Integer getWk12Day3Status() {
        return wk12Day3Status;
    }

    public void setWk12Day3Status(Integer wk12Day3Status) {
        this.wk12Day3Status = wk12Day3Status;
    }

    public BigDecimal getWk12Day4() {
        return wk12Day4;
    }

    public void setWk12Day4(BigDecimal wk12Day4) {
        this.wk12Day4 = wk12Day4;
    }

    public Integer getWk12Day4Status() {
        return wk12Day4Status;
    }

    public void setWk12Day4Status(Integer wk12Day4Status) {
        this.wk12Day4Status = wk12Day4Status;
    }

    public BigDecimal getWk12Day5() {
        return wk12Day5;
    }

    public void setWk12Day5(BigDecimal wk12Day5) {
        this.wk12Day5 = wk12Day5;
    }

    public Integer getWk12Day5Status() {
        return wk12Day5Status;
    }

    public void setWk12Day5Status(Integer wk12Day5Status) {
        this.wk12Day5Status = wk12Day5Status;
    }

    public BigDecimal getWk12Day6() {
        return wk12Day6;
    }

    public void setWk12Day6(BigDecimal wk12Day6) {
        this.wk12Day6 = wk12Day6;
    }

    public Integer getWk12Day6Status() {
        return wk12Day6Status;
    }

    public void setWk12Day6Status(Integer wk12Day6Status) {
        this.wk12Day6Status = wk12Day6Status;
    }

    public BigDecimal getWk12Day7() {
        return wk12Day7;
    }

    public void setWk12Day7(BigDecimal wk12Day7) {
        this.wk12Day7 = wk12Day7;
    }

    public Integer getWk12Day7Status() {
        return wk12Day7Status;
    }

    public void setWk12Day7Status(Integer wk12Day7Status) {
        this.wk12Day7Status = wk12Day7Status;
    }

    public BigDecimal getWk11Day1() {
        return wk11Day1;
    }

    public void setWk11Day1(BigDecimal wk11Day1) {
        this.wk11Day1 = wk11Day1;
    }

    public Integer getWk11Day1Status() {
        return wk11Day1Status;
    }

    public void setWk11Day1Status(Integer wk11Day1Status) {
        this.wk11Day1Status = wk11Day1Status;
    }

    public BigDecimal getWk11Day2() {
        return wk11Day2;
    }

    public void setWk11Day2(BigDecimal wk11Day2) {
        this.wk11Day2 = wk11Day2;
    }

    public Integer getWk11Day2Status() {
        return wk11Day2Status;
    }

    public void setWk11Day2Status(Integer wk11Day2Status) {
        this.wk11Day2Status = wk11Day2Status;
    }

    public BigDecimal getWk11Day3() {
        return wk11Day3;
    }

    public void setWk11Day3(BigDecimal wk11Day3) {
        this.wk11Day3 = wk11Day3;
    }

    public Integer getWk11Day3Status() {
        return wk11Day3Status;
    }

    public void setWk11Day3Status(Integer wk11Day3Status) {
        this.wk11Day3Status = wk11Day3Status;
    }

    public BigDecimal getWk11Day4() {
        return wk11Day4;
    }

    public void setWk11Day4(BigDecimal wk11Day4) {
        this.wk11Day4 = wk11Day4;
    }

    public Integer getWk11Day4Status() {
        return wk11Day4Status;
    }

    public void setWk11Day4Status(Integer wk11Day4Status) {
        this.wk11Day4Status = wk11Day4Status;
    }

    public BigDecimal getWk11Day5() {
        return wk11Day5;
    }

    public void setWk11Day5(BigDecimal wk11Day5) {
        this.wk11Day5 = wk11Day5;
    }

    public Integer getWk11Day5Status() {
        return wk11Day5Status;
    }

    public void setWk11Day5Status(Integer wk11Day5Status) {
        this.wk11Day5Status = wk11Day5Status;
    }

    public BigDecimal getWk11Day6() {
        return wk11Day6;
    }

    public void setWk11Day6(BigDecimal wk11Day6) {
        this.wk11Day6 = wk11Day6;
    }

    public Integer getWk11Day6Status() {
        return wk11Day6Status;
    }

    public void setWk11Day6Status(Integer wk11Day6Status) {
        this.wk11Day6Status = wk11Day6Status;
    }

    public BigDecimal getWk11Day7() {
        return wk11Day7;
    }

    public void setWk11Day7(BigDecimal wk11Day7) {
        this.wk11Day7 = wk11Day7;
    }

    public Integer getWk11Day7Status() {
        return wk11Day7Status;
    }

    public void setWk11Day7Status(Integer wk11Day7Status) {
        this.wk11Day7Status = wk11Day7Status;
    }

    public BigDecimal getWk10Day1() {
        return wk10Day1;
    }

    public void setWk10Day1(BigDecimal wk10Day1) {
        this.wk10Day1 = wk10Day1;
    }

    public Integer getWk10Day1Status() {
        return wk10Day1Status;
    }

    public void setWk10Day1Status(Integer wk10Day1Status) {
        this.wk10Day1Status = wk10Day1Status;
    }

    public BigDecimal getWk10Day2() {
        return wk10Day2;
    }

    public void setWk10Day2(BigDecimal wk10Day2) {
        this.wk10Day2 = wk10Day2;
    }

    public Integer getWk10Day2Status() {
        return wk10Day2Status;
    }

    public void setWk10Day2Status(Integer wk10Day2Status) {
        this.wk10Day2Status = wk10Day2Status;
    }

    public BigDecimal getWk10Day3() {
        return wk10Day3;
    }

    public void setWk10Day3(BigDecimal wk10Day3) {
        this.wk10Day3 = wk10Day3;
    }

    public Integer getWk10Day3Status() {
        return wk10Day3Status;
    }

    public void setWk10Day3Status(Integer wk10Day3Status) {
        this.wk10Day3Status = wk10Day3Status;
    }

    public BigDecimal getWk10Day4() {
        return wk10Day4;
    }

    public void setWk10Day4(BigDecimal wk10Day4) {
        this.wk10Day4 = wk10Day4;
    }

    public Integer getWk10Day4Status() {
        return wk10Day4Status;
    }

    public void setWk10Day4Status(Integer wk10Day4Status) {
        this.wk10Day4Status = wk10Day4Status;
    }

    public BigDecimal getWk10Day5() {
        return wk10Day5;
    }

    public void setWk10Day5(BigDecimal wk10Day5) {
        this.wk10Day5 = wk10Day5;
    }

    public Integer getWk10Day5Status() {
        return wk10Day5Status;
    }

    public void setWk10Day5Status(Integer wk10Day5Status) {
        this.wk10Day5Status = wk10Day5Status;
    }

    public BigDecimal getWk10Day6() {
        return wk10Day6;
    }

    public void setWk10Day6(BigDecimal wk10Day6) {
        this.wk10Day6 = wk10Day6;
    }

    public Integer getWk10Day6Status() {
        return wk10Day6Status;
    }

    public void setWk10Day6Status(Integer wk10Day6Status) {
        this.wk10Day6Status = wk10Day6Status;
    }

    public BigDecimal getWk10Day7() {
        return wk10Day7;
    }

    public void setWk10Day7(BigDecimal wk10Day7) {
        this.wk10Day7 = wk10Day7;
    }

    public Integer getWk10Day7Status() {
        return wk10Day7Status;
    }

    public void setWk10Day7Status(Integer wk10Day7Status) {
        this.wk10Day7Status = wk10Day7Status;
    }




    public Integer getWk5Day7Status() {
        return wk5Day7Status;
    }

    public void setWk5Day7Status(Integer wk5Day7Status) {
        this.wk5Day7Status = wk5Day7Status;
    }

    public BigDecimal getWk5Day7() {
        return wk5Day7;
    }

    public void setWk5Day7(BigDecimal wk5Day7) {
        this.wk5Day7 = wk5Day7;
    }

    public Integer getWk5Day6Status() {
        return wk5Day6Status;
    }

    public void setWk5Day6Status(Integer wk5Day6Status) {
        this.wk5Day6Status = wk5Day6Status;
    }

    public BigDecimal getWk5Day6() {
        return wk5Day6;
    }

    public void setWk5Day6(BigDecimal wk5Day6) {
        this.wk5Day6 = wk5Day6;
    }

    public Integer getWk5Day5Status() {
        return wk5Day5Status;
    }

    public void setWk5Day5Status(Integer wk5Day5Status) {
        this.wk5Day5Status = wk5Day5Status;
    }

    public BigDecimal getWk5Day5() {
        return wk5Day5;
    }

    public void setWk5Day5(BigDecimal wk5Day5) {
        this.wk5Day5 = wk5Day5;
    }

    public Integer getWk5Day4Status() {
        return wk5Day4Status;
    }

    public void setWk5Day4Status(Integer wk5Day4Status) {
        this.wk5Day4Status = wk5Day4Status;
    }

    public BigDecimal getWk5Day4() {
        return wk5Day4;
    }

    public void setWk5Day4(BigDecimal wk5Day4) {
        this.wk5Day4 = wk5Day4;
    }

    public Integer getWk5Day3Status() {
        return wk5Day3Status;
    }

    public void setWk5Day3Status(Integer wk5Day3Status) {
        this.wk5Day3Status = wk5Day3Status;
    }

    public BigDecimal getWk5Day3() {
        return wk5Day3;
    }

    public void setWk5Day3(BigDecimal wk5Day3) {
        this.wk5Day3 = wk5Day3;
    }

    public Integer getWk5Day2Status() {
        return wk5Day2Status;
    }

    public void setWk5Day2Status(Integer wk5Day2Status) {
        this.wk5Day2Status = wk5Day2Status;
    }

    public BigDecimal getWk5Day2() {
        return wk5Day2;
    }

    public void setWk5Day2(BigDecimal wk5Day2) {
        this.wk5Day2 = wk5Day2;
    }

    public Integer getWk5Day1Status() {
        return wk5Day1Status;
    }

    public void setWk5Day1Status(Integer wk5Day1Status) {
        this.wk5Day1Status = wk5Day1Status;
    }

    public BigDecimal getWk5Day1() {
        return wk5Day1;
    }

    public void setWk5Day1(BigDecimal wk5Day1) {
        this.wk5Day1 = wk5Day1;
    }

    public Integer getWk6Day7Status() {
        return wk6Day7Status;
    }

    public void setWk6Day7Status(Integer wk6Day7Status) {
        this.wk6Day7Status = wk6Day7Status;
    }

    public BigDecimal getWk6Day7() {
        return wk6Day7;
    }

    public void setWk6Day7(BigDecimal wk6Day7) {
        this.wk6Day7 = wk6Day7;
    }

    public Integer getWk6Day6Status() {
        return wk6Day6Status;
    }

    public void setWk6Day6Status(Integer wk6Day6Status) {
        this.wk6Day6Status = wk6Day6Status;
    }

    public BigDecimal getWk6Day6() {
        return wk6Day6;
    }

    public void setWk6Day6(BigDecimal wk6Day6) {
        this.wk6Day6 = wk6Day6;
    }

    public Integer getWk6Day5Status() {
        return wk6Day5Status;
    }

    public void setWk6Day5Status(Integer wk6Day5Status) {
        this.wk6Day5Status = wk6Day5Status;
    }

    public BigDecimal getWk6Day5() {
        return wk6Day5;
    }

    public void setWk6Day5(BigDecimal wk6Day5) {
        this.wk6Day5 = wk6Day5;
    }

    public Integer getWk6Day4Status() {
        return wk6Day4Status;
    }

    public void setWk6Day4Status(Integer wk6Day4Status) {
        this.wk6Day4Status = wk6Day4Status;
    }

    public BigDecimal getWk6Day4() {
        return wk6Day4;
    }

    public void setWk6Day4(BigDecimal wk6Day4) {
        this.wk6Day4 = wk6Day4;
    }

    public Integer getWk6Day3Status() {
        return wk6Day3Status;
    }

    public void setWk6Day3Status(Integer wk6Day3Status) {
        this.wk6Day3Status = wk6Day3Status;
    }

    public BigDecimal getWk6Day3() {
        return wk6Day3;
    }

    public void setWk6Day3(BigDecimal wk6Day3) {
        this.wk6Day3 = wk6Day3;
    }

    public Integer getWk6Day2Status() {
        return wk6Day2Status;
    }

    public void setWk6Day2Status(Integer wk6Day2Status) {
        this.wk6Day2Status = wk6Day2Status;
    }

    public BigDecimal getWk6Day2() {
        return wk6Day2;
    }

    public void setWk6Day2(BigDecimal wk6Day2) {
        this.wk6Day2 = wk6Day2;
    }

    public Integer getWk6Day1Status() {
        return wk6Day1Status;
    }

    public void setWk6Day1Status(Integer wk6Day1Status) {
        this.wk6Day1Status = wk6Day1Status;
    }

    public BigDecimal getWk6Day1() {
        return wk6Day1;
    }

    public void setWk6Day1(BigDecimal wk6Day1) {
        this.wk6Day1 = wk6Day1;
    }

    public Integer getWk7Day7Status() {
        return wk7Day7Status;
    }

    public void setWk7Day7Status(Integer wk7Day7Status) {
        this.wk7Day7Status = wk7Day7Status;
    }

    public BigDecimal getWk7Day7() {
        return wk7Day7;
    }

    public void setWk7Day7(BigDecimal wk7Day7) {
        this.wk7Day7 = wk7Day7;
    }

    public Integer getWk7Day6Status() {
        return wk7Day6Status;
    }

    public void setWk7Day6Status(Integer wk7Day6Status) {
        this.wk7Day6Status = wk7Day6Status;
    }

    public BigDecimal getWk7Day6() {
        return wk7Day6;
    }

    public void setWk7Day6(BigDecimal wk7Day6) {
        this.wk7Day6 = wk7Day6;
    }

    public Integer getWk7Day5Status() {
        return wk7Day5Status;
    }

    public void setWk7Day5Status(Integer wk7Day5Status) {
        this.wk7Day5Status = wk7Day5Status;
    }

    public BigDecimal getWk7Day5() {
        return wk7Day5;
    }

    public void setWk7Day5(BigDecimal wk7Day5) {
        this.wk7Day5 = wk7Day5;
    }

    public Integer getWk7Day4Status() {
        return wk7Day4Status;
    }

    public void setWk7Day4Status(Integer wk7Day4Status) {
        this.wk7Day4Status = wk7Day4Status;
    }

    public BigDecimal getWk7Day4() {
        return wk7Day4;
    }

    public void setWk7Day4(BigDecimal wk7Day4) {
        this.wk7Day4 = wk7Day4;
    }

    public Integer getWk7Day3Status() {
        return wk7Day3Status;
    }

    public void setWk7Day3Status(Integer wk7Day3Status) {
        this.wk7Day3Status = wk7Day3Status;
    }

    public BigDecimal getWk7Day3() {
        return wk7Day3;
    }

    public void setWk7Day3(BigDecimal wk7Day3) {
        this.wk7Day3 = wk7Day3;
    }

    public Integer getWk7Day2Status() {
        return wk7Day2Status;
    }

    public void setWk7Day2Status(Integer wk7Day2Status) {
        this.wk7Day2Status = wk7Day2Status;
    }

    public BigDecimal getWk7Day2() {
        return wk7Day2;
    }

    public void setWk7Day2(BigDecimal wk7Day2) {
        this.wk7Day2 = wk7Day2;
    }

    public Integer getWk7Day1Status() {
        return wk7Day1Status;
    }

    public void setWk7Day1Status(Integer wk7Day1Status) {
        this.wk7Day1Status = wk7Day1Status;
    }

    public BigDecimal getWk7Day1() {
        return wk7Day1;
    }

    public void setWk7Day1(BigDecimal wk7Day1) {
        this.wk7Day1 = wk7Day1;
    }

    public Integer getWk8Day7Status() {
        return wk8Day7Status;
    }

    public void setWk8Day7Status(Integer wk8Day7Status) {
        this.wk8Day7Status = wk8Day7Status;
    }

    public BigDecimal getWk8Day7() {
        return wk8Day7;
    }

    public void setWk8Day7(BigDecimal wk8Day7) {
        this.wk8Day7 = wk8Day7;
    }

    public Integer getWk8Day6Status() {
        return wk8Day6Status;
    }

    public void setWk8Day6Status(Integer wk8Day6Status) {
        this.wk8Day6Status = wk8Day6Status;
    }

    public BigDecimal getWk8Day6() {
        return wk8Day6;
    }

    public void setWk8Day6(BigDecimal wk8Day6) {
        this.wk8Day6 = wk8Day6;
    }

    public Integer getWk8Day5Status() {
        return wk8Day5Status;
    }

    public void setWk8Day5Status(Integer wk8Day5Status) {
        this.wk8Day5Status = wk8Day5Status;
    }

    public BigDecimal getWk8Day5() {
        return wk8Day5;
    }

    public void setWk8Day5(BigDecimal wk8Day5) {
        this.wk8Day5 = wk8Day5;
    }

    public Integer getWk8Day4Status() {
        return wk8Day4Status;
    }

    public void setWk8Day4Status(Integer wk8Day4Status) {
        this.wk8Day4Status = wk8Day4Status;
    }

    public BigDecimal getWk8Day4() {
        return wk8Day4;
    }

    public void setWk8Day4(BigDecimal wk8Day4) {
        this.wk8Day4 = wk8Day4;
    }

    public Integer getWk8Day3Status() {
        return wk8Day3Status;
    }

    public void setWk8Day3Status(Integer wk8Day3Status) {
        this.wk8Day3Status = wk8Day3Status;
    }

    public BigDecimal getWk8Day3() {
        return wk8Day3;
    }

    public void setWk8Day3(BigDecimal wk8Day3) {
        this.wk8Day3 = wk8Day3;
    }

    public Integer getWk8Day2Status() {
        return wk8Day2Status;
    }

    public void setWk8Day2Status(Integer wk8Day2Status) {
        this.wk8Day2Status = wk8Day2Status;
    }

    public BigDecimal getWk8Day2() {
        return wk8Day2;
    }

    public void setWk8Day2(BigDecimal wk8Day2) {
        this.wk8Day2 = wk8Day2;
    }

    public Integer getWk8Day1Status() {
        return wk8Day1Status;
    }

    public void setWk8Day1Status(Integer wk8Day1Status) {
        this.wk8Day1Status = wk8Day1Status;
    }

    public BigDecimal getWk8Day1() {
        return wk8Day1;
    }

    public void setWk8Day1(BigDecimal wk8Day1) {
        this.wk8Day1 = wk8Day1;
    }

    public Integer getWk9Day7Status() {
        return wk9Day7Status;
    }

    public void setWk9Day7Status(Integer wk9Day7Status) {
        this.wk9Day7Status = wk9Day7Status;
    }

    public BigDecimal getWk9Day7() {
        return wk9Day7;
    }

    public void setWk9Day7(BigDecimal wk9Day7) {
        this.wk9Day7 = wk9Day7;
    }

    public Integer getWk9Day6Status() {
        return wk9Day6Status;
    }

    public void setWk9Day6Status(Integer wk9Day6Status) {
        this.wk9Day6Status = wk9Day6Status;
    }

    public BigDecimal getWk9Day6() {
        return wk9Day6;
    }

    public void setWk9Day6(BigDecimal wk9Day6) {
        this.wk9Day6 = wk9Day6;
    }

    public Integer getWk9Day5Status() {
        return wk9Day5Status;
    }

    public void setWk9Day5Status(Integer wk9Day5Status) {
        this.wk9Day5Status = wk9Day5Status;
    }

    public BigDecimal getWk9Day5() {
        return wk9Day5;
    }

    public void setWk9Day5(BigDecimal wk9Day5) {
        this.wk9Day5 = wk9Day5;
    }

    public Integer getWk9Day4Status() {
        return wk9Day4Status;
    }

    public void setWk9Day4Status(Integer wk9Day4Status) {
        this.wk9Day4Status = wk9Day4Status;
    }

    public BigDecimal getWk9Day4() {
        return wk9Day4;
    }

    public void setWk9Day4(BigDecimal wk9Day4) {
        this.wk9Day4 = wk9Day4;
    }

    public Integer getWk9Day3Status() {
        return wk9Day3Status;
    }

    public void setWk9Day3Status(Integer wk9Day3Status) {
        this.wk9Day3Status = wk9Day3Status;
    }

    public BigDecimal getWk9Day3() {
        return wk9Day3;
    }

    public void setWk9Day3(BigDecimal wk9Day3) {
        this.wk9Day3 = wk9Day3;
    }

    public Integer getWk9Day2Status() {
        return wk9Day2Status;
    }

    public void setWk9Day2Status(Integer wk9Day2Status) {
        this.wk9Day2Status = wk9Day2Status;
    }

    public BigDecimal getWk9Day2() {
        return wk9Day2;
    }

    public void setWk9Day2(BigDecimal wk9Day2) {
        this.wk9Day2 = wk9Day2;
    }

    public Integer getWk9Day1Status() {
        return wk9Day1Status;
    }

    public void setWk9Day1Status(Integer wk9Day1Status) {
        this.wk9Day1Status = wk9Day1Status;
    }

    public BigDecimal getWk9Day1() {
        return wk9Day1;
    }

    public void setWk9Day1(BigDecimal wk9Day1) {
        this.wk9Day1 = wk9Day1;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getDisposeId() {
        return disposeId;
    }

    public void setDisposeId(Long disposeId) {
        this.disposeId = disposeId;
    }

    public String getDisposeName() {
        return disposeName;
    }

    public void setDisposeName(String disposeName) {
        this.disposeName = disposeName;
    }

    public Integer getModelSortId() {
        return modelSortId;
    }

    public void setModelSortId(Integer modelSortId) {
        this.modelSortId = modelSortId;
    }

    public Integer getDisposeSortId() {
        return disposeSortId;
    }

    public void setDisposeSortId(Integer disposeSortId) {
        this.disposeSortId = disposeSortId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<String> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<String> customerList) {
        this.customerList = customerList;
    }

    public List<String> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<String> skuList) {
        this.skuList = skuList;
    }

    public Long getMsrpId() {
        return msrpId;
    }

    public void setMsrpId(Long msrpId) {
        this.msrpId = msrpId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public BigDecimal getMsrp() {
        return msrp;
    }

    public void setMsrp(BigDecimal msrp) {
        this.msrp = msrp;
    }

    public BigDecimal getMsrpCountry() {
        return msrpCountry;
    }

    public void setMsrpCountry(BigDecimal msrpCountry) {
        this.msrpCountry = msrpCountry;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public BigDecimal getSellingPriceUSD() {
        return sellingPriceUSD;
    }

    public void setSellingPriceUSD(BigDecimal sellingPriceUSD) {
        this.sellingPriceUSD = sellingPriceUSD;
    }

    public BigDecimal getWk1Msrp() {
        return wk1Msrp;
    }

    public void setWk1Msrp(BigDecimal wk1Msrp) {
        this.wk1Msrp = wk1Msrp;
    }

    public BigDecimal getWk1MsrpUSD() {
        return wk1MsrpUSD;
    }

    public void setWk1MsrpUSD(BigDecimal wk1MsrpUSD) {
        this.wk1MsrpUSD = wk1MsrpUSD;
    }

    public Integer getWk1Status() {
        return wk1Status;
    }

    public void setWk1Status(Integer wk1Status) {
        this.wk1Status = wk1Status;
    }

    public BigDecimal getWk2Msrp() {
        return wk2Msrp;
    }

    public void setWk2Msrp(BigDecimal wk2Msrp) {
        this.wk2Msrp = wk2Msrp;
    }

    public BigDecimal getWk2MsrpUSD() {
        return wk2MsrpUSD;
    }

    public void setWk2MsrpUSD(BigDecimal wk2MsrpUSD) {
        this.wk2MsrpUSD = wk2MsrpUSD;
    }

    public Integer getWk2Status() {
        return wk2Status;
    }

    public void setWk2Status(Integer wk2Status) {
        this.wk2Status = wk2Status;
    }

    public BigDecimal getWk3Msrp() {
        return wk3Msrp;
    }

    public BigDecimal getWk3MsrpUSD() {
        return wk3MsrpUSD;
    }

    public void setWk3MsrpUSD(BigDecimal wk3MsrpUSD) {
        this.wk3MsrpUSD = wk3MsrpUSD;
    }

    public Integer getWk3Status() {
        return wk3Status;
    }

    public void setWk3Status(Integer wk3Status) {
        this.wk3Status = wk3Status;
    }

    public BigDecimal getWk4Msrp() {
        return wk4Msrp;
    }

    public void setWk4Msrp(BigDecimal wk4Msrp) {
        this.wk4Msrp = wk4Msrp;
    }

    public BigDecimal getWk4MsrpUSD() {
        return wk4MsrpUSD;
    }

    public void setWk4MsrpUSD(BigDecimal wk4MsrpUSD) {
        this.wk4MsrpUSD = wk4MsrpUSD;
    }

    public Integer getWk4Status() {
        return wk4Status;
    }

    public void setWk4Status(Integer wk4Status) {
        this.wk4Status = wk4Status;
    }

    public BigDecimal getWk5Msrp() {
        return wk5Msrp;
    }

    public void setWk5Msrp(BigDecimal wk5Msrp) {
        this.wk5Msrp = wk5Msrp;
    }

    public BigDecimal getWk5MsrpUSD() {
        return wk5MsrpUSD;
    }

    public void setWk5MsrpUSD(BigDecimal wk5MsrpUSD) {
        this.wk5MsrpUSD = wk5MsrpUSD;
    }

    public Integer getWk5Status() {
        return wk5Status;
    }

    public void setWk5Status(Integer wk5Status) {
        this.wk5Status = wk5Status;
    }

    public BigDecimal getWk6Msrp() {
        return wk6Msrp;
    }

    public void setWk6Msrp(BigDecimal wk6Msrp) {
        this.wk6Msrp = wk6Msrp;
    }

    public BigDecimal getWk6MsrpUSD() {
        return wk6MsrpUSD;
    }

    public void setWk6MsrpUSD(BigDecimal wk6MsrpUSD) {
        this.wk6MsrpUSD = wk6MsrpUSD;
    }

    public Integer getWk6Status() {
        return wk6Status;
    }

    public void setWk6Status(Integer wk6Status) {
        this.wk6Status = wk6Status;
    }

    public BigDecimal getWk7Msrp() {
        return wk7Msrp;
    }

    public void setWk7Msrp(BigDecimal wk7Msrp) {
        this.wk7Msrp = wk7Msrp;
    }

    public BigDecimal getWk7MsrpUSD() {
        return wk7MsrpUSD;
    }

    public void setWk7MsrpUSD(BigDecimal wk7MsrpUSD) {
        this.wk7MsrpUSD = wk7MsrpUSD;
    }

    public Integer getWk7Status() {
        return wk7Status;
    }

    public void setWk7Status(Integer wk7Status) {
        this.wk7Status = wk7Status;
    }

    public BigDecimal getWk8Msrp() {
        return wk8Msrp;
    }

    public void setWk8Msrp(BigDecimal wk8Msrp) {
        this.wk8Msrp = wk8Msrp;
    }

    public BigDecimal getWk8MsrpUSD() {
        return wk8MsrpUSD;
    }

    public void setWk8MsrpUSD(BigDecimal wk8MsrpUSD) {
        this.wk8MsrpUSD = wk8MsrpUSD;
    }

    public Integer getWk8Status() {
        return wk8Status;
    }

    public void setWk8Status(Integer wk8Status) {
        this.wk8Status = wk8Status;
    }

    public BigDecimal getWk9Msrp() {
        return wk9Msrp;
    }

    public void setWk9Msrp(BigDecimal wk9Msrp) {
        this.wk9Msrp = wk9Msrp;
    }

    public BigDecimal getWk9MsrpUSD() {
        return wk9MsrpUSD;
    }

    public void setWk9MsrpUSD(BigDecimal wk9MsrpUSD) {
        this.wk9MsrpUSD = wk9MsrpUSD;
    }

    public Integer getWk9Status() {
        return wk9Status;
    }

    public void setWk9Status(Integer wk9Status) {
        this.wk9Status = wk9Status;
    }

    public String getWk30Day7Date() {
        return wk30Day7Date;
    }

    public void setWk30Day7Date(String wk30Day7Date) {
        this.wk30Day7Date = wk30Day7Date;
    }

    public String getWk30Day6Date() {
        return wk30Day6Date;
    }

    public void setWk30Day6Date(String wk30Day6Date) {
        this.wk30Day6Date = wk30Day6Date;
    }

    public String getWk30Day5Date() {
        return wk30Day5Date;
    }

    public void setWk30Day5Date(String wk30Day5Date) {
        this.wk30Day5Date = wk30Day5Date;
    }

    public String getWk30Day4Date() {
        return wk30Day4Date;
    }

    public void setWk30Day4Date(String wk30Day4Date) {
        this.wk30Day4Date = wk30Day4Date;
    }

    public String getWk30Day3Date() {
        return wk30Day3Date;
    }

    public void setWk30Day3Date(String wk30Day3Date) {
        this.wk30Day3Date = wk30Day3Date;
    }

    public String getWk30Day2Date() {
        return wk30Day2Date;
    }

    public void setWk30Day2Date(String wk30Day2Date) {
        this.wk30Day2Date = wk30Day2Date;
    }

    public String getWk30Day1Date() {
        return wk30Day1Date;
    }

    public void setWk30Day1Date(String wk30Day1Date) {
        this.wk30Day1Date = wk30Day1Date;
    }

    public String getWk29Day7Date() {
        return wk29Day7Date;
    }

    public void setWk29Day7Date(String wk29Day7Date) {
        this.wk29Day7Date = wk29Day7Date;
    }

    public String getWk29Day6Date() {
        return wk29Day6Date;
    }

    public void setWk29Day6Date(String wk29Day6Date) {
        this.wk29Day6Date = wk29Day6Date;
    }

    public String getWk29Day5Date() {
        return wk29Day5Date;
    }

    public void setWk29Day5Date(String wk29Day5Date) {
        this.wk29Day5Date = wk29Day5Date;
    }

    public String getWk29Day4Date() {
        return wk29Day4Date;
    }

    public void setWk29Day4Date(String wk29Day4Date) {
        this.wk29Day4Date = wk29Day4Date;
    }

    public String getWk29Day3Date() {
        return wk29Day3Date;
    }

    public void setWk29Day3Date(String wk29Day3Date) {
        this.wk29Day3Date = wk29Day3Date;
    }

    public String getWk29Day2Date() {
        return wk29Day2Date;
    }

    public void setWk29Day2Date(String wk29Day2Date) {
        this.wk29Day2Date = wk29Day2Date;
    }

    public String getWk29Day1Date() {
        return wk29Day1Date;
    }

    public void setWk29Day1Date(String wk29Day1Date) {
        this.wk29Day1Date = wk29Day1Date;
    }

    public String getWk1Day1Date() {
        return wk1Day1Date;
    }

    public void setWk1Day1Date(String wk1Day1Date) {
        this.wk1Day1Date = wk1Day1Date;
    }

    public String getValidFromWK() {
        return validFromWK;
    }

    public void setValidFromWK(String validFromWK) {
        this.validFromWK = validFromWK;
    }

    public String getValidToWK() {
        return validToWK;
    }

    public void setValidToWK(String validToWK) {
        this.validToWK = validToWK;
    }

    public String getWk28Day1Date() {
        return wk28Day1Date;
    }

    public void setWk28Day1Date(String wk28Day1Date) {
        this.wk28Day1Date = wk28Day1Date;
    }

    public String getWk28Day2Date() {
        return wk28Day2Date;
    }

    public void setWk28Day2Date(String wk28Day2Date) {
        this.wk28Day2Date = wk28Day2Date;
    }

    public String getWk28Day3Date() {
        return wk28Day3Date;
    }

    public void setWk28Day3Date(String wk28Day3Date) {
        this.wk28Day3Date = wk28Day3Date;
    }

    public String getWk28Day4Date() {
        return wk28Day4Date;
    }

    public void setWk28Day4Date(String wk28Day4Date) {
        this.wk28Day4Date = wk28Day4Date;
    }

    public String getWk28Day5Date() {
        return wk28Day5Date;
    }

    public void setWk28Day5Date(String wk28Day5Date) {
        this.wk28Day5Date = wk28Day5Date;
    }

    public String getWk28Day6Date() {
        return wk28Day6Date;
    }

    public void setWk28Day6Date(String wk28Day6Date) {
        this.wk28Day6Date = wk28Day6Date;
    }

    public String getWk28Day7Date() {
        return wk28Day7Date;
    }

    public void setWk28Day7Date(String wk28Day7Date) {
        this.wk28Day7Date = wk28Day7Date;
    }

    public String getWk31Day1Date() {
        return wk31Day1Date;
    }

    public void setWk31Day1Date(String wk31Day1Date) {
        this.wk31Day1Date = wk31Day1Date;
    }

    public String getWk31Day2Date() {
        return wk31Day2Date;
    }

    public void setWk31Day2Date(String wk31Day2Date) {
        this.wk31Day2Date = wk31Day2Date;
    }

    public String getWk31Day3Date() {
        return wk31Day3Date;
    }

    public void setWk31Day3Date(String wk31Day3Date) {
        this.wk31Day3Date = wk31Day3Date;
    }

    public String getWk31Day4Date() {
        return wk31Day4Date;
    }

    public void setWk31Day4Date(String wk31Day4Date) {
        this.wk31Day4Date = wk31Day4Date;
    }

    public String getWk31Day5Date() {
        return wk31Day5Date;
    }

    public void setWk31Day5Date(String wk31Day5Date) {
        this.wk31Day5Date = wk31Day5Date;
    }

    public String getWk31Day6Date() {
        return wk31Day6Date;
    }

    public void setWk31Day6Date(String wk31Day6Date) {
        this.wk31Day6Date = wk31Day6Date;
    }

    public String getWk31Day7Date() {
        return wk31Day7Date;
    }

    public void setWk31Day7Date(String wk31Day7Date) {
        this.wk31Day7Date = wk31Day7Date;
    }

    public String getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(String promotionType) {
        this.promotionType = promotionType;
    }

    public String getWk1Day2Date() {
        return wk1Day2Date;
    }

    public void setWk1Day2Date(String wk1Day2Date) {
        this.wk1Day2Date = wk1Day2Date;
    }

    public String getWk1Day3Date() {
        return wk1Day3Date;
    }

    public void setWk1Day3Date(String wk1Day3Date) {
        this.wk1Day3Date = wk1Day3Date;
    }

    public String getWk1Day4Date() {
        return wk1Day4Date;
    }

    public void setWk1Day4Date(String wk1Day4Date) {
        this.wk1Day4Date = wk1Day4Date;
    }

    public String getWk1Day5Date() {
        return wk1Day5Date;
    }

    public void setWk1Day5Date(String wk1Day5Date) {
        this.wk1Day5Date = wk1Day5Date;
    }

    public String getWk1Day6Date() {
        return wk1Day6Date;
    }

    public void setWk1Day6Date(String wk1Day6Date) {
        this.wk1Day6Date = wk1Day6Date;
    }

    public String getWk1Day7Date() {
        return wk1Day7Date;
    }

    public void setWk1Day7Date(String wk1Day7Date) {
        this.wk1Day7Date = wk1Day7Date;
    }

    public String getWk2Day1Date() {
        return wk2Day1Date;
    }

    public void setWk2Day1Date(String wk2Day1Date) {
        this.wk2Day1Date = wk2Day1Date;
    }

    public String getWk2Day2Date() {
        return wk2Day2Date;
    }

    public void setWk2Day2Date(String wk2Day2Date) {
        this.wk2Day2Date = wk2Day2Date;
    }

    public String getWk2Day3Date() {
        return wk2Day3Date;
    }

    public void setWk2Day3Date(String wk2Day3Date) {
        this.wk2Day3Date = wk2Day3Date;
    }

    public String getWk2Day4Date() {
        return wk2Day4Date;
    }

    public void setWk2Day4Date(String wk2Day4Date) {
        this.wk2Day4Date = wk2Day4Date;
    }

    public String getWk2Day5Date() {
        return wk2Day5Date;
    }

    public void setWk2Day5Date(String wk2Day5Date) {
        this.wk2Day5Date = wk2Day5Date;
    }

    public String getWk2Day6Date() {
        return wk2Day6Date;
    }

    public void setWk2Day6Date(String wk2Day6Date) {
        this.wk2Day6Date = wk2Day6Date;
    }

    public String getWk2Day7Date() {
        return wk2Day7Date;
    }

    public void setWk2Day7Date(String wk2Day7Date) {
        this.wk2Day7Date = wk2Day7Date;
    }

    public String getWk3Day1Date() {
        return wk3Day1Date;
    }

    public void setWk3Day1Date(String wk3Day1Date) {
        this.wk3Day1Date = wk3Day1Date;
    }

    public String getWk3Day2Date() {
        return wk3Day2Date;
    }

    public void setWk3Day2Date(String wk3Day2Date) {
        this.wk3Day2Date = wk3Day2Date;
    }

    public String getWk3Day3Date() {
        return wk3Day3Date;
    }

    public void setWk3Day3Date(String wk3Day3Date) {
        this.wk3Day3Date = wk3Day3Date;
    }

    public String getWk3Day4Date() {
        return wk3Day4Date;
    }

    public void setWk3Day4Date(String wk3Day4Date) {
        this.wk3Day4Date = wk3Day4Date;
    }

    public String getWk3Day5Date() {
        return wk3Day5Date;
    }

    public void setWk3Day5Date(String wk3Day5Date) {
        this.wk3Day5Date = wk3Day5Date;
    }

    public String getWk3Day6Date() {
        return wk3Day6Date;
    }

    public void setWk3Day6Date(String wk3Day6Date) {
        this.wk3Day6Date = wk3Day6Date;
    }

    public String getWk3Day7Date() {
        return wk3Day7Date;
    }

    public void setWk3Day7Date(String wk3Day7Date) {
        this.wk3Day7Date = wk3Day7Date;
    }

    public String getWk4Day1Date() {
        return wk4Day1Date;
    }

    public void setWk4Day1Date(String wk4Day1Date) {
        this.wk4Day1Date = wk4Day1Date;
    }

    public String getWk4Day2Date() {
        return wk4Day2Date;
    }

    public void setWk4Day2Date(String wk4Day2Date) {
        this.wk4Day2Date = wk4Day2Date;
    }

    public String getWk4Day3Date() {
        return wk4Day3Date;
    }

    public void setWk4Day3Date(String wk4Day3Date) {
        this.wk4Day3Date = wk4Day3Date;
    }

    public String getWk4Day4Date() {
        return wk4Day4Date;
    }

    public void setWk4Day4Date(String wk4Day4Date) {
        this.wk4Day4Date = wk4Day4Date;
    }

    public String getWk4Day5Date() {
        return wk4Day5Date;
    }

    public void setWk4Day5Date(String wk4Day5Date) {
        this.wk4Day5Date = wk4Day5Date;
    }

    public String getWk4Day6Date() {
        return wk4Day6Date;
    }

    public void setWk4Day6Date(String wk4Day6Date) {
        this.wk4Day6Date = wk4Day6Date;
    }

    public String getWk4Day7Date() {
        return wk4Day7Date;
    }

    public void setWk4Day7Date(String wk4Day7Date) {
        this.wk4Day7Date = wk4Day7Date;
    }

    public String getWk5Day1Date() {
        return wk5Day1Date;
    }

    public void setWk5Day1Date(String wk5Day1Date) {
        this.wk5Day1Date = wk5Day1Date;
    }

    public String getWk5Day2Date() {
        return wk5Day2Date;
    }

    public void setWk5Day2Date(String wk5Day2Date) {
        this.wk5Day2Date = wk5Day2Date;
    }

    public String getWk5Day3Date() {
        return wk5Day3Date;
    }

    public void setWk5Day3Date(String wk5Day3Date) {
        this.wk5Day3Date = wk5Day3Date;
    }

    public String getWk5Day4Date() {
        return wk5Day4Date;
    }

    public void setWk5Day4Date(String wk5Day4Date) {
        this.wk5Day4Date = wk5Day4Date;
    }

    public String getWk5Day5Date() {
        return wk5Day5Date;
    }

    public void setWk5Day5Date(String wk5Day5Date) {
        this.wk5Day5Date = wk5Day5Date;
    }

    public String getWk5Day6Date() {
        return wk5Day6Date;
    }

    public void setWk5Day6Date(String wk5Day6Date) {
        this.wk5Day6Date = wk5Day6Date;
    }

    public String getWk5Day7Date() {
        return wk5Day7Date;
    }

    public void setWk5Day7Date(String wk5Day7Date) {
        this.wk5Day7Date = wk5Day7Date;
    }

    public String getWk6Day1Date() {
        return wk6Day1Date;
    }

    public void setWk6Day1Date(String wk6Day1Date) {
        this.wk6Day1Date = wk6Day1Date;
    }

    public String getWk6Day2Date() {
        return wk6Day2Date;
    }

    public void setWk6Day2Date(String wk6Day2Date) {
        this.wk6Day2Date = wk6Day2Date;
    }

    public String getWk6Day3Date() {
        return wk6Day3Date;
    }

    public void setWk6Day3Date(String wk6Day3Date) {
        this.wk6Day3Date = wk6Day3Date;
    }

    public String getWk6Day4Date() {
        return wk6Day4Date;
    }

    public void setWk6Day4Date(String wk6Day4Date) {
        this.wk6Day4Date = wk6Day4Date;
    }

    public String getWk6Day5Date() {
        return wk6Day5Date;
    }

    public void setWk6Day5Date(String wk6Day5Date) {
        this.wk6Day5Date = wk6Day5Date;
    }

    public String getWk6Day6Date() {
        return wk6Day6Date;
    }

    public void setWk6Day6Date(String wk6Day6Date) {
        this.wk6Day6Date = wk6Day6Date;
    }

    public String getWk6Day7Date() {
        return wk6Day7Date;
    }

    public void setWk6Day7Date(String wk6Day7Date) {
        this.wk6Day7Date = wk6Day7Date;
    }

    public String getWk7Day1Date() {
        return wk7Day1Date;
    }

    public void setWk7Day1Date(String wk7Day1Date) {
        this.wk7Day1Date = wk7Day1Date;
    }

    public String getWk7Day2Date() {
        return wk7Day2Date;
    }

    public void setWk7Day2Date(String wk7Day2Date) {
        this.wk7Day2Date = wk7Day2Date;
    }

    public String getWk7Day3Date() {
        return wk7Day3Date;
    }

    public void setWk7Day3Date(String wk7Day3Date) {
        this.wk7Day3Date = wk7Day3Date;
    }

    public String getWk7Day4Date() {
        return wk7Day4Date;
    }

    public void setWk7Day4Date(String wk7Day4Date) {
        this.wk7Day4Date = wk7Day4Date;
    }

    public String getWk7Day5Date() {
        return wk7Day5Date;
    }

    public void setWk7Day5Date(String wk7Day5Date) {
        this.wk7Day5Date = wk7Day5Date;
    }

    public String getWk7Day6Date() {
        return wk7Day6Date;
    }

    public void setWk7Day6Date(String wk7Day6Date) {
        this.wk7Day6Date = wk7Day6Date;
    }

    public String getWk7Day7Date() {
        return wk7Day7Date;
    }

    public void setWk7Day7Date(String wk7Day7Date) {
        this.wk7Day7Date = wk7Day7Date;
    }

    public String getWk8Day1Date() {
        return wk8Day1Date;
    }

    public void setWk8Day1Date(String wk8Day1Date) {
        this.wk8Day1Date = wk8Day1Date;
    }

    public String getWk8Day2Date() {
        return wk8Day2Date;
    }

    public void setWk8Day2Date(String wk8Day2Date) {
        this.wk8Day2Date = wk8Day2Date;
    }

    public String getWk8Day3Date() {
        return wk8Day3Date;
    }

    public void setWk8Day3Date(String wk8Day3Date) {
        this.wk8Day3Date = wk8Day3Date;
    }

    public String getWk8Day4Date() {
        return wk8Day4Date;
    }

    public void setWk8Day4Date(String wk8Day4Date) {
        this.wk8Day4Date = wk8Day4Date;
    }

    public String getWk8Day5Date() {
        return wk8Day5Date;
    }

    public void setWk8Day5Date(String wk8Day5Date) {
        this.wk8Day5Date = wk8Day5Date;
    }

    public String getWk8Day6Date() {
        return wk8Day6Date;
    }

    public void setWk8Day6Date(String wk8Day6Date) {
        this.wk8Day6Date = wk8Day6Date;
    }

    public String getWk8Day7Date() {
        return wk8Day7Date;
    }

    public void setWk8Day7Date(String wk8Day7Date) {
        this.wk8Day7Date = wk8Day7Date;
    }

    public String getWk9Day1Date() {
        return wk9Day1Date;
    }

    public void setWk9Day1Date(String wk9Day1Date) {
        this.wk9Day1Date = wk9Day1Date;
    }

    public String getWk9Day2Date() {
        return wk9Day2Date;
    }

    public void setWk9Day2Date(String wk9Day2Date) {
        this.wk9Day2Date = wk9Day2Date;
    }

    public String getWk9Day3Date() {
        return wk9Day3Date;
    }

    public void setWk9Day3Date(String wk9Day3Date) {
        this.wk9Day3Date = wk9Day3Date;
    }

    public String getWk9Day4Date() {
        return wk9Day4Date;
    }

    public void setWk9Day4Date(String wk9Day4Date) {
        this.wk9Day4Date = wk9Day4Date;
    }

    public String getWk9Day5Date() {
        return wk9Day5Date;
    }

    public void setWk9Day5Date(String wk9Day5Date) {
        this.wk9Day5Date = wk9Day5Date;
    }

    public String getWk9Day6Date() {
        return wk9Day6Date;
    }

    public void setWk9Day6Date(String wk9Day6Date) {
        this.wk9Day6Date = wk9Day6Date;
    }

    public String getWk9Day7Date() {
        return wk9Day7Date;
    }

    public void setWk9Day7Date(String wk9Day7Date) {
        this.wk9Day7Date = wk9Day7Date;
    }

    public String getWk10Day1Date() {
        return wk10Day1Date;
    }

    public void setWk10Day1Date(String wk10Day1Date) {
        this.wk10Day1Date = wk10Day1Date;
    }

    public String getWk10Day2Date() {
        return wk10Day2Date;
    }

    public void setWk10Day2Date(String wk10Day2Date) {
        this.wk10Day2Date = wk10Day2Date;
    }

    public String getWk10Day3Date() {
        return wk10Day3Date;
    }

    public void setWk10Day3Date(String wk10Day3Date) {
        this.wk10Day3Date = wk10Day3Date;
    }

    public String getWk10Day4Date() {
        return wk10Day4Date;
    }

    public void setWk10Day4Date(String wk10Day4Date) {
        this.wk10Day4Date = wk10Day4Date;
    }

    public String getWk10Day5Date() {
        return wk10Day5Date;
    }

    public void setWk10Day5Date(String wk10Day5Date) {
        this.wk10Day5Date = wk10Day5Date;
    }

    public String getWk10Day6Date() {
        return wk10Day6Date;
    }

    public void setWk10Day6Date(String wk10Day6Date) {
        this.wk10Day6Date = wk10Day6Date;
    }

    public String getWk10Day7Date() {
        return wk10Day7Date;
    }

    public void setWk10Day7Date(String wk10Day7Date) {
        this.wk10Day7Date = wk10Day7Date;
    }

    public String getWk11Day1Date() {
        return wk11Day1Date;
    }

    public void setWk11Day1Date(String wk11Day1Date) {
        this.wk11Day1Date = wk11Day1Date;
    }

    public String getWk11Day2Date() {
        return wk11Day2Date;
    }

    public void setWk11Day2Date(String wk11Day2Date) {
        this.wk11Day2Date = wk11Day2Date;
    }

    public String getWk11Day3Date() {
        return wk11Day3Date;
    }

    public void setWk11Day3Date(String wk11Day3Date) {
        this.wk11Day3Date = wk11Day3Date;
    }

    public String getWk11Day4Date() {
        return wk11Day4Date;
    }

    public void setWk11Day4Date(String wk11Day4Date) {
        this.wk11Day4Date = wk11Day4Date;
    }

    public String getWk11Day5Date() {
        return wk11Day5Date;
    }

    public void setWk11Day5Date(String wk11Day5Date) {
        this.wk11Day5Date = wk11Day5Date;
    }

    public String getWk11Day6Date() {
        return wk11Day6Date;
    }

    public void setWk11Day6Date(String wk11Day6Date) {
        this.wk11Day6Date = wk11Day6Date;
    }

    public String getWk11Day7Date() {
        return wk11Day7Date;
    }

    public void setWk11Day7Date(String wk11Day7Date) {
        this.wk11Day7Date = wk11Day7Date;
    }

    public String getWk12Day1Date() {
        return wk12Day1Date;
    }

    public void setWk12Day1Date(String wk12Day1Date) {
        this.wk12Day1Date = wk12Day1Date;
    }

    public String getWk12Day2Date() {
        return wk12Day2Date;
    }

    public void setWk12Day2Date(String wk12Day2Date) {
        this.wk12Day2Date = wk12Day2Date;
    }

    public String getWk12Day3Date() {
        return wk12Day3Date;
    }

    public void setWk12Day3Date(String wk12Day3Date) {
        this.wk12Day3Date = wk12Day3Date;
    }

    public String getWk12Day4Date() {
        return wk12Day4Date;
    }

    public void setWk12Day4Date(String wk12Day4Date) {
        this.wk12Day4Date = wk12Day4Date;
    }

    public String getWk12Day5Date() {
        return wk12Day5Date;
    }

    public void setWk12Day5Date(String wk12Day5Date) {
        this.wk12Day5Date = wk12Day5Date;
    }

    public String getWk12Day6Date() {
        return wk12Day6Date;
    }

    public void setWk12Day6Date(String wk12Day6Date) {
        this.wk12Day6Date = wk12Day6Date;
    }

    public String getWk12Day7Date() {
        return wk12Day7Date;
    }

    public void setWk12Day7Date(String wk12Day7Date) {
        this.wk12Day7Date = wk12Day7Date;
    }

    public String getWk13Day1Date() {
        return wk13Day1Date;
    }

    public void setWk13Day1Date(String wk13Day1Date) {
        this.wk13Day1Date = wk13Day1Date;
    }

    public String getWk13Day2Date() {
        return wk13Day2Date;
    }

    public void setWk13Day2Date(String wk13Day2Date) {
        this.wk13Day2Date = wk13Day2Date;
    }

    public String getWk13Day3Date() {
        return wk13Day3Date;
    }

    public void setWk13Day3Date(String wk13Day3Date) {
        this.wk13Day3Date = wk13Day3Date;
    }

    public String getWk13Day4Date() {
        return wk13Day4Date;
    }

    public void setWk13Day4Date(String wk13Day4Date) {
        this.wk13Day4Date = wk13Day4Date;
    }

    public String getWk13Day5Date() {
        return wk13Day5Date;
    }

    public void setWk13Day5Date(String wk13Day5Date) {
        this.wk13Day5Date = wk13Day5Date;
    }

    public String getWk13Day6Date() {
        return wk13Day6Date;
    }

    public void setWk13Day6Date(String wk13Day6Date) {
        this.wk13Day6Date = wk13Day6Date;
    }

    public String getWk13Day7Date() {
        return wk13Day7Date;
    }

    public void setWk13Day7Date(String wk13Day7Date) {
        this.wk13Day7Date = wk13Day7Date;
    }

    public String getWk14Day1Date() {
        return wk14Day1Date;
    }

    public void setWk14Day1Date(String wk14Day1Date) {
        this.wk14Day1Date = wk14Day1Date;
    }

    public String getWk14Day2Date() {
        return wk14Day2Date;
    }

    public void setWk14Day2Date(String wk14Day2Date) {
        this.wk14Day2Date = wk14Day2Date;
    }

    public String getWk14Day3Date() {
        return wk14Day3Date;
    }

    public void setWk14Day3Date(String wk14Day3Date) {
        this.wk14Day3Date = wk14Day3Date;
    }

    public String getWk14Day4Date() {
        return wk14Day4Date;
    }

    public void setWk14Day4Date(String wk14Day4Date) {
        this.wk14Day4Date = wk14Day4Date;
    }

    public String getWk14Day5Date() {
        return wk14Day5Date;
    }

    public void setWk14Day5Date(String wk14Day5Date) {
        this.wk14Day5Date = wk14Day5Date;
    }

    public String getWk14Day6Date() {
        return wk14Day6Date;
    }

    public void setWk14Day6Date(String wk14Day6Date) {
        this.wk14Day6Date = wk14Day6Date;
    }

    public String getWk14Day7Date() {
        return wk14Day7Date;
    }

    public void setWk14Day7Date(String wk14Day7Date) {
        this.wk14Day7Date = wk14Day7Date;
    }

    public String getWk15Day1Date() {
        return wk15Day1Date;
    }

    public void setWk15Day1Date(String wk15Day1Date) {
        this.wk15Day1Date = wk15Day1Date;
    }

    public String getWk15Day2Date() {
        return wk15Day2Date;
    }

    public void setWk15Day2Date(String wk15Day2Date) {
        this.wk15Day2Date = wk15Day2Date;
    }

    public String getWk15Day3Date() {
        return wk15Day3Date;
    }

    public void setWk15Day3Date(String wk15Day3Date) {
        this.wk15Day3Date = wk15Day3Date;
    }

    public String getWk15Day4Date() {
        return wk15Day4Date;
    }

    public void setWk15Day4Date(String wk15Day4Date) {
        this.wk15Day4Date = wk15Day4Date;
    }

    public String getWk15Day5Date() {
        return wk15Day5Date;
    }

    public void setWk15Day5Date(String wk15Day5Date) {
        this.wk15Day5Date = wk15Day5Date;
    }

    public String getWk15Day6Date() {
        return wk15Day6Date;
    }

    public void setWk15Day6Date(String wk15Day6Date) {
        this.wk15Day6Date = wk15Day6Date;
    }

    public String getWk15Day7Date() {
        return wk15Day7Date;
    }

    public void setWk15Day7Date(String wk15Day7Date) {
        this.wk15Day7Date = wk15Day7Date;
    }

    public String getWk16Day1Date() {
        return wk16Day1Date;
    }

    public void setWk16Day1Date(String wk16Day1Date) {
        this.wk16Day1Date = wk16Day1Date;
    }

    public String getWk16Day2Date() {
        return wk16Day2Date;
    }

    public void setWk16Day2Date(String wk16Day2Date) {
        this.wk16Day2Date = wk16Day2Date;
    }

    public String getWk16Day3Date() {
        return wk16Day3Date;
    }

    public void setWk16Day3Date(String wk16Day3Date) {
        this.wk16Day3Date = wk16Day3Date;
    }

    public String getWk16Day4Date() {
        return wk16Day4Date;
    }

    public void setWk16Day4Date(String wk16Day4Date) {
        this.wk16Day4Date = wk16Day4Date;
    }

    public String getWk16Day5Date() {
        return wk16Day5Date;
    }

    public void setWk16Day5Date(String wk16Day5Date) {
        this.wk16Day5Date = wk16Day5Date;
    }

    public String getWk16Day6Date() {
        return wk16Day6Date;
    }

    public void setWk16Day6Date(String wk16Day6Date) {
        this.wk16Day6Date = wk16Day6Date;
    }

    public String getWk16Day7Date() {
        return wk16Day7Date;
    }

    public void setWk16Day7Date(String wk16Day7Date) {
        this.wk16Day7Date = wk16Day7Date;
    }

    public String getWk17Day1Date() {
        return wk17Day1Date;
    }

    public void setWk17Day1Date(String wk17Day1Date) {
        this.wk17Day1Date = wk17Day1Date;
    }

    public String getWk17Day2Date() {
        return wk17Day2Date;
    }

    public void setWk17Day2Date(String wk17Day2Date) {
        this.wk17Day2Date = wk17Day2Date;
    }

    public String getWk17Day3Date() {
        return wk17Day3Date;
    }

    public void setWk17Day3Date(String wk17Day3Date) {
        this.wk17Day3Date = wk17Day3Date;
    }

    public String getWk17Day4Date() {
        return wk17Day4Date;
    }

    public void setWk17Day4Date(String wk17Day4Date) {
        this.wk17Day4Date = wk17Day4Date;
    }

    public String getWk17Day5Date() {
        return wk17Day5Date;
    }

    public void setWk17Day5Date(String wk17Day5Date) {
        this.wk17Day5Date = wk17Day5Date;
    }

    public String getWk17Day6Date() {
        return wk17Day6Date;
    }

    public void setWk17Day6Date(String wk17Day6Date) {
        this.wk17Day6Date = wk17Day6Date;
    }

    public String getWk17Day7Date() {
        return wk17Day7Date;
    }

    public void setWk17Day7Date(String wk17Day7Date) {
        this.wk17Day7Date = wk17Day7Date;
    }

    public String getWk18Day1Date() {
        return wk18Day1Date;
    }

    public void setWk18Day1Date(String wk18Day1Date) {
        this.wk18Day1Date = wk18Day1Date;
    }

    public String getWk18Day2Date() {
        return wk18Day2Date;
    }

    public void setWk18Day2Date(String wk18Day2Date) {
        this.wk18Day2Date = wk18Day2Date;
    }

    public String getWk18Day3Date() {
        return wk18Day3Date;
    }

    public void setWk18Day3Date(String wk18Day3Date) {
        this.wk18Day3Date = wk18Day3Date;
    }

    public String getWk18Day4Date() {
        return wk18Day4Date;
    }

    public void setWk18Day4Date(String wk18Day4Date) {
        this.wk18Day4Date = wk18Day4Date;
    }

    public String getWk18Day5Date() {
        return wk18Day5Date;
    }

    public void setWk18Day5Date(String wk18Day5Date) {
        this.wk18Day5Date = wk18Day5Date;
    }

    public String getWk18Day6Date() {
        return wk18Day6Date;
    }

    public void setWk18Day6Date(String wk18Day6Date) {
        this.wk18Day6Date = wk18Day6Date;
    }

    public String getWk18Day7Date() {
        return wk18Day7Date;
    }

    public void setWk18Day7Date(String wk18Day7Date) {
        this.wk18Day7Date = wk18Day7Date;
    }

    public String getWk19Day1Date() {
        return wk19Day1Date;
    }

    public void setWk19Day1Date(String wk19Day1Date) {
        this.wk19Day1Date = wk19Day1Date;
    }

    public String getWk19Day2Date() {
        return wk19Day2Date;
    }

    public void setWk19Day2Date(String wk19Day2Date) {
        this.wk19Day2Date = wk19Day2Date;
    }

    public String getWk19Day3Date() {
        return wk19Day3Date;
    }

    public void setWk19Day3Date(String wk19Day3Date) {
        this.wk19Day3Date = wk19Day3Date;
    }

    public String getWk19Day4Date() {
        return wk19Day4Date;
    }

    public void setWk19Day4Date(String wk19Day4Date) {
        this.wk19Day4Date = wk19Day4Date;
    }

    public String getWk19Day5Date() {
        return wk19Day5Date;
    }

    public void setWk19Day5Date(String wk19Day5Date) {
        this.wk19Day5Date = wk19Day5Date;
    }

    public String getWk19Day6Date() {
        return wk19Day6Date;
    }

    public void setWk19Day6Date(String wk19Day6Date) {
        this.wk19Day6Date = wk19Day6Date;
    }

    public String getWk19Day7Date() {
        return wk19Day7Date;
    }

    public void setWk19Day7Date(String wk19Day7Date) {
        this.wk19Day7Date = wk19Day7Date;
    }

    public String getWk20Day1Date() {
        return wk20Day1Date;
    }

    public void setWk20Day1Date(String wk20Day1Date) {
        this.wk20Day1Date = wk20Day1Date;
    }

    public String getWk20Day2Date() {
        return wk20Day2Date;
    }

    public void setWk20Day2Date(String wk20Day2Date) {
        this.wk20Day2Date = wk20Day2Date;
    }

    public String getWk20Day3Date() {
        return wk20Day3Date;
    }

    public void setWk20Day3Date(String wk20Day3Date) {
        this.wk20Day3Date = wk20Day3Date;
    }

    public String getWk20Day4Date() {
        return wk20Day4Date;
    }

    public void setWk20Day4Date(String wk20Day4Date) {
        this.wk20Day4Date = wk20Day4Date;
    }

    public String getWk20Day5Date() {
        return wk20Day5Date;
    }

    public void setWk20Day5Date(String wk20Day5Date) {
        this.wk20Day5Date = wk20Day5Date;
    }

    public String getWk20Day6Date() {
        return wk20Day6Date;
    }

    public void setWk20Day6Date(String wk20Day6Date) {
        this.wk20Day6Date = wk20Day6Date;
    }

    public String getWk20Day7Date() {
        return wk20Day7Date;
    }

    public void setWk20Day7Date(String wk20Day7Date) {
        this.wk20Day7Date = wk20Day7Date;
    }

    public String getWk21Day1Date() {
        return wk21Day1Date;
    }

    public void setWk21Day1Date(String wk21Day1Date) {
        this.wk21Day1Date = wk21Day1Date;
    }

    public String getWk21Day2Date() {
        return wk21Day2Date;
    }

    public void setWk21Day2Date(String wk21Day2Date) {
        this.wk21Day2Date = wk21Day2Date;
    }

    public String getWk21Day3Date() {
        return wk21Day3Date;
    }

    public void setWk21Day3Date(String wk21Day3Date) {
        this.wk21Day3Date = wk21Day3Date;
    }

    public String getWk21Day4Date() {
        return wk21Day4Date;
    }

    public void setWk21Day4Date(String wk21Day4Date) {
        this.wk21Day4Date = wk21Day4Date;
    }

    public String getWk21Day5Date() {
        return wk21Day5Date;
    }

    public void setWk21Day5Date(String wk21Day5Date) {
        this.wk21Day5Date = wk21Day5Date;
    }

    public String getWk21Day6Date() {
        return wk21Day6Date;
    }

    public void setWk21Day6Date(String wk21Day6Date) {
        this.wk21Day6Date = wk21Day6Date;
    }

    public String getWk21Day7Date() {
        return wk21Day7Date;
    }

    public void setWk21Day7Date(String wk21Day7Date) {
        this.wk21Day7Date = wk21Day7Date;
    }

    public String getWk22Day1Date() {
        return wk22Day1Date;
    }

    public void setWk22Day1Date(String wk22Day1Date) {
        this.wk22Day1Date = wk22Day1Date;
    }

    public String getWk22Day2Date() {
        return wk22Day2Date;
    }

    public void setWk22Day2Date(String wk22Day2Date) {
        this.wk22Day2Date = wk22Day2Date;
    }

    public String getWk22Day3Date() {
        return wk22Day3Date;
    }

    public void setWk22Day3Date(String wk22Day3Date) {
        this.wk22Day3Date = wk22Day3Date;
    }

    public String getWk22Day4Date() {
        return wk22Day4Date;
    }

    public void setWk22Day4Date(String wk22Day4Date) {
        this.wk22Day4Date = wk22Day4Date;
    }

    public String getWk22Day5Date() {
        return wk22Day5Date;
    }

    public void setWk22Day5Date(String wk22Day5Date) {
        this.wk22Day5Date = wk22Day5Date;
    }

    public String getWk22Day6Date() {
        return wk22Day6Date;
    }

    public void setWk22Day6Date(String wk22Day6Date) {
        this.wk22Day6Date = wk22Day6Date;
    }

    public String getWk22Day7Date() {
        return wk22Day7Date;
    }

    public void setWk22Day7Date(String wk22Day7Date) {
        this.wk22Day7Date = wk22Day7Date;
    }

    public String getWk23Day1Date() {
        return wk23Day1Date;
    }

    public void setWk23Day1Date(String wk23Day1Date) {
        this.wk23Day1Date = wk23Day1Date;
    }

    public String getWk23Day2Date() {
        return wk23Day2Date;
    }

    public void setWk23Day2Date(String wk23Day2Date) {
        this.wk23Day2Date = wk23Day2Date;
    }

    public String getWk23Day3Date() {
        return wk23Day3Date;
    }

    public void setWk23Day3Date(String wk23Day3Date) {
        this.wk23Day3Date = wk23Day3Date;
    }

    public String getWk23Day4Date() {
        return wk23Day4Date;
    }

    public void setWk23Day4Date(String wk23Day4Date) {
        this.wk23Day4Date = wk23Day4Date;
    }

    public String getWk23Day5Date() {
        return wk23Day5Date;
    }

    public void setWk23Day5Date(String wk23Day5Date) {
        this.wk23Day5Date = wk23Day5Date;
    }

    public String getWk23Day6Date() {
        return wk23Day6Date;
    }

    public void setWk23Day6Date(String wk23Day6Date) {
        this.wk23Day6Date = wk23Day6Date;
    }

    public String getWk23Day7Date() {
        return wk23Day7Date;
    }

    public void setWk23Day7Date(String wk23Day7Date) {
        this.wk23Day7Date = wk23Day7Date;
    }

    public String getWk24Day1Date() {
        return wk24Day1Date;
    }

    public void setWk24Day1Date(String wk24Day1Date) {
        this.wk24Day1Date = wk24Day1Date;
    }

    public String getWk24Day2Date() {
        return wk24Day2Date;
    }

    public void setWk24Day2Date(String wk24Day2Date) {
        this.wk24Day2Date = wk24Day2Date;
    }

    public String getWk24Day3Date() {
        return wk24Day3Date;
    }

    public void setWk24Day3Date(String wk24Day3Date) {
        this.wk24Day3Date = wk24Day3Date;
    }

    public String getWk24Day4Date() {
        return wk24Day4Date;
    }

    public void setWk24Day4Date(String wk24Day4Date) {
        this.wk24Day4Date = wk24Day4Date;
    }

    public String getWk24Day5Date() {
        return wk24Day5Date;
    }

    public void setWk24Day5Date(String wk24Day5Date) {
        this.wk24Day5Date = wk24Day5Date;
    }

    public String getWk24Day6Date() {
        return wk24Day6Date;
    }

    public void setWk24Day6Date(String wk24Day6Date) {
        this.wk24Day6Date = wk24Day6Date;
    }

    public String getWk24Day7Date() {
        return wk24Day7Date;
    }

    public void setWk24Day7Date(String wk24Day7Date) {
        this.wk24Day7Date = wk24Day7Date;
    }

    public String getWk25Day1Date() {
        return wk25Day1Date;
    }

    public void setWk25Day1Date(String wk25Day1Date) {
        this.wk25Day1Date = wk25Day1Date;
    }

    public String getWk25Day2Date() {
        return wk25Day2Date;
    }

    public void setWk25Day2Date(String wk25Day2Date) {
        this.wk25Day2Date = wk25Day2Date;
    }

    public String getWk25Day3Date() {
        return wk25Day3Date;
    }

    public void setWk25Day3Date(String wk25Day3Date) {
        this.wk25Day3Date = wk25Day3Date;
    }

    public String getWk25Day4Date() {
        return wk25Day4Date;
    }

    public void setWk25Day4Date(String wk25Day4Date) {
        this.wk25Day4Date = wk25Day4Date;
    }

    public String getWk25Day5Date() {
        return wk25Day5Date;
    }

    public void setWk25Day5Date(String wk25Day5Date) {
        this.wk25Day5Date = wk25Day5Date;
    }

    public String getWk25Day6Date() {
        return wk25Day6Date;
    }

    public void setWk25Day6Date(String wk25Day6Date) {
        this.wk25Day6Date = wk25Day6Date;
    }

    public String getWk25Day7Date() {
        return wk25Day7Date;
    }

    public void setWk25Day7Date(String wk25Day7Date) {
        this.wk25Day7Date = wk25Day7Date;
    }

    public String getWk26Day1Date() {
        return wk26Day1Date;
    }

    public void setWk26Day1Date(String wk26Day1Date) {
        this.wk26Day1Date = wk26Day1Date;
    }

    public String getWk26Day2Date() {
        return wk26Day2Date;
    }

    public void setWk26Day2Date(String wk26Day2Date) {
        this.wk26Day2Date = wk26Day2Date;
    }

    public String getWk26Day3Date() {
        return wk26Day3Date;
    }

    public void setWk26Day3Date(String wk26Day3Date) {
        this.wk26Day3Date = wk26Day3Date;
    }

    public String getWk26Day4Date() {
        return wk26Day4Date;
    }

    public void setWk26Day4Date(String wk26Day4Date) {
        this.wk26Day4Date = wk26Day4Date;
    }

    public String getWk26Day5Date() {
        return wk26Day5Date;
    }

    public void setWk26Day5Date(String wk26Day5Date) {
        this.wk26Day5Date = wk26Day5Date;
    }

    public String getWk26Day6Date() {
        return wk26Day6Date;
    }

    public void setWk26Day6Date(String wk26Day6Date) {
        this.wk26Day6Date = wk26Day6Date;
    }

    public String getWk26Day7Date() {
        return wk26Day7Date;
    }

    public void setWk26Day7Date(String wk26Day7Date) {
        this.wk26Day7Date = wk26Day7Date;
    }

    public String getWk27Day1Date() {
        return wk27Day1Date;
    }

    public void setWk27Day1Date(String wk27Day1Date) {
        this.wk27Day1Date = wk27Day1Date;
    }

    public String getWk27Day2Date() {
        return wk27Day2Date;
    }

    public void setWk27Day2Date(String wk27Day2Date) {
        this.wk27Day2Date = wk27Day2Date;
    }

    public String getWk27Day3Date() {
        return wk27Day3Date;
    }

    public void setWk27Day3Date(String wk27Day3Date) {
        this.wk27Day3Date = wk27Day3Date;
    }

    public String getWk27Day4Date() {
        return wk27Day4Date;
    }

    public void setWk27Day4Date(String wk27Day4Date) {
        this.wk27Day4Date = wk27Day4Date;
    }

    public String getWk27Day5Date() {
        return wk27Day5Date;
    }

    public void setWk27Day5Date(String wk27Day5Date) {
        this.wk27Day5Date = wk27Day5Date;
    }

    public String getWk27Day6Date() {
        return wk27Day6Date;
    }

    public void setWk27Day6Date(String wk27Day6Date) {
        this.wk27Day6Date = wk27Day6Date;
    }

    public String getWk27Day7Date() {
        return wk27Day7Date;
    }

    public void setWk27Day7Date(String wk27Day7Date) {
        this.wk27Day7Date = wk27Day7Date;
    }

    public String getWk32Day1Date() {
        return wk32Day1Date;
    }

    public void setWk32Day1Date(String wk32Day1Date) {
        this.wk32Day1Date = wk32Day1Date;
    }

    public String getWk32Day2Date() {
        return wk32Day2Date;
    }

    public void setWk32Day2Date(String wk32Day2Date) {
        this.wk32Day2Date = wk32Day2Date;
    }

    public String getWk32Day3Date() {
        return wk32Day3Date;
    }

    public void setWk32Day3Date(String wk32Day3Date) {
        this.wk32Day3Date = wk32Day3Date;
    }

    public String getWk32Day4Date() {
        return wk32Day4Date;
    }

    public void setWk32Day4Date(String wk32Day4Date) {
        this.wk32Day4Date = wk32Day4Date;
    }

    public String getWk32Day5Date() {
        return wk32Day5Date;
    }

    public void setWk32Day5Date(String wk32Day5Date) {
        this.wk32Day5Date = wk32Day5Date;
    }

    public String getWk32Day6Date() {
        return wk32Day6Date;
    }

    public void setWk32Day6Date(String wk32Day6Date) {
        this.wk32Day6Date = wk32Day6Date;
    }

    public String getWk32Day7Date() {
        return wk32Day7Date;
    }

    public void setWk32Day7Date(String wk32Day7Date) {
        this.wk32Day7Date = wk32Day7Date;
    }

    public String getWk33Day1Date() {
        return wk33Day1Date;
    }

    public void setWk33Day1Date(String wk33Day1Date) {
        this.wk33Day1Date = wk33Day1Date;
    }

    public String getWk33Day2Date() {
        return wk33Day2Date;
    }

    public void setWk33Day2Date(String wk33Day2Date) {
        this.wk33Day2Date = wk33Day2Date;
    }

    public String getWk33Day3Date() {
        return wk33Day3Date;
    }

    public void setWk33Day3Date(String wk33Day3Date) {
        this.wk33Day3Date = wk33Day3Date;
    }

    public String getWk33Day4Date() {
        return wk33Day4Date;
    }

    public void setWk33Day4Date(String wk33Day4Date) {
        this.wk33Day4Date = wk33Day4Date;
    }

    public String getWk33Day5Date() {
        return wk33Day5Date;
    }

    public void setWk33Day5Date(String wk33Day5Date) {
        this.wk33Day5Date = wk33Day5Date;
    }

    public String getWk33Day6Date() {
        return wk33Day6Date;
    }

    public void setWk33Day6Date(String wk33Day6Date) {
        this.wk33Day6Date = wk33Day6Date;
    }

    public String getWk33Day7Date() {
        return wk33Day7Date;
    }

    public void setWk33Day7Date(String wk33Day7Date) {
        this.wk33Day7Date = wk33Day7Date;
    }

    public String getWk34Day1Date() {
        return wk34Day1Date;
    }

    public void setWk34Day1Date(String wk34Day1Date) {
        this.wk34Day1Date = wk34Day1Date;
    }

    public String getWk34Day2Date() {
        return wk34Day2Date;
    }

    public void setWk34Day2Date(String wk34Day2Date) {
        this.wk34Day2Date = wk34Day2Date;
    }

    public String getWk34Day3Date() {
        return wk34Day3Date;
    }

    public void setWk34Day3Date(String wk34Day3Date) {
        this.wk34Day3Date = wk34Day3Date;
    }

    public String getWk34Day4Date() {
        return wk34Day4Date;
    }

    public void setWk34Day4Date(String wk34Day4Date) {
        this.wk34Day4Date = wk34Day4Date;
    }

    public String getWk34Day5Date() {
        return wk34Day5Date;
    }

    public void setWk34Day5Date(String wk34Day5Date) {
        this.wk34Day5Date = wk34Day5Date;
    }

    public String getWk34Day6Date() {
        return wk34Day6Date;
    }

    public void setWk34Day6Date(String wk34Day6Date) {
        this.wk34Day6Date = wk34Day6Date;
    }

    public String getWk34Day7Date() {
        return wk34Day7Date;
    }

    public void setWk34Day7Date(String wk34Day7Date) {
        this.wk34Day7Date = wk34Day7Date;
    }

    public String getWk35Day1Date() {
        return wk35Day1Date;
    }

    public void setWk35Day1Date(String wk35Day1Date) {
        this.wk35Day1Date = wk35Day1Date;
    }

    public String getWk35Day2Date() {
        return wk35Day2Date;
    }

    public void setWk35Day2Date(String wk35Day2Date) {
        this.wk35Day2Date = wk35Day2Date;
    }

    public String getWk35Day3Date() {
        return wk35Day3Date;
    }

    public void setWk35Day3Date(String wk35Day3Date) {
        this.wk35Day3Date = wk35Day3Date;
    }

    public String getWk35Day4Date() {
        return wk35Day4Date;
    }

    public void setWk35Day4Date(String wk35Day4Date) {
        this.wk35Day4Date = wk35Day4Date;
    }

    public String getWk35Day5Date() {
        return wk35Day5Date;
    }

    public void setWk35Day5Date(String wk35Day5Date) {
        this.wk35Day5Date = wk35Day5Date;
    }

    public String getWk35Day6Date() {
        return wk35Day6Date;
    }

    public void setWk35Day6Date(String wk35Day6Date) {
        this.wk35Day6Date = wk35Day6Date;
    }

    public String getWk35Day7Date() {
        return wk35Day7Date;
    }

    public void setWk35Day7Date(String wk35Day7Date) {
        this.wk35Day7Date = wk35Day7Date;
    }

    public String getWk36Day1Date() {
        return wk36Day1Date;
    }

    public void setWk36Day1Date(String wk36Day1Date) {
        this.wk36Day1Date = wk36Day1Date;
    }

    public String getWk36Day2Date() {
        return wk36Day2Date;
    }

    public void setWk36Day2Date(String wk36Day2Date) {
        this.wk36Day2Date = wk36Day2Date;
    }

    public String getWk36Day3Date() {
        return wk36Day3Date;
    }

    public void setWk36Day3Date(String wk36Day3Date) {
        this.wk36Day3Date = wk36Day3Date;
    }

    public String getWk36Day4Date() {
        return wk36Day4Date;
    }

    public void setWk36Day4Date(String wk36Day4Date) {
        this.wk36Day4Date = wk36Day4Date;
    }

    public String getWk36Day5Date() {
        return wk36Day5Date;
    }

    public void setWk36Day5Date(String wk36Day5Date) {
        this.wk36Day5Date = wk36Day5Date;
    }

    public String getWk36Day6Date() {
        return wk36Day6Date;
    }

    public void setWk36Day6Date(String wk36Day6Date) {
        this.wk36Day6Date = wk36Day6Date;
    }

    public String getWk36Day7Date() {
        return wk36Day7Date;
    }

    public void setWk36Day7Date(String wk36Day7Date) {
        this.wk36Day7Date = wk36Day7Date;
    }

    public String getWk37Day1Date() {
        return wk37Day1Date;
    }

    public void setWk37Day1Date(String wk37Day1Date) {
        this.wk37Day1Date = wk37Day1Date;
    }

    public String getWk37Day2Date() {
        return wk37Day2Date;
    }

    public void setWk37Day2Date(String wk37Day2Date) {
        this.wk37Day2Date = wk37Day2Date;
    }

    public String getWk37Day3Date() {
        return wk37Day3Date;
    }

    public void setWk37Day3Date(String wk37Day3Date) {
        this.wk37Day3Date = wk37Day3Date;
    }

    public String getWk37Day4Date() {
        return wk37Day4Date;
    }

    public void setWk37Day4Date(String wk37Day4Date) {
        this.wk37Day4Date = wk37Day4Date;
    }

    public String getWk37Day5Date() {
        return wk37Day5Date;
    }

    public void setWk37Day5Date(String wk37Day5Date) {
        this.wk37Day5Date = wk37Day5Date;
    }

    public String getWk37Day6Date() {
        return wk37Day6Date;
    }

    public void setWk37Day6Date(String wk37Day6Date) {
        this.wk37Day6Date = wk37Day6Date;
    }

    public String getWk37Day7Date() {
        return wk37Day7Date;
    }

    public void setWk37Day7Date(String wk37Day7Date) {
        this.wk37Day7Date = wk37Day7Date;
    }

    public String getWk38Day1Date() {
        return wk38Day1Date;
    }

    public void setWk38Day1Date(String wk38Day1Date) {
        this.wk38Day1Date = wk38Day1Date;
    }

    public String getWk38Day2Date() {
        return wk38Day2Date;
    }

    public void setWk38Day2Date(String wk38Day2Date) {
        this.wk38Day2Date = wk38Day2Date;
    }

    public String getWk38Day3Date() {
        return wk38Day3Date;
    }

    public void setWk38Day3Date(String wk38Day3Date) {
        this.wk38Day3Date = wk38Day3Date;
    }

    public String getWk38Day4Date() {
        return wk38Day4Date;
    }

    public void setWk38Day4Date(String wk38Day4Date) {
        this.wk38Day4Date = wk38Day4Date;
    }

    public String getWk38Day5Date() {
        return wk38Day5Date;
    }

    public void setWk38Day5Date(String wk38Day5Date) {
        this.wk38Day5Date = wk38Day5Date;
    }

    public String getWk38Day6Date() {
        return wk38Day6Date;
    }

    public void setWk38Day6Date(String wk38Day6Date) {
        this.wk38Day6Date = wk38Day6Date;
    }

    public String getWk38Day7Date() {
        return wk38Day7Date;
    }

    public void setWk38Day7Date(String wk38Day7Date) {
        this.wk38Day7Date = wk38Day7Date;
    }

    public String getWk39Day1Date() {
        return wk39Day1Date;
    }

    public void setWk39Day1Date(String wk39Day1Date) {
        this.wk39Day1Date = wk39Day1Date;
    }

    public String getWk39Day2Date() {
        return wk39Day2Date;
    }

    public void setWk39Day2Date(String wk39Day2Date) {
        this.wk39Day2Date = wk39Day2Date;
    }

    public String getWk39Day3Date() {
        return wk39Day3Date;
    }

    public void setWk39Day3Date(String wk39Day3Date) {
        this.wk39Day3Date = wk39Day3Date;
    }

    public String getWk39Day4Date() {
        return wk39Day4Date;
    }

    public void setWk39Day4Date(String wk39Day4Date) {
        this.wk39Day4Date = wk39Day4Date;
    }

    public String getWk39Day5Date() {
        return wk39Day5Date;
    }

    public void setWk39Day5Date(String wk39Day5Date) {
        this.wk39Day5Date = wk39Day5Date;
    }

    public String getWk39Day6Date() {
        return wk39Day6Date;
    }

    public void setWk39Day6Date(String wk39Day6Date) {
        this.wk39Day6Date = wk39Day6Date;
    }

    public String getWk39Day7Date() {
        return wk39Day7Date;
    }

    public void setWk39Day7Date(String wk39Day7Date) {
        this.wk39Day7Date = wk39Day7Date;
    }

    public String getWk40Day1Date() {
        return wk40Day1Date;
    }

    public void setWk40Day1Date(String wk40Day1Date) {
        this.wk40Day1Date = wk40Day1Date;
    }

    public String getWk40Day2Date() {
        return wk40Day2Date;
    }

    public void setWk40Day2Date(String wk40Day2Date) {
        this.wk40Day2Date = wk40Day2Date;
    }

    public String getWk40Day3Date() {
        return wk40Day3Date;
    }

    public void setWk40Day3Date(String wk40Day3Date) {
        this.wk40Day3Date = wk40Day3Date;
    }

    public String getWk40Day4Date() {
        return wk40Day4Date;
    }

    public void setWk40Day4Date(String wk40Day4Date) {
        this.wk40Day4Date = wk40Day4Date;
    }

    public String getWk40Day5Date() {
        return wk40Day5Date;
    }

    public void setWk40Day5Date(String wk40Day5Date) {
        this.wk40Day5Date = wk40Day5Date;
    }

    public String getWk40Day6Date() {
        return wk40Day6Date;
    }

    public void setWk40Day6Date(String wk40Day6Date) {
        this.wk40Day6Date = wk40Day6Date;
    }

    public String getWk40Day7Date() {
        return wk40Day7Date;
    }

    public void setWk40Day7Date(String wk40Day7Date) {
        this.wk40Day7Date = wk40Day7Date;
    }

    public String getWk41Day1Date() {
        return wk41Day1Date;
    }

    public void setWk41Day1Date(String wk41Day1Date) {
        this.wk41Day1Date = wk41Day1Date;
    }

    public String getWk41Day2Date() {
        return wk41Day2Date;
    }

    public void setWk41Day2Date(String wk41Day2Date) {
        this.wk41Day2Date = wk41Day2Date;
    }

    public String getWk41Day3Date() {
        return wk41Day3Date;
    }

    public void setWk41Day3Date(String wk41Day3Date) {
        this.wk41Day3Date = wk41Day3Date;
    }

    public String getWk41Day4Date() {
        return wk41Day4Date;
    }

    public void setWk41Day4Date(String wk41Day4Date) {
        this.wk41Day4Date = wk41Day4Date;
    }

    public String getWk41Day5Date() {
        return wk41Day5Date;
    }

    public void setWk41Day5Date(String wk41Day5Date) {
        this.wk41Day5Date = wk41Day5Date;
    }

    public String getWk41Day6Date() {
        return wk41Day6Date;
    }

    public void setWk41Day6Date(String wk41Day6Date) {
        this.wk41Day6Date = wk41Day6Date;
    }

    public String getWk41Day7Date() {
        return wk41Day7Date;
    }

    public void setWk41Day7Date(String wk41Day7Date) {
        this.wk41Day7Date = wk41Day7Date;
    }

    public String getWk42Day1Date() {
        return wk42Day1Date;
    }

    public void setWk42Day1Date(String wk42Day1Date) {
        this.wk42Day1Date = wk42Day1Date;
    }

    public String getWk42Day2Date() {
        return wk42Day2Date;
    }

    public void setWk42Day2Date(String wk42Day2Date) {
        this.wk42Day2Date = wk42Day2Date;
    }

    public String getWk42Day3Date() {
        return wk42Day3Date;
    }

    public void setWk42Day3Date(String wk42Day3Date) {
        this.wk42Day3Date = wk42Day3Date;
    }

    public String getWk42Day4Date() {
        return wk42Day4Date;
    }

    public void setWk42Day4Date(String wk42Day4Date) {
        this.wk42Day4Date = wk42Day4Date;
    }

    public String getWk42Day5Date() {
        return wk42Day5Date;
    }

    public void setWk42Day5Date(String wk42Day5Date) {
        this.wk42Day5Date = wk42Day5Date;
    }

    public String getWk42Day6Date() {
        return wk42Day6Date;
    }

    public void setWk42Day6Date(String wk42Day6Date) {
        this.wk42Day6Date = wk42Day6Date;
    }

    public String getWk42Day7Date() {
        return wk42Day7Date;
    }

    public void setWk42Day7Date(String wk42Day7Date) {
        this.wk42Day7Date = wk42Day7Date;
    }

    public String getWk43Day1Date() {
        return wk43Day1Date;
    }

    public void setWk43Day1Date(String wk43Day1Date) {
        this.wk43Day1Date = wk43Day1Date;
    }

    public String getWk43Day2Date() {
        return wk43Day2Date;
    }

    public void setWk43Day2Date(String wk43Day2Date) {
        this.wk43Day2Date = wk43Day2Date;
    }

    public String getWk43Day3Date() {
        return wk43Day3Date;
    }

    public void setWk43Day3Date(String wk43Day3Date) {
        this.wk43Day3Date = wk43Day3Date;
    }

    public String getWk43Day4Date() {
        return wk43Day4Date;
    }

    public void setWk43Day4Date(String wk43Day4Date) {
        this.wk43Day4Date = wk43Day4Date;
    }

    public String getWk43Day5Date() {
        return wk43Day5Date;
    }

    public void setWk43Day5Date(String wk43Day5Date) {
        this.wk43Day5Date = wk43Day5Date;
    }

    public String getWk43Day6Date() {
        return wk43Day6Date;
    }

    public void setWk43Day6Date(String wk43Day6Date) {
        this.wk43Day6Date = wk43Day6Date;
    }

    public String getWk43Day7Date() {
        return wk43Day7Date;
    }

    public void setWk43Day7Date(String wk43Day7Date) {
        this.wk43Day7Date = wk43Day7Date;
    }

    public String getWk44Day1Date() {
        return wk44Day1Date;
    }

    public void setWk44Day1Date(String wk44Day1Date) {
        this.wk44Day1Date = wk44Day1Date;
    }

    public String getWk44Day2Date() {
        return wk44Day2Date;
    }

    public void setWk44Day2Date(String wk44Day2Date) {
        this.wk44Day2Date = wk44Day2Date;
    }

    public String getWk44Day3Date() {
        return wk44Day3Date;
    }

    public void setWk44Day3Date(String wk44Day3Date) {
        this.wk44Day3Date = wk44Day3Date;
    }

    public String getWk44Day4Date() {
        return wk44Day4Date;
    }

    public void setWk44Day4Date(String wk44Day4Date) {
        this.wk44Day4Date = wk44Day4Date;
    }

    public String getWk44Day5Date() {
        return wk44Day5Date;
    }

    public void setWk44Day5Date(String wk44Day5Date) {
        this.wk44Day5Date = wk44Day5Date;
    }

    public String getWk44Day6Date() {
        return wk44Day6Date;
    }

    public void setWk44Day6Date(String wk44Day6Date) {
        this.wk44Day6Date = wk44Day6Date;
    }

    public String getWk44Day7Date() {
        return wk44Day7Date;
    }

    public void setWk44Day7Date(String wk44Day7Date) {
        this.wk44Day7Date = wk44Day7Date;
    }

    public String getWk45Day1Date() {
        return wk45Day1Date;
    }

    public void setWk45Day1Date(String wk45Day1Date) {
        this.wk45Day1Date = wk45Day1Date;
    }

    public String getWk45Day2Date() {
        return wk45Day2Date;
    }

    public void setWk45Day2Date(String wk45Day2Date) {
        this.wk45Day2Date = wk45Day2Date;
    }

    public String getWk45Day3Date() {
        return wk45Day3Date;
    }

    public void setWk45Day3Date(String wk45Day3Date) {
        this.wk45Day3Date = wk45Day3Date;
    }

    public String getWk45Day4Date() {
        return wk45Day4Date;
    }

    public void setWk45Day4Date(String wk45Day4Date) {
        this.wk45Day4Date = wk45Day4Date;
    }

    public String getWk45Day5Date() {
        return wk45Day5Date;
    }

    public void setWk45Day5Date(String wk45Day5Date) {
        this.wk45Day5Date = wk45Day5Date;
    }

    public String getWk45Day6Date() {
        return wk45Day6Date;
    }

    public void setWk45Day6Date(String wk45Day6Date) {
        this.wk45Day6Date = wk45Day6Date;
    }

    public String getWk45Day7Date() {
        return wk45Day7Date;
    }

    public void setWk45Day7Date(String wk45Day7Date) {
        this.wk45Day7Date = wk45Day7Date;
    }

    public String getWk46Day1Date() {
        return wk46Day1Date;
    }

    public void setWk46Day1Date(String wk46Day1Date) {
        this.wk46Day1Date = wk46Day1Date;
    }

    public String getWk46Day2Date() {
        return wk46Day2Date;
    }

    public void setWk46Day2Date(String wk46Day2Date) {
        this.wk46Day2Date = wk46Day2Date;
    }

    public String getWk46Day3Date() {
        return wk46Day3Date;
    }

    public void setWk46Day3Date(String wk46Day3Date) {
        this.wk46Day3Date = wk46Day3Date;
    }

    public String getWk46Day4Date() {
        return wk46Day4Date;
    }

    public void setWk46Day4Date(String wk46Day4Date) {
        this.wk46Day4Date = wk46Day4Date;
    }

    public String getWk46Day5Date() {
        return wk46Day5Date;
    }

    public void setWk46Day5Date(String wk46Day5Date) {
        this.wk46Day5Date = wk46Day5Date;
    }

    public String getWk46Day6Date() {
        return wk46Day6Date;
    }

    public void setWk46Day6Date(String wk46Day6Date) {
        this.wk46Day6Date = wk46Day6Date;
    }

    public String getWk46Day7Date() {
        return wk46Day7Date;
    }

    public void setWk46Day7Date(String wk46Day7Date) {
        this.wk46Day7Date = wk46Day7Date;
    }

    public String getWk47Day1Date() {
        return wk47Day1Date;
    }

    public void setWk47Day1Date(String wk47Day1Date) {
        this.wk47Day1Date = wk47Day1Date;
    }

    public String getWk47Day2Date() {
        return wk47Day2Date;
    }

    public void setWk47Day2Date(String wk47Day2Date) {
        this.wk47Day2Date = wk47Day2Date;
    }

    public String getWk47Day3Date() {
        return wk47Day3Date;
    }

    public void setWk47Day3Date(String wk47Day3Date) {
        this.wk47Day3Date = wk47Day3Date;
    }

    public String getWk47Day4Date() {
        return wk47Day4Date;
    }

    public void setWk47Day4Date(String wk47Day4Date) {
        this.wk47Day4Date = wk47Day4Date;
    }

    public String getWk47Day5Date() {
        return wk47Day5Date;
    }

    public void setWk47Day5Date(String wk47Day5Date) {
        this.wk47Day5Date = wk47Day5Date;
    }

    public String getWk47Day6Date() {
        return wk47Day6Date;
    }

    public void setWk47Day6Date(String wk47Day6Date) {
        this.wk47Day6Date = wk47Day6Date;
    }

    public String getWk47Day7Date() {
        return wk47Day7Date;
    }

    public void setWk47Day7Date(String wk47Day7Date) {
        this.wk47Day7Date = wk47Day7Date;
    }

    public String getWk48Day1Date() {
        return wk48Day1Date;
    }

    public void setWk48Day1Date(String wk48Day1Date) {
        this.wk48Day1Date = wk48Day1Date;
    }

    public String getWk48Day2Date() {
        return wk48Day2Date;
    }

    public void setWk48Day2Date(String wk48Day2Date) {
        this.wk48Day2Date = wk48Day2Date;
    }

    public String getWk48Day3Date() {
        return wk48Day3Date;
    }

    public void setWk48Day3Date(String wk48Day3Date) {
        this.wk48Day3Date = wk48Day3Date;
    }

    public String getWk48Day4Date() {
        return wk48Day4Date;
    }

    public void setWk48Day4Date(String wk48Day4Date) {
        this.wk48Day4Date = wk48Day4Date;
    }

    public String getWk48Day5Date() {
        return wk48Day5Date;
    }

    public void setWk48Day5Date(String wk48Day5Date) {
        this.wk48Day5Date = wk48Day5Date;
    }

    public String getWk48Day6Date() {
        return wk48Day6Date;
    }

    public void setWk48Day6Date(String wk48Day6Date) {
        this.wk48Day6Date = wk48Day6Date;
    }

    public String getWk48Day7Date() {
        return wk48Day7Date;
    }

    public void setWk48Day7Date(String wk48Day7Date) {
        this.wk48Day7Date = wk48Day7Date;
    }

    public String getWk49Day1Date() {
        return wk49Day1Date;
    }

    public void setWk49Day1Date(String wk49Day1Date) {
        this.wk49Day1Date = wk49Day1Date;
    }

    public String getWk49Day2Date() {
        return wk49Day2Date;
    }

    public void setWk49Day2Date(String wk49Day2Date) {
        this.wk49Day2Date = wk49Day2Date;
    }

    public String getWk49Day3Date() {
        return wk49Day3Date;
    }

    public void setWk49Day3Date(String wk49Day3Date) {
        this.wk49Day3Date = wk49Day3Date;
    }

    public String getWk49Day4Date() {
        return wk49Day4Date;
    }

    public void setWk49Day4Date(String wk49Day4Date) {
        this.wk49Day4Date = wk49Day4Date;
    }

    public String getWk49Day5Date() {
        return wk49Day5Date;
    }

    public void setWk49Day5Date(String wk49Day5Date) {
        this.wk49Day5Date = wk49Day5Date;
    }

    public String getWk49Day6Date() {
        return wk49Day6Date;
    }

    public void setWk49Day6Date(String wk49Day6Date) {
        this.wk49Day6Date = wk49Day6Date;
    }

    public String getWk49Day7Date() {
        return wk49Day7Date;
    }

    public void setWk49Day7Date(String wk49Day7Date) {
        this.wk49Day7Date = wk49Day7Date;
    }

    public String getWk50Day1Date() {
        return wk50Day1Date;
    }

    public void setWk50Day1Date(String wk50Day1Date) {
        this.wk50Day1Date = wk50Day1Date;
    }

    public String getWk50Day2Date() {
        return wk50Day2Date;
    }

    public void setWk50Day2Date(String wk50Day2Date) {
        this.wk50Day2Date = wk50Day2Date;
    }

    public String getWk50Day3Date() {
        return wk50Day3Date;
    }

    public void setWk50Day3Date(String wk50Day3Date) {
        this.wk50Day3Date = wk50Day3Date;
    }

    public String getWk50Day4Date() {
        return wk50Day4Date;
    }

    public void setWk50Day4Date(String wk50Day4Date) {
        this.wk50Day4Date = wk50Day4Date;
    }

    public String getWk50Day5Date() {
        return wk50Day5Date;
    }

    public void setWk50Day5Date(String wk50Day5Date) {
        this.wk50Day5Date = wk50Day5Date;
    }

    public String getWk50Day6Date() {
        return wk50Day6Date;
    }

    public void setWk50Day6Date(String wk50Day6Date) {
        this.wk50Day6Date = wk50Day6Date;
    }

    public String getWk50Day7Date() {
        return wk50Day7Date;
    }

    public void setWk50Day7Date(String wk50Day7Date) {
        this.wk50Day7Date = wk50Day7Date;
    }

    public String getWk51Day1Date() {
        return wk51Day1Date;
    }

    public void setWk51Day1Date(String wk51Day1Date) {
        this.wk51Day1Date = wk51Day1Date;
    }

    public String getWk51Day2Date() {
        return wk51Day2Date;
    }

    public void setWk51Day2Date(String wk51Day2Date) {
        this.wk51Day2Date = wk51Day2Date;
    }

    public String getWk51Day3Date() {
        return wk51Day3Date;
    }

    public void setWk51Day3Date(String wk51Day3Date) {
        this.wk51Day3Date = wk51Day3Date;
    }

    public String getWk51Day4Date() {
        return wk51Day4Date;
    }

    public void setWk51Day4Date(String wk51Day4Date) {
        this.wk51Day4Date = wk51Day4Date;
    }

    public String getWk51Day5Date() {
        return wk51Day5Date;
    }

    public void setWk51Day5Date(String wk51Day5Date) {
        this.wk51Day5Date = wk51Day5Date;
    }

    public String getWk51Day6Date() {
        return wk51Day6Date;
    }

    public void setWk51Day6Date(String wk51Day6Date) {
        this.wk51Day6Date = wk51Day6Date;
    }

    public String getWk51Day7Date() {
        return wk51Day7Date;
    }

    public void setWk51Day7Date(String wk51Day7Date) {
        this.wk51Day7Date = wk51Day7Date;
    }

    public String getWk52Day1Date() {
        return wk52Day1Date;
    }

    public void setWk52Day1Date(String wk52Day1Date) {
        this.wk52Day1Date = wk52Day1Date;
    }

    public String getWk52Day2Date() {
        return wk52Day2Date;
    }

    public void setWk52Day2Date(String wk52Day2Date) {
        this.wk52Day2Date = wk52Day2Date;
    }

    public String getWk52Day3Date() {
        return wk52Day3Date;
    }

    public void setWk52Day3Date(String wk52Day3Date) {
        this.wk52Day3Date = wk52Day3Date;
    }

    public String getWk52Day4Date() {
        return wk52Day4Date;
    }

    public void setWk52Day4Date(String wk52Day4Date) {
        this.wk52Day4Date = wk52Day4Date;
    }

    public String getWk52Day5Date() {
        return wk52Day5Date;
    }

    public void setWk52Day5Date(String wk52Day5Date) {
        this.wk52Day5Date = wk52Day5Date;
    }

    public String getWk52Day6Date() {
        return wk52Day6Date;
    }

    public void setWk52Day6Date(String wk52Day6Date) {
        this.wk52Day6Date = wk52Day6Date;
    }

    public String getWk52Day7Date() {
        return wk52Day7Date;
    }

    public void setWk52Day7Date(String wk52Day7Date) {
        this.wk52Day7Date = wk52Day7Date;
    }

    public BigDecimal getWk53Msrp() {
        return wk53Msrp;
    }

    public void setWk53Msrp(BigDecimal wk53Msrp) {
        this.wk53Msrp = wk53Msrp;
    }

    public BigDecimal getWk53MsrpUSD() {
        return wk53MsrpUSD;
    }

    public void setWk53MsrpUSD(BigDecimal wk53MsrpUSD) {
        this.wk53MsrpUSD = wk53MsrpUSD;
    }

    public Integer getWk53Status() {
        return wk53Status;
    }

    public void setWk53Status(Integer wk53Status) {
        this.wk53Status = wk53Status;
    }

    public String getWk53Day1Date() {
        return wk53Day1Date;
    }

    public void setWk53Day1Date(String wk53Day1Date) {
        this.wk53Day1Date = wk53Day1Date;
    }

    public String getWk53Day2Date() {
        return wk53Day2Date;
    }

    public void setWk53Day2Date(String wk53Day2Date) {
        this.wk53Day2Date = wk53Day2Date;
    }

    public String getWk53Day3Date() {
        return wk53Day3Date;
    }

    public void setWk53Day3Date(String wk53Day3Date) {
        this.wk53Day3Date = wk53Day3Date;
    }

    public String getWk53Day4Date() {
        return wk53Day4Date;
    }

    public void setWk53Day4Date(String wk53Day4Date) {
        this.wk53Day4Date = wk53Day4Date;
    }

    public String getWk53Day5Date() {
        return wk53Day5Date;
    }

    public void setWk53Day5Date(String wk53Day5Date) {
        this.wk53Day5Date = wk53Day5Date;
    }

    public String getWk53Day6Date() {
        return wk53Day6Date;
    }

    public void setWk53Day6Date(String wk53Day6Date) {
        this.wk53Day6Date = wk53Day6Date;
    }

    public String getWk53Day7Date() {
        return wk53Day7Date;
    }

    public void setWk53Day7Date(String wk53Day7Date) {
        this.wk53Day7Date = wk53Day7Date;
    }

    public BigDecimal getWk53Day1() {
        return wk53Day1;
    }

    public void setWk53Day1(BigDecimal wk53Day1) {
        this.wk53Day1 = wk53Day1;
    }

    public Integer getWk53Day1Status() {
        return wk53Day1Status;
    }

    public void setWk53Day1Status(Integer wk53Day1Status) {
        this.wk53Day1Status = wk53Day1Status;
    }

    public BigDecimal getWk53Day2() {
        return wk53Day2;
    }

    public void setWk53Day2(BigDecimal wk53Day2) {
        this.wk53Day2 = wk53Day2;
    }

    public Integer getWk53Day2Status() {
        return wk53Day2Status;
    }

    public void setWk53Day2Status(Integer wk53Day2Status) {
        this.wk53Day2Status = wk53Day2Status;
    }

    public BigDecimal getWk53Day3() {
        return wk53Day3;
    }

    public void setWk53Day3(BigDecimal wk53Day3) {
        this.wk53Day3 = wk53Day3;
    }

    public Integer getWk53Day3Status() {
        return wk53Day3Status;
    }

    public void setWk53Day3Status(Integer wk53Day3Status) {
        this.wk53Day3Status = wk53Day3Status;
    }

    public BigDecimal getWk53Day4() {
        return wk53Day4;
    }

    public void setWk53Day4(BigDecimal wk53Day4) {
        this.wk53Day4 = wk53Day4;
    }

    public Integer getWk53Day4Status() {
        return wk53Day4Status;
    }

    public void setWk53Day4Status(Integer wk53Day4Status) {
        this.wk53Day4Status = wk53Day4Status;
    }

    public BigDecimal getWk53Day5() {
        return wk53Day5;
    }

    public void setWk53Day5(BigDecimal wk53Day5) {
        this.wk53Day5 = wk53Day5;
    }

    public Integer getWk53Day5Status() {
        return wk53Day5Status;
    }

    public void setWk53Day5Status(Integer wk53Day5Status) {
        this.wk53Day5Status = wk53Day5Status;
    }

    public BigDecimal getWk53Day6() {
        return wk53Day6;
    }

    public void setWk53Day6(BigDecimal wk53Day6) {
        this.wk53Day6 = wk53Day6;
    }

    public Integer getWk53Day6Status() {
        return wk53Day6Status;
    }

    public void setWk53Day6Status(Integer wk53Day6Status) {
        this.wk53Day6Status = wk53Day6Status;
    }

    public BigDecimal getWk53Day7() {
        return wk53Day7;
    }

    public void setWk53Day7(BigDecimal wk53Day7) {
        this.wk53Day7 = wk53Day7;
    }

    public Integer getWk53Day7Status() {
        return wk53Day7Status;
    }

    public void setWk53Day7Status(Integer wk53Day7Status) {
        this.wk53Day7Status = wk53Day7Status;
    }

    public Long getDisposeGroupId() {
        return disposeGroupId;
    }

    public void setDisposeGroupId(Long disposeGroupId) {
        this.disposeGroupId = disposeGroupId;
    }

    public String getDisposeGroupName() {
        return disposeGroupName;
    }

    public void setDisposeGroupName(String disposeGroupName) {
        this.disposeGroupName = disposeGroupName;
    }
}
