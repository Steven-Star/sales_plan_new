package com.ruoyi.project.tek.service;

import com.ruoyi.project.tek.domain.TblTimeZone;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 时区管理 服务类
 * </p>
 *
 * @author steven.guo
 * @since 2020-05-29
 */
public interface ITblTimeZoneService {

    List<TblTimeZone> getTimeZoneListAll();

    Map<String, TblTimeZone>  getTimezoneMap();


}
