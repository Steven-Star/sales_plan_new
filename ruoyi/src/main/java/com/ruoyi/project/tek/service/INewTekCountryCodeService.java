package com.ruoyi.project.tek.service;

import com.ruoyi.project.tek.domain.NewTekCountryCode;
import com.ruoyi.project.tek.domain.NewTekCurrencyCode;

import java.util.List;

/**
 * <p>
 * 国家和地区代码表 服务类
 * </p>
 *
 * @author steven.guo
 * @since 2020-07-08
 */
public interface INewTekCountryCodeService {

    List<NewTekCountryCode> queryNewTekCountryCodeAll(String code);

    NewTekCountryCode getNewTekCountryCodeById(Long id);

    NewTekCountryCode getCountryCodeByCode(String code);

    List<NewTekCountryCode> queryNewTekCountryCodeAllForPermission(NewTekCountryCode newTekCountryCode);

}
