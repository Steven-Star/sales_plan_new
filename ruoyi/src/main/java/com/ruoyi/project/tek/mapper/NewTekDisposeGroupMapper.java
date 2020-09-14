package com.ruoyi.project.tek.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.project.tek.domain.NewTekDispose;
import com.ruoyi.project.tek.domain.NewTekDisposeGroup;
import com.ruoyi.project.tek.request.QueryDisposeReq;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 销售计划项目-产品配置组表 Mapper 接口
 * </p>
 *
 * @author steven.guo
 * @since 2020-06-02
 */
@Repository
public interface NewTekDisposeGroupMapper extends BaseMapper<NewTekDisposeGroup> {

    /**
     * 新增产品配置组信息
     *
     * @param newTekDisposeGroup 配置组
     * @return 结果
     */
    int insertNewTekDisposeGroup(NewTekDisposeGroup newTekDisposeGroup);

    /**
     * 修改产品配置信息
     *
     * @param newTekDisposeGroup 产品配置信息
     * @return 结果
     */
    int updateNewTekDisposeGroup(NewTekDisposeGroup newTekDisposeGroup);


    NewTekDisposeGroup getNewTekDisposeGroupById(Long id);

    /**
     * 批量删除产品配置信息
     *
     * @param ids 需要删除的产品配置ID
     * @return 结果
     */
    int deleteDisposeGroupByIds(Long[] ids);

    NewTekDispose getNewTekDisposeByCategoryAndModelAndSortId(NewTekDispose newTekDispose);

    List<NewTekDisposeGroup> queryNewTekDisposeGroupForPage(NewTekDisposeGroup newTekDisposeGroup);

    NewTekDisposeGroup checkDisposeGroupIsExist(NewTekDisposeGroup newTekDisposeGroup);
}
