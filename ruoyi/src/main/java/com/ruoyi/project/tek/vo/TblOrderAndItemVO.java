package com.ruoyi.project.tek.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

/**
 * @author steven.guo
 * @version 1.0
 * @description:
 * @date 2020/6/12 13:16
 */
@Data
public class TblOrderAndItemVO {

    private String salesPlatform;

    private String site;

    private String amazonOrderId;

    private String fulfillmentChannel;

    private String sellerSku;

    private LocalDate purchaseDate;

    private String year;

    private String month;

    private String skuCounts;

    private String ttt;

}
