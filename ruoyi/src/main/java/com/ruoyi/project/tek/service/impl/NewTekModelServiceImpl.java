package com.ruoyi.project.tek.service.impl;

import com.ruoyi.project.tek.domain.NewTekCategory;
import com.ruoyi.project.tek.domain.NewTekModel;
import com.ruoyi.project.tek.domain.NewTekProductModel;
import com.ruoyi.project.tek.mapper.NewTekCategoryMapper;
import com.ruoyi.project.tek.mapper.NewTekModelMapper;
import com.ruoyi.project.tek.mapper.NewTekProductModelMapper;
import com.ruoyi.project.tek.request.QueryModelReq;
import com.ruoyi.project.tek.service.INewTekModelService;
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
 * @date 2020/6/3 10:25
 */
@Service
public class NewTekModelServiceImpl implements INewTekModelService {

    private static final Logger log = LoggerFactory.getLogger(NewTekModelServiceImpl.class);

    @Autowired
    private NewTekModelMapper newTekModelMapper;

    @Autowired
    private NewTekProductModelMapper newTekProductModelMapper;

    @Autowired
    private NewTekCategoryMapper newTekCategoryMapper;

    @Override
    public int insertNewTekModel(NewTekModel newTekModel) {
        int rows = newTekModelMapper.insertNewTekModel(newTekModel);
        return rows;
    }

    @Override
    @Transactional
    public int updateNewTekModel(NewTekModel newTekModel) {
        return newTekModelMapper.updateNewTekModel(newTekModel);
    }

    @Override
    public List<NewTekModel> queryNewTekModelAll(QueryModelReq req) {
        List<NewTekModel> newTekModelList = newTekModelMapper.queryNewTekModelAll(req);
        for (NewTekModel newTekModel : newTekModelList) {
            NewTekCategory newTekCategory = newTekCategoryMapper.getNewTekCategoryById(newTekModel.getCategoryId());
            newTekModel.setCategoryName(newTekCategory.getCategoryName());
        }
        return newTekModelList;
    }

    @Override
    public NewTekModel getNewTekModelById(Long id) {
        return newTekModelMapper.getNewTekModelById(id);
    }

    /**
     * 批量删除产品系列信息
     *
     * @param ids 需要删除的产品系列ID
     * @return 结果
     */
    public int deleteModelByIds(Long[] ids) {
        return newTekModelMapper.deleteModelByIds(ids);
    }

    @Override
    public List<NewTekModel> getAllModel(Long categoryId) {
        return newTekModelMapper.getAllModel(categoryId);
    }

    /**
     * 删除model时候，判断msrp和productModel是不是已经存在，如果已经存在，就不给删
     */
    @Override
    public Boolean checkModelIsExist(Long[] ids) {
        for (int i = 0; i < ids.length; i++) {
            Long id = ids[i];
            List<NewTekProductModel> newTekProductModelList = newTekProductModelMapper.getNewTekProductModelByModelId(id);


        }
        return null;
    }

    @Override
    public List<NewTekModel> getModelByCategoryAndModelName(NewTekModel newTekModel) {
        return newTekModelMapper.getModelByCategoryAndModelName(newTekModel);
    }

    /**
     * 通过ID查询产品系列信息
     *
     * @param id 系列ID
     * @return 角色对象信息
     */
    public NewTekModel selectModelById(Long id) {
        return newTekModelMapper.getNewTekModelById(id);
    }

}
