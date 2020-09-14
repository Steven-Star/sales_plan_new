package com.ruoyi.project.tek.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 销售计划项目-产品和系列的关系表
 * </p>
 *
 * @author steven.guo
 * @since 2020-06-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class NewTekProductModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 对应的产品ID
     */
    private Long productId;

    private Long categoryId;

    @Excel(name = "CategoryName")
    private String categoryName;

    /**
     * 对应的系列ID
     */
    private Long modelId;

    @Excel(name = "modelName")
    private String modelName;

    @Excel(name = "disposeName")
    private String disposeName;

    private Long disposeId;

    /**
     * 对应的产品sku
     */
    @Excel(name = "SKU")
    private String sku;

    @Excel(name = "Country")
    private String countryName;

    /**
     * 渠道类别
     * offline、online、both
     * */
    @Excel(name = "ChannelType")
    private String channelType;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private char delFlag;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;

    private Integer disposeSortId;

    private Integer modelSortId;

    private Long disposeGroupId;


}
