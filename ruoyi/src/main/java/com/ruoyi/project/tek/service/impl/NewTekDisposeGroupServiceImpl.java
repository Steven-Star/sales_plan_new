package com.ruoyi.project.tek.service.impl;

import com.ruoyi.project.tek.domain.NewTekCategory;
import com.ruoyi.project.tek.domain.NewTekDispose;
import com.ruoyi.project.tek.domain.NewTekDisposeGroup;
import com.ruoyi.project.tek.domain.NewTekModel;
import com.ruoyi.project.tek.mapper.NewTekCategoryMapper;
import com.ruoyi.project.tek.mapper.NewTekDisposeGroupMapper;
import com.ruoyi.project.tek.mapper.NewTekDisposeMapper;
import com.ruoyi.project.tek.mapper.NewTekModelMapper;
import com.ruoyi.project.tek.request.QueryDisposeReq;
import com.ruoyi.project.tek.service.INewTekDisposeGroupService;
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
 * @date 2020/8/19 11:07
 */
@Service
public class NewTekDisposeGroupServiceImpl implements INewTekDisposeGroupService {

    private static final Logger log = LoggerFactory.getLogger(NewTekDisposeGroupServiceImpl.class);

    @Autowired
    private NewTekDisposeGroupMapper newTekDisposeGroupMapper;


    @Override
    public int insertNewTekDisposeGroup(NewTekDisposeGroup newTekDisposeGroup) {
        return newTekDisposeGroupMapper.insertNewTekDisposeGroup(newTekDisposeGroup);
    }

    @Override
    public int updateNewTekDisposeGroup(NewTekDisposeGroup newTekDisposeGroup) {
        return newTekDisposeGroupMapper.updateNewTekDisposeGroup(newTekDisposeGroup);
    }

    @Override
    public List<NewTekDisposeGroup> queryNewTekDisposeGroupForPage(NewTekDisposeGroup newTekDisposeGroup) {
        return newTekDisposeGroupMapper.queryNewTekDisposeGroupForPage(newTekDisposeGroup);
    }

    @Override
    public NewTekDisposeGroup checkDisposeGroupIsExist(NewTekDisposeGroup newTekDisposeGroup) {
        return newTekDisposeGroupMapper.checkDisposeGroupIsExist(newTekDisposeGroup);
    }

    @Override
    public NewTekDisposeGroup getNewTekDisposeGroupById(Long id) {
        return newTekDisposeGroupMapper.getNewTekDisposeGroupById(id);
    }

    @Override
    public int deleteDisposeGroupByIds(Long[] ids) {
        return newTekDisposeGroupMapper.deleteDisposeGroupByIds(ids);
    }
}
