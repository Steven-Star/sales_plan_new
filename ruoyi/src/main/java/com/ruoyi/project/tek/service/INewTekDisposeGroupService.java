package com.ruoyi.project.tek.service;

import com.ruoyi.project.tek.domain.NewTekDispose;
import com.ruoyi.project.tek.domain.NewTekDisposeGroup;
import com.ruoyi.project.tek.request.QueryDisposeReq;

import java.util.List;

/**
 * <p>
 * 产品配置组 服务类
 * </p>
 *
 * @author steven.guo
 * @since 2020-8-19
 */
public interface INewTekDisposeGroupService {

    int insertNewTekDisposeGroup(NewTekDisposeGroup newTekDisposeGroup);

    int updateNewTekDisposeGroup(NewTekDisposeGroup newTekDisposeGroup);

    List<NewTekDisposeGroup> queryNewTekDisposeGroupForPage(NewTekDisposeGroup newTekDisposeGroup);

    NewTekDisposeGroup checkDisposeGroupIsExist(NewTekDisposeGroup newTekDisposeGroup);

    NewTekDisposeGroup getNewTekDisposeGroupById(Long id);

    int deleteDisposeGroupByIds(Long[] ids);

}
