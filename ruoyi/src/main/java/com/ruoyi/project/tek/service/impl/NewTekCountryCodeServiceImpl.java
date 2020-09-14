package com.ruoyi.project.tek.service.impl;

import com.ruoyi.project.tek.domain.NewTekCountryCode;
import com.ruoyi.project.tek.mapper.NewTekCountryCodeMapper;
import com.ruoyi.project.tek.service.INewTekCountryCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author steven.guo
 * @version 1.0
 * @description:
 * @date 2020/7/8 13:39
 */
@Service
public class NewTekCountryCodeServiceImpl implements INewTekCountryCodeService {

    @Autowired
    private NewTekCountryCodeMapper newTekCountryCodeMapper;

    @Override
    public List<NewTekCountryCode> queryNewTekCountryCodeAll(String code) {
        return newTekCountryCodeMapper.queryNewTekCountryCodeAll(code);
    }

    @Override
    public NewTekCountryCode getNewTekCountryCodeById(Long id) {
        return newTekCountryCodeMapper.getNewTekCountryCodeById(id);
    }

    @Override
    public NewTekCountryCode getCountryCodeByCode(String code) {
        return newTekCountryCodeMapper.getCountryCodeByCode(code);
    }

    @Override
    public List<NewTekCountryCode> queryNewTekCountryCodeAllForPermission(NewTekCountryCode newTekCountryCode) {
        return newTekCountryCodeMapper.queryNewTekCountryCodeAllForPermission(newTekCountryCode);
    }
}
