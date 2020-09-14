package com.ruoyi.project.tek.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * sap配置表
 * </p>
 *
 * @author steven.guo
 * @since 2020-05-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TekSapParamConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 业务编号
     */
    private String businessNumber;

    /**
     * 业务类型
     */
    private String businessType;

    /**
     * 业务类型描述
     */
    private String businessTypeDetail;

    /**
     * 业务反向标识
     */
    private String businessReserve;

    /**
     * 业务单据编号
     */
    private String businessBillNo;

    /**
     * 业务步骤
     */
    private String businessStep;

    /**
     * 工厂
     */
    private String factory;

    /**
     * 子程序名
     */
    private String childProgramName;

    /**
     * 子程序描述
     */
    private String childProgramDetail;


}
