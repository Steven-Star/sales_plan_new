package com.ruoyi.project.tek.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.project.tek.domain.TblGoods;
import com.ruoyi.project.tek.request.QueryCalendarReq;
import com.ruoyi.project.tek.request.QuerySalesPlanGoodsReq;
import com.ruoyi.project.tek.vo.NewTekCalendarVO;
import com.ruoyi.project.tek.vo.SalesPlanVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 商品 Mapper 接口
 * </p>
 *
 * @author steven.guo
 * @since 2020-05-28
 */
@Repository
public interface TblGoodsMapper extends BaseMapper<TblGoods> {

    List<TblGoods> queryGoodsAll();

    TblGoods getTblGoodsById(Long id);

    List<NewTekCalendarVO> queryCalendarAllData(QueryCalendarReq req);

    List<NewTekCalendarVO> queryCalendarAllDataForPermission(QueryCalendarReq req);

    List<SalesPlanVO> getSalesPlanGoodsAll(QuerySalesPlanGoodsReq req);

    List<TblGoods> getTblGoodsBySku(String sku);

}
