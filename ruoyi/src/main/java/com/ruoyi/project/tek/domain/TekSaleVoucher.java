package com.ruoyi.project.tek.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 销售凭证配置表
 * </p>
 *
 * @author steven.guo
 * @since 2020-05-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TekSaleVoucher implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 销售组织 
     */
    private String vkorg;

    /**
     * 销售组织描述
     */
    private String vkVtext;

    /**
     * 分销渠道
     */
    private String vtweg;

    /**
     * 分销渠道描述
     */
    private String vtVtext;

    /**
     * 产品组 
     */
    private String spart;

    /**
     * 产品组描述
     */
    private String spVtext;

    /**
     * 销售凭证类型 
     */
    private String auart;

    /**
     * 销售凭证描述
     */
    private String auVtext;

    /**
     * 删除标志[默认false]
     */
    private Boolean delFlag;

    /**
     * 创建id
     */
    private String createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createDt;

    /**
     * 更新id
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateDt;


}
