package com.ruoyi.project.tek.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author steven.guo
 * @version 1.0
 * @description:
 * @date 2020/6/30 13:46
 */
@Data
public class QueryMsrpWKReq {

    /**
     * sku
     * */
    private String sku;

    private String validFrom;

    private String validTo;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date validFromDate;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date validToDate;

    private String country;

    private String customerName;

    private String year;

    private String promotionType;

    private String validFromWK;

    private String channelType;

    private List<String> countryNameList;

    private List<String> customerList;

}
