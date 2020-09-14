package com.ruoyi.project.tek.service;

import com.ruoyi.project.tek.domain.NewTekMsrp;
import com.ruoyi.project.tek.domain.NewTekMsrpWk;
import com.ruoyi.project.tek.domain.NewTekProductModel;
import com.ruoyi.project.tek.request.QueryMsrpReq;
import com.ruoyi.project.tek.request.QueryMsrpWKReq;
import com.ruoyi.project.tek.vo.NewTekMsrpVO;
import com.ruoyi.project.tek.vo.NewTekMsrpWKVO;
import com.ruoyi.project.tek.vo.NewTekPromotionListVO;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 周建议零售价 服务类
 * </p>
 *
 * @author steven.guo
 * @since 2020-05-28
 */
public interface INewTekMsrpWkService {

    int insertNewTekMsrpWk(NewTekMsrpWk newTekMsrpWk);

    int updateNewTekMsrpWk(NewTekMsrpWk newTekMsrpWk);

    List<NewTekMsrpWk> getMsrpWkByCustomerIdAndSku(NewTekMsrpWk newTekMsrpWk);

    List<NewTekMsrpWk> checkMsrpWkByCustomerAndSkuAndCountryAndFromAndTo(NewTekMsrpWk newTekMsrpWk);

    List<NewTekMsrpWKVO> queryNewTekMsrpWKByPage(QueryMsrpWKReq req);

    List<NewTekMsrpWKVO> queryNewTekMsrpWKByPageForPermission(QueryMsrpWKReq req);

    NewTekMsrpWKVO getNewTekMsrpWKById(Long id);

    int deleteMsrpWKByIds(Long[] ids);

    List<NewTekPromotionListVO> queryPromotionListByPage(QueryMsrpWKReq req);

    List<NewTekPromotionListVO> queryPromotionListByPageForPermission(QueryMsrpWKReq req);

    List<NewTekMsrpWk> queryPromotionCalendarList(NewTekMsrpWk newTekMsrpWk);

    List<NewTekMsrpWk> queryPromotionCalendarListForPermission(NewTekMsrpWk newTekMsrpWk);

    List<NewTekMsrpWk> queryByDateAndPromotionType(QueryMsrpWKReq req) throws Exception;

    List<NewTekMsrpWk> queryByDateAndPromotionTypeForPermission(QueryMsrpWKReq req) throws Exception;

    /**
     * 导入Selling Price数据
     *
     * @param sellingPriceList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    String importSellingPrice(List<NewTekMsrpWk> sellingPriceList, Boolean isUpdateSupport);

    List<NewTekMsrpWk> queryMsrpWKCountryList();

}
