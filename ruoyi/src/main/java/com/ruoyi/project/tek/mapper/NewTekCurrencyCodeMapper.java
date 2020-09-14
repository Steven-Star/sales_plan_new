package com.ruoyi.project.tek.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.project.tek.domain.NewTekCountryCode;
import com.ruoyi.project.tek.domain.NewTekCurrencyCode;
import com.ruoyi.project.tek.domain.NewTekModel;
import com.ruoyi.project.tek.request.QueryModelReq;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 销售计划项目-币别表 Mapper 接口
 * </p>
 *
 * @author steven.guo
 * @since 2020-06-29
 */
@Repository
public interface NewTekCurrencyCodeMapper extends BaseMapper<NewTekCurrencyCode> {

    List<NewTekCurrencyCode> queryNewTekCurrencyCodeAll(String country);

    NewTekCurrencyCode getNewTekCurrencyCodeById(Long id);

    NewTekCurrencyCode getCurrencyCodeByCountry(String country);

}
