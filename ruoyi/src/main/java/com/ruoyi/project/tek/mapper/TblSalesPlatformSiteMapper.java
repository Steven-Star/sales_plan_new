package com.ruoyi.project.tek.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.project.tek.domain.TblSalesPlatformSite;
import com.ruoyi.project.tek.vo.TblSalesPlatformSiteVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 销售平台的国别限制 Mapper 接口
 * </p>
 *
 * @author steven.guo
 * @since 2020-05-29
 */
@Repository
public interface TblSalesPlatformSiteMapper extends BaseMapper<TblSalesPlatformSite> {

    List<TblSalesPlatformSiteVO> getAllSalesPlatformAndSite(String salesPlatform, String site);



}
