package com.ruoyi.project.tek.service;

import com.ruoyi.project.tek.domain.NewTekMsrp;
import com.ruoyi.project.tek.domain.NewTekProductModel;
import com.ruoyi.project.tek.domain.TblGoods;
import com.ruoyi.project.tek.request.QueryMsrpReq;
import com.ruoyi.project.tek.vo.NewTekMsrpVO;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 商品 服务类
 * </p>
 *
 * @author steven.guo
 * @since 2020-05-28
 */
public interface INewTekMsrpService {

    List<NewTekMsrpVO> queryNewTekMsrpByPage(QueryMsrpReq req);

    List<NewTekMsrpVO> queryNewTekMsrpByPageForPermission(QueryMsrpReq req);

    int insertNewTekMsrp(NewTekMsrp newTekMsrp);

    BigDecimal updateNewTekMsrpOther(NewTekMsrp newTekMsrp);

    NewTekMsrpVO getNewTekMsrpVOById(Long id);

    NewTekMsrp getNewTekMsrpById(Long id);

    List<NewTekMsrp> getNewTekMsrpByCustomerAndProductAndSku(NewTekMsrp newTekMsrp);

    int deleteMsrpByIds(Long[] ids);

    /**
     * 导入MSRP数据
     *
     * @param msrpList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    public String importMSRP(List<NewTekMsrp> msrpList, Boolean isUpdateSupport);
}
