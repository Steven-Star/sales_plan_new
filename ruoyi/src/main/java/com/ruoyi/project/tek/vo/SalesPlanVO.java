package com.ruoyi.project.tek.vo;

import lombok.Data;

/**
 * @author steven.guo
 * @version 1.0
 * @description:
 * @date 2020/6/11 13:55
 */
@Data
public class SalesPlanVO {

    //销售预测录入列表的展示字段
    private Long id;

    private String salesPlatform;

    private String sku;

    private String site;

    private String productName;

    /**
     * 商品服务商
     * */
    private String serviceProviderName;

    private String serviceProviderId;

    private String LOC;

    private String skuAndLoc;

    private String lastYearOneMonth;

    private String lastYearOneMonthHistory;

    private String lastYearTwoMonth;

    private String lastYearTwoMonthHistory;

    private String lastYearThreeMonth;

    private String lastYearThreeMonthHistory;

    private String lastYearFourMonth;

    private String lastYearFourMonthHistory;

    private String lastYearFiveMonth;

    private String lastYearFiveMonthHistory;

    private String lastYearSixMonth;

    private String lastYearSixMonthHistory;

    private String lastYearSevenMonth;

    private String lastYearSevenMonthHistory;

    private String lastYearEightMonth;

    private String lastYearEightMonthHistory;

    private String lastYearNineMonth;

    private String lastYearNineMonthHistory;

    private String lastYearTenMonth;

    private String lastYearTenMonthHistory;

    private String lastYearElevenMonth;

    private String lastYearElevenMonthHistory;

    private String lastYearTwelveMonth;

    private String lastYearTwelveMonthHistory;

    private String demandGroup;


}
