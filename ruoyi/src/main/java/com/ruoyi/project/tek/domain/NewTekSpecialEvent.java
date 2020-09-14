package com.ruoyi.project.tek.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 销售计划项目-特殊事件表（记录手动添加的日期对应的特殊事件）
 * </p>
 *
 * @author steven.guo
 * @since 2020-06-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class NewTekSpecialEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 对应的事件日期,yyyy-mm-dd
     */
    private String eventDay;

    private String eventName;

    private String year;

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

}
