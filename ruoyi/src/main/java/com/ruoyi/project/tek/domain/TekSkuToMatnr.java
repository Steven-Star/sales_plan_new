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
public class TekSkuToMatnr implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 物料号matnr
     */
    private String matnr;

    /**
     * 物料描述
     */
    private String description;

    /**
     * 商品型号
     */
    private String goodsModel;

    /**
     * 商品sku
     */
    private String goodsSku;

    /**
     * 分类
     */
    private String classify;

    /**
     * ABCD
     */
    private String abcd;

    /**
     * 国别
     */
    private String country;

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
