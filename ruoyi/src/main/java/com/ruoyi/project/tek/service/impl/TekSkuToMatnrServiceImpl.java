package com.ruoyi.project.tek.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.framework.aspectj.lang.annotation.DataSource;
import com.ruoyi.framework.aspectj.lang.enums.DataSourceType;
import com.ruoyi.project.tek.domain.TekSkuToMatnr;
import com.ruoyi.project.tek.mapper.TekSkuToMatnrMapper;
import com.ruoyi.project.tek.service.ITekSkuToMatnrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 字典 服务实现类
 * </p>
 *
 * @author steven.guo
 * @since 2020-05-29
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class TekSkuToMatnrServiceImpl extends ServiceImpl<TekSkuToMatnrMapper, TekSkuToMatnr> implements ITekSkuToMatnrService {

    @Autowired
    private TekSkuToMatnrMapper tekSkuToMatnrMapper;

    @Override
    public List<TekSkuToMatnr> queryTekSkuAll() {
        return tekSkuToMatnrMapper.queryTekSkuAll();
    }

    @Override
    public List<TekSkuToMatnr> queryTekSkuByGoodsSKU(String goodsSku) {
        return tekSkuToMatnrMapper.queryTekSkuByGoodsSKU(goodsSku);
    }
}
