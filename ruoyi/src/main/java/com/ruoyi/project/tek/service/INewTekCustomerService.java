package com.ruoyi.project.tek.service;

import com.ruoyi.project.tek.domain.NewTekCustomer;
import com.ruoyi.project.tek.request.QueryCustomerReq;

import java.util.List;

/**
 * <p>
 * 商品 服务类
 * </p>
 *
 * @author steven.guo
 * @since 2020-05-28
 */
public interface INewTekCustomerService {

    List<NewTekCustomer> getAllCustomer(QueryCustomerReq req);

    List<NewTekCustomer> getCustomerListPageForPermission(QueryCustomerReq req);

    List<NewTekCustomer> getCustomerInfoForPermission(QueryCustomerReq req);

    int insertNewTekCustomer(NewTekCustomer newTekCustomer);

    int updateNewTekCustomer(NewTekCustomer newTekCustomer);

    NewTekCustomer getNewTekCustomerById(Long id);

    List<NewTekCustomer> getNewTekCustomerByCustomerName(String customerName);

    /**
     * 批量删除客户信息
     *
     * @param ids 需要删除的客户ID数组
     * @return 结果
     */
    int deleteCustomerByIds(Long[] ids);

    List<NewTekCustomer> getAllCustomerForSelect(QueryCustomerReq req);

    List<NewTekCustomer> getAllCustomerForPermission();

}
