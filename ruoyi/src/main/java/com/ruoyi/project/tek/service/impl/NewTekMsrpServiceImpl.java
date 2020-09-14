package com.ruoyi.project.tek.service.impl;

import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.exception.SKUException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.project.tek.domain.NewTekCountryCode;
import com.ruoyi.project.tek.domain.NewTekCurrencyCode;
import com.ruoyi.project.tek.domain.NewTekDispose;
import com.ruoyi.project.tek.domain.NewTekModel;
import com.ruoyi.project.tek.domain.NewTekMsrp;
import com.ruoyi.project.tek.domain.NewTekProductModel;
import com.ruoyi.project.tek.mapper.NewTekCountryCodeMapper;
import com.ruoyi.project.tek.mapper.NewTekCurrencyCodeMapper;
import com.ruoyi.project.tek.mapper.NewTekDisposeMapper;
import com.ruoyi.project.tek.mapper.NewTekModelMapper;
import com.ruoyi.project.tek.mapper.NewTekMsrpMapper;
import com.ruoyi.project.tek.mapper.NewTekProductModelMapper;
import com.ruoyi.project.tek.request.QueryMsrpReq;
import com.ruoyi.project.tek.service.INewTekMsrpService;
import com.ruoyi.project.tek.vo.NewTekMsrpVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author steven.guo
 * @version 1.0
 * @description:
 * @date 2020/6/3 10:50
 */
@Service
public class NewTekMsrpServiceImpl implements INewTekMsrpService {

    private static final Logger log = LoggerFactory.getLogger(NewTekMsrpServiceImpl.class);

    @Autowired
    private NewTekMsrpMapper newTekMsrpMapper;
    @Autowired
    private NewTekCurrencyCodeMapper newTekCurrencyCodeMapper;
    @Autowired
    private NewTekCountryCodeMapper newTekCountryCodeMapper;
    @Autowired
    private NewTekProductModelMapper newTekProductModelMapper;
    @Autowired
    private NewTekModelMapper newTekModelMapper;
    @Autowired
    private NewTekDisposeMapper newTekDisposeMapper;

    @Override
    public List<NewTekMsrpVO> queryNewTekMsrpByPage(QueryMsrpReq req) {
        List<NewTekMsrpVO> newTekMsrpVOList = null;
        try {
            if (req.getValidFrom() != null) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//注意月份是MM
                req.setValidFromDate(simpleDateFormat.parse(req.getValidFrom()));
            }
            if (req.getValidTo() != null) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//注意月份是MM
                req.setValidToDate(simpleDateFormat.parse(req.getValidTo()));
            }
            newTekMsrpVOList = newTekMsrpMapper.queryNewTekMsrpByPage(req);
            for (NewTekMsrpVO newTekMsrpVO : newTekMsrpVOList) {
                String sku = newTekMsrpVO.getSku();
                List<NewTekProductModel> productModelList = newTekProductModelMapper.getProductModelBySKU(sku);
                if (productModelList.size() > 0 && productModelList != null) {
                    Long modelId = productModelList.get(0).getModelId();
                    Long disposeId = productModelList.get(0).getDisposeId();
                    NewTekModel newTekModel = newTekModelMapper.getNewTekModelById(modelId);
                    NewTekDispose newTekDispose = newTekDisposeMapper.getNewTekDisposeById(disposeId);
                    newTekMsrpVO.setModelInfo(newTekModel.getModelName() + "-" + newTekDispose.getDisposeName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newTekMsrpVOList;
    }

    @Override
    public List<NewTekMsrpVO> queryNewTekMsrpByPageForPermission(QueryMsrpReq req) {
        List<NewTekMsrpVO> newTekMsrpVOList = null;
        try {
            if (req.getValidFrom() != null) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//注意月份是MM
                req.setValidFromDate(simpleDateFormat.parse(req.getValidFrom()));
            }
            if (req.getValidTo() != null) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//注意月份是MM
                req.setValidToDate(simpleDateFormat.parse(req.getValidTo()));
            }
            newTekMsrpVOList = newTekMsrpMapper.queryNewTekMsrpByPageForPermission(req);
            for (NewTekMsrpVO newTekMsrpVO : newTekMsrpVOList) {
                String sku = newTekMsrpVO.getSku();
                List<NewTekProductModel> productModelList = newTekProductModelMapper.getProductModelBySKU(sku);
                if (productModelList.size() > 0 && productModelList != null) {
                    Long modelId = productModelList.get(0).getModelId();
                    Long disposeId = productModelList.get(0).getDisposeId();
                    NewTekModel newTekModel = newTekModelMapper.getNewTekModelById(modelId);
                    NewTekDispose newTekDispose = newTekDisposeMapper.getNewTekDisposeById(disposeId);
                    newTekMsrpVO.setModelInfo(newTekModel.getModelName() + "-" + newTekDispose.getDisposeName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newTekMsrpVOList;
    }

    @Override
    @Transactional
    public int insertNewTekMsrp(NewTekMsrp newTekMsrp) {
//        BigDecimal msrp = newTekMsrp.getMsrp();
//        if(msrp != null){
//            BigDecimal msrp15 = msrp.multiply(new BigDecimal(0.85)).setScale(2,BigDecimal.ROUND_HALF_UP);
//            BigDecimal msrp20 = msrp.multiply(new BigDecimal(0.80)).setScale(2,BigDecimal.ROUND_HALF_UP);
//            BigDecimal msrp25 = msrp.multiply(new BigDecimal(0.75)).setScale(2,BigDecimal.ROUND_HALF_UP);
//            BigDecimal msrp30 = msrp.multiply(new BigDecimal(0.70)).setScale(2,BigDecimal.ROUND_HALF_UP);
//            newTekMsrp.setMsrp_15(msrp15);
//            newTekMsrp.setMsrp_20(msrp20);
//            newTekMsrp.setMsrp_25(msrp25);
//            newTekMsrp.setMsrp_30(msrp30);
//        }
        String countryName = newTekMsrp.getCountry().split("_")[0];
        NewTekCurrencyCode newTekCurrencyCode = newTekCurrencyCodeMapper.getCurrencyCodeByCountry(countryName);
        if (newTekCurrencyCode != null) {
            newTekMsrp.setCurrencyType(newTekCurrencyCode.getCurrencyCode());
        }
        return newTekMsrpMapper.insertNewTekMsrp(newTekMsrp);
    }

    @Override
    @Transactional
    public BigDecimal updateNewTekMsrpOther(NewTekMsrp newTekMsrp) {
        NewTekMsrp newTekMsrp2 = new NewTekMsrp();
        newTekMsrp2.setCountry(newTekMsrp.getCountry());
        newTekMsrp2.setSku(newTekMsrp.getSku());
        List<NewTekMsrp> newTekMsrpList = newTekMsrpMapper.getNewTekMsrpByCustomerAndProductAndSku(newTekMsrp2);
        if (newTekMsrpList.size() > 0 && newTekMsrpList != null) {
            for (NewTekMsrp newTekMsrp3 : newTekMsrpList) {
                if ((newTekMsrp.getValidFrom().compareTo(newTekMsrp3.getValidFrom()) > 0 && newTekMsrp.getValidTo().compareTo(newTekMsrp3.getValidTo()) <= 0 && newTekMsrp.getValidTo().compareTo(newTekMsrp3.getValidFrom()) >= 0) || (newTekMsrp.getValidFrom().compareTo(newTekMsrp3.getValidFrom()) < 0 && newTekMsrp.getValidTo().compareTo(newTekMsrp3.getValidFrom()) >= 0)) {
                    throw new CustomException("一个SKU在同一个国家同一个有效期内只能有一个价格！");
                }
            }
        }
        newTekMsrpMapper.updateNewTekMsrpOther(newTekMsrp);
        return BigDecimal.ZERO;
    }

    @Override
    public NewTekMsrpVO getNewTekMsrpVOById(Long id) {
        return newTekMsrpMapper.getNewTekMsrpVOById(id);
    }

    @Override
    public NewTekMsrp getNewTekMsrpById(Long id) {
        return newTekMsrpMapper.getNewTekMsrpById(id);
    }


    @Override
    public List<NewTekMsrp> getNewTekMsrpByCustomerAndProductAndSku(NewTekMsrp newTekMsrp) {
        return newTekMsrpMapper.getNewTekMsrpByCustomerAndProductAndSku(newTekMsrp);
    }

    @Override
    public int deleteMsrpByIds(Long[] ids) {
//        for (Long id : ids)
//        {
//            NewTekModel newTekModel = selectModelById(id);
//            if (countProductModelByModelId(id) > 0)
//            {
//                throw new CustomException(String.format("%1$s已分配,不能删除", newTekModel.getModelName()));
//            }
//        }
        return newTekMsrpMapper.deleteMsrpByIds(ids);
    }

    @Override
    public String importMSRP(List<NewTekMsrp> msrpList, Boolean isUpdateSupport) {
        if (StringUtils.isNull(msrpList) || msrpList.size() == 0) {
            throw new SKUException("导入MSRP数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (NewTekMsrp newTekMsrp : msrpList) {
            try {
                String sku = newTekMsrp.getSku();
                if (sku == null || sku == "") {
                    throw new SKUException("SKU不能为空");
                }
                String country = newTekMsrp.getCountry();


                if (country == null || country == "") {
                    throw new SKUException("Country不能为空");
                }
                List<NewTekCountryCode> countryCodeList = newTekCountryCodeMapper.queryNewTekCountryCodeAll(null);
                List<String> countryCodeList1 = new ArrayList<>();
                for (NewTekCountryCode newTekCountryCode : countryCodeList) {
                    countryCodeList1.add(newTekCountryCode.getCode());
                }
                if (!countryCodeList1.contains(country)) {
                    throw new SKUException("请输入正确的Country");
                }

                Date validFrom = newTekMsrp.getValidFrom();
                if (validFrom == null) {
                    throw new SKUException("ValidFrom不能为空");
                }
                Date validTo = newTekMsrp.getValidTo();
                if (validTo == null) {
                    throw new SKUException("ValidTo不能为空");
                }
                BigDecimal msrp = newTekMsrp.getMsrp();
                if (msrp == null) {
                    throw new SKUException("MSRP不能为空");
                }
                String countryFinal = "";
                NewTekCountryCode newTekCountryCode = newTekCountryCodeMapper.getCountryCodeByCode(country);
                countryFinal += newTekCountryCode.getCode() + "_" + newTekCountryCode.getChineseName() + "_" + newTekCountryCode.getEnglishName();

                // 验证是否存在这个sku对应关系
                NewTekMsrp newTekMsrp1 = new NewTekMsrp();
                newTekMsrp1.setCountry(countryFinal);
                newTekMsrp1.setSku(newTekMsrp.getSku());
                List<NewTekMsrp> newTekMsrpList = newTekMsrpMapper.getNewTekMsrpByCustomerAndProductAndSku(newTekMsrp1);
                if (newTekMsrpList.size() == 0 || newTekMsrpList == null) {
                    newTekMsrp.setCountry(countryFinal);
                    this.insertNewTekMsrp(newTekMsrp);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、SKU " + newTekMsrp.getSku() + " 的MSRP导入成功");

//                    if(categoryList.size() > 0 && categoryList != null){
//                        NewTekModel newTekModel = new NewTekModel();
//                        newTekModel.setModelName(productModel.getModelName());
//                        newTekModel.setCategoryId(categoryList.get(0).getId());
//                        List<NewTekModel> newTekModelList = newTekModelMapper.getModelByCategoryAndModelName(newTekModel);
//                        if(newTekModelList.size() > 0 && newTekModelList != null){
//                            NewTekDispose newTekDispose = new NewTekDispose();
//                            newTekDispose.setCategoryId(categoryList.get(0).getId());
//                            newTekDispose.setModelId(newTekModelList.get(0).getId());
//                            newTekDispose.setDisposeName(productModel.getDisposeName());
//                            NewTekDispose newTekDispose1 = newTekDisposeMapper.getNewTekDisposeByCategoryAndModelAndSortId(newTekDispose);
//                            if(newTekDispose1 != null ){
//                                productModel.setCategoryId(categoryList.get(0).getId());
//                                productModel.setModelId(newTekModelList.get(0).getId());
//                                productModel.setDisposeId(newTekDispose1.getId());
//
//                                this.insertNewTekMsrp(newTekMsrp);
//                                successNum++;
//                                successMsg.append("<br/>" + successNum + "、SKU " + newTekMsrp.getSku() + " 的MSRP导入成功");
//                            } else {
//                                failureNum++;
//                                failureMsg.append("<br/>" + failureNum + "、SKU " + newTekMsrp.getSku() + "的MSRP导入失败！");
//                            }
//                        } else {
//                            failureNum++;
//                            failureMsg.append("<br/>" + failureNum + "、SKU " + newTekMsrp.getSku() + "的MSRP导入失败！");
//                        }
//                    } else {
//                        failureNum++;
//                        failureMsg.append("<br/>" + failureNum + "、SKU " + newTekMsrp.getSku() + "的MSRP导入失败！");
//                    }
                } else if (isUpdateSupport) {
                    // TODO: 2020/7/16
//                    user.setUpdateBy(operName);
//                    this.UPDAT
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、MSRP " + newTekMsrp.getSku() + " 更新成功");
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、MSRP " + newTekMsrp.getSku() + " 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、MSRP " + newTekMsrp.getSku() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new CustomException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }
}
