package com.ruoyi.project.tek.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 添可库位调整
 * </p>
 *
 * @author steven.guo
 * @since 2020-05-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TekStockFlow implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 数据序号
     */
    private String serialNo;

    /**
     * 库位-国家
     */
    private String country;

    /**
     * 库位-服务商
     */
    private Integer serverProvider;

    /**
     * 库位-类型 （1.可售仓 2.在途 3.退换货 4.零部件）
     */
    private Integer type;

    /**
     * 商品sku
     */
    private String goodsSku;

    /**
     * 数量（正数入仓，负数出仓）
     */
    private BigDecimal counts;

    /**
     * 原因（包含tek_stock_adjust的原因）
     */
    private String reason;

    /**
     * 备注
     */
    private String remark;

    /**
     * 关联号码
     */
    private String relatedNo;

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

    /**
     * matnr表的id
     */
    private Long matnrId;


}
