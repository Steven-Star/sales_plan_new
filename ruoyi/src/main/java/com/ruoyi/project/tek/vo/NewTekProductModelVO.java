package com.ruoyi.project.tek.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * @author steven.guo
 * @version 1.0
 * @description:
 * @date 2020/6/3 16:03
 */
@Data
public class NewTekProductModelVO {

    private Long id;

    @Excel(name = "SKU", type = Excel.Type.EXPORT)
    private String sku;

    @Excel(name = "Country", type = Excel.Type.EXPORT)
    private String countryName;

    @Excel(name = "ChannelType", type = Excel.Type.EXPORT)
    private String channelType;

    @Excel(name = "CategoryName", type = Excel.Type.EXPORT)
    private String categoryName;

    @Excel(name = "ModelName", type = Excel.Type.EXPORT)
    private String modelName;

    @Excel(name = "DisposeName", type = Excel.Type.EXPORT)
    private String disposeName;

    @Excel(name = "createTime", type = Excel.Type.EXPORT)
    private String createTimeStr;

    @Excel(name = "updateTime", type = Excel.Type.EXPORT)
    private String updateTimeStr;

    @Excel(name = "creator", type = Excel.Type.EXPORT)
    private String createBy;

    @Excel(name = "updator", type = Excel.Type.EXPORT)
    private String updateBy;

    @Excel(name = "remark", type = Excel.Type.EXPORT)
    private String remark;

    private Long productId;

    private Long modelId;

    private String productName;

    private Long categoryId;

    private Long disposeId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateDate;







}
