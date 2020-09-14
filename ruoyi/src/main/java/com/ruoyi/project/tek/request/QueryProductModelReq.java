package com.ruoyi.project.tek.request;

import lombok.Data;
import org.omg.CORBA.PRIVATE_MEMBER;

import java.util.List;

/**
 * @author steven.guo
 * @version 1.0
 * @description:
 * @date 2020/6/2 22:07
 */
@Data
public class QueryProductModelReq {

    /**
     * 产品系列名称
     * */
    private String modelName;

    /**
     * 产品名称
     * */
    private String productName;

    private Long modelId;

    private Long productId;

    private String sku;

    private String countryName;

    private Long categoryId;

    private Long disposeId;

    private String channelType;

    private List<String> countryNameList;

}
