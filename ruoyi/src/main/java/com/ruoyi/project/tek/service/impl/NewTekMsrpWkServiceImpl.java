package com.ruoyi.project.tek.service.impl;

import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.exception.SKUException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.aspectj.lang.enums.DataSourceType;
import com.ruoyi.framework.datasource.DynamicDataSourceContextHolder;
import com.ruoyi.project.tek.domain.NewTekCountryCode;
import com.ruoyi.project.tek.domain.NewTekCurrencyCode;
import com.ruoyi.project.tek.domain.NewTekCurrencyData;
import com.ruoyi.project.tek.domain.NewTekCustomer;
import com.ruoyi.project.tek.domain.NewTekDispose;
import com.ruoyi.project.tek.domain.NewTekModel;
import com.ruoyi.project.tek.domain.NewTekMsrp;
import com.ruoyi.project.tek.domain.NewTekMsrpWk;
import com.ruoyi.project.tek.domain.NewTekProductModel;
import com.ruoyi.project.tek.domain.NewTekPromotionType;
import com.ruoyi.project.tek.domain.TekSkuToMatnr;
import com.ruoyi.project.tek.mapper.NewTekCountryCodeMapper;
import com.ruoyi.project.tek.mapper.NewTekCurrencyCodeMapper;
import com.ruoyi.project.tek.mapper.NewTekCurrencyDataMapper;
import com.ruoyi.project.tek.mapper.NewTekCustomerMapper;
import com.ruoyi.project.tek.mapper.NewTekDisposeMapper;
import com.ruoyi.project.tek.mapper.NewTekModelMapper;
import com.ruoyi.project.tek.mapper.NewTekMsrpMapper;
import com.ruoyi.project.tek.mapper.NewTekMsrpWkMapper;
import com.ruoyi.project.tek.mapper.NewTekProductModelMapper;
import com.ruoyi.project.tek.mapper.NewTekPromotionTypeMapper;
import com.ruoyi.project.tek.mapper.TekSkuToMatnrMapper;
import com.ruoyi.project.tek.request.QueryCustomerReq;
import com.ruoyi.project.tek.request.QueryMsrpWKReq;
import com.ruoyi.project.tek.service.INewTekCustomerService;
import com.ruoyi.project.tek.service.INewTekMsrpWkService;
import com.ruoyi.project.tek.vo.NewTekMsrpWKVO;
import com.ruoyi.project.tek.vo.NewTekPromotionListVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author steven.guo
 * @version 1.0
 * @description:
 * @date 2020/6/19 09:09
 */
@Service
public class NewTekMsrpWkServiceImpl implements INewTekMsrpWkService {

    private static final Logger log = LoggerFactory.getLogger(NewTekMsrpWkServiceImpl.class);

    @Autowired
    private NewTekMsrpWkMapper newTekMsrpWkMapper;
    @Autowired
    private NewTekCustomerMapper newTekCustomerMapper;
    @Autowired
    private NewTekCurrencyDataMapper newTekCurrencyDataMapper;
    @Autowired
    private NewTekMsrpMapper newTekMsrpMapper;
    @Autowired
    private INewTekCustomerService newTekCustomerService;
    @Autowired
    private TekSkuToMatnrMapper tekSkuToMatnrMapper;
    @Autowired
    private NewTekCurrencyCodeMapper newTekCurrencyCodeMapper;
    @Autowired
    private NewTekCountryCodeMapper newTekCountryCodeMapper;
    @Autowired
    private NewTekPromotionTypeMapper newTekPromotionTypeMapper;
    @Autowired
    private NewTekProductModelMapper newTekProductModelMapper;
    @Autowired
    private NewTekModelMapper newTekModelMapper;
    @Autowired
    private NewTekDisposeMapper newTekDisposeMapper;

    @Override
    public int insertNewTekMsrpWk(NewTekMsrpWk newTekMsrpWk) {
        String currencyType = newTekMsrpWk.getCurrencyType();
        if (currencyType.equals("USD")) {
            newTekMsrpWk.setSellingPriceUSD(newTekMsrpWk.getSellingPrice());
        } else {
            NewTekCurrencyData newTekCurrencyData = new NewTekCurrencyData();
            newTekCurrencyData.setFromCountry(currencyType);
            newTekCurrencyData.setToCountry("USD");
            NewTekCurrencyData newTekCurrencyData1 = newTekCurrencyDataMapper.getCurrencyCodeByFromCountryAndToCountry(newTekCurrencyData);
            if (newTekCurrencyData1 != null) {
                BigDecimal sellingPriceUSD = newTekMsrpWk.getSellingPrice().multiply(BigDecimal.valueOf(Double.valueOf(newTekCurrencyData1.getCurrency()))).setScale(2, BigDecimal.ROUND_HALF_UP);
                newTekMsrpWk.setSellingPriceUSD(sellingPriceUSD);
            }
        }
        return newTekMsrpWkMapper.insertNewTekMsrpWk(newTekMsrpWk);
    }

    @Override
    public int updateNewTekMsrpWk(NewTekMsrpWk newTekMsrpWk) {
        String currencyType = newTekMsrpWk.getCurrencyType();
        if (currencyType.equals("USD")) {
            newTekMsrpWk.setSellingPriceUSD(newTekMsrpWk.getSellingPrice());
        } else {
            NewTekCurrencyData newTekCurrencyData = new NewTekCurrencyData();
            newTekCurrencyData.setFromCountry(currencyType);
            newTekCurrencyData.setToCountry("USD");
            NewTekCurrencyData newTekCurrencyData1 = newTekCurrencyDataMapper.getCurrencyCodeByFromCountryAndToCountry(newTekCurrencyData);
            if (newTekCurrencyData1 != null) {
                BigDecimal sellingPriceUSD = newTekMsrpWk.getSellingPrice().multiply(BigDecimal.valueOf(Double.valueOf(newTekCurrencyData1.getCurrency()))).setScale(2, BigDecimal.ROUND_HALF_UP);
                newTekMsrpWk.setSellingPriceUSD(sellingPriceUSD);
            }
        }
        return newTekMsrpWkMapper.updateNewTekMsrpWk(newTekMsrpWk);
    }

    @Override
    public List<NewTekMsrpWk> getMsrpWkByCustomerIdAndSku(NewTekMsrpWk newTekMsrpWk) {
        return newTekMsrpWkMapper.getMsrpWkByCustomerIdAndSku(newTekMsrpWk);
    }

    @Override
    public List<NewTekMsrpWk> checkMsrpWkByCustomerAndSkuAndCountryAndFromAndTo(NewTekMsrpWk newTekMsrpWk) {
        return newTekMsrpWkMapper.checkMsrpWkByCustomerAndSkuAndCountryAndFromAndTo(newTekMsrpWk);
    }

    @Override
    public List<NewTekMsrpWKVO> queryNewTekMsrpWKByPage(QueryMsrpWKReq req) {
        List<NewTekMsrpWKVO> newTekMsrpWkVOList = newTekMsrpWkMapper.queryNewTekMsrpWKByPage(req);
        for (NewTekMsrpWKVO newTekMsrpWKVO : newTekMsrpWkVOList) {
            String sku = newTekMsrpWKVO.getSku();
            List<NewTekProductModel> productModelList = newTekProductModelMapper.getProductModelBySKU(sku);
            if (productModelList.size() > 0 && productModelList != null) {
                Long modelId = productModelList.get(0).getModelId();
                Long disposeId = productModelList.get(0).getDisposeId();
                NewTekModel newTekModel = newTekModelMapper.getNewTekModelById(modelId);
                NewTekDispose newTekDispose = newTekDisposeMapper.getNewTekDisposeById(disposeId);
                newTekMsrpWKVO.setModelInfo(newTekModel.getModelName() + "-" + newTekDispose.getDisposeName());
            }
            Long customerId = newTekMsrpWKVO.getCustomerId();
            if (customerId != null) {
                NewTekCustomer newTekCustomer = newTekCustomerMapper.getNewTekCustomerById(customerId);
                if (newTekCustomer != null) {
                    newTekMsrpWKVO.setCustomerName(newTekCustomer.getCustomerName());
                }
            }
            newTekMsrpWKVO.setCountry(newTekMsrpWKVO.getCountry().split("_")[0]);
            List<NewTekProductModel> newTekProductModelList = newTekProductModelMapper.getProductModelBySKU(newTekMsrpWKVO.getSku());
            for (NewTekProductModel newTekProductModel : newTekProductModelList) {
                if (newTekMsrpWKVO.getCountry().equals(newTekProductModel.getCountryName().split("_")[0])) {
                    newTekMsrpWKVO.setChannelType(newTekProductModel.getChannelType());
                }
            }
        }
        return newTekMsrpWkVOList;
    }

    @Override
    public List<NewTekMsrpWKVO> queryNewTekMsrpWKByPageForPermission(QueryMsrpWKReq req) {
        List<NewTekMsrpWKVO> newTekMsrpWkVOList = newTekMsrpWkMapper.queryNewTekMsrpWKByPageForPermission(req);
        for (NewTekMsrpWKVO newTekMsrpWKVO : newTekMsrpWkVOList) {
            String sku = newTekMsrpWKVO.getSku();
            List<NewTekProductModel> productModelList = newTekProductModelMapper.getProductModelBySKU(sku);
            if (productModelList.size() > 0 && productModelList != null) {
                Long modelId = productModelList.get(0).getModelId();
                Long disposeId = productModelList.get(0).getDisposeId();
                NewTekModel newTekModel = newTekModelMapper.getNewTekModelById(modelId);
                NewTekDispose newTekDispose = newTekDisposeMapper.getNewTekDisposeById(disposeId);
                newTekMsrpWKVO.setModelInfo(newTekModel.getModelName() + "-" + newTekDispose.getDisposeName());
            }
            Long customerId = newTekMsrpWKVO.getCustomerId();
            if (customerId != null) {
                NewTekCustomer newTekCustomer = newTekCustomerMapper.getNewTekCustomerById(customerId);
                if (newTekCustomer != null) {
                    newTekMsrpWKVO.setCustomerName(newTekCustomer.getCustomerName());
                }
            }
            newTekMsrpWKVO.setCountry(newTekMsrpWKVO.getCountry().split("_")[0]);
            List<NewTekProductModel> newTekProductModelList = newTekProductModelMapper.getProductModelBySKU(newTekMsrpWKVO.getSku());
            for (NewTekProductModel newTekProductModel : newTekProductModelList) {
                if (newTekMsrpWKVO.getCountry().equals(newTekProductModel.getCountryName().split("_")[0])) {
                    newTekMsrpWKVO.setChannelType(newTekProductModel.getChannelType());
                }
            }
        }
        return newTekMsrpWkVOList;
    }

    @Override
    public NewTekMsrpWKVO getNewTekMsrpWKById(Long id) {
        NewTekMsrpWKVO newTekMsrpWKVO = newTekMsrpWkMapper.getNewTekMsrpWKById(id);

        if (newTekMsrpWKVO.getCustomerId() != null) {
            NewTekCustomer newTekCustomer = newTekCustomerMapper.getNewTekCustomerById(newTekMsrpWKVO.getCustomerId());
            if (newTekCustomer != null) {
                newTekMsrpWKVO.setCustomerName(newTekCustomer.getCustomerName());
            }
        }
        return newTekMsrpWKVO;
    }

    @Override
    public int deleteMsrpWKByIds(Long[] ids) {
        return newTekMsrpWkMapper.deleteMsrpWKByIds(ids);
    }

    @Override
    public List<NewTekPromotionListVO> queryPromotionListByPage(QueryMsrpWKReq req) {
        List<NewTekPromotionListVO> newTekPromotionListVOList = newTekMsrpWkMapper.queryPromotionListByPage(req);
        for (NewTekPromotionListVO newTekPromotionListVO : newTekPromotionListVOList) {
            Long customerId = newTekPromotionListVO.getCustomerId();
            String sku = newTekPromotionListVO.getSku();
            if (customerId != null) {
                NewTekCustomer newTekCustomer = newTekCustomerMapper.getNewTekCustomerById(customerId);
                if (newTekCustomer != null) {
                    newTekPromotionListVO.setCustomerName(newTekCustomer.getCustomerName());
                }
            }
            newTekPromotionListVO.setCountry(newTekPromotionListVO.getCountry().split("_")[0]);

            List<NewTekProductModel> productModelList = newTekProductModelMapper.getProductModelBySKU(sku);
            if (productModelList.size() > 0 && productModelList != null) {
                Long modelId = productModelList.get(0).getModelId();
                Long disposeId = productModelList.get(0).getDisposeId();
                NewTekModel newTekModel = newTekModelMapper.getNewTekModelById(modelId);
                NewTekDispose newTekDispose = newTekDisposeMapper.getNewTekDisposeById(disposeId);
                newTekPromotionListVO.setModelInfo(newTekModel.getModelName() + "-" + newTekDispose.getDisposeName());
            }
        }
        return newTekPromotionListVOList;
    }

    @Override
    public List<NewTekPromotionListVO> queryPromotionListByPageForPermission(QueryMsrpWKReq req) {
        List<NewTekPromotionListVO> newTekPromotionListVOList = newTekMsrpWkMapper.queryPromotionListByPageForPermission(req);
        for (NewTekPromotionListVO newTekPromotionListVO : newTekPromotionListVOList) {
            Long customerId = newTekPromotionListVO.getCustomerId();
            String sku = newTekPromotionListVO.getSku();
            if (customerId != null) {
                NewTekCustomer newTekCustomer = newTekCustomerMapper.getNewTekCustomerById(customerId);
                if (newTekCustomer != null) {
                    newTekPromotionListVO.setCustomerName(newTekCustomer.getCustomerName());
                }
            }
            newTekPromotionListVO.setCountry(newTekPromotionListVO.getCountry().split("_")[0]);

            List<NewTekProductModel> productModelList = newTekProductModelMapper.getProductModelBySKU(sku);
            if (productModelList.size() > 0 && productModelList != null) {
                Long modelId = productModelList.get(0).getModelId();
                Long disposeId = productModelList.get(0).getDisposeId();
                NewTekModel newTekModel = newTekModelMapper.getNewTekModelById(modelId);
                NewTekDispose newTekDispose = newTekDisposeMapper.getNewTekDisposeById(disposeId);
                newTekPromotionListVO.setModelInfo(newTekModel.getModelName() + "-" + newTekDispose.getDisposeName());
            }
        }
        return newTekPromotionListVOList;
    }

    @Override
    public List<NewTekMsrpWk> queryPromotionCalendarList(NewTekMsrpWk newTekMsrpWk) {
        return newTekMsrpWkMapper.queryPromotionCalendarList(newTekMsrpWk);
    }

    @Override
    public List<NewTekMsrpWk> queryPromotionCalendarListForPermission(NewTekMsrpWk newTekMsrpWk) {
        return newTekMsrpWkMapper.queryPromotionCalendarListForPermission(newTekMsrpWk);
    }

    @Override
    public List<NewTekMsrpWk> queryByDateAndPromotionType(QueryMsrpWKReq req) throws Exception {
        String reqDate = req.getValidFromWK();
        List<NewTekMsrpWk> msrpVOList = newTekMsrpWkMapper.queryByDateAndPromotionType(req);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        for (int z = 0; z < msrpVOList.size(); z++) {
            msrpVOList.get(z).setModelName(msrpVOList.get(z).getModelName() + "-" + msrpVOList.get(z).getDisposeName());
            String[] validFromWKGroupArr = msrpVOList.get(z).getValidFromWKGroup().split(",");
            String[] validToWKGroupArr = msrpVOList.get(z).getValidToWKGroup().split(",");
            String validFromWKFinal = null;
            String validToWKFinal = null;
            for (int i = 0; i < validFromWKGroupArr.length; i++) {
                if (i <= validFromWKGroupArr.length - 2) {
                    int a = sf.parse(validFromWKGroupArr[i]).compareTo(sf.parse(validFromWKGroupArr[i + 1]));
                    if (sf.parse(validFromWKGroupArr[i]).compareTo(sf.parse(validFromWKGroupArr[i + 1])) == -1) {
                        validFromWKFinal = validFromWKGroupArr[i];
                    } else {
                        validFromWKFinal = validFromWKGroupArr[i + 1];
                    }
                } else if (i == validFromWKGroupArr.length - 1 && validFromWKGroupArr.length > 1) {
                    if (sf.parse(validFromWKFinal).compareTo(sf.parse(validFromWKGroupArr[i])) == -1) {

                    } else {
                        validFromWKFinal = validFromWKGroupArr[i];
                    }
                } else {
                    validFromWKFinal = validFromWKGroupArr[0];
                }
            }
            for (int i = 0; i < validToWKGroupArr.length; i++) {
                if (i <= validToWKGroupArr.length - 2) {
                    if (sf.parse(validToWKGroupArr[i]).compareTo(sf.parse(validToWKGroupArr[i + 1])) == -1) {
                        validToWKFinal = validToWKGroupArr[i + 1];
                    } else {
                        validToWKFinal = validToWKGroupArr[i];
                    }
                } else if (i == validToWKGroupArr.length - 1 && validToWKGroupArr.length > 1) {
                    if (sf.parse(validFromWKFinal).compareTo(sf.parse(validToWKGroupArr[i])) == -1) {

                    } else {
                        validToWKFinal = validToWKGroupArr[i];
                    }
                } else {
                    validToWKFinal = validToWKGroupArr[0];
                }
            }
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate validFromWK = LocalDate.parse(validFromWKFinal, fmt);
            LocalDate validToWK = LocalDate.parse(validToWKFinal, fmt);
            LocalDate reqFromDate = LocalDate.parse(reqDate, fmt);
            if (reqFromDate.compareTo(validFromWK) >= 0 && reqFromDate.compareTo(validToWK) <= 0) {
            } else {
                msrpVOList.remove(z);
                z--;
            }
        }
        return msrpVOList;
    }

    @Override
    public List<NewTekMsrpWk> queryByDateAndPromotionTypeForPermission(QueryMsrpWKReq req) throws Exception {
        String reqDate = req.getValidFromWK();
        List<NewTekMsrpWk> msrpVOList = newTekMsrpWkMapper.queryByDateAndPromotionTypeForPermission(req);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        for (int z = 0; z < msrpVOList.size(); z++) {
            msrpVOList.get(z).setModelName(msrpVOList.get(z).getModelName() + "-" + msrpVOList.get(z).getDisposeName());
            String[] validFromWKGroupArr = msrpVOList.get(z).getValidFromWKGroup().split(",");
            String[] validToWKGroupArr = msrpVOList.get(z).getValidToWKGroup().split(",");
            String validFromWKFinal = null;
            String validToWKFinal = null;
            for (int i = 0; i < validFromWKGroupArr.length; i++) {
                if (i <= validFromWKGroupArr.length - 2) {
                    int a = sf.parse(validFromWKGroupArr[i]).compareTo(sf.parse(validFromWKGroupArr[i + 1]));
                    if (sf.parse(validFromWKGroupArr[i]).compareTo(sf.parse(validFromWKGroupArr[i + 1])) == -1) {
                        validFromWKFinal = validFromWKGroupArr[i];
                    } else {
                        validFromWKFinal = validFromWKGroupArr[i + 1];
                    }
                } else if (i == validFromWKGroupArr.length - 1 && validFromWKGroupArr.length > 1) {
                    if (sf.parse(validFromWKFinal).compareTo(sf.parse(validFromWKGroupArr[i])) == -1) {

                    } else {
                        validFromWKFinal = validFromWKGroupArr[i];
                    }
                } else {
                    validFromWKFinal = validFromWKGroupArr[0];
                }
            }
            for (int i = 0; i < validToWKGroupArr.length; i++) {
                if (i <= validToWKGroupArr.length - 2) {
                    if (sf.parse(validToWKGroupArr[i]).compareTo(sf.parse(validToWKGroupArr[i + 1])) == -1) {
                        validToWKFinal = validToWKGroupArr[i + 1];
                    } else {
                        validToWKFinal = validToWKGroupArr[i];
                    }
                } else if (i == validToWKGroupArr.length - 1 && validToWKGroupArr.length > 1) {
                    if (sf.parse(validFromWKFinal).compareTo(sf.parse(validToWKGroupArr[i])) == -1) {

                    } else {
                        validToWKFinal = validToWKGroupArr[i];
                    }
                } else {
                    validToWKFinal = validToWKGroupArr[0];
                }
            }
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate validFromWK = LocalDate.parse(validFromWKFinal, fmt);
            LocalDate validToWK = LocalDate.parse(validToWKFinal, fmt);
            LocalDate reqFromDate = LocalDate.parse(reqDate, fmt);
            if (reqFromDate.compareTo(validFromWK) >= 0 && reqFromDate.compareTo(validToWK) <= 0) {
            } else {
                msrpVOList.remove(z);
                z--;
            }
        }
        return msrpVOList;
    }

    @Override
    public String importSellingPrice(List<NewTekMsrpWk> sellingPriceList, Boolean isUpdateSupport) {
        if (StringUtils.isNull(sellingPriceList) || sellingPriceList.size() == 0) {
            throw new SKUException("导入Selling Price数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        List<TekSkuToMatnr> tekSkuToMatnrList = tekSkuToMatnrMapper.queryTekSkuAll();
        DynamicDataSourceContextHolder.clearDataSourceType();


        List<String> skuList = new ArrayList<>();
        for (TekSkuToMatnr tekSkuToMatnr : tekSkuToMatnrList) {
            skuList.add(tekSkuToMatnr.getGoodsSku());
        }
        for (NewTekMsrpWk newTekMsrpWk : sellingPriceList) {
            try {
                String sku = newTekMsrpWk.getSku();
                if (sku == null || sku == "") {
                    throw new SKUException("SKU不能为空");
                }
                String currency = newTekMsrpWk.getCurrencyType();
                if (currency == null || currency == "") {
                    throw new SKUException("Currency不能为空");
                }
                List<NewTekCurrencyCode> currencyCodeList = newTekCurrencyCodeMapper.queryNewTekCurrencyCodeAll(null);
                List<String> currencyList = new ArrayList<>();
                for (NewTekCurrencyCode newTekCurrencyCode : currencyCodeList) {
                    currencyList.add(newTekCurrencyCode.getCurrencyCode());
                }
                if (!currencyList.contains(currency)) {
                    throw new SKUException("请输入正确的Currency！");
                }

                String country = newTekMsrpWk.getCountry();
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

                String customerName = newTekMsrpWk.getCustomerName();
                if (customerName == null || customerName == "") {
                    throw new SKUException("CustomerName不能为空");
                }
                QueryCustomerReq req = new QueryCustomerReq();
                req.setCustomerName(customerName);
                req.setCountry(country);
                List<NewTekCustomer> customerList = newTekCustomerMapper.getAllCustomer(req);
                List<String> customerList1 = new ArrayList<>();
                for (NewTekCustomer newTekCustomer : customerList) {
                    customerList1.add(newTekCustomer.getCustomerName());
                }
                if (!customerList1.contains(customerName)) {
                    throw new SKUException("请输入正确的Country或者CustomerName！");
                }

                String validFromWK = newTekMsrpWk.getValidFromWK();
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                Date fromDate = new Date(validFromWK);
                String formatDateFrom = sf.format(fromDate);
                newTekMsrpWk.setValidFromWK(formatDateFrom);

                if (validFromWK == null || validFromWK == "") {
                    throw new SKUException("ValidFrom不能为空");
                }
                String validToWK = newTekMsrpWk.getValidToWK();
                Date toDate = new Date(validToWK);
                String formatDateTo = sf.format(toDate);
                newTekMsrpWk.setValidToWK(formatDateTo);

                if (validToWK == null || validToWK == "") {
                    throw new SKUException("ValidTo不能为空");
                }
                String promotionType = newTekMsrpWk.getPromotionType();
                if (promotionType == null || promotionType == "") {
                    throw new SKUException("PromotionType不能为空");
                }
                List<NewTekPromotionType> promotionTypeList = newTekPromotionTypeMapper.getAllPromotionType();
                List<String> promotionTypeListStr = new ArrayList<>();
                for (NewTekPromotionType newTekPromotionType : promotionTypeList) {
                    promotionTypeListStr.add(newTekPromotionType.getPromotionType());
                }
                if (!promotionTypeListStr.contains(promotionType)) {
                    throw new SKUException("请输入正确的PromotionType");
                }

                BigDecimal sellingPrice = newTekMsrpWk.getSellingPrice();
                if (sellingPrice == null) {
                    throw new SKUException("Selling Price不能为空");
                }
                if (!skuList.contains(sku)) {
                    throw new SKUException("请输入正确的SKU");
                }
                String fromYear = formatDateFrom.split("-")[0];
                newTekMsrpWk.setYear(fromYear);
                LocalDate now = LocalDate.now();
                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate fromWK = LocalDate.parse(formatDateFrom, fmt);
                LocalDate toWK = LocalDate.parse(formatDateTo, fmt);
                if (fromWK.compareTo(now) == -1 || toWK.compareTo(now) == -1) {
                    throw new SKUException("当前时间只能维护未来周的Selling Price");
                }
                QueryCustomerReq req1 = new QueryCustomerReq();
                req1.setCountry(country);
                req1.setCustomerName(customerName);
                List<NewTekCustomer> customerList2 = newTekCustomerService.getAllCustomer(req1);
                if (customerList2.size() > 0 && customerList2 != null) {
                    newTekMsrpWk.setCustomerId(customerList2.get(0).getId());
                }
                NewTekMsrp newTekMsrp = new NewTekMsrp();
                newTekMsrp.setSku(newTekMsrpWk.getSku());
                List<NewTekMsrp> newTekMsrpList = newTekMsrpMapper.getNewTekMsrpByCustomerAndProductAndSku(newTekMsrp);
                if (newTekMsrpList.size() > 0 && newTekMsrpList != null) {
                    newTekMsrpWk.setMsrpId(newTekMsrpList.get(0).getId());
                }
                newTekMsrpWk.setCreateBy(SecurityUtils.getUsername());
                newTekMsrpWk.setUpdateBy(SecurityUtils.getUsername());
                //根据sku、customerId、year、country查询已维护的周价格，如果没有冲突的周，就可以插入，如果有冲突，就报错
                newTekMsrpWk.setValidFromWK(null);
                List<NewTekMsrpWk> newTekMsrpWkList = this.getMsrpWkByCustomerIdAndSku(newTekMsrpWk);
                if (newTekMsrpWkList.size() == 0 || newTekMsrpWkList == null) {
                    newTekMsrpWk.setValidFromWK(formatDateFrom);
                    this.insertNewTekMsrpWk(newTekMsrpWk);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、Selling Price " + newTekMsrpWk.getSku() + " 导入成功");
                } else if (newTekMsrpWkList.size() > 0 && newTekMsrpWkList != null) {
                    for (NewTekMsrpWk newTekMsrpWk1 : newTekMsrpWkList) {
                        LocalDate fromWKExist = LocalDate.parse(newTekMsrpWk1.getValidFromWK(), fmt);
                        LocalDate toWKExist = LocalDate.parse(newTekMsrpWk1.getValidToWK(), fmt);
                        if (fromWK.compareTo(fromWKExist) == 0 || toWK.compareTo(toWKExist) == 0 || (fromWK.compareTo(fromWKExist) > 0 && toWK.compareTo(toWKExist) < 0) || fromWK.compareTo(toWKExist) == 0) {
                            throw new SKUException("不能维护已存在周期内的价格！");
                        }
                    }
                    newTekMsrpWk.setValidFromWK(formatDateTo);
                    this.insertNewTekMsrpWk(newTekMsrpWk);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、Selling Price " + newTekMsrpWk.getSku() + " 导入成功");
                } else if (isUpdateSupport) {
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、Selling Price " + newTekMsrpWk.getSku() + " 更新成功");
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、Selling Price " + newTekMsrpWk.getSku() + " 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、Selling Price " + newTekMsrpWk.getSku() + " 导入失败：";
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

    @Override
    public List<NewTekMsrpWk> queryMsrpWKCountryList() {
        return newTekMsrpWkMapper.queryMsrpWKCountryList();
    }


}
