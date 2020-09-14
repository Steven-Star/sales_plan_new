package com.ruoyi.project.tek.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 销售平台的国别限制
 * </p>
 *
 * @author steven.guo
 * @since 2020-05-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TblSalesPlatformSite implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 销售平台
     */
    private String salesPlatform;

    /**
     * 平台名称
     */
    private String platformName;

    /**
     * site
     */
    private String site;

    /**
     * 站点名称
     */
    private String siteName;

    /**
     * 备注
     */
    private String remark;

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
