package com.ruoyi.project.tek.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 销售计划项目-各国家汇率转换对照表
 * </p>
 *
 * @author steven.guo
 * @since 2020-06-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class NewTekCurrencyData implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 原国家
     */
    private String fromCountry;

    /**
     * 要转换的国家
     * */
    private String toCountry;

    /**
     * 当前汇率
     * */
    private String currency;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private char delFlag;

    /**
     * 创建者
     */
    private String createBy;

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

}
