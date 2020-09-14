package com.ruoyi.project.tek.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 物料主数据
 * </p>
 *
 * @author steven.guo
 * @since 2020-05-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TekMaterielData implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 工厂
     */
    private String werks;

    /**
     * 物料号 
     */
    private String matnr;

    /**
     * 在工厂级标记要删除的物料
     */
    private String lvorm;

    /**
     * 跨分销链物料状态 
     */
    private String mstav;

    /**
     * 工厂特定的物料状态
     */
    private String mmsta;

    /**
     * 物料描述（短文本） 
     */
    private String maktx;

    /**
     * 长描述 
     */
    private String zcms;

    /**
     * 旧物料号
     */
    private String zjwlh;

    /**
     * 产品类别
     */
    private String zcplb;

    /**
     * 物料类型
     */
    private String mtart;

    /**
     * 创建日期
     */
    private LocalDateTime ersda;

    /**
     * 上次更改的日期 
     */
    private LocalDateTime laeda;

    /**
     * 销售组织
     */
    private String vkorg;

    /**
     * 渠道
     */
    private String vtweg;

    /**
     * 指定分销链的物料状态 
     */
    private String vmsta;

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
