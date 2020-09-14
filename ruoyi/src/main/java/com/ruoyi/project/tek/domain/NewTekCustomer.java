package com.ruoyi.project.tek.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 销售计划项目-客户表
 * </p>
 *
 * @author steven.guo
 * @since 2020-06-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class NewTekCustomer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Excel(name = "CustomerId", type = Excel.Type.EXPORT)
    private Long id;

    /**
     * 对应国家的名称
     * */
    @Excel(name = "Country", type = Excel.Type.EXPORT)
    private String country;

    /**
     * 客户名称
     */
    @Excel(name = "CustomerName", type = Excel.Type.EXPORT)
    private String customerName;

    @Excel(name = "createTime", type = Excel.Type.EXPORT)
    private String createTimeStr;

    @Excel(name = "updateTime", type = Excel.Type.EXPORT)
    private String updateTimeStr;

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

    /**
     * 对应国家ID
     * */
    private Long countryId;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private char delFlag;

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

}
