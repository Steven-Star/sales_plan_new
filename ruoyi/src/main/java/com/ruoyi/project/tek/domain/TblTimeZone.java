package com.ruoyi.project.tek.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 时区管理
 * </p>
 *
 * @author steven.guo
 * @since 2020-05-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TblTimeZone implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 站点名称
     */
    private String siteName;

    /**
     * 站点code
     */
    private String siteCode;

    /**
     * 0时区偏差
     */
    private String offset;

    /**
     * 描述
     */
    private String remark;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String createBy;

    private String updateBy;

    /**
     * 0-已删除    1-未删除
     */
    private Integer isDelete;

    /**
     * 中文名称
     */
    private String chineseName;

    private String country;


}
