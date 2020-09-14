package com.ruoyi.project.tek.request;

import lombok.Data;

/**
 * @author steven.guo
 * @version 1.0
 * @description:
 * @date 2020/6/16 15:55
 */
@Data
public class QueryCategoryReq {

    /**
     * 产品品类名称
     * */
    private String categoryName;

    private Long disposeGroupId;

}
