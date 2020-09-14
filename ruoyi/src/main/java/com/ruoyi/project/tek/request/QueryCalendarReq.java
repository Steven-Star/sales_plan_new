package com.ruoyi.project.tek.request;

import lombok.Data;

import java.util.List;

/**
 * @author steven.guo
 * @version 1.0
 * @description:
 * @date 2020/6/2 22:07
 */
@Data
public class QueryCalendarReq {

    /**
     * 产品sku
     * */
    private String sku;

    /**
     * 前台编码后的sku，后台要解码一下使用
     * */
    private String skuEncode;

    /**
     * 零售商名称(客户名称)
     * */
    private String customerName;

    private String modelName;

    private String year;

    private String disposeName;

    private String country;

    private Long categoryId;

    private Long modelId;

    private Long disposeId;

    private Integer disposeSortId;

    private Integer modelSortId;

    private List<String> countryNameList;

    private List<String> customerList;

}
