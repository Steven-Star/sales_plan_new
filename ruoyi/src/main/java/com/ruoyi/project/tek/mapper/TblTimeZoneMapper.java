package com.ruoyi.project.tek.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.project.tek.domain.TblTimeZone;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 时区管理 Mapper 接口
 * </p>
 *
 * @author steven.guo
 * @since 2020-05-29
 */
@Repository
public interface TblTimeZoneMapper extends BaseMapper<TblTimeZone> {

    List<TblTimeZone> getTimeZoneListAll();

}
