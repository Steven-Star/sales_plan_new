package com.ruoyi.project.tek.service;

import com.ruoyi.project.tek.domain.NewTekDispose;
import com.ruoyi.project.tek.domain.NewTekModel;
import com.ruoyi.project.tek.request.QueryDisposeReq;
import com.ruoyi.project.tek.request.QueryModelReq;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 产品配置 服务类
 * </p>
 *
 * @author steven.guo
 * @since 2020-05-28
 */
public interface INewTekDisposeService {

    int insertNewTekDispose(NewTekDispose newTekDispose);

    int updateNewTekDispose(NewTekDispose newTekDispose);

    List<NewTekDispose> queryNewTekDisposeAll(QueryDisposeReq req);

    NewTekDispose getNewTekDisposeById(Long id);

    int deleteDisposeByIds(Long[] ids);

    NewTekDispose getNewTekDisposeByCategoryAndModelAndSortId(NewTekDispose newTekDispose);

    List<NewTekDispose> getAllDispose(Long modelId);

    List<NewTekDispose> getAllDisposeByGroupId(Long disposeGroupId);


}
