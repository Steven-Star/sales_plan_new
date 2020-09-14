package com.ruoyi.project.tek.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.project.tek.domain.NewTekCustomer;
import com.ruoyi.project.tek.domain.NewTekModel;
import com.ruoyi.project.tek.domain.TblGoods;
import com.ruoyi.project.tek.request.QueryCustomerReq;
import com.ruoyi.project.tek.request.QueryModelReq;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 销售计划项目-客户表 Mapper 接口
 * </p>
 *
 * @author steven.guo
 * @since 2020-05-28
 */
@Repository
public interface NewTekCustomerMapper extends BaseMapper<NewTekCustomer> {

    /**
     * 新增客户信息
     *
     * @param newTekCustomer 客户信息
     * @return 结果
     */
    int insertNewTekCustomer(NewTekCustomer newTekCustomer);

    /**
     * 修改客户信息
     *
     * @param newTekCustomer 客户信息
     * @return 结果
     */
    int updateNewTekCustomer(NewTekCustomer newTekCustomer);

    NewTekCustomer getNewTekCustomerById(Long id);

    List<NewTekCustomer> getNewTekCustomerByCustomerName(String customerName);

    /**
     * 批量删除客户信息
     *
     * @param ids 需要删除的客户ID
     * @return 结果
     */
    int deleteCustomerByIds(Long[] ids);

    List<NewTekCustomer> getAllCustomer(QueryCustomerReq req);

    List<NewTekCustomer> getCustomerListPageForPermission(QueryCustomerReq req);

    List<NewTekCustomer> getCustomerInfoForPermission(QueryCustomerReq req);

    List<NewTekCustomer> getAllCustomerForSelect(QueryCustomerReq req);

    List<NewTekCustomer> getAllCustomerForPermission();

}
