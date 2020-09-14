package com.ruoyi.project.tek.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.project.tek.domain.NewTekCategory;
import com.ruoyi.project.tek.domain.NewTekDispose;
import com.ruoyi.project.tek.domain.NewTekModel;
import com.ruoyi.project.tek.request.QueryDisposeReq;
import com.ruoyi.project.tek.request.QueryModelReq;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 销售计划项目-产品配置表 Mapper 接口
 * </p>
 *
 * @author steven.guo
 * @since 2020-06-02
 */
@Repository
public interface NewTekDisposeMapper extends BaseMapper<NewTekDispose> {

    /**
     * 新增产品配置信息
     *
     * @param newTekDispose 产品系列信息
     * @return 结果
     */
    int insertNewTekDispose(NewTekDispose newTekDispose);

    /**
     * 修改产品配置信息
     *
     * @param newTekDispose 产品配置信息
     * @return 结果
     */
    int updateNewTekDispose(NewTekDispose newTekDispose);

    List<NewTekDispose> queryNewTekDisposeAll(QueryDisposeReq req);

    NewTekDispose getNewTekDisposeById(Long id);

    /**
     * 批量删除产品配置信息
     *
     * @param ids 需要删除的产品配置ID
     * @return 结果
     */
    int deleteDisposeByIds(Long[] ids);

    NewTekDispose getNewTekDisposeByCategoryAndModelAndSortId(NewTekDispose newTekDispose);

    List<NewTekDispose> getAllDispose(@Param("modelId") Long modelId);

    List<NewTekDispose> getAllDisposeByGroupId(@Param("disposeGroupId") Long disposeGroupId);

}
