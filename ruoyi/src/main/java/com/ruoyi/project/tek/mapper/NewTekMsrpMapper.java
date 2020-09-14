package com.ruoyi.project.tek.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.project.tek.domain.NewTekMsrp;
import com.ruoyi.project.tek.domain.TblGoods;
import com.ruoyi.project.tek.request.QueryMsrpReq;
import com.ruoyi.project.tek.vo.NewTekMsrpVO;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 销售计划项目-建议零售价表 Mapper 接口
 * </p>
 *
 * @author steven.guo
 * @since 2020-06-02
 */
@Repository
public interface NewTekMsrpMapper extends BaseMapper<NewTekMsrp> {

    List<NewTekMsrpVO> queryNewTekMsrpByPage(QueryMsrpReq req);

    List<NewTekMsrpVO> queryNewTekMsrpByPageForPermission(QueryMsrpReq req);

    int insertNewTekMsrp(NewTekMsrp newTekMsrp);

    int updateNewTekMsrpOther(NewTekMsrp newTekMsrp);

    NewTekMsrpVO getNewTekMsrpVOById(Long id);

    NewTekMsrp getNewTekMsrpById(Long id);

//    NewTekMsrp getNewTekMsrpByModelId(Long modelId);

    List<NewTekMsrp> getNewTekMsrpByCustomerAndProductAndSku(NewTekMsrp newTekMsrp);

    int deleteMsrpByIds(Long[] ids);

}
