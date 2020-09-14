package com.ruoyi.project.tek.service;

import com.ruoyi.project.tek.domain.TblGoods;
import com.ruoyi.project.tek.request.QueryCalendarReq;
import com.ruoyi.project.tek.request.QuerySalesPlanGoodsReq;
import com.ruoyi.project.tek.vo.NewTekCalendarVO;
import com.ruoyi.project.tek.vo.SalesPlanVO;

import java.util.List;

/**
 * <p>
 * 商品 服务类
 * </p>
 *
 * @author steven.guo
 * @since 2020-05-28
 */
public interface ITblGoodsService {

    List<TblGoods> queryGoodsAll();

    List<NewTekCalendarVO> queryCalendarAllData(QueryCalendarReq req) throws Exception;

    List<NewTekCalendarVO> queryCalendarAllDataForPermission(QueryCalendarReq req)  throws Exception;

    List<SalesPlanVO> getSalesPlanGoodsAll(QuerySalesPlanGoodsReq req);




}
