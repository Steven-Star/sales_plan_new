package com.ruoyi.project.tek.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * sap销售凭证配置
 * </p>
 *
 * @author steven.guo
 * @since 2020-05-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TekSaleVoucherConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 销售订单类型
     */
    private String saleOrderType;

    /**
     * 收费行项目类别
     */
    private String chargeProjectType;

    /**
     * 收费描述
     */
    private String chargeDetail;

    /**
     * 免费行项目类别
     */
    private String freeProjectType;

    /**
     * 免费项目描述
     */
    private String freeDetail;


}
