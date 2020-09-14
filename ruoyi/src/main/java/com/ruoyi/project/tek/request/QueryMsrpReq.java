package com.ruoyi.project.tek.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author steven.guo
 * @version 1.0
 * @description:
 * @date 2020/6/2 22:07
 */
@Data
public class QueryMsrpReq {

    /**
     * sku
     * */
    private String sku;

    private Long productId;

//    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private String validFrom;

//    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private String validTo;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date validFromDate;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date validToDate;

    private List<String> countryNameList;


}
