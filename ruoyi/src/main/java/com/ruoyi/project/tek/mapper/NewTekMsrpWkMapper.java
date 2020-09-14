package com.ruoyi.project.tek.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.project.tek.domain.NewTekMsrp;
import com.ruoyi.project.tek.domain.NewTekMsrpWk;
import com.ruoyi.project.tek.request.QueryMsrpReq;
import com.ruoyi.project.tek.request.QueryMsrpWKReq;
import com.ruoyi.project.tek.vo.NewTekMsrpVO;
import com.ruoyi.project.tek.vo.NewTekMsrpWKVO;
import com.ruoyi.project.tek.vo.NewTekPromotionListVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 销售计划项目-周建议零售价表 Mapper 接口
 * </p>
 *
 * @author steven.guo
 * @since 2020-06-02
 */
@Repository
public interface NewTekMsrpWkMapper extends BaseMapper<NewTekMsrpWk> {

    List<NewTekMsrpWKVO> queryNewTekMsrpWKByPage(QueryMsrpWKReq req);

    List<NewTekMsrpWKVO> queryNewTekMsrpWKByPageForPermission(QueryMsrpWKReq req);

    List<NewTekPromotionListVO> queryPromotionListByPage(QueryMsrpWKReq req);

    List<NewTekPromotionListVO> queryPromotionListByPageForPermission(QueryMsrpWKReq req);

    int insertNewTekMsrpWk(NewTekMsrpWk newTekMsrpWk);

    List<NewTekMsrpWk> getMsrpWkByCustomerIdAndSku(NewTekMsrpWk newTekMsrpWk);

    List<NewTekMsrpWk> checkMsrpWkByCustomerAndSkuAndCountryAndFromAndTo(NewTekMsrpWk newTekMsrpWk);

    int updateNewTekMsrpWk(NewTekMsrpWk newTekMsrpWk);

    NewTekMsrpWKVO getNewTekMsrpWKById(Long id);

    int getCalendarCount();

    int deleteMsrpWKByIds(Long[] ids);

    List<NewTekMsrpWk> queryPromotionCalendarList(NewTekMsrpWk newTekMsrpWk);

    List<NewTekMsrpWk> queryPromotionCalendarListForPermission(NewTekMsrpWk newTekMsrpWk);

    List<NewTekMsrpWk> queryByDateAndPromotionType(QueryMsrpWKReq req);

    List<NewTekMsrpWk> queryByDateAndPromotionTypeForPermission(QueryMsrpWKReq req);

    List<NewTekMsrpWk> queryMsrpWKByPromotionType(String promotionType);

    List<NewTekMsrpWk> queryMsrpWKCountryList();

}
