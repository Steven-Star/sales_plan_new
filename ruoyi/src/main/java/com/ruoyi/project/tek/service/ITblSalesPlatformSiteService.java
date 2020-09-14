package com.ruoyi.project.tek.service;

import com.ruoyi.project.tek.vo.SalesPlatformWithSiteVO;

import java.util.List;

/**
 * <p>
 * 销售平台的国别限制 服务类
 * </p>
 *
 * @author steven.guo
 * @since 2020-05-29
 */
public interface ITblSalesPlatformSiteService {

    List<SalesPlatformWithSiteVO> getAllSalesPlatformAndSite(String salesPlatform, String site);


}
