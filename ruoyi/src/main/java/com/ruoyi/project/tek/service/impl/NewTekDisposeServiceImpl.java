package com.ruoyi.project.tek.service.impl;

import com.ruoyi.project.tek.domain.NewTekDispose;
import com.ruoyi.project.tek.domain.NewTekDisposeGroup;
import com.ruoyi.project.tek.mapper.NewTekDisposeGroupMapper;
import com.ruoyi.project.tek.mapper.NewTekDisposeMapper;
import com.ruoyi.project.tek.request.QueryDisposeReq;
import com.ruoyi.project.tek.service.INewTekDisposeService;
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
 * @date 2020/6/16 16:15
 */
@Service
public class NewTekDisposeServiceImpl implements INewTekDisposeService {

    private static final Logger log = LoggerFactory.getLogger(NewTekDisposeServiceImpl.class);

    @Autowired
    private NewTekDisposeMapper newTekDisposeMapper;

    @Autowired
    private NewTekDisposeGroupMapper newTekDisposeGroupMapper;

    @Override
    public int insertNewTekDispose(NewTekDispose newTekDispose) {
        int rows = newTekDisposeMapper.insertNewTekDispose(newTekDispose);
        return rows;
    }

    @Override
    @Transactional
    public int updateNewTekDispose(NewTekDispose newTekDispose) {
        return newTekDisposeMapper.updateNewTekDispose(newTekDispose);
    }

    @Override
    public List<NewTekDispose> queryNewTekDisposeAll(QueryDisposeReq req) {
        List<NewTekDispose> newTekDisposeList = newTekDisposeMapper.queryNewTekDisposeAll(req);
        for (NewTekDispose newTekDispose : newTekDisposeList) {
            NewTekDisposeGroup newTekDisposeGroup = newTekDisposeGroupMapper.getNewTekDisposeGroupById(newTekDispose.getDisposeGroupId());
            newTekDispose.setDisposeGroupName(newTekDisposeGroup.getDisposeGroupName());
        }
        return newTekDisposeList;
    }

    @Override
    public NewTekDispose getNewTekDisposeById(Long id) {
        return newTekDisposeMapper.getNewTekDisposeById(id);
    }

    /**
     * 批量删除产品系列信息
     *
     * @param ids 需要删除的产品系列ID
     * @return 结果
     */
    public int deleteDisposeByIds(Long[] ids) {
        return newTekDisposeMapper.deleteDisposeByIds(ids);
    }

    @Override
    public NewTekDispose getNewTekDisposeByCategoryAndModelAndSortId(NewTekDispose newTekDispose) {
        return newTekDisposeMapper.getNewTekDisposeByCategoryAndModelAndSortId(newTekDispose);
    }

    @Override
    public List<NewTekDispose> getAllDispose(Long modelId) {
        return newTekDisposeMapper.getAllDispose(modelId);
    }

    @Override
    public List<NewTekDispose> getAllDisposeByGroupId(Long disposeGroupId) {
        return newTekDisposeMapper.getAllDisposeByGroupId(disposeGroupId);
    }


    /**
     * 通过ID查询产品系列信息
     *
     * @param id 系列ID
     * @return 角色对象信息
     */
    public NewTekDispose selectDisposeById(Long id) {
        return newTekDisposeMapper.getNewTekDisposeById(id);
    }

}
