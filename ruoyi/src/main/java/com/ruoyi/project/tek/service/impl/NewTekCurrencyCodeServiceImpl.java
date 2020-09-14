package com.ruoyi.project.tek.service.impl;

import com.ruoyi.project.tek.domain.NewTekCurrencyCode;
import com.ruoyi.project.tek.mapper.NewTekCurrencyCodeMapper;
import com.ruoyi.project.tek.service.INewTekCurrencyCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author steven.guo
 * @version 1.0
 * @description:
 * @date 2020/6/29 11:25
 */
@Service
public class NewTekCurrencyCodeServiceImpl implements INewTekCurrencyCodeService {

    private static final Logger log = LoggerFactory.getLogger(NewTekCurrencyCodeServiceImpl.class);

    @Autowired
    private NewTekCurrencyCodeMapper newTekCurrencyCodeMapper;

    @Override
    public List<NewTekCurrencyCode> queryNewTekCurrencyCodeAll(String country) {
        return newTekCurrencyCodeMapper.queryNewTekCurrencyCodeAll(country);
    }

    @Override
    public NewTekCurrencyCode getNewTekCurrencyCodeById(Long id) {
        return newTekCurrencyCodeMapper.getNewTekCurrencyCodeById(id);
    }

    @Override
    public NewTekCurrencyCode getCurrencyCodeByCountry(String country) {
        return newTekCurrencyCodeMapper.getCurrencyCodeByCountry(country);
    }
}
