package com.ruoyi.project.tek.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.project.tek.domain.NewTekCurrencyData;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 销售计划项目-各国家汇率转换对照表 Mapper 接口
 * </p>
 *
 * @author steven.guo
 * @since 2020-06-29
 */
@Repository
public interface NewTekCurrencyDataMapper extends BaseMapper<NewTekCurrencyData> {

    int insertNewTekCurrencyData(List<NewTekCurrencyData> newTekCurrencyDataList);

    List<NewTekCurrencyData> queryNewTekCurrencyDataAll();

    NewTekCurrencyData getNewTekCurrencyDataById(Long id);

    NewTekCurrencyData getCurrencyCodeByFromCountryAndToCountry(NewTekCurrencyData newTekCurrencyData);

    int deleteNewTekCurrencyDataAll();

}
