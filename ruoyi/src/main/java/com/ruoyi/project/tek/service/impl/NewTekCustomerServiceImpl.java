package com.ruoyi.project.tek.service.impl;

import com.ruoyi.common.exception.CustomException;
import com.ruoyi.project.tek.domain.NewTekCountryCode;
import com.ruoyi.project.tek.domain.NewTekCustomer;
import com.ruoyi.project.tek.domain.NewTekMsrp;
import com.ruoyi.project.tek.mapper.NewTekCountryCodeMapper;
import com.ruoyi.project.tek.mapper.NewTekCustomerMapper;
import com.ruoyi.project.tek.mapper.NewTekMsrpMapper;
import com.ruoyi.project.tek.request.QueryCustomerReq;
import com.ruoyi.project.tek.service.INewTekCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 商品 服务实现类
 * </p>
 *
 * @author steven.guo
 * @since 2020-05-28
 */
@Service
public class NewTekCustomerServiceImpl implements INewTekCustomerService {

    @Autowired
    private NewTekCustomerMapper newTekCustomerMapper;
    @Autowired
    private NewTekMsrpMapper newTekMsrpMapper;
    @Autowired
    private NewTekCountryCodeMapper newTekCountryCodeMapper;

    @Override
    public List<NewTekCustomer> getAllCustomer(QueryCustomerReq req) {
        return newTekCustomerMapper.getAllCustomer(req);
    }

    @Override
    public List<NewTekCustomer> getCustomerListPageForPermission(QueryCustomerReq req) {
        return newTekCustomerMapper.getCustomerListPageForPermission(req);
    }

    @Override
    public List<NewTekCustomer> getCustomerInfoForPermission(QueryCustomerReq req) {
        return newTekCustomerMapper.getCustomerInfoForPermission(req);
    }

    @Override
    @Transactional
    public int insertNewTekCustomer(NewTekCustomer newTekCustomer) {
        String country = newTekCustomer.getCountry();
        String code = country.split("_")[0];
        NewTekCountryCode newTekCountryCode = newTekCountryCodeMapper.getCountryCodeByCode(code);
        if (newTekCountryCode != null) {
            newTekCustomer.setCountryId(newTekCountryCode.getId());
        }
        return newTekCustomerMapper.insertNewTekCustomer(newTekCustomer);
    }

    @Override
    @Transactional
    public int updateNewTekCustomer(NewTekCustomer newTekCustomer) {
        return newTekCustomerMapper.updateNewTekCustomer(newTekCustomer);
    }

    @Override
    public NewTekCustomer getNewTekCustomerById(Long id) {
        return newTekCustomerMapper.getNewTekCustomerById(id);
    }

    @Override
    public List<NewTekCustomer> getNewTekCustomerByCustomerName(String customerName) {
        return newTekCustomerMapper.getNewTekCustomerByCustomerName(customerName);
    }

    @Override
    @Transactional
    public int deleteCustomerByIds(Long[] ids) {
        {
            for (Long id : ids) {
                NewTekCustomer newTekCustomer = newTekCustomerMapper.getNewTekCustomerById(id);
                if (countMsrpByCustomerId(id) > 0) {
                    throw new CustomException(String.format("%1$s已分配,不能删除", newTekCustomer.getCustomerName()));
                }
            }
        }

        return newTekCustomerMapper.deleteCustomerByIds(ids);
    }

    @Override
    public List<NewTekCustomer> getAllCustomerForSelect(QueryCustomerReq req) {
        return newTekCustomerMapper.getAllCustomerForSelect(req);
    }

    @Override
    public List<NewTekCustomer> getAllCustomerForPermission() {
        return newTekCustomerMapper.getAllCustomerForPermission();
    }


    public Integer countMsrpByCustomerId(Long customerId) {
        NewTekMsrp newTekMsrp = new NewTekMsrp();
        newTekMsrp.setCustomerId(customerId);
        List<NewTekMsrp> newTekMsrpList = newTekMsrpMapper.getNewTekMsrpByCustomerAndProductAndSku(newTekMsrp);
        if (newTekMsrpList.size() > 0) {
            return newTekMsrpList.size();
        } else {
            return 0;
        }
    }


}
