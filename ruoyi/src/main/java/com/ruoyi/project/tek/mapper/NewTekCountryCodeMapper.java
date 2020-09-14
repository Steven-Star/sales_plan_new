package com.ruoyi.project.tek.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.project.tek.domain.NewTekCountryCode;
import com.ruoyi.project.tek.domain.NewTekCurrencyCode;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 国家和地区代码表 Mapper 接口
 * </p>
 *
 * @author steven.guo
 * @since 2020-06-29
 */
@Repository
public interface NewTekCountryCodeMapper extends BaseMapper<NewTekCountryCode> {

    List<NewTekCountryCode> queryNewTekCountryCodeAll(String code);

    List<NewTekCountryCode> queryNewTekCountryCodeAllForPermission(NewTekCountryCode newTekCountryCode);

    NewTekCountryCode getNewTekCountryCodeById(Long id);

    NewTekCountryCode getCountryCodeByCode(String code);

    NewTekCountryCode getCountryCodeByCodeAndChineseName(String code);

}
