package com.ruoyi.project.tek.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.tek.TekTransforSalesPlatformAndSiteUtils;
import com.ruoyi.project.tek.domain.TblSalesPlatformSite;
import com.ruoyi.project.tek.domain.TblTimeZone;
import com.ruoyi.project.tek.mapper.TblSalesPlatformSiteMapper;
import com.ruoyi.project.tek.service.ITblSalesPlatformSiteService;
import com.ruoyi.project.tek.service.ITblTimeZoneService;
import com.ruoyi.project.tek.vo.SalesPlatformWithSiteVO;
import com.ruoyi.project.tek.vo.TblSalesPlatformSiteVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 销售平台的国别限制 服务实现类
 * </p>
 *
 * @author steven.guo
 * @since 2020-05-29
 */
@Service
public class TblSalesPlatformSiteServiceImpl extends ServiceImpl<TblSalesPlatformSiteMapper, TblSalesPlatformSite> implements ITblSalesPlatformSiteService {

    @Autowired
    private TblSalesPlatformSiteMapper tblSalesPlatformSiteMapper;
    @Autowired
    private ITblTimeZoneService tblTimeZoneService;

    @Override
    public List<SalesPlatformWithSiteVO> getAllSalesPlatformAndSite(String salesPlatform, String site) {
        List<TblSalesPlatformSiteVO> tblSalesPlatformSiteList =  tblSalesPlatformSiteMapper.getAllSalesPlatformAndSite(salesPlatform,site);
        Map<String, TblTimeZone> tblTimeZoneMap =  tblTimeZoneService.getTimezoneMap();
        List<SalesPlatformWithSiteVO> salesPlatformWithSiteVOList = TekTransforSalesPlatformAndSiteUtils.buildSalesPlatformWithSiteVO(tblSalesPlatformSiteList,tblTimeZoneMap);
        return salesPlatformWithSiteVOList;
    }


}
