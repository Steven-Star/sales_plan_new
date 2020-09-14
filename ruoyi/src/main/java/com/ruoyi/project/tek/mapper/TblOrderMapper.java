package com.ruoyi.project.tek.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.project.tek.domain.TblOrder;
import com.ruoyi.project.tek.vo.SalesPlanVO;
import com.ruoyi.project.tek.vo.TblOrderAndItemVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author steven.guo
 * @since 2020-05-28
 */
@Repository
public interface TblOrderMapper extends BaseMapper<TblOrder> {

    List<TblOrderAndItemVO> getTblOrderAndItemCountBySku(List<SalesPlanVO> salesPlanVOList);

}
