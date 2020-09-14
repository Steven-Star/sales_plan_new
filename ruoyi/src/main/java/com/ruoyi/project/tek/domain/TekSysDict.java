package com.ruoyi.project.tek.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 字典
 * </p>
 *
 * @author steven.guo
 * @since 2020-05-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TekSysDict implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 编码
     */
    private String code;

    /**
     * 类型code
     */
    private String typeCode;

    /**
     * 字典名
     */
    private String name;

    /**
     * 字典值
     */
    private String value;

    /**
     * 是否是固定的
     */
    private Boolean fixed;

    /**
     * 组别,逗号分隔
     */
    private String groupsValue;

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
