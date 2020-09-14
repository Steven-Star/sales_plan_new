package com.ruoyi.project.tek.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 销售计划项目-产品系列表
 * </p>
 *
 * @author steven.guo
 * @since 2020-06-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class NewTekModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Excel(name = "ModelId", type = Excel.Type.EXPORT)
    private Long id;

    /**
     * 产品系列名称
     */
    @Excel(name = "ModelName", type = Excel.Type.EXPORT)
    private String modelName;

    @Excel(name = "CategoryName", type = Excel.Type.EXPORT)
    private String categoryName;

    /**
     * 系列排序字段（1.2.3.4.5.6.7.8等）
     * */
    @Excel(name = "ModelSortId", type = Excel.Type.EXPORT)
    private Integer modelSortId;

    /**
     * 对应的品类ID
     * */
    private Long categoryId;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private char delFlag;

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
