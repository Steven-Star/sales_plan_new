package com.ruoyi.project.tek.service.impl;

import com.ruoyi.project.tek.domain.NewTekCategory;
import com.ruoyi.project.tek.mapper.NewTekCategoryMapper;
import com.ruoyi.project.tek.mapper.NewTekProductModelMapper;
import com.ruoyi.project.tek.request.QueryCategoryReq;
import com.ruoyi.project.tek.service.INewTekCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author steven.guo
 * @version 1.0
 * @description:
 * @date 2020/6/16 16:25
 */
@Service
public class NewTekCategoryServiceImpl implements INewTekCategoryService {

    private static final Logger log = LoggerFactory.getLogger(NewTekCategoryServiceImpl.class);

    @Autowired
    private NewTekCategoryMapper newTekCategoryMapper;

    @Override
    public int insertNewTekCategory(NewTekCategory newTekCategory) {
        int rows = newTekCategoryMapper.insertNewTekCategory(newTekCategory);
        return rows;
    }

    @Override
    @Transactional
    public int updateNewTekCategory(NewTekCategory newTekCategory) {
        return newTekCategoryMapper.updateNewTekCategory(newTekCategory);
    }

    @Override
    public List<NewTekCategory> queryNewTekCategoryAll(QueryCategoryReq req) {
        return newTekCategoryMapper.queryNewTekCategoryAll(req);
    }

    @Override
    public NewTekCategory queryNewTekCategoryByName(String categoryName) {
        return newTekCategoryMapper.queryNewTekCategoryByName(categoryName);
    }

    @Override
    public NewTekCategory getNewTekCategoryById(Long id) {
        return newTekCategoryMapper.getNewTekCategoryById(id);
    }

    public int deleteCategoryByIds(Long[] ids) {
        return newTekCategoryMapper.deleteCategoryByIds(ids);
    }

    @Override
    public List<NewTekCategory> getAllCategory() {
        return newTekCategoryMapper.getAllCategory();
    }

    /**
     * 通过ID查询产品系列信息
     *
     * @param id 系列ID
     * @return 角色对象信息
     */
    public NewTekCategory selectCategoryById(Long id) {
        return newTekCategoryMapper.getNewTekCategoryById(id);
    }


}
