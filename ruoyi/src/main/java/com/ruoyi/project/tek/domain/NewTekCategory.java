package com.ruoyi.project.tek.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 销售计划项目-产品品类表
 * </p>
 *
 * @author steven.guo
 * @since 2020-06-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class NewTekCategory implements Serializable {

    private static final long serialVersionUID = 1L;



    @Excel(name = "CategoryId", type = Excel.Type.EXPORT)
    private Long id;

    /**
     * 产品品类名称
     */
    @Excel(name = "CategoryName", type = Excel.Type.EXPORT)
    private String categoryName;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private char delFlag;

    @Excel(name = "createTime", type = Excel.Type.EXPORT)
    private String createTimeStr;

    @Excel(name = "updateTime", type = Excel.Type.EXPORT)
    private String updateTimeStr;

    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")

    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")

    private Date updateTime;

    /**
     * 创建者
     */
    @Excel(name = "creator", type = Excel.Type.EXPORT)
    private String createBy;

    /**
     * 更新者
     */
    @Excel(name = "updator", type = Excel.Type.EXPORT)
    private String updateBy;

    /**
     * 备注
     */
    @Excel(name = "remark", type = Excel.Type.EXPORT)
    private String remark;

    private Long disposeGroupId;

    private String disposeGroupName;

}
