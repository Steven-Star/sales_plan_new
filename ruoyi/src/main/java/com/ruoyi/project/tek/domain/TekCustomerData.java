package com.ruoyi.project.tek.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 客户主数据表
 * </p>
 *
 * @author steven.guo
 * @since 2020-05-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TekCustomerData implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 客户编号
     */
    private String kunnr;

    /**
     * 国家代码
     */
    private String land1;

    /**
     * 名称 1
     */
    private String name1;

    /**
     * 名称 2
     */
    private String name2;

    /**
     * 排序字段
     */
    private String sortl;

    /**
     * 城市
     */
    private String ort01;

    /**
     * 邮政编码
     */
    private String pstlz;

    /**
     * 地区（省/自治区/直辖市、市、县） 
     */
    private String regio;

    /**
     * 住宅号及街道
     */
    private String stras;

    /**
     * 第一个电话号
     */
    private String telf1;

    /**
     * 传真号 
     */
    private String telfx;

    /**
     * 地址
     */
    private String adrnr;

    /**
     * 销售组织 
     */
    private String vkorg;

    /**
     * 分销渠道
     */
    private String vtweg;

    /**
     * 产品组 
     */
    private String spart;

    /**
     * 销售地区
     */
    private String bzirk;

    /**
     * 记录创建日期
     */
    private LocalDateTime erdat;

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
