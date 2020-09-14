package com.ruoyi.project.tek.service;

import com.ruoyi.project.tek.domain.NewTekCurrencyCode;
import com.ruoyi.project.tek.domain.NewTekModel;
import com.ruoyi.project.tek.request.QueryModelReq;

import java.util.List;

/**
 * <p>
 * 币别表 服务类
 * </p>
 *
 * @author steven.guo
 * @since 2020-06-29
 */
public interface INewTekCurrencyCodeService {

    List<NewTekCurrencyCode> queryNewTekCurrencyCodeAll(String country);

    NewTekCurrencyCode getNewTekCurrencyCodeById(Long id);

    NewTekCurrencyCode getCurrencyCodeByCountry(String country);

}
