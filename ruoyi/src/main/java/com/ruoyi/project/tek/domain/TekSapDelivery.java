package com.ruoyi.project.tek.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * sap来的800仓库货物
 * </p>
 *
 * @author steven.guo
 * @since 2020-05-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TekSapDelivery implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 数据序号
     */
    private String serialNo;

    /**
     * 关联的sap数据id
     */
    private Long fromSapId;

    /**
     * 800号(交货单)
     */
    private String deliveryOrderNo;

    /**
     * 库位-国家
     */
    private String country;

    /**
     * 库位-服务商
     */
    private Integer serverProvider;

    /**
     * 商品sku
     */
    private String goodsSku;

    /**
     * 入库数量（正数）
     */
    private BigDecimal putInCounts;

    /**
     * 是否放到了仓库中
     */
    private Boolean putInFlag;

    /**
     * 入库时间
     */
    private LocalDateTime putInTime;

    /**
     * 入库的外键id（FBM为tek_fbm_wait_receiving表id，fba为shipping_schedule的id）
     */
    private String receiveRelatedId;

    /**
     * 实时在仓库中的数量
     */
    private BigDecimal realInStockCounts;

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
