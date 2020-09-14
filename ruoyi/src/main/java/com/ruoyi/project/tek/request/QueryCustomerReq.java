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
public class QueryCustomerReq {

    /**
     * 客户名称
     * */
    private String customerName;

    /**
     * 客户对应的国家
     * */
    private String country;

    private List<String> countryNameList;

    private List<String> customerNameList;

}
