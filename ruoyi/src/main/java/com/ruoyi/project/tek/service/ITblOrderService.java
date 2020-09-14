package com.ruoyi.project.tek.service;

import com.ruoyi.project.tek.domain.TblOrder;
import com.ruoyi.project.tek.vo.SalesPlanVO;
import com.ruoyi.project.tek.vo.TblOrderAndItemVO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author steven.guo
 * @since 2020-05-28
 */
public interface ITblOrderService {

    TblOrder getTblOrderById(@RequestParam Long id);

    List<TblOrderAndItemVO> getTblOrderAndItemCountBySku(List<SalesPlanVO> salesPlanVOList);

}
