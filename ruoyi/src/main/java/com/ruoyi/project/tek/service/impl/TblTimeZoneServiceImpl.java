package com.ruoyi.project.tek.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.project.tek.domain.TblTimeZone;
import com.ruoyi.project.tek.mapper.TblTimeZoneMapper;
import com.ruoyi.project.tek.service.ITblTimeZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 时区管理 服务实现类
 * </p>
 *
 * @author steven.guo
 * @since 2020-05-29
 */
@Service
public class TblTimeZoneServiceImpl extends ServiceImpl<TblTimeZoneMapper, TblTimeZone> implements ITblTimeZoneService {

    @Autowired
    private TblTimeZoneMapper tblTimeZoneMapper;



    @Override
    public List<TblTimeZone> getTimeZoneListAll() {
        List<TblTimeZone> tblTimeZoneList = tblTimeZoneMapper.getTimeZoneListAll();
        return tblTimeZoneList;
    }

    private static Map<String, TblTimeZone> siteMap = null;

    @Override
    public Map<String, TblTimeZone> getTimezoneMap() {
        if (siteMap != null) {
            // 只加载一次
            return siteMap;
        }
        List<TblTimeZone> timeZoneDOS = tblTimeZoneMapper.getTimeZoneListAll();
        // 构建站点code和name的映射
        siteMap = new HashMap<>(timeZoneDOS.size());
        for (TblTimeZone timeZoneDO : timeZoneDOS) {
            siteMap.put(timeZoneDO.getSiteCode(), timeZoneDO);
        }
        return siteMap;
    }

}
