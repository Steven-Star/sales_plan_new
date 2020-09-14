package com.ruoyi.project.tek.service;

import com.ruoyi.project.tek.domain.NewTekCategory;
import com.ruoyi.project.tek.domain.NewTekModel;
import com.ruoyi.project.tek.request.QueryCategoryReq;
import com.ruoyi.project.tek.request.QueryModelReq;

import java.util.List;

/**
 * <p>
 * 产品品类 服务类
 * </p>
 *
 * @author steven.guo
 * @since 2020-06-16
 */
public interface INewTekCategoryService {

    int insertNewTekCategory(NewTekCategory newTekCategory);

    int updateNewTekCategory(NewTekCategory newTekCategory);

    List<NewTekCategory> queryNewTekCategoryAll(QueryCategoryReq req);

    NewTekCategory queryNewTekCategoryByName(String categoryName);

    NewTekCategory getNewTekCategoryById(Long id);

    int deleteCategoryByIds(Long[] ids);

    List<NewTekCategory> getAllCategory();



}
