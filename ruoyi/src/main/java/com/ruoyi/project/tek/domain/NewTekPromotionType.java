package com.ruoyi.project.tek.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 国家和地区代码表
 * </p>
 *
 * @author steven.guo
 * @since 2020-07-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class NewTekPromotionType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Excel(name = "PromotionTypeId", type = Excel.Type.EXPORT)
    private Long id;

    @Excel(name = "PromotionTypeId", type = Excel.Type.EXPORT)
    private String promotionType;

    /**
     * 备注
     */
    @Excel(name = "remark", type = Excel.Type.EXPORT)
    private String remark;

    @Excel(name = "createTime", type = Excel.Type.EXPORT)
    private String createTimeStr;

    @Excel(name = "updateTime", type = Excel.Type.EXPORT)
    private String updateTimeStr;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private char delFlag;

    /**
     * 创建者
     */
    @Excel(name = "creator", type = Excel.Type.EXPORT)
    private String createBy;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新者
     */
    @Excel(name = "updator", type = Excel.Type.EXPORT)
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;



}
