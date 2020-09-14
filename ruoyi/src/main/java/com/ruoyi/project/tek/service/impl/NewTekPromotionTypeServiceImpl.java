package com.ruoyi.project.tek.service.impl;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.tek.domain.NewTekMsrpWk;
import com.ruoyi.project.tek.domain.NewTekPromotionType;
import com.ruoyi.project.tek.mapper.NewTekMsrpWkMapper;
import com.ruoyi.project.tek.mapper.NewTekPromotionTypeMapper;
import com.ruoyi.project.tek.service.INewTekPromotionTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author steven.guo
 * @version 1.0
 * @description:
 * @date 2020/7/08 15:32
 */
@Service
public class NewTekPromotionTypeServiceImpl implements INewTekPromotionTypeService {

    private static final Logger log = LoggerFactory.getLogger(NewTekPromotionTypeServiceImpl.class);

    @Autowired
    private NewTekPromotionTypeMapper newTekPromotionTypeMapper;
    @Autowired
    private NewTekMsrpWkMapper newTekMsrpWkMapper;


    @Override
    public List<NewTekPromotionType> queryNewTekPromotionTypeAll(NewTekPromotionType newTekPromotionType) {
        return newTekPromotionTypeMapper.queryNewTekPromotionTypeAll(newTekPromotionType);
    }

    @Override
    public int insertNewTekPromotionType(NewTekPromotionType newTekPromotionType) {
        return newTekPromotionTypeMapper.insertNewTekPromotionType(newTekPromotionType);
    }

    @Override
    public int updateNewTekPromotionType(NewTekPromotionType newTekPromotionType) {
        Long id = newTekPromotionType.getId();
        NewTekPromotionType newTekPromotionType1 = newTekPromotionTypeMapper.getNewTekPromotionTypeById(id);
        List<NewTekMsrpWk> msrpWkList = newTekMsrpWkMapper.queryMsrpWKByPromotionType(newTekPromotionType1.getPromotionType());
        for (NewTekMsrpWk newTekMsrpWk : msrpWkList) {
            newTekMsrpWk.setPromotionType(newTekPromotionType.getPromotionType());
            newTekMsrpWk.setUpdateBy(SecurityUtils.getUsername());
            newTekMsrpWkMapper.updateNewTekMsrpWk(newTekMsrpWk);
        }
        return newTekPromotionTypeMapper.updateNewTekPromotionType(newTekPromotionType);
    }

    @Override
    public NewTekPromotionType getNewTekPromotionTypeById(Long id) {
        return newTekPromotionTypeMapper.getNewTekPromotionTypeById(id);
    }

    @Override
    public int deletePromotionTypeByIds(Long[] ids) {
        return newTekPromotionTypeMapper.deletePromotionTypeByIds(ids);
    }

    @Override
    public List<NewTekPromotionType> getAllPromotionType() {
        return newTekPromotionTypeMapper.getAllPromotionType();
    }

    @Override
    public boolean checkPromotionTypeUnique(String promotionType) {
        NewTekPromotionType newTekPromotionType = new NewTekPromotionType();
        newTekPromotionType.setPromotionType(promotionType);
        List<NewTekPromotionType> promotionTypeList = newTekPromotionTypeMapper.queryNewTekPromotionTypeAll(newTekPromotionType);
        return promotionTypeList.size() > 0 && promotionTypeList != null ? true : false;
    }
}
