package com.ruoyi.project.tek.service;


import com.ruoyi.project.tek.domain.TekSkuToMatnr;

import java.util.List;

/**
 * <p>
 * 字典 服务类
 * </p>
 *
 * @author steven.guo
 * @since 2020-05-29
 */
public interface ITekSkuToMatnrService {

    List<TekSkuToMatnr> queryTekSkuAll();

    List<TekSkuToMatnr> queryTekSkuByGoodsSKU(String goodsSku);

}
