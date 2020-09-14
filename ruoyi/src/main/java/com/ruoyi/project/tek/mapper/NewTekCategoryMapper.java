package com.ruoyi.project.tek.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.project.tek.domain.NewTekCategory;
import com.ruoyi.project.tek.request.QueryCategoryReq;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 销售计划项目-产品品类表 Mapper 接口
 * </p>
 *
 * @author steven.guo
 * @since 2020-06-02
 */
@Repository
public interface NewTekCategoryMapper extends BaseMapper<NewTekCategory> {

    /**
     * 新增产品品类信息
     *
     * @param newTekCategory 产品品类信息
     * @return 结果
     */
    int insertNewTekCategory(NewTekCategory newTekCategory);

    /**
     * 修改产品品类信息
     *
     * @param newTekCategory 产品品类信息
     * @return 结果
     */
    int updateNewTekCategory(NewTekCategory newTekCategory);

    List<NewTekCategory> queryNewTekCategoryAll(QueryCategoryReq req);

    NewTekCategory queryNewTekCategoryByName(String categoryName);

    NewTekCategory getNewTekCategoryById(Long id);

    /**
     * 批量删除产品品类信息
     *
     * @param ids 需要删除的产品系列ID
     * @return 结果
     */
    int deleteCategoryByIds(Long[] ids);

    List<NewTekCategory> getAllCategory();

}
