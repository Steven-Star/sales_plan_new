package com.ruoyi.project.tek.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.project.tek.domain.TblOrder;
import com.ruoyi.project.tek.mapper.TblOrderMapper;
import com.ruoyi.project.tek.service.ITblOrderService;
import com.ruoyi.project.tek.vo.SalesPlanVO;
import com.ruoyi.project.tek.vo.TblOrderAndItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author steven.guo
 * @since 2020-05-28
 */
@Service
public class TblOrderServiceImpl implements ITblOrderService {

    @Autowired
    private TblOrderMapper tblOrderMapper;


    @Override
    public TblOrder getTblOrderById(Long id) {
        TblOrder tblOrder = tblOrderMapper.selectById(id);
        return tblOrder;
    }

    @Override
    public List<TblOrderAndItemVO> getTblOrderAndItemCountBySku(List<SalesPlanVO> salesPlanVOList) {
        return tblOrderMapper.getTblOrderAndItemCountBySku(salesPlanVOList);
    }
}
