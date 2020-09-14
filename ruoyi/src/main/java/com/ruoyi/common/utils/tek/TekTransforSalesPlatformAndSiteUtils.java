package com.ruoyi.common.utils.tek;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.ruoyi.project.tek.domain.TblTimeZone;
import com.ruoyi.project.tek.vo.SalesPlatformWithSiteVO;
import com.ruoyi.project.tek.vo.TblSalesPlatformSiteVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lihuinch
 * @date 2019/11/28 17:14
 */
public class TekTransforSalesPlatformAndSiteUtils {

    protected final static Logger logger = LoggerFactory.getLogger(TekTransforSalesPlatformAndSiteUtils.class);

    public static List<SalesPlatformWithSiteVO> buildSalesPlatformWithSiteVO(List<TblSalesPlatformSiteVO> dos, Map<String, TblTimeZone> siteMap) {
        Map<String, SalesPlatformWithSiteVO> plaMap = new HashMap<>(10);

        for (TblSalesPlatformSiteVO mem : dos) {

            // 得到用户可访问的站点
            if (StringUtils.isNotEmpty(mem.getSite())) {
                String salesPlatform = mem.getSalesPlatform();

                SalesPlatformWithSiteVO pla = plaMap.get(salesPlatform);
                if (pla == null) {
                    // 不存在
                    pla = new SalesPlatformWithSiteVO();
                    pla.setName(mem.getPlatformName());
                    pla.setValue(mem.getSalesPlatform());
                    pla.setRoleSites(new ArrayList<>());

                    plaMap.put(salesPlatform, pla);
                }

                pla.getRoleSites().add(TekTransforSalesPlatformAndSiteUtils.buildSiteAccess(mem, siteMap.get(mem.getSite())));
            }
        }
        return new ArrayList<>(plaMap.values());
    }

    public static SalesPlatformWithSiteVO.SiteAccess buildSiteAccess(TblSalesPlatformSiteVO salesPlatformSiteDO, TblTimeZone timeZoneDO) {
        if (timeZoneDO == null) {
            return null;
        }

        SalesPlatformWithSiteVO.SiteAccess siteAccess;
        siteAccess = new SalesPlatformWithSiteVO.SiteAccess();
        siteAccess.setName(salesPlatformSiteDO.getSiteName());
        siteAccess.setValue(salesPlatformSiteDO.getSite());
        siteAccess.setOffset(timeZoneDO.getOffset());
        siteAccess.setId(salesPlatformSiteDO.getId());
        siteAccess.setCountry(timeZoneDO.getCountry());
        siteAccess.setChineseName(timeZoneDO.getChineseName());
        siteAccess.setId(salesPlatformSiteDO.getId());
        return siteAccess;
    }

    public static List<Long> transferString2LongList(String value, String separator) {
        if (value == null || separator == null) {
            return null;
        }
        String[] split = StringUtils.split(value, separator);

        if (split == null) {
            return null;
        }

        List<Long> result = new ArrayList<>();

        for (String aSplit : split) {
            try {
                result.add(new Long(aSplit));
            } catch (Exception e) {
                logger.error("类型转换失败 {} : {}", aSplit, e.getMessage());
            }
        }

        return result;
    }

}
