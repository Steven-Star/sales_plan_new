package com.ruoyi.project.tek.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.project.tek.domain.NewTekModel;
import com.ruoyi.project.tek.request.QueryModelReq;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 销售计划项目-产品系列表 Mapper 接口
 * </p>
 *
 * @author steven.guo
 * @since 2020-06-02
 */
@Repository
public interface NewTekModelMapper extends BaseMapper<NewTekModel> {

    /**
     * 新增产品系列信息
     *
     * @param newTekModel 产品系列信息
     * @return 结果
     */
    int insertNewTekModel(NewTekModel newTekModel);

    /**
     * 修改产品系列信息
     *
     * @param newTekModel 产品系列信息
     * @return 结果
     */
    int updateNewTekModel(NewTekModel newTekModel);

    List<NewTekModel> queryNewTekModelAll(QueryModelReq req);

    NewTekModel getNewTekModelById(Long id);

    /**
     * 批量删除产品系列信息
     *
     * @param ids 需要删除的产品系列ID
     * @return 结果
     */
    int deleteModelByIds(Long[] ids);

    List<NewTekModel> getAllModel(@Param("categoryId") Long categoryId);

    List<NewTekModel> getModelByCategoryAndModelName(NewTekModel newTekModel);

}
