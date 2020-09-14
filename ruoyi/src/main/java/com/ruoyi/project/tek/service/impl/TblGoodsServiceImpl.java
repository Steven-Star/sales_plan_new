package com.ruoyi.project.tek.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.http.HttpUtils;
import com.ruoyi.framework.aspectj.lang.annotation.DataSource;
import com.ruoyi.framework.aspectj.lang.enums.DataSourceType;
import com.ruoyi.project.tek.domain.NewTekCurrencyCode;
import com.ruoyi.project.tek.domain.NewTekCurrencyData;
import com.ruoyi.project.tek.domain.NewTekDispose;
import com.ruoyi.project.tek.domain.NewTekMsrp;
import com.ruoyi.project.tek.domain.NewTekMsrpWk;
import com.ruoyi.project.tek.domain.NewTekProductModel;
import com.ruoyi.project.tek.domain.TblGoods;
import com.ruoyi.project.tek.mapper.NewTekCurrencyCodeMapper;
import com.ruoyi.project.tek.mapper.NewTekCurrencyDataMapper;
import com.ruoyi.project.tek.mapper.NewTekDisposeMapper;
import com.ruoyi.project.tek.mapper.NewTekMsrpMapper;
import com.ruoyi.project.tek.mapper.NewTekMsrpWkMapper;
import com.ruoyi.project.tek.mapper.NewTekProductModelMapper;
import com.ruoyi.project.tek.mapper.TblGoodsMapper;
import com.ruoyi.project.tek.request.QueryCalendarReq;
import com.ruoyi.project.tek.request.QuerySalesPlanGoodsReq;
import com.ruoyi.project.tek.service.ITblGoodsService;
import com.ruoyi.project.tek.vo.NewTekCalendarVO;
import com.ruoyi.project.tek.vo.SalesPlanVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ruoyi.common.constant.tekConstants;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 商品 服务实现类
 * </p>
 */
@Service
public class TblGoodsServiceImpl implements ITblGoodsService {

    @Autowired
    private TblGoodsMapper tblGoodsMapper;
    @Autowired
    private NewTekProductModelMapper newTekProductModelMapper;
    @Autowired
    private NewTekMsrpMapper newTekMsrpMapper;
    @Autowired
    private NewTekCurrencyCodeMapper newTekCurrencyCodeMapper;
    @Autowired
    private NewTekCurrencyDataMapper newTekCurrencyDataMapper;


    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<TblGoods> queryGoodsAll() {
        return tblGoodsMapper.queryGoodsAll();
    }

    @Override
    @Transactional
    public List<NewTekCalendarVO> queryCalendarAllData(QueryCalendarReq req) throws Exception{
        String reqYear = req.getYear();
        if(reqYear == null){
            req.setYear(String.valueOf(LocalDate.now().getYear()));
            reqYear = String.valueOf(LocalDate.now().getYear());
        }
        List<NewTekCalendarVO> newTekCalendarVOList = tblGoodsMapper.queryCalendarAllData(req);
        List<NewTekCalendarVO> newTekCalendarVOList1 = getFirstStep(reqYear,newTekCalendarVOList);
        Map<String,NewTekCalendarVO> map = new HashMap<>();
        List<String> customerList = new ArrayList<>();
        List<String> skuList = new ArrayList<>();
        for(NewTekCalendarVO newTekCalendarVO : newTekCalendarVOList1){
            map.put(newTekCalendarVO.getKey(),newTekCalendarVO);
        }
        for(NewTekCalendarVO newTekCalendarVO : newTekCalendarVOList1){
            if (!customerList.contains(newTekCalendarVO.getCustomerName())) {
                customerList.add(newTekCalendarVO.getCustomerName());
            }
            if (!skuList.contains(newTekCalendarVO.getSku())) {
                skuList.add(newTekCalendarVO.getSku());
            }
            String modelName = newTekCalendarVO.getModelName();
            String disposeName = newTekCalendarVO.getDisposeName();
            if (modelName != null && disposeName != null) {
                modelName = modelName + "-" + disposeName;
                newTekCalendarVO.setModelName(modelName);
            } else if (modelName == null && disposeName != null) {
                modelName = disposeName;
                newTekCalendarVO.setModelName(modelName);
            }
            //2.同品类、同系列、不同配置
            newTekCalendarVO = queryByDisposeSortId(map,newTekCalendarVO);
            //2.同品类、同配置、不同系列
            newTekCalendarVO = queryByModelSortId(map,newTekCalendarVO);
            //3.同一个sku、country、时间段内，不同的customer的价格要一样，否则要warning
            // TODO: 2020/7/22
            newTekCalendarVO = queryBySkuCountryAndDiffCustomer(map,newTekCalendarVO);
            newTekCalendarVO.setSkuList(skuList);
            newTekCalendarVO.setCustomerList(customerList);
            newTekCalendarVO.setCountry(newTekCalendarVO.getCountry().split("_")[0]);
        }
        return newTekCalendarVOList1;
    }

    @Override
    public List<NewTekCalendarVO> queryCalendarAllDataForPermission(QueryCalendarReq req) throws Exception {
        String reqYear = req.getYear();
        if(reqYear == null){
            req.setYear(String.valueOf(LocalDate.now().getYear()));
            reqYear = String.valueOf(LocalDate.now().getYear());
        }
        List<NewTekCalendarVO> newTekCalendarVOList = tblGoodsMapper.queryCalendarAllDataForPermission(req);
        List<NewTekCalendarVO> newTekCalendarVOList1 = getFirstStep(reqYear,newTekCalendarVOList);
        Map<String,NewTekCalendarVO> map = new HashMap<>();
        List<String> customerList = new ArrayList<>();
        List<String> skuList = new ArrayList<>();
        for(NewTekCalendarVO newTekCalendarVO : newTekCalendarVOList1){
            map.put(newTekCalendarVO.getKey(),newTekCalendarVO);
        }
        for(NewTekCalendarVO newTekCalendarVO : newTekCalendarVOList1){
            if (!customerList.contains(newTekCalendarVO.getCustomerName())) {
                customerList.add(newTekCalendarVO.getCustomerName());
            }
            if (!skuList.contains(newTekCalendarVO.getSku())) {
                skuList.add(newTekCalendarVO.getSku());
            }
            String modelName = newTekCalendarVO.getModelName();
            String disposeName = newTekCalendarVO.getDisposeName();
            if (modelName != null && disposeName != null) {
                modelName = modelName + "-" + disposeName;
                newTekCalendarVO.setModelName(modelName);
            } else if (modelName == null && disposeName != null) {
                modelName = disposeName;
                newTekCalendarVO.setModelName(modelName);
            }
            //2.同品类、同系列、不同配置
            newTekCalendarVO = queryByDisposeSortId(map,newTekCalendarVO);
            //2.同品类、同配置、不同系列
            newTekCalendarVO = queryByModelSortId(map,newTekCalendarVO);
            //3.同一个sku、country、时间段内，不同的customer的价格要一样，否则要warning
            newTekCalendarVO = queryBySkuCountryAndDiffCustomer(map,newTekCalendarVO);
            newTekCalendarVO.setSkuList(skuList);
            newTekCalendarVO.setCustomerList(customerList);
            newTekCalendarVO.setCountry(newTekCalendarVO.getCountry().split("_")[0]);
        }
        return newTekCalendarVOList1;
    }

    /**
     * 销售预测项目
     * 查询所有 平台+站点+sku的产品
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<SalesPlanVO> getSalesPlanGoodsAll(QuerySalesPlanGoodsReq req) {
        return tblGoodsMapper.getSalesPlanGoodsAll(req);
    }

    public List<NewTekCalendarVO> getFirstStep(String reqYear,List<NewTekCalendarVO> newTekCalendarVOList) throws Exception{
        Map<String,List<NewTekCalendarVO>> map = new HashMap<>();
        for(NewTekCalendarVO newTekCalendarVO : newTekCalendarVOList){
            String country = newTekCalendarVO.getCountry();
            String sku = newTekCalendarVO.getSku();
            List<NewTekCalendarVO> newTekCalendarVOList1 = new ArrayList<>();
            Long customerId = newTekCalendarVO.getCustomerId();
            String year = newTekCalendarVO.getYear();
            String key = sku + "," + customerId + "," + country + "," + year;
            if(map.containsKey(key)){
                //已存在的selling price数据，直接追加
                List<NewTekCalendarVO> newTekCalendarVOList2 = map.get(key);
                NewTekCalendarVO newTekCalendarVO1 = new NewTekCalendarVO();
                newTekCalendarVO1.setSku(newTekCalendarVO.getSku());
                newTekCalendarVO1.setCountry(newTekCalendarVO.getCountry());
                newTekCalendarVO1.setCustomerName(newTekCalendarVO.getCustomerName());
                newTekCalendarVO1.setCustomerId(newTekCalendarVO.getCustomerId());
                newTekCalendarVO1.setYear(newTekCalendarVO.getYear());
                newTekCalendarVO1.setValidFromWK(newTekCalendarVO.getValidFromWK());
                newTekCalendarVO1.setValidToWK(newTekCalendarVO.getValidToWK());
                newTekCalendarVO1.setCurrencyType(newTekCalendarVO.getCurrencyType());
                newTekCalendarVO1.setSellingPrice(newTekCalendarVO.getSellingPrice());
                newTekCalendarVO1.setSellingPriceUSD(newTekCalendarVO.getSellingPriceUSD());
                newTekCalendarVO1.setCategoryId(newTekCalendarVO.getCategoryId());
                newTekCalendarVO1.setCategoryName(newTekCalendarVO.getCategoryName());
                newTekCalendarVO1.setModelId(newTekCalendarVO.getModelId());
                newTekCalendarVO1.setModelName(newTekCalendarVO.getModelName());
                newTekCalendarVO1.setModelSortId(newTekCalendarVO.getModelSortId());
                newTekCalendarVO1.setDisposeId(newTekCalendarVO.getDisposeId());
                newTekCalendarVO1.setDisposeName(newTekCalendarVO.getDisposeName());
                newTekCalendarVO1.setDisposeSortId(newTekCalendarVO.getDisposeSortId());
                newTekCalendarVO1.setMsrp(newTekCalendarVO.getMsrp());
                newTekCalendarVO1.setMsrpCountry(newTekCalendarVO.getMsrpCountry());
                newTekCalendarVO1.setCurrencyTypeWK(newTekCalendarVO.getCurrencyTypeWK());
                newTekCalendarVO1.setPromotionType(newTekCalendarVO.getPromotionType());
                newTekCalendarVO1.setDisposeGroupId(newTekCalendarVO.getDisposeGroupId());
                newTekCalendarVO1.setDisposeGroupName(newTekCalendarVO.getDisposeGroupName());
                newTekCalendarVOList2.add(newTekCalendarVO1);
            }else{
                newTekCalendarVOList1.add(newTekCalendarVO);
                map.put(key,newTekCalendarVOList1);
            }
        }
        //此时的map存放已经分过组的selling price数据，再组合拆分日期及价格
        List<NewTekCalendarVO> newTekCalendarVOListFinal = new ArrayList<>();
        for (Map.Entry<String, List<NewTekCalendarVO>> m : map.entrySet()) {
            NewTekCalendarVO newTekCalendarVOFinal = new NewTekCalendarVO();
            String key = m.getKey();
            String[] arr = key.split(",");
            newTekCalendarVOFinal.setSku(arr[0]);
            newTekCalendarVOFinal.setCustomerId(Long.parseLong(arr[1]));
            newTekCalendarVOFinal.setCountry(arr[2]);
            newTekCalendarVOFinal.setYear(arr[3]);
            newTekCalendarVOFinal.setKey(key);
            NewTekCurrencyCode newTekCurrencyCode = newTekCurrencyCodeMapper.getCurrencyCodeByCountry(newTekCalendarVOFinal.getCountry().split("_")[0]);
            //这是返回的总个数
            List<NewTekCalendarVO> newTekCalendarVOList10 = m.getValue();
            for(NewTekCalendarVO newTekCalendarVO : newTekCalendarVOList10){
                NewTekMsrp newTekMsrp = new NewTekMsrp();
                newTekMsrp.setSku(newTekCalendarVOFinal.getSku());
                newTekMsrp.setCountry(newTekCalendarVOFinal.getCountry());
                List<NewTekMsrp> newTekMsrpList = newTekMsrpMapper.getNewTekMsrpByCustomerAndProductAndSku(newTekMsrp);
                for (NewTekMsrp newTekMsrp1 : newTekMsrpList) {
                    Date validFrom = newTekMsrp1.getValidFrom();
                    Date validTo = newTekMsrp1.getValidTo();
                    Date now = new Date();
                    //求msrp的客户本国和转换为美金汇率价格
                    if ((now.compareTo(validFrom) == 1 || now.compareTo(validFrom) == 0) && (now.compareTo(validTo) == -1 || now.compareTo(validTo) == 0)) {
                        BigDecimal msrp = newTekMsrp1.getMsrp();
                        newTekCalendarVO.setMsrpCountry(msrp);
                        NewTekCurrencyData newTekCurrencyData = new NewTekCurrencyData();
                        newTekCurrencyData.setFromCountry(newTekMsrp1.getCurrencyType());
                        newTekCurrencyData.setToCountry("USD");
                        NewTekCurrencyData newTekCurrencyData1 = newTekCurrencyDataMapper.getCurrencyCodeByFromCountryAndToCountry(newTekCurrencyData);
                        if (newTekCurrencyData1 != null) {
                            newTekCalendarVO.setMsrp(msrp.multiply(BigDecimal.valueOf(Double.valueOf(newTekCurrencyData1.getCurrency()))).setScale(2, BigDecimal.ROUND_HALF_UP));
                        } else if (newTekMsrp1.getCurrencyType().equals(newTekCurrencyCode.getCurrencyCode())) {
                            newTekCalendarVO.setMsrp(msrp);
                        }

//                        BigDecimal msrp = newTekMsrp1.getMsrp();
//                        newTekCalendarVO.setMsrp(msrp);
//                        NewTekCurrencyData newTekCurrencyData = new NewTekCurrencyData();
//                        newTekCurrencyData.setFromCountry(newTekMsrp1.getCurrencyType());
//                        newTekCurrencyData.setToCountry(newTekCurrencyCode.getCurrencyCode());
//                        NewTekCurrencyData newTekCurrencyData1 = newTekCurrencyDataMapper.getCurrencyCodeByFromCountryAndToCountry(newTekCurrencyData);
//                        if (newTekCurrencyData1 != null) {
//                            newTekCalendarVO.setMsrpCountry(msrp.multiply(BigDecimal.valueOf(Double.valueOf(newTekCurrencyData1.getCurrency()))).setScale(2, BigDecimal.ROUND_HALF_UP));
//                        } else if (newTekMsrp1.getCurrencyType().equals(newTekCurrencyCode.getCurrencyCode())) {
//                            newTekCalendarVO.setMsrpCountry(msrp);
//                        }
                    }
                }
                newTekCalendarVOFinal.setMsrp(newTekCalendarVO.getMsrp());
                newTekCalendarVOFinal.setMsrpCountry(newTekCalendarVO.getMsrpCountry());
                newTekCalendarVOFinal.setCustomerName(newTekCalendarVO.getCustomerName());
                newTekCalendarVOFinal.setCustomerId(newTekCalendarVO.getCustomerId());
                newTekCalendarVOFinal.setCategoryId(newTekCalendarVO.getCategoryId());
                newTekCalendarVOFinal.setCategoryName(newTekCalendarVO.getCategoryName());
                newTekCalendarVOFinal.setModelId(newTekCalendarVO.getModelId());
                newTekCalendarVOFinal.setModelName(newTekCalendarVO.getModelName());
                newTekCalendarVOFinal.setModelSortId(newTekCalendarVO.getModelSortId());
                newTekCalendarVOFinal.setDisposeSortId(newTekCalendarVO.getDisposeSortId());
                newTekCalendarVOFinal.setDisposeName(newTekCalendarVO.getDisposeName());
                newTekCalendarVOFinal.setDisposeId(newTekCalendarVO.getDisposeId());
                newTekCalendarVOFinal.setCurrencyTypeWK(newTekCalendarVO.getCurrencyTypeWK());
                newTekCalendarVOFinal.setPromotionType(newTekCalendarVO.getPromotionType());
                newTekCalendarVOFinal.setDisposeGroupId(newTekCalendarVO.getDisposeGroupId());
                newTekCalendarVOFinal.setDisposeGroupName(newTekCalendarVO.getDisposeGroupName());
                String year = newTekCalendarVO.getYear();
                String fromDay = newTekCalendarVO.getValidFromWK();
                String toDay = newTekCalendarVO.getValidToWK();
                // TODO: 2020/7/22
                if(toDay.equals("9999-12-31")){
                    toDay = reqYear + "-12-31";
                }
                //获取时间段的所有日期
                List<String> list = DateUtils.addDates(fromDay, toDay);
                for (String date : list) {
                    //获取时间段内每个日期属于哪一周
                    int week = DateUtils.getWeekByDate(reqYear,date);
                    if(week == 1){
                        if(newTekCalendarVOFinal.getWk1Msrp() != null && newTekCalendarVOFinal.getWk1Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk1Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk1MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk1Msrp() == null){
                            newTekCalendarVOFinal.setWk1Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk1MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        //获取周内日期，第一天-第七天，以周一开始计算
                        String week1Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),1,0);
                        String[] week1DayArr = week1Days.split(",");
                        String week1DayFrom = week1DayArr[0];
                        String week1DayTo = week1DayArr[1];
                        //获取周内所有日期，从第一天到第七天
                        List<String> week1 = DateUtils.addDates(week1DayFrom,week1DayTo);
                        if(week1.contains(date)){
                            for(int i=1;i<=week1.size();i++){
                                newTekCalendarVOFinal.setWk1Day1Date(week1.get(0));
                                newTekCalendarVOFinal.setWk1Day2Date(week1.get(1));
                                newTekCalendarVOFinal.setWk1Day3Date(week1.get(2));
                                newTekCalendarVOFinal.setWk1Day4Date(week1.get(3));
                                newTekCalendarVOFinal.setWk1Day5Date(week1.get(4));
                                newTekCalendarVOFinal.setWk1Day6Date(week1.get(5));
                                newTekCalendarVOFinal.setWk1Day7Date(week1.get(6));
                                if (i == 1 && week1.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk1Day1(newTekCalendarVO.getSellingPriceUSD());
                                    newTekCalendarVOFinal.setWk1Day1Date(week1.get(0));
                                } else if(i == 2 && week1.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk1Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week1.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk1Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week1.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk1Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week1.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk1Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week1.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk1Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week1.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk1Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                    if(week == 2){
                        if(newTekCalendarVOFinal.getWk2Msrp() != null && newTekCalendarVOFinal.getWk2Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk2Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk2MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk2Msrp() == null){
                            newTekCalendarVOFinal.setWk2Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk2MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        //获取周内日期，第一天-第七天，以周一开始计算
                        String week2Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),2,0);
                        String[] week2DayArr = week2Days.split(",");
                        String week2DayFrom = week2DayArr[0];
                        String week2DayTo = week2DayArr[1];
                        //获取周内所有日期，从第一天到第七天
                        List<String> week2 = DateUtils.addDates(week2DayFrom,week2DayTo);
                        if(week2.contains(date)){
                            for(int i=1;i<=week2.size();i++){
                                newTekCalendarVOFinal.setWk2Day1Date(week2.get(0));
                                newTekCalendarVOFinal.setWk2Day2Date(week2.get(1));
                                newTekCalendarVOFinal.setWk2Day3Date(week2.get(2));
                                newTekCalendarVOFinal.setWk2Day4Date(week2.get(3));
                                newTekCalendarVOFinal.setWk2Day5Date(week2.get(4));
                                newTekCalendarVOFinal.setWk2Day6Date(week2.get(5));
                                newTekCalendarVOFinal.setWk2Day7Date(week2.get(6));
                                if (i == 1 && week2.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk2Day1(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 2 && week2.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk2Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week2.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk2Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week2.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk2Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week2.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk2Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week2.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk2Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week2.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk2Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                    if(week == 3){
                        if(newTekCalendarVOFinal.getWk3Msrp() != null && newTekCalendarVOFinal.getWk3Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk3Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk3MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk3Msrp() == null){
                            newTekCalendarVOFinal.setWk3Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk3MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        String week3Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),3,0);
                        String[] week3DayArr = week3Days.split(",");
                        String week3DayFrom = week3DayArr[0];
                        String week3DayTo = week3DayArr[1];
                        List<String> week3 = DateUtils.addDates(week3DayFrom,week3DayTo);
                        if(week3.contains(date)){
                            for(int i=1;i<=week3.size();i++){
                                newTekCalendarVOFinal.setWk3Day1Date(week3.get(0));
                                newTekCalendarVOFinal.setWk3Day2Date(week3.get(1));
                                newTekCalendarVOFinal.setWk3Day3Date(week3.get(2));
                                newTekCalendarVOFinal.setWk3Day4Date(week3.get(3));
                                newTekCalendarVOFinal.setWk3Day5Date(week3.get(4));
                                newTekCalendarVOFinal.setWk3Day6Date(week3.get(5));
                                newTekCalendarVOFinal.setWk3Day7Date(week3.get(6));
                                if (i == 1 && week3.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk3Day1(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 2 && week3.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk3Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week3.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk3Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week3.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk3Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week3.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk3Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week3.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk3Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week3.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk3Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                    if(week == 4){
                        if(newTekCalendarVOFinal.getWk4Msrp() != null && newTekCalendarVOFinal.getWk4Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk4Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk4MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk4Msrp() == null){
                            newTekCalendarVOFinal.setWk4Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk4MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        String week4Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),4,0);
                        String[] week4DayArr = week4Days.split(",");
                        String week4DayFrom = week4DayArr[0];
                        String week4DayTo = week4DayArr[1];
                        List<String> week4 = DateUtils.addDates(week4DayFrom,week4DayTo);
                        if(week4.contains(date)){
                            for(int i=1;i<=week4.size();i++){
                                newTekCalendarVOFinal.setWk4Day1Date(week4.get(0));
                                newTekCalendarVOFinal.setWk4Day2Date(week4.get(1));
                                newTekCalendarVOFinal.setWk4Day3Date(week4.get(2));
                                newTekCalendarVOFinal.setWk4Day4Date(week4.get(3));
                                newTekCalendarVOFinal.setWk4Day5Date(week4.get(4));
                                newTekCalendarVOFinal.setWk4Day6Date(week4.get(5));
                                newTekCalendarVOFinal.setWk4Day7Date(week4.get(6));
                                if (i == 1 && week4.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk4Day1(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 2 && week4.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk4Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week4.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk4Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week4.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk4Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week4.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk4Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week4.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk4Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week4.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk4Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                    if(week == 5){
                        if(newTekCalendarVOFinal.getWk5Msrp() != null && newTekCalendarVOFinal.getWk5Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk5Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk5MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk5Msrp() == null){
                            newTekCalendarVOFinal.setWk5Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk5MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        String week5Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),5,0);
                        String[] week5DayArr = week5Days.split(",");
                        String week5DayFrom = week5DayArr[0];
                        String week5DayTo = week5DayArr[1];
                        List<String> week5 = DateUtils.addDates(week5DayFrom,week5DayTo);
                        if(week5.contains(date)){
                            for(int i=1;i<=week5.size();i++){
                                newTekCalendarVOFinal.setWk5Day1Date(week5.get(0));
                                newTekCalendarVOFinal.setWk5Day2Date(week5.get(1));
                                newTekCalendarVOFinal.setWk5Day3Date(week5.get(2));
                                newTekCalendarVOFinal.setWk5Day4Date(week5.get(3));
                                newTekCalendarVOFinal.setWk5Day5Date(week5.get(4));
                                newTekCalendarVOFinal.setWk5Day6Date(week5.get(5));
                                newTekCalendarVOFinal.setWk5Day7Date(week5.get(6));
                                if (i == 1 && week5.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk5Day1(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 2 && week5.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk5Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week5.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk5Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week5.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk5Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week5.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk5Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week5.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk5Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week5.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk5Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                    if(week == 6){
                        if(newTekCalendarVOFinal.getWk6Msrp() != null && newTekCalendarVOFinal.getWk6Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk6Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk6MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk6Msrp() == null){
                            newTekCalendarVOFinal.setWk6Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk6MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        String week6Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),6,0);
                        String[] week6DayArr = week6Days.split(",");
                        String week6DayFrom = week6DayArr[0];
                        String week6DayTo = week6DayArr[1];
                        List<String> week6 = DateUtils.addDates(week6DayFrom,week6DayTo);
                        if(week6.contains(date)){
                            for(int i=1;i<=week6.size();i++){
                                newTekCalendarVOFinal.setWk6Day1Date(week6.get(0));
                                newTekCalendarVOFinal.setWk6Day2Date(week6.get(1));
                                newTekCalendarVOFinal.setWk6Day3Date(week6.get(2));
                                newTekCalendarVOFinal.setWk6Day4Date(week6.get(3));
                                newTekCalendarVOFinal.setWk6Day5Date(week6.get(4));
                                newTekCalendarVOFinal.setWk6Day6Date(week6.get(5));
                                newTekCalendarVOFinal.setWk6Day7Date(week6.get(6));
                                if (i == 1 && week6.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk6Day1(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 2 && week6.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk6Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week6.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk6Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week6.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk6Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week6.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk6Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week6.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk6Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week6.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk6Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                    if(week == 7){
                        if(newTekCalendarVOFinal.getWk7Msrp() != null && newTekCalendarVOFinal.getWk7Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk7Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk7MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk7Msrp() == null){
                            newTekCalendarVOFinal.setWk7Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk7MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        String week7Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),7,0);
                        String[] week7DayArr = week7Days.split(",");
                        String week7DayFrom = week7DayArr[0];
                        String week7DayTo = week7DayArr[1];
                        List<String> week7 = DateUtils.addDates(week7DayFrom,week7DayTo);
                        if(week7.contains(date)){
                            for(int i=1;i<=week7.size();i++){
                                newTekCalendarVOFinal.setWk7Day1Date(week7.get(0));
                                newTekCalendarVOFinal.setWk7Day2Date(week7.get(1));
                                newTekCalendarVOFinal.setWk7Day3Date(week7.get(2));
                                newTekCalendarVOFinal.setWk7Day4Date(week7.get(3));
                                newTekCalendarVOFinal.setWk7Day5Date(week7.get(4));
                                newTekCalendarVOFinal.setWk7Day6Date(week7.get(5));
                                newTekCalendarVOFinal.setWk7Day7Date(week7.get(6));
                                if (i == 1 && week7.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk7Day1(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 2 && week7.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk7Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week7.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk7Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week7.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk7Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week7.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk7Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week7.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk7Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week7.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk7Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                    if(week == 8){
                        if(newTekCalendarVOFinal.getWk8Msrp() != null && newTekCalendarVOFinal.getWk8Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk8Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk8MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk8Msrp() == null){
                            newTekCalendarVOFinal.setWk8Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk8MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        String week8Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),8,0);
                        String[] week8DayArr = week8Days.split(",");
                        String week8DayFrom = week8DayArr[0];
                        String week8DayTo = week8DayArr[1];
                        List<String> week8 = DateUtils.addDates(week8DayFrom,week8DayTo);
                        if(week8.contains(date)){
                            for(int i=1;i<=week8.size();i++){
                                newTekCalendarVOFinal.setWk8Day1Date(week8.get(0));
                                newTekCalendarVOFinal.setWk8Day2Date(week8.get(1));
                                newTekCalendarVOFinal.setWk8Day3Date(week8.get(2));
                                newTekCalendarVOFinal.setWk8Day4Date(week8.get(3));
                                newTekCalendarVOFinal.setWk8Day5Date(week8.get(4));
                                newTekCalendarVOFinal.setWk8Day6Date(week8.get(5));
                                newTekCalendarVOFinal.setWk8Day7Date(week8.get(6));
                                if (i == 1 && week8.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk8Day1(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 2 && week8.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk8Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week8.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk8Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week8.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk8Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week8.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk8Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week8.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk8Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week8.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk8Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                    if(week == 9){
                        if(newTekCalendarVOFinal.getWk9Msrp() != null && newTekCalendarVOFinal.getWk9Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk9Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk9MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk9Msrp() == null){
                            newTekCalendarVOFinal.setWk9Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk9MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        String week9Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),9,0);
                        String[] week9DayArr = week9Days.split(",");
                        String week9DayFrom = week9DayArr[0];
                        String week9DayTo = week9DayArr[1];
                        List<String> week9 = DateUtils.addDates(week9DayFrom,week9DayTo);
                        if(week9.contains(date)){
                            for(int i=1;i<=week9.size();i++){
                                newTekCalendarVOFinal.setWk9Day1Date(week9.get(0));
                                newTekCalendarVOFinal.setWk9Day2Date(week9.get(1));
                                newTekCalendarVOFinal.setWk9Day3Date(week9.get(2));
                                newTekCalendarVOFinal.setWk9Day4Date(week9.get(3));
                                newTekCalendarVOFinal.setWk9Day5Date(week9.get(4));
                                newTekCalendarVOFinal.setWk9Day6Date(week9.get(5));
                                newTekCalendarVOFinal.setWk9Day7Date(week9.get(6));
                                if (i == 1 && week9.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk9Day1(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 2 && week9.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk9Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week9.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk9Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week9.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk9Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week9.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk9Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week9.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk9Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week9.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk9Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                    if(week == 10){
                        if(newTekCalendarVOFinal.getWk10Msrp() != null && newTekCalendarVOFinal.getWk10Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk10Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk10MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk10Msrp() == null){
                            newTekCalendarVOFinal.setWk10Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk10MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        String week10Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),10,0);
                        String[] week10DayArr = week10Days.split(",");
                        String week10DayFrom = week10DayArr[0];
                        String week10DayTo = week10DayArr[1];
                        List<String> week10 = DateUtils.addDates(week10DayFrom,week10DayTo);
                        if(week10.contains(date)){
                            for(int i=1;i<=week10.size();i++){
                                newTekCalendarVOFinal.setWk10Day1Date(week10.get(0));
                                newTekCalendarVOFinal.setWk10Day2Date(week10.get(1));
                                newTekCalendarVOFinal.setWk10Day3Date(week10.get(2));
                                newTekCalendarVOFinal.setWk10Day4Date(week10.get(3));
                                newTekCalendarVOFinal.setWk10Day5Date(week10.get(4));
                                newTekCalendarVOFinal.setWk10Day6Date(week10.get(5));
                                newTekCalendarVOFinal.setWk10Day7Date(week10.get(6));
                                if (i == 1 && week10.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk10Day1(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 2 && week10.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk10Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week10.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk10Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week10.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk10Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week10.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk10Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week10.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk10Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week10.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk10Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                    if(week == 11){
                        if(newTekCalendarVOFinal.getWk11Msrp() != null && newTekCalendarVOFinal.getWk11Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk11Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk11MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk11Msrp() == null){
                            newTekCalendarVOFinal.setWk11Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk11MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        String week11Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),11,0);
                        String[] week11DayArr = week11Days.split(",");
                        String week11DayFrom = week11DayArr[0];
                        String week11DayTo = week11DayArr[1];
                        List<String> week11 = DateUtils.addDates(week11DayFrom,week11DayTo);
                        if(week11.contains(date)){
                            for(int i=1;i<=week11.size();i++){
                                newTekCalendarVOFinal.setWk11Day1Date(week11.get(0));
                                newTekCalendarVOFinal.setWk11Day2Date(week11.get(1));
                                newTekCalendarVOFinal.setWk11Day3Date(week11.get(2));
                                newTekCalendarVOFinal.setWk11Day4Date(week11.get(3));
                                newTekCalendarVOFinal.setWk11Day5Date(week11.get(4));
                                newTekCalendarVOFinal.setWk11Day6Date(week11.get(5));
                                newTekCalendarVOFinal.setWk11Day7Date(week11.get(6));
                                if (i == 1 && week11.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk11Day1(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 2 && week11.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk11Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week11.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk11Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week11.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk11Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week11.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk11Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week11.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk11Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week11.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk11Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                    if(week == 12){
                        if(newTekCalendarVOFinal.getWk12Msrp() != null && newTekCalendarVOFinal.getWk12Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk12Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk12MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk12Msrp() == null){
                            newTekCalendarVOFinal.setWk12Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk12MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        String week12Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),12,0);
                        String[] week12DayArr = week12Days.split(",");
                        String week12DayFrom = week12DayArr[0];
                        String week12DayTo = week12DayArr[1];
                        List<String> week12 = DateUtils.addDates(week12DayFrom,week12DayTo);
                        if(week12.contains(date)){
                            for(int i=1;i<=week12.size();i++){
                                newTekCalendarVOFinal.setWk12Day1Date(week12.get(0));
                                newTekCalendarVOFinal.setWk12Day2Date(week12.get(1));
                                newTekCalendarVOFinal.setWk12Day3Date(week12.get(2));
                                newTekCalendarVOFinal.setWk12Day4Date(week12.get(3));
                                newTekCalendarVOFinal.setWk12Day5Date(week12.get(4));
                                newTekCalendarVOFinal.setWk12Day6Date(week12.get(5));
                                newTekCalendarVOFinal.setWk12Day7Date(week12.get(6));
                                if (i == 1 && week12.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk12Day1(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 2 && week12.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk12Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week12.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk12Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week12.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk12Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week12.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk12Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week12.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk12Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week12.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk12Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                    if(week == 13){
                        if(newTekCalendarVOFinal.getWk13Msrp() != null && newTekCalendarVOFinal.getWk13Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk13Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk13MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk13Msrp() == null){
                            newTekCalendarVOFinal.setWk13Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk13MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        String week13Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),13,0);
                        String[] week13DayArr = week13Days.split(",");
                        String week13DayFrom = week13DayArr[0];
                        String week13DayTo = week13DayArr[1];
                        List<String> week13 = DateUtils.addDates(week13DayFrom,week13DayTo);
                        if(week13.contains(date)){
                            for(int i=1;i<=week13.size();i++){
                                newTekCalendarVOFinal.setWk13Day1Date(week13.get(0));
                                newTekCalendarVOFinal.setWk13Day2Date(week13.get(1));
                                newTekCalendarVOFinal.setWk13Day3Date(week13.get(2));
                                newTekCalendarVOFinal.setWk13Day4Date(week13.get(3));
                                newTekCalendarVOFinal.setWk13Day5Date(week13.get(4));
                                newTekCalendarVOFinal.setWk13Day6Date(week13.get(5));
                                newTekCalendarVOFinal.setWk13Day7Date(week13.get(6));
                                if (i == 1 && week13.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk13Day1(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 2 && week13.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk13Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week13.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk13Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week13.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk13Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week13.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk13Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week13.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk13Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week13.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk13Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                    if(week == 14){
                        if(newTekCalendarVOFinal.getWk14Msrp() != null && newTekCalendarVOFinal.getWk14Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk14Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk14MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk14Msrp() == null){
                            newTekCalendarVOFinal.setWk14Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk14MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        String week14Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),14,0);
                        String[] week14DayArr = week14Days.split(",");
                        String week14DayFrom = week14DayArr[0];
                        String week14DayTo = week14DayArr[1];
                        List<String> week14 = DateUtils.addDates(week14DayFrom,week14DayTo);
                        if(week14.contains(date)){
                            for(int i=1;i<=week14.size();i++){
                                newTekCalendarVOFinal.setWk14Day1Date(week14.get(0));
                                newTekCalendarVOFinal.setWk14Day2Date(week14.get(1));
                                newTekCalendarVOFinal.setWk14Day3Date(week14.get(2));
                                newTekCalendarVOFinal.setWk14Day4Date(week14.get(3));
                                newTekCalendarVOFinal.setWk14Day5Date(week14.get(4));
                                newTekCalendarVOFinal.setWk14Day6Date(week14.get(5));
                                newTekCalendarVOFinal.setWk14Day7Date(week14.get(6));
                                if (i == 1 && week14.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk14Day1(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 2 && week14.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk14Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week14.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk14Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week14.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk14Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week14.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk14Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week14.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk14Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week14.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk14Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                    if(week == 15){
                        if(newTekCalendarVOFinal.getWk15Msrp() != null && newTekCalendarVOFinal.getWk15Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk15Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk15MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk15Msrp() == null){
                            newTekCalendarVOFinal.setWk15Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk15MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        String week15Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),15,0);
                        String[] week15DayArr = week15Days.split(",");
                        String week15DayFrom = week15DayArr[0];
                        String week15DayTo = week15DayArr[1];
                        List<String> week15 = DateUtils.addDates(week15DayFrom,week15DayTo);
                        if(week15.contains(date)){
                            for(int i=1;i<=week15.size();i++){
                                newTekCalendarVOFinal.setWk15Day1Date(week15.get(0));
                                newTekCalendarVOFinal.setWk15Day2Date(week15.get(1));
                                newTekCalendarVOFinal.setWk15Day3Date(week15.get(2));
                                newTekCalendarVOFinal.setWk15Day4Date(week15.get(3));
                                newTekCalendarVOFinal.setWk15Day5Date(week15.get(4));
                                newTekCalendarVOFinal.setWk15Day6Date(week15.get(5));
                                newTekCalendarVOFinal.setWk15Day7Date(week15.get(6));
                                if (i == 1 && week15.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk15Day1(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 2 && week15.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk15Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week15.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk15Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week15.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk15Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week15.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk15Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week15.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk15Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week15.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk15Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                    if(week == 16){
                        if(newTekCalendarVOFinal.getWk16Msrp() != null && newTekCalendarVOFinal.getWk16Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk16Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk16MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk16Msrp() == null){
                            newTekCalendarVOFinal.setWk16Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk16MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        String week16Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),16,0);
                        String[] week16DayArr = week16Days.split(",");
                        String week16DayFrom = week16DayArr[0];
                        String week16DayTo = week16DayArr[1];
                        List<String> week16 = DateUtils.addDates(week16DayFrom,week16DayTo);
                        if(week16.contains(date)){
                            for(int i=1;i<=week16.size();i++){
                                newTekCalendarVOFinal.setWk16Day1Date(week16.get(0));
                                newTekCalendarVOFinal.setWk16Day2Date(week16.get(1));
                                newTekCalendarVOFinal.setWk16Day3Date(week16.get(2));
                                newTekCalendarVOFinal.setWk16Day4Date(week16.get(3));
                                newTekCalendarVOFinal.setWk16Day5Date(week16.get(4));
                                newTekCalendarVOFinal.setWk16Day6Date(week16.get(5));
                                newTekCalendarVOFinal.setWk16Day7Date(week16.get(6));
                                if (i == 1 && week16.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk16Day1(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 2 && week16.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk16Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week16.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk16Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week16.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk16Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week16.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk16Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week16.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk16Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week16.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk16Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                    if(week == 17){
                        if(newTekCalendarVOFinal.getWk17Msrp() != null && newTekCalendarVOFinal.getWk17Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk17Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk17MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk17Msrp() == null){
                            newTekCalendarVOFinal.setWk17Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk17MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        String week17Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),17,0);
                        String[] week17DayArr = week17Days.split(",");
                        String week17DayFrom = week17DayArr[0];
                        String week17DayTo = week17DayArr[1];
                        List<String> week17 = DateUtils.addDates(week17DayFrom,week17DayTo);
                        if(week17.contains(date)){
                            for(int i=1;i<=week17.size();i++){
                                newTekCalendarVOFinal.setWk17Day1Date(week17.get(0));
                                newTekCalendarVOFinal.setWk17Day2Date(week17.get(1));
                                newTekCalendarVOFinal.setWk17Day3Date(week17.get(2));
                                newTekCalendarVOFinal.setWk17Day4Date(week17.get(3));
                                newTekCalendarVOFinal.setWk17Day5Date(week17.get(4));
                                newTekCalendarVOFinal.setWk17Day6Date(week17.get(5));
                                newTekCalendarVOFinal.setWk17Day7Date(week17.get(6));
                                if (i == 1 && week17.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk17Day1(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 2 && week17.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk17Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week17.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk17Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week17.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk17Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week17.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk17Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week17.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk17Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week17.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk17Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                    if(week == 18){
                        if(newTekCalendarVOFinal.getWk18Msrp() != null && newTekCalendarVOFinal.getWk18Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk18Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk18MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk18Msrp() == null){
                            newTekCalendarVOFinal.setWk18Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk18MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        String week18Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),18,0);
                        String[] week18DayArr = week18Days.split(",");
                        String week18DayFrom = week18DayArr[0];
                        String week18DayTo = week18DayArr[1];
                        List<String> week18 = DateUtils.addDates(week18DayFrom,week18DayTo);
                        if(week18.contains(date)){
                            for(int i=1;i<=week18.size();i++){
                                newTekCalendarVOFinal.setWk18Day1Date(week18.get(0));
                                newTekCalendarVOFinal.setWk18Day2Date(week18.get(1));
                                newTekCalendarVOFinal.setWk18Day3Date(week18.get(2));
                                newTekCalendarVOFinal.setWk18Day4Date(week18.get(3));
                                newTekCalendarVOFinal.setWk18Day5Date(week18.get(4));
                                newTekCalendarVOFinal.setWk18Day6Date(week18.get(5));
                                newTekCalendarVOFinal.setWk18Day7Date(week18.get(6));
                                if (i == 1 && week18.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk18Day1(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 2 && week18.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk18Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week18.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk18Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week18.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk18Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week18.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk18Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week18.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk18Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week18.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk18Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                    if(week == 19){
                        if(newTekCalendarVOFinal.getWk19Msrp() != null && newTekCalendarVOFinal.getWk19Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk19Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk19MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk19Msrp() == null){
                            newTekCalendarVOFinal.setWk19Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk19MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        String week19Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),19,0);
                        String[] week19DayArr = week19Days.split(",");
                        String week19DayFrom = week19DayArr[0];
                        String week19DayTo = week19DayArr[1];
                        List<String> week19 = DateUtils.addDates(week19DayFrom,week19DayTo);
                        if(week19.contains(date)){
                            for(int i=1;i<=week19.size();i++){
                                newTekCalendarVOFinal.setWk19Day1Date(week19.get(0));
                                newTekCalendarVOFinal.setWk19Day2Date(week19.get(1));
                                newTekCalendarVOFinal.setWk19Day3Date(week19.get(2));
                                newTekCalendarVOFinal.setWk19Day4Date(week19.get(3));
                                newTekCalendarVOFinal.setWk19Day5Date(week19.get(4));
                                newTekCalendarVOFinal.setWk19Day6Date(week19.get(5));
                                newTekCalendarVOFinal.setWk19Day7Date(week19.get(6));
                                if (i == 1 && week19.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk19Day1(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 2 && week19.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk19Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week19.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk19Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week19.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk19Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week19.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk19Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week19.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk19Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week19.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk19Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                    if(week == 20){
                        if(newTekCalendarVOFinal.getWk20Msrp() != null && newTekCalendarVOFinal.getWk20Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk20Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk20MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk20Msrp() == null){
                            newTekCalendarVOFinal.setWk20Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk20MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        String week20Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),20,0);
                        String[] week20DayArr = week20Days.split(",");
                        String week20DayFrom = week20DayArr[0];
                        String week20DayTo = week20DayArr[1];
                        List<String> week20 = DateUtils.addDates(week20DayFrom,week20DayTo);
                        if(week20.contains(date)){
                            for(int i=1;i<=week20.size();i++){
                                newTekCalendarVOFinal.setWk20Day1Date(week20.get(0));
                                newTekCalendarVOFinal.setWk20Day2Date(week20.get(1));
                                newTekCalendarVOFinal.setWk20Day3Date(week20.get(2));
                                newTekCalendarVOFinal.setWk20Day4Date(week20.get(3));
                                newTekCalendarVOFinal.setWk20Day5Date(week20.get(4));
                                newTekCalendarVOFinal.setWk20Day6Date(week20.get(5));
                                newTekCalendarVOFinal.setWk20Day7Date(week20.get(6));
                                if (i == 1 && week20.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk20Day1(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 2 && week20.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk20Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week20.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk20Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week20.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk20Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week20.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk20Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week20.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk20Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week20.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk20Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                    if(week == 21){
                        if(newTekCalendarVOFinal.getWk21Msrp() != null && newTekCalendarVOFinal.getWk21Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk21Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk21MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk21Msrp() == null){
                            newTekCalendarVOFinal.setWk21Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk21MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        String week21Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),21,0);
                        String[] week21DayArr = week21Days.split(",");
                        String week21DayFrom = week21DayArr[0];
                        String week21DayTo = week21DayArr[1];
                        List<String> week21 = DateUtils.addDates(week21DayFrom,week21DayTo);
                        if(week21.contains(date)){
                            for(int i=1;i<=week21.size();i++){
                                newTekCalendarVOFinal.setWk21Day1Date(week21.get(0));
                                newTekCalendarVOFinal.setWk21Day2Date(week21.get(1));
                                newTekCalendarVOFinal.setWk21Day3Date(week21.get(2));
                                newTekCalendarVOFinal.setWk21Day4Date(week21.get(3));
                                newTekCalendarVOFinal.setWk21Day5Date(week21.get(4));
                                newTekCalendarVOFinal.setWk21Day6Date(week21.get(5));
                                newTekCalendarVOFinal.setWk21Day7Date(week21.get(6));
                                if (i == 1 && week21.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk21Day1(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 2 && week21.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk21Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week21.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk21Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week21.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk21Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week21.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk21Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week21.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk21Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week21.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk21Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                    if(week == 22){
                        if(newTekCalendarVOFinal.getWk22Msrp() != null && newTekCalendarVOFinal.getWk22Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk22Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk22MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk22Msrp() == null){
                            newTekCalendarVOFinal.setWk22Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk22MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        String week22Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),22,0);
                        String[] week22DayArr = week22Days.split(",");
                        String week22DayFrom = week22DayArr[0];
                        String week22DayTo = week22DayArr[1];
                        List<String> week22 = DateUtils.addDates(week22DayFrom,week22DayTo);
                        if(week22.contains(date)){
                            for(int i=1;i<=week22.size();i++){
                                newTekCalendarVOFinal.setWk22Day1Date(week22.get(0));
                                newTekCalendarVOFinal.setWk22Day2Date(week22.get(1));
                                newTekCalendarVOFinal.setWk22Day3Date(week22.get(2));
                                newTekCalendarVOFinal.setWk22Day4Date(week22.get(3));
                                newTekCalendarVOFinal.setWk22Day5Date(week22.get(4));
                                newTekCalendarVOFinal.setWk22Day6Date(week22.get(5));
                                newTekCalendarVOFinal.setWk22Day7Date(week22.get(6));
                                if (i == 1 && week22.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk22Day1(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 2 && week22.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk22Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week22.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk22Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week22.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk22Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week22.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk22Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week22.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk22Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week22.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk22Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                    if(week == 23){
                        if(newTekCalendarVOFinal.getWk23Msrp() != null && newTekCalendarVOFinal.getWk23Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk23Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk23MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk23Msrp() == null){
                            newTekCalendarVOFinal.setWk23Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk23MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        String week23Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),23,0);
                        String[] week23DayArr = week23Days.split(",");
                        String week23DayFrom = week23DayArr[0];
                        String week23DayTo = week23DayArr[1];
                        List<String> week23 = DateUtils.addDates(week23DayFrom,week23DayTo);
                        if(week23.contains(date)){
                            for(int i=1;i<=week23.size();i++){
                                newTekCalendarVOFinal.setWk23Day1Date(week23.get(0));
                                newTekCalendarVOFinal.setWk23Day2Date(week23.get(1));
                                newTekCalendarVOFinal.setWk23Day3Date(week23.get(2));
                                newTekCalendarVOFinal.setWk23Day4Date(week23.get(3));
                                newTekCalendarVOFinal.setWk23Day5Date(week23.get(4));
                                newTekCalendarVOFinal.setWk23Day6Date(week23.get(5));
                                newTekCalendarVOFinal.setWk23Day7Date(week23.get(6));
                                if (i == 1 && week23.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk23Day1(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 2 && week23.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk23Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week23.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk23Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week23.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk23Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week23.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk23Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week23.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk23Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week23.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk23Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                    if(week == 24){
                        if(newTekCalendarVOFinal.getWk24Msrp() != null && newTekCalendarVOFinal.getWk24Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk24Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk24MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk24Msrp() == null){
                            newTekCalendarVOFinal.setWk24Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk24MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        String week24Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),24,0);
                        String[] week24DayArr = week24Days.split(",");
                        String week24DayFrom = week24DayArr[0];
                        String week24DayTo = week24DayArr[1];
                        List<String> week24 = DateUtils.addDates(week24DayFrom,week24DayTo);
                        if(week24.contains(date)){
                            for(int i=1;i<=week24.size();i++){
                                if (i == 1 && week24.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk24Day1Date(week24.get(0));
                                    newTekCalendarVOFinal.setWk24Day2Date(week24.get(1));
                                    newTekCalendarVOFinal.setWk24Day3Date(week24.get(2));
                                    newTekCalendarVOFinal.setWk24Day4Date(week24.get(3));
                                    newTekCalendarVOFinal.setWk24Day5Date(week24.get(4));
                                    newTekCalendarVOFinal.setWk24Day6Date(week24.get(5));
                                    newTekCalendarVOFinal.setWk24Day7Date(week24.get(6));
                                    newTekCalendarVOFinal.setWk24Day1(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 2 && week24.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk24Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week24.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk24Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week24.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk24Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week24.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk24Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week24.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk24Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week24.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk24Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                    if(week == 25){
                        if(newTekCalendarVOFinal.getWk25Msrp() != null && newTekCalendarVOFinal.getWk25Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk25Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk25MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk25Msrp() == null){
                            newTekCalendarVOFinal.setWk25Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk25MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        String week25Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),25,0);
                        String[] week25DayArr = week25Days.split(",");
                        String week25DayFrom = week25DayArr[0];
                        String week25DayTo = week25DayArr[1];
                        List<String> week25 = DateUtils.addDates(week25DayFrom,week25DayTo);
                        if(week25.contains(date)){
                            for(int i=1;i<=week25.size();i++){
                                if (i == 1 && week25.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk25Day1Date(week25.get(0));
                                    newTekCalendarVOFinal.setWk25Day2Date(week25.get(1));
                                    newTekCalendarVOFinal.setWk25Day3Date(week25.get(2));
                                    newTekCalendarVOFinal.setWk25Day4Date(week25.get(3));
                                    newTekCalendarVOFinal.setWk25Day5Date(week25.get(4));
                                    newTekCalendarVOFinal.setWk25Day6Date(week25.get(5));
                                    newTekCalendarVOFinal.setWk25Day7Date(week25.get(6));
                                    newTekCalendarVOFinal.setWk25Day1(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 2 && week25.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk25Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week25.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk25Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week25.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk25Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week25.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk25Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week25.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk25Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week25.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk25Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                    if(week == 26){
                        if(newTekCalendarVOFinal.getWk26Msrp() != null && newTekCalendarVOFinal.getWk26Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk26Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk26MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk26Msrp() == null){
                            newTekCalendarVOFinal.setWk26Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk26MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        String week26Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),26,0);
                        String[] week26DayArr = week26Days.split(",");
                        String week26DayFrom = week26DayArr[0];
                        String week26DayTo = week26DayArr[1];
                        List<String> week26 = DateUtils.addDates(week26DayFrom,week26DayTo);
                        if(week26.contains(date)){
                            for(int i=1;i<=week26.size();i++){
                                if (i == 1 && week26.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk26Day1Date(week26.get(0));
                                    newTekCalendarVOFinal.setWk26Day2Date(week26.get(1));
                                    newTekCalendarVOFinal.setWk26Day3Date(week26.get(2));
                                    newTekCalendarVOFinal.setWk26Day4Date(week26.get(3));
                                    newTekCalendarVOFinal.setWk26Day5Date(week26.get(4));
                                    newTekCalendarVOFinal.setWk26Day6Date(week26.get(5));
                                    newTekCalendarVOFinal.setWk26Day7Date(week26.get(6));
                                    newTekCalendarVOFinal.setWk26Day1(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 2 && week26.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk26Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week26.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk26Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week26.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk26Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week26.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk26Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week26.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk26Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week26.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk26Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                    if(week == 27){
                        if(newTekCalendarVOFinal.getWk27Msrp() != null && newTekCalendarVOFinal.getWk27Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk27Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk27MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk27Msrp() == null){
                            newTekCalendarVOFinal.setWk27Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk27MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        String week27Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),27,0);
                        String[] week27DayArr = week27Days.split(",");
                        String week27DayFrom = week27DayArr[0];
                        String week27DayTo = week27DayArr[1];
                        List<String> week27 = DateUtils.addDates(week27DayFrom,week27DayTo);
                        if(week27.contains(date)){
                            for(int i=1;i<=week27.size();i++){
                                newTekCalendarVOFinal.setWk27Day1Date(week27.get(0));
                                newTekCalendarVOFinal.setWk27Day2Date(week27.get(1));
                                newTekCalendarVOFinal.setWk27Day3Date(week27.get(2));
                                newTekCalendarVOFinal.setWk27Day4Date(week27.get(3));
                                newTekCalendarVOFinal.setWk27Day5Date(week27.get(4));
                                newTekCalendarVOFinal.setWk27Day6Date(week27.get(5));
                                newTekCalendarVOFinal.setWk27Day7Date(week27.get(6));
                                if (i == 1 && week27.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk27Day1(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 2 && week27.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk27Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week27.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk27Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week27.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk27Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week27.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk27Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week27.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk27Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week27.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk27Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                    if(week == 28){
                        if(newTekCalendarVOFinal.getWk28Msrp() != null && newTekCalendarVOFinal.getWk28Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk28Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk28MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk28Msrp() == null){
                            newTekCalendarVOFinal.setWk28Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk28MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        String week28Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),28,0);
                        String[] week28DayArr = week28Days.split(",");
                        String week28DayFrom = week28DayArr[0];
                        String week28DayTo = week28DayArr[1];
                        List<String> week28 = DateUtils.addDates(week28DayFrom,week28DayTo);
                        if(week28.contains(date)){
                            for(int i=1;i<=week28.size();i++){
                                newTekCalendarVOFinal.setWk28Day1Date(week28.get(0));
                                newTekCalendarVOFinal.setWk28Day2Date(week28.get(1));
                                newTekCalendarVOFinal.setWk28Day3Date(week28.get(2));
                                newTekCalendarVOFinal.setWk28Day4Date(week28.get(3));
                                newTekCalendarVOFinal.setWk28Day5Date(week28.get(4));
                                newTekCalendarVOFinal.setWk28Day6Date(week28.get(5));
                                newTekCalendarVOFinal.setWk28Day7Date(week28.get(6));
                                if (i == 1 && week28.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk28Day1(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 2 && week28.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk28Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week28.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk28Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week28.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk28Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week28.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk28Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week28.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk28Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week28.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk28Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                    if(week == 29){
                        if(newTekCalendarVOFinal.getWk29Msrp() != null && newTekCalendarVOFinal.getWk29Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk29Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk29MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk29Msrp() == null){
                            newTekCalendarVOFinal.setWk29Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk29MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        //获取周内日期，第一天-第七天，以周一开始计算
                        String week29Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),29,0);
                        String[] week29DayArr = week29Days.split(",");
                        String week29DayFrom = week29DayArr[0];
                        String week29DayTo = week29DayArr[1];
                        //获取周内所有日期，从第一天到第七天
                        List<String> week29 = DateUtils.addDates(week29DayFrom,week29DayTo);
                        if(week29.contains(date)){
                            for(int i=1;i<=week29.size();i++){
                                newTekCalendarVOFinal.setWk29Day1Date(week29.get(0));
                                newTekCalendarVOFinal.setWk29Day2Date(week29.get(1));
                                newTekCalendarVOFinal.setWk29Day3Date(week29.get(2));
                                newTekCalendarVOFinal.setWk29Day4Date(week29.get(3));
                                newTekCalendarVOFinal.setWk29Day5Date(week29.get(4));
                                newTekCalendarVOFinal.setWk29Day6Date(week29.get(5));
                                newTekCalendarVOFinal.setWk29Day7Date(week29.get(6));
                                if (i == 1 && week29.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk29Day1(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 2 && week29.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk29Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week29.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk29Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week29.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk29Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week29.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk29Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week29.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk29Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week29.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk29Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                    if(week == 30){
                        if(newTekCalendarVOFinal.getWk30Msrp() != null && newTekCalendarVOFinal.getWk30Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk30Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk30MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk30Msrp() == null){
                            newTekCalendarVOFinal.setWk30Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk30MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        String week30Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),30,0);
                        String[] week30DayArr = week30Days.split(",");
                        String week30DayFrom = week30DayArr[0];
                        String week30DayTo = week30DayArr[1];
                        List<String> week30 = DateUtils.addDates(week30DayFrom,week30DayTo);
                        if(week30.contains(date)){
                            for(int i=1;i<=week30.size();i++){
                                newTekCalendarVOFinal.setWk30Day1Date(week30.get(0));
                                newTekCalendarVOFinal.setWk30Day2Date(week30.get(1));
                                newTekCalendarVOFinal.setWk30Day3Date(week30.get(2));
                                newTekCalendarVOFinal.setWk30Day4Date(week30.get(3));
                                newTekCalendarVOFinal.setWk30Day5Date(week30.get(4));
                                newTekCalendarVOFinal.setWk30Day6Date(week30.get(5));
                                newTekCalendarVOFinal.setWk30Day7Date(week30.get(6));
                                if (i == 1 && week30.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk30Day1(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 2 && week30.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk30Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week30.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk30Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week30.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk30Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week30.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk30Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week30.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk30Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week30.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk30Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                    if(week == 31){
                        if(newTekCalendarVOFinal.getWk31Msrp() != null && newTekCalendarVOFinal.getWk31Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk31Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk31MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk31Msrp() == null){
                            newTekCalendarVOFinal.setWk31Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk31MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        String week31Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),31,0);
                        String[] week31DayArr = week31Days.split(",");
                        String week31DayFrom = week31DayArr[0];
                        String week31DayTo = week31DayArr[1];
                        List<String> week31 = DateUtils.addDates(week31DayFrom,week31DayTo);
                        if(week31.contains(date)){
                            for(int i=1;i<=week31.size();i++){
                                newTekCalendarVOFinal.setWk31Day1Date(week31.get(0));
                                newTekCalendarVOFinal.setWk31Day2Date(week31.get(1));
                                newTekCalendarVOFinal.setWk31Day3Date(week31.get(2));
                                newTekCalendarVOFinal.setWk31Day4Date(week31.get(3));
                                newTekCalendarVOFinal.setWk31Day5Date(week31.get(4));
                                newTekCalendarVOFinal.setWk31Day6Date(week31.get(5));
                                newTekCalendarVOFinal.setWk31Day7Date(week31.get(6));
                                if (i == 1 && week31.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk31Day1(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 2 && week31.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk31Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week31.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk31Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week31.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk31Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week31.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk31Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week31.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk31Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week31.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk31Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                    if(week == 32){
                        if(newTekCalendarVOFinal.getWk32Msrp() != null && newTekCalendarVOFinal.getWk32Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk32Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk32MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk32Msrp() == null){
                            newTekCalendarVOFinal.setWk32Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk32MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        String week32Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),32,0);
                        String[] week32DayArr = week32Days.split(",");
                        String week32DayFrom = week32DayArr[0];
                        String week32DayTo = week32DayArr[1];
                        List<String> week32 = DateUtils.addDates(week32DayFrom,week32DayTo);
                        if(week32.contains(date)){
                            for(int i=1;i<=week32.size();i++){
                                newTekCalendarVOFinal.setWk32Day1Date(week32.get(0));
                                newTekCalendarVOFinal.setWk32Day2Date(week32.get(1));
                                newTekCalendarVOFinal.setWk32Day3Date(week32.get(2));
                                newTekCalendarVOFinal.setWk32Day4Date(week32.get(3));
                                newTekCalendarVOFinal.setWk32Day5Date(week32.get(4));
                                newTekCalendarVOFinal.setWk32Day6Date(week32.get(5));
                                newTekCalendarVOFinal.setWk32Day7Date(week32.get(6));
                                if (i == 1 && week32.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk32Day1(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 2 && week32.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk32Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week32.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk32Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week32.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk32Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week32.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk32Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week32.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk32Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week32.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk32Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                    if(week == 33){
                        if(newTekCalendarVOFinal.getWk33Msrp() != null && newTekCalendarVOFinal.getWk33Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk33Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk33MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk33Msrp() == null){
                            newTekCalendarVOFinal.setWk33Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk33MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        String week33Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),33,0);
                        String[] week33DayArr = week33Days.split(",");
                        String week33DayFrom = week33DayArr[0];
                        String week33DayTo = week33DayArr[1];
                        List<String> week33 = DateUtils.addDates(week33DayFrom,week33DayTo);
                        if(week33.contains(date)){
                            for(int i=1;i<=week33.size();i++){
                                newTekCalendarVOFinal.setWk33Day1Date(week33.get(0));
                                newTekCalendarVOFinal.setWk33Day2Date(week33.get(1));
                                newTekCalendarVOFinal.setWk33Day3Date(week33.get(2));
                                newTekCalendarVOFinal.setWk33Day4Date(week33.get(3));
                                newTekCalendarVOFinal.setWk33Day5Date(week33.get(4));
                                newTekCalendarVOFinal.setWk33Day6Date(week33.get(5));
                                newTekCalendarVOFinal.setWk33Day7Date(week33.get(6));
                                if (i == 1 && week33.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk33Day1(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 2 && week33.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk33Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week33.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk33Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week33.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk33Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week33.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk33Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week33.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk33Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week33.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk33Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                    if(week == 34){
                        if(newTekCalendarVOFinal.getWk34Msrp() != null && newTekCalendarVOFinal.getWk34Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk34Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk34MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk34Msrp() == null){
                            newTekCalendarVOFinal.setWk34Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk34MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        String week34Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),34,0);
                        String[] week34DayArr = week34Days.split(",");
                        String week34DayFrom = week34DayArr[0];
                        String week34DayTo = week34DayArr[1];
                        List<String> week34 = DateUtils.addDates(week34DayFrom,week34DayTo);
                        if(week34.contains(date)){
                            for(int i=1;i<=week34.size();i++){
                                newTekCalendarVOFinal.setWk34Day1Date(week34.get(0));
                                newTekCalendarVOFinal.setWk34Day2Date(week34.get(1));
                                newTekCalendarVOFinal.setWk34Day3Date(week34.get(2));
                                newTekCalendarVOFinal.setWk34Day4Date(week34.get(3));
                                newTekCalendarVOFinal.setWk34Day5Date(week34.get(4));
                                newTekCalendarVOFinal.setWk34Day6Date(week34.get(5));
                                newTekCalendarVOFinal.setWk34Day7Date(week34.get(6));
                                if (i == 1 && week34.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk34Day1(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 2 && week34.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk34Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week34.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk34Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week34.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk34Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week34.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk34Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week34.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk34Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week34.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk34Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                    if(week == 35){
                        if(newTekCalendarVOFinal.getWk35Msrp() != null && newTekCalendarVOFinal.getWk35Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk35Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk35MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk35Msrp() == null){
                            newTekCalendarVOFinal.setWk35Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk35MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        String week35Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),35,0);
                        String[] week35DayArr = week35Days.split(",");
                        String week35DayFrom = week35DayArr[0];
                        String week35DayTo = week35DayArr[1];
                        List<String> week35 = DateUtils.addDates(week35DayFrom,week35DayTo);
                        if(week35.contains(date)){
                            for(int i=1;i<=week35.size();i++){
                                newTekCalendarVOFinal.setWk35Day1Date(week35.get(0));
                                newTekCalendarVOFinal.setWk35Day2Date(week35.get(1));
                                newTekCalendarVOFinal.setWk35Day3Date(week35.get(2));
                                newTekCalendarVOFinal.setWk35Day4Date(week35.get(3));
                                newTekCalendarVOFinal.setWk35Day5Date(week35.get(4));
                                newTekCalendarVOFinal.setWk35Day6Date(week35.get(5));
                                newTekCalendarVOFinal.setWk35Day7Date(week35.get(6));
                                if (i == 1 && week35.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk35Day1(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 2 && week35.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk35Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week35.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk35Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week35.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk35Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week35.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk35Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week35.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk35Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week35.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk35Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                    if(week == 36){
                        if(newTekCalendarVOFinal.getWk36Msrp() != null && newTekCalendarVOFinal.getWk36Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk36Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk36MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk36Msrp() == null){
                            newTekCalendarVOFinal.setWk36Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk36MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        String week36Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),36,0);
                        String[] week36DayArr = week36Days.split(",");
                        String week36DayFrom = week36DayArr[0];
                        String week36DayTo = week36DayArr[1];
                        List<String> week36 = DateUtils.addDates(week36DayFrom,week36DayTo);
                        if(week36.contains(date)){
                            for(int i=1;i<=week36.size();i++){
                                newTekCalendarVOFinal.setWk36Day1Date(week36.get(0));
                                newTekCalendarVOFinal.setWk36Day2Date(week36.get(1));
                                newTekCalendarVOFinal.setWk36Day3Date(week36.get(2));
                                newTekCalendarVOFinal.setWk36Day4Date(week36.get(3));
                                newTekCalendarVOFinal.setWk36Day5Date(week36.get(4));
                                newTekCalendarVOFinal.setWk36Day6Date(week36.get(5));
                                newTekCalendarVOFinal.setWk36Day7Date(week36.get(6));
                                if (i == 1 && week36.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk36Day1(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 2 && week36.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk36Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week36.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk36Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week36.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk36Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week36.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk36Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week36.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk36Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week36.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk36Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                    if(week == 37){
                        if(newTekCalendarVOFinal.getWk37Msrp() != null && newTekCalendarVOFinal.getWk37Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk37Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk37MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk37Msrp() == null){
                            newTekCalendarVOFinal.setWk37Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk37MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        String week37Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),37,0);
                        String[] week37DayArr = week37Days.split(",");
                        String week37DayFrom = week37DayArr[0];
                        String week37DayTo = week37DayArr[1];
                        List<String> week37 = DateUtils.addDates(week37DayFrom,week37DayTo);
                        if(week37.contains(date)){
                            for(int i=1;i<=week37.size();i++){
                                newTekCalendarVOFinal.setWk37Day1Date(week37.get(0));
                                newTekCalendarVOFinal.setWk37Day2Date(week37.get(1));
                                newTekCalendarVOFinal.setWk37Day3Date(week37.get(2));
                                newTekCalendarVOFinal.setWk37Day4Date(week37.get(3));
                                newTekCalendarVOFinal.setWk37Day5Date(week37.get(4));
                                newTekCalendarVOFinal.setWk37Day6Date(week37.get(5));
                                newTekCalendarVOFinal.setWk37Day7Date(week37.get(6));
                                if (i == 1 && week37.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk37Day1(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 2 && week37.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk37Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week37.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk37Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week37.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk37Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week37.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk37Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week37.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk37Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week37.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk37Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                    if(week == 38){
                        if(newTekCalendarVOFinal.getWk38Msrp() != null && newTekCalendarVOFinal.getWk38Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk38Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk38MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk38Msrp() == null){
                            newTekCalendarVOFinal.setWk38Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk38MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        String week38Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),38,0);
                        String[] week38DayArr = week38Days.split(",");
                        String week38DayFrom = week38DayArr[0];
                        String week38DayTo = week38DayArr[1];
                        List<String> week38 = DateUtils.addDates(week38DayFrom,week38DayTo);
                        if(week38.contains(date)){
                            for(int i=1;i<=week38.size();i++){
                                newTekCalendarVOFinal.setWk38Day1Date(week38.get(0));
                                newTekCalendarVOFinal.setWk38Day2Date(week38.get(1));
                                newTekCalendarVOFinal.setWk38Day3Date(week38.get(2));
                                newTekCalendarVOFinal.setWk38Day4Date(week38.get(3));
                                newTekCalendarVOFinal.setWk38Day5Date(week38.get(4));
                                newTekCalendarVOFinal.setWk38Day6Date(week38.get(5));
                                newTekCalendarVOFinal.setWk38Day7Date(week38.get(6));
                                if (i == 1 && week38.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk38Day1(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 2 && week38.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk38Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week38.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk38Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week38.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk38Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week38.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk38Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week38.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk38Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week38.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk38Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                    if(week == 39){
                        if(newTekCalendarVOFinal.getWk39Msrp() != null && newTekCalendarVOFinal.getWk39Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk39Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk39MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk39Msrp() == null){
                            newTekCalendarVOFinal.setWk39Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk39MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        String week39Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),39,0);
                        String[] week39DayArr = week39Days.split(",");
                        String week39DayFrom = week39DayArr[0];
                        String week39DayTo = week39DayArr[1];
                        List<String> week39 = DateUtils.addDates(week39DayFrom,week39DayTo);
                        if(week39.contains(date)){
                            for(int i=1;i<=week39.size();i++){
                                newTekCalendarVOFinal.setWk39Day1Date(week39.get(0));
                                newTekCalendarVOFinal.setWk39Day2Date(week39.get(1));
                                newTekCalendarVOFinal.setWk39Day3Date(week39.get(2));
                                newTekCalendarVOFinal.setWk39Day4Date(week39.get(3));
                                newTekCalendarVOFinal.setWk39Day5Date(week39.get(4));
                                newTekCalendarVOFinal.setWk39Day6Date(week39.get(5));
                                newTekCalendarVOFinal.setWk39Day7Date(week39.get(6));
                                if (i == 1 && week39.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk39Day1(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 2 && week39.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk39Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week39.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk39Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week39.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk39Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week39.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk39Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week39.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk39Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week39.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk39Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                    if(week == 40){
                        if(newTekCalendarVOFinal.getWk40Msrp() != null && newTekCalendarVOFinal.getWk40Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk40Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk40MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk40Msrp() == null){
                            newTekCalendarVOFinal.setWk40Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk40MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        String week40Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),40,0);
                        String[] week40DayArr = week40Days.split(",");
                        String week40DayFrom = week40DayArr[0];
                        String week40DayTo = week40DayArr[1];
                        List<String> week40 = DateUtils.addDates(week40DayFrom,week40DayTo);
                        if(week40.contains(date)){
                            for(int i=1;i<=week40.size();i++){
                                newTekCalendarVOFinal.setWk40Day1Date(week40.get(0));
                                newTekCalendarVOFinal.setWk40Day2Date(week40.get(1));
                                newTekCalendarVOFinal.setWk40Day3Date(week40.get(2));
                                newTekCalendarVOFinal.setWk40Day4Date(week40.get(3));
                                newTekCalendarVOFinal.setWk40Day5Date(week40.get(4));
                                newTekCalendarVOFinal.setWk40Day6Date(week40.get(5));
                                newTekCalendarVOFinal.setWk40Day7Date(week40.get(6));
                                if (i == 1 && week40.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk40Day1(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 2 && week40.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk40Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week40.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk40Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week40.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk40Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week40.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk40Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week40.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk40Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week40.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk40Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                    if(week == 41){
                        if(newTekCalendarVOFinal.getWk41Msrp() != null && newTekCalendarVOFinal.getWk41Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk41Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk41MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk41Msrp() == null){
                            newTekCalendarVOFinal.setWk41Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk41MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        String week41Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),411,0);
                        String[] week41DayArr = week41Days.split(",");
                        String week41DayFrom = week41DayArr[0];
                        String week41DayTo = week41DayArr[1];
                        List<String> week41 = DateUtils.addDates(week41DayFrom,week41DayTo);
                        if(week41.contains(date)){
                            for(int i=1;i<=week41.size();i++){
                                newTekCalendarVOFinal.setWk41Day1Date(week41.get(0));
                                newTekCalendarVOFinal.setWk41Day2Date(week41.get(1));
                                newTekCalendarVOFinal.setWk41Day3Date(week41.get(2));
                                newTekCalendarVOFinal.setWk41Day4Date(week41.get(3));
                                newTekCalendarVOFinal.setWk41Day5Date(week41.get(4));
                                newTekCalendarVOFinal.setWk41Day6Date(week41.get(5));
                                newTekCalendarVOFinal.setWk41Day7Date(week41.get(6));
                                if (i == 1 && week41.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk41Day1(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 2 && week41.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk41Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week41.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk41Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week41.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk41Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week41.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk41Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week41.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk41Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week41.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk41Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                    if(week == 42){
                        if(newTekCalendarVOFinal.getWk42Msrp() != null && newTekCalendarVOFinal.getWk42Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk42Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk42MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk42Msrp() == null){
                            newTekCalendarVOFinal.setWk42Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk42MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        String week42Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),42,0);
                        String[] week42DayArr = week42Days.split(",");
                        String week42DayFrom = week42DayArr[0];
                        String week42DayTo = week42DayArr[1];
                        List<String> week42 = DateUtils.addDates(week42DayFrom,week42DayTo);
                        if(week42.contains(date)){
                            for(int i=1;i<=week42.size();i++){
                                newTekCalendarVOFinal.setWk42Day1Date(week42.get(0));
                                newTekCalendarVOFinal.setWk42Day2Date(week42.get(1));
                                newTekCalendarVOFinal.setWk42Day3Date(week42.get(2));
                                newTekCalendarVOFinal.setWk42Day4Date(week42.get(3));
                                newTekCalendarVOFinal.setWk42Day5Date(week42.get(4));
                                newTekCalendarVOFinal.setWk42Day6Date(week42.get(5));
                                newTekCalendarVOFinal.setWk42Day7Date(week42.get(6));
                                if (i == 1 && week42.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk42Day1(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 2 && week42.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk42Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week42.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk42Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week42.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk42Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week42.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk42Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week42.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk42Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week42.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk42Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                    if(week == 43){
                        if(newTekCalendarVOFinal.getWk43Msrp() != null && newTekCalendarVOFinal.getWk43Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk43Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk43MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk43Msrp() == null){
                            newTekCalendarVOFinal.setWk43Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk43MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        String week43Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),43,0);
                        String[] week43DayArr = week43Days.split(",");
                        String week43DayFrom = week43DayArr[0];
                        String week43DayTo = week43DayArr[1];
                        List<String> week43 = DateUtils.addDates(week43DayFrom,week43DayTo);
                        if(week43.contains(date)){
                            for(int i=1;i<=week43.size();i++){
                                newTekCalendarVOFinal.setWk43Day1Date(week43.get(0));
                                newTekCalendarVOFinal.setWk43Day2Date(week43.get(1));
                                newTekCalendarVOFinal.setWk43Day3Date(week43.get(2));
                                newTekCalendarVOFinal.setWk43Day4Date(week43.get(3));
                                newTekCalendarVOFinal.setWk43Day5Date(week43.get(4));
                                newTekCalendarVOFinal.setWk43Day6Date(week43.get(5));
                                newTekCalendarVOFinal.setWk43Day7Date(week43.get(6));
                                if (i == 1 && week43.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk43Day1(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 2 && week43.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk43Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week43.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk43Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week43.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk43Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week43.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk43Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week43.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk43Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week43.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk43Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                    if(week == 44){
                        if(newTekCalendarVOFinal.getWk44Msrp() != null && newTekCalendarVOFinal.getWk44Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk44Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk44MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk44Msrp() == null){
                            newTekCalendarVOFinal.setWk44Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk44MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        String week44Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),44,0);
                        String[] week44DayArr = week44Days.split(",");
                        String week44DayFrom = week44DayArr[0];
                        String week44DayTo = week44DayArr[1];
                        List<String> week44 = DateUtils.addDates(week44DayFrom,week44DayTo);
                        if(week44.contains(date)){
                            for(int i=1;i<=week44.size();i++){
                                newTekCalendarVOFinal.setWk44Day1Date(week44.get(0));
                                newTekCalendarVOFinal.setWk44Day2Date(week44.get(1));
                                newTekCalendarVOFinal.setWk44Day3Date(week44.get(2));
                                newTekCalendarVOFinal.setWk44Day4Date(week44.get(3));
                                newTekCalendarVOFinal.setWk44Day5Date(week44.get(4));
                                newTekCalendarVOFinal.setWk44Day6Date(week44.get(5));
                                newTekCalendarVOFinal.setWk44Day7Date(week44.get(6));
                                if (i == 1 && week44.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk44Day1(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 2 && week44.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk44Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week44.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk44Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week44.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk44Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week44.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk44Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week44.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk44Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week44.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk44Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                    if(week == 45){
                        if(newTekCalendarVOFinal.getWk45Msrp() != null && newTekCalendarVOFinal.getWk45Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk45Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk45MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk45Msrp() == null){
                            newTekCalendarVOFinal.setWk45Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk45MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        String week45Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),45,0);
                        String[] week45DayArr = week45Days.split(",");
                        String week45DayFrom = week45DayArr[0];
                        String week45DayTo = week45DayArr[1];
                        List<String> week45 = DateUtils.addDates(week45DayFrom,week45DayTo);
                        if(week45.contains(date)){
                            for(int i=1;i<=week45.size();i++){
                                newTekCalendarVOFinal.setWk45Day1Date(week45.get(0));
                                newTekCalendarVOFinal.setWk45Day2Date(week45.get(1));
                                newTekCalendarVOFinal.setWk45Day3Date(week45.get(2));
                                newTekCalendarVOFinal.setWk45Day4Date(week45.get(3));
                                newTekCalendarVOFinal.setWk45Day5Date(week45.get(4));
                                newTekCalendarVOFinal.setWk45Day6Date(week45.get(5));
                                newTekCalendarVOFinal.setWk45Day7Date(week45.get(6));
                                if (i == 1 && week45.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk45Day1(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 2 && week45.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk45Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week45.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk45Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week45.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk45Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week45.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk45Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week45.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk45Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week45.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk45Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                    if(week == 46){
                        if(newTekCalendarVOFinal.getWk46Msrp() != null && newTekCalendarVOFinal.getWk46Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk46Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk46MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk46Msrp() == null){
                            newTekCalendarVOFinal.setWk46Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk46MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        String week46Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),46,0);
                        String[] week46DayArr = week46Days.split(",");
                        String week46DayFrom = week46DayArr[0];
                        String week46DayTo = week46DayArr[1];
                        List<String> week46 = DateUtils.addDates(week46DayFrom,week46DayTo);
                        if(week46.contains(date)){
                            for(int i=1;i<=week46.size();i++){
                                newTekCalendarVOFinal.setWk46Day1Date(week46.get(0));
                                newTekCalendarVOFinal.setWk46Day2Date(week46.get(1));
                                newTekCalendarVOFinal.setWk46Day3Date(week46.get(2));
                                newTekCalendarVOFinal.setWk46Day4Date(week46.get(3));
                                newTekCalendarVOFinal.setWk46Day5Date(week46.get(4));
                                newTekCalendarVOFinal.setWk46Day6Date(week46.get(5));
                                newTekCalendarVOFinal.setWk46Day7Date(week46.get(6));
                                if (i == 1 && week46.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk46Day1(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 2 && week46.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk46Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week46.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk46Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week46.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk46Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week46.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk46Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week46.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk46Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week46.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk46Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                    if(week == 47){
                        if(newTekCalendarVOFinal.getWk47Msrp() != null && newTekCalendarVOFinal.getWk47Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk47Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk47MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk47Msrp() == null){
                            newTekCalendarVOFinal.setWk47Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk47MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        String week47Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),47,0);
                        String[] week47DayArr = week47Days.split(",");
                        String week47DayFrom = week47DayArr[0];
                        String week47DayTo = week47DayArr[1];
                        List<String> week47 = DateUtils.addDates(week47DayFrom,week47DayTo);
                        if(week47.contains(date)){
                            for(int i=1;i<=week47.size();i++){
                                newTekCalendarVOFinal.setWk47Day1Date(week47.get(0));
                                newTekCalendarVOFinal.setWk47Day2Date(week47.get(1));
                                newTekCalendarVOFinal.setWk47Day3Date(week47.get(2));
                                newTekCalendarVOFinal.setWk47Day4Date(week47.get(3));
                                newTekCalendarVOFinal.setWk47Day5Date(week47.get(4));
                                newTekCalendarVOFinal.setWk47Day6Date(week47.get(5));
                                newTekCalendarVOFinal.setWk47Day7Date(week47.get(6));
                                if (i == 1 && week47.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk47Day1(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 2 && week47.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk47Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week47.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk47Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week47.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk47Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week47.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk47Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week47.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk47Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week47.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk47Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                    if(week == 48){
                        if(newTekCalendarVOFinal.getWk48Msrp() != null && newTekCalendarVOFinal.getWk48Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk48Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk48MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk48Msrp() == null){
                            newTekCalendarVOFinal.setWk48Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk48MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        String week48Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),48,0);
                        String[] week48DayArr = week48Days.split(",");
                        String week48DayFrom = week48DayArr[0];
                        String week48DayTo = week48DayArr[1];
                        List<String> week48 = DateUtils.addDates(week48DayFrom,week48DayTo);
                        if(week48.contains(date)){
                            for(int i=1;i<=week48.size();i++){
                                newTekCalendarVOFinal.setWk48Day1Date(week48.get(0));
                                newTekCalendarVOFinal.setWk48Day2Date(week48.get(1));
                                newTekCalendarVOFinal.setWk48Day3Date(week48.get(2));
                                newTekCalendarVOFinal.setWk48Day4Date(week48.get(3));
                                newTekCalendarVOFinal.setWk48Day5Date(week48.get(4));
                                newTekCalendarVOFinal.setWk48Day6Date(week48.get(5));
                                newTekCalendarVOFinal.setWk48Day7Date(week48.get(6));
                                if (i == 1 && week48.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk48Day1(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 2 && week48.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk48Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week48.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk48Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week48.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk48Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week48.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk48Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week48.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk48Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week48.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk48Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                    if(week == 49){
                        if(newTekCalendarVOFinal.getWk49Msrp() != null && newTekCalendarVOFinal.getWk49Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk49Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk49MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk49Msrp() == null){
                            newTekCalendarVOFinal.setWk49Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk49MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        String week49Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),49,0);
                        String[] week49DayArr = week49Days.split(",");
                        String week49DayFrom = week49DayArr[0];
                        String week49DayTo = week49DayArr[1];
                        List<String> week49 = DateUtils.addDates(week49DayFrom,week49DayTo);
                        if(week49.contains(date)){
                            for(int i=1;i<=week49.size();i++){
                                newTekCalendarVOFinal.setWk49Day1Date(week49.get(0));
                                newTekCalendarVOFinal.setWk49Day2Date(week49.get(1));
                                newTekCalendarVOFinal.setWk49Day3Date(week49.get(2));
                                newTekCalendarVOFinal.setWk49Day4Date(week49.get(3));
                                newTekCalendarVOFinal.setWk49Day5Date(week49.get(4));
                                newTekCalendarVOFinal.setWk49Day6Date(week49.get(5));
                                newTekCalendarVOFinal.setWk49Day7Date(week49.get(6));
                                if (i == 1 && week49.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk49Day1(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 2 && week49.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk49Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week49.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk49Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week49.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk49Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week49.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk49Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week49.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk49Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week49.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk49Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                    if(week == 50){
                        if(newTekCalendarVOFinal.getWk50Msrp() != null && newTekCalendarVOFinal.getWk50Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk50Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk50MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk50Msrp() == null){
                            newTekCalendarVOFinal.setWk50Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk50MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        String week50Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),50,0);
                        String[] week50DayArr = week50Days.split(",");
                        String week50DayFrom = week50DayArr[0];
                        String week50DayTo = week50DayArr[1];
                        List<String> week50 = DateUtils.addDates(week50DayFrom,week50DayTo);
                        if(week50.contains(date)){
                            for(int i=1;i<=week50.size();i++){
                                newTekCalendarVOFinal.setWk50Day1Date(week50.get(0));
                                newTekCalendarVOFinal.setWk50Day2Date(week50.get(1));
                                newTekCalendarVOFinal.setWk50Day3Date(week50.get(2));
                                newTekCalendarVOFinal.setWk50Day4Date(week50.get(3));
                                newTekCalendarVOFinal.setWk50Day5Date(week50.get(4));
                                newTekCalendarVOFinal.setWk50Day6Date(week50.get(5));
                                newTekCalendarVOFinal.setWk50Day7Date(week50.get(6));
                                if (i == 1 && week50.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk50Day1(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 2 && week50.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk50Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week50.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk50Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week50.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk50Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week50.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk50Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week50.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk50Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week50.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk50Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                    if(week == 51){
                        if(newTekCalendarVOFinal.getWk51Msrp() != null && newTekCalendarVOFinal.getWk51Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk51Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk51MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk51Msrp() == null){
                            newTekCalendarVOFinal.setWk51Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk51MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        String week51Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),51,0);
                        String[] week51DayArr = week51Days.split(",");
                        String week51DayFrom = week51DayArr[0];
                        String week51DayTo = week51DayArr[1];
                        List<String> week51 = DateUtils.addDates(week51DayFrom,week51DayTo);
                        if(week51.contains(date)){
                            for(int i=1;i<=week51.size();i++){
                                newTekCalendarVOFinal.setWk51Day1Date(week51.get(0));
                                newTekCalendarVOFinal.setWk51Day2Date(week51.get(1));
                                newTekCalendarVOFinal.setWk51Day3Date(week51.get(2));
                                newTekCalendarVOFinal.setWk51Day4Date(week51.get(3));
                                newTekCalendarVOFinal.setWk51Day5Date(week51.get(4));
                                newTekCalendarVOFinal.setWk51Day6Date(week51.get(5));
                                newTekCalendarVOFinal.setWk51Day7Date(week51.get(6));
                                if (i == 1 && week51.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk51Day1(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 2 && week51.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk51Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week51.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk51Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week51.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk51Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week51.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk51Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week51.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk51Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week51.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk51Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                    if(week == 52){
                        if(newTekCalendarVOFinal.getWk52Msrp() != null && newTekCalendarVOFinal.getWk52Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk52Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk52MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk52Msrp() == null){
                            newTekCalendarVOFinal.setWk52Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk52MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        String week52Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),52,0);
                        String[] week52DayArr = week52Days.split(",");
                        String week52DayFrom = week52DayArr[0];
                        String week52DayTo = week52DayArr[1];
                        List<String> week52 = DateUtils.addDates(week52DayFrom,week52DayTo);
                        if(week52.contains(date)){
                            for(int i=1;i<=week52.size();i++){
                                newTekCalendarVOFinal.setWk52Day1Date(week52.get(0));
                                newTekCalendarVOFinal.setWk52Day2Date(week52.get(1));
                                newTekCalendarVOFinal.setWk52Day3Date(week52.get(2));
                                newTekCalendarVOFinal.setWk52Day4Date(week52.get(3));
                                newTekCalendarVOFinal.setWk52Day5Date(week52.get(4));
                                newTekCalendarVOFinal.setWk52Day6Date(week52.get(5));
                                newTekCalendarVOFinal.setWk52Day7Date(week52.get(6));
                                if (i == 1 && week52.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk52Day1(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 2 && week52.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk52Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week52.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk52Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week52.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk52Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week52.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk52Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week52.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk52Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week52.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk52Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                    if(week == 53){
                        if(newTekCalendarVOFinal.getWk53Msrp() != null && newTekCalendarVOFinal.getWk53Msrp().compareTo(newTekCalendarVO.getSellingPrice()) == 1){
                            newTekCalendarVOFinal.setWk53Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk53MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        } else if(newTekCalendarVOFinal.getWk53Msrp() == null){
                            newTekCalendarVOFinal.setWk53Msrp(newTekCalendarVO.getSellingPrice());
                            newTekCalendarVOFinal.setWk53MsrpUSD(newTekCalendarVO.getSellingPriceUSD());
                        }
                        String week53Days = DateUtils.getWeekDays(Integer.parseInt(reqYear),53,0);
                        String[] week53DayArr = week53Days.split(",");
                        String week53DayFrom = week53DayArr[0];
                        String week53DayTo = week53DayArr[1];
                        List<String> week53 = DateUtils.addDates(week53DayFrom,week53DayTo);
                        if(week53.contains(date)){
                            for(int i=1;i<=week53.size();i++){
                                newTekCalendarVOFinal.setWk53Day1Date(week53.get(0));
                                newTekCalendarVOFinal.setWk53Day2Date(week53.get(1));
                                newTekCalendarVOFinal.setWk53Day3Date(week53.get(2));
                                newTekCalendarVOFinal.setWk53Day4Date(week53.get(3));
                                newTekCalendarVOFinal.setWk53Day5Date(week53.get(4));
                                newTekCalendarVOFinal.setWk53Day6Date(week53.get(5));
                                newTekCalendarVOFinal.setWk53Day7Date(week53.get(6));
                                if (i == 1 && week53.get(0).equals(date)) {
                                    newTekCalendarVOFinal.setWk53Day1(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 2 && week53.get(1).equals(date)){
                                    newTekCalendarVOFinal.setWk53Day2(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 3 && week53.get(2).equals(date)){
                                    newTekCalendarVOFinal.setWk53Day3(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 4 && week53.get(3).equals(date)){
                                    newTekCalendarVOFinal.setWk53Day4(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 5 && week53.get(4).equals(date)){
                                    newTekCalendarVOFinal.setWk53Day5(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 6 && week53.get(5).equals(date)){
                                    newTekCalendarVOFinal.setWk53Day6(newTekCalendarVO.getSellingPriceUSD());
                                } else if(i == 7 && week53.get(6).equals(date)){
                                    newTekCalendarVOFinal.setWk53Day7(newTekCalendarVO.getSellingPriceUSD());
                                }
                            }
                        }
                    }
                }
            }
            newTekCalendarVOListFinal.add(newTekCalendarVOFinal);
        }
        return newTekCalendarVOListFinal;
    }

    public NewTekCalendarVO queryByDisposeSortId(Map<String,NewTekCalendarVO> map,NewTekCalendarVO newTekCalendarVO){
        if(newTekCalendarVO.getDisposeSortId() != 1) {
            for (int i = newTekCalendarVO.getDisposeSortId() - 1; i >= 1; i--) {
                NewTekProductModel productModel = new NewTekProductModel();
                productModel.setCategoryId(newTekCalendarVO.getCategoryId());
                productModel.setModelId(newTekCalendarVO.getModelId());
                productModel.setDisposeSortId(i);
                String countryNew = newTekCalendarVO.getCountry().split("_")[0] + "_" + newTekCalendarVO.getCountry().split("_")[1];
                // TODO: 2020/8/18
//                productModel.setCountryName(countryNew);
                productModel.setCountryName(newTekCalendarVO.getCountry());
                 List<NewTekProductModel> productModelList = newTekProductModelMapper.getProductModelByCategoryAndModelAndDisposeSortId(productModel);
                if (productModelList.size() > 0 && productModelList != null) {
                    String key = productModelList.get(0).getSku() + "," + newTekCalendarVO.getCustomerId() + "," + newTekCalendarVO.getCountry() + "," + newTekCalendarVO.getYear();
                    if (map.containsKey(key) && newTekCalendarVO.getDisposeGroupId().equals(productModelList.get(0).getDisposeGroupId())) {
                        NewTekCalendarVO newTekCalendarVO1 = map.get(key);
                        if ((!newTekCalendarVO.getDisposeName().contains("EX") && !newTekCalendarVO1.getDisposeName().contains("EX")) || (newTekCalendarVO.getDisposeName().contains("EX") && !newTekCalendarVO1.getDisposeName().contains("EX"))) {
                            //当前同品类、同系列，比前一个配置的价格低，报异常
                            if (newTekCalendarVO.getWk1MsrpUSD() != null && newTekCalendarVO1.getWk1MsrpUSD() != null && (newTekCalendarVO.getWk1MsrpUSD().compareTo(newTekCalendarVO1.getWk1MsrpUSD()) == -1)) {
                                newTekCalendarVO.setWk1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk2MsrpUSD() != null && newTekCalendarVO1.getWk2MsrpUSD() != null && newTekCalendarVO.getWk2MsrpUSD().compareTo(newTekCalendarVO1.getWk2MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk3MsrpUSD() != null && newTekCalendarVO1.getWk3MsrpUSD() != null && newTekCalendarVO.getWk3MsrpUSD().compareTo(newTekCalendarVO1.getWk3MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk4MsrpUSD() != null && newTekCalendarVO1.getWk4MsrpUSD() != null && newTekCalendarVO.getWk4MsrpUSD().compareTo(newTekCalendarVO1.getWk4MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk5MsrpUSD() != null && newTekCalendarVO1.getWk5MsrpUSD() != null && newTekCalendarVO.getWk5MsrpUSD().compareTo(newTekCalendarVO1.getWk5MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk6MsrpUSD() != null && newTekCalendarVO1.getWk6MsrpUSD() != null && newTekCalendarVO.getWk6MsrpUSD().compareTo(newTekCalendarVO1.getWk6MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk7MsrpUSD() != null && newTekCalendarVO1.getWk7MsrpUSD() != null && newTekCalendarVO.getWk7MsrpUSD().compareTo(newTekCalendarVO1.getWk7MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk8MsrpUSD() != null && newTekCalendarVO1.getWk8MsrpUSD() != null && newTekCalendarVO.getWk8MsrpUSD().compareTo(newTekCalendarVO1.getWk8MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk8Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk9MsrpUSD() != null && newTekCalendarVO1.getWk9MsrpUSD() != null && newTekCalendarVO.getWk9MsrpUSD().compareTo(newTekCalendarVO1.getWk9MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk9Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk10MsrpUSD() != null && newTekCalendarVO1.getWk10MsrpUSD() != null && newTekCalendarVO.getWk10MsrpUSD().compareTo(newTekCalendarVO1.getWk10MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk10Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk11MsrpUSD() != null && newTekCalendarVO1.getWk11MsrpUSD() != null && newTekCalendarVO.getWk11MsrpUSD().compareTo(newTekCalendarVO1.getWk11MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk11Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk12MsrpUSD() != null && newTekCalendarVO1.getWk12MsrpUSD() != null && newTekCalendarVO.getWk12MsrpUSD().compareTo(newTekCalendarVO1.getWk12MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk12Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk13MsrpUSD() != null && newTekCalendarVO1.getWk13MsrpUSD() != null && newTekCalendarVO.getWk13MsrpUSD().compareTo(newTekCalendarVO1.getWk13MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk13Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk14MsrpUSD() != null && newTekCalendarVO1.getWk14MsrpUSD() != null && newTekCalendarVO.getWk14MsrpUSD().compareTo(newTekCalendarVO1.getWk14MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk14Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk15MsrpUSD() != null && newTekCalendarVO1.getWk15MsrpUSD() != null && newTekCalendarVO.getWk15MsrpUSD().compareTo(newTekCalendarVO1.getWk15MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk15Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk16MsrpUSD() != null && newTekCalendarVO1.getWk16MsrpUSD() != null && newTekCalendarVO.getWk16MsrpUSD().compareTo(newTekCalendarVO1.getWk16MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk16Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk17MsrpUSD() != null && newTekCalendarVO1.getWk17MsrpUSD() != null && newTekCalendarVO.getWk17MsrpUSD().compareTo(newTekCalendarVO1.getWk17MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk17Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk18MsrpUSD() != null && newTekCalendarVO1.getWk18MsrpUSD() != null && newTekCalendarVO.getWk18MsrpUSD().compareTo(newTekCalendarVO1.getWk18MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk18Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk19MsrpUSD() != null && newTekCalendarVO1.getWk19MsrpUSD() != null && newTekCalendarVO.getWk19MsrpUSD().compareTo(newTekCalendarVO1.getWk19MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk19Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk20MsrpUSD() != null && newTekCalendarVO1.getWk20MsrpUSD() != null && newTekCalendarVO.getWk20MsrpUSD().compareTo(newTekCalendarVO1.getWk20MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk20Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk21MsrpUSD() != null && newTekCalendarVO1.getWk21MsrpUSD() != null && newTekCalendarVO.getWk21MsrpUSD().compareTo(newTekCalendarVO1.getWk21MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk21Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk22MsrpUSD() != null && newTekCalendarVO1.getWk22MsrpUSD() != null && newTekCalendarVO.getWk22MsrpUSD().compareTo(newTekCalendarVO1.getWk22MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk22Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk23MsrpUSD() != null && newTekCalendarVO1.getWk23MsrpUSD() != null && newTekCalendarVO.getWk23MsrpUSD().compareTo(newTekCalendarVO1.getWk23MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk23Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk24MsrpUSD() != null && newTekCalendarVO1.getWk24MsrpUSD() != null && newTekCalendarVO.getWk24MsrpUSD().compareTo(newTekCalendarVO1.getWk24MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk24Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk25MsrpUSD() != null && newTekCalendarVO1.getWk25MsrpUSD() != null && newTekCalendarVO.getWk25MsrpUSD().compareTo(newTekCalendarVO1.getWk25MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk25Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk26MsrpUSD() != null && newTekCalendarVO1.getWk26MsrpUSD() != null && newTekCalendarVO.getWk26MsrpUSD().compareTo(newTekCalendarVO1.getWk26MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk26Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk27MsrpUSD() != null && newTekCalendarVO1.getWk27MsrpUSD() != null && newTekCalendarVO.getWk27MsrpUSD().compareTo(newTekCalendarVO1.getWk27MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk27Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk28MsrpUSD() != null && newTekCalendarVO1.getWk28MsrpUSD() != null && newTekCalendarVO.getWk28MsrpUSD().compareTo(newTekCalendarVO1.getWk28MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk28Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk29MsrpUSD() != null && newTekCalendarVO1.getWk29MsrpUSD() != null && newTekCalendarVO.getWk29MsrpUSD().compareTo(newTekCalendarVO1.getWk29MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk29Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk30MsrpUSD() != null && newTekCalendarVO1.getWk30MsrpUSD() != null && newTekCalendarVO.getWk30MsrpUSD().compareTo(newTekCalendarVO1.getWk30MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk30Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk31MsrpUSD() != null && newTekCalendarVO1.getWk31MsrpUSD() != null && newTekCalendarVO.getWk31MsrpUSD().compareTo(newTekCalendarVO1.getWk31MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk31Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk32MsrpUSD() != null && newTekCalendarVO1.getWk32MsrpUSD() != null && newTekCalendarVO.getWk32MsrpUSD().compareTo(newTekCalendarVO1.getWk32MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk32Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk33MsrpUSD() != null && newTekCalendarVO1.getWk33MsrpUSD() != null && newTekCalendarVO.getWk33MsrpUSD().compareTo(newTekCalendarVO1.getWk33MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk33Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk34MsrpUSD() != null && newTekCalendarVO1.getWk34MsrpUSD() != null && newTekCalendarVO.getWk34MsrpUSD().compareTo(newTekCalendarVO1.getWk34MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk34Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk35MsrpUSD() != null && newTekCalendarVO1.getWk35MsrpUSD() != null && newTekCalendarVO.getWk35MsrpUSD().compareTo(newTekCalendarVO1.getWk35MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk35Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk36MsrpUSD() != null && newTekCalendarVO1.getWk36MsrpUSD() != null && newTekCalendarVO.getWk36MsrpUSD().compareTo(newTekCalendarVO1.getWk36MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk36Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk37MsrpUSD() != null && newTekCalendarVO1.getWk37MsrpUSD() != null && newTekCalendarVO.getWk37MsrpUSD().compareTo(newTekCalendarVO1.getWk37MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk37Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk38MsrpUSD() != null && newTekCalendarVO1.getWk38MsrpUSD() != null && newTekCalendarVO.getWk38MsrpUSD().compareTo(newTekCalendarVO1.getWk38MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk38Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk39MsrpUSD() != null && newTekCalendarVO1.getWk39MsrpUSD() != null && newTekCalendarVO.getWk39MsrpUSD().compareTo(newTekCalendarVO1.getWk39MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk39Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk40MsrpUSD() != null && newTekCalendarVO1.getWk40MsrpUSD() != null && newTekCalendarVO.getWk40MsrpUSD().compareTo(newTekCalendarVO1.getWk40MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk40Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk41MsrpUSD() != null && newTekCalendarVO1.getWk41MsrpUSD() != null && newTekCalendarVO.getWk41MsrpUSD().compareTo(newTekCalendarVO1.getWk41MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk41Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk42MsrpUSD() != null && newTekCalendarVO1.getWk42MsrpUSD() != null && newTekCalendarVO.getWk42MsrpUSD().compareTo(newTekCalendarVO1.getWk42MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk42Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk43MsrpUSD() != null && newTekCalendarVO1.getWk43MsrpUSD() != null && newTekCalendarVO.getWk43MsrpUSD().compareTo(newTekCalendarVO1.getWk43MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk43Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk44MsrpUSD() != null && newTekCalendarVO1.getWk44MsrpUSD() != null && newTekCalendarVO.getWk44MsrpUSD().compareTo(newTekCalendarVO1.getWk44MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk44Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk45MsrpUSD() != null && newTekCalendarVO1.getWk45MsrpUSD() != null && newTekCalendarVO.getWk45MsrpUSD().compareTo(newTekCalendarVO1.getWk45MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk45Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk46MsrpUSD() != null && newTekCalendarVO1.getWk46MsrpUSD() != null && newTekCalendarVO.getWk46MsrpUSD().compareTo(newTekCalendarVO1.getWk46MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk46Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk47MsrpUSD() != null && newTekCalendarVO1.getWk47MsrpUSD() != null && newTekCalendarVO.getWk47MsrpUSD().compareTo(newTekCalendarVO1.getWk47MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk47Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk48MsrpUSD() != null && newTekCalendarVO1.getWk48MsrpUSD() != null && newTekCalendarVO.getWk48MsrpUSD().compareTo(newTekCalendarVO1.getWk48MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk48Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk49MsrpUSD() != null && newTekCalendarVO1.getWk49MsrpUSD() != null && newTekCalendarVO.getWk49MsrpUSD().compareTo(newTekCalendarVO1.getWk49MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk49Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk50MsrpUSD() != null && newTekCalendarVO1.getWk50MsrpUSD() != null && newTekCalendarVO.getWk50MsrpUSD().compareTo(newTekCalendarVO1.getWk50MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk50Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk51MsrpUSD() != null && newTekCalendarVO1.getWk51MsrpUSD() != null && newTekCalendarVO.getWk51MsrpUSD().compareTo(newTekCalendarVO1.getWk51MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk51Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk52MsrpUSD() != null && newTekCalendarVO1.getWk52MsrpUSD() != null && newTekCalendarVO.getWk52MsrpUSD().compareTo(newTekCalendarVO1.getWk52MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk52Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk53MsrpUSD() != null && newTekCalendarVO1.getWk53MsrpUSD() != null && newTekCalendarVO.getWk53MsrpUSD().compareTo(newTekCalendarVO1.getWk53MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk53Status(tekConstants.WK_STATUS_ERROR);
                            }
                            //day判断
                            if (newTekCalendarVO.getWk1Day1() != null && newTekCalendarVO1.getWk1Day1() != null && newTekCalendarVO.getWk1Day1().compareTo(newTekCalendarVO1.getWk1Day1()) == -1) {
                                newTekCalendarVO.setWk1Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk1Day2() != null && newTekCalendarVO1.getWk1Day2() != null && newTekCalendarVO.getWk1Day2().compareTo(newTekCalendarVO1.getWk1Day2()) == -1) {
                                newTekCalendarVO.setWk1Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk1Day3() != null && newTekCalendarVO1.getWk1Day3() != null && newTekCalendarVO.getWk1Day3().compareTo(newTekCalendarVO1.getWk1Day3()) == -1) {
                                newTekCalendarVO.setWk1Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk1Day4() != null && newTekCalendarVO1.getWk1Day4() != null && newTekCalendarVO.getWk1Day4().compareTo(newTekCalendarVO1.getWk1Day4()) == -1) {
                                newTekCalendarVO.setWk1Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk1Day5() != null && newTekCalendarVO1.getWk1Day5() != null && newTekCalendarVO.getWk1Day5().compareTo(newTekCalendarVO1.getWk1Day5()) == -1) {
                                newTekCalendarVO.setWk1Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk1Day6() != null && newTekCalendarVO1.getWk1Day6() != null && newTekCalendarVO.getWk1Day6().compareTo(newTekCalendarVO1.getWk1Day6()) == -1) {
                                newTekCalendarVO.setWk1Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk1Day7() != null && newTekCalendarVO1.getWk1Day7() != null && newTekCalendarVO.getWk1Day7().compareTo(newTekCalendarVO1.getWk1Day7()) == -1) {
                                newTekCalendarVO.setWk1Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk2Day1() != null && newTekCalendarVO1.getWk2Day1() != null && newTekCalendarVO.getWk2Day1().compareTo(newTekCalendarVO1.getWk2Day1()) == -1) {
                                newTekCalendarVO.setWk2Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk2Day2() != null && newTekCalendarVO1.getWk2Day2() != null && newTekCalendarVO.getWk2Day2().compareTo(newTekCalendarVO1.getWk2Day2()) == -1) {
                                newTekCalendarVO.setWk2Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk2Day3() != null && newTekCalendarVO1.getWk2Day3() != null && newTekCalendarVO.getWk2Day3().compareTo(newTekCalendarVO1.getWk2Day3()) == -1) {
                                newTekCalendarVO.setWk2Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk2Day4() != null && newTekCalendarVO1.getWk2Day4() != null && newTekCalendarVO.getWk2Day4().compareTo(newTekCalendarVO1.getWk2Day4()) == -1) {
                                newTekCalendarVO.setWk2Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk2Day5() != null && newTekCalendarVO1.getWk2Day5() != null && newTekCalendarVO.getWk2Day5().compareTo(newTekCalendarVO1.getWk2Day5()) == -1) {
                                newTekCalendarVO.setWk2Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk2Day6() != null && newTekCalendarVO1.getWk2Day6() != null && newTekCalendarVO.getWk2Day6().compareTo(newTekCalendarVO1.getWk2Day6()) == -1) {
                                newTekCalendarVO.setWk2Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk2Day7() != null && newTekCalendarVO1.getWk2Day7() != null && newTekCalendarVO.getWk2Day7().compareTo(newTekCalendarVO1.getWk2Day7()) == -1) {
                                newTekCalendarVO.setWk2Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk3Day1() != null && newTekCalendarVO1.getWk3Day1() != null && newTekCalendarVO.getWk3Day1().compareTo(newTekCalendarVO1.getWk3Day1()) == -1) {
                                newTekCalendarVO.setWk3Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk3Day2() != null && newTekCalendarVO1.getWk3Day2() != null && newTekCalendarVO.getWk3Day2().compareTo(newTekCalendarVO1.getWk3Day2()) == -1) {
                                newTekCalendarVO.setWk3Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk3Day3() != null && newTekCalendarVO1.getWk3Day3() != null && newTekCalendarVO.getWk3Day3().compareTo(newTekCalendarVO1.getWk3Day3()) == -1) {
                                newTekCalendarVO.setWk3Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk3Day4() != null && newTekCalendarVO1.getWk3Day4() != null && newTekCalendarVO.getWk3Day4().compareTo(newTekCalendarVO1.getWk3Day4()) == -1) {
                                newTekCalendarVO.setWk3Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk3Day5() != null && newTekCalendarVO1.getWk3Day5() != null && newTekCalendarVO.getWk3Day5().compareTo(newTekCalendarVO1.getWk3Day5()) == -1) {
                                newTekCalendarVO.setWk3Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk3Day6() != null && newTekCalendarVO1.getWk3Day6() != null && newTekCalendarVO.getWk3Day6().compareTo(newTekCalendarVO1.getWk3Day6()) == -1) {
                                newTekCalendarVO.setWk3Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk3Day7() != null && newTekCalendarVO1.getWk3Day7() != null && newTekCalendarVO.getWk3Day7().compareTo(newTekCalendarVO1.getWk3Day7()) == -1) {
                                newTekCalendarVO.setWk3Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk4Day1() != null && newTekCalendarVO1.getWk4Day1() != null && newTekCalendarVO.getWk4Day1().compareTo(newTekCalendarVO1.getWk4Day1()) == -1) {
                                newTekCalendarVO.setWk4Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk4Day2() != null && newTekCalendarVO1.getWk4Day2() != null && newTekCalendarVO.getWk4Day2().compareTo(newTekCalendarVO1.getWk4Day2()) == -1) {
                                newTekCalendarVO.setWk4Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk4Day3() != null && newTekCalendarVO1.getWk4Day3() != null && newTekCalendarVO.getWk4Day3().compareTo(newTekCalendarVO1.getWk4Day3()) == -1) {
                                newTekCalendarVO.setWk4Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk4Day4() != null && newTekCalendarVO1.getWk4Day4() != null && newTekCalendarVO.getWk4Day4().compareTo(newTekCalendarVO1.getWk4Day4()) == -1) {
                                newTekCalendarVO.setWk4Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk4Day5() != null && newTekCalendarVO1.getWk4Day5() != null && newTekCalendarVO.getWk4Day5().compareTo(newTekCalendarVO1.getWk4Day5()) == -1) {
                                newTekCalendarVO.setWk4Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk4Day6() != null && newTekCalendarVO1.getWk4Day6() != null && newTekCalendarVO.getWk4Day6().compareTo(newTekCalendarVO1.getWk4Day6()) == -1) {
                                newTekCalendarVO.setWk4Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk4Day7() != null && newTekCalendarVO1.getWk4Day7() != null && newTekCalendarVO.getWk4Day7().compareTo(newTekCalendarVO1.getWk4Day7()) == -1) {
                                newTekCalendarVO.setWk4Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk5Day1() != null && newTekCalendarVO1.getWk5Day1() != null && newTekCalendarVO.getWk5Day1().compareTo(newTekCalendarVO1.getWk5Day1()) == -1) {
                                newTekCalendarVO.setWk5Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk5Day2() != null && newTekCalendarVO1.getWk5Day2() != null && newTekCalendarVO.getWk5Day2().compareTo(newTekCalendarVO1.getWk5Day2()) == -1) {
                                newTekCalendarVO.setWk5Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk5Day3() != null && newTekCalendarVO1.getWk5Day3() != null && newTekCalendarVO.getWk5Day3().compareTo(newTekCalendarVO1.getWk5Day3()) == -1) {
                                newTekCalendarVO.setWk5Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk5Day4() != null && newTekCalendarVO1.getWk5Day4() != null && newTekCalendarVO.getWk5Day4().compareTo(newTekCalendarVO1.getWk5Day4()) == -1) {
                                newTekCalendarVO.setWk5Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk5Day5() != null && newTekCalendarVO1.getWk5Day5() != null && newTekCalendarVO.getWk5Day5().compareTo(newTekCalendarVO1.getWk5Day5()) == -1) {
                                newTekCalendarVO.setWk5Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk5Day6() != null && newTekCalendarVO1.getWk5Day6() != null && newTekCalendarVO.getWk5Day6().compareTo(newTekCalendarVO1.getWk5Day6()) == -1) {
                                newTekCalendarVO.setWk5Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk5Day7() != null && newTekCalendarVO1.getWk5Day7() != null && newTekCalendarVO.getWk5Day7().compareTo(newTekCalendarVO1.getWk5Day7()) == -1) {
                                newTekCalendarVO.setWk5Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk6Day1() != null && newTekCalendarVO1.getWk6Day1() != null && newTekCalendarVO.getWk6Day1().compareTo(newTekCalendarVO1.getWk6Day1()) == -1) {
                                newTekCalendarVO.setWk7Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk6Day2() != null && newTekCalendarVO1.getWk6Day2() != null && newTekCalendarVO.getWk6Day2().compareTo(newTekCalendarVO1.getWk6Day2()) == -1) {
                                newTekCalendarVO.setWk7Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk6Day3() != null && newTekCalendarVO1.getWk6Day3() != null && newTekCalendarVO.getWk6Day3().compareTo(newTekCalendarVO1.getWk6Day3()) == -1) {
                                newTekCalendarVO.setWk7Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk6Day4() != null && newTekCalendarVO1.getWk6Day4() != null && newTekCalendarVO.getWk6Day4().compareTo(newTekCalendarVO1.getWk6Day4()) == -1) {
                                newTekCalendarVO.setWk7Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk6Day5() != null && newTekCalendarVO1.getWk6Day5() != null && newTekCalendarVO.getWk6Day5().compareTo(newTekCalendarVO1.getWk6Day5()) == -1) {
                                newTekCalendarVO.setWk7Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk6Day6() != null && newTekCalendarVO1.getWk6Day6() != null && newTekCalendarVO.getWk6Day6().compareTo(newTekCalendarVO1.getWk6Day6()) == -1) {
                                newTekCalendarVO.setWk7Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk6Day7() != null && newTekCalendarVO1.getWk6Day7() != null && newTekCalendarVO.getWk6Day7().compareTo(newTekCalendarVO1.getWk6Day7()) == -1) {
                                newTekCalendarVO.setWk7Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk7Day1() != null && newTekCalendarVO1.getWk7Day1() != null && newTekCalendarVO.getWk7Day1().compareTo(newTekCalendarVO1.getWk7Day1()) == -1) {
                                newTekCalendarVO.setWk7Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk7Day2() != null && newTekCalendarVO1.getWk7Day2() != null && newTekCalendarVO.getWk7Day2().compareTo(newTekCalendarVO1.getWk7Day2()) == -1) {
                                newTekCalendarVO.setWk7Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk7Day3() != null && newTekCalendarVO1.getWk7Day3() != null && newTekCalendarVO.getWk7Day3().compareTo(newTekCalendarVO1.getWk7Day3()) == -1) {
                                newTekCalendarVO.setWk7Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk7Day4() != null && newTekCalendarVO1.getWk7Day4() != null && newTekCalendarVO.getWk7Day4().compareTo(newTekCalendarVO1.getWk7Day4()) == -1) {
                                newTekCalendarVO.setWk7Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk7Day5() != null && newTekCalendarVO1.getWk7Day5() != null && newTekCalendarVO.getWk7Day5().compareTo(newTekCalendarVO1.getWk7Day5()) == -1) {
                                newTekCalendarVO.setWk7Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk7Day6() != null && newTekCalendarVO1.getWk7Day6() != null && newTekCalendarVO.getWk7Day6().compareTo(newTekCalendarVO1.getWk7Day6()) == -1) {
                                newTekCalendarVO.setWk7Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk7Day7() != null && newTekCalendarVO1.getWk7Day7() != null && newTekCalendarVO.getWk7Day7().compareTo(newTekCalendarVO1.getWk7Day7()) == -1) {
                                newTekCalendarVO.setWk7Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk8Day1() != null && newTekCalendarVO1.getWk8Day1() != null && newTekCalendarVO.getWk8Day1().compareTo(newTekCalendarVO1.getWk8Day1()) == -1) {
                                newTekCalendarVO.setWk8Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk8Day2() != null && newTekCalendarVO1.getWk8Day2() != null && newTekCalendarVO.getWk8Day2().compareTo(newTekCalendarVO1.getWk8Day2()) == -1) {
                                newTekCalendarVO.setWk8Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk8Day3() != null && newTekCalendarVO1.getWk8Day3() != null && newTekCalendarVO.getWk8Day3().compareTo(newTekCalendarVO1.getWk8Day3()) == -1) {
                                newTekCalendarVO.setWk8Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk8Day4() != null && newTekCalendarVO1.getWk8Day4() != null && newTekCalendarVO.getWk8Day4().compareTo(newTekCalendarVO1.getWk8Day4()) == -1) {
                                newTekCalendarVO.setWk8Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk8Day5() != null && newTekCalendarVO1.getWk8Day5() != null && newTekCalendarVO.getWk8Day5().compareTo(newTekCalendarVO1.getWk8Day5()) == -1) {
                                newTekCalendarVO.setWk8Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk8Day6() != null && newTekCalendarVO1.getWk8Day6() != null && newTekCalendarVO.getWk8Day6().compareTo(newTekCalendarVO1.getWk8Day6()) == -1) {
                                newTekCalendarVO.setWk8Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk8Day7() != null && newTekCalendarVO1.getWk8Day7() != null && newTekCalendarVO.getWk8Day7().compareTo(newTekCalendarVO1.getWk8Day7()) == -1) {
                                newTekCalendarVO.setWk8Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk9Day1() != null && newTekCalendarVO1.getWk9Day1() != null && newTekCalendarVO.getWk9Day1().compareTo(newTekCalendarVO1.getWk9Day1()) == -1) {
                                newTekCalendarVO.setWk9Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk9Day2() != null && newTekCalendarVO1.getWk9Day2() != null && newTekCalendarVO.getWk9Day2().compareTo(newTekCalendarVO1.getWk9Day2()) == -1) {
                                newTekCalendarVO.setWk9Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk9Day3() != null && newTekCalendarVO1.getWk9Day3() != null && newTekCalendarVO.getWk9Day3().compareTo(newTekCalendarVO1.getWk9Day3()) == -1) {
                                newTekCalendarVO.setWk9Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk9Day4() != null && newTekCalendarVO1.getWk9Day4() != null && newTekCalendarVO.getWk9Day4().compareTo(newTekCalendarVO1.getWk9Day4()) == -1) {
                                newTekCalendarVO.setWk9Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk9Day5() != null && newTekCalendarVO1.getWk9Day5() != null && newTekCalendarVO.getWk9Day5().compareTo(newTekCalendarVO1.getWk9Day5()) == -1) {
                                newTekCalendarVO.setWk9Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk9Day6() != null && newTekCalendarVO1.getWk9Day6() != null && newTekCalendarVO.getWk9Day6().compareTo(newTekCalendarVO1.getWk9Day6()) == -1) {
                                newTekCalendarVO.setWk9Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk9Day7() != null && newTekCalendarVO1.getWk9Day7() != null && newTekCalendarVO.getWk9Day7().compareTo(newTekCalendarVO1.getWk9Day7()) == -1) {
                                newTekCalendarVO.setWk9Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk10Day1() != null && newTekCalendarVO1.getWk10Day1() != null && newTekCalendarVO.getWk10Day1().compareTo(newTekCalendarVO1.getWk10Day1()) == -1) {
                                newTekCalendarVO.setWk10Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk10Day2() != null && newTekCalendarVO1.getWk10Day2() != null && newTekCalendarVO.getWk10Day2().compareTo(newTekCalendarVO1.getWk10Day2()) == -1) {
                                newTekCalendarVO.setWk10Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk10Day3() != null && newTekCalendarVO1.getWk10Day3() != null && newTekCalendarVO.getWk10Day3().compareTo(newTekCalendarVO1.getWk10Day3()) == -1) {
                                newTekCalendarVO.setWk10Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk10Day4() != null && newTekCalendarVO1.getWk10Day4() != null && newTekCalendarVO.getWk10Day4().compareTo(newTekCalendarVO1.getWk10Day4()) == -1) {
                                newTekCalendarVO.setWk10Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk10Day5() != null && newTekCalendarVO1.getWk10Day5() != null && newTekCalendarVO.getWk10Day5().compareTo(newTekCalendarVO1.getWk10Day5()) == -1) {
                                newTekCalendarVO.setWk10Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk10Day6() != null && newTekCalendarVO1.getWk10Day6() != null && newTekCalendarVO.getWk10Day6().compareTo(newTekCalendarVO1.getWk10Day6()) == -1) {
                                newTekCalendarVO.setWk10Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk10Day7() != null && newTekCalendarVO1.getWk10Day7() != null && newTekCalendarVO.getWk10Day7().compareTo(newTekCalendarVO1.getWk10Day7()) == -1) {
                                newTekCalendarVO.setWk10Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk11Day1() != null && newTekCalendarVO1.getWk11Day1() != null && newTekCalendarVO.getWk11Day1().compareTo(newTekCalendarVO1.getWk11Day1()) == -1) {
                                newTekCalendarVO.setWk11Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk11Day2() != null && newTekCalendarVO1.getWk11Day2() != null && newTekCalendarVO.getWk11Day2().compareTo(newTekCalendarVO1.getWk11Day2()) == -1) {
                                newTekCalendarVO.setWk11Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk11Day3() != null && newTekCalendarVO1.getWk11Day3() != null && newTekCalendarVO.getWk11Day3().compareTo(newTekCalendarVO1.getWk11Day3()) == -1) {
                                newTekCalendarVO.setWk11Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk11Day4() != null && newTekCalendarVO1.getWk11Day4() != null && newTekCalendarVO.getWk11Day4().compareTo(newTekCalendarVO1.getWk11Day4()) == -1) {
                                newTekCalendarVO.setWk11Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk11Day5() != null && newTekCalendarVO1.getWk11Day5() != null && newTekCalendarVO.getWk11Day5().compareTo(newTekCalendarVO1.getWk11Day5()) == -1) {
                                newTekCalendarVO.setWk11Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk11Day6() != null && newTekCalendarVO1.getWk11Day6() != null && newTekCalendarVO.getWk11Day6().compareTo(newTekCalendarVO1.getWk11Day6()) == -1) {
                                newTekCalendarVO.setWk11Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk11Day7() != null && newTekCalendarVO1.getWk11Day7() != null && newTekCalendarVO.getWk11Day7().compareTo(newTekCalendarVO1.getWk11Day7()) == -1) {
                                newTekCalendarVO.setWk11Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk12Day1() != null && newTekCalendarVO1.getWk12Day1() != null && newTekCalendarVO.getWk12Day1().compareTo(newTekCalendarVO1.getWk12Day1()) == -1) {
                                newTekCalendarVO.setWk12Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk12Day2() != null && newTekCalendarVO1.getWk12Day2() != null && newTekCalendarVO.getWk12Day2().compareTo(newTekCalendarVO1.getWk12Day2()) == -1) {
                                newTekCalendarVO.setWk12Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk12Day3() != null && newTekCalendarVO1.getWk12Day3() != null && newTekCalendarVO.getWk12Day3().compareTo(newTekCalendarVO1.getWk12Day3()) == -1) {
                                newTekCalendarVO.setWk12Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk12Day4() != null && newTekCalendarVO1.getWk12Day4() != null && newTekCalendarVO.getWk12Day4().compareTo(newTekCalendarVO1.getWk12Day4()) == -1) {
                                newTekCalendarVO.setWk12Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk12Day5() != null && newTekCalendarVO1.getWk12Day5() != null && newTekCalendarVO.getWk12Day5().compareTo(newTekCalendarVO1.getWk12Day5()) == -1) {
                                newTekCalendarVO.setWk12Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk12Day6() != null && newTekCalendarVO1.getWk12Day6() != null && newTekCalendarVO.getWk12Day6().compareTo(newTekCalendarVO1.getWk12Day6()) == -1) {
                                newTekCalendarVO.setWk12Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk12Day7() != null && newTekCalendarVO1.getWk12Day7() != null && newTekCalendarVO.getWk12Day7().compareTo(newTekCalendarVO1.getWk12Day7()) == -1) {
                                newTekCalendarVO.setWk12Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk13Day1() != null && newTekCalendarVO1.getWk13Day1() != null && newTekCalendarVO.getWk13Day1().compareTo(newTekCalendarVO1.getWk13Day1()) == -1) {
                                newTekCalendarVO.setWk13Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk13Day2() != null && newTekCalendarVO1.getWk13Day2() != null && newTekCalendarVO.getWk13Day2().compareTo(newTekCalendarVO1.getWk13Day2()) == -1) {
                                newTekCalendarVO.setWk13Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk13Day3() != null && newTekCalendarVO1.getWk13Day3() != null && newTekCalendarVO.getWk13Day3().compareTo(newTekCalendarVO1.getWk13Day3()) == -1) {
                                newTekCalendarVO.setWk13Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk13Day4() != null && newTekCalendarVO1.getWk13Day4() != null && newTekCalendarVO.getWk13Day4().compareTo(newTekCalendarVO1.getWk13Day4()) == -1) {
                                newTekCalendarVO.setWk13Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk13Day5() != null && newTekCalendarVO1.getWk13Day5() != null && newTekCalendarVO.getWk13Day5().compareTo(newTekCalendarVO1.getWk13Day5()) == -1) {
                                newTekCalendarVO.setWk13Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk13Day6() != null && newTekCalendarVO1.getWk13Day6() != null && newTekCalendarVO.getWk13Day6().compareTo(newTekCalendarVO1.getWk13Day6()) == -1) {
                                newTekCalendarVO.setWk13Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk13Day7() != null && newTekCalendarVO1.getWk13Day7() != null && newTekCalendarVO.getWk13Day7().compareTo(newTekCalendarVO1.getWk13Day7()) == -1) {
                                newTekCalendarVO.setWk13Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk14Day1() != null && newTekCalendarVO1.getWk14Day1() != null && newTekCalendarVO.getWk14Day1().compareTo(newTekCalendarVO1.getWk14Day1()) == -1) {
                                newTekCalendarVO.setWk14Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk14Day2() != null && newTekCalendarVO1.getWk14Day2() != null && newTekCalendarVO.getWk14Day2().compareTo(newTekCalendarVO1.getWk14Day2()) == -1) {
                                newTekCalendarVO.setWk14Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk14Day3() != null && newTekCalendarVO1.getWk14Day3() != null && newTekCalendarVO.getWk14Day3().compareTo(newTekCalendarVO1.getWk14Day3()) == -1) {
                                newTekCalendarVO.setWk14Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk14Day4() != null && newTekCalendarVO1.getWk14Day4() != null && newTekCalendarVO.getWk14Day4().compareTo(newTekCalendarVO1.getWk14Day4()) == -1) {
                                newTekCalendarVO.setWk14Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk14Day5() != null && newTekCalendarVO1.getWk14Day5() != null && newTekCalendarVO.getWk14Day5().compareTo(newTekCalendarVO1.getWk14Day5()) == -1) {
                                newTekCalendarVO.setWk14Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk14Day6() != null && newTekCalendarVO1.getWk14Day6() != null && newTekCalendarVO.getWk14Day6().compareTo(newTekCalendarVO1.getWk14Day6()) == -1) {
                                newTekCalendarVO.setWk14Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk14Day7() != null && newTekCalendarVO1.getWk14Day7() != null && newTekCalendarVO.getWk14Day7().compareTo(newTekCalendarVO1.getWk14Day7()) == -1) {
                                newTekCalendarVO.setWk14Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk15Day1() != null && newTekCalendarVO1.getWk15Day1() != null && newTekCalendarVO.getWk15Day1().compareTo(newTekCalendarVO1.getWk15Day1()) == -1) {
                                newTekCalendarVO.setWk15Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk15Day2() != null && newTekCalendarVO1.getWk15Day2() != null && newTekCalendarVO.getWk15Day2().compareTo(newTekCalendarVO1.getWk15Day2()) == -1) {
                                newTekCalendarVO.setWk15Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk15Day3() != null && newTekCalendarVO1.getWk15Day3() != null && newTekCalendarVO.getWk15Day3().compareTo(newTekCalendarVO1.getWk15Day3()) == -1) {
                                newTekCalendarVO.setWk15Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk15Day4() != null && newTekCalendarVO1.getWk15Day4() != null && newTekCalendarVO.getWk15Day4().compareTo(newTekCalendarVO1.getWk15Day4()) == -1) {
                                newTekCalendarVO.setWk15Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk15Day5() != null && newTekCalendarVO1.getWk15Day5() != null && newTekCalendarVO.getWk15Day5().compareTo(newTekCalendarVO1.getWk15Day5()) == -1) {
                                newTekCalendarVO.setWk15Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk15Day6() != null && newTekCalendarVO1.getWk15Day6() != null && newTekCalendarVO.getWk15Day6().compareTo(newTekCalendarVO1.getWk15Day6()) == -1) {
                                newTekCalendarVO.setWk15Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk15Day7() != null && newTekCalendarVO1.getWk15Day7() != null && newTekCalendarVO.getWk15Day7().compareTo(newTekCalendarVO1.getWk15Day7()) == -1) {
                                newTekCalendarVO.setWk15Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk16Day1() != null && newTekCalendarVO1.getWk16Day1() != null && newTekCalendarVO.getWk16Day1().compareTo(newTekCalendarVO1.getWk16Day1()) == -1) {
                                newTekCalendarVO.setWk16Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk16Day2() != null && newTekCalendarVO1.getWk16Day2() != null && newTekCalendarVO.getWk16Day2().compareTo(newTekCalendarVO1.getWk16Day2()) == -1) {
                                newTekCalendarVO.setWk16Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk16Day3() != null && newTekCalendarVO1.getWk16Day3() != null && newTekCalendarVO.getWk16Day3().compareTo(newTekCalendarVO1.getWk16Day3()) == -1) {
                                newTekCalendarVO.setWk16Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk16Day4() != null && newTekCalendarVO1.getWk16Day4() != null && newTekCalendarVO.getWk16Day4().compareTo(newTekCalendarVO1.getWk16Day4()) == -1) {
                                newTekCalendarVO.setWk16Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk16Day5() != null && newTekCalendarVO1.getWk16Day5() != null && newTekCalendarVO.getWk16Day5().compareTo(newTekCalendarVO1.getWk16Day5()) == -1) {
                                newTekCalendarVO.setWk16Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk16Day6() != null && newTekCalendarVO1.getWk16Day6() != null && newTekCalendarVO.getWk16Day6().compareTo(newTekCalendarVO1.getWk16Day6()) == -1) {
                                newTekCalendarVO.setWk16Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk16Day7() != null && newTekCalendarVO1.getWk16Day7() != null && newTekCalendarVO.getWk16Day7().compareTo(newTekCalendarVO1.getWk16Day7()) == -1) {
                                newTekCalendarVO.setWk16Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk17Day1() != null && newTekCalendarVO1.getWk17Day1() != null && newTekCalendarVO.getWk17Day1().compareTo(newTekCalendarVO1.getWk17Day1()) == -1) {
                                newTekCalendarVO.setWk17Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk17Day2() != null && newTekCalendarVO1.getWk17Day2() != null && newTekCalendarVO.getWk17Day2().compareTo(newTekCalendarVO1.getWk17Day2()) == -1) {
                                newTekCalendarVO.setWk17Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk17Day3() != null && newTekCalendarVO1.getWk17Day3() != null && newTekCalendarVO.getWk17Day3().compareTo(newTekCalendarVO1.getWk17Day3()) == -1) {
                                newTekCalendarVO.setWk17Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk17Day4() != null && newTekCalendarVO1.getWk17Day4() != null && newTekCalendarVO.getWk17Day4().compareTo(newTekCalendarVO1.getWk17Day4()) == -1) {
                                newTekCalendarVO.setWk17Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk17Day5() != null && newTekCalendarVO1.getWk17Day5() != null && newTekCalendarVO.getWk17Day5().compareTo(newTekCalendarVO1.getWk17Day5()) == -1) {
                                newTekCalendarVO.setWk17Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk17Day6() != null && newTekCalendarVO1.getWk17Day6() != null && newTekCalendarVO.getWk17Day6().compareTo(newTekCalendarVO1.getWk17Day6()) == -1) {
                                newTekCalendarVO.setWk17Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk17Day7() != null && newTekCalendarVO1.getWk17Day7() != null && newTekCalendarVO.getWk17Day7().compareTo(newTekCalendarVO1.getWk17Day7()) == -1) {
                                newTekCalendarVO.setWk17Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk18Day1() != null && newTekCalendarVO1.getWk18Day1() != null && newTekCalendarVO.getWk18Day1().compareTo(newTekCalendarVO1.getWk18Day1()) == -1) {
                                newTekCalendarVO.setWk18Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk18Day2() != null && newTekCalendarVO1.getWk18Day2() != null && newTekCalendarVO.getWk18Day2().compareTo(newTekCalendarVO1.getWk18Day2()) == -1) {
                                newTekCalendarVO.setWk18Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk18Day3() != null && newTekCalendarVO1.getWk18Day3() != null && newTekCalendarVO.getWk18Day3().compareTo(newTekCalendarVO1.getWk18Day3()) == -1) {
                                newTekCalendarVO.setWk18Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk18Day4() != null && newTekCalendarVO1.getWk18Day4() != null && newTekCalendarVO.getWk18Day4().compareTo(newTekCalendarVO1.getWk18Day4()) == -1) {
                                newTekCalendarVO.setWk18Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk18Day5() != null && newTekCalendarVO1.getWk18Day5() != null && newTekCalendarVO.getWk18Day5().compareTo(newTekCalendarVO1.getWk18Day5()) == -1) {
                                newTekCalendarVO.setWk18Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk18Day6() != null && newTekCalendarVO1.getWk18Day6() != null && newTekCalendarVO.getWk18Day6().compareTo(newTekCalendarVO1.getWk18Day6()) == -1) {
                                newTekCalendarVO.setWk18Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk18Day7() != null && newTekCalendarVO1.getWk18Day7() != null && newTekCalendarVO.getWk18Day7().compareTo(newTekCalendarVO1.getWk18Day7()) == -1) {
                                newTekCalendarVO.setWk18Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk19Day1() != null && newTekCalendarVO1.getWk19Day1() != null && newTekCalendarVO.getWk19Day1().compareTo(newTekCalendarVO1.getWk19Day1()) == -1) {
                                newTekCalendarVO.setWk19Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk19Day2() != null && newTekCalendarVO1.getWk19Day2() != null && newTekCalendarVO.getWk19Day2().compareTo(newTekCalendarVO1.getWk19Day2()) == -1) {
                                newTekCalendarVO.setWk19Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk19Day3() != null && newTekCalendarVO1.getWk19Day3() != null && newTekCalendarVO.getWk19Day3().compareTo(newTekCalendarVO1.getWk19Day3()) == -1) {
                                newTekCalendarVO.setWk19Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk19Day4() != null && newTekCalendarVO1.getWk19Day4() != null && newTekCalendarVO.getWk19Day4().compareTo(newTekCalendarVO1.getWk19Day4()) == -1) {
                                newTekCalendarVO.setWk19Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk19Day5() != null && newTekCalendarVO1.getWk19Day5() != null && newTekCalendarVO.getWk19Day5().compareTo(newTekCalendarVO1.getWk19Day5()) == -1) {
                                newTekCalendarVO.setWk19Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk19Day6() != null && newTekCalendarVO1.getWk19Day6() != null && newTekCalendarVO.getWk19Day6().compareTo(newTekCalendarVO1.getWk19Day6()) == -1) {
                                newTekCalendarVO.setWk19Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk19Day7() != null && newTekCalendarVO1.getWk19Day7() != null && newTekCalendarVO.getWk19Day7().compareTo(newTekCalendarVO1.getWk19Day7()) == -1) {
                                newTekCalendarVO.setWk19Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk20Day1() != null && newTekCalendarVO1.getWk20Day1() != null && newTekCalendarVO.getWk20Day1().compareTo(newTekCalendarVO1.getWk20Day1()) == -1) {
                                newTekCalendarVO.setWk20Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk20Day2() != null && newTekCalendarVO1.getWk20Day2() != null && newTekCalendarVO.getWk20Day2().compareTo(newTekCalendarVO1.getWk20Day2()) == -1) {
                                newTekCalendarVO.setWk20Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk20Day3() != null && newTekCalendarVO1.getWk20Day3() != null && newTekCalendarVO.getWk20Day3().compareTo(newTekCalendarVO1.getWk20Day3()) == -1) {
                                newTekCalendarVO.setWk20Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk20Day4() != null && newTekCalendarVO1.getWk20Day4() != null && newTekCalendarVO.getWk20Day4().compareTo(newTekCalendarVO1.getWk20Day4()) == -1) {
                                newTekCalendarVO.setWk20Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk20Day5() != null && newTekCalendarVO1.getWk20Day5() != null && newTekCalendarVO.getWk20Day5().compareTo(newTekCalendarVO1.getWk20Day5()) == -1) {
                                newTekCalendarVO.setWk20Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk20Day6() != null && newTekCalendarVO1.getWk20Day6() != null && newTekCalendarVO.getWk20Day6().compareTo(newTekCalendarVO1.getWk20Day6()) == -1) {
                                newTekCalendarVO.setWk20Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk20Day7() != null && newTekCalendarVO1.getWk20Day7() != null && newTekCalendarVO.getWk20Day7().compareTo(newTekCalendarVO1.getWk20Day7()) == -1) {
                                newTekCalendarVO.setWk20Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk21Day1() != null && newTekCalendarVO1.getWk21Day1() != null && newTekCalendarVO.getWk21Day1().compareTo(newTekCalendarVO1.getWk21Day1()) == -1) {
                                newTekCalendarVO.setWk21Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk21Day2() != null && newTekCalendarVO1.getWk21Day2() != null && newTekCalendarVO.getWk21Day2().compareTo(newTekCalendarVO1.getWk21Day2()) == -1) {
                                newTekCalendarVO.setWk21Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk21Day3() != null && newTekCalendarVO1.getWk21Day3() != null && newTekCalendarVO.getWk21Day3().compareTo(newTekCalendarVO1.getWk21Day3()) == -1) {
                                newTekCalendarVO.setWk21Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk21Day4() != null && newTekCalendarVO1.getWk21Day4() != null && newTekCalendarVO.getWk21Day4().compareTo(newTekCalendarVO1.getWk21Day4()) == -1) {
                                newTekCalendarVO.setWk21Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk21Day5() != null && newTekCalendarVO1.getWk21Day5() != null && newTekCalendarVO.getWk21Day5().compareTo(newTekCalendarVO1.getWk21Day5()) == -1) {
                                newTekCalendarVO.setWk21Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk21Day6() != null && newTekCalendarVO1.getWk21Day6() != null && newTekCalendarVO.getWk21Day6().compareTo(newTekCalendarVO1.getWk21Day6()) == -1) {
                                newTekCalendarVO.setWk21Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk21Day7() != null && newTekCalendarVO1.getWk21Day7() != null && newTekCalendarVO.getWk21Day7().compareTo(newTekCalendarVO1.getWk21Day7()) == -1) {
                                newTekCalendarVO.setWk21Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk22Day1() != null && newTekCalendarVO1.getWk22Day1() != null && newTekCalendarVO.getWk22Day1().compareTo(newTekCalendarVO1.getWk22Day1()) == -1) {
                                newTekCalendarVO.setWk22Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk22Day2() != null && newTekCalendarVO1.getWk22Day2() != null && newTekCalendarVO.getWk22Day2().compareTo(newTekCalendarVO1.getWk22Day2()) == -1) {
                                newTekCalendarVO.setWk22Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk22Day3() != null && newTekCalendarVO1.getWk22Day3() != null && newTekCalendarVO.getWk22Day3().compareTo(newTekCalendarVO1.getWk22Day3()) == -1) {
                                newTekCalendarVO.setWk22Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk22Day4() != null && newTekCalendarVO1.getWk22Day4() != null && newTekCalendarVO.getWk22Day4().compareTo(newTekCalendarVO1.getWk22Day4()) == -1) {
                                newTekCalendarVO.setWk22Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk22Day5() != null && newTekCalendarVO1.getWk22Day5() != null && newTekCalendarVO.getWk22Day5().compareTo(newTekCalendarVO1.getWk22Day5()) == -1) {
                                newTekCalendarVO.setWk22Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk22Day6() != null && newTekCalendarVO1.getWk22Day6() != null && newTekCalendarVO.getWk22Day6().compareTo(newTekCalendarVO1.getWk22Day6()) == -1) {
                                newTekCalendarVO.setWk22Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk22Day7() != null && newTekCalendarVO1.getWk22Day7() != null && newTekCalendarVO.getWk22Day7().compareTo(newTekCalendarVO1.getWk22Day7()) == -1) {
                                newTekCalendarVO.setWk22Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk23Day1() != null && newTekCalendarVO1.getWk23Day1() != null && newTekCalendarVO.getWk23Day1().compareTo(newTekCalendarVO1.getWk23Day1()) == -1) {
                                newTekCalendarVO.setWk23Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk23Day2() != null && newTekCalendarVO1.getWk23Day2() != null && newTekCalendarVO.getWk23Day2().compareTo(newTekCalendarVO1.getWk23Day2()) == -1) {
                                newTekCalendarVO.setWk23Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk23Day3() != null && newTekCalendarVO1.getWk23Day3() != null && newTekCalendarVO.getWk23Day3().compareTo(newTekCalendarVO1.getWk23Day3()) == -1) {
                                newTekCalendarVO.setWk23Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk23Day4() != null && newTekCalendarVO1.getWk23Day4() != null && newTekCalendarVO.getWk23Day4().compareTo(newTekCalendarVO1.getWk23Day4()) == -1) {
                                newTekCalendarVO.setWk23Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk23Day5() != null && newTekCalendarVO1.getWk23Day5() != null && newTekCalendarVO.getWk23Day5().compareTo(newTekCalendarVO1.getWk23Day5()) == -1) {
                                newTekCalendarVO.setWk23Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk23Day6() != null && newTekCalendarVO1.getWk23Day6() != null && newTekCalendarVO.getWk23Day6().compareTo(newTekCalendarVO1.getWk23Day6()) == -1) {
                                newTekCalendarVO.setWk23Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk23Day7() != null && newTekCalendarVO1.getWk23Day7() != null && newTekCalendarVO.getWk23Day7().compareTo(newTekCalendarVO1.getWk23Day7()) == -1) {
                                newTekCalendarVO.setWk23Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk24Day1() != null && newTekCalendarVO1.getWk24Day1() != null && newTekCalendarVO.getWk24Day1().compareTo(newTekCalendarVO1.getWk24Day1()) == -1) {
                                newTekCalendarVO.setWk24Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk24Day2() != null && newTekCalendarVO1.getWk24Day2() != null && newTekCalendarVO.getWk24Day2().compareTo(newTekCalendarVO1.getWk24Day2()) == -1) {
                                newTekCalendarVO.setWk24Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk24Day3() != null && newTekCalendarVO1.getWk24Day3() != null && newTekCalendarVO.getWk24Day3().compareTo(newTekCalendarVO1.getWk24Day3()) == -1) {
                                newTekCalendarVO.setWk24Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk24Day4() != null && newTekCalendarVO1.getWk24Day4() != null && newTekCalendarVO.getWk24Day4().compareTo(newTekCalendarVO1.getWk24Day4()) == -1) {
                                newTekCalendarVO.setWk24Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk24Day5() != null && newTekCalendarVO1.getWk24Day5() != null && newTekCalendarVO.getWk24Day5().compareTo(newTekCalendarVO1.getWk24Day5()) == -1) {
                                newTekCalendarVO.setWk24Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk24Day6() != null && newTekCalendarVO1.getWk24Day6() != null && newTekCalendarVO.getWk24Day6().compareTo(newTekCalendarVO1.getWk24Day6()) == -1) {
                                newTekCalendarVO.setWk24Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk24Day7() != null && newTekCalendarVO1.getWk24Day7() != null && newTekCalendarVO.getWk24Day7().compareTo(newTekCalendarVO1.getWk24Day7()) == -1) {
                                newTekCalendarVO.setWk24Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk25Day1() != null && newTekCalendarVO1.getWk25Day1() != null && newTekCalendarVO.getWk25Day1().compareTo(newTekCalendarVO1.getWk25Day1()) == -1) {
                                newTekCalendarVO.setWk25Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk25Day2() != null && newTekCalendarVO1.getWk25Day2() != null && newTekCalendarVO.getWk25Day2().compareTo(newTekCalendarVO1.getWk25Day2()) == -1) {
                                newTekCalendarVO.setWk25Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk25Day3() != null && newTekCalendarVO1.getWk25Day3() != null && newTekCalendarVO.getWk25Day3().compareTo(newTekCalendarVO1.getWk25Day3()) == -1) {
                                newTekCalendarVO.setWk25Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk25Day4() != null && newTekCalendarVO1.getWk25Day4() != null && newTekCalendarVO.getWk25Day4().compareTo(newTekCalendarVO1.getWk25Day4()) == -1) {
                                newTekCalendarVO.setWk25Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk25Day5() != null && newTekCalendarVO1.getWk25Day5() != null && newTekCalendarVO.getWk25Day5().compareTo(newTekCalendarVO1.getWk25Day5()) == -1) {
                                newTekCalendarVO.setWk25Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk25Day6() != null && newTekCalendarVO1.getWk25Day6() != null && newTekCalendarVO.getWk25Day6().compareTo(newTekCalendarVO1.getWk25Day6()) == -1) {
                                newTekCalendarVO.setWk25Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk25Day7() != null && newTekCalendarVO1.getWk25Day7() != null && newTekCalendarVO.getWk25Day7().compareTo(newTekCalendarVO1.getWk25Day7()) == -1) {
                                newTekCalendarVO.setWk25Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk26Day1() != null && newTekCalendarVO1.getWk26Day1() != null && newTekCalendarVO.getWk26Day1().compareTo(newTekCalendarVO1.getWk26Day1()) == -1) {
                                newTekCalendarVO.setWk26Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk26Day2() != null && newTekCalendarVO1.getWk26Day2() != null && newTekCalendarVO.getWk26Day2().compareTo(newTekCalendarVO1.getWk26Day2()) == -1) {
                                newTekCalendarVO.setWk26Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk26Day3() != null && newTekCalendarVO1.getWk26Day3() != null && newTekCalendarVO.getWk26Day3().compareTo(newTekCalendarVO1.getWk26Day3()) == -1) {
                                newTekCalendarVO.setWk26Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk26Day4() != null && newTekCalendarVO1.getWk26Day4() != null && newTekCalendarVO.getWk26Day4().compareTo(newTekCalendarVO1.getWk26Day4()) == -1) {
                                newTekCalendarVO.setWk26Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk26Day5() != null && newTekCalendarVO1.getWk26Day5() != null && newTekCalendarVO.getWk26Day5().compareTo(newTekCalendarVO1.getWk26Day5()) == -1) {
                                newTekCalendarVO.setWk26Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk26Day6() != null && newTekCalendarVO1.getWk26Day6() != null && newTekCalendarVO.getWk26Day6().compareTo(newTekCalendarVO1.getWk26Day6()) == -1) {
                                newTekCalendarVO.setWk26Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk26Day7() != null && newTekCalendarVO1.getWk26Day7() != null && newTekCalendarVO.getWk26Day7().compareTo(newTekCalendarVO1.getWk26Day7()) == -1) {
                                newTekCalendarVO.setWk26Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk27Day1() != null && newTekCalendarVO1.getWk27Day1() != null && newTekCalendarVO.getWk27Day1().compareTo(newTekCalendarVO1.getWk27Day1()) == -1) {
                                newTekCalendarVO.setWk27Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk27Day2() != null && newTekCalendarVO1.getWk27Day2() != null && newTekCalendarVO.getWk27Day2().compareTo(newTekCalendarVO1.getWk27Day2()) == -1) {
                                newTekCalendarVO.setWk27Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk27Day3() != null && newTekCalendarVO1.getWk27Day3() != null && newTekCalendarVO.getWk27Day3().compareTo(newTekCalendarVO1.getWk27Day3()) == -1) {
                                newTekCalendarVO.setWk27Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk27Day4() != null && newTekCalendarVO1.getWk27Day4() != null && newTekCalendarVO.getWk27Day4().compareTo(newTekCalendarVO1.getWk27Day4()) == -1) {
                                newTekCalendarVO.setWk27Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk27Day5() != null && newTekCalendarVO1.getWk27Day5() != null && newTekCalendarVO.getWk27Day5().compareTo(newTekCalendarVO1.getWk27Day5()) == -1) {
                                newTekCalendarVO.setWk27Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk27Day6() != null && newTekCalendarVO1.getWk27Day6() != null && newTekCalendarVO.getWk27Day6().compareTo(newTekCalendarVO1.getWk27Day6()) == -1) {
                                newTekCalendarVO.setWk27Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk27Day7() != null && newTekCalendarVO1.getWk27Day7() != null && newTekCalendarVO.getWk27Day7().compareTo(newTekCalendarVO1.getWk27Day7()) == -1) {
                                newTekCalendarVO.setWk27Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk28Day1() != null && newTekCalendarVO1.getWk28Day1() != null && newTekCalendarVO.getWk28Day1().compareTo(newTekCalendarVO1.getWk28Day1()) == -1) {
                                newTekCalendarVO.setWk28Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk28Day2() != null && newTekCalendarVO1.getWk28Day2() != null && newTekCalendarVO.getWk28Day2().compareTo(newTekCalendarVO1.getWk28Day2()) == -1) {
                                newTekCalendarVO.setWk28Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk28Day3() != null && newTekCalendarVO1.getWk28Day3() != null && newTekCalendarVO.getWk28Day3().compareTo(newTekCalendarVO1.getWk28Day3()) == -1) {
                                newTekCalendarVO.setWk28Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk28Day4() != null && newTekCalendarVO1.getWk28Day4() != null && newTekCalendarVO.getWk28Day4().compareTo(newTekCalendarVO1.getWk28Day4()) == -1) {
                                newTekCalendarVO.setWk28Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk28Day5() != null && newTekCalendarVO1.getWk28Day5() != null && newTekCalendarVO.getWk28Day5().compareTo(newTekCalendarVO1.getWk28Day5()) == -1) {
                                newTekCalendarVO.setWk28Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk28Day6() != null && newTekCalendarVO1.getWk28Day6() != null && newTekCalendarVO.getWk28Day6().compareTo(newTekCalendarVO1.getWk28Day6()) == -1) {
                                newTekCalendarVO.setWk28Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk28Day7() != null && newTekCalendarVO1.getWk28Day7() != null && newTekCalendarVO.getWk28Day7().compareTo(newTekCalendarVO1.getWk28Day7()) == -1) {
                                newTekCalendarVO.setWk28Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk29Day1() != null && newTekCalendarVO1.getWk29Day1() != null && newTekCalendarVO.getWk29Day1().compareTo(newTekCalendarVO1.getWk29Day1()) == -1) {
                                newTekCalendarVO.setWk29Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk29Day2() != null && newTekCalendarVO1.getWk29Day2() != null && newTekCalendarVO.getWk29Day2().compareTo(newTekCalendarVO1.getWk29Day2()) == -1) {
                                newTekCalendarVO.setWk29Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk29Day3() != null && newTekCalendarVO1.getWk29Day3() != null && newTekCalendarVO.getWk29Day3().compareTo(newTekCalendarVO1.getWk29Day3()) == -1) {
                                newTekCalendarVO.setWk29Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk29Day4() != null && newTekCalendarVO1.getWk29Day4() != null && newTekCalendarVO.getWk29Day4().compareTo(newTekCalendarVO1.getWk29Day4()) == -1) {
                                newTekCalendarVO.setWk29Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk29Day5() != null && newTekCalendarVO1.getWk29Day5() != null && newTekCalendarVO.getWk29Day5().compareTo(newTekCalendarVO1.getWk29Day5()) == -1) {
                                newTekCalendarVO.setWk29Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk29Day6() != null && newTekCalendarVO1.getWk29Day6() != null && newTekCalendarVO.getWk29Day6().compareTo(newTekCalendarVO1.getWk29Day6()) == -1) {
                                newTekCalendarVO.setWk29Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk29Day7() != null && newTekCalendarVO1.getWk29Day7() != null && newTekCalendarVO.getWk29Day7().compareTo(newTekCalendarVO1.getWk29Day7()) == -1) {
                                newTekCalendarVO.setWk29Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk30Day1() != null && newTekCalendarVO1.getWk30Day1() != null && newTekCalendarVO.getWk30Day1().compareTo(newTekCalendarVO1.getWk30Day1()) == -1) {
                                newTekCalendarVO.setWk30Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk30Day2() != null && newTekCalendarVO1.getWk30Day2() != null && newTekCalendarVO.getWk30Day2().compareTo(newTekCalendarVO1.getWk30Day2()) == -1) {
                                newTekCalendarVO.setWk30Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk30Day3() != null && newTekCalendarVO1.getWk30Day3() != null && newTekCalendarVO.getWk30Day3().compareTo(newTekCalendarVO1.getWk30Day3()) == -1) {
                                newTekCalendarVO.setWk30Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk30Day4() != null && newTekCalendarVO1.getWk30Day4() != null && newTekCalendarVO.getWk30Day4().compareTo(newTekCalendarVO1.getWk30Day4()) == -1) {
                                newTekCalendarVO.setWk30Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk30Day5() != null && newTekCalendarVO1.getWk30Day5() != null && newTekCalendarVO.getWk30Day5().compareTo(newTekCalendarVO1.getWk30Day5()) == -1) {
                                newTekCalendarVO.setWk30Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk30Day6() != null && newTekCalendarVO1.getWk30Day6() != null && newTekCalendarVO.getWk30Day6().compareTo(newTekCalendarVO1.getWk30Day6()) == -1) {
                                newTekCalendarVO.setWk30Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk30Day7() != null && newTekCalendarVO1.getWk30Day7() != null && newTekCalendarVO.getWk30Day7().compareTo(newTekCalendarVO1.getWk30Day7()) == -1) {
                                newTekCalendarVO.setWk30Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk31Day1() != null && newTekCalendarVO1.getWk31Day1() != null && newTekCalendarVO.getWk31Day1().compareTo(newTekCalendarVO1.getWk31Day1()) == -1) {
                                newTekCalendarVO.setWk31Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk31Day2() != null && newTekCalendarVO1.getWk31Day2() != null && newTekCalendarVO.getWk31Day2().compareTo(newTekCalendarVO1.getWk31Day2()) == -1) {
                                newTekCalendarVO.setWk31Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk31Day3() != null && newTekCalendarVO1.getWk31Day3() != null && newTekCalendarVO.getWk31Day3().compareTo(newTekCalendarVO1.getWk31Day3()) == -1) {
                                newTekCalendarVO.setWk31Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk31Day4() != null && newTekCalendarVO1.getWk31Day4() != null && newTekCalendarVO.getWk31Day4().compareTo(newTekCalendarVO1.getWk31Day4()) == -1) {
                                newTekCalendarVO.setWk31Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk31Day5() != null && newTekCalendarVO1.getWk31Day5() != null && newTekCalendarVO.getWk31Day5().compareTo(newTekCalendarVO1.getWk31Day5()) == -1) {
                                newTekCalendarVO.setWk31Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk31Day6() != null && newTekCalendarVO1.getWk31Day6() != null && newTekCalendarVO.getWk31Day6().compareTo(newTekCalendarVO1.getWk31Day6()) == -1) {
                                newTekCalendarVO.setWk31Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk31Day7() != null && newTekCalendarVO1.getWk31Day7() != null && newTekCalendarVO.getWk31Day7().compareTo(newTekCalendarVO1.getWk31Day7()) == -1) {
                                newTekCalendarVO.setWk31Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk32Day1() != null && newTekCalendarVO1.getWk32Day1() != null && newTekCalendarVO.getWk32Day1().compareTo(newTekCalendarVO1.getWk32Day1()) == -1) {
                                newTekCalendarVO.setWk32Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk32Day2() != null && newTekCalendarVO1.getWk32Day2() != null && newTekCalendarVO.getWk32Day2().compareTo(newTekCalendarVO1.getWk32Day2()) == -1) {
                                newTekCalendarVO.setWk32Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk32Day3() != null && newTekCalendarVO1.getWk32Day3() != null && newTekCalendarVO.getWk32Day3().compareTo(newTekCalendarVO1.getWk32Day3()) == -1) {
                                newTekCalendarVO.setWk32Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk32Day4() != null && newTekCalendarVO1.getWk32Day4() != null && newTekCalendarVO.getWk32Day4().compareTo(newTekCalendarVO1.getWk32Day4()) == -1) {
                                newTekCalendarVO.setWk32Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk32Day5() != null && newTekCalendarVO1.getWk32Day5() != null && newTekCalendarVO.getWk32Day5().compareTo(newTekCalendarVO1.getWk32Day5()) == -1) {
                                newTekCalendarVO.setWk32Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk32Day6() != null && newTekCalendarVO1.getWk32Day6() != null && newTekCalendarVO.getWk32Day6().compareTo(newTekCalendarVO1.getWk32Day6()) == -1) {
                                newTekCalendarVO.setWk32Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk32Day7() != null && newTekCalendarVO1.getWk32Day7() != null && newTekCalendarVO.getWk32Day7().compareTo(newTekCalendarVO1.getWk32Day7()) == -1) {
                                newTekCalendarVO.setWk32Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk33Day1() != null && newTekCalendarVO1.getWk33Day1() != null && newTekCalendarVO.getWk33Day1().compareTo(newTekCalendarVO1.getWk33Day1()) == -1) {
                                newTekCalendarVO.setWk33Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk33Day2() != null && newTekCalendarVO1.getWk33Day2() != null && newTekCalendarVO.getWk33Day2().compareTo(newTekCalendarVO1.getWk33Day2()) == -1) {
                                newTekCalendarVO.setWk33Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk33Day3() != null && newTekCalendarVO1.getWk33Day3() != null && newTekCalendarVO.getWk33Day3().compareTo(newTekCalendarVO1.getWk33Day3()) == -1) {
                                newTekCalendarVO.setWk33Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk33Day4() != null && newTekCalendarVO1.getWk33Day4() != null && newTekCalendarVO.getWk33Day4().compareTo(newTekCalendarVO1.getWk33Day4()) == -1) {
                                newTekCalendarVO.setWk33Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk33Day5() != null && newTekCalendarVO1.getWk33Day5() != null && newTekCalendarVO.getWk33Day5().compareTo(newTekCalendarVO1.getWk33Day5()) == -1) {
                                newTekCalendarVO.setWk33Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk33Day6() != null && newTekCalendarVO1.getWk33Day6() != null && newTekCalendarVO.getWk33Day6().compareTo(newTekCalendarVO1.getWk33Day6()) == -1) {
                                newTekCalendarVO.setWk33Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk33Day7() != null && newTekCalendarVO1.getWk33Day7() != null && newTekCalendarVO.getWk33Day7().compareTo(newTekCalendarVO1.getWk33Day7()) == -1) {
                                newTekCalendarVO.setWk33Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk34Day1() != null && newTekCalendarVO1.getWk34Day1() != null && newTekCalendarVO.getWk34Day1().compareTo(newTekCalendarVO1.getWk34Day1()) == -1) {
                                newTekCalendarVO.setWk34Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk34Day2() != null && newTekCalendarVO1.getWk34Day2() != null && newTekCalendarVO.getWk34Day2().compareTo(newTekCalendarVO1.getWk34Day2()) == -1) {
                                newTekCalendarVO.setWk34Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk34Day3() != null && newTekCalendarVO1.getWk34Day3() != null && newTekCalendarVO.getWk34Day3().compareTo(newTekCalendarVO1.getWk34Day3()) == -1) {
                                newTekCalendarVO.setWk34Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk34Day4() != null && newTekCalendarVO1.getWk34Day4() != null && newTekCalendarVO.getWk34Day4().compareTo(newTekCalendarVO1.getWk34Day4()) == -1) {
                                newTekCalendarVO.setWk34Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk34Day5() != null && newTekCalendarVO1.getWk34Day5() != null && newTekCalendarVO.getWk34Day5().compareTo(newTekCalendarVO1.getWk34Day5()) == -1) {
                                newTekCalendarVO.setWk34Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk34Day6() != null && newTekCalendarVO1.getWk34Day6() != null && newTekCalendarVO.getWk34Day6().compareTo(newTekCalendarVO1.getWk34Day6()) == -1) {
                                newTekCalendarVO.setWk34Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk34Day7() != null && newTekCalendarVO1.getWk34Day7() != null && newTekCalendarVO.getWk34Day7().compareTo(newTekCalendarVO1.getWk34Day7()) == -1) {
                                newTekCalendarVO.setWk34Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk35Day1() != null && newTekCalendarVO1.getWk35Day1() != null && newTekCalendarVO.getWk35Day1().compareTo(newTekCalendarVO1.getWk35Day1()) == -1) {
                                newTekCalendarVO.setWk35Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk35Day2() != null && newTekCalendarVO1.getWk35Day2() != null && newTekCalendarVO.getWk35Day2().compareTo(newTekCalendarVO1.getWk35Day2()) == -1) {
                                newTekCalendarVO.setWk35Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk35Day3() != null && newTekCalendarVO1.getWk35Day3() != null && newTekCalendarVO.getWk35Day3().compareTo(newTekCalendarVO1.getWk35Day3()) == -1) {
                                newTekCalendarVO.setWk35Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk35Day4() != null && newTekCalendarVO1.getWk35Day4() != null && newTekCalendarVO.getWk35Day4().compareTo(newTekCalendarVO1.getWk35Day4()) == -1) {
                                newTekCalendarVO.setWk35Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk35Day5() != null && newTekCalendarVO1.getWk35Day5() != null && newTekCalendarVO.getWk35Day5().compareTo(newTekCalendarVO1.getWk35Day5()) == -1) {
                                newTekCalendarVO.setWk35Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk35Day6() != null && newTekCalendarVO1.getWk35Day6() != null && newTekCalendarVO.getWk35Day6().compareTo(newTekCalendarVO1.getWk35Day6()) == -1) {
                                newTekCalendarVO.setWk35Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk35Day7() != null && newTekCalendarVO1.getWk35Day7() != null && newTekCalendarVO.getWk35Day7().compareTo(newTekCalendarVO1.getWk35Day7()) == -1) {
                                newTekCalendarVO.setWk35Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk36Day1() != null && newTekCalendarVO1.getWk36Day1() != null && newTekCalendarVO.getWk36Day1().compareTo(newTekCalendarVO1.getWk36Day1()) == -1) {
                                newTekCalendarVO.setWk36Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk36Day2() != null && newTekCalendarVO1.getWk36Day2() != null && newTekCalendarVO.getWk36Day2().compareTo(newTekCalendarVO1.getWk36Day2()) == -1) {
                                newTekCalendarVO.setWk36Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk36Day3() != null && newTekCalendarVO1.getWk36Day3() != null && newTekCalendarVO.getWk36Day3().compareTo(newTekCalendarVO1.getWk36Day3()) == -1) {
                                newTekCalendarVO.setWk36Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk36Day4() != null && newTekCalendarVO1.getWk36Day4() != null && newTekCalendarVO.getWk36Day4().compareTo(newTekCalendarVO1.getWk36Day4()) == -1) {
                                newTekCalendarVO.setWk36Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk36Day5() != null && newTekCalendarVO1.getWk36Day5() != null && newTekCalendarVO.getWk36Day5().compareTo(newTekCalendarVO1.getWk36Day5()) == -1) {
                                newTekCalendarVO.setWk36Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk36Day6() != null && newTekCalendarVO1.getWk36Day6() != null && newTekCalendarVO.getWk36Day6().compareTo(newTekCalendarVO1.getWk36Day6()) == -1) {
                                newTekCalendarVO.setWk36Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk36Day7() != null && newTekCalendarVO1.getWk36Day7() != null && newTekCalendarVO.getWk36Day7().compareTo(newTekCalendarVO1.getWk36Day7()) == -1) {
                                newTekCalendarVO.setWk36Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk37Day1() != null && newTekCalendarVO1.getWk37Day1() != null && newTekCalendarVO.getWk37Day1().compareTo(newTekCalendarVO1.getWk37Day1()) == -1) {
                                newTekCalendarVO.setWk37Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk37Day2() != null && newTekCalendarVO1.getWk37Day2() != null && newTekCalendarVO.getWk37Day2().compareTo(newTekCalendarVO1.getWk37Day2()) == -1) {
                                newTekCalendarVO.setWk37Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk37Day3() != null && newTekCalendarVO1.getWk37Day3() != null && newTekCalendarVO.getWk37Day3().compareTo(newTekCalendarVO1.getWk37Day3()) == -1) {
                                newTekCalendarVO.setWk37Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk37Day4() != null && newTekCalendarVO1.getWk37Day4() != null && newTekCalendarVO.getWk37Day4().compareTo(newTekCalendarVO1.getWk37Day4()) == -1) {
                                newTekCalendarVO.setWk37Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk37Day5() != null && newTekCalendarVO1.getWk37Day5() != null && newTekCalendarVO.getWk37Day5().compareTo(newTekCalendarVO1.getWk37Day5()) == -1) {
                                newTekCalendarVO.setWk37Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk37Day6() != null && newTekCalendarVO1.getWk37Day6() != null && newTekCalendarVO.getWk37Day6().compareTo(newTekCalendarVO1.getWk37Day6()) == -1) {
                                newTekCalendarVO.setWk37Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk37Day7() != null && newTekCalendarVO1.getWk37Day7() != null && newTekCalendarVO.getWk37Day7().compareTo(newTekCalendarVO1.getWk37Day7()) == -1) {
                                newTekCalendarVO.setWk37Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk38Day1() != null && newTekCalendarVO1.getWk38Day1() != null && newTekCalendarVO.getWk38Day1().compareTo(newTekCalendarVO1.getWk38Day1()) == -1) {
                                newTekCalendarVO.setWk38Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk38Day2() != null && newTekCalendarVO1.getWk38Day2() != null && newTekCalendarVO.getWk38Day2().compareTo(newTekCalendarVO1.getWk38Day2()) == -1) {
                                newTekCalendarVO.setWk38Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk38Day3() != null && newTekCalendarVO1.getWk38Day3() != null && newTekCalendarVO.getWk38Day3().compareTo(newTekCalendarVO1.getWk38Day3()) == -1) {
                                newTekCalendarVO.setWk38Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk38Day4() != null && newTekCalendarVO1.getWk38Day4() != null && newTekCalendarVO.getWk38Day4().compareTo(newTekCalendarVO1.getWk38Day4()) == -1) {
                                newTekCalendarVO.setWk38Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk38Day5() != null && newTekCalendarVO1.getWk38Day5() != null && newTekCalendarVO.getWk38Day5().compareTo(newTekCalendarVO1.getWk38Day5()) == -1) {
                                newTekCalendarVO.setWk38Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk38Day6() != null && newTekCalendarVO1.getWk38Day6() != null && newTekCalendarVO.getWk38Day6().compareTo(newTekCalendarVO1.getWk38Day6()) == -1) {
                                newTekCalendarVO.setWk38Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk38Day7() != null && newTekCalendarVO1.getWk38Day7() != null && newTekCalendarVO.getWk38Day7().compareTo(newTekCalendarVO1.getWk38Day7()) == -1) {
                                newTekCalendarVO.setWk38Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk39Day1() != null && newTekCalendarVO1.getWk39Day1() != null && newTekCalendarVO.getWk39Day1().compareTo(newTekCalendarVO1.getWk39Day1()) == -1) {
                                newTekCalendarVO.setWk39Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk39Day2() != null && newTekCalendarVO1.getWk39Day2() != null && newTekCalendarVO.getWk39Day2().compareTo(newTekCalendarVO1.getWk39Day2()) == -1) {
                                newTekCalendarVO.setWk39Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk39Day3() != null && newTekCalendarVO1.getWk39Day3() != null && newTekCalendarVO.getWk39Day3().compareTo(newTekCalendarVO1.getWk39Day3()) == -1) {
                                newTekCalendarVO.setWk39Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk39Day4() != null && newTekCalendarVO1.getWk39Day4() != null && newTekCalendarVO.getWk39Day4().compareTo(newTekCalendarVO1.getWk39Day4()) == -1) {
                                newTekCalendarVO.setWk39Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk39Day5() != null && newTekCalendarVO1.getWk39Day5() != null && newTekCalendarVO.getWk39Day5().compareTo(newTekCalendarVO1.getWk39Day5()) == -1) {
                                newTekCalendarVO.setWk39Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk39Day6() != null && newTekCalendarVO1.getWk39Day6() != null && newTekCalendarVO.getWk39Day6().compareTo(newTekCalendarVO1.getWk39Day6()) == -1) {
                                newTekCalendarVO.setWk39Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk39Day7() != null && newTekCalendarVO1.getWk39Day7() != null && newTekCalendarVO.getWk39Day7().compareTo(newTekCalendarVO1.getWk39Day7()) == -1) {
                                newTekCalendarVO.setWk39Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk40Day1() != null && newTekCalendarVO1.getWk40Day1() != null && newTekCalendarVO.getWk40Day1().compareTo(newTekCalendarVO1.getWk40Day1()) == -1) {
                                newTekCalendarVO.setWk40Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk40Day2() != null && newTekCalendarVO1.getWk40Day2() != null && newTekCalendarVO.getWk40Day2().compareTo(newTekCalendarVO1.getWk40Day2()) == -1) {
                                newTekCalendarVO.setWk40Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk40Day3() != null && newTekCalendarVO1.getWk40Day3() != null && newTekCalendarVO.getWk40Day3().compareTo(newTekCalendarVO1.getWk40Day3()) == -1) {
                                newTekCalendarVO.setWk40Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk40Day4() != null && newTekCalendarVO1.getWk40Day4() != null && newTekCalendarVO.getWk40Day4().compareTo(newTekCalendarVO1.getWk40Day4()) == -1) {
                                newTekCalendarVO.setWk40Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk40Day5() != null && newTekCalendarVO1.getWk40Day5() != null && newTekCalendarVO.getWk40Day5().compareTo(newTekCalendarVO1.getWk40Day5()) == -1) {
                                newTekCalendarVO.setWk40Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk40Day6() != null && newTekCalendarVO1.getWk40Day6() != null && newTekCalendarVO.getWk40Day6().compareTo(newTekCalendarVO1.getWk40Day6()) == -1) {
                                newTekCalendarVO.setWk40Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk40Day7() != null && newTekCalendarVO1.getWk40Day7() != null && newTekCalendarVO.getWk40Day7().compareTo(newTekCalendarVO1.getWk40Day7()) == -1) {
                                newTekCalendarVO.setWk40Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk41Day1() != null && newTekCalendarVO1.getWk41Day1() != null && newTekCalendarVO.getWk41Day1().compareTo(newTekCalendarVO1.getWk41Day1()) == -1) {
                                newTekCalendarVO.setWk41Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk41Day2() != null && newTekCalendarVO1.getWk41Day2() != null && newTekCalendarVO.getWk41Day2().compareTo(newTekCalendarVO1.getWk41Day2()) == -1) {
                                newTekCalendarVO.setWk41Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk41Day3() != null && newTekCalendarVO1.getWk41Day3() != null && newTekCalendarVO.getWk41Day3().compareTo(newTekCalendarVO1.getWk41Day3()) == -1) {
                                newTekCalendarVO.setWk41Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk41Day4() != null && newTekCalendarVO1.getWk41Day4() != null && newTekCalendarVO.getWk41Day4().compareTo(newTekCalendarVO1.getWk41Day4()) == -1) {
                                newTekCalendarVO.setWk41Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk41Day5() != null && newTekCalendarVO1.getWk41Day5() != null && newTekCalendarVO.getWk41Day5().compareTo(newTekCalendarVO1.getWk41Day5()) == -1) {
                                newTekCalendarVO.setWk41Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk41Day6() != null && newTekCalendarVO1.getWk41Day6() != null && newTekCalendarVO.getWk41Day6().compareTo(newTekCalendarVO1.getWk41Day6()) == -1) {
                                newTekCalendarVO.setWk41Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk41Day7() != null && newTekCalendarVO1.getWk41Day7() != null && newTekCalendarVO.getWk41Day7().compareTo(newTekCalendarVO1.getWk41Day7()) == -1) {
                                newTekCalendarVO.setWk41Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk42Day1() != null && newTekCalendarVO1.getWk42Day1() != null && newTekCalendarVO.getWk42Day1().compareTo(newTekCalendarVO1.getWk42Day1()) == -1) {
                                newTekCalendarVO.setWk42Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk42Day2() != null && newTekCalendarVO1.getWk42Day2() != null && newTekCalendarVO.getWk42Day2().compareTo(newTekCalendarVO1.getWk42Day2()) == -1) {
                                newTekCalendarVO.setWk42Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk42Day3() != null && newTekCalendarVO1.getWk42Day3() != null && newTekCalendarVO.getWk42Day3().compareTo(newTekCalendarVO1.getWk42Day3()) == -1) {
                                newTekCalendarVO.setWk42Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk42Day4() != null && newTekCalendarVO1.getWk42Day4() != null && newTekCalendarVO.getWk42Day4().compareTo(newTekCalendarVO1.getWk42Day4()) == -1) {
                                newTekCalendarVO.setWk42Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk42Day5() != null && newTekCalendarVO1.getWk42Day5() != null && newTekCalendarVO.getWk42Day5().compareTo(newTekCalendarVO1.getWk42Day5()) == -1) {
                                newTekCalendarVO.setWk42Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk42Day6() != null && newTekCalendarVO1.getWk42Day6() != null && newTekCalendarVO.getWk42Day6().compareTo(newTekCalendarVO1.getWk42Day6()) == -1) {
                                newTekCalendarVO.setWk42Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk42Day7() != null && newTekCalendarVO1.getWk42Day7() != null && newTekCalendarVO.getWk42Day7().compareTo(newTekCalendarVO1.getWk42Day7()) == -1) {
                                newTekCalendarVO.setWk42Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk43Day1() != null && newTekCalendarVO1.getWk43Day1() != null && newTekCalendarVO.getWk43Day1().compareTo(newTekCalendarVO1.getWk43Day1()) == -1) {
                                newTekCalendarVO.setWk43Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk43Day2() != null && newTekCalendarVO1.getWk43Day2() != null && newTekCalendarVO.getWk43Day2().compareTo(newTekCalendarVO1.getWk43Day2()) == -1) {
                                newTekCalendarVO.setWk43Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk43Day3() != null && newTekCalendarVO1.getWk43Day3() != null && newTekCalendarVO.getWk43Day3().compareTo(newTekCalendarVO1.getWk43Day3()) == -1) {
                                newTekCalendarVO.setWk43Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk43Day4() != null && newTekCalendarVO1.getWk43Day4() != null && newTekCalendarVO.getWk43Day4().compareTo(newTekCalendarVO1.getWk43Day4()) == -1) {
                                newTekCalendarVO.setWk43Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk43Day5() != null && newTekCalendarVO1.getWk43Day5() != null && newTekCalendarVO.getWk43Day5().compareTo(newTekCalendarVO1.getWk43Day5()) == -1) {
                                newTekCalendarVO.setWk43Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk43Day6() != null && newTekCalendarVO1.getWk43Day6() != null && newTekCalendarVO.getWk43Day6().compareTo(newTekCalendarVO1.getWk43Day6()) == -1) {
                                newTekCalendarVO.setWk43Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk43Day7() != null && newTekCalendarVO1.getWk43Day7() != null && newTekCalendarVO.getWk43Day7().compareTo(newTekCalendarVO1.getWk43Day7()) == -1) {
                                newTekCalendarVO.setWk43Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk44Day1() != null && newTekCalendarVO1.getWk44Day1() != null && newTekCalendarVO.getWk44Day1().compareTo(newTekCalendarVO1.getWk44Day1()) == -1) {
                                newTekCalendarVO.setWk44Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk44Day2() != null && newTekCalendarVO1.getWk44Day2() != null && newTekCalendarVO.getWk44Day2().compareTo(newTekCalendarVO1.getWk44Day2()) == -1) {
                                newTekCalendarVO.setWk44Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk44Day3() != null && newTekCalendarVO1.getWk44Day3() != null && newTekCalendarVO.getWk44Day3().compareTo(newTekCalendarVO1.getWk44Day3()) == -1) {
                                newTekCalendarVO.setWk44Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk44Day4() != null && newTekCalendarVO1.getWk44Day4() != null && newTekCalendarVO.getWk44Day4().compareTo(newTekCalendarVO1.getWk44Day4()) == -1) {
                                newTekCalendarVO.setWk44Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk44Day5() != null && newTekCalendarVO1.getWk44Day5() != null && newTekCalendarVO.getWk44Day5().compareTo(newTekCalendarVO1.getWk44Day5()) == -1) {
                                newTekCalendarVO.setWk44Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk44Day6() != null && newTekCalendarVO1.getWk44Day6() != null && newTekCalendarVO.getWk44Day6().compareTo(newTekCalendarVO1.getWk44Day6()) == -1) {
                                newTekCalendarVO.setWk44Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk44Day7() != null && newTekCalendarVO1.getWk44Day7() != null && newTekCalendarVO.getWk44Day7().compareTo(newTekCalendarVO1.getWk44Day7()) == -1) {
                                newTekCalendarVO.setWk44Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk45Day1() != null && newTekCalendarVO1.getWk45Day1() != null && newTekCalendarVO.getWk45Day1().compareTo(newTekCalendarVO1.getWk45Day1()) == -1) {
                                newTekCalendarVO.setWk45Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk45Day2() != null && newTekCalendarVO1.getWk45Day2() != null && newTekCalendarVO.getWk45Day2().compareTo(newTekCalendarVO1.getWk45Day2()) == -1) {
                                newTekCalendarVO.setWk45Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk45Day3() != null && newTekCalendarVO1.getWk45Day3() != null && newTekCalendarVO.getWk45Day3().compareTo(newTekCalendarVO1.getWk45Day3()) == -1) {
                                newTekCalendarVO.setWk45Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk45Day4() != null && newTekCalendarVO1.getWk45Day4() != null && newTekCalendarVO.getWk45Day4().compareTo(newTekCalendarVO1.getWk45Day4()) == -1) {
                                newTekCalendarVO.setWk45Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk45Day5() != null && newTekCalendarVO1.getWk45Day5() != null && newTekCalendarVO.getWk45Day5().compareTo(newTekCalendarVO1.getWk45Day5()) == -1) {
                                newTekCalendarVO.setWk45Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk45Day6() != null && newTekCalendarVO1.getWk45Day6() != null && newTekCalendarVO.getWk45Day6().compareTo(newTekCalendarVO1.getWk45Day6()) == -1) {
                                newTekCalendarVO.setWk45Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk45Day7() != null && newTekCalendarVO1.getWk45Day7() != null && newTekCalendarVO.getWk45Day7().compareTo(newTekCalendarVO1.getWk45Day7()) == -1) {
                                newTekCalendarVO.setWk45Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk46Day1() != null && newTekCalendarVO1.getWk46Day1() != null && newTekCalendarVO.getWk46Day1().compareTo(newTekCalendarVO1.getWk46Day1()) == -1) {
                                newTekCalendarVO.setWk46Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk46Day2() != null && newTekCalendarVO1.getWk46Day2() != null && newTekCalendarVO.getWk46Day2().compareTo(newTekCalendarVO1.getWk46Day2()) == -1) {
                                newTekCalendarVO.setWk46Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk46Day3() != null && newTekCalendarVO1.getWk46Day3() != null && newTekCalendarVO.getWk46Day3().compareTo(newTekCalendarVO1.getWk46Day3()) == -1) {
                                newTekCalendarVO.setWk46Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk46Day4() != null && newTekCalendarVO1.getWk46Day4() != null && newTekCalendarVO.getWk46Day4().compareTo(newTekCalendarVO1.getWk46Day4()) == -1) {
                                newTekCalendarVO.setWk46Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk46Day5() != null && newTekCalendarVO1.getWk46Day5() != null && newTekCalendarVO.getWk46Day5().compareTo(newTekCalendarVO1.getWk46Day5()) == -1) {
                                newTekCalendarVO.setWk46Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk46Day6() != null && newTekCalendarVO1.getWk46Day6() != null && newTekCalendarVO.getWk46Day6().compareTo(newTekCalendarVO1.getWk46Day6()) == -1) {
                                newTekCalendarVO.setWk46Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk46Day7() != null && newTekCalendarVO1.getWk46Day7() != null && newTekCalendarVO.getWk46Day7().compareTo(newTekCalendarVO1.getWk46Day7()) == -1) {
                                newTekCalendarVO.setWk46Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk47Day1() != null && newTekCalendarVO1.getWk47Day1() != null && newTekCalendarVO.getWk47Day1().compareTo(newTekCalendarVO1.getWk47Day1()) == -1) {
                                newTekCalendarVO.setWk47Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk47Day2() != null && newTekCalendarVO1.getWk47Day2() != null && newTekCalendarVO.getWk47Day2().compareTo(newTekCalendarVO1.getWk47Day2()) == -1) {
                                newTekCalendarVO.setWk47Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk47Day3() != null && newTekCalendarVO1.getWk47Day3() != null && newTekCalendarVO.getWk47Day3().compareTo(newTekCalendarVO1.getWk47Day3()) == -1) {
                                newTekCalendarVO.setWk47Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk47Day4() != null && newTekCalendarVO1.getWk47Day4() != null && newTekCalendarVO.getWk47Day4().compareTo(newTekCalendarVO1.getWk47Day4()) == -1) {
                                newTekCalendarVO.setWk47Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk47Day5() != null && newTekCalendarVO1.getWk47Day5() != null && newTekCalendarVO.getWk47Day5().compareTo(newTekCalendarVO1.getWk47Day5()) == -1) {
                                newTekCalendarVO.setWk47Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk47Day6() != null && newTekCalendarVO1.getWk47Day6() != null && newTekCalendarVO.getWk47Day6().compareTo(newTekCalendarVO1.getWk47Day6()) == -1) {
                                newTekCalendarVO.setWk47Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk47Day7() != null && newTekCalendarVO1.getWk47Day7() != null && newTekCalendarVO.getWk47Day7().compareTo(newTekCalendarVO1.getWk47Day7()) == -1) {
                                newTekCalendarVO.setWk47Day7Status(tekConstants.WK_STATUS_ERROR);
                            }

                            if (newTekCalendarVO.getWk48Day1() != null && newTekCalendarVO1.getWk48Day1() != null && newTekCalendarVO.getWk48Day1().compareTo(newTekCalendarVO1.getWk48Day1()) == -1) {
                                newTekCalendarVO.setWk48Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk48Day2() != null && newTekCalendarVO1.getWk48Day2() != null && newTekCalendarVO.getWk48Day2().compareTo(newTekCalendarVO1.getWk48Day2()) == -1) {
                                newTekCalendarVO.setWk48Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk48Day3() != null && newTekCalendarVO1.getWk48Day3() != null && newTekCalendarVO.getWk48Day3().compareTo(newTekCalendarVO1.getWk48Day3()) == -1) {
                                newTekCalendarVO.setWk48Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk48Day4() != null && newTekCalendarVO1.getWk48Day4() != null && newTekCalendarVO.getWk48Day4().compareTo(newTekCalendarVO1.getWk48Day4()) == -1) {
                                newTekCalendarVO.setWk48Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk48Day5() != null && newTekCalendarVO1.getWk48Day5() != null && newTekCalendarVO.getWk48Day5().compareTo(newTekCalendarVO1.getWk48Day5()) == -1) {
                                newTekCalendarVO.setWk48Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk48Day6() != null && newTekCalendarVO1.getWk48Day6() != null && newTekCalendarVO.getWk48Day6().compareTo(newTekCalendarVO1.getWk48Day6()) == -1) {
                                newTekCalendarVO.setWk48Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk48Day7() != null && newTekCalendarVO1.getWk48Day7() != null && newTekCalendarVO.getWk48Day7().compareTo(newTekCalendarVO1.getWk48Day7()) == -1) {
                                newTekCalendarVO.setWk48Day7Status(tekConstants.WK_STATUS_ERROR);
                            }

                            if (newTekCalendarVO.getWk49Day1() != null && newTekCalendarVO1.getWk49Day1() != null && newTekCalendarVO.getWk49Day1().compareTo(newTekCalendarVO1.getWk49Day1()) == -1) {
                                newTekCalendarVO.setWk49Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk49Day2() != null && newTekCalendarVO1.getWk49Day2() != null && newTekCalendarVO.getWk49Day2().compareTo(newTekCalendarVO1.getWk49Day2()) == -1) {
                                newTekCalendarVO.setWk49Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk49Day3() != null && newTekCalendarVO1.getWk49Day3() != null && newTekCalendarVO.getWk49Day3().compareTo(newTekCalendarVO1.getWk49Day3()) == -1) {
                                newTekCalendarVO.setWk49Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk49Day4() != null && newTekCalendarVO1.getWk49Day4() != null && newTekCalendarVO.getWk49Day4().compareTo(newTekCalendarVO1.getWk49Day4()) == -1) {
                                newTekCalendarVO.setWk49Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk49Day5() != null && newTekCalendarVO1.getWk49Day5() != null && newTekCalendarVO.getWk49Day5().compareTo(newTekCalendarVO1.getWk49Day5()) == -1) {
                                newTekCalendarVO.setWk49Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk49Day6() != null && newTekCalendarVO1.getWk49Day6() != null && newTekCalendarVO.getWk49Day6().compareTo(newTekCalendarVO1.getWk49Day6()) == -1) {
                                newTekCalendarVO.setWk49Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk49Day7() != null && newTekCalendarVO1.getWk49Day7() != null && newTekCalendarVO.getWk49Day7().compareTo(newTekCalendarVO1.getWk49Day7()) == -1) {
                                newTekCalendarVO.setWk49Day7Status(tekConstants.WK_STATUS_ERROR);
                            }

                            if (newTekCalendarVO.getWk50Day1() != null && newTekCalendarVO1.getWk50Day1() != null && newTekCalendarVO.getWk50Day1().compareTo(newTekCalendarVO1.getWk50Day1()) == -1) {
                                newTekCalendarVO.setWk50Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk50Day2() != null && newTekCalendarVO1.getWk50Day2() != null && newTekCalendarVO.getWk50Day2().compareTo(newTekCalendarVO1.getWk50Day2()) == -1) {
                                newTekCalendarVO.setWk50Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk50Day3() != null && newTekCalendarVO1.getWk50Day3() != null && newTekCalendarVO.getWk50Day3().compareTo(newTekCalendarVO1.getWk50Day3()) == -1) {
                                newTekCalendarVO.setWk50Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk50Day4() != null && newTekCalendarVO1.getWk50Day4() != null && newTekCalendarVO.getWk50Day4().compareTo(newTekCalendarVO1.getWk50Day4()) == -1) {
                                newTekCalendarVO.setWk50Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk50Day5() != null && newTekCalendarVO1.getWk50Day5() != null && newTekCalendarVO.getWk50Day5().compareTo(newTekCalendarVO1.getWk50Day5()) == -1) {
                                newTekCalendarVO.setWk50Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk50Day6() != null && newTekCalendarVO1.getWk50Day6() != null && newTekCalendarVO.getWk50Day6().compareTo(newTekCalendarVO1.getWk50Day6()) == -1) {
                                newTekCalendarVO.setWk50Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk50Day7() != null && newTekCalendarVO1.getWk50Day7() != null && newTekCalendarVO.getWk50Day7().compareTo(newTekCalendarVO1.getWk50Day7()) == -1) {
                                newTekCalendarVO.setWk50Day7Status(tekConstants.WK_STATUS_ERROR);
                            }

                            if (newTekCalendarVO.getWk51Day1() != null && newTekCalendarVO1.getWk51Day1() != null && newTekCalendarVO.getWk51Day1().compareTo(newTekCalendarVO1.getWk51Day1()) == -1) {
                                newTekCalendarVO.setWk51Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk51Day2() != null && newTekCalendarVO1.getWk51Day2() != null && newTekCalendarVO.getWk51Day2().compareTo(newTekCalendarVO1.getWk51Day2()) == -1) {
                                newTekCalendarVO.setWk51Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk51Day3() != null && newTekCalendarVO1.getWk51Day3() != null && newTekCalendarVO.getWk51Day3().compareTo(newTekCalendarVO1.getWk51Day3()) == -1) {
                                newTekCalendarVO.setWk51Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk51Day4() != null && newTekCalendarVO1.getWk51Day4() != null && newTekCalendarVO.getWk51Day4().compareTo(newTekCalendarVO1.getWk51Day4()) == -1) {
                                newTekCalendarVO.setWk51Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk51Day5() != null && newTekCalendarVO1.getWk51Day5() != null && newTekCalendarVO.getWk51Day5().compareTo(newTekCalendarVO1.getWk51Day5()) == -1) {
                                newTekCalendarVO.setWk51Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk51Day6() != null && newTekCalendarVO1.getWk51Day6() != null && newTekCalendarVO.getWk51Day6().compareTo(newTekCalendarVO1.getWk51Day6()) == -1) {
                                newTekCalendarVO.setWk51Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk51Day7() != null && newTekCalendarVO1.getWk51Day7() != null && newTekCalendarVO.getWk51Day7().compareTo(newTekCalendarVO1.getWk51Day7()) == -1) {
                                newTekCalendarVO.setWk51Day7Status(tekConstants.WK_STATUS_ERROR);
                            }

                            if (newTekCalendarVO.getWk52Day1() != null && newTekCalendarVO1.getWk52Day1() != null && newTekCalendarVO.getWk52Day1().compareTo(newTekCalendarVO1.getWk52Day1()) == -1) {
                                newTekCalendarVO.setWk52Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk52Day2() != null && newTekCalendarVO1.getWk52Day2() != null && newTekCalendarVO.getWk52Day2().compareTo(newTekCalendarVO1.getWk52Day2()) == -1) {
                                newTekCalendarVO.setWk52Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk52Day3() != null && newTekCalendarVO1.getWk52Day3() != null && newTekCalendarVO.getWk52Day3().compareTo(newTekCalendarVO1.getWk52Day3()) == -1) {
                                newTekCalendarVO.setWk52Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk52Day4() != null && newTekCalendarVO1.getWk52Day4() != null && newTekCalendarVO.getWk52Day4().compareTo(newTekCalendarVO1.getWk52Day4()) == -1) {
                                newTekCalendarVO.setWk52Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk52Day5() != null && newTekCalendarVO1.getWk52Day5() != null && newTekCalendarVO.getWk52Day5().compareTo(newTekCalendarVO1.getWk52Day5()) == -1) {
                                newTekCalendarVO.setWk52Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk52Day6() != null && newTekCalendarVO1.getWk52Day6() != null && newTekCalendarVO.getWk52Day6().compareTo(newTekCalendarVO1.getWk52Day6()) == -1) {
                                newTekCalendarVO.setWk52Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk52Day7() != null && newTekCalendarVO1.getWk52Day7() != null && newTekCalendarVO.getWk52Day7().compareTo(newTekCalendarVO1.getWk52Day7()) == -1) {
                                newTekCalendarVO.setWk52Day7Status(tekConstants.WK_STATUS_ERROR);
                            }

                            if (newTekCalendarVO.getWk53Day1() != null && newTekCalendarVO1.getWk53Day1() != null && newTekCalendarVO.getWk53Day1().compareTo(newTekCalendarVO1.getWk53Day1()) == -1) {
                                newTekCalendarVO.setWk53Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk53Day2() != null && newTekCalendarVO1.getWk53Day2() != null && newTekCalendarVO.getWk53Day2().compareTo(newTekCalendarVO1.getWk53Day2()) == -1) {
                                newTekCalendarVO.setWk53Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk53Day3() != null && newTekCalendarVO1.getWk53Day3() != null && newTekCalendarVO.getWk53Day3().compareTo(newTekCalendarVO1.getWk53Day3()) == -1) {
                                newTekCalendarVO.setWk53Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk53Day4() != null && newTekCalendarVO1.getWk53Day4() != null && newTekCalendarVO.getWk53Day4().compareTo(newTekCalendarVO1.getWk53Day4()) == -1) {
                                newTekCalendarVO.setWk53Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk53Day5() != null && newTekCalendarVO1.getWk53Day5() != null && newTekCalendarVO.getWk53Day5().compareTo(newTekCalendarVO1.getWk53Day5()) == -1) {
                                newTekCalendarVO.setWk53Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk53Day6() != null && newTekCalendarVO1.getWk53Day6() != null && newTekCalendarVO.getWk53Day6().compareTo(newTekCalendarVO1.getWk53Day6()) == -1) {
                                newTekCalendarVO.setWk53Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk53Day7() != null && newTekCalendarVO1.getWk53Day7() != null && newTekCalendarVO.getWk53Day7().compareTo(newTekCalendarVO1.getWk53Day7()) == -1) {
                                newTekCalendarVO.setWk53Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                        }
                        //当前没有EX，前一个有EX
                        else if (!newTekCalendarVO.getDisposeName().contains("EX") && newTekCalendarVO.getDisposeName().equals(newTekCalendarVO1.getDisposeName().substring(newTekCalendarVO1.getDisposeName().length() - 3))) {
                            //当前同品类、同系列，比前一个配置的价格低，报异常
                            if (newTekCalendarVO.getWk1MsrpUSD() != null && newTekCalendarVO1.getWk1MsrpUSD() != null && (newTekCalendarVO.getWk1MsrpUSD().compareTo(newTekCalendarVO1.getWk1MsrpUSD()) == -1)) {
                                newTekCalendarVO.setWk1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk2MsrpUSD() != null && newTekCalendarVO1.getWk2MsrpUSD() != null && newTekCalendarVO.getWk2MsrpUSD().compareTo(newTekCalendarVO1.getWk2MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk3MsrpUSD() != null && newTekCalendarVO1.getWk3MsrpUSD() != null && newTekCalendarVO.getWk3MsrpUSD().compareTo(newTekCalendarVO1.getWk3MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk4MsrpUSD() != null && newTekCalendarVO1.getWk4MsrpUSD() != null && newTekCalendarVO.getWk4MsrpUSD().compareTo(newTekCalendarVO1.getWk4MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk5MsrpUSD() != null && newTekCalendarVO1.getWk5MsrpUSD() != null && newTekCalendarVO.getWk5MsrpUSD().compareTo(newTekCalendarVO1.getWk5MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk6MsrpUSD() != null && newTekCalendarVO1.getWk6MsrpUSD() != null && newTekCalendarVO.getWk6MsrpUSD().compareTo(newTekCalendarVO1.getWk6MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk7MsrpUSD() != null && newTekCalendarVO1.getWk7MsrpUSD() != null && newTekCalendarVO.getWk7MsrpUSD().compareTo(newTekCalendarVO1.getWk7MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk8MsrpUSD() != null && newTekCalendarVO1.getWk8MsrpUSD() != null && newTekCalendarVO.getWk8MsrpUSD().compareTo(newTekCalendarVO1.getWk8MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk8Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk9MsrpUSD() != null && newTekCalendarVO1.getWk9MsrpUSD() != null && newTekCalendarVO.getWk9MsrpUSD().compareTo(newTekCalendarVO1.getWk9MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk9Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk10MsrpUSD() != null && newTekCalendarVO1.getWk10MsrpUSD() != null && newTekCalendarVO.getWk10MsrpUSD().compareTo(newTekCalendarVO1.getWk10MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk10Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk11MsrpUSD() != null && newTekCalendarVO1.getWk11MsrpUSD() != null && newTekCalendarVO.getWk11MsrpUSD().compareTo(newTekCalendarVO1.getWk11MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk11Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk12MsrpUSD() != null && newTekCalendarVO1.getWk12MsrpUSD() != null && newTekCalendarVO.getWk12MsrpUSD().compareTo(newTekCalendarVO1.getWk12MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk12Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk13MsrpUSD() != null && newTekCalendarVO1.getWk13MsrpUSD() != null && newTekCalendarVO.getWk13MsrpUSD().compareTo(newTekCalendarVO1.getWk13MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk13Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk14MsrpUSD() != null && newTekCalendarVO1.getWk14MsrpUSD() != null && newTekCalendarVO.getWk14MsrpUSD().compareTo(newTekCalendarVO1.getWk14MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk14Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk15MsrpUSD() != null && newTekCalendarVO1.getWk15MsrpUSD() != null && newTekCalendarVO.getWk15MsrpUSD().compareTo(newTekCalendarVO1.getWk15MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk15Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk16MsrpUSD() != null && newTekCalendarVO1.getWk16MsrpUSD() != null && newTekCalendarVO.getWk16MsrpUSD().compareTo(newTekCalendarVO1.getWk16MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk16Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk17MsrpUSD() != null && newTekCalendarVO1.getWk17MsrpUSD() != null && newTekCalendarVO.getWk17MsrpUSD().compareTo(newTekCalendarVO1.getWk17MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk17Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk18MsrpUSD() != null && newTekCalendarVO1.getWk18MsrpUSD() != null && newTekCalendarVO.getWk18MsrpUSD().compareTo(newTekCalendarVO1.getWk18MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk18Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk19MsrpUSD() != null && newTekCalendarVO1.getWk19MsrpUSD() != null && newTekCalendarVO.getWk19MsrpUSD().compareTo(newTekCalendarVO1.getWk19MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk19Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk20MsrpUSD() != null && newTekCalendarVO1.getWk20MsrpUSD() != null && newTekCalendarVO.getWk20MsrpUSD().compareTo(newTekCalendarVO1.getWk20MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk20Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk21MsrpUSD() != null && newTekCalendarVO1.getWk21MsrpUSD() != null && newTekCalendarVO.getWk21MsrpUSD().compareTo(newTekCalendarVO1.getWk21MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk21Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk22MsrpUSD() != null && newTekCalendarVO1.getWk22MsrpUSD() != null && newTekCalendarVO.getWk22MsrpUSD().compareTo(newTekCalendarVO1.getWk22MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk22Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk23MsrpUSD() != null && newTekCalendarVO1.getWk23MsrpUSD() != null && newTekCalendarVO.getWk23MsrpUSD().compareTo(newTekCalendarVO1.getWk23MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk23Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk24MsrpUSD() != null && newTekCalendarVO1.getWk24MsrpUSD() != null && newTekCalendarVO.getWk24MsrpUSD().compareTo(newTekCalendarVO1.getWk24MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk24Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk25MsrpUSD() != null && newTekCalendarVO1.getWk25MsrpUSD() != null && newTekCalendarVO.getWk25MsrpUSD().compareTo(newTekCalendarVO1.getWk25MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk25Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk26MsrpUSD() != null && newTekCalendarVO1.getWk26MsrpUSD() != null && newTekCalendarVO.getWk26MsrpUSD().compareTo(newTekCalendarVO1.getWk26MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk26Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk27MsrpUSD() != null && newTekCalendarVO1.getWk27MsrpUSD() != null && newTekCalendarVO.getWk27MsrpUSD().compareTo(newTekCalendarVO1.getWk27MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk27Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk28MsrpUSD() != null && newTekCalendarVO1.getWk28MsrpUSD() != null && newTekCalendarVO.getWk28MsrpUSD().compareTo(newTekCalendarVO1.getWk28MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk28Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk29MsrpUSD() != null && newTekCalendarVO1.getWk29MsrpUSD() != null && newTekCalendarVO.getWk29MsrpUSD().compareTo(newTekCalendarVO1.getWk29MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk29Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk30MsrpUSD() != null && newTekCalendarVO1.getWk30MsrpUSD() != null && newTekCalendarVO.getWk30MsrpUSD().compareTo(newTekCalendarVO1.getWk30MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk30Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk31MsrpUSD() != null && newTekCalendarVO1.getWk31MsrpUSD() != null && newTekCalendarVO.getWk31MsrpUSD().compareTo(newTekCalendarVO1.getWk31MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk31Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk32MsrpUSD() != null && newTekCalendarVO1.getWk32MsrpUSD() != null && newTekCalendarVO.getWk32MsrpUSD().compareTo(newTekCalendarVO1.getWk32MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk32Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk33MsrpUSD() != null && newTekCalendarVO1.getWk33MsrpUSD() != null && newTekCalendarVO.getWk33MsrpUSD().compareTo(newTekCalendarVO1.getWk33MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk33Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk34MsrpUSD() != null && newTekCalendarVO1.getWk34MsrpUSD() != null && newTekCalendarVO.getWk34MsrpUSD().compareTo(newTekCalendarVO1.getWk34MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk34Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk35MsrpUSD() != null && newTekCalendarVO1.getWk35MsrpUSD() != null && newTekCalendarVO.getWk35MsrpUSD().compareTo(newTekCalendarVO1.getWk35MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk35Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk36MsrpUSD() != null && newTekCalendarVO1.getWk36MsrpUSD() != null && newTekCalendarVO.getWk36MsrpUSD().compareTo(newTekCalendarVO1.getWk36MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk36Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk37MsrpUSD() != null && newTekCalendarVO1.getWk37MsrpUSD() != null && newTekCalendarVO.getWk37MsrpUSD().compareTo(newTekCalendarVO1.getWk37MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk37Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk38MsrpUSD() != null && newTekCalendarVO1.getWk38MsrpUSD() != null && newTekCalendarVO.getWk38MsrpUSD().compareTo(newTekCalendarVO1.getWk38MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk38Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk39MsrpUSD() != null && newTekCalendarVO1.getWk39MsrpUSD() != null && newTekCalendarVO.getWk39MsrpUSD().compareTo(newTekCalendarVO1.getWk39MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk39Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk40MsrpUSD() != null && newTekCalendarVO1.getWk40MsrpUSD() != null && newTekCalendarVO.getWk40MsrpUSD().compareTo(newTekCalendarVO1.getWk40MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk40Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk41MsrpUSD() != null && newTekCalendarVO1.getWk41MsrpUSD() != null && newTekCalendarVO.getWk41MsrpUSD().compareTo(newTekCalendarVO1.getWk41MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk41Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk42MsrpUSD() != null && newTekCalendarVO1.getWk42MsrpUSD() != null && newTekCalendarVO.getWk42MsrpUSD().compareTo(newTekCalendarVO1.getWk42MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk42Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk43MsrpUSD() != null && newTekCalendarVO1.getWk43MsrpUSD() != null && newTekCalendarVO.getWk43MsrpUSD().compareTo(newTekCalendarVO1.getWk43MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk43Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk44MsrpUSD() != null && newTekCalendarVO1.getWk44MsrpUSD() != null && newTekCalendarVO.getWk44MsrpUSD().compareTo(newTekCalendarVO1.getWk44MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk44Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk45MsrpUSD() != null && newTekCalendarVO1.getWk45MsrpUSD() != null && newTekCalendarVO.getWk45MsrpUSD().compareTo(newTekCalendarVO1.getWk45MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk45Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk46MsrpUSD() != null && newTekCalendarVO1.getWk46MsrpUSD() != null && newTekCalendarVO.getWk46MsrpUSD().compareTo(newTekCalendarVO1.getWk46MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk46Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk47MsrpUSD() != null && newTekCalendarVO1.getWk47MsrpUSD() != null && newTekCalendarVO.getWk47MsrpUSD().compareTo(newTekCalendarVO1.getWk47MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk47Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk48MsrpUSD() != null && newTekCalendarVO1.getWk48MsrpUSD() != null && newTekCalendarVO.getWk48MsrpUSD().compareTo(newTekCalendarVO1.getWk48MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk48Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk49MsrpUSD() != null && newTekCalendarVO1.getWk49MsrpUSD() != null && newTekCalendarVO.getWk49MsrpUSD().compareTo(newTekCalendarVO1.getWk49MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk49Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk50MsrpUSD() != null && newTekCalendarVO1.getWk50MsrpUSD() != null && newTekCalendarVO.getWk50MsrpUSD().compareTo(newTekCalendarVO1.getWk50MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk50Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk51MsrpUSD() != null && newTekCalendarVO1.getWk51MsrpUSD() != null && newTekCalendarVO.getWk51MsrpUSD().compareTo(newTekCalendarVO1.getWk51MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk51Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk52MsrpUSD() != null && newTekCalendarVO1.getWk52MsrpUSD() != null && newTekCalendarVO.getWk52MsrpUSD().compareTo(newTekCalendarVO1.getWk52MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk52Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk53MsrpUSD() != null && newTekCalendarVO1.getWk53MsrpUSD() != null && newTekCalendarVO.getWk53MsrpUSD().compareTo(newTekCalendarVO1.getWk53MsrpUSD()) == -1) {
                                newTekCalendarVO.setWk53Status(tekConstants.WK_STATUS_ERROR);
                            }
                            //day判断
                            if (newTekCalendarVO.getWk1Day1() != null && newTekCalendarVO1.getWk1Day1() != null && newTekCalendarVO.getWk1Day1().compareTo(newTekCalendarVO1.getWk1Day1()) == -1) {
                                newTekCalendarVO.setWk1Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk1Day2() != null && newTekCalendarVO1.getWk1Day2() != null && newTekCalendarVO.getWk1Day2().compareTo(newTekCalendarVO1.getWk1Day2()) == -1) {
                                newTekCalendarVO.setWk1Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk1Day3() != null && newTekCalendarVO1.getWk1Day3() != null && newTekCalendarVO.getWk1Day3().compareTo(newTekCalendarVO1.getWk1Day3()) == -1) {
                                newTekCalendarVO.setWk1Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk1Day4() != null && newTekCalendarVO1.getWk1Day4() != null && newTekCalendarVO.getWk1Day4().compareTo(newTekCalendarVO1.getWk1Day4()) == -1) {
                                newTekCalendarVO.setWk1Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk1Day5() != null && newTekCalendarVO1.getWk1Day5() != null && newTekCalendarVO.getWk1Day5().compareTo(newTekCalendarVO1.getWk1Day5()) == -1) {
                                newTekCalendarVO.setWk1Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk1Day6() != null && newTekCalendarVO1.getWk1Day6() != null && newTekCalendarVO.getWk1Day6().compareTo(newTekCalendarVO1.getWk1Day6()) == -1) {
                                newTekCalendarVO.setWk1Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk1Day7() != null && newTekCalendarVO1.getWk1Day7() != null && newTekCalendarVO.getWk1Day7().compareTo(newTekCalendarVO1.getWk1Day7()) == -1) {
                                newTekCalendarVO.setWk1Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk2Day1() != null && newTekCalendarVO1.getWk2Day1() != null && newTekCalendarVO.getWk2Day1().compareTo(newTekCalendarVO1.getWk2Day1()) == -1) {
                                newTekCalendarVO.setWk2Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk2Day2() != null && newTekCalendarVO1.getWk2Day2() != null && newTekCalendarVO.getWk2Day2().compareTo(newTekCalendarVO1.getWk2Day2()) == -1) {
                                newTekCalendarVO.setWk2Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk2Day3() != null && newTekCalendarVO1.getWk2Day3() != null && newTekCalendarVO.getWk2Day3().compareTo(newTekCalendarVO1.getWk2Day3()) == -1) {
                                newTekCalendarVO.setWk2Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk2Day4() != null && newTekCalendarVO1.getWk2Day4() != null && newTekCalendarVO.getWk2Day4().compareTo(newTekCalendarVO1.getWk2Day4()) == -1) {
                                newTekCalendarVO.setWk2Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk2Day5() != null && newTekCalendarVO1.getWk2Day5() != null && newTekCalendarVO.getWk2Day5().compareTo(newTekCalendarVO1.getWk2Day5()) == -1) {
                                newTekCalendarVO.setWk2Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk2Day6() != null && newTekCalendarVO1.getWk2Day6() != null && newTekCalendarVO.getWk2Day6().compareTo(newTekCalendarVO1.getWk2Day6()) == -1) {
                                newTekCalendarVO.setWk2Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk2Day7() != null && newTekCalendarVO1.getWk2Day7() != null && newTekCalendarVO.getWk2Day7().compareTo(newTekCalendarVO1.getWk2Day7()) == -1) {
                                newTekCalendarVO.setWk2Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk3Day1() != null && newTekCalendarVO1.getWk3Day1() != null && newTekCalendarVO.getWk3Day1().compareTo(newTekCalendarVO1.getWk3Day1()) == -1) {
                                newTekCalendarVO.setWk3Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk3Day2() != null && newTekCalendarVO1.getWk3Day2() != null && newTekCalendarVO.getWk3Day2().compareTo(newTekCalendarVO1.getWk3Day2()) == -1) {
                                newTekCalendarVO.setWk3Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk3Day3() != null && newTekCalendarVO1.getWk3Day3() != null && newTekCalendarVO.getWk3Day3().compareTo(newTekCalendarVO1.getWk3Day3()) == -1) {
                                newTekCalendarVO.setWk3Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk3Day4() != null && newTekCalendarVO1.getWk3Day4() != null && newTekCalendarVO.getWk3Day4().compareTo(newTekCalendarVO1.getWk3Day4()) == -1) {
                                newTekCalendarVO.setWk3Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk3Day5() != null && newTekCalendarVO1.getWk3Day5() != null && newTekCalendarVO.getWk3Day5().compareTo(newTekCalendarVO1.getWk3Day5()) == -1) {
                                newTekCalendarVO.setWk3Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk3Day6() != null && newTekCalendarVO1.getWk3Day6() != null && newTekCalendarVO.getWk3Day6().compareTo(newTekCalendarVO1.getWk3Day6()) == -1) {
                                newTekCalendarVO.setWk3Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk3Day7() != null && newTekCalendarVO1.getWk3Day7() != null && newTekCalendarVO.getWk3Day7().compareTo(newTekCalendarVO1.getWk3Day7()) == -1) {
                                newTekCalendarVO.setWk3Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk4Day1() != null && newTekCalendarVO1.getWk4Day1() != null && newTekCalendarVO.getWk4Day1().compareTo(newTekCalendarVO1.getWk4Day1()) == -1) {
                                newTekCalendarVO.setWk4Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk4Day2() != null && newTekCalendarVO1.getWk4Day2() != null && newTekCalendarVO.getWk4Day2().compareTo(newTekCalendarVO1.getWk4Day2()) == -1) {
                                newTekCalendarVO.setWk4Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk4Day3() != null && newTekCalendarVO1.getWk4Day3() != null && newTekCalendarVO.getWk4Day3().compareTo(newTekCalendarVO1.getWk4Day3()) == -1) {
                                newTekCalendarVO.setWk4Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk4Day4() != null && newTekCalendarVO1.getWk4Day4() != null && newTekCalendarVO.getWk4Day4().compareTo(newTekCalendarVO1.getWk4Day4()) == -1) {
                                newTekCalendarVO.setWk4Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk4Day5() != null && newTekCalendarVO1.getWk4Day5() != null && newTekCalendarVO.getWk4Day5().compareTo(newTekCalendarVO1.getWk4Day5()) == -1) {
                                newTekCalendarVO.setWk4Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk4Day6() != null && newTekCalendarVO1.getWk4Day6() != null && newTekCalendarVO.getWk4Day6().compareTo(newTekCalendarVO1.getWk4Day6()) == -1) {
                                newTekCalendarVO.setWk4Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk4Day7() != null && newTekCalendarVO1.getWk4Day7() != null && newTekCalendarVO.getWk4Day7().compareTo(newTekCalendarVO1.getWk4Day7()) == -1) {
                                newTekCalendarVO.setWk4Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk5Day1() != null && newTekCalendarVO1.getWk5Day1() != null && newTekCalendarVO.getWk5Day1().compareTo(newTekCalendarVO1.getWk5Day1()) == -1) {
                                newTekCalendarVO.setWk5Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk5Day2() != null && newTekCalendarVO1.getWk5Day2() != null && newTekCalendarVO.getWk5Day2().compareTo(newTekCalendarVO1.getWk5Day2()) == -1) {
                                newTekCalendarVO.setWk5Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk5Day3() != null && newTekCalendarVO1.getWk5Day3() != null && newTekCalendarVO.getWk5Day3().compareTo(newTekCalendarVO1.getWk5Day3()) == -1) {
                                newTekCalendarVO.setWk5Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk5Day4() != null && newTekCalendarVO1.getWk5Day4() != null && newTekCalendarVO.getWk5Day4().compareTo(newTekCalendarVO1.getWk5Day4()) == -1) {
                                newTekCalendarVO.setWk5Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk5Day5() != null && newTekCalendarVO1.getWk5Day5() != null && newTekCalendarVO.getWk5Day5().compareTo(newTekCalendarVO1.getWk5Day5()) == -1) {
                                newTekCalendarVO.setWk5Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk5Day6() != null && newTekCalendarVO1.getWk5Day6() != null && newTekCalendarVO.getWk5Day6().compareTo(newTekCalendarVO1.getWk5Day6()) == -1) {
                                newTekCalendarVO.setWk5Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk5Day7() != null && newTekCalendarVO1.getWk5Day7() != null && newTekCalendarVO.getWk5Day7().compareTo(newTekCalendarVO1.getWk5Day7()) == -1) {
                                newTekCalendarVO.setWk5Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk6Day1() != null && newTekCalendarVO1.getWk6Day1() != null && newTekCalendarVO.getWk6Day1().compareTo(newTekCalendarVO1.getWk6Day1()) == -1) {
                                newTekCalendarVO.setWk7Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk6Day2() != null && newTekCalendarVO1.getWk6Day2() != null && newTekCalendarVO.getWk6Day2().compareTo(newTekCalendarVO1.getWk6Day2()) == -1) {
                                newTekCalendarVO.setWk7Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk6Day3() != null && newTekCalendarVO1.getWk6Day3() != null && newTekCalendarVO.getWk6Day3().compareTo(newTekCalendarVO1.getWk6Day3()) == -1) {
                                newTekCalendarVO.setWk7Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk6Day4() != null && newTekCalendarVO1.getWk6Day4() != null && newTekCalendarVO.getWk6Day4().compareTo(newTekCalendarVO1.getWk6Day4()) == -1) {
                                newTekCalendarVO.setWk7Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk6Day5() != null && newTekCalendarVO1.getWk6Day5() != null && newTekCalendarVO.getWk6Day5().compareTo(newTekCalendarVO1.getWk6Day5()) == -1) {
                                newTekCalendarVO.setWk7Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk6Day6() != null && newTekCalendarVO1.getWk6Day6() != null && newTekCalendarVO.getWk6Day6().compareTo(newTekCalendarVO1.getWk6Day6()) == -1) {
                                newTekCalendarVO.setWk7Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk6Day7() != null && newTekCalendarVO1.getWk6Day7() != null && newTekCalendarVO.getWk6Day7().compareTo(newTekCalendarVO1.getWk6Day7()) == -1) {
                                newTekCalendarVO.setWk7Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk7Day1() != null && newTekCalendarVO1.getWk7Day1() != null && newTekCalendarVO.getWk7Day1().compareTo(newTekCalendarVO1.getWk7Day1()) == -1) {
                                newTekCalendarVO.setWk7Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk7Day2() != null && newTekCalendarVO1.getWk7Day2() != null && newTekCalendarVO.getWk7Day2().compareTo(newTekCalendarVO1.getWk7Day2()) == -1) {
                                newTekCalendarVO.setWk7Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk7Day3() != null && newTekCalendarVO1.getWk7Day3() != null && newTekCalendarVO.getWk7Day3().compareTo(newTekCalendarVO1.getWk7Day3()) == -1) {
                                newTekCalendarVO.setWk7Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk7Day4() != null && newTekCalendarVO1.getWk7Day4() != null && newTekCalendarVO.getWk7Day4().compareTo(newTekCalendarVO1.getWk7Day4()) == -1) {
                                newTekCalendarVO.setWk7Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk7Day5() != null && newTekCalendarVO1.getWk7Day5() != null && newTekCalendarVO.getWk7Day5().compareTo(newTekCalendarVO1.getWk7Day5()) == -1) {
                                newTekCalendarVO.setWk7Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk7Day6() != null && newTekCalendarVO1.getWk7Day6() != null && newTekCalendarVO.getWk7Day6().compareTo(newTekCalendarVO1.getWk7Day6()) == -1) {
                                newTekCalendarVO.setWk7Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk7Day7() != null && newTekCalendarVO1.getWk7Day7() != null && newTekCalendarVO.getWk7Day7().compareTo(newTekCalendarVO1.getWk7Day7()) == -1) {
                                newTekCalendarVO.setWk7Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk8Day1() != null && newTekCalendarVO1.getWk8Day1() != null && newTekCalendarVO.getWk8Day1().compareTo(newTekCalendarVO1.getWk8Day1()) == -1) {
                                newTekCalendarVO.setWk8Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk8Day2() != null && newTekCalendarVO1.getWk8Day2() != null && newTekCalendarVO.getWk8Day2().compareTo(newTekCalendarVO1.getWk8Day2()) == -1) {
                                newTekCalendarVO.setWk8Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk8Day3() != null && newTekCalendarVO1.getWk8Day3() != null && newTekCalendarVO.getWk8Day3().compareTo(newTekCalendarVO1.getWk8Day3()) == -1) {
                                newTekCalendarVO.setWk8Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk8Day4() != null && newTekCalendarVO1.getWk8Day4() != null && newTekCalendarVO.getWk8Day4().compareTo(newTekCalendarVO1.getWk8Day4()) == -1) {
                                newTekCalendarVO.setWk8Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk8Day5() != null && newTekCalendarVO1.getWk8Day5() != null && newTekCalendarVO.getWk8Day5().compareTo(newTekCalendarVO1.getWk8Day5()) == -1) {
                                newTekCalendarVO.setWk8Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk8Day6() != null && newTekCalendarVO1.getWk8Day6() != null && newTekCalendarVO.getWk8Day6().compareTo(newTekCalendarVO1.getWk8Day6()) == -1) {
                                newTekCalendarVO.setWk8Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk8Day7() != null && newTekCalendarVO1.getWk8Day7() != null && newTekCalendarVO.getWk8Day7().compareTo(newTekCalendarVO1.getWk8Day7()) == -1) {
                                newTekCalendarVO.setWk8Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk9Day1() != null && newTekCalendarVO1.getWk9Day1() != null && newTekCalendarVO.getWk9Day1().compareTo(newTekCalendarVO1.getWk9Day1()) == -1) {
                                newTekCalendarVO.setWk9Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk9Day2() != null && newTekCalendarVO1.getWk9Day2() != null && newTekCalendarVO.getWk9Day2().compareTo(newTekCalendarVO1.getWk9Day2()) == -1) {
                                newTekCalendarVO.setWk9Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk9Day3() != null && newTekCalendarVO1.getWk9Day3() != null && newTekCalendarVO.getWk9Day3().compareTo(newTekCalendarVO1.getWk9Day3()) == -1) {
                                newTekCalendarVO.setWk9Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk9Day4() != null && newTekCalendarVO1.getWk9Day4() != null && newTekCalendarVO.getWk9Day4().compareTo(newTekCalendarVO1.getWk9Day4()) == -1) {
                                newTekCalendarVO.setWk9Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk9Day5() != null && newTekCalendarVO1.getWk9Day5() != null && newTekCalendarVO.getWk9Day5().compareTo(newTekCalendarVO1.getWk9Day5()) == -1) {
                                newTekCalendarVO.setWk9Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk9Day6() != null && newTekCalendarVO1.getWk9Day6() != null && newTekCalendarVO.getWk9Day6().compareTo(newTekCalendarVO1.getWk9Day6()) == -1) {
                                newTekCalendarVO.setWk9Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk9Day7() != null && newTekCalendarVO1.getWk9Day7() != null && newTekCalendarVO.getWk9Day7().compareTo(newTekCalendarVO1.getWk9Day7()) == -1) {
                                newTekCalendarVO.setWk9Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk10Day1() != null && newTekCalendarVO1.getWk10Day1() != null && newTekCalendarVO.getWk10Day1().compareTo(newTekCalendarVO1.getWk10Day1()) == -1) {
                                newTekCalendarVO.setWk10Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk10Day2() != null && newTekCalendarVO1.getWk10Day2() != null && newTekCalendarVO.getWk10Day2().compareTo(newTekCalendarVO1.getWk10Day2()) == -1) {
                                newTekCalendarVO.setWk10Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk10Day3() != null && newTekCalendarVO1.getWk10Day3() != null && newTekCalendarVO.getWk10Day3().compareTo(newTekCalendarVO1.getWk10Day3()) == -1) {
                                newTekCalendarVO.setWk10Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk10Day4() != null && newTekCalendarVO1.getWk10Day4() != null && newTekCalendarVO.getWk10Day4().compareTo(newTekCalendarVO1.getWk10Day4()) == -1) {
                                newTekCalendarVO.setWk10Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk10Day5() != null && newTekCalendarVO1.getWk10Day5() != null && newTekCalendarVO.getWk10Day5().compareTo(newTekCalendarVO1.getWk10Day5()) == -1) {
                                newTekCalendarVO.setWk10Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk10Day6() != null && newTekCalendarVO1.getWk10Day6() != null && newTekCalendarVO.getWk10Day6().compareTo(newTekCalendarVO1.getWk10Day6()) == -1) {
                                newTekCalendarVO.setWk10Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk10Day7() != null && newTekCalendarVO1.getWk10Day7() != null && newTekCalendarVO.getWk10Day7().compareTo(newTekCalendarVO1.getWk10Day7()) == -1) {
                                newTekCalendarVO.setWk10Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk11Day1() != null && newTekCalendarVO1.getWk11Day1() != null && newTekCalendarVO.getWk11Day1().compareTo(newTekCalendarVO1.getWk11Day1()) == -1) {
                                newTekCalendarVO.setWk11Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk11Day2() != null && newTekCalendarVO1.getWk11Day2() != null && newTekCalendarVO.getWk11Day2().compareTo(newTekCalendarVO1.getWk11Day2()) == -1) {
                                newTekCalendarVO.setWk11Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk11Day3() != null && newTekCalendarVO1.getWk11Day3() != null && newTekCalendarVO.getWk11Day3().compareTo(newTekCalendarVO1.getWk11Day3()) == -1) {
                                newTekCalendarVO.setWk11Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk11Day4() != null && newTekCalendarVO1.getWk11Day4() != null && newTekCalendarVO.getWk11Day4().compareTo(newTekCalendarVO1.getWk11Day4()) == -1) {
                                newTekCalendarVO.setWk11Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk11Day5() != null && newTekCalendarVO1.getWk11Day5() != null && newTekCalendarVO.getWk11Day5().compareTo(newTekCalendarVO1.getWk11Day5()) == -1) {
                                newTekCalendarVO.setWk11Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk11Day6() != null && newTekCalendarVO1.getWk11Day6() != null && newTekCalendarVO.getWk11Day6().compareTo(newTekCalendarVO1.getWk11Day6()) == -1) {
                                newTekCalendarVO.setWk11Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk11Day7() != null && newTekCalendarVO1.getWk11Day7() != null && newTekCalendarVO.getWk11Day7().compareTo(newTekCalendarVO1.getWk11Day7()) == -1) {
                                newTekCalendarVO.setWk11Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk12Day1() != null && newTekCalendarVO1.getWk12Day1() != null && newTekCalendarVO.getWk12Day1().compareTo(newTekCalendarVO1.getWk12Day1()) == -1) {
                                newTekCalendarVO.setWk12Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk12Day2() != null && newTekCalendarVO1.getWk12Day2() != null && newTekCalendarVO.getWk12Day2().compareTo(newTekCalendarVO1.getWk12Day2()) == -1) {
                                newTekCalendarVO.setWk12Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk12Day3() != null && newTekCalendarVO1.getWk12Day3() != null && newTekCalendarVO.getWk12Day3().compareTo(newTekCalendarVO1.getWk12Day3()) == -1) {
                                newTekCalendarVO.setWk12Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk12Day4() != null && newTekCalendarVO1.getWk12Day4() != null && newTekCalendarVO.getWk12Day4().compareTo(newTekCalendarVO1.getWk12Day4()) == -1) {
                                newTekCalendarVO.setWk12Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk12Day5() != null && newTekCalendarVO1.getWk12Day5() != null && newTekCalendarVO.getWk12Day5().compareTo(newTekCalendarVO1.getWk12Day5()) == -1) {
                                newTekCalendarVO.setWk12Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk12Day6() != null && newTekCalendarVO1.getWk12Day6() != null && newTekCalendarVO.getWk12Day6().compareTo(newTekCalendarVO1.getWk12Day6()) == -1) {
                                newTekCalendarVO.setWk12Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk12Day7() != null && newTekCalendarVO1.getWk12Day7() != null && newTekCalendarVO.getWk12Day7().compareTo(newTekCalendarVO1.getWk12Day7()) == -1) {
                                newTekCalendarVO.setWk12Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk13Day1() != null && newTekCalendarVO1.getWk13Day1() != null && newTekCalendarVO.getWk13Day1().compareTo(newTekCalendarVO1.getWk13Day1()) == -1) {
                                newTekCalendarVO.setWk13Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk13Day2() != null && newTekCalendarVO1.getWk13Day2() != null && newTekCalendarVO.getWk13Day2().compareTo(newTekCalendarVO1.getWk13Day2()) == -1) {
                                newTekCalendarVO.setWk13Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk13Day3() != null && newTekCalendarVO1.getWk13Day3() != null && newTekCalendarVO.getWk13Day3().compareTo(newTekCalendarVO1.getWk13Day3()) == -1) {
                                newTekCalendarVO.setWk13Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk13Day4() != null && newTekCalendarVO1.getWk13Day4() != null && newTekCalendarVO.getWk13Day4().compareTo(newTekCalendarVO1.getWk13Day4()) == -1) {
                                newTekCalendarVO.setWk13Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk13Day5() != null && newTekCalendarVO1.getWk13Day5() != null && newTekCalendarVO.getWk13Day5().compareTo(newTekCalendarVO1.getWk13Day5()) == -1) {
                                newTekCalendarVO.setWk13Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk13Day6() != null && newTekCalendarVO1.getWk13Day6() != null && newTekCalendarVO.getWk13Day6().compareTo(newTekCalendarVO1.getWk13Day6()) == -1) {
                                newTekCalendarVO.setWk13Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk13Day7() != null && newTekCalendarVO1.getWk13Day7() != null && newTekCalendarVO.getWk13Day7().compareTo(newTekCalendarVO1.getWk13Day7()) == -1) {
                                newTekCalendarVO.setWk13Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk14Day1() != null && newTekCalendarVO1.getWk14Day1() != null && newTekCalendarVO.getWk14Day1().compareTo(newTekCalendarVO1.getWk14Day1()) == -1) {
                                newTekCalendarVO.setWk14Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk14Day2() != null && newTekCalendarVO1.getWk14Day2() != null && newTekCalendarVO.getWk14Day2().compareTo(newTekCalendarVO1.getWk14Day2()) == -1) {
                                newTekCalendarVO.setWk14Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk14Day3() != null && newTekCalendarVO1.getWk14Day3() != null && newTekCalendarVO.getWk14Day3().compareTo(newTekCalendarVO1.getWk14Day3()) == -1) {
                                newTekCalendarVO.setWk14Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk14Day4() != null && newTekCalendarVO1.getWk14Day4() != null && newTekCalendarVO.getWk14Day4().compareTo(newTekCalendarVO1.getWk14Day4()) == -1) {
                                newTekCalendarVO.setWk14Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk14Day5() != null && newTekCalendarVO1.getWk14Day5() != null && newTekCalendarVO.getWk14Day5().compareTo(newTekCalendarVO1.getWk14Day5()) == -1) {
                                newTekCalendarVO.setWk14Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk14Day6() != null && newTekCalendarVO1.getWk14Day6() != null && newTekCalendarVO.getWk14Day6().compareTo(newTekCalendarVO1.getWk14Day6()) == -1) {
                                newTekCalendarVO.setWk14Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk14Day7() != null && newTekCalendarVO1.getWk14Day7() != null && newTekCalendarVO.getWk14Day7().compareTo(newTekCalendarVO1.getWk14Day7()) == -1) {
                                newTekCalendarVO.setWk14Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk15Day1() != null && newTekCalendarVO1.getWk15Day1() != null && newTekCalendarVO.getWk15Day1().compareTo(newTekCalendarVO1.getWk15Day1()) == -1) {
                                newTekCalendarVO.setWk15Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk15Day2() != null && newTekCalendarVO1.getWk15Day2() != null && newTekCalendarVO.getWk15Day2().compareTo(newTekCalendarVO1.getWk15Day2()) == -1) {
                                newTekCalendarVO.setWk15Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk15Day3() != null && newTekCalendarVO1.getWk15Day3() != null && newTekCalendarVO.getWk15Day3().compareTo(newTekCalendarVO1.getWk15Day3()) == -1) {
                                newTekCalendarVO.setWk15Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk15Day4() != null && newTekCalendarVO1.getWk15Day4() != null && newTekCalendarVO.getWk15Day4().compareTo(newTekCalendarVO1.getWk15Day4()) == -1) {
                                newTekCalendarVO.setWk15Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk15Day5() != null && newTekCalendarVO1.getWk15Day5() != null && newTekCalendarVO.getWk15Day5().compareTo(newTekCalendarVO1.getWk15Day5()) == -1) {
                                newTekCalendarVO.setWk15Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk15Day6() != null && newTekCalendarVO1.getWk15Day6() != null && newTekCalendarVO.getWk15Day6().compareTo(newTekCalendarVO1.getWk15Day6()) == -1) {
                                newTekCalendarVO.setWk15Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk15Day7() != null && newTekCalendarVO1.getWk15Day7() != null && newTekCalendarVO.getWk15Day7().compareTo(newTekCalendarVO1.getWk15Day7()) == -1) {
                                newTekCalendarVO.setWk15Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk16Day1() != null && newTekCalendarVO1.getWk16Day1() != null && newTekCalendarVO.getWk16Day1().compareTo(newTekCalendarVO1.getWk16Day1()) == -1) {
                                newTekCalendarVO.setWk16Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk16Day2() != null && newTekCalendarVO1.getWk16Day2() != null && newTekCalendarVO.getWk16Day2().compareTo(newTekCalendarVO1.getWk16Day2()) == -1) {
                                newTekCalendarVO.setWk16Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk16Day3() != null && newTekCalendarVO1.getWk16Day3() != null && newTekCalendarVO.getWk16Day3().compareTo(newTekCalendarVO1.getWk16Day3()) == -1) {
                                newTekCalendarVO.setWk16Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk16Day4() != null && newTekCalendarVO1.getWk16Day4() != null && newTekCalendarVO.getWk16Day4().compareTo(newTekCalendarVO1.getWk16Day4()) == -1) {
                                newTekCalendarVO.setWk16Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk16Day5() != null && newTekCalendarVO1.getWk16Day5() != null && newTekCalendarVO.getWk16Day5().compareTo(newTekCalendarVO1.getWk16Day5()) == -1) {
                                newTekCalendarVO.setWk16Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk16Day6() != null && newTekCalendarVO1.getWk16Day6() != null && newTekCalendarVO.getWk16Day6().compareTo(newTekCalendarVO1.getWk16Day6()) == -1) {
                                newTekCalendarVO.setWk16Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk16Day7() != null && newTekCalendarVO1.getWk16Day7() != null && newTekCalendarVO.getWk16Day7().compareTo(newTekCalendarVO1.getWk16Day7()) == -1) {
                                newTekCalendarVO.setWk16Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk17Day1() != null && newTekCalendarVO1.getWk17Day1() != null && newTekCalendarVO.getWk17Day1().compareTo(newTekCalendarVO1.getWk17Day1()) == -1) {
                                newTekCalendarVO.setWk17Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk17Day2() != null && newTekCalendarVO1.getWk17Day2() != null && newTekCalendarVO.getWk17Day2().compareTo(newTekCalendarVO1.getWk17Day2()) == -1) {
                                newTekCalendarVO.setWk17Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk17Day3() != null && newTekCalendarVO1.getWk17Day3() != null && newTekCalendarVO.getWk17Day3().compareTo(newTekCalendarVO1.getWk17Day3()) == -1) {
                                newTekCalendarVO.setWk17Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk17Day4() != null && newTekCalendarVO1.getWk17Day4() != null && newTekCalendarVO.getWk17Day4().compareTo(newTekCalendarVO1.getWk17Day4()) == -1) {
                                newTekCalendarVO.setWk17Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk17Day5() != null && newTekCalendarVO1.getWk17Day5() != null && newTekCalendarVO.getWk17Day5().compareTo(newTekCalendarVO1.getWk17Day5()) == -1) {
                                newTekCalendarVO.setWk17Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk17Day6() != null && newTekCalendarVO1.getWk17Day6() != null && newTekCalendarVO.getWk17Day6().compareTo(newTekCalendarVO1.getWk17Day6()) == -1) {
                                newTekCalendarVO.setWk17Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk17Day7() != null && newTekCalendarVO1.getWk17Day7() != null && newTekCalendarVO.getWk17Day7().compareTo(newTekCalendarVO1.getWk17Day7()) == -1) {
                                newTekCalendarVO.setWk17Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk18Day1() != null && newTekCalendarVO1.getWk18Day1() != null && newTekCalendarVO.getWk18Day1().compareTo(newTekCalendarVO1.getWk18Day1()) == -1) {
                                newTekCalendarVO.setWk18Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk18Day2() != null && newTekCalendarVO1.getWk18Day2() != null && newTekCalendarVO.getWk18Day2().compareTo(newTekCalendarVO1.getWk18Day2()) == -1) {
                                newTekCalendarVO.setWk18Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk18Day3() != null && newTekCalendarVO1.getWk18Day3() != null && newTekCalendarVO.getWk18Day3().compareTo(newTekCalendarVO1.getWk18Day3()) == -1) {
                                newTekCalendarVO.setWk18Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk18Day4() != null && newTekCalendarVO1.getWk18Day4() != null && newTekCalendarVO.getWk18Day4().compareTo(newTekCalendarVO1.getWk18Day4()) == -1) {
                                newTekCalendarVO.setWk18Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk18Day5() != null && newTekCalendarVO1.getWk18Day5() != null && newTekCalendarVO.getWk18Day5().compareTo(newTekCalendarVO1.getWk18Day5()) == -1) {
                                newTekCalendarVO.setWk18Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk18Day6() != null && newTekCalendarVO1.getWk18Day6() != null && newTekCalendarVO.getWk18Day6().compareTo(newTekCalendarVO1.getWk18Day6()) == -1) {
                                newTekCalendarVO.setWk18Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk18Day7() != null && newTekCalendarVO1.getWk18Day7() != null && newTekCalendarVO.getWk18Day7().compareTo(newTekCalendarVO1.getWk18Day7()) == -1) {
                                newTekCalendarVO.setWk18Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk19Day1() != null && newTekCalendarVO1.getWk19Day1() != null && newTekCalendarVO.getWk19Day1().compareTo(newTekCalendarVO1.getWk19Day1()) == -1) {
                                newTekCalendarVO.setWk19Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk19Day2() != null && newTekCalendarVO1.getWk19Day2() != null && newTekCalendarVO.getWk19Day2().compareTo(newTekCalendarVO1.getWk19Day2()) == -1) {
                                newTekCalendarVO.setWk19Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk19Day3() != null && newTekCalendarVO1.getWk19Day3() != null && newTekCalendarVO.getWk19Day3().compareTo(newTekCalendarVO1.getWk19Day3()) == -1) {
                                newTekCalendarVO.setWk19Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk19Day4() != null && newTekCalendarVO1.getWk19Day4() != null && newTekCalendarVO.getWk19Day4().compareTo(newTekCalendarVO1.getWk19Day4()) == -1) {
                                newTekCalendarVO.setWk19Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk19Day5() != null && newTekCalendarVO1.getWk19Day5() != null && newTekCalendarVO.getWk19Day5().compareTo(newTekCalendarVO1.getWk19Day5()) == -1) {
                                newTekCalendarVO.setWk19Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk19Day6() != null && newTekCalendarVO1.getWk19Day6() != null && newTekCalendarVO.getWk19Day6().compareTo(newTekCalendarVO1.getWk19Day6()) == -1) {
                                newTekCalendarVO.setWk19Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk19Day7() != null && newTekCalendarVO1.getWk19Day7() != null && newTekCalendarVO.getWk19Day7().compareTo(newTekCalendarVO1.getWk19Day7()) == -1) {
                                newTekCalendarVO.setWk19Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk20Day1() != null && newTekCalendarVO1.getWk20Day1() != null && newTekCalendarVO.getWk20Day1().compareTo(newTekCalendarVO1.getWk20Day1()) == -1) {
                                newTekCalendarVO.setWk20Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk20Day2() != null && newTekCalendarVO1.getWk20Day2() != null && newTekCalendarVO.getWk20Day2().compareTo(newTekCalendarVO1.getWk20Day2()) == -1) {
                                newTekCalendarVO.setWk20Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk20Day3() != null && newTekCalendarVO1.getWk20Day3() != null && newTekCalendarVO.getWk20Day3().compareTo(newTekCalendarVO1.getWk20Day3()) == -1) {
                                newTekCalendarVO.setWk20Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk20Day4() != null && newTekCalendarVO1.getWk20Day4() != null && newTekCalendarVO.getWk20Day4().compareTo(newTekCalendarVO1.getWk20Day4()) == -1) {
                                newTekCalendarVO.setWk20Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk20Day5() != null && newTekCalendarVO1.getWk20Day5() != null && newTekCalendarVO.getWk20Day5().compareTo(newTekCalendarVO1.getWk20Day5()) == -1) {
                                newTekCalendarVO.setWk20Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk20Day6() != null && newTekCalendarVO1.getWk20Day6() != null && newTekCalendarVO.getWk20Day6().compareTo(newTekCalendarVO1.getWk20Day6()) == -1) {
                                newTekCalendarVO.setWk20Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk20Day7() != null && newTekCalendarVO1.getWk20Day7() != null && newTekCalendarVO.getWk20Day7().compareTo(newTekCalendarVO1.getWk20Day7()) == -1) {
                                newTekCalendarVO.setWk20Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk21Day1() != null && newTekCalendarVO1.getWk21Day1() != null && newTekCalendarVO.getWk21Day1().compareTo(newTekCalendarVO1.getWk21Day1()) == -1) {
                                newTekCalendarVO.setWk21Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk21Day2() != null && newTekCalendarVO1.getWk21Day2() != null && newTekCalendarVO.getWk21Day2().compareTo(newTekCalendarVO1.getWk21Day2()) == -1) {
                                newTekCalendarVO.setWk21Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk21Day3() != null && newTekCalendarVO1.getWk21Day3() != null && newTekCalendarVO.getWk21Day3().compareTo(newTekCalendarVO1.getWk21Day3()) == -1) {
                                newTekCalendarVO.setWk21Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk21Day4() != null && newTekCalendarVO1.getWk21Day4() != null && newTekCalendarVO.getWk21Day4().compareTo(newTekCalendarVO1.getWk21Day4()) == -1) {
                                newTekCalendarVO.setWk21Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk21Day5() != null && newTekCalendarVO1.getWk21Day5() != null && newTekCalendarVO.getWk21Day5().compareTo(newTekCalendarVO1.getWk21Day5()) == -1) {
                                newTekCalendarVO.setWk21Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk21Day6() != null && newTekCalendarVO1.getWk21Day6() != null && newTekCalendarVO.getWk21Day6().compareTo(newTekCalendarVO1.getWk21Day6()) == -1) {
                                newTekCalendarVO.setWk21Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk21Day7() != null && newTekCalendarVO1.getWk21Day7() != null && newTekCalendarVO.getWk21Day7().compareTo(newTekCalendarVO1.getWk21Day7()) == -1) {
                                newTekCalendarVO.setWk21Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk22Day1() != null && newTekCalendarVO1.getWk22Day1() != null && newTekCalendarVO.getWk22Day1().compareTo(newTekCalendarVO1.getWk22Day1()) == -1) {
                                newTekCalendarVO.setWk22Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk22Day2() != null && newTekCalendarVO1.getWk22Day2() != null && newTekCalendarVO.getWk22Day2().compareTo(newTekCalendarVO1.getWk22Day2()) == -1) {
                                newTekCalendarVO.setWk22Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk22Day3() != null && newTekCalendarVO1.getWk22Day3() != null && newTekCalendarVO.getWk22Day3().compareTo(newTekCalendarVO1.getWk22Day3()) == -1) {
                                newTekCalendarVO.setWk22Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk22Day4() != null && newTekCalendarVO1.getWk22Day4() != null && newTekCalendarVO.getWk22Day4().compareTo(newTekCalendarVO1.getWk22Day4()) == -1) {
                                newTekCalendarVO.setWk22Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk22Day5() != null && newTekCalendarVO1.getWk22Day5() != null && newTekCalendarVO.getWk22Day5().compareTo(newTekCalendarVO1.getWk22Day5()) == -1) {
                                newTekCalendarVO.setWk22Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk22Day6() != null && newTekCalendarVO1.getWk22Day6() != null && newTekCalendarVO.getWk22Day6().compareTo(newTekCalendarVO1.getWk22Day6()) == -1) {
                                newTekCalendarVO.setWk22Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk22Day7() != null && newTekCalendarVO1.getWk22Day7() != null && newTekCalendarVO.getWk22Day7().compareTo(newTekCalendarVO1.getWk22Day7()) == -1) {
                                newTekCalendarVO.setWk22Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk23Day1() != null && newTekCalendarVO1.getWk23Day1() != null && newTekCalendarVO.getWk23Day1().compareTo(newTekCalendarVO1.getWk23Day1()) == -1) {
                                newTekCalendarVO.setWk23Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk23Day2() != null && newTekCalendarVO1.getWk23Day2() != null && newTekCalendarVO.getWk23Day2().compareTo(newTekCalendarVO1.getWk23Day2()) == -1) {
                                newTekCalendarVO.setWk23Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk23Day3() != null && newTekCalendarVO1.getWk23Day3() != null && newTekCalendarVO.getWk23Day3().compareTo(newTekCalendarVO1.getWk23Day3()) == -1) {
                                newTekCalendarVO.setWk23Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk23Day4() != null && newTekCalendarVO1.getWk23Day4() != null && newTekCalendarVO.getWk23Day4().compareTo(newTekCalendarVO1.getWk23Day4()) == -1) {
                                newTekCalendarVO.setWk23Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk23Day5() != null && newTekCalendarVO1.getWk23Day5() != null && newTekCalendarVO.getWk23Day5().compareTo(newTekCalendarVO1.getWk23Day5()) == -1) {
                                newTekCalendarVO.setWk23Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk23Day6() != null && newTekCalendarVO1.getWk23Day6() != null && newTekCalendarVO.getWk23Day6().compareTo(newTekCalendarVO1.getWk23Day6()) == -1) {
                                newTekCalendarVO.setWk23Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk23Day7() != null && newTekCalendarVO1.getWk23Day7() != null && newTekCalendarVO.getWk23Day7().compareTo(newTekCalendarVO1.getWk23Day7()) == -1) {
                                newTekCalendarVO.setWk23Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk24Day1() != null && newTekCalendarVO1.getWk24Day1() != null && newTekCalendarVO.getWk24Day1().compareTo(newTekCalendarVO1.getWk24Day1()) == -1) {
                                newTekCalendarVO.setWk24Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk24Day2() != null && newTekCalendarVO1.getWk24Day2() != null && newTekCalendarVO.getWk24Day2().compareTo(newTekCalendarVO1.getWk24Day2()) == -1) {
                                newTekCalendarVO.setWk24Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk24Day3() != null && newTekCalendarVO1.getWk24Day3() != null && newTekCalendarVO.getWk24Day3().compareTo(newTekCalendarVO1.getWk24Day3()) == -1) {
                                newTekCalendarVO.setWk24Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk24Day4() != null && newTekCalendarVO1.getWk24Day4() != null && newTekCalendarVO.getWk24Day4().compareTo(newTekCalendarVO1.getWk24Day4()) == -1) {
                                newTekCalendarVO.setWk24Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk24Day5() != null && newTekCalendarVO1.getWk24Day5() != null && newTekCalendarVO.getWk24Day5().compareTo(newTekCalendarVO1.getWk24Day5()) == -1) {
                                newTekCalendarVO.setWk24Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk24Day6() != null && newTekCalendarVO1.getWk24Day6() != null && newTekCalendarVO.getWk24Day6().compareTo(newTekCalendarVO1.getWk24Day6()) == -1) {
                                newTekCalendarVO.setWk24Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk24Day7() != null && newTekCalendarVO1.getWk24Day7() != null && newTekCalendarVO.getWk24Day7().compareTo(newTekCalendarVO1.getWk24Day7()) == -1) {
                                newTekCalendarVO.setWk24Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk25Day1() != null && newTekCalendarVO1.getWk25Day1() != null && newTekCalendarVO.getWk25Day1().compareTo(newTekCalendarVO1.getWk25Day1()) == -1) {
                                newTekCalendarVO.setWk25Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk25Day2() != null && newTekCalendarVO1.getWk25Day2() != null && newTekCalendarVO.getWk25Day2().compareTo(newTekCalendarVO1.getWk25Day2()) == -1) {
                                newTekCalendarVO.setWk25Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk25Day3() != null && newTekCalendarVO1.getWk25Day3() != null && newTekCalendarVO.getWk25Day3().compareTo(newTekCalendarVO1.getWk25Day3()) == -1) {
                                newTekCalendarVO.setWk25Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk25Day4() != null && newTekCalendarVO1.getWk25Day4() != null && newTekCalendarVO.getWk25Day4().compareTo(newTekCalendarVO1.getWk25Day4()) == -1) {
                                newTekCalendarVO.setWk25Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk25Day5() != null && newTekCalendarVO1.getWk25Day5() != null && newTekCalendarVO.getWk25Day5().compareTo(newTekCalendarVO1.getWk25Day5()) == -1) {
                                newTekCalendarVO.setWk25Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk25Day6() != null && newTekCalendarVO1.getWk25Day6() != null && newTekCalendarVO.getWk25Day6().compareTo(newTekCalendarVO1.getWk25Day6()) == -1) {
                                newTekCalendarVO.setWk25Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk25Day7() != null && newTekCalendarVO1.getWk25Day7() != null && newTekCalendarVO.getWk25Day7().compareTo(newTekCalendarVO1.getWk25Day7()) == -1) {
                                newTekCalendarVO.setWk25Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk26Day1() != null && newTekCalendarVO1.getWk26Day1() != null && newTekCalendarVO.getWk26Day1().compareTo(newTekCalendarVO1.getWk26Day1()) == -1) {
                                newTekCalendarVO.setWk26Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk26Day2() != null && newTekCalendarVO1.getWk26Day2() != null && newTekCalendarVO.getWk26Day2().compareTo(newTekCalendarVO1.getWk26Day2()) == -1) {
                                newTekCalendarVO.setWk26Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk26Day3() != null && newTekCalendarVO1.getWk26Day3() != null && newTekCalendarVO.getWk26Day3().compareTo(newTekCalendarVO1.getWk26Day3()) == -1) {
                                newTekCalendarVO.setWk26Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk26Day4() != null && newTekCalendarVO1.getWk26Day4() != null && newTekCalendarVO.getWk26Day4().compareTo(newTekCalendarVO1.getWk26Day4()) == -1) {
                                newTekCalendarVO.setWk26Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk26Day5() != null && newTekCalendarVO1.getWk26Day5() != null && newTekCalendarVO.getWk26Day5().compareTo(newTekCalendarVO1.getWk26Day5()) == -1) {
                                newTekCalendarVO.setWk26Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk26Day6() != null && newTekCalendarVO1.getWk26Day6() != null && newTekCalendarVO.getWk26Day6().compareTo(newTekCalendarVO1.getWk26Day6()) == -1) {
                                newTekCalendarVO.setWk26Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk26Day7() != null && newTekCalendarVO1.getWk26Day7() != null && newTekCalendarVO.getWk26Day7().compareTo(newTekCalendarVO1.getWk26Day7()) == -1) {
                                newTekCalendarVO.setWk26Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk27Day1() != null && newTekCalendarVO1.getWk27Day1() != null && newTekCalendarVO.getWk27Day1().compareTo(newTekCalendarVO1.getWk27Day1()) == -1) {
                                newTekCalendarVO.setWk27Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk27Day2() != null && newTekCalendarVO1.getWk27Day2() != null && newTekCalendarVO.getWk27Day2().compareTo(newTekCalendarVO1.getWk27Day2()) == -1) {
                                newTekCalendarVO.setWk27Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk27Day3() != null && newTekCalendarVO1.getWk27Day3() != null && newTekCalendarVO.getWk27Day3().compareTo(newTekCalendarVO1.getWk27Day3()) == -1) {
                                newTekCalendarVO.setWk27Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk27Day4() != null && newTekCalendarVO1.getWk27Day4() != null && newTekCalendarVO.getWk27Day4().compareTo(newTekCalendarVO1.getWk27Day4()) == -1) {
                                newTekCalendarVO.setWk27Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk27Day5() != null && newTekCalendarVO1.getWk27Day5() != null && newTekCalendarVO.getWk27Day5().compareTo(newTekCalendarVO1.getWk27Day5()) == -1) {
                                newTekCalendarVO.setWk27Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk27Day6() != null && newTekCalendarVO1.getWk27Day6() != null && newTekCalendarVO.getWk27Day6().compareTo(newTekCalendarVO1.getWk27Day6()) == -1) {
                                newTekCalendarVO.setWk27Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk27Day7() != null && newTekCalendarVO1.getWk27Day7() != null && newTekCalendarVO.getWk27Day7().compareTo(newTekCalendarVO1.getWk27Day7()) == -1) {
                                newTekCalendarVO.setWk27Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk28Day1() != null && newTekCalendarVO1.getWk28Day1() != null && newTekCalendarVO.getWk28Day1().compareTo(newTekCalendarVO1.getWk28Day1()) == -1) {
                                newTekCalendarVO.setWk28Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk28Day2() != null && newTekCalendarVO1.getWk28Day2() != null && newTekCalendarVO.getWk28Day2().compareTo(newTekCalendarVO1.getWk28Day2()) == -1) {
                                newTekCalendarVO.setWk28Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk28Day3() != null && newTekCalendarVO1.getWk28Day3() != null && newTekCalendarVO.getWk28Day3().compareTo(newTekCalendarVO1.getWk28Day3()) == -1) {
                                newTekCalendarVO.setWk28Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk28Day4() != null && newTekCalendarVO1.getWk28Day4() != null && newTekCalendarVO.getWk28Day4().compareTo(newTekCalendarVO1.getWk28Day4()) == -1) {
                                newTekCalendarVO.setWk28Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk28Day5() != null && newTekCalendarVO1.getWk28Day5() != null && newTekCalendarVO.getWk28Day5().compareTo(newTekCalendarVO1.getWk28Day5()) == -1) {
                                newTekCalendarVO.setWk28Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk28Day6() != null && newTekCalendarVO1.getWk28Day6() != null && newTekCalendarVO.getWk28Day6().compareTo(newTekCalendarVO1.getWk28Day6()) == -1) {
                                newTekCalendarVO.setWk28Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk28Day7() != null && newTekCalendarVO1.getWk28Day7() != null && newTekCalendarVO.getWk28Day7().compareTo(newTekCalendarVO1.getWk28Day7()) == -1) {
                                newTekCalendarVO.setWk28Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk29Day1() != null && newTekCalendarVO1.getWk29Day1() != null && newTekCalendarVO.getWk29Day1().compareTo(newTekCalendarVO1.getWk29Day1()) == -1) {
                                newTekCalendarVO.setWk29Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk29Day2() != null && newTekCalendarVO1.getWk29Day2() != null && newTekCalendarVO.getWk29Day2().compareTo(newTekCalendarVO1.getWk29Day2()) == -1) {
                                newTekCalendarVO.setWk29Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk29Day3() != null && newTekCalendarVO1.getWk29Day3() != null && newTekCalendarVO.getWk29Day3().compareTo(newTekCalendarVO1.getWk29Day3()) == -1) {
                                newTekCalendarVO.setWk29Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk29Day4() != null && newTekCalendarVO1.getWk29Day4() != null && newTekCalendarVO.getWk29Day4().compareTo(newTekCalendarVO1.getWk29Day4()) == -1) {
                                newTekCalendarVO.setWk29Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk29Day5() != null && newTekCalendarVO1.getWk29Day5() != null && newTekCalendarVO.getWk29Day5().compareTo(newTekCalendarVO1.getWk29Day5()) == -1) {
                                newTekCalendarVO.setWk29Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk29Day6() != null && newTekCalendarVO1.getWk29Day6() != null && newTekCalendarVO.getWk29Day6().compareTo(newTekCalendarVO1.getWk29Day6()) == -1) {
                                newTekCalendarVO.setWk29Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk29Day7() != null && newTekCalendarVO1.getWk29Day7() != null && newTekCalendarVO.getWk29Day7().compareTo(newTekCalendarVO1.getWk29Day7()) == -1) {
                                newTekCalendarVO.setWk29Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk30Day1() != null && newTekCalendarVO1.getWk30Day1() != null && newTekCalendarVO.getWk30Day1().compareTo(newTekCalendarVO1.getWk30Day1()) == -1) {
                                newTekCalendarVO.setWk30Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk30Day2() != null && newTekCalendarVO1.getWk30Day2() != null && newTekCalendarVO.getWk30Day2().compareTo(newTekCalendarVO1.getWk30Day2()) == -1) {
                                newTekCalendarVO.setWk30Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk30Day3() != null && newTekCalendarVO1.getWk30Day3() != null && newTekCalendarVO.getWk30Day3().compareTo(newTekCalendarVO1.getWk30Day3()) == -1) {
                                newTekCalendarVO.setWk30Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk30Day4() != null && newTekCalendarVO1.getWk30Day4() != null && newTekCalendarVO.getWk30Day4().compareTo(newTekCalendarVO1.getWk30Day4()) == -1) {
                                newTekCalendarVO.setWk30Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk30Day5() != null && newTekCalendarVO1.getWk30Day5() != null && newTekCalendarVO.getWk30Day5().compareTo(newTekCalendarVO1.getWk30Day5()) == -1) {
                                newTekCalendarVO.setWk30Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk30Day6() != null && newTekCalendarVO1.getWk30Day6() != null && newTekCalendarVO.getWk30Day6().compareTo(newTekCalendarVO1.getWk30Day6()) == -1) {
                                newTekCalendarVO.setWk30Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk30Day7() != null && newTekCalendarVO1.getWk30Day7() != null && newTekCalendarVO.getWk30Day7().compareTo(newTekCalendarVO1.getWk30Day7()) == -1) {
                                newTekCalendarVO.setWk30Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk31Day1() != null && newTekCalendarVO1.getWk31Day1() != null && newTekCalendarVO.getWk31Day1().compareTo(newTekCalendarVO1.getWk31Day1()) == -1) {
                                newTekCalendarVO.setWk31Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk31Day2() != null && newTekCalendarVO1.getWk31Day2() != null && newTekCalendarVO.getWk31Day2().compareTo(newTekCalendarVO1.getWk31Day2()) == -1) {
                                newTekCalendarVO.setWk31Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk31Day3() != null && newTekCalendarVO1.getWk31Day3() != null && newTekCalendarVO.getWk31Day3().compareTo(newTekCalendarVO1.getWk31Day3()) == -1) {
                                newTekCalendarVO.setWk31Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk31Day4() != null && newTekCalendarVO1.getWk31Day4() != null && newTekCalendarVO.getWk31Day4().compareTo(newTekCalendarVO1.getWk31Day4()) == -1) {
                                newTekCalendarVO.setWk31Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk31Day5() != null && newTekCalendarVO1.getWk31Day5() != null && newTekCalendarVO.getWk31Day5().compareTo(newTekCalendarVO1.getWk31Day5()) == -1) {
                                newTekCalendarVO.setWk31Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk31Day6() != null && newTekCalendarVO1.getWk31Day6() != null && newTekCalendarVO.getWk31Day6().compareTo(newTekCalendarVO1.getWk31Day6()) == -1) {
                                newTekCalendarVO.setWk31Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk31Day7() != null && newTekCalendarVO1.getWk31Day7() != null && newTekCalendarVO.getWk31Day7().compareTo(newTekCalendarVO1.getWk31Day7()) == -1) {
                                newTekCalendarVO.setWk31Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk32Day1() != null && newTekCalendarVO1.getWk32Day1() != null && newTekCalendarVO.getWk32Day1().compareTo(newTekCalendarVO1.getWk32Day1()) == -1) {
                                newTekCalendarVO.setWk32Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk32Day2() != null && newTekCalendarVO1.getWk32Day2() != null && newTekCalendarVO.getWk32Day2().compareTo(newTekCalendarVO1.getWk32Day2()) == -1) {
                                newTekCalendarVO.setWk32Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk32Day3() != null && newTekCalendarVO1.getWk32Day3() != null && newTekCalendarVO.getWk32Day3().compareTo(newTekCalendarVO1.getWk32Day3()) == -1) {
                                newTekCalendarVO.setWk32Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk32Day4() != null && newTekCalendarVO1.getWk32Day4() != null && newTekCalendarVO.getWk32Day4().compareTo(newTekCalendarVO1.getWk32Day4()) == -1) {
                                newTekCalendarVO.setWk32Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk32Day5() != null && newTekCalendarVO1.getWk32Day5() != null && newTekCalendarVO.getWk32Day5().compareTo(newTekCalendarVO1.getWk32Day5()) == -1) {
                                newTekCalendarVO.setWk32Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk32Day6() != null && newTekCalendarVO1.getWk32Day6() != null && newTekCalendarVO.getWk32Day6().compareTo(newTekCalendarVO1.getWk32Day6()) == -1) {
                                newTekCalendarVO.setWk32Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk32Day7() != null && newTekCalendarVO1.getWk32Day7() != null && newTekCalendarVO.getWk32Day7().compareTo(newTekCalendarVO1.getWk32Day7()) == -1) {
                                newTekCalendarVO.setWk32Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk33Day1() != null && newTekCalendarVO1.getWk33Day1() != null && newTekCalendarVO.getWk33Day1().compareTo(newTekCalendarVO1.getWk33Day1()) == -1) {
                                newTekCalendarVO.setWk33Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk33Day2() != null && newTekCalendarVO1.getWk33Day2() != null && newTekCalendarVO.getWk33Day2().compareTo(newTekCalendarVO1.getWk33Day2()) == -1) {
                                newTekCalendarVO.setWk33Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk33Day3() != null && newTekCalendarVO1.getWk33Day3() != null && newTekCalendarVO.getWk33Day3().compareTo(newTekCalendarVO1.getWk33Day3()) == -1) {
                                newTekCalendarVO.setWk33Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk33Day4() != null && newTekCalendarVO1.getWk33Day4() != null && newTekCalendarVO.getWk33Day4().compareTo(newTekCalendarVO1.getWk33Day4()) == -1) {
                                newTekCalendarVO.setWk33Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk33Day5() != null && newTekCalendarVO1.getWk33Day5() != null && newTekCalendarVO.getWk33Day5().compareTo(newTekCalendarVO1.getWk33Day5()) == -1) {
                                newTekCalendarVO.setWk33Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk33Day6() != null && newTekCalendarVO1.getWk33Day6() != null && newTekCalendarVO.getWk33Day6().compareTo(newTekCalendarVO1.getWk33Day6()) == -1) {
                                newTekCalendarVO.setWk33Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk33Day7() != null && newTekCalendarVO1.getWk33Day7() != null && newTekCalendarVO.getWk33Day7().compareTo(newTekCalendarVO1.getWk33Day7()) == -1) {
                                newTekCalendarVO.setWk33Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk34Day1() != null && newTekCalendarVO1.getWk34Day1() != null && newTekCalendarVO.getWk34Day1().compareTo(newTekCalendarVO1.getWk34Day1()) == -1) {
                                newTekCalendarVO.setWk34Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk34Day2() != null && newTekCalendarVO1.getWk34Day2() != null && newTekCalendarVO.getWk34Day2().compareTo(newTekCalendarVO1.getWk34Day2()) == -1) {
                                newTekCalendarVO.setWk34Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk34Day3() != null && newTekCalendarVO1.getWk34Day3() != null && newTekCalendarVO.getWk34Day3().compareTo(newTekCalendarVO1.getWk34Day3()) == -1) {
                                newTekCalendarVO.setWk34Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk34Day4() != null && newTekCalendarVO1.getWk34Day4() != null && newTekCalendarVO.getWk34Day4().compareTo(newTekCalendarVO1.getWk34Day4()) == -1) {
                                newTekCalendarVO.setWk34Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk34Day5() != null && newTekCalendarVO1.getWk34Day5() != null && newTekCalendarVO.getWk34Day5().compareTo(newTekCalendarVO1.getWk34Day5()) == -1) {
                                newTekCalendarVO.setWk34Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk34Day6() != null && newTekCalendarVO1.getWk34Day6() != null && newTekCalendarVO.getWk34Day6().compareTo(newTekCalendarVO1.getWk34Day6()) == -1) {
                                newTekCalendarVO.setWk34Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk34Day7() != null && newTekCalendarVO1.getWk34Day7() != null && newTekCalendarVO.getWk34Day7().compareTo(newTekCalendarVO1.getWk34Day7()) == -1) {
                                newTekCalendarVO.setWk34Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk35Day1() != null && newTekCalendarVO1.getWk35Day1() != null && newTekCalendarVO.getWk35Day1().compareTo(newTekCalendarVO1.getWk35Day1()) == -1) {
                                newTekCalendarVO.setWk35Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk35Day2() != null && newTekCalendarVO1.getWk35Day2() != null && newTekCalendarVO.getWk35Day2().compareTo(newTekCalendarVO1.getWk35Day2()) == -1) {
                                newTekCalendarVO.setWk35Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk35Day3() != null && newTekCalendarVO1.getWk35Day3() != null && newTekCalendarVO.getWk35Day3().compareTo(newTekCalendarVO1.getWk35Day3()) == -1) {
                                newTekCalendarVO.setWk35Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk35Day4() != null && newTekCalendarVO1.getWk35Day4() != null && newTekCalendarVO.getWk35Day4().compareTo(newTekCalendarVO1.getWk35Day4()) == -1) {
                                newTekCalendarVO.setWk35Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk35Day5() != null && newTekCalendarVO1.getWk35Day5() != null && newTekCalendarVO.getWk35Day5().compareTo(newTekCalendarVO1.getWk35Day5()) == -1) {
                                newTekCalendarVO.setWk35Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk35Day6() != null && newTekCalendarVO1.getWk35Day6() != null && newTekCalendarVO.getWk35Day6().compareTo(newTekCalendarVO1.getWk35Day6()) == -1) {
                                newTekCalendarVO.setWk35Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk35Day7() != null && newTekCalendarVO1.getWk35Day7() != null && newTekCalendarVO.getWk35Day7().compareTo(newTekCalendarVO1.getWk35Day7()) == -1) {
                                newTekCalendarVO.setWk35Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk36Day1() != null && newTekCalendarVO1.getWk36Day1() != null && newTekCalendarVO.getWk36Day1().compareTo(newTekCalendarVO1.getWk36Day1()) == -1) {
                                newTekCalendarVO.setWk36Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk36Day2() != null && newTekCalendarVO1.getWk36Day2() != null && newTekCalendarVO.getWk36Day2().compareTo(newTekCalendarVO1.getWk36Day2()) == -1) {
                                newTekCalendarVO.setWk36Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk36Day3() != null && newTekCalendarVO1.getWk36Day3() != null && newTekCalendarVO.getWk36Day3().compareTo(newTekCalendarVO1.getWk36Day3()) == -1) {
                                newTekCalendarVO.setWk36Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk36Day4() != null && newTekCalendarVO1.getWk36Day4() != null && newTekCalendarVO.getWk36Day4().compareTo(newTekCalendarVO1.getWk36Day4()) == -1) {
                                newTekCalendarVO.setWk36Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk36Day5() != null && newTekCalendarVO1.getWk36Day5() != null && newTekCalendarVO.getWk36Day5().compareTo(newTekCalendarVO1.getWk36Day5()) == -1) {
                                newTekCalendarVO.setWk36Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk36Day6() != null && newTekCalendarVO1.getWk36Day6() != null && newTekCalendarVO.getWk36Day6().compareTo(newTekCalendarVO1.getWk36Day6()) == -1) {
                                newTekCalendarVO.setWk36Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk36Day7() != null && newTekCalendarVO1.getWk36Day7() != null && newTekCalendarVO.getWk36Day7().compareTo(newTekCalendarVO1.getWk36Day7()) == -1) {
                                newTekCalendarVO.setWk36Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk37Day1() != null && newTekCalendarVO1.getWk37Day1() != null && newTekCalendarVO.getWk37Day1().compareTo(newTekCalendarVO1.getWk37Day1()) == -1) {
                                newTekCalendarVO.setWk37Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk37Day2() != null && newTekCalendarVO1.getWk37Day2() != null && newTekCalendarVO.getWk37Day2().compareTo(newTekCalendarVO1.getWk37Day2()) == -1) {
                                newTekCalendarVO.setWk37Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk37Day3() != null && newTekCalendarVO1.getWk37Day3() != null && newTekCalendarVO.getWk37Day3().compareTo(newTekCalendarVO1.getWk37Day3()) == -1) {
                                newTekCalendarVO.setWk37Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk37Day4() != null && newTekCalendarVO1.getWk37Day4() != null && newTekCalendarVO.getWk37Day4().compareTo(newTekCalendarVO1.getWk37Day4()) == -1) {
                                newTekCalendarVO.setWk37Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk37Day5() != null && newTekCalendarVO1.getWk37Day5() != null && newTekCalendarVO.getWk37Day5().compareTo(newTekCalendarVO1.getWk37Day5()) == -1) {
                                newTekCalendarVO.setWk37Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk37Day6() != null && newTekCalendarVO1.getWk37Day6() != null && newTekCalendarVO.getWk37Day6().compareTo(newTekCalendarVO1.getWk37Day6()) == -1) {
                                newTekCalendarVO.setWk37Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk37Day7() != null && newTekCalendarVO1.getWk37Day7() != null && newTekCalendarVO.getWk37Day7().compareTo(newTekCalendarVO1.getWk37Day7()) == -1) {
                                newTekCalendarVO.setWk37Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk38Day1() != null && newTekCalendarVO1.getWk38Day1() != null && newTekCalendarVO.getWk38Day1().compareTo(newTekCalendarVO1.getWk38Day1()) == -1) {
                                newTekCalendarVO.setWk38Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk38Day2() != null && newTekCalendarVO1.getWk38Day2() != null && newTekCalendarVO.getWk38Day2().compareTo(newTekCalendarVO1.getWk38Day2()) == -1) {
                                newTekCalendarVO.setWk38Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk38Day3() != null && newTekCalendarVO1.getWk38Day3() != null && newTekCalendarVO.getWk38Day3().compareTo(newTekCalendarVO1.getWk38Day3()) == -1) {
                                newTekCalendarVO.setWk38Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk38Day4() != null && newTekCalendarVO1.getWk38Day4() != null && newTekCalendarVO.getWk38Day4().compareTo(newTekCalendarVO1.getWk38Day4()) == -1) {
                                newTekCalendarVO.setWk38Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk38Day5() != null && newTekCalendarVO1.getWk38Day5() != null && newTekCalendarVO.getWk38Day5().compareTo(newTekCalendarVO1.getWk38Day5()) == -1) {
                                newTekCalendarVO.setWk38Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk38Day6() != null && newTekCalendarVO1.getWk38Day6() != null && newTekCalendarVO.getWk38Day6().compareTo(newTekCalendarVO1.getWk38Day6()) == -1) {
                                newTekCalendarVO.setWk38Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk38Day7() != null && newTekCalendarVO1.getWk38Day7() != null && newTekCalendarVO.getWk38Day7().compareTo(newTekCalendarVO1.getWk38Day7()) == -1) {
                                newTekCalendarVO.setWk38Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk39Day1() != null && newTekCalendarVO1.getWk39Day1() != null && newTekCalendarVO.getWk39Day1().compareTo(newTekCalendarVO1.getWk39Day1()) == -1) {
                                newTekCalendarVO.setWk39Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk39Day2() != null && newTekCalendarVO1.getWk39Day2() != null && newTekCalendarVO.getWk39Day2().compareTo(newTekCalendarVO1.getWk39Day2()) == -1) {
                                newTekCalendarVO.setWk39Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk39Day3() != null && newTekCalendarVO1.getWk39Day3() != null && newTekCalendarVO.getWk39Day3().compareTo(newTekCalendarVO1.getWk39Day3()) == -1) {
                                newTekCalendarVO.setWk39Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk39Day4() != null && newTekCalendarVO1.getWk39Day4() != null && newTekCalendarVO.getWk39Day4().compareTo(newTekCalendarVO1.getWk39Day4()) == -1) {
                                newTekCalendarVO.setWk39Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk39Day5() != null && newTekCalendarVO1.getWk39Day5() != null && newTekCalendarVO.getWk39Day5().compareTo(newTekCalendarVO1.getWk39Day5()) == -1) {
                                newTekCalendarVO.setWk39Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk39Day6() != null && newTekCalendarVO1.getWk39Day6() != null && newTekCalendarVO.getWk39Day6().compareTo(newTekCalendarVO1.getWk39Day6()) == -1) {
                                newTekCalendarVO.setWk39Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk39Day7() != null && newTekCalendarVO1.getWk39Day7() != null && newTekCalendarVO.getWk39Day7().compareTo(newTekCalendarVO1.getWk39Day7()) == -1) {
                                newTekCalendarVO.setWk39Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk40Day1() != null && newTekCalendarVO1.getWk40Day1() != null && newTekCalendarVO.getWk40Day1().compareTo(newTekCalendarVO1.getWk40Day1()) == -1) {
                                newTekCalendarVO.setWk40Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk40Day2() != null && newTekCalendarVO1.getWk40Day2() != null && newTekCalendarVO.getWk40Day2().compareTo(newTekCalendarVO1.getWk40Day2()) == -1) {
                                newTekCalendarVO.setWk40Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk40Day3() != null && newTekCalendarVO1.getWk40Day3() != null && newTekCalendarVO.getWk40Day3().compareTo(newTekCalendarVO1.getWk40Day3()) == -1) {
                                newTekCalendarVO.setWk40Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk40Day4() != null && newTekCalendarVO1.getWk40Day4() != null && newTekCalendarVO.getWk40Day4().compareTo(newTekCalendarVO1.getWk40Day4()) == -1) {
                                newTekCalendarVO.setWk40Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk40Day5() != null && newTekCalendarVO1.getWk40Day5() != null && newTekCalendarVO.getWk40Day5().compareTo(newTekCalendarVO1.getWk40Day5()) == -1) {
                                newTekCalendarVO.setWk40Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk40Day6() != null && newTekCalendarVO1.getWk40Day6() != null && newTekCalendarVO.getWk40Day6().compareTo(newTekCalendarVO1.getWk40Day6()) == -1) {
                                newTekCalendarVO.setWk40Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk40Day7() != null && newTekCalendarVO1.getWk40Day7() != null && newTekCalendarVO.getWk40Day7().compareTo(newTekCalendarVO1.getWk40Day7()) == -1) {
                                newTekCalendarVO.setWk40Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk41Day1() != null && newTekCalendarVO1.getWk41Day1() != null && newTekCalendarVO.getWk41Day1().compareTo(newTekCalendarVO1.getWk41Day1()) == -1) {
                                newTekCalendarVO.setWk41Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk41Day2() != null && newTekCalendarVO1.getWk41Day2() != null && newTekCalendarVO.getWk41Day2().compareTo(newTekCalendarVO1.getWk41Day2()) == -1) {
                                newTekCalendarVO.setWk41Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk41Day3() != null && newTekCalendarVO1.getWk41Day3() != null && newTekCalendarVO.getWk41Day3().compareTo(newTekCalendarVO1.getWk41Day3()) == -1) {
                                newTekCalendarVO.setWk41Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk41Day4() != null && newTekCalendarVO1.getWk41Day4() != null && newTekCalendarVO.getWk41Day4().compareTo(newTekCalendarVO1.getWk41Day4()) == -1) {
                                newTekCalendarVO.setWk41Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk41Day5() != null && newTekCalendarVO1.getWk41Day5() != null && newTekCalendarVO.getWk41Day5().compareTo(newTekCalendarVO1.getWk41Day5()) == -1) {
                                newTekCalendarVO.setWk41Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk41Day6() != null && newTekCalendarVO1.getWk41Day6() != null && newTekCalendarVO.getWk41Day6().compareTo(newTekCalendarVO1.getWk41Day6()) == -1) {
                                newTekCalendarVO.setWk41Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk41Day7() != null && newTekCalendarVO1.getWk41Day7() != null && newTekCalendarVO.getWk41Day7().compareTo(newTekCalendarVO1.getWk41Day7()) == -1) {
                                newTekCalendarVO.setWk41Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk42Day1() != null && newTekCalendarVO1.getWk42Day1() != null && newTekCalendarVO.getWk42Day1().compareTo(newTekCalendarVO1.getWk42Day1()) == -1) {
                                newTekCalendarVO.setWk42Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk42Day2() != null && newTekCalendarVO1.getWk42Day2() != null && newTekCalendarVO.getWk42Day2().compareTo(newTekCalendarVO1.getWk42Day2()) == -1) {
                                newTekCalendarVO.setWk42Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk42Day3() != null && newTekCalendarVO1.getWk42Day3() != null && newTekCalendarVO.getWk42Day3().compareTo(newTekCalendarVO1.getWk42Day3()) == -1) {
                                newTekCalendarVO.setWk42Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk42Day4() != null && newTekCalendarVO1.getWk42Day4() != null && newTekCalendarVO.getWk42Day4().compareTo(newTekCalendarVO1.getWk42Day4()) == -1) {
                                newTekCalendarVO.setWk42Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk42Day5() != null && newTekCalendarVO1.getWk42Day5() != null && newTekCalendarVO.getWk42Day5().compareTo(newTekCalendarVO1.getWk42Day5()) == -1) {
                                newTekCalendarVO.setWk42Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk42Day6() != null && newTekCalendarVO1.getWk42Day6() != null && newTekCalendarVO.getWk42Day6().compareTo(newTekCalendarVO1.getWk42Day6()) == -1) {
                                newTekCalendarVO.setWk42Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk42Day7() != null && newTekCalendarVO1.getWk42Day7() != null && newTekCalendarVO.getWk42Day7().compareTo(newTekCalendarVO1.getWk42Day7()) == -1) {
                                newTekCalendarVO.setWk42Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk43Day1() != null && newTekCalendarVO1.getWk43Day1() != null && newTekCalendarVO.getWk43Day1().compareTo(newTekCalendarVO1.getWk43Day1()) == -1) {
                                newTekCalendarVO.setWk43Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk43Day2() != null && newTekCalendarVO1.getWk43Day2() != null && newTekCalendarVO.getWk43Day2().compareTo(newTekCalendarVO1.getWk43Day2()) == -1) {
                                newTekCalendarVO.setWk43Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk43Day3() != null && newTekCalendarVO1.getWk43Day3() != null && newTekCalendarVO.getWk43Day3().compareTo(newTekCalendarVO1.getWk43Day3()) == -1) {
                                newTekCalendarVO.setWk43Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk43Day4() != null && newTekCalendarVO1.getWk43Day4() != null && newTekCalendarVO.getWk43Day4().compareTo(newTekCalendarVO1.getWk43Day4()) == -1) {
                                newTekCalendarVO.setWk43Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk43Day5() != null && newTekCalendarVO1.getWk43Day5() != null && newTekCalendarVO.getWk43Day5().compareTo(newTekCalendarVO1.getWk43Day5()) == -1) {
                                newTekCalendarVO.setWk43Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk43Day6() != null && newTekCalendarVO1.getWk43Day6() != null && newTekCalendarVO.getWk43Day6().compareTo(newTekCalendarVO1.getWk43Day6()) == -1) {
                                newTekCalendarVO.setWk43Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk43Day7() != null && newTekCalendarVO1.getWk43Day7() != null && newTekCalendarVO.getWk43Day7().compareTo(newTekCalendarVO1.getWk43Day7()) == -1) {
                                newTekCalendarVO.setWk43Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk44Day1() != null && newTekCalendarVO1.getWk44Day1() != null && newTekCalendarVO.getWk44Day1().compareTo(newTekCalendarVO1.getWk44Day1()) == -1) {
                                newTekCalendarVO.setWk44Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk44Day2() != null && newTekCalendarVO1.getWk44Day2() != null && newTekCalendarVO.getWk44Day2().compareTo(newTekCalendarVO1.getWk44Day2()) == -1) {
                                newTekCalendarVO.setWk44Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk44Day3() != null && newTekCalendarVO1.getWk44Day3() != null && newTekCalendarVO.getWk44Day3().compareTo(newTekCalendarVO1.getWk44Day3()) == -1) {
                                newTekCalendarVO.setWk44Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk44Day4() != null && newTekCalendarVO1.getWk44Day4() != null && newTekCalendarVO.getWk44Day4().compareTo(newTekCalendarVO1.getWk44Day4()) == -1) {
                                newTekCalendarVO.setWk44Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk44Day5() != null && newTekCalendarVO1.getWk44Day5() != null && newTekCalendarVO.getWk44Day5().compareTo(newTekCalendarVO1.getWk44Day5()) == -1) {
                                newTekCalendarVO.setWk44Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk44Day6() != null && newTekCalendarVO1.getWk44Day6() != null && newTekCalendarVO.getWk44Day6().compareTo(newTekCalendarVO1.getWk44Day6()) == -1) {
                                newTekCalendarVO.setWk44Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk44Day7() != null && newTekCalendarVO1.getWk44Day7() != null && newTekCalendarVO.getWk44Day7().compareTo(newTekCalendarVO1.getWk44Day7()) == -1) {
                                newTekCalendarVO.setWk44Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk45Day1() != null && newTekCalendarVO1.getWk45Day1() != null && newTekCalendarVO.getWk45Day1().compareTo(newTekCalendarVO1.getWk45Day1()) == -1) {
                                newTekCalendarVO.setWk45Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk45Day2() != null && newTekCalendarVO1.getWk45Day2() != null && newTekCalendarVO.getWk45Day2().compareTo(newTekCalendarVO1.getWk45Day2()) == -1) {
                                newTekCalendarVO.setWk45Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk45Day3() != null && newTekCalendarVO1.getWk45Day3() != null && newTekCalendarVO.getWk45Day3().compareTo(newTekCalendarVO1.getWk45Day3()) == -1) {
                                newTekCalendarVO.setWk45Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk45Day4() != null && newTekCalendarVO1.getWk45Day4() != null && newTekCalendarVO.getWk45Day4().compareTo(newTekCalendarVO1.getWk45Day4()) == -1) {
                                newTekCalendarVO.setWk45Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk45Day5() != null && newTekCalendarVO1.getWk45Day5() != null && newTekCalendarVO.getWk45Day5().compareTo(newTekCalendarVO1.getWk45Day5()) == -1) {
                                newTekCalendarVO.setWk45Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk45Day6() != null && newTekCalendarVO1.getWk45Day6() != null && newTekCalendarVO.getWk45Day6().compareTo(newTekCalendarVO1.getWk45Day6()) == -1) {
                                newTekCalendarVO.setWk45Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk45Day7() != null && newTekCalendarVO1.getWk45Day7() != null && newTekCalendarVO.getWk45Day7().compareTo(newTekCalendarVO1.getWk45Day7()) == -1) {
                                newTekCalendarVO.setWk45Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk46Day1() != null && newTekCalendarVO1.getWk46Day1() != null && newTekCalendarVO.getWk46Day1().compareTo(newTekCalendarVO1.getWk46Day1()) == -1) {
                                newTekCalendarVO.setWk46Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk46Day2() != null && newTekCalendarVO1.getWk46Day2() != null && newTekCalendarVO.getWk46Day2().compareTo(newTekCalendarVO1.getWk46Day2()) == -1) {
                                newTekCalendarVO.setWk46Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk46Day3() != null && newTekCalendarVO1.getWk46Day3() != null && newTekCalendarVO.getWk46Day3().compareTo(newTekCalendarVO1.getWk46Day3()) == -1) {
                                newTekCalendarVO.setWk46Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk46Day4() != null && newTekCalendarVO1.getWk46Day4() != null && newTekCalendarVO.getWk46Day4().compareTo(newTekCalendarVO1.getWk46Day4()) == -1) {
                                newTekCalendarVO.setWk46Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk46Day5() != null && newTekCalendarVO1.getWk46Day5() != null && newTekCalendarVO.getWk46Day5().compareTo(newTekCalendarVO1.getWk46Day5()) == -1) {
                                newTekCalendarVO.setWk46Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk46Day6() != null && newTekCalendarVO1.getWk46Day6() != null && newTekCalendarVO.getWk46Day6().compareTo(newTekCalendarVO1.getWk46Day6()) == -1) {
                                newTekCalendarVO.setWk46Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk46Day7() != null && newTekCalendarVO1.getWk46Day7() != null && newTekCalendarVO.getWk46Day7().compareTo(newTekCalendarVO1.getWk46Day7()) == -1) {
                                newTekCalendarVO.setWk46Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk47Day1() != null && newTekCalendarVO1.getWk47Day1() != null && newTekCalendarVO.getWk47Day1().compareTo(newTekCalendarVO1.getWk47Day1()) == -1) {
                                newTekCalendarVO.setWk47Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk47Day2() != null && newTekCalendarVO1.getWk47Day2() != null && newTekCalendarVO.getWk47Day2().compareTo(newTekCalendarVO1.getWk47Day2()) == -1) {
                                newTekCalendarVO.setWk47Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk47Day3() != null && newTekCalendarVO1.getWk47Day3() != null && newTekCalendarVO.getWk47Day3().compareTo(newTekCalendarVO1.getWk47Day3()) == -1) {
                                newTekCalendarVO.setWk47Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk47Day4() != null && newTekCalendarVO1.getWk47Day4() != null && newTekCalendarVO.getWk47Day4().compareTo(newTekCalendarVO1.getWk47Day4()) == -1) {
                                newTekCalendarVO.setWk47Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk47Day5() != null && newTekCalendarVO1.getWk47Day5() != null && newTekCalendarVO.getWk47Day5().compareTo(newTekCalendarVO1.getWk47Day5()) == -1) {
                                newTekCalendarVO.setWk47Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk47Day6() != null && newTekCalendarVO1.getWk47Day6() != null && newTekCalendarVO.getWk47Day6().compareTo(newTekCalendarVO1.getWk47Day6()) == -1) {
                                newTekCalendarVO.setWk47Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk47Day7() != null && newTekCalendarVO1.getWk47Day7() != null && newTekCalendarVO.getWk47Day7().compareTo(newTekCalendarVO1.getWk47Day7()) == -1) {
                                newTekCalendarVO.setWk47Day7Status(tekConstants.WK_STATUS_ERROR);
                            }

                            if (newTekCalendarVO.getWk48Day1() != null && newTekCalendarVO1.getWk48Day1() != null && newTekCalendarVO.getWk48Day1().compareTo(newTekCalendarVO1.getWk48Day1()) == -1) {
                                newTekCalendarVO.setWk48Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk48Day2() != null && newTekCalendarVO1.getWk48Day2() != null && newTekCalendarVO.getWk48Day2().compareTo(newTekCalendarVO1.getWk48Day2()) == -1) {
                                newTekCalendarVO.setWk48Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk48Day3() != null && newTekCalendarVO1.getWk48Day3() != null && newTekCalendarVO.getWk48Day3().compareTo(newTekCalendarVO1.getWk48Day3()) == -1) {
                                newTekCalendarVO.setWk48Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk48Day4() != null && newTekCalendarVO1.getWk48Day4() != null && newTekCalendarVO.getWk48Day4().compareTo(newTekCalendarVO1.getWk48Day4()) == -1) {
                                newTekCalendarVO.setWk48Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk48Day5() != null && newTekCalendarVO1.getWk48Day5() != null && newTekCalendarVO.getWk48Day5().compareTo(newTekCalendarVO1.getWk48Day5()) == -1) {
                                newTekCalendarVO.setWk48Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk48Day6() != null && newTekCalendarVO1.getWk48Day6() != null && newTekCalendarVO.getWk48Day6().compareTo(newTekCalendarVO1.getWk48Day6()) == -1) {
                                newTekCalendarVO.setWk48Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk48Day7() != null && newTekCalendarVO1.getWk48Day7() != null && newTekCalendarVO.getWk48Day7().compareTo(newTekCalendarVO1.getWk48Day7()) == -1) {
                                newTekCalendarVO.setWk48Day7Status(tekConstants.WK_STATUS_ERROR);
                            }

                            if (newTekCalendarVO.getWk49Day1() != null && newTekCalendarVO1.getWk49Day1() != null && newTekCalendarVO.getWk49Day1().compareTo(newTekCalendarVO1.getWk49Day1()) == -1) {
                                newTekCalendarVO.setWk49Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk49Day2() != null && newTekCalendarVO1.getWk49Day2() != null && newTekCalendarVO.getWk49Day2().compareTo(newTekCalendarVO1.getWk49Day2()) == -1) {
                                newTekCalendarVO.setWk49Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk49Day3() != null && newTekCalendarVO1.getWk49Day3() != null && newTekCalendarVO.getWk49Day3().compareTo(newTekCalendarVO1.getWk49Day3()) == -1) {
                                newTekCalendarVO.setWk49Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk49Day4() != null && newTekCalendarVO1.getWk49Day4() != null && newTekCalendarVO.getWk49Day4().compareTo(newTekCalendarVO1.getWk49Day4()) == -1) {
                                newTekCalendarVO.setWk49Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk49Day5() != null && newTekCalendarVO1.getWk49Day5() != null && newTekCalendarVO.getWk49Day5().compareTo(newTekCalendarVO1.getWk49Day5()) == -1) {
                                newTekCalendarVO.setWk49Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk49Day6() != null && newTekCalendarVO1.getWk49Day6() != null && newTekCalendarVO.getWk49Day6().compareTo(newTekCalendarVO1.getWk49Day6()) == -1) {
                                newTekCalendarVO.setWk49Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk49Day7() != null && newTekCalendarVO1.getWk49Day7() != null && newTekCalendarVO.getWk49Day7().compareTo(newTekCalendarVO1.getWk49Day7()) == -1) {
                                newTekCalendarVO.setWk49Day7Status(tekConstants.WK_STATUS_ERROR);
                            }

                            if (newTekCalendarVO.getWk50Day1() != null && newTekCalendarVO1.getWk50Day1() != null && newTekCalendarVO.getWk50Day1().compareTo(newTekCalendarVO1.getWk50Day1()) == -1) {
                                newTekCalendarVO.setWk50Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk50Day2() != null && newTekCalendarVO1.getWk50Day2() != null && newTekCalendarVO.getWk50Day2().compareTo(newTekCalendarVO1.getWk50Day2()) == -1) {
                                newTekCalendarVO.setWk50Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk50Day3() != null && newTekCalendarVO1.getWk50Day3() != null && newTekCalendarVO.getWk50Day3().compareTo(newTekCalendarVO1.getWk50Day3()) == -1) {
                                newTekCalendarVO.setWk50Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk50Day4() != null && newTekCalendarVO1.getWk50Day4() != null && newTekCalendarVO.getWk50Day4().compareTo(newTekCalendarVO1.getWk50Day4()) == -1) {
                                newTekCalendarVO.setWk50Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk50Day5() != null && newTekCalendarVO1.getWk50Day5() != null && newTekCalendarVO.getWk50Day5().compareTo(newTekCalendarVO1.getWk50Day5()) == -1) {
                                newTekCalendarVO.setWk50Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk50Day6() != null && newTekCalendarVO1.getWk50Day6() != null && newTekCalendarVO.getWk50Day6().compareTo(newTekCalendarVO1.getWk50Day6()) == -1) {
                                newTekCalendarVO.setWk50Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk50Day7() != null && newTekCalendarVO1.getWk50Day7() != null && newTekCalendarVO.getWk50Day7().compareTo(newTekCalendarVO1.getWk50Day7()) == -1) {
                                newTekCalendarVO.setWk50Day7Status(tekConstants.WK_STATUS_ERROR);
                            }

                            if (newTekCalendarVO.getWk51Day1() != null && newTekCalendarVO1.getWk51Day1() != null && newTekCalendarVO.getWk51Day1().compareTo(newTekCalendarVO1.getWk51Day1()) == -1) {
                                newTekCalendarVO.setWk51Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk51Day2() != null && newTekCalendarVO1.getWk51Day2() != null && newTekCalendarVO.getWk51Day2().compareTo(newTekCalendarVO1.getWk51Day2()) == -1) {
                                newTekCalendarVO.setWk51Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk51Day3() != null && newTekCalendarVO1.getWk51Day3() != null && newTekCalendarVO.getWk51Day3().compareTo(newTekCalendarVO1.getWk51Day3()) == -1) {
                                newTekCalendarVO.setWk51Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk51Day4() != null && newTekCalendarVO1.getWk51Day4() != null && newTekCalendarVO.getWk51Day4().compareTo(newTekCalendarVO1.getWk51Day4()) == -1) {
                                newTekCalendarVO.setWk51Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk51Day5() != null && newTekCalendarVO1.getWk51Day5() != null && newTekCalendarVO.getWk51Day5().compareTo(newTekCalendarVO1.getWk51Day5()) == -1) {
                                newTekCalendarVO.setWk51Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk51Day6() != null && newTekCalendarVO1.getWk51Day6() != null && newTekCalendarVO.getWk51Day6().compareTo(newTekCalendarVO1.getWk51Day6()) == -1) {
                                newTekCalendarVO.setWk51Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk51Day7() != null && newTekCalendarVO1.getWk51Day7() != null && newTekCalendarVO.getWk51Day7().compareTo(newTekCalendarVO1.getWk51Day7()) == -1) {
                                newTekCalendarVO.setWk51Day7Status(tekConstants.WK_STATUS_ERROR);
                            }

                            if (newTekCalendarVO.getWk52Day1() != null && newTekCalendarVO1.getWk52Day1() != null && newTekCalendarVO.getWk52Day1().compareTo(newTekCalendarVO1.getWk52Day1()) == -1) {
                                newTekCalendarVO.setWk52Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk52Day2() != null && newTekCalendarVO1.getWk52Day2() != null && newTekCalendarVO.getWk52Day2().compareTo(newTekCalendarVO1.getWk52Day2()) == -1) {
                                newTekCalendarVO.setWk52Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk52Day3() != null && newTekCalendarVO1.getWk52Day3() != null && newTekCalendarVO.getWk52Day3().compareTo(newTekCalendarVO1.getWk52Day3()) == -1) {
                                newTekCalendarVO.setWk52Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk52Day4() != null && newTekCalendarVO1.getWk52Day4() != null && newTekCalendarVO.getWk52Day4().compareTo(newTekCalendarVO1.getWk52Day4()) == -1) {
                                newTekCalendarVO.setWk52Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk52Day5() != null && newTekCalendarVO1.getWk52Day5() != null && newTekCalendarVO.getWk52Day5().compareTo(newTekCalendarVO1.getWk52Day5()) == -1) {
                                newTekCalendarVO.setWk52Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk52Day6() != null && newTekCalendarVO1.getWk52Day6() != null && newTekCalendarVO.getWk52Day6().compareTo(newTekCalendarVO1.getWk52Day6()) == -1) {
                                newTekCalendarVO.setWk52Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk52Day7() != null && newTekCalendarVO1.getWk52Day7() != null && newTekCalendarVO.getWk52Day7().compareTo(newTekCalendarVO1.getWk52Day7()) == -1) {
                                newTekCalendarVO.setWk52Day7Status(tekConstants.WK_STATUS_ERROR);
                            }

                            if (newTekCalendarVO.getWk53Day1() != null && newTekCalendarVO1.getWk53Day1() != null && newTekCalendarVO.getWk53Day1().compareTo(newTekCalendarVO1.getWk53Day1()) == -1) {
                                newTekCalendarVO.setWk53Day1Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk53Day2() != null && newTekCalendarVO1.getWk53Day2() != null && newTekCalendarVO.getWk53Day2().compareTo(newTekCalendarVO1.getWk53Day2()) == -1) {
                                newTekCalendarVO.setWk53Day2Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk53Day3() != null && newTekCalendarVO1.getWk53Day3() != null && newTekCalendarVO.getWk53Day3().compareTo(newTekCalendarVO1.getWk53Day3()) == -1) {
                                newTekCalendarVO.setWk53Day3Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk53Day4() != null && newTekCalendarVO1.getWk53Day4() != null && newTekCalendarVO.getWk53Day4().compareTo(newTekCalendarVO1.getWk53Day4()) == -1) {
                                newTekCalendarVO.setWk53Day4Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk53Day5() != null && newTekCalendarVO1.getWk53Day5() != null && newTekCalendarVO.getWk53Day5().compareTo(newTekCalendarVO1.getWk53Day5()) == -1) {
                                newTekCalendarVO.setWk53Day5Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk53Day6() != null && newTekCalendarVO1.getWk53Day6() != null && newTekCalendarVO.getWk53Day6().compareTo(newTekCalendarVO1.getWk53Day6()) == -1) {
                                newTekCalendarVO.setWk53Day6Status(tekConstants.WK_STATUS_ERROR);
                            }
                            if (newTekCalendarVO.getWk53Day7() != null && newTekCalendarVO1.getWk53Day7() != null && newTekCalendarVO.getWk53Day7().compareTo(newTekCalendarVO1.getWk53Day7()) == -1) {
                                newTekCalendarVO.setWk53Day7Status(tekConstants.WK_STATUS_ERROR);
                            }
                        }
                    }
                }
            }
        }
        return newTekCalendarVO;
    }

    public NewTekCalendarVO queryByModelSortId(Map<String,NewTekCalendarVO> map,NewTekCalendarVO newTekCalendarVO){
        if (newTekCalendarVO.getModelSortId() != 1) {
            for (int i = newTekCalendarVO.getModelSortId() - 1; i >= 1; i--) {
                NewTekProductModel productModel = new NewTekProductModel();
                productModel.setCategoryId(newTekCalendarVO.getCategoryId());
                productModel.setModelSortId(i);
//                productModel.setDisposeId(newTekCalendarVO.getDisposeId());
                // TODO: 2020/8/6 修改disposeId为disposeSortId
                productModel.setDisposeSortId(newTekCalendarVO.getDisposeSortId());
//                String countryNew = newTekCalendarVO.getCountry().split("_")[0] + "-" + newTekCalendarVO.getCountry().split("_")[1];
//                productModel.setCountryName(countryNew);
                productModel.setCountryName(newTekCalendarVO.getCountry());
                List<NewTekProductModel> productModelList = newTekProductModelMapper.getProductModelByCategoryAndModelAndDisposeSortId(productModel);
                if (productModelList.size() > 0 && productModelList != null) {
//                    String key = productModelList.get(0).getSku() + "," + newTekCalendarVO.getCustomerId() + "," + newTekCalendarVO.getCountry() + "," + newTekCalendarVO.getYear();
                    for(Map.Entry<String, NewTekCalendarVO> m : map.entrySet()){
                        String mapKey = m.getKey();
                        String skuu = mapKey.split(",")[0];
                        String countryy = mapKey.split(",")[2];
                        String yearr = mapKey.split(",")[3];

                        if (productModelList.get(0).getDisposeGroupId().equals(newTekCalendarVO.getDisposeGroupId()) && skuu.equals(productModelList.get(0).getSku()) && countryy.equals(newTekCalendarVO.getCountry()) && yearr.equals(newTekCalendarVO.getYear())) {
//                            NewTekCalendarVO newTekCalendarVO1 = map.get(key);
                            NewTekCalendarVO newTekCalendarVO1 = m.getValue();
                            if (newTekCalendarVO.getDisposeName().equals(newTekCalendarVO1.getDisposeName())) {
                                //当前同品类、同配置，比前一个系列的价格低，报异常
                                if (newTekCalendarVO.getWk1MsrpUSD() != null && newTekCalendarVO1.getWk1MsrpUSD() != null && (newTekCalendarVO.getWk1MsrpUSD().compareTo(newTekCalendarVO1.getWk1MsrpUSD()) == -1)) {
                                    newTekCalendarVO.setWk1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk2MsrpUSD() != null && newTekCalendarVO1.getWk2MsrpUSD() != null && newTekCalendarVO.getWk2MsrpUSD().compareTo(newTekCalendarVO1.getWk2MsrpUSD()) == -1) {
                                    newTekCalendarVO.setWk2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk3MsrpUSD() != null && newTekCalendarVO1.getWk3MsrpUSD() != null && newTekCalendarVO.getWk3MsrpUSD().compareTo(newTekCalendarVO1.getWk3MsrpUSD()) == -1) {
                                    newTekCalendarVO.setWk3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk4MsrpUSD() != null && newTekCalendarVO1.getWk4MsrpUSD() != null && newTekCalendarVO.getWk4MsrpUSD().compareTo(newTekCalendarVO1.getWk4MsrpUSD()) == -1) {
                                    newTekCalendarVO.setWk4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk5MsrpUSD() != null && newTekCalendarVO1.getWk5MsrpUSD() != null && newTekCalendarVO.getWk5MsrpUSD().compareTo(newTekCalendarVO1.getWk5MsrpUSD()) == -1) {
                                    newTekCalendarVO.setWk5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk6MsrpUSD() != null && newTekCalendarVO1.getWk6MsrpUSD() != null && newTekCalendarVO.getWk6MsrpUSD().compareTo(newTekCalendarVO1.getWk6MsrpUSD()) == -1) {
                                    newTekCalendarVO.setWk6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk7MsrpUSD() != null && newTekCalendarVO1.getWk7MsrpUSD() != null && newTekCalendarVO.getWk7MsrpUSD().compareTo(newTekCalendarVO1.getWk7MsrpUSD()) == -1) {
                                    newTekCalendarVO.setWk7Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk8MsrpUSD() != null && newTekCalendarVO1.getWk8MsrpUSD() != null && newTekCalendarVO.getWk8MsrpUSD().compareTo(newTekCalendarVO1.getWk8MsrpUSD()) == -1) {
                                    newTekCalendarVO.setWk8Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk9MsrpUSD() != null && newTekCalendarVO1.getWk9MsrpUSD() != null && newTekCalendarVO.getWk9MsrpUSD().compareTo(newTekCalendarVO1.getWk9MsrpUSD()) == -1) {
                                    newTekCalendarVO.setWk9Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk10MsrpUSD() != null && newTekCalendarVO1.getWk10MsrpUSD() != null && newTekCalendarVO.getWk10MsrpUSD().compareTo(newTekCalendarVO1.getWk10MsrpUSD()) == -1) {
                                    newTekCalendarVO.setWk10Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk11MsrpUSD() != null && newTekCalendarVO1.getWk11MsrpUSD() != null && newTekCalendarVO.getWk11MsrpUSD().compareTo(newTekCalendarVO1.getWk11MsrpUSD()) == -1) {
                                    newTekCalendarVO.setWk11Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk12MsrpUSD() != null && newTekCalendarVO1.getWk12MsrpUSD() != null && newTekCalendarVO.getWk12MsrpUSD().compareTo(newTekCalendarVO1.getWk12MsrpUSD()) == -1) {
                                    newTekCalendarVO.setWk12Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk13MsrpUSD() != null && newTekCalendarVO1.getWk13MsrpUSD() != null && newTekCalendarVO.getWk13MsrpUSD().compareTo(newTekCalendarVO1.getWk13MsrpUSD()) == -1) {
                                    newTekCalendarVO.setWk13Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk14MsrpUSD() != null && newTekCalendarVO1.getWk14MsrpUSD() != null && newTekCalendarVO.getWk14MsrpUSD().compareTo(newTekCalendarVO1.getWk14MsrpUSD()) == -1) {
                                    newTekCalendarVO.setWk14Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk15MsrpUSD() != null && newTekCalendarVO1.getWk15MsrpUSD() != null && newTekCalendarVO.getWk15MsrpUSD().compareTo(newTekCalendarVO1.getWk15MsrpUSD()) == -1) {
                                    newTekCalendarVO.setWk15Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk16MsrpUSD() != null && newTekCalendarVO1.getWk16MsrpUSD() != null && newTekCalendarVO.getWk16MsrpUSD().compareTo(newTekCalendarVO1.getWk16MsrpUSD()) == -1) {
                                    newTekCalendarVO.setWk16Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk17MsrpUSD() != null && newTekCalendarVO1.getWk17MsrpUSD() != null && newTekCalendarVO.getWk17MsrpUSD().compareTo(newTekCalendarVO1.getWk17MsrpUSD()) == -1) {
                                    newTekCalendarVO.setWk17Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk18MsrpUSD() != null && newTekCalendarVO1.getWk18MsrpUSD() != null && newTekCalendarVO.getWk18MsrpUSD().compareTo(newTekCalendarVO1.getWk18MsrpUSD()) == -1) {
                                    newTekCalendarVO.setWk18Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk19MsrpUSD() != null && newTekCalendarVO1.getWk19MsrpUSD() != null && newTekCalendarVO.getWk19MsrpUSD().compareTo(newTekCalendarVO1.getWk19MsrpUSD()) == -1) {
                                    newTekCalendarVO.setWk19Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk20MsrpUSD() != null && newTekCalendarVO1.getWk20MsrpUSD() != null && newTekCalendarVO.getWk20MsrpUSD().compareTo(newTekCalendarVO1.getWk20MsrpUSD()) == -1) {
                                    newTekCalendarVO.setWk20Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk21MsrpUSD() != null && newTekCalendarVO1.getWk21MsrpUSD() != null && newTekCalendarVO.getWk21MsrpUSD().compareTo(newTekCalendarVO1.getWk21MsrpUSD()) == -1) {
                                    newTekCalendarVO.setWk21Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk22MsrpUSD() != null && newTekCalendarVO1.getWk22MsrpUSD() != null && newTekCalendarVO.getWk22MsrpUSD().compareTo(newTekCalendarVO1.getWk22MsrpUSD()) == -1) {
                                    newTekCalendarVO.setWk22Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk23MsrpUSD() != null && newTekCalendarVO1.getWk23MsrpUSD() != null && newTekCalendarVO.getWk23MsrpUSD().compareTo(newTekCalendarVO1.getWk23MsrpUSD()) == -1) {
                                    newTekCalendarVO.setWk23Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk24MsrpUSD() != null && newTekCalendarVO1.getWk24MsrpUSD() != null && newTekCalendarVO.getWk24MsrpUSD().compareTo(newTekCalendarVO1.getWk24MsrpUSD()) == -1) {
                                    newTekCalendarVO.setWk24Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk25MsrpUSD() != null && newTekCalendarVO1.getWk25MsrpUSD() != null && newTekCalendarVO.getWk25MsrpUSD().compareTo(newTekCalendarVO1.getWk25MsrpUSD()) == -1) {
                                    newTekCalendarVO.setWk25Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk26MsrpUSD() != null && newTekCalendarVO1.getWk26MsrpUSD() != null && newTekCalendarVO.getWk26MsrpUSD().compareTo(newTekCalendarVO1.getWk26MsrpUSD()) == -1) {
                                    newTekCalendarVO.setWk26Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk27MsrpUSD() != null && newTekCalendarVO1.getWk27MsrpUSD() != null && newTekCalendarVO.getWk27MsrpUSD().compareTo(newTekCalendarVO1.getWk27MsrpUSD()) == -1) {
                                    newTekCalendarVO.setWk27Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk28MsrpUSD() != null && newTekCalendarVO1.getWk28MsrpUSD() != null && newTekCalendarVO.getWk28MsrpUSD().compareTo(newTekCalendarVO1.getWk28MsrpUSD()) == -1) {
                                    newTekCalendarVO.setWk28Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk29MsrpUSD() != null && newTekCalendarVO1.getWk29MsrpUSD() != null && newTekCalendarVO.getWk29MsrpUSD().compareTo(newTekCalendarVO1.getWk29MsrpUSD()) == -1) {
                                    newTekCalendarVO.setWk29Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk30MsrpUSD() != null && newTekCalendarVO1.getWk30MsrpUSD() != null && newTekCalendarVO.getWk30MsrpUSD().compareTo(newTekCalendarVO1.getWk30MsrpUSD()) == -1) {
                                    newTekCalendarVO.setWk30Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk31MsrpUSD() != null && newTekCalendarVO1.getWk31MsrpUSD() != null && newTekCalendarVO.getWk31MsrpUSD().compareTo(newTekCalendarVO1.getWk31MsrpUSD()) == -1) {
                                    newTekCalendarVO.setWk31Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk32MsrpUSD() != null && newTekCalendarVO1.getWk32MsrpUSD() != null && newTekCalendarVO.getWk32MsrpUSD().compareTo(newTekCalendarVO1.getWk32MsrpUSD()) == -1) {
                                    newTekCalendarVO.setWk32Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk33MsrpUSD() != null && newTekCalendarVO1.getWk33MsrpUSD() != null && newTekCalendarVO.getWk33MsrpUSD().compareTo(newTekCalendarVO1.getWk33MsrpUSD()) == -1) {
                                    newTekCalendarVO.setWk33Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk34MsrpUSD() != null && newTekCalendarVO1.getWk34MsrpUSD() != null && newTekCalendarVO.getWk34MsrpUSD().compareTo(newTekCalendarVO1.getWk34MsrpUSD()) == -1) {
                                    newTekCalendarVO.setWk34Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk35MsrpUSD() != null && newTekCalendarVO1.getWk35MsrpUSD() != null && newTekCalendarVO.getWk35MsrpUSD().compareTo(newTekCalendarVO1.getWk35MsrpUSD()) == -1) {
                                    newTekCalendarVO.setWk35Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk36MsrpUSD() != null && newTekCalendarVO1.getWk36MsrpUSD() != null && newTekCalendarVO.getWk36MsrpUSD().compareTo(newTekCalendarVO1.getWk36MsrpUSD()) == -1) {
                                    newTekCalendarVO.setWk36Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk37MsrpUSD() != null && newTekCalendarVO1.getWk37MsrpUSD() != null && newTekCalendarVO.getWk37MsrpUSD().compareTo(newTekCalendarVO1.getWk37MsrpUSD()) == -1) {
                                    newTekCalendarVO.setWk37Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk38MsrpUSD() != null && newTekCalendarVO1.getWk38MsrpUSD() != null && newTekCalendarVO.getWk38MsrpUSD().compareTo(newTekCalendarVO1.getWk38MsrpUSD()) == -1) {
                                    newTekCalendarVO.setWk38Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk39MsrpUSD() != null && newTekCalendarVO1.getWk39MsrpUSD() != null && newTekCalendarVO.getWk39MsrpUSD().compareTo(newTekCalendarVO1.getWk39MsrpUSD()) == -1) {
                                    newTekCalendarVO.setWk39Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk40MsrpUSD() != null && newTekCalendarVO1.getWk40MsrpUSD() != null && newTekCalendarVO.getWk40MsrpUSD().compareTo(newTekCalendarVO1.getWk40MsrpUSD()) == -1) {
                                    newTekCalendarVO.setWk40Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk41MsrpUSD() != null && newTekCalendarVO1.getWk41MsrpUSD() != null && newTekCalendarVO.getWk41MsrpUSD().compareTo(newTekCalendarVO1.getWk41MsrpUSD()) == -1) {
                                    newTekCalendarVO.setWk41Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk42MsrpUSD() != null && newTekCalendarVO1.getWk42MsrpUSD() != null && newTekCalendarVO.getWk42MsrpUSD().compareTo(newTekCalendarVO1.getWk42MsrpUSD()) == -1) {
                                    newTekCalendarVO.setWk42Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk43MsrpUSD() != null && newTekCalendarVO1.getWk43MsrpUSD() != null && newTekCalendarVO.getWk43MsrpUSD().compareTo(newTekCalendarVO1.getWk43MsrpUSD()) == -1) {
                                    newTekCalendarVO.setWk43Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk44MsrpUSD() != null && newTekCalendarVO1.getWk44MsrpUSD() != null && newTekCalendarVO.getWk44MsrpUSD().compareTo(newTekCalendarVO1.getWk44MsrpUSD()) == -1) {
                                    newTekCalendarVO.setWk44Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk45MsrpUSD() != null && newTekCalendarVO1.getWk45MsrpUSD() != null && newTekCalendarVO.getWk45MsrpUSD().compareTo(newTekCalendarVO1.getWk45MsrpUSD()) == -1) {
                                    newTekCalendarVO.setWk45Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk46MsrpUSD() != null && newTekCalendarVO1.getWk46MsrpUSD() != null && newTekCalendarVO.getWk46MsrpUSD().compareTo(newTekCalendarVO1.getWk46MsrpUSD()) == -1) {
                                    newTekCalendarVO.setWk46Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk47MsrpUSD() != null && newTekCalendarVO1.getWk47MsrpUSD() != null && newTekCalendarVO.getWk47MsrpUSD().compareTo(newTekCalendarVO1.getWk47MsrpUSD()) == -1) {
                                    newTekCalendarVO.setWk47Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk48MsrpUSD() != null && newTekCalendarVO1.getWk48MsrpUSD() != null && newTekCalendarVO.getWk48MsrpUSD().compareTo(newTekCalendarVO1.getWk48MsrpUSD()) == -1) {
                                    newTekCalendarVO.setWk48Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk49MsrpUSD() != null && newTekCalendarVO1.getWk49MsrpUSD() != null && newTekCalendarVO.getWk49MsrpUSD().compareTo(newTekCalendarVO1.getWk49MsrpUSD()) == -1) {
                                    newTekCalendarVO.setWk49Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk50MsrpUSD() != null && newTekCalendarVO1.getWk50MsrpUSD() != null && newTekCalendarVO.getWk50MsrpUSD().compareTo(newTekCalendarVO1.getWk50MsrpUSD()) == -1) {
                                    newTekCalendarVO.setWk50Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk51MsrpUSD() != null && newTekCalendarVO1.getWk51MsrpUSD() != null && newTekCalendarVO.getWk51MsrpUSD().compareTo(newTekCalendarVO1.getWk51MsrpUSD()) == -1) {
                                    newTekCalendarVO.setWk51Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk52MsrpUSD() != null && newTekCalendarVO1.getWk52MsrpUSD() != null && newTekCalendarVO.getWk52MsrpUSD().compareTo(newTekCalendarVO1.getWk52MsrpUSD()) == -1) {
                                    newTekCalendarVO.setWk52Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk53MsrpUSD() != null && newTekCalendarVO1.getWk53MsrpUSD() != null && newTekCalendarVO.getWk53MsrpUSD().compareTo(newTekCalendarVO1.getWk53MsrpUSD()) == -1) {
                                    newTekCalendarVO.setWk53Status(tekConstants.WK_STATUS_ERROR);
                                }
                                //day判断
                                if (newTekCalendarVO.getWk1Day1() != null && newTekCalendarVO1.getWk1Day1() != null && newTekCalendarVO.getWk1Day1().compareTo(newTekCalendarVO1.getWk1Day1()) == -1) {
                                    newTekCalendarVO.setWk1Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk1Day2() != null && newTekCalendarVO1.getWk1Day2() != null && newTekCalendarVO.getWk1Day2().compareTo(newTekCalendarVO1.getWk1Day2()) == -1) {
                                    newTekCalendarVO.setWk1Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk1Day3() != null && newTekCalendarVO1.getWk1Day3() != null && newTekCalendarVO.getWk1Day3().compareTo(newTekCalendarVO1.getWk1Day3()) == -1) {
                                    newTekCalendarVO.setWk1Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk1Day4() != null && newTekCalendarVO1.getWk1Day4() != null && newTekCalendarVO.getWk1Day4().compareTo(newTekCalendarVO1.getWk1Day4()) == -1) {
                                    newTekCalendarVO.setWk1Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk1Day5() != null && newTekCalendarVO1.getWk1Day5() != null && newTekCalendarVO.getWk1Day5().compareTo(newTekCalendarVO1.getWk1Day5()) == -1) {
                                    newTekCalendarVO.setWk1Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk1Day6() != null && newTekCalendarVO1.getWk1Day6() != null && newTekCalendarVO.getWk1Day6().compareTo(newTekCalendarVO1.getWk1Day6()) == -1) {
                                    newTekCalendarVO.setWk1Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk1Day7() != null && newTekCalendarVO1.getWk1Day7() != null && newTekCalendarVO.getWk1Day7().compareTo(newTekCalendarVO1.getWk1Day7()) == -1) {
                                    newTekCalendarVO.setWk1Day7Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk2Day1() != null && newTekCalendarVO1.getWk2Day1() != null && newTekCalendarVO.getWk2Day1().compareTo(newTekCalendarVO1.getWk2Day1()) == -1) {
                                    newTekCalendarVO.setWk2Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk2Day2() != null && newTekCalendarVO1.getWk2Day2() != null && newTekCalendarVO.getWk2Day2().compareTo(newTekCalendarVO1.getWk2Day2()) == -1) {
                                    newTekCalendarVO.setWk2Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk2Day3() != null && newTekCalendarVO1.getWk2Day3() != null && newTekCalendarVO.getWk2Day3().compareTo(newTekCalendarVO1.getWk2Day3()) == -1) {
                                    newTekCalendarVO.setWk2Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk2Day4() != null && newTekCalendarVO1.getWk2Day4() != null && newTekCalendarVO.getWk2Day4().compareTo(newTekCalendarVO1.getWk2Day4()) == -1) {
                                    newTekCalendarVO.setWk2Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk2Day5() != null && newTekCalendarVO1.getWk2Day5() != null && newTekCalendarVO.getWk2Day5().compareTo(newTekCalendarVO1.getWk2Day5()) == -1) {
                                    newTekCalendarVO.setWk2Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk2Day6() != null && newTekCalendarVO1.getWk2Day6() != null && newTekCalendarVO.getWk2Day6().compareTo(newTekCalendarVO1.getWk2Day6()) == -1) {
                                    newTekCalendarVO.setWk2Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk2Day7() != null && newTekCalendarVO1.getWk2Day7() != null && newTekCalendarVO.getWk2Day7().compareTo(newTekCalendarVO1.getWk2Day7()) == -1) {
                                    newTekCalendarVO.setWk2Day7Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk3Day1() != null && newTekCalendarVO1.getWk3Day1() != null && newTekCalendarVO.getWk3Day1().compareTo(newTekCalendarVO1.getWk3Day1()) == -1) {
                                    newTekCalendarVO.setWk3Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk3Day2() != null && newTekCalendarVO1.getWk3Day2() != null && newTekCalendarVO.getWk3Day2().compareTo(newTekCalendarVO1.getWk3Day2()) == -1) {
                                    newTekCalendarVO.setWk3Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk3Day3() != null && newTekCalendarVO1.getWk3Day3() != null && newTekCalendarVO.getWk3Day3().compareTo(newTekCalendarVO1.getWk3Day3()) == -1) {
                                    newTekCalendarVO.setWk3Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk3Day4() != null && newTekCalendarVO1.getWk3Day4() != null && newTekCalendarVO.getWk3Day4().compareTo(newTekCalendarVO1.getWk3Day4()) == -1) {
                                    newTekCalendarVO.setWk3Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk3Day5() != null && newTekCalendarVO1.getWk3Day5() != null && newTekCalendarVO.getWk3Day5().compareTo(newTekCalendarVO1.getWk3Day5()) == -1) {
                                    newTekCalendarVO.setWk3Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk3Day6() != null && newTekCalendarVO1.getWk3Day6() != null && newTekCalendarVO.getWk3Day6().compareTo(newTekCalendarVO1.getWk3Day6()) == -1) {
                                    newTekCalendarVO.setWk3Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk3Day7() != null && newTekCalendarVO1.getWk3Day7() != null && newTekCalendarVO.getWk3Day7().compareTo(newTekCalendarVO1.getWk3Day7()) == -1) {
                                    newTekCalendarVO.setWk3Day7Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk4Day1() != null && newTekCalendarVO1.getWk4Day1() != null && newTekCalendarVO.getWk4Day1().compareTo(newTekCalendarVO1.getWk4Day1()) == -1) {
                                    newTekCalendarVO.setWk4Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk4Day2() != null && newTekCalendarVO1.getWk4Day2() != null && newTekCalendarVO.getWk4Day2().compareTo(newTekCalendarVO1.getWk4Day2()) == -1) {
                                    newTekCalendarVO.setWk4Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk4Day3() != null && newTekCalendarVO1.getWk4Day3() != null && newTekCalendarVO.getWk4Day3().compareTo(newTekCalendarVO1.getWk4Day3()) == -1) {
                                    newTekCalendarVO.setWk4Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk4Day4() != null && newTekCalendarVO1.getWk4Day4() != null && newTekCalendarVO.getWk4Day4().compareTo(newTekCalendarVO1.getWk4Day4()) == -1) {
                                    newTekCalendarVO.setWk4Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk4Day5() != null && newTekCalendarVO1.getWk4Day5() != null && newTekCalendarVO.getWk4Day5().compareTo(newTekCalendarVO1.getWk4Day5()) == -1) {
                                    newTekCalendarVO.setWk4Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk4Day6() != null && newTekCalendarVO1.getWk4Day6() != null && newTekCalendarVO.getWk4Day6().compareTo(newTekCalendarVO1.getWk4Day6()) == -1) {
                                    newTekCalendarVO.setWk4Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk4Day7() != null && newTekCalendarVO1.getWk4Day7() != null && newTekCalendarVO.getWk4Day7().compareTo(newTekCalendarVO1.getWk4Day7()) == -1) {
                                    newTekCalendarVO.setWk4Day7Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk5Day1() != null && newTekCalendarVO1.getWk5Day1() != null && newTekCalendarVO.getWk5Day1().compareTo(newTekCalendarVO1.getWk5Day1()) == -1) {
                                    newTekCalendarVO.setWk5Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk5Day2() != null && newTekCalendarVO1.getWk5Day2() != null && newTekCalendarVO.getWk5Day2().compareTo(newTekCalendarVO1.getWk5Day2()) == -1) {
                                    newTekCalendarVO.setWk5Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk5Day3() != null && newTekCalendarVO1.getWk5Day3() != null && newTekCalendarVO.getWk5Day3().compareTo(newTekCalendarVO1.getWk5Day3()) == -1) {
                                    newTekCalendarVO.setWk5Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk5Day4() != null && newTekCalendarVO1.getWk5Day4() != null && newTekCalendarVO.getWk5Day4().compareTo(newTekCalendarVO1.getWk5Day4()) == -1) {
                                    newTekCalendarVO.setWk5Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk5Day5() != null && newTekCalendarVO1.getWk5Day5() != null && newTekCalendarVO.getWk5Day5().compareTo(newTekCalendarVO1.getWk5Day5()) == -1) {
                                    newTekCalendarVO.setWk5Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk5Day6() != null && newTekCalendarVO1.getWk5Day6() != null && newTekCalendarVO.getWk5Day6().compareTo(newTekCalendarVO1.getWk5Day6()) == -1) {
                                    newTekCalendarVO.setWk5Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk5Day7() != null && newTekCalendarVO1.getWk5Day7() != null && newTekCalendarVO.getWk5Day7().compareTo(newTekCalendarVO1.getWk5Day7()) == -1) {
                                    newTekCalendarVO.setWk5Day7Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk6Day1() != null && newTekCalendarVO1.getWk6Day1() != null && newTekCalendarVO.getWk6Day1().compareTo(newTekCalendarVO1.getWk6Day1()) == -1) {
                                    newTekCalendarVO.setWk7Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk6Day2() != null && newTekCalendarVO1.getWk6Day2() != null && newTekCalendarVO.getWk6Day2().compareTo(newTekCalendarVO1.getWk6Day2()) == -1) {
                                    newTekCalendarVO.setWk7Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk6Day3() != null && newTekCalendarVO1.getWk6Day3() != null && newTekCalendarVO.getWk6Day3().compareTo(newTekCalendarVO1.getWk6Day3()) == -1) {
                                    newTekCalendarVO.setWk7Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk6Day4() != null && newTekCalendarVO1.getWk6Day4() != null && newTekCalendarVO.getWk6Day4().compareTo(newTekCalendarVO1.getWk6Day4()) == -1) {
                                    newTekCalendarVO.setWk7Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk6Day5() != null && newTekCalendarVO1.getWk6Day5() != null && newTekCalendarVO.getWk6Day5().compareTo(newTekCalendarVO1.getWk6Day5()) == -1) {
                                    newTekCalendarVO.setWk7Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk6Day6() != null && newTekCalendarVO1.getWk6Day6() != null && newTekCalendarVO.getWk6Day6().compareTo(newTekCalendarVO1.getWk6Day6()) == -1) {
                                    newTekCalendarVO.setWk7Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk6Day7() != null && newTekCalendarVO1.getWk6Day7() != null && newTekCalendarVO.getWk6Day7().compareTo(newTekCalendarVO1.getWk6Day7()) == -1) {
                                    newTekCalendarVO.setWk7Day7Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk7Day1() != null && newTekCalendarVO1.getWk7Day1() != null && newTekCalendarVO.getWk7Day1().compareTo(newTekCalendarVO1.getWk7Day1()) == -1) {
                                    newTekCalendarVO.setWk7Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk7Day2() != null && newTekCalendarVO1.getWk7Day2() != null && newTekCalendarVO.getWk7Day2().compareTo(newTekCalendarVO1.getWk7Day2()) == -1) {
                                    newTekCalendarVO.setWk7Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk7Day3() != null && newTekCalendarVO1.getWk7Day3() != null && newTekCalendarVO.getWk7Day3().compareTo(newTekCalendarVO1.getWk7Day3()) == -1) {
                                    newTekCalendarVO.setWk7Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk7Day4() != null && newTekCalendarVO1.getWk7Day4() != null && newTekCalendarVO.getWk7Day4().compareTo(newTekCalendarVO1.getWk7Day4()) == -1) {
                                    newTekCalendarVO.setWk7Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk7Day5() != null && newTekCalendarVO1.getWk7Day5() != null && newTekCalendarVO.getWk7Day5().compareTo(newTekCalendarVO1.getWk7Day5()) == -1) {
                                    newTekCalendarVO.setWk7Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk7Day6() != null && newTekCalendarVO1.getWk7Day6() != null && newTekCalendarVO.getWk7Day6().compareTo(newTekCalendarVO1.getWk7Day6()) == -1) {
                                    newTekCalendarVO.setWk7Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk7Day7() != null && newTekCalendarVO1.getWk7Day7() != null && newTekCalendarVO.getWk7Day7().compareTo(newTekCalendarVO1.getWk7Day7()) == -1) {
                                    newTekCalendarVO.setWk7Day7Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk8Day1() != null && newTekCalendarVO1.getWk8Day1() != null && newTekCalendarVO.getWk8Day1().compareTo(newTekCalendarVO1.getWk8Day1()) == -1) {
                                    newTekCalendarVO.setWk8Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk8Day2() != null && newTekCalendarVO1.getWk8Day2() != null && newTekCalendarVO.getWk8Day2().compareTo(newTekCalendarVO1.getWk8Day2()) == -1) {
                                    newTekCalendarVO.setWk8Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk8Day3() != null && newTekCalendarVO1.getWk8Day3() != null && newTekCalendarVO.getWk8Day3().compareTo(newTekCalendarVO1.getWk8Day3()) == -1) {
                                    newTekCalendarVO.setWk8Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk8Day4() != null && newTekCalendarVO1.getWk8Day4() != null && newTekCalendarVO.getWk8Day4().compareTo(newTekCalendarVO1.getWk8Day4()) == -1) {
                                    newTekCalendarVO.setWk8Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk8Day5() != null && newTekCalendarVO1.getWk8Day5() != null && newTekCalendarVO.getWk8Day5().compareTo(newTekCalendarVO1.getWk8Day5()) == -1) {
                                    newTekCalendarVO.setWk8Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk8Day6() != null && newTekCalendarVO1.getWk8Day6() != null && newTekCalendarVO.getWk8Day6().compareTo(newTekCalendarVO1.getWk8Day6()) == -1) {
                                    newTekCalendarVO.setWk8Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk8Day7() != null && newTekCalendarVO1.getWk8Day7() != null && newTekCalendarVO.getWk8Day7().compareTo(newTekCalendarVO1.getWk8Day7()) == -1) {
                                    newTekCalendarVO.setWk8Day7Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk9Day1() != null && newTekCalendarVO1.getWk9Day1() != null && newTekCalendarVO.getWk9Day1().compareTo(newTekCalendarVO1.getWk9Day1()) == -1) {
                                    newTekCalendarVO.setWk9Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk9Day2() != null && newTekCalendarVO1.getWk9Day2() != null && newTekCalendarVO.getWk9Day2().compareTo(newTekCalendarVO1.getWk9Day2()) == -1) {
                                    newTekCalendarVO.setWk9Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk9Day3() != null && newTekCalendarVO1.getWk9Day3() != null && newTekCalendarVO.getWk9Day3().compareTo(newTekCalendarVO1.getWk9Day3()) == -1) {
                                    newTekCalendarVO.setWk9Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk9Day4() != null && newTekCalendarVO1.getWk9Day4() != null && newTekCalendarVO.getWk9Day4().compareTo(newTekCalendarVO1.getWk9Day4()) == -1) {
                                    newTekCalendarVO.setWk9Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk9Day5() != null && newTekCalendarVO1.getWk9Day5() != null && newTekCalendarVO.getWk9Day5().compareTo(newTekCalendarVO1.getWk9Day5()) == -1) {
                                    newTekCalendarVO.setWk9Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk9Day6() != null && newTekCalendarVO1.getWk9Day6() != null && newTekCalendarVO.getWk9Day6().compareTo(newTekCalendarVO1.getWk9Day6()) == -1) {
                                    newTekCalendarVO.setWk9Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk9Day7() != null && newTekCalendarVO1.getWk9Day7() != null && newTekCalendarVO.getWk9Day7().compareTo(newTekCalendarVO1.getWk9Day7()) == -1) {
                                    newTekCalendarVO.setWk9Day7Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk10Day1() != null && newTekCalendarVO1.getWk10Day1() != null && newTekCalendarVO.getWk10Day1().compareTo(newTekCalendarVO1.getWk10Day1()) == -1) {
                                    newTekCalendarVO.setWk10Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk10Day2() != null && newTekCalendarVO1.getWk10Day2() != null && newTekCalendarVO.getWk10Day2().compareTo(newTekCalendarVO1.getWk10Day2()) == -1) {
                                    newTekCalendarVO.setWk10Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk10Day3() != null && newTekCalendarVO1.getWk10Day3() != null && newTekCalendarVO.getWk10Day3().compareTo(newTekCalendarVO1.getWk10Day3()) == -1) {
                                    newTekCalendarVO.setWk10Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk10Day4() != null && newTekCalendarVO1.getWk10Day4() != null && newTekCalendarVO.getWk10Day4().compareTo(newTekCalendarVO1.getWk10Day4()) == -1) {
                                    newTekCalendarVO.setWk10Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk10Day5() != null && newTekCalendarVO1.getWk10Day5() != null && newTekCalendarVO.getWk10Day5().compareTo(newTekCalendarVO1.getWk10Day5()) == -1) {
                                    newTekCalendarVO.setWk10Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk10Day6() != null && newTekCalendarVO1.getWk10Day6() != null && newTekCalendarVO.getWk10Day6().compareTo(newTekCalendarVO1.getWk10Day6()) == -1) {
                                    newTekCalendarVO.setWk10Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk10Day7() != null && newTekCalendarVO1.getWk10Day7() != null && newTekCalendarVO.getWk10Day7().compareTo(newTekCalendarVO1.getWk10Day7()) == -1) {
                                    newTekCalendarVO.setWk10Day7Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk11Day1() != null && newTekCalendarVO1.getWk11Day1() != null && newTekCalendarVO.getWk11Day1().compareTo(newTekCalendarVO1.getWk11Day1()) == -1) {
                                    newTekCalendarVO.setWk11Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk11Day2() != null && newTekCalendarVO1.getWk11Day2() != null && newTekCalendarVO.getWk11Day2().compareTo(newTekCalendarVO1.getWk11Day2()) == -1) {
                                    newTekCalendarVO.setWk11Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk11Day3() != null && newTekCalendarVO1.getWk11Day3() != null && newTekCalendarVO.getWk11Day3().compareTo(newTekCalendarVO1.getWk11Day3()) == -1) {
                                    newTekCalendarVO.setWk11Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk11Day4() != null && newTekCalendarVO1.getWk11Day4() != null && newTekCalendarVO.getWk11Day4().compareTo(newTekCalendarVO1.getWk11Day4()) == -1) {
                                    newTekCalendarVO.setWk11Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk11Day5() != null && newTekCalendarVO1.getWk11Day5() != null && newTekCalendarVO.getWk11Day5().compareTo(newTekCalendarVO1.getWk11Day5()) == -1) {
                                    newTekCalendarVO.setWk11Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk11Day6() != null && newTekCalendarVO1.getWk11Day6() != null && newTekCalendarVO.getWk11Day6().compareTo(newTekCalendarVO1.getWk11Day6()) == -1) {
                                    newTekCalendarVO.setWk11Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk11Day7() != null && newTekCalendarVO1.getWk11Day7() != null && newTekCalendarVO.getWk11Day7().compareTo(newTekCalendarVO1.getWk11Day7()) == -1) {
                                    newTekCalendarVO.setWk11Day7Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk12Day1() != null && newTekCalendarVO1.getWk12Day1() != null && newTekCalendarVO.getWk12Day1().compareTo(newTekCalendarVO1.getWk12Day1()) == -1) {
                                    newTekCalendarVO.setWk12Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk12Day2() != null && newTekCalendarVO1.getWk12Day2() != null && newTekCalendarVO.getWk12Day2().compareTo(newTekCalendarVO1.getWk12Day2()) == -1) {
                                    newTekCalendarVO.setWk12Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk12Day3() != null && newTekCalendarVO1.getWk12Day3() != null && newTekCalendarVO.getWk12Day3().compareTo(newTekCalendarVO1.getWk12Day3()) == -1) {
                                    newTekCalendarVO.setWk12Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk12Day4() != null && newTekCalendarVO1.getWk12Day4() != null && newTekCalendarVO.getWk12Day4().compareTo(newTekCalendarVO1.getWk12Day4()) == -1) {
                                    newTekCalendarVO.setWk12Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk12Day5() != null && newTekCalendarVO1.getWk12Day5() != null && newTekCalendarVO.getWk12Day5().compareTo(newTekCalendarVO1.getWk12Day5()) == -1) {
                                    newTekCalendarVO.setWk12Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk12Day6() != null && newTekCalendarVO1.getWk12Day6() != null && newTekCalendarVO.getWk12Day6().compareTo(newTekCalendarVO1.getWk12Day6()) == -1) {
                                    newTekCalendarVO.setWk12Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk12Day7() != null && newTekCalendarVO1.getWk12Day7() != null && newTekCalendarVO.getWk12Day7().compareTo(newTekCalendarVO1.getWk12Day7()) == -1) {
                                    newTekCalendarVO.setWk12Day7Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk13Day1() != null && newTekCalendarVO1.getWk13Day1() != null && newTekCalendarVO.getWk13Day1().compareTo(newTekCalendarVO1.getWk13Day1()) == -1) {
                                    newTekCalendarVO.setWk13Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk13Day2() != null && newTekCalendarVO1.getWk13Day2() != null && newTekCalendarVO.getWk13Day2().compareTo(newTekCalendarVO1.getWk13Day2()) == -1) {
                                    newTekCalendarVO.setWk13Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk13Day3() != null && newTekCalendarVO1.getWk13Day3() != null && newTekCalendarVO.getWk13Day3().compareTo(newTekCalendarVO1.getWk13Day3()) == -1) {
                                    newTekCalendarVO.setWk13Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk13Day4() != null && newTekCalendarVO1.getWk13Day4() != null && newTekCalendarVO.getWk13Day4().compareTo(newTekCalendarVO1.getWk13Day4()) == -1) {
                                    newTekCalendarVO.setWk13Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk13Day5() != null && newTekCalendarVO1.getWk13Day5() != null && newTekCalendarVO.getWk13Day5().compareTo(newTekCalendarVO1.getWk13Day5()) == -1) {
                                    newTekCalendarVO.setWk13Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk13Day6() != null && newTekCalendarVO1.getWk13Day6() != null && newTekCalendarVO.getWk13Day6().compareTo(newTekCalendarVO1.getWk13Day6()) == -1) {
                                    newTekCalendarVO.setWk13Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk13Day7() != null && newTekCalendarVO1.getWk13Day7() != null && newTekCalendarVO.getWk13Day7().compareTo(newTekCalendarVO1.getWk13Day7()) == -1) {
                                    newTekCalendarVO.setWk13Day7Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk14Day1() != null && newTekCalendarVO1.getWk14Day1() != null && newTekCalendarVO.getWk14Day1().compareTo(newTekCalendarVO1.getWk14Day1()) == -1) {
                                    newTekCalendarVO.setWk14Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk14Day2() != null && newTekCalendarVO1.getWk14Day2() != null && newTekCalendarVO.getWk14Day2().compareTo(newTekCalendarVO1.getWk14Day2()) == -1) {
                                    newTekCalendarVO.setWk14Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk14Day3() != null && newTekCalendarVO1.getWk14Day3() != null && newTekCalendarVO.getWk14Day3().compareTo(newTekCalendarVO1.getWk14Day3()) == -1) {
                                    newTekCalendarVO.setWk14Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk14Day4() != null && newTekCalendarVO1.getWk14Day4() != null && newTekCalendarVO.getWk14Day4().compareTo(newTekCalendarVO1.getWk14Day4()) == -1) {
                                    newTekCalendarVO.setWk14Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk14Day5() != null && newTekCalendarVO1.getWk14Day5() != null && newTekCalendarVO.getWk14Day5().compareTo(newTekCalendarVO1.getWk14Day5()) == -1) {
                                    newTekCalendarVO.setWk14Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk14Day6() != null && newTekCalendarVO1.getWk14Day6() != null && newTekCalendarVO.getWk14Day6().compareTo(newTekCalendarVO1.getWk14Day6()) == -1) {
                                    newTekCalendarVO.setWk14Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk14Day7() != null && newTekCalendarVO1.getWk14Day7() != null && newTekCalendarVO.getWk14Day7().compareTo(newTekCalendarVO1.getWk14Day7()) == -1) {
                                    newTekCalendarVO.setWk14Day7Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk15Day1() != null && newTekCalendarVO1.getWk15Day1() != null && newTekCalendarVO.getWk15Day1().compareTo(newTekCalendarVO1.getWk15Day1()) == -1) {
                                    newTekCalendarVO.setWk15Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk15Day2() != null && newTekCalendarVO1.getWk15Day2() != null && newTekCalendarVO.getWk15Day2().compareTo(newTekCalendarVO1.getWk15Day2()) == -1) {
                                    newTekCalendarVO.setWk15Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk15Day3() != null && newTekCalendarVO1.getWk15Day3() != null && newTekCalendarVO.getWk15Day3().compareTo(newTekCalendarVO1.getWk15Day3()) == -1) {
                                    newTekCalendarVO.setWk15Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk15Day4() != null && newTekCalendarVO1.getWk15Day4() != null && newTekCalendarVO.getWk15Day4().compareTo(newTekCalendarVO1.getWk15Day4()) == -1) {
                                    newTekCalendarVO.setWk15Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk15Day5() != null && newTekCalendarVO1.getWk15Day5() != null && newTekCalendarVO.getWk15Day5().compareTo(newTekCalendarVO1.getWk15Day5()) == -1) {
                                    newTekCalendarVO.setWk15Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk15Day6() != null && newTekCalendarVO1.getWk15Day6() != null && newTekCalendarVO.getWk15Day6().compareTo(newTekCalendarVO1.getWk15Day6()) == -1) {
                                    newTekCalendarVO.setWk15Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk15Day7() != null && newTekCalendarVO1.getWk15Day7() != null && newTekCalendarVO.getWk15Day7().compareTo(newTekCalendarVO1.getWk15Day7()) == -1) {
                                    newTekCalendarVO.setWk15Day7Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk16Day1() != null && newTekCalendarVO1.getWk16Day1() != null && newTekCalendarVO.getWk16Day1().compareTo(newTekCalendarVO1.getWk16Day1()) == -1) {
                                    newTekCalendarVO.setWk16Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk16Day2() != null && newTekCalendarVO1.getWk16Day2() != null && newTekCalendarVO.getWk16Day2().compareTo(newTekCalendarVO1.getWk16Day2()) == -1) {
                                    newTekCalendarVO.setWk16Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk16Day3() != null && newTekCalendarVO1.getWk16Day3() != null && newTekCalendarVO.getWk16Day3().compareTo(newTekCalendarVO1.getWk16Day3()) == -1) {
                                    newTekCalendarVO.setWk16Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk16Day4() != null && newTekCalendarVO1.getWk16Day4() != null && newTekCalendarVO.getWk16Day4().compareTo(newTekCalendarVO1.getWk16Day4()) == -1) {
                                    newTekCalendarVO.setWk16Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk16Day5() != null && newTekCalendarVO1.getWk16Day5() != null && newTekCalendarVO.getWk16Day5().compareTo(newTekCalendarVO1.getWk16Day5()) == -1) {
                                    newTekCalendarVO.setWk16Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk16Day6() != null && newTekCalendarVO1.getWk16Day6() != null && newTekCalendarVO.getWk16Day6().compareTo(newTekCalendarVO1.getWk16Day6()) == -1) {
                                    newTekCalendarVO.setWk16Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk16Day7() != null && newTekCalendarVO1.getWk16Day7() != null && newTekCalendarVO.getWk16Day7().compareTo(newTekCalendarVO1.getWk16Day7()) == -1) {
                                    newTekCalendarVO.setWk16Day7Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk17Day1() != null && newTekCalendarVO1.getWk17Day1() != null && newTekCalendarVO.getWk17Day1().compareTo(newTekCalendarVO1.getWk17Day1()) == -1) {
                                    newTekCalendarVO.setWk17Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk17Day2() != null && newTekCalendarVO1.getWk17Day2() != null && newTekCalendarVO.getWk17Day2().compareTo(newTekCalendarVO1.getWk17Day2()) == -1) {
                                    newTekCalendarVO.setWk17Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk17Day3() != null && newTekCalendarVO1.getWk17Day3() != null && newTekCalendarVO.getWk17Day3().compareTo(newTekCalendarVO1.getWk17Day3()) == -1) {
                                    newTekCalendarVO.setWk17Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk17Day4() != null && newTekCalendarVO1.getWk17Day4() != null && newTekCalendarVO.getWk17Day4().compareTo(newTekCalendarVO1.getWk17Day4()) == -1) {
                                    newTekCalendarVO.setWk17Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk17Day5() != null && newTekCalendarVO1.getWk17Day5() != null && newTekCalendarVO.getWk17Day5().compareTo(newTekCalendarVO1.getWk17Day5()) == -1) {
                                    newTekCalendarVO.setWk17Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk17Day6() != null && newTekCalendarVO1.getWk17Day6() != null && newTekCalendarVO.getWk17Day6().compareTo(newTekCalendarVO1.getWk17Day6()) == -1) {
                                    newTekCalendarVO.setWk17Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk17Day7() != null && newTekCalendarVO1.getWk17Day7() != null && newTekCalendarVO.getWk17Day7().compareTo(newTekCalendarVO1.getWk17Day7()) == -1) {
                                    newTekCalendarVO.setWk17Day7Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk18Day1() != null && newTekCalendarVO1.getWk18Day1() != null && newTekCalendarVO.getWk18Day1().compareTo(newTekCalendarVO1.getWk18Day1()) == -1) {
                                    newTekCalendarVO.setWk18Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk18Day2() != null && newTekCalendarVO1.getWk18Day2() != null && newTekCalendarVO.getWk18Day2().compareTo(newTekCalendarVO1.getWk18Day2()) == -1) {
                                    newTekCalendarVO.setWk18Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk18Day3() != null && newTekCalendarVO1.getWk18Day3() != null && newTekCalendarVO.getWk18Day3().compareTo(newTekCalendarVO1.getWk18Day3()) == -1) {
                                    newTekCalendarVO.setWk18Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk18Day4() != null && newTekCalendarVO1.getWk18Day4() != null && newTekCalendarVO.getWk18Day4().compareTo(newTekCalendarVO1.getWk18Day4()) == -1) {
                                    newTekCalendarVO.setWk18Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk18Day5() != null && newTekCalendarVO1.getWk18Day5() != null && newTekCalendarVO.getWk18Day5().compareTo(newTekCalendarVO1.getWk18Day5()) == -1) {
                                    newTekCalendarVO.setWk18Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk18Day6() != null && newTekCalendarVO1.getWk18Day6() != null && newTekCalendarVO.getWk18Day6().compareTo(newTekCalendarVO1.getWk18Day6()) == -1) {
                                    newTekCalendarVO.setWk18Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk18Day7() != null && newTekCalendarVO1.getWk18Day7() != null && newTekCalendarVO.getWk18Day7().compareTo(newTekCalendarVO1.getWk18Day7()) == -1) {
                                    newTekCalendarVO.setWk18Day7Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk19Day1() != null && newTekCalendarVO1.getWk19Day1() != null && newTekCalendarVO.getWk19Day1().compareTo(newTekCalendarVO1.getWk19Day1()) == -1) {
                                    newTekCalendarVO.setWk19Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk19Day2() != null && newTekCalendarVO1.getWk19Day2() != null && newTekCalendarVO.getWk19Day2().compareTo(newTekCalendarVO1.getWk19Day2()) == -1) {
                                    newTekCalendarVO.setWk19Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk19Day3() != null && newTekCalendarVO1.getWk19Day3() != null && newTekCalendarVO.getWk19Day3().compareTo(newTekCalendarVO1.getWk19Day3()) == -1) {
                                    newTekCalendarVO.setWk19Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk19Day4() != null && newTekCalendarVO1.getWk19Day4() != null && newTekCalendarVO.getWk19Day4().compareTo(newTekCalendarVO1.getWk19Day4()) == -1) {
                                    newTekCalendarVO.setWk19Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk19Day5() != null && newTekCalendarVO1.getWk19Day5() != null && newTekCalendarVO.getWk19Day5().compareTo(newTekCalendarVO1.getWk19Day5()) == -1) {
                                    newTekCalendarVO.setWk19Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk19Day6() != null && newTekCalendarVO1.getWk19Day6() != null && newTekCalendarVO.getWk19Day6().compareTo(newTekCalendarVO1.getWk19Day6()) == -1) {
                                    newTekCalendarVO.setWk19Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk19Day7() != null && newTekCalendarVO1.getWk19Day7() != null && newTekCalendarVO.getWk19Day7().compareTo(newTekCalendarVO1.getWk19Day7()) == -1) {
                                    newTekCalendarVO.setWk19Day7Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk20Day1() != null && newTekCalendarVO1.getWk20Day1() != null && newTekCalendarVO.getWk20Day1().compareTo(newTekCalendarVO1.getWk20Day1()) == -1) {
                                    newTekCalendarVO.setWk20Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk20Day2() != null && newTekCalendarVO1.getWk20Day2() != null && newTekCalendarVO.getWk20Day2().compareTo(newTekCalendarVO1.getWk20Day2()) == -1) {
                                    newTekCalendarVO.setWk20Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk20Day3() != null && newTekCalendarVO1.getWk20Day3() != null && newTekCalendarVO.getWk20Day3().compareTo(newTekCalendarVO1.getWk20Day3()) == -1) {
                                    newTekCalendarVO.setWk20Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk20Day4() != null && newTekCalendarVO1.getWk20Day4() != null && newTekCalendarVO.getWk20Day4().compareTo(newTekCalendarVO1.getWk20Day4()) == -1) {
                                    newTekCalendarVO.setWk20Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk20Day5() != null && newTekCalendarVO1.getWk20Day5() != null && newTekCalendarVO.getWk20Day5().compareTo(newTekCalendarVO1.getWk20Day5()) == -1) {
                                    newTekCalendarVO.setWk20Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk20Day6() != null && newTekCalendarVO1.getWk20Day6() != null && newTekCalendarVO.getWk20Day6().compareTo(newTekCalendarVO1.getWk20Day6()) == -1) {
                                    newTekCalendarVO.setWk20Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk20Day7() != null && newTekCalendarVO1.getWk20Day7() != null && newTekCalendarVO.getWk20Day7().compareTo(newTekCalendarVO1.getWk20Day7()) == -1) {
                                    newTekCalendarVO.setWk20Day7Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk21Day1() != null && newTekCalendarVO1.getWk21Day1() != null && newTekCalendarVO.getWk21Day1().compareTo(newTekCalendarVO1.getWk21Day1()) == -1) {
                                    newTekCalendarVO.setWk21Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk21Day2() != null && newTekCalendarVO1.getWk21Day2() != null && newTekCalendarVO.getWk21Day2().compareTo(newTekCalendarVO1.getWk21Day2()) == -1) {
                                    newTekCalendarVO.setWk21Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk21Day3() != null && newTekCalendarVO1.getWk21Day3() != null && newTekCalendarVO.getWk21Day3().compareTo(newTekCalendarVO1.getWk21Day3()) == -1) {
                                    newTekCalendarVO.setWk21Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk21Day4() != null && newTekCalendarVO1.getWk21Day4() != null && newTekCalendarVO.getWk21Day4().compareTo(newTekCalendarVO1.getWk21Day4()) == -1) {
                                    newTekCalendarVO.setWk21Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk21Day5() != null && newTekCalendarVO1.getWk21Day5() != null && newTekCalendarVO.getWk21Day5().compareTo(newTekCalendarVO1.getWk21Day5()) == -1) {
                                    newTekCalendarVO.setWk21Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk21Day6() != null && newTekCalendarVO1.getWk21Day6() != null && newTekCalendarVO.getWk21Day6().compareTo(newTekCalendarVO1.getWk21Day6()) == -1) {
                                    newTekCalendarVO.setWk21Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk21Day7() != null && newTekCalendarVO1.getWk21Day7() != null && newTekCalendarVO.getWk21Day7().compareTo(newTekCalendarVO1.getWk21Day7()) == -1) {
                                    newTekCalendarVO.setWk21Day7Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk22Day1() != null && newTekCalendarVO1.getWk22Day1() != null && newTekCalendarVO.getWk22Day1().compareTo(newTekCalendarVO1.getWk22Day1()) == -1) {
                                    newTekCalendarVO.setWk22Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk22Day2() != null && newTekCalendarVO1.getWk22Day2() != null && newTekCalendarVO.getWk22Day2().compareTo(newTekCalendarVO1.getWk22Day2()) == -1) {
                                    newTekCalendarVO.setWk22Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk22Day3() != null && newTekCalendarVO1.getWk22Day3() != null && newTekCalendarVO.getWk22Day3().compareTo(newTekCalendarVO1.getWk22Day3()) == -1) {
                                    newTekCalendarVO.setWk22Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk22Day4() != null && newTekCalendarVO1.getWk22Day4() != null && newTekCalendarVO.getWk22Day4().compareTo(newTekCalendarVO1.getWk22Day4()) == -1) {
                                    newTekCalendarVO.setWk22Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk22Day5() != null && newTekCalendarVO1.getWk22Day5() != null && newTekCalendarVO.getWk22Day5().compareTo(newTekCalendarVO1.getWk22Day5()) == -1) {
                                    newTekCalendarVO.setWk22Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk22Day6() != null && newTekCalendarVO1.getWk22Day6() != null && newTekCalendarVO.getWk22Day6().compareTo(newTekCalendarVO1.getWk22Day6()) == -1) {
                                    newTekCalendarVO.setWk22Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk22Day7() != null && newTekCalendarVO1.getWk22Day7() != null && newTekCalendarVO.getWk22Day7().compareTo(newTekCalendarVO1.getWk22Day7()) == -1) {
                                    newTekCalendarVO.setWk22Day7Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk23Day1() != null && newTekCalendarVO1.getWk23Day1() != null && newTekCalendarVO.getWk23Day1().compareTo(newTekCalendarVO1.getWk23Day1()) == -1) {
                                    newTekCalendarVO.setWk23Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk23Day2() != null && newTekCalendarVO1.getWk23Day2() != null && newTekCalendarVO.getWk23Day2().compareTo(newTekCalendarVO1.getWk23Day2()) == -1) {
                                    newTekCalendarVO.setWk23Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk23Day3() != null && newTekCalendarVO1.getWk23Day3() != null && newTekCalendarVO.getWk23Day3().compareTo(newTekCalendarVO1.getWk23Day3()) == -1) {
                                    newTekCalendarVO.setWk23Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk23Day4() != null && newTekCalendarVO1.getWk23Day4() != null && newTekCalendarVO.getWk23Day4().compareTo(newTekCalendarVO1.getWk23Day4()) == -1) {
                                    newTekCalendarVO.setWk23Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk23Day5() != null && newTekCalendarVO1.getWk23Day5() != null && newTekCalendarVO.getWk23Day5().compareTo(newTekCalendarVO1.getWk23Day5()) == -1) {
                                    newTekCalendarVO.setWk23Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk23Day6() != null && newTekCalendarVO1.getWk23Day6() != null && newTekCalendarVO.getWk23Day6().compareTo(newTekCalendarVO1.getWk23Day6()) == -1) {
                                    newTekCalendarVO.setWk23Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk23Day7() != null && newTekCalendarVO1.getWk23Day7() != null && newTekCalendarVO.getWk23Day7().compareTo(newTekCalendarVO1.getWk23Day7()) == -1) {
                                    newTekCalendarVO.setWk23Day7Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk24Day1() != null && newTekCalendarVO1.getWk24Day1() != null && newTekCalendarVO.getWk24Day1().compareTo(newTekCalendarVO1.getWk24Day1()) == -1) {
                                    newTekCalendarVO.setWk24Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk24Day2() != null && newTekCalendarVO1.getWk24Day2() != null && newTekCalendarVO.getWk24Day2().compareTo(newTekCalendarVO1.getWk24Day2()) == -1) {
                                    newTekCalendarVO.setWk24Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk24Day3() != null && newTekCalendarVO1.getWk24Day3() != null && newTekCalendarVO.getWk24Day3().compareTo(newTekCalendarVO1.getWk24Day3()) == -1) {
                                    newTekCalendarVO.setWk24Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk24Day4() != null && newTekCalendarVO1.getWk24Day4() != null && newTekCalendarVO.getWk24Day4().compareTo(newTekCalendarVO1.getWk24Day4()) == -1) {
                                    newTekCalendarVO.setWk24Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk24Day5() != null && newTekCalendarVO1.getWk24Day5() != null && newTekCalendarVO.getWk24Day5().compareTo(newTekCalendarVO1.getWk24Day5()) == -1) {
                                    newTekCalendarVO.setWk24Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk24Day6() != null && newTekCalendarVO1.getWk24Day6() != null && newTekCalendarVO.getWk24Day6().compareTo(newTekCalendarVO1.getWk24Day6()) == -1) {
                                    newTekCalendarVO.setWk24Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk24Day7() != null && newTekCalendarVO1.getWk24Day7() != null && newTekCalendarVO.getWk24Day7().compareTo(newTekCalendarVO1.getWk24Day7()) == -1) {
                                    newTekCalendarVO.setWk24Day7Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk25Day1() != null && newTekCalendarVO1.getWk25Day1() != null && newTekCalendarVO.getWk25Day1().compareTo(newTekCalendarVO1.getWk25Day1()) == -1) {
                                    newTekCalendarVO.setWk25Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk25Day2() != null && newTekCalendarVO1.getWk25Day2() != null && newTekCalendarVO.getWk25Day2().compareTo(newTekCalendarVO1.getWk25Day2()) == -1) {
                                    newTekCalendarVO.setWk25Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk25Day3() != null && newTekCalendarVO1.getWk25Day3() != null && newTekCalendarVO.getWk25Day3().compareTo(newTekCalendarVO1.getWk25Day3()) == -1) {
                                    newTekCalendarVO.setWk25Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk25Day4() != null && newTekCalendarVO1.getWk25Day4() != null && newTekCalendarVO.getWk25Day4().compareTo(newTekCalendarVO1.getWk25Day4()) == -1) {
                                    newTekCalendarVO.setWk25Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk25Day5() != null && newTekCalendarVO1.getWk25Day5() != null && newTekCalendarVO.getWk25Day5().compareTo(newTekCalendarVO1.getWk25Day5()) == -1) {
                                    newTekCalendarVO.setWk25Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk25Day6() != null && newTekCalendarVO1.getWk25Day6() != null && newTekCalendarVO.getWk25Day6().compareTo(newTekCalendarVO1.getWk25Day6()) == -1) {
                                    newTekCalendarVO.setWk25Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk25Day7() != null && newTekCalendarVO1.getWk25Day7() != null && newTekCalendarVO.getWk25Day7().compareTo(newTekCalendarVO1.getWk25Day7()) == -1) {
                                    newTekCalendarVO.setWk25Day7Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk26Day1() != null && newTekCalendarVO1.getWk26Day1() != null && newTekCalendarVO.getWk26Day1().compareTo(newTekCalendarVO1.getWk26Day1()) == -1) {
                                    newTekCalendarVO.setWk26Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk26Day2() != null && newTekCalendarVO1.getWk26Day2() != null && newTekCalendarVO.getWk26Day2().compareTo(newTekCalendarVO1.getWk26Day2()) == -1) {
                                    newTekCalendarVO.setWk26Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk26Day3() != null && newTekCalendarVO1.getWk26Day3() != null && newTekCalendarVO.getWk26Day3().compareTo(newTekCalendarVO1.getWk26Day3()) == -1) {
                                    newTekCalendarVO.setWk26Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk26Day4() != null && newTekCalendarVO1.getWk26Day4() != null && newTekCalendarVO.getWk26Day4().compareTo(newTekCalendarVO1.getWk26Day4()) == -1) {
                                    newTekCalendarVO.setWk26Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk26Day5() != null && newTekCalendarVO1.getWk26Day5() != null && newTekCalendarVO.getWk26Day5().compareTo(newTekCalendarVO1.getWk26Day5()) == -1) {
                                    newTekCalendarVO.setWk26Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk26Day6() != null && newTekCalendarVO1.getWk26Day6() != null && newTekCalendarVO.getWk26Day6().compareTo(newTekCalendarVO1.getWk26Day6()) == -1) {
                                    newTekCalendarVO.setWk26Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk26Day7() != null && newTekCalendarVO1.getWk26Day7() != null && newTekCalendarVO.getWk26Day7().compareTo(newTekCalendarVO1.getWk26Day7()) == -1) {
                                    newTekCalendarVO.setWk26Day7Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk27Day1() != null && newTekCalendarVO1.getWk27Day1() != null && newTekCalendarVO.getWk27Day1().compareTo(newTekCalendarVO1.getWk27Day1()) == -1) {
                                    newTekCalendarVO.setWk27Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk27Day2() != null && newTekCalendarVO1.getWk27Day2() != null && newTekCalendarVO.getWk27Day2().compareTo(newTekCalendarVO1.getWk27Day2()) == -1) {
                                    newTekCalendarVO.setWk27Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk27Day3() != null && newTekCalendarVO1.getWk27Day3() != null && newTekCalendarVO.getWk27Day3().compareTo(newTekCalendarVO1.getWk27Day3()) == -1) {
                                    newTekCalendarVO.setWk27Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk27Day4() != null && newTekCalendarVO1.getWk27Day4() != null && newTekCalendarVO.getWk27Day4().compareTo(newTekCalendarVO1.getWk27Day4()) == -1) {
                                    newTekCalendarVO.setWk27Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk27Day5() != null && newTekCalendarVO1.getWk27Day5() != null && newTekCalendarVO.getWk27Day5().compareTo(newTekCalendarVO1.getWk27Day5()) == -1) {
                                    newTekCalendarVO.setWk27Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk27Day6() != null && newTekCalendarVO1.getWk27Day6() != null && newTekCalendarVO.getWk27Day6().compareTo(newTekCalendarVO1.getWk27Day6()) == -1) {
                                    newTekCalendarVO.setWk27Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk27Day7() != null && newTekCalendarVO1.getWk27Day7() != null && newTekCalendarVO.getWk27Day7().compareTo(newTekCalendarVO1.getWk27Day7()) == -1) {
                                    newTekCalendarVO.setWk27Day7Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk28Day1() != null && newTekCalendarVO1.getWk28Day1() != null && newTekCalendarVO.getWk28Day1().compareTo(newTekCalendarVO1.getWk28Day1()) == -1) {
                                    newTekCalendarVO.setWk28Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk28Day2() != null && newTekCalendarVO1.getWk28Day2() != null && newTekCalendarVO.getWk28Day2().compareTo(newTekCalendarVO1.getWk28Day2()) == -1) {
                                    newTekCalendarVO.setWk28Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk28Day3() != null && newTekCalendarVO1.getWk28Day3() != null && newTekCalendarVO.getWk28Day3().compareTo(newTekCalendarVO1.getWk28Day3()) == -1) {
                                    newTekCalendarVO.setWk28Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk28Day4() != null && newTekCalendarVO1.getWk28Day4() != null && newTekCalendarVO.getWk28Day4().compareTo(newTekCalendarVO1.getWk28Day4()) == -1) {
                                    newTekCalendarVO.setWk28Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk28Day5() != null && newTekCalendarVO1.getWk28Day5() != null && newTekCalendarVO.getWk28Day5().compareTo(newTekCalendarVO1.getWk28Day5()) == -1) {
                                    newTekCalendarVO.setWk28Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk28Day6() != null && newTekCalendarVO1.getWk28Day6() != null && newTekCalendarVO.getWk28Day6().compareTo(newTekCalendarVO1.getWk28Day6()) == -1) {
                                    newTekCalendarVO.setWk28Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk28Day7() != null && newTekCalendarVO1.getWk28Day7() != null && newTekCalendarVO.getWk28Day7().compareTo(newTekCalendarVO1.getWk28Day7()) == -1) {
                                    newTekCalendarVO.setWk28Day7Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk29Day1() != null && newTekCalendarVO1.getWk29Day1() != null && newTekCalendarVO.getWk29Day1().compareTo(newTekCalendarVO1.getWk29Day1()) == -1) {
                                    newTekCalendarVO.setWk29Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk29Day2() != null && newTekCalendarVO1.getWk29Day2() != null && newTekCalendarVO.getWk29Day2().compareTo(newTekCalendarVO1.getWk29Day2()) == -1) {
                                    newTekCalendarVO.setWk29Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk29Day3() != null && newTekCalendarVO1.getWk29Day3() != null && newTekCalendarVO.getWk29Day3().compareTo(newTekCalendarVO1.getWk29Day3()) == -1) {
                                    newTekCalendarVO.setWk29Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk29Day4() != null && newTekCalendarVO1.getWk29Day4() != null && newTekCalendarVO.getWk29Day4().compareTo(newTekCalendarVO1.getWk29Day4()) == -1) {
                                    newTekCalendarVO.setWk29Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk29Day5() != null && newTekCalendarVO1.getWk29Day5() != null && newTekCalendarVO.getWk29Day5().compareTo(newTekCalendarVO1.getWk29Day5()) == -1) {
                                    newTekCalendarVO.setWk29Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk29Day6() != null && newTekCalendarVO1.getWk29Day6() != null && newTekCalendarVO.getWk29Day6().compareTo(newTekCalendarVO1.getWk29Day6()) == -1) {
                                    newTekCalendarVO.setWk29Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk29Day7() != null && newTekCalendarVO1.getWk29Day7() != null && newTekCalendarVO.getWk29Day7().compareTo(newTekCalendarVO1.getWk29Day7()) == -1) {
                                    newTekCalendarVO.setWk29Day7Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk30Day1() != null && newTekCalendarVO1.getWk30Day1() != null && newTekCalendarVO.getWk30Day1().compareTo(newTekCalendarVO1.getWk30Day1()) == -1) {
                                    newTekCalendarVO.setWk30Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk30Day2() != null && newTekCalendarVO1.getWk30Day2() != null && newTekCalendarVO.getWk30Day2().compareTo(newTekCalendarVO1.getWk30Day2()) == -1) {
                                    newTekCalendarVO.setWk30Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk30Day3() != null && newTekCalendarVO1.getWk30Day3() != null && newTekCalendarVO.getWk30Day3().compareTo(newTekCalendarVO1.getWk30Day3()) == -1) {
                                    newTekCalendarVO.setWk30Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk30Day4() != null && newTekCalendarVO1.getWk30Day4() != null && newTekCalendarVO.getWk30Day4().compareTo(newTekCalendarVO1.getWk30Day4()) == -1) {
                                    newTekCalendarVO.setWk30Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk30Day5() != null && newTekCalendarVO1.getWk30Day5() != null && newTekCalendarVO.getWk30Day5().compareTo(newTekCalendarVO1.getWk30Day5()) == -1) {
                                    newTekCalendarVO.setWk30Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk30Day6() != null && newTekCalendarVO1.getWk30Day6() != null && newTekCalendarVO.getWk30Day6().compareTo(newTekCalendarVO1.getWk30Day6()) == -1) {
                                    newTekCalendarVO.setWk30Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk30Day7() != null && newTekCalendarVO1.getWk30Day7() != null && newTekCalendarVO.getWk30Day7().compareTo(newTekCalendarVO1.getWk30Day7()) == -1) {
                                    newTekCalendarVO.setWk30Day7Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk31Day1() != null && newTekCalendarVO1.getWk31Day1() != null && newTekCalendarVO.getWk31Day1().compareTo(newTekCalendarVO1.getWk31Day1()) == -1) {
                                    newTekCalendarVO.setWk31Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk31Day2() != null && newTekCalendarVO1.getWk31Day2() != null && newTekCalendarVO.getWk31Day2().compareTo(newTekCalendarVO1.getWk31Day2()) == -1) {
                                    newTekCalendarVO.setWk31Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk31Day3() != null && newTekCalendarVO1.getWk31Day3() != null && newTekCalendarVO.getWk31Day3().compareTo(newTekCalendarVO1.getWk31Day3()) == -1) {
                                    newTekCalendarVO.setWk31Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk31Day4() != null && newTekCalendarVO1.getWk31Day4() != null && newTekCalendarVO.getWk31Day4().compareTo(newTekCalendarVO1.getWk31Day4()) == -1) {
                                    newTekCalendarVO.setWk31Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk31Day5() != null && newTekCalendarVO1.getWk31Day5() != null && newTekCalendarVO.getWk31Day5().compareTo(newTekCalendarVO1.getWk31Day5()) == -1) {
                                    newTekCalendarVO.setWk31Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk31Day6() != null && newTekCalendarVO1.getWk31Day6() != null && newTekCalendarVO.getWk31Day6().compareTo(newTekCalendarVO1.getWk31Day6()) == -1) {
                                    newTekCalendarVO.setWk31Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk31Day7() != null && newTekCalendarVO1.getWk31Day7() != null && newTekCalendarVO.getWk31Day7().compareTo(newTekCalendarVO1.getWk31Day7()) == -1) {
                                    newTekCalendarVO.setWk31Day7Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk32Day1() != null && newTekCalendarVO1.getWk32Day1() != null && newTekCalendarVO.getWk32Day1().compareTo(newTekCalendarVO1.getWk32Day1()) == -1) {
                                    newTekCalendarVO.setWk32Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk32Day2() != null && newTekCalendarVO1.getWk32Day2() != null && newTekCalendarVO.getWk32Day2().compareTo(newTekCalendarVO1.getWk32Day2()) == -1) {
                                    newTekCalendarVO.setWk32Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk32Day3() != null && newTekCalendarVO1.getWk32Day3() != null && newTekCalendarVO.getWk32Day3().compareTo(newTekCalendarVO1.getWk32Day3()) == -1) {
                                    newTekCalendarVO.setWk32Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk32Day4() != null && newTekCalendarVO1.getWk32Day4() != null && newTekCalendarVO.getWk32Day4().compareTo(newTekCalendarVO1.getWk32Day4()) == -1) {
                                    newTekCalendarVO.setWk32Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk32Day5() != null && newTekCalendarVO1.getWk32Day5() != null && newTekCalendarVO.getWk32Day5().compareTo(newTekCalendarVO1.getWk32Day5()) == -1) {
                                    newTekCalendarVO.setWk32Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk32Day6() != null && newTekCalendarVO1.getWk32Day6() != null && newTekCalendarVO.getWk32Day6().compareTo(newTekCalendarVO1.getWk32Day6()) == -1) {
                                    newTekCalendarVO.setWk32Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk32Day7() != null && newTekCalendarVO1.getWk32Day7() != null && newTekCalendarVO.getWk32Day7().compareTo(newTekCalendarVO1.getWk32Day7()) == -1) {
                                    newTekCalendarVO.setWk32Day7Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk33Day1() != null && newTekCalendarVO1.getWk33Day1() != null && newTekCalendarVO.getWk33Day1().compareTo(newTekCalendarVO1.getWk33Day1()) == -1) {
                                    newTekCalendarVO.setWk33Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk33Day2() != null && newTekCalendarVO1.getWk33Day2() != null && newTekCalendarVO.getWk33Day2().compareTo(newTekCalendarVO1.getWk33Day2()) == -1) {
                                    newTekCalendarVO.setWk33Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk33Day3() != null && newTekCalendarVO1.getWk33Day3() != null && newTekCalendarVO.getWk33Day3().compareTo(newTekCalendarVO1.getWk33Day3()) == -1) {
                                    newTekCalendarVO.setWk33Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk33Day4() != null && newTekCalendarVO1.getWk33Day4() != null && newTekCalendarVO.getWk33Day4().compareTo(newTekCalendarVO1.getWk33Day4()) == -1) {
                                    newTekCalendarVO.setWk33Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk33Day5() != null && newTekCalendarVO1.getWk33Day5() != null && newTekCalendarVO.getWk33Day5().compareTo(newTekCalendarVO1.getWk33Day5()) == -1) {
                                    newTekCalendarVO.setWk33Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk33Day6() != null && newTekCalendarVO1.getWk33Day6() != null && newTekCalendarVO.getWk33Day6().compareTo(newTekCalendarVO1.getWk33Day6()) == -1) {
                                    newTekCalendarVO.setWk33Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk33Day7() != null && newTekCalendarVO1.getWk33Day7() != null && newTekCalendarVO.getWk33Day7().compareTo(newTekCalendarVO1.getWk33Day7()) == -1) {
                                    newTekCalendarVO.setWk33Day7Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk34Day1() != null && newTekCalendarVO1.getWk34Day1() != null && newTekCalendarVO.getWk34Day1().compareTo(newTekCalendarVO1.getWk34Day1()) == -1) {
                                    newTekCalendarVO.setWk34Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk34Day2() != null && newTekCalendarVO1.getWk34Day2() != null && newTekCalendarVO.getWk34Day2().compareTo(newTekCalendarVO1.getWk34Day2()) == -1) {
                                    newTekCalendarVO.setWk34Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk34Day3() != null && newTekCalendarVO1.getWk34Day3() != null && newTekCalendarVO.getWk34Day3().compareTo(newTekCalendarVO1.getWk34Day3()) == -1) {
                                    newTekCalendarVO.setWk34Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk34Day4() != null && newTekCalendarVO1.getWk34Day4() != null && newTekCalendarVO.getWk34Day4().compareTo(newTekCalendarVO1.getWk34Day4()) == -1) {
                                    newTekCalendarVO.setWk34Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk34Day5() != null && newTekCalendarVO1.getWk34Day5() != null && newTekCalendarVO.getWk34Day5().compareTo(newTekCalendarVO1.getWk34Day5()) == -1) {
                                    newTekCalendarVO.setWk34Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk34Day6() != null && newTekCalendarVO1.getWk34Day6() != null && newTekCalendarVO.getWk34Day6().compareTo(newTekCalendarVO1.getWk34Day6()) == -1) {
                                    newTekCalendarVO.setWk34Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk34Day7() != null && newTekCalendarVO1.getWk34Day7() != null && newTekCalendarVO.getWk34Day7().compareTo(newTekCalendarVO1.getWk34Day7()) == -1) {
                                    newTekCalendarVO.setWk34Day7Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk35Day1() != null && newTekCalendarVO1.getWk35Day1() != null && newTekCalendarVO.getWk35Day1().compareTo(newTekCalendarVO1.getWk35Day1()) == -1) {
                                    newTekCalendarVO.setWk35Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk35Day2() != null && newTekCalendarVO1.getWk35Day2() != null && newTekCalendarVO.getWk35Day2().compareTo(newTekCalendarVO1.getWk35Day2()) == -1) {
                                    newTekCalendarVO.setWk35Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk35Day3() != null && newTekCalendarVO1.getWk35Day3() != null && newTekCalendarVO.getWk35Day3().compareTo(newTekCalendarVO1.getWk35Day3()) == -1) {
                                    newTekCalendarVO.setWk35Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk35Day4() != null && newTekCalendarVO1.getWk35Day4() != null && newTekCalendarVO.getWk35Day4().compareTo(newTekCalendarVO1.getWk35Day4()) == -1) {
                                    newTekCalendarVO.setWk35Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk35Day5() != null && newTekCalendarVO1.getWk35Day5() != null && newTekCalendarVO.getWk35Day5().compareTo(newTekCalendarVO1.getWk35Day5()) == -1) {
                                    newTekCalendarVO.setWk35Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk35Day6() != null && newTekCalendarVO1.getWk35Day6() != null && newTekCalendarVO.getWk35Day6().compareTo(newTekCalendarVO1.getWk35Day6()) == -1) {
                                    newTekCalendarVO.setWk35Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk35Day7() != null && newTekCalendarVO1.getWk35Day7() != null && newTekCalendarVO.getWk35Day7().compareTo(newTekCalendarVO1.getWk35Day7()) == -1) {
                                    newTekCalendarVO.setWk35Day7Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk36Day1() != null && newTekCalendarVO1.getWk36Day1() != null && newTekCalendarVO.getWk36Day1().compareTo(newTekCalendarVO1.getWk36Day1()) == -1) {
                                    newTekCalendarVO.setWk36Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk36Day2() != null && newTekCalendarVO1.getWk36Day2() != null && newTekCalendarVO.getWk36Day2().compareTo(newTekCalendarVO1.getWk36Day2()) == -1) {
                                    newTekCalendarVO.setWk36Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk36Day3() != null && newTekCalendarVO1.getWk36Day3() != null && newTekCalendarVO.getWk36Day3().compareTo(newTekCalendarVO1.getWk36Day3()) == -1) {
                                    newTekCalendarVO.setWk36Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk36Day4() != null && newTekCalendarVO1.getWk36Day4() != null && newTekCalendarVO.getWk36Day4().compareTo(newTekCalendarVO1.getWk36Day4()) == -1) {
                                    newTekCalendarVO.setWk36Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk36Day5() != null && newTekCalendarVO1.getWk36Day5() != null && newTekCalendarVO.getWk36Day5().compareTo(newTekCalendarVO1.getWk36Day5()) == -1) {
                                    newTekCalendarVO.setWk36Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk36Day6() != null && newTekCalendarVO1.getWk36Day6() != null && newTekCalendarVO.getWk36Day6().compareTo(newTekCalendarVO1.getWk36Day6()) == -1) {
                                    newTekCalendarVO.setWk36Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk36Day7() != null && newTekCalendarVO1.getWk36Day7() != null && newTekCalendarVO.getWk36Day7().compareTo(newTekCalendarVO1.getWk36Day7()) == -1) {
                                    newTekCalendarVO.setWk36Day7Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk37Day1() != null && newTekCalendarVO1.getWk37Day1() != null && newTekCalendarVO.getWk37Day1().compareTo(newTekCalendarVO1.getWk37Day1()) == -1) {
                                    newTekCalendarVO.setWk37Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk37Day2() != null && newTekCalendarVO1.getWk37Day2() != null && newTekCalendarVO.getWk37Day2().compareTo(newTekCalendarVO1.getWk37Day2()) == -1) {
                                    newTekCalendarVO.setWk37Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk37Day3() != null && newTekCalendarVO1.getWk37Day3() != null && newTekCalendarVO.getWk37Day3().compareTo(newTekCalendarVO1.getWk37Day3()) == -1) {
                                    newTekCalendarVO.setWk37Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk37Day4() != null && newTekCalendarVO1.getWk37Day4() != null && newTekCalendarVO.getWk37Day4().compareTo(newTekCalendarVO1.getWk37Day4()) == -1) {
                                    newTekCalendarVO.setWk37Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk37Day5() != null && newTekCalendarVO1.getWk37Day5() != null && newTekCalendarVO.getWk37Day5().compareTo(newTekCalendarVO1.getWk37Day5()) == -1) {
                                    newTekCalendarVO.setWk37Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk37Day6() != null && newTekCalendarVO1.getWk37Day6() != null && newTekCalendarVO.getWk37Day6().compareTo(newTekCalendarVO1.getWk37Day6()) == -1) {
                                    newTekCalendarVO.setWk37Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk37Day7() != null && newTekCalendarVO1.getWk37Day7() != null && newTekCalendarVO.getWk37Day7().compareTo(newTekCalendarVO1.getWk37Day7()) == -1) {
                                    newTekCalendarVO.setWk37Day7Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk38Day1() != null && newTekCalendarVO1.getWk38Day1() != null && newTekCalendarVO.getWk38Day1().compareTo(newTekCalendarVO1.getWk38Day1()) == -1) {
                                    newTekCalendarVO.setWk38Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk38Day2() != null && newTekCalendarVO1.getWk38Day2() != null && newTekCalendarVO.getWk38Day2().compareTo(newTekCalendarVO1.getWk38Day2()) == -1) {
                                    newTekCalendarVO.setWk38Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk38Day3() != null && newTekCalendarVO1.getWk38Day3() != null && newTekCalendarVO.getWk38Day3().compareTo(newTekCalendarVO1.getWk38Day3()) == -1) {
                                    newTekCalendarVO.setWk38Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk38Day4() != null && newTekCalendarVO1.getWk38Day4() != null && newTekCalendarVO.getWk38Day4().compareTo(newTekCalendarVO1.getWk38Day4()) == -1) {
                                    newTekCalendarVO.setWk38Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk38Day5() != null && newTekCalendarVO1.getWk38Day5() != null && newTekCalendarVO.getWk38Day5().compareTo(newTekCalendarVO1.getWk38Day5()) == -1) {
                                    newTekCalendarVO.setWk38Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk38Day6() != null && newTekCalendarVO1.getWk38Day6() != null && newTekCalendarVO.getWk38Day6().compareTo(newTekCalendarVO1.getWk38Day6()) == -1) {
                                    newTekCalendarVO.setWk38Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk38Day7() != null && newTekCalendarVO1.getWk38Day7() != null && newTekCalendarVO.getWk38Day7().compareTo(newTekCalendarVO1.getWk38Day7()) == -1) {
                                    newTekCalendarVO.setWk38Day7Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk39Day1() != null && newTekCalendarVO1.getWk39Day1() != null && newTekCalendarVO.getWk39Day1().compareTo(newTekCalendarVO1.getWk39Day1()) == -1) {
                                    newTekCalendarVO.setWk39Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk39Day2() != null && newTekCalendarVO1.getWk39Day2() != null && newTekCalendarVO.getWk39Day2().compareTo(newTekCalendarVO1.getWk39Day2()) == -1) {
                                    newTekCalendarVO.setWk39Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk39Day3() != null && newTekCalendarVO1.getWk39Day3() != null && newTekCalendarVO.getWk39Day3().compareTo(newTekCalendarVO1.getWk39Day3()) == -1) {
                                    newTekCalendarVO.setWk39Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk39Day4() != null && newTekCalendarVO1.getWk39Day4() != null && newTekCalendarVO.getWk39Day4().compareTo(newTekCalendarVO1.getWk39Day4()) == -1) {
                                    newTekCalendarVO.setWk39Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk39Day5() != null && newTekCalendarVO1.getWk39Day5() != null && newTekCalendarVO.getWk39Day5().compareTo(newTekCalendarVO1.getWk39Day5()) == -1) {
                                    newTekCalendarVO.setWk39Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk39Day6() != null && newTekCalendarVO1.getWk39Day6() != null && newTekCalendarVO.getWk39Day6().compareTo(newTekCalendarVO1.getWk39Day6()) == -1) {
                                    newTekCalendarVO.setWk39Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk39Day7() != null && newTekCalendarVO1.getWk39Day7() != null && newTekCalendarVO.getWk39Day7().compareTo(newTekCalendarVO1.getWk39Day7()) == -1) {
                                    newTekCalendarVO.setWk39Day7Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk40Day1() != null && newTekCalendarVO1.getWk40Day1() != null && newTekCalendarVO.getWk40Day1().compareTo(newTekCalendarVO1.getWk40Day1()) == -1) {
                                    newTekCalendarVO.setWk40Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk40Day2() != null && newTekCalendarVO1.getWk40Day2() != null && newTekCalendarVO.getWk40Day2().compareTo(newTekCalendarVO1.getWk40Day2()) == -1) {
                                    newTekCalendarVO.setWk40Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk40Day3() != null && newTekCalendarVO1.getWk40Day3() != null && newTekCalendarVO.getWk40Day3().compareTo(newTekCalendarVO1.getWk40Day3()) == -1) {
                                    newTekCalendarVO.setWk40Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk40Day4() != null && newTekCalendarVO1.getWk40Day4() != null && newTekCalendarVO.getWk40Day4().compareTo(newTekCalendarVO1.getWk40Day4()) == -1) {
                                    newTekCalendarVO.setWk40Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk40Day5() != null && newTekCalendarVO1.getWk40Day5() != null && newTekCalendarVO.getWk40Day5().compareTo(newTekCalendarVO1.getWk40Day5()) == -1) {
                                    newTekCalendarVO.setWk40Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk40Day6() != null && newTekCalendarVO1.getWk40Day6() != null && newTekCalendarVO.getWk40Day6().compareTo(newTekCalendarVO1.getWk40Day6()) == -1) {
                                    newTekCalendarVO.setWk40Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk40Day7() != null && newTekCalendarVO1.getWk40Day7() != null && newTekCalendarVO.getWk40Day7().compareTo(newTekCalendarVO1.getWk40Day7()) == -1) {
                                    newTekCalendarVO.setWk40Day7Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk41Day1() != null && newTekCalendarVO1.getWk41Day1() != null && newTekCalendarVO.getWk41Day1().compareTo(newTekCalendarVO1.getWk41Day1()) == -1) {
                                    newTekCalendarVO.setWk41Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk41Day2() != null && newTekCalendarVO1.getWk41Day2() != null && newTekCalendarVO.getWk41Day2().compareTo(newTekCalendarVO1.getWk41Day2()) == -1) {
                                    newTekCalendarVO.setWk41Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk41Day3() != null && newTekCalendarVO1.getWk41Day3() != null && newTekCalendarVO.getWk41Day3().compareTo(newTekCalendarVO1.getWk41Day3()) == -1) {
                                    newTekCalendarVO.setWk41Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk41Day4() != null && newTekCalendarVO1.getWk41Day4() != null && newTekCalendarVO.getWk41Day4().compareTo(newTekCalendarVO1.getWk41Day4()) == -1) {
                                    newTekCalendarVO.setWk41Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk41Day5() != null && newTekCalendarVO1.getWk41Day5() != null && newTekCalendarVO.getWk41Day5().compareTo(newTekCalendarVO1.getWk41Day5()) == -1) {
                                    newTekCalendarVO.setWk41Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk41Day6() != null && newTekCalendarVO1.getWk41Day6() != null && newTekCalendarVO.getWk41Day6().compareTo(newTekCalendarVO1.getWk41Day6()) == -1) {
                                    newTekCalendarVO.setWk41Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk41Day7() != null && newTekCalendarVO1.getWk41Day7() != null && newTekCalendarVO.getWk41Day7().compareTo(newTekCalendarVO1.getWk41Day7()) == -1) {
                                    newTekCalendarVO.setWk41Day7Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk42Day1() != null && newTekCalendarVO1.getWk42Day1() != null && newTekCalendarVO.getWk42Day1().compareTo(newTekCalendarVO1.getWk42Day1()) == -1) {
                                    newTekCalendarVO.setWk42Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk42Day2() != null && newTekCalendarVO1.getWk42Day2() != null && newTekCalendarVO.getWk42Day2().compareTo(newTekCalendarVO1.getWk42Day2()) == -1) {
                                    newTekCalendarVO.setWk42Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk42Day3() != null && newTekCalendarVO1.getWk42Day3() != null && newTekCalendarVO.getWk42Day3().compareTo(newTekCalendarVO1.getWk42Day3()) == -1) {
                                    newTekCalendarVO.setWk42Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk42Day4() != null && newTekCalendarVO1.getWk42Day4() != null && newTekCalendarVO.getWk42Day4().compareTo(newTekCalendarVO1.getWk42Day4()) == -1) {
                                    newTekCalendarVO.setWk42Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk42Day5() != null && newTekCalendarVO1.getWk42Day5() != null && newTekCalendarVO.getWk42Day5().compareTo(newTekCalendarVO1.getWk42Day5()) == -1) {
                                    newTekCalendarVO.setWk42Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk42Day6() != null && newTekCalendarVO1.getWk42Day6() != null && newTekCalendarVO.getWk42Day6().compareTo(newTekCalendarVO1.getWk42Day6()) == -1) {
                                    newTekCalendarVO.setWk42Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk42Day7() != null && newTekCalendarVO1.getWk42Day7() != null && newTekCalendarVO.getWk42Day7().compareTo(newTekCalendarVO1.getWk42Day7()) == -1) {
                                    newTekCalendarVO.setWk42Day7Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk43Day1() != null && newTekCalendarVO1.getWk43Day1() != null && newTekCalendarVO.getWk43Day1().compareTo(newTekCalendarVO1.getWk43Day1()) == -1) {
                                    newTekCalendarVO.setWk43Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk43Day2() != null && newTekCalendarVO1.getWk43Day2() != null && newTekCalendarVO.getWk43Day2().compareTo(newTekCalendarVO1.getWk43Day2()) == -1) {
                                    newTekCalendarVO.setWk43Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk43Day3() != null && newTekCalendarVO1.getWk43Day3() != null && newTekCalendarVO.getWk43Day3().compareTo(newTekCalendarVO1.getWk43Day3()) == -1) {
                                    newTekCalendarVO.setWk43Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk43Day4() != null && newTekCalendarVO1.getWk43Day4() != null && newTekCalendarVO.getWk43Day4().compareTo(newTekCalendarVO1.getWk43Day4()) == -1) {
                                    newTekCalendarVO.setWk43Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk43Day5() != null && newTekCalendarVO1.getWk43Day5() != null && newTekCalendarVO.getWk43Day5().compareTo(newTekCalendarVO1.getWk43Day5()) == -1) {
                                    newTekCalendarVO.setWk43Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk43Day6() != null && newTekCalendarVO1.getWk43Day6() != null && newTekCalendarVO.getWk43Day6().compareTo(newTekCalendarVO1.getWk43Day6()) == -1) {
                                    newTekCalendarVO.setWk43Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk43Day7() != null && newTekCalendarVO1.getWk43Day7() != null && newTekCalendarVO.getWk43Day7().compareTo(newTekCalendarVO1.getWk43Day7()) == -1) {
                                    newTekCalendarVO.setWk43Day7Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk44Day1() != null && newTekCalendarVO1.getWk44Day1() != null && newTekCalendarVO.getWk44Day1().compareTo(newTekCalendarVO1.getWk44Day1()) == -1) {
                                    newTekCalendarVO.setWk44Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk44Day2() != null && newTekCalendarVO1.getWk44Day2() != null && newTekCalendarVO.getWk44Day2().compareTo(newTekCalendarVO1.getWk44Day2()) == -1) {
                                    newTekCalendarVO.setWk44Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk44Day3() != null && newTekCalendarVO1.getWk44Day3() != null && newTekCalendarVO.getWk44Day3().compareTo(newTekCalendarVO1.getWk44Day3()) == -1) {
                                    newTekCalendarVO.setWk44Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk44Day4() != null && newTekCalendarVO1.getWk44Day4() != null && newTekCalendarVO.getWk44Day4().compareTo(newTekCalendarVO1.getWk44Day4()) == -1) {
                                    newTekCalendarVO.setWk44Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk44Day5() != null && newTekCalendarVO1.getWk44Day5() != null && newTekCalendarVO.getWk44Day5().compareTo(newTekCalendarVO1.getWk44Day5()) == -1) {
                                    newTekCalendarVO.setWk44Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk44Day6() != null && newTekCalendarVO1.getWk44Day6() != null && newTekCalendarVO.getWk44Day6().compareTo(newTekCalendarVO1.getWk44Day6()) == -1) {
                                    newTekCalendarVO.setWk44Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk44Day7() != null && newTekCalendarVO1.getWk44Day7() != null && newTekCalendarVO.getWk44Day7().compareTo(newTekCalendarVO1.getWk44Day7()) == -1) {
                                    newTekCalendarVO.setWk44Day7Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk45Day1() != null && newTekCalendarVO1.getWk45Day1() != null && newTekCalendarVO.getWk45Day1().compareTo(newTekCalendarVO1.getWk45Day1()) == -1) {
                                    newTekCalendarVO.setWk45Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk45Day2() != null && newTekCalendarVO1.getWk45Day2() != null && newTekCalendarVO.getWk45Day2().compareTo(newTekCalendarVO1.getWk45Day2()) == -1) {
                                    newTekCalendarVO.setWk45Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk45Day3() != null && newTekCalendarVO1.getWk45Day3() != null && newTekCalendarVO.getWk45Day3().compareTo(newTekCalendarVO1.getWk45Day3()) == -1) {
                                    newTekCalendarVO.setWk45Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk45Day4() != null && newTekCalendarVO1.getWk45Day4() != null && newTekCalendarVO.getWk45Day4().compareTo(newTekCalendarVO1.getWk45Day4()) == -1) {
                                    newTekCalendarVO.setWk45Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk45Day5() != null && newTekCalendarVO1.getWk45Day5() != null && newTekCalendarVO.getWk45Day5().compareTo(newTekCalendarVO1.getWk45Day5()) == -1) {
                                    newTekCalendarVO.setWk45Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk45Day6() != null && newTekCalendarVO1.getWk45Day6() != null && newTekCalendarVO.getWk45Day6().compareTo(newTekCalendarVO1.getWk45Day6()) == -1) {
                                    newTekCalendarVO.setWk45Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk45Day7() != null && newTekCalendarVO1.getWk45Day7() != null && newTekCalendarVO.getWk45Day7().compareTo(newTekCalendarVO1.getWk45Day7()) == -1) {
                                    newTekCalendarVO.setWk45Day7Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk46Day1() != null && newTekCalendarVO1.getWk46Day1() != null && newTekCalendarVO.getWk46Day1().compareTo(newTekCalendarVO1.getWk46Day1()) == -1) {
                                    newTekCalendarVO.setWk46Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk46Day2() != null && newTekCalendarVO1.getWk46Day2() != null && newTekCalendarVO.getWk46Day2().compareTo(newTekCalendarVO1.getWk46Day2()) == -1) {
                                    newTekCalendarVO.setWk46Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk46Day3() != null && newTekCalendarVO1.getWk46Day3() != null && newTekCalendarVO.getWk46Day3().compareTo(newTekCalendarVO1.getWk46Day3()) == -1) {
                                    newTekCalendarVO.setWk46Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk46Day4() != null && newTekCalendarVO1.getWk46Day4() != null && newTekCalendarVO.getWk46Day4().compareTo(newTekCalendarVO1.getWk46Day4()) == -1) {
                                    newTekCalendarVO.setWk46Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk46Day5() != null && newTekCalendarVO1.getWk46Day5() != null && newTekCalendarVO.getWk46Day5().compareTo(newTekCalendarVO1.getWk46Day5()) == -1) {
                                    newTekCalendarVO.setWk46Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk46Day6() != null && newTekCalendarVO1.getWk46Day6() != null && newTekCalendarVO.getWk46Day6().compareTo(newTekCalendarVO1.getWk46Day6()) == -1) {
                                    newTekCalendarVO.setWk46Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk46Day7() != null && newTekCalendarVO1.getWk46Day7() != null && newTekCalendarVO.getWk46Day7().compareTo(newTekCalendarVO1.getWk46Day7()) == -1) {
                                    newTekCalendarVO.setWk46Day7Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk47Day1() != null && newTekCalendarVO1.getWk47Day1() != null && newTekCalendarVO.getWk47Day1().compareTo(newTekCalendarVO1.getWk47Day1()) == -1) {
                                    newTekCalendarVO.setWk47Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk47Day2() != null && newTekCalendarVO1.getWk47Day2() != null && newTekCalendarVO.getWk47Day2().compareTo(newTekCalendarVO1.getWk47Day2()) == -1) {
                                    newTekCalendarVO.setWk47Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk47Day3() != null && newTekCalendarVO1.getWk47Day3() != null && newTekCalendarVO.getWk47Day3().compareTo(newTekCalendarVO1.getWk47Day3()) == -1) {
                                    newTekCalendarVO.setWk47Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk47Day4() != null && newTekCalendarVO1.getWk47Day4() != null && newTekCalendarVO.getWk47Day4().compareTo(newTekCalendarVO1.getWk47Day4()) == -1) {
                                    newTekCalendarVO.setWk47Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk47Day5() != null && newTekCalendarVO1.getWk47Day5() != null && newTekCalendarVO.getWk47Day5().compareTo(newTekCalendarVO1.getWk47Day5()) == -1) {
                                    newTekCalendarVO.setWk47Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk47Day6() != null && newTekCalendarVO1.getWk47Day6() != null && newTekCalendarVO.getWk47Day6().compareTo(newTekCalendarVO1.getWk47Day6()) == -1) {
                                    newTekCalendarVO.setWk47Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk47Day7() != null && newTekCalendarVO1.getWk47Day7() != null && newTekCalendarVO.getWk47Day7().compareTo(newTekCalendarVO1.getWk47Day7()) == -1) {
                                    newTekCalendarVO.setWk47Day7Status(tekConstants.WK_STATUS_ERROR);
                                }

                                if (newTekCalendarVO.getWk48Day1() != null && newTekCalendarVO1.getWk48Day1() != null && newTekCalendarVO.getWk48Day1().compareTo(newTekCalendarVO1.getWk48Day1()) == -1) {
                                    newTekCalendarVO.setWk48Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk48Day2() != null && newTekCalendarVO1.getWk48Day2() != null && newTekCalendarVO.getWk48Day2().compareTo(newTekCalendarVO1.getWk48Day2()) == -1) {
                                    newTekCalendarVO.setWk48Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk48Day3() != null && newTekCalendarVO1.getWk48Day3() != null && newTekCalendarVO.getWk48Day3().compareTo(newTekCalendarVO1.getWk48Day3()) == -1) {
                                    newTekCalendarVO.setWk48Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk48Day4() != null && newTekCalendarVO1.getWk48Day4() != null && newTekCalendarVO.getWk48Day4().compareTo(newTekCalendarVO1.getWk48Day4()) == -1) {
                                    newTekCalendarVO.setWk48Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk48Day5() != null && newTekCalendarVO1.getWk48Day5() != null && newTekCalendarVO.getWk48Day5().compareTo(newTekCalendarVO1.getWk48Day5()) == -1) {
                                    newTekCalendarVO.setWk48Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk48Day6() != null && newTekCalendarVO1.getWk48Day6() != null && newTekCalendarVO.getWk48Day6().compareTo(newTekCalendarVO1.getWk48Day6()) == -1) {
                                    newTekCalendarVO.setWk48Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk48Day7() != null && newTekCalendarVO1.getWk48Day7() != null && newTekCalendarVO.getWk48Day7().compareTo(newTekCalendarVO1.getWk48Day7()) == -1) {
                                    newTekCalendarVO.setWk48Day7Status(tekConstants.WK_STATUS_ERROR);
                                }

                                if (newTekCalendarVO.getWk49Day1() != null && newTekCalendarVO1.getWk49Day1() != null && newTekCalendarVO.getWk49Day1().compareTo(newTekCalendarVO1.getWk49Day1()) == -1) {
                                    newTekCalendarVO.setWk49Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk49Day2() != null && newTekCalendarVO1.getWk49Day2() != null && newTekCalendarVO.getWk49Day2().compareTo(newTekCalendarVO1.getWk49Day2()) == -1) {
                                    newTekCalendarVO.setWk49Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk49Day3() != null && newTekCalendarVO1.getWk49Day3() != null && newTekCalendarVO.getWk49Day3().compareTo(newTekCalendarVO1.getWk49Day3()) == -1) {
                                    newTekCalendarVO.setWk49Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk49Day4() != null && newTekCalendarVO1.getWk49Day4() != null && newTekCalendarVO.getWk49Day4().compareTo(newTekCalendarVO1.getWk49Day4()) == -1) {
                                    newTekCalendarVO.setWk49Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk49Day5() != null && newTekCalendarVO1.getWk49Day5() != null && newTekCalendarVO.getWk49Day5().compareTo(newTekCalendarVO1.getWk49Day5()) == -1) {
                                    newTekCalendarVO.setWk49Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk49Day6() != null && newTekCalendarVO1.getWk49Day6() != null && newTekCalendarVO.getWk49Day6().compareTo(newTekCalendarVO1.getWk49Day6()) == -1) {
                                    newTekCalendarVO.setWk49Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk49Day7() != null && newTekCalendarVO1.getWk49Day7() != null && newTekCalendarVO.getWk49Day7().compareTo(newTekCalendarVO1.getWk49Day7()) == -1) {
                                    newTekCalendarVO.setWk49Day7Status(tekConstants.WK_STATUS_ERROR);
                                }

                                if (newTekCalendarVO.getWk50Day1() != null && newTekCalendarVO1.getWk50Day1() != null && newTekCalendarVO.getWk50Day1().compareTo(newTekCalendarVO1.getWk50Day1()) == -1) {
                                    newTekCalendarVO.setWk50Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk50Day2() != null && newTekCalendarVO1.getWk50Day2() != null && newTekCalendarVO.getWk50Day2().compareTo(newTekCalendarVO1.getWk50Day2()) == -1) {
                                    newTekCalendarVO.setWk50Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk50Day3() != null && newTekCalendarVO1.getWk50Day3() != null && newTekCalendarVO.getWk50Day3().compareTo(newTekCalendarVO1.getWk50Day3()) == -1) {
                                    newTekCalendarVO.setWk50Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk50Day4() != null && newTekCalendarVO1.getWk50Day4() != null && newTekCalendarVO.getWk50Day4().compareTo(newTekCalendarVO1.getWk50Day4()) == -1) {
                                    newTekCalendarVO.setWk50Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk50Day5() != null && newTekCalendarVO1.getWk50Day5() != null && newTekCalendarVO.getWk50Day5().compareTo(newTekCalendarVO1.getWk50Day5()) == -1) {
                                    newTekCalendarVO.setWk50Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk50Day6() != null && newTekCalendarVO1.getWk50Day6() != null && newTekCalendarVO.getWk50Day6().compareTo(newTekCalendarVO1.getWk50Day6()) == -1) {
                                    newTekCalendarVO.setWk50Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk50Day7() != null && newTekCalendarVO1.getWk50Day7() != null && newTekCalendarVO.getWk50Day7().compareTo(newTekCalendarVO1.getWk50Day7()) == -1) {
                                    newTekCalendarVO.setWk50Day7Status(tekConstants.WK_STATUS_ERROR);
                                }

                                if (newTekCalendarVO.getWk51Day1() != null && newTekCalendarVO1.getWk51Day1() != null && newTekCalendarVO.getWk51Day1().compareTo(newTekCalendarVO1.getWk51Day1()) == -1) {
                                    newTekCalendarVO.setWk51Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk51Day2() != null && newTekCalendarVO1.getWk51Day2() != null && newTekCalendarVO.getWk51Day2().compareTo(newTekCalendarVO1.getWk51Day2()) == -1) {
                                    newTekCalendarVO.setWk51Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk51Day3() != null && newTekCalendarVO1.getWk51Day3() != null && newTekCalendarVO.getWk51Day3().compareTo(newTekCalendarVO1.getWk51Day3()) == -1) {
                                    newTekCalendarVO.setWk51Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk51Day4() != null && newTekCalendarVO1.getWk51Day4() != null && newTekCalendarVO.getWk51Day4().compareTo(newTekCalendarVO1.getWk51Day4()) == -1) {
                                    newTekCalendarVO.setWk51Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk51Day5() != null && newTekCalendarVO1.getWk51Day5() != null && newTekCalendarVO.getWk51Day5().compareTo(newTekCalendarVO1.getWk51Day5()) == -1) {
                                    newTekCalendarVO.setWk51Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk51Day6() != null && newTekCalendarVO1.getWk51Day6() != null && newTekCalendarVO.getWk51Day6().compareTo(newTekCalendarVO1.getWk51Day6()) == -1) {
                                    newTekCalendarVO.setWk51Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk51Day7() != null && newTekCalendarVO1.getWk51Day7() != null && newTekCalendarVO.getWk51Day7().compareTo(newTekCalendarVO1.getWk51Day7()) == -1) {
                                    newTekCalendarVO.setWk51Day7Status(tekConstants.WK_STATUS_ERROR);
                                }

                                if (newTekCalendarVO.getWk52Day1() != null && newTekCalendarVO1.getWk52Day1() != null && newTekCalendarVO.getWk52Day1().compareTo(newTekCalendarVO1.getWk52Day1()) == -1) {
                                    newTekCalendarVO.setWk52Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk52Day2() != null && newTekCalendarVO1.getWk52Day2() != null && newTekCalendarVO.getWk52Day2().compareTo(newTekCalendarVO1.getWk52Day2()) == -1) {
                                    newTekCalendarVO.setWk52Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk52Day3() != null && newTekCalendarVO1.getWk52Day3() != null && newTekCalendarVO.getWk52Day3().compareTo(newTekCalendarVO1.getWk52Day3()) == -1) {
                                    newTekCalendarVO.setWk52Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk52Day4() != null && newTekCalendarVO1.getWk52Day4() != null && newTekCalendarVO.getWk52Day4().compareTo(newTekCalendarVO1.getWk52Day4()) == -1) {
                                    newTekCalendarVO.setWk52Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk52Day5() != null && newTekCalendarVO1.getWk52Day5() != null && newTekCalendarVO.getWk52Day5().compareTo(newTekCalendarVO1.getWk52Day5()) == -1) {
                                    newTekCalendarVO.setWk52Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk52Day6() != null && newTekCalendarVO1.getWk52Day6() != null && newTekCalendarVO.getWk52Day6().compareTo(newTekCalendarVO1.getWk52Day6()) == -1) {
                                    newTekCalendarVO.setWk52Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk52Day7() != null && newTekCalendarVO1.getWk52Day7() != null && newTekCalendarVO.getWk52Day7().compareTo(newTekCalendarVO1.getWk52Day7()) == -1) {
                                    newTekCalendarVO.setWk52Day7Status(tekConstants.WK_STATUS_ERROR);
                                }

                                if (newTekCalendarVO.getWk53Day1() != null && newTekCalendarVO1.getWk53Day1() != null && newTekCalendarVO.getWk53Day1().compareTo(newTekCalendarVO1.getWk53Day1()) == -1) {
                                    newTekCalendarVO.setWk53Day1Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk53Day2() != null && newTekCalendarVO1.getWk53Day2() != null && newTekCalendarVO.getWk53Day2().compareTo(newTekCalendarVO1.getWk53Day2()) == -1) {
                                    newTekCalendarVO.setWk53Day2Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk53Day3() != null && newTekCalendarVO1.getWk53Day3() != null && newTekCalendarVO.getWk53Day3().compareTo(newTekCalendarVO1.getWk53Day3()) == -1) {
                                    newTekCalendarVO.setWk53Day3Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk53Day4() != null && newTekCalendarVO1.getWk53Day4() != null && newTekCalendarVO.getWk53Day4().compareTo(newTekCalendarVO1.getWk53Day4()) == -1) {
                                    newTekCalendarVO.setWk53Day4Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk53Day5() != null && newTekCalendarVO1.getWk53Day5() != null && newTekCalendarVO.getWk53Day5().compareTo(newTekCalendarVO1.getWk53Day5()) == -1) {
                                    newTekCalendarVO.setWk53Day5Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk53Day6() != null && newTekCalendarVO1.getWk53Day6() != null && newTekCalendarVO.getWk53Day6().compareTo(newTekCalendarVO1.getWk53Day6()) == -1) {
                                    newTekCalendarVO.setWk53Day6Status(tekConstants.WK_STATUS_ERROR);
                                }
                                if (newTekCalendarVO.getWk53Day7() != null && newTekCalendarVO1.getWk53Day7() != null && newTekCalendarVO.getWk53Day7().compareTo(newTekCalendarVO1.getWk53Day7()) == -1) {
                                    newTekCalendarVO.setWk53Day7Status(tekConstants.WK_STATUS_ERROR);
                                }
                            }
                        }
                    }

                }
            }
        }
        return newTekCalendarVO;
    }

    public NewTekCalendarVO queryBySkuCountryAndDiffCustomer(Map<String,NewTekCalendarVO> map,NewTekCalendarVO newTekCalendarVO){
        for (Map.Entry<String,NewTekCalendarVO> m : map.entrySet()){
            String key = m.getKey();
            NewTekCalendarVO newTekCalendarVO2 = m.getValue();
            String sku = key.split(",")[0];
            String customerId = key.split(",")[1];
            String country = key.split(",")[2];
            String year = key.split(",")[3];

            String existSku = newTekCalendarVO.getSku();
            String existCountry = newTekCalendarVO.getCountry();
            String existYear = newTekCalendarVO.getYear();
            Long existCustomerId = newTekCalendarVO.getCustomerId();
            String existCustomerName = newTekCalendarVO.getCustomerName();

            if(sku.equals(existSku) && country.equals(existCountry) && year.equals(existYear) && !customerId.equals(String.valueOf(existCustomerId))){
                if(newTekCalendarVO2.getWk1MsrpUSD() != null && newTekCalendarVO.getWk1MsrpUSD() != null && newTekCalendarVO2.getWk1MsrpUSD().compareTo(newTekCalendarVO.getWk1MsrpUSD()) != 0){
                    newTekCalendarVO.setWk1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk2MsrpUSD() != null && newTekCalendarVO.getWk2MsrpUSD() != null && newTekCalendarVO2.getWk2MsrpUSD().compareTo(newTekCalendarVO.getWk2MsrpUSD()) != 0){
                    newTekCalendarVO.setWk2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk3MsrpUSD() != null && newTekCalendarVO.getWk3MsrpUSD() != null && newTekCalendarVO2.getWk3MsrpUSD().compareTo(newTekCalendarVO.getWk3MsrpUSD()) != 0){
                    newTekCalendarVO.setWk3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk4MsrpUSD() != null && newTekCalendarVO.getWk4MsrpUSD() != null && newTekCalendarVO2.getWk4MsrpUSD().compareTo(newTekCalendarVO.getWk4MsrpUSD()) != 0){
                    newTekCalendarVO.setWk4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk5MsrpUSD() != null && newTekCalendarVO.getWk5MsrpUSD() != null && newTekCalendarVO2.getWk5MsrpUSD().compareTo(newTekCalendarVO.getWk5MsrpUSD()) != 0){
                    newTekCalendarVO.setWk5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk6MsrpUSD() != null && newTekCalendarVO.getWk6MsrpUSD() != null && newTekCalendarVO2.getWk6MsrpUSD().compareTo(newTekCalendarVO.getWk6MsrpUSD()) != 0){
                    newTekCalendarVO.setWk6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk7MsrpUSD() != null && newTekCalendarVO.getWk7MsrpUSD() != null && newTekCalendarVO2.getWk7MsrpUSD().compareTo(newTekCalendarVO.getWk7MsrpUSD()) != 0){
                    newTekCalendarVO.setWk7Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk8MsrpUSD() != null && newTekCalendarVO.getWk8MsrpUSD() != null && newTekCalendarVO2.getWk8MsrpUSD().compareTo(newTekCalendarVO.getWk8MsrpUSD()) != 0){
                    newTekCalendarVO.setWk8Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk9MsrpUSD() != null && newTekCalendarVO.getWk9MsrpUSD() != null && newTekCalendarVO2.getWk9MsrpUSD().compareTo(newTekCalendarVO.getWk9MsrpUSD()) != 0){
                    newTekCalendarVO.setWk9Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk10MsrpUSD() != null && newTekCalendarVO.getWk10MsrpUSD() != null && newTekCalendarVO2.getWk10MsrpUSD().compareTo(newTekCalendarVO.getWk10MsrpUSD()) != 0){
                    newTekCalendarVO.setWk10Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk11MsrpUSD() != null && newTekCalendarVO.getWk11MsrpUSD() != null && newTekCalendarVO2.getWk11MsrpUSD().compareTo(newTekCalendarVO.getWk11MsrpUSD()) != 0){
                    newTekCalendarVO.setWk11Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk12MsrpUSD() != null && newTekCalendarVO.getWk12MsrpUSD() != null && newTekCalendarVO2.getWk12MsrpUSD().compareTo(newTekCalendarVO.getWk12MsrpUSD()) != 0){
                    newTekCalendarVO.setWk12Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk13MsrpUSD() != null && newTekCalendarVO.getWk13MsrpUSD() != null && newTekCalendarVO2.getWk13MsrpUSD().compareTo(newTekCalendarVO.getWk13MsrpUSD()) != 0){
                    newTekCalendarVO.setWk13Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk14MsrpUSD() != null && newTekCalendarVO.getWk14MsrpUSD() != null && newTekCalendarVO2.getWk14MsrpUSD().compareTo(newTekCalendarVO.getWk14MsrpUSD()) != 0){
                    newTekCalendarVO.setWk14Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk15MsrpUSD() != null && newTekCalendarVO.getWk15MsrpUSD() != null && newTekCalendarVO2.getWk15MsrpUSD().compareTo(newTekCalendarVO.getWk15MsrpUSD()) != 0){
                    newTekCalendarVO.setWk15Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk16MsrpUSD() != null && newTekCalendarVO.getWk16MsrpUSD() != null && newTekCalendarVO2.getWk16MsrpUSD().compareTo(newTekCalendarVO.getWk16MsrpUSD()) != 0){
                    newTekCalendarVO.setWk16Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk17MsrpUSD() != null && newTekCalendarVO.getWk17MsrpUSD() != null && newTekCalendarVO2.getWk17MsrpUSD().compareTo(newTekCalendarVO.getWk17MsrpUSD()) != 0){
                    newTekCalendarVO.setWk17Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk18MsrpUSD() != null && newTekCalendarVO.getWk18MsrpUSD() != null && newTekCalendarVO2.getWk18MsrpUSD().compareTo(newTekCalendarVO.getWk18MsrpUSD()) != 0){
                    newTekCalendarVO.setWk18Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk19MsrpUSD() != null && newTekCalendarVO.getWk19MsrpUSD() != null && newTekCalendarVO2.getWk19MsrpUSD().compareTo(newTekCalendarVO.getWk19MsrpUSD()) != 0){
                    newTekCalendarVO.setWk19Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk20MsrpUSD() != null && newTekCalendarVO.getWk20MsrpUSD() != null && newTekCalendarVO2.getWk20MsrpUSD().compareTo(newTekCalendarVO.getWk20MsrpUSD()) != 0){
                    newTekCalendarVO.setWk20Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk21MsrpUSD() != null && newTekCalendarVO.getWk21MsrpUSD() != null && newTekCalendarVO2.getWk21MsrpUSD().compareTo(newTekCalendarVO.getWk21MsrpUSD()) != 0){
                    newTekCalendarVO.setWk21Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk22MsrpUSD() != null && newTekCalendarVO.getWk22MsrpUSD() != null && newTekCalendarVO2.getWk22MsrpUSD().compareTo(newTekCalendarVO.getWk22MsrpUSD()) != 0){
                    newTekCalendarVO.setWk22Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk23MsrpUSD() != null && newTekCalendarVO.getWk23MsrpUSD() != null && newTekCalendarVO2.getWk23MsrpUSD().compareTo(newTekCalendarVO.getWk23MsrpUSD()) != 0){
                    newTekCalendarVO.setWk23Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk24MsrpUSD() != null && newTekCalendarVO.getWk24MsrpUSD() != null && newTekCalendarVO2.getWk24MsrpUSD().compareTo(newTekCalendarVO.getWk24MsrpUSD()) != 0){
                    newTekCalendarVO.setWk24Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk25MsrpUSD() != null && newTekCalendarVO.getWk25MsrpUSD() != null && newTekCalendarVO2.getWk25MsrpUSD().compareTo(newTekCalendarVO.getWk25MsrpUSD()) != 0){
                    newTekCalendarVO.setWk25Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk26MsrpUSD() != null && newTekCalendarVO.getWk26MsrpUSD() != null && newTekCalendarVO2.getWk26MsrpUSD().compareTo(newTekCalendarVO.getWk26MsrpUSD()) != 0){
                    newTekCalendarVO.setWk26Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk27MsrpUSD() != null && newTekCalendarVO.getWk27MsrpUSD() != null && newTekCalendarVO2.getWk27MsrpUSD().compareTo(newTekCalendarVO.getWk27MsrpUSD()) != 0){
                    newTekCalendarVO.setWk27Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk28MsrpUSD() != null && newTekCalendarVO.getWk28MsrpUSD() != null && newTekCalendarVO2.getWk28MsrpUSD().compareTo(newTekCalendarVO.getWk28MsrpUSD()) != 0){
                    newTekCalendarVO.setWk28Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk29MsrpUSD() != null && newTekCalendarVO.getWk29MsrpUSD() != null && newTekCalendarVO2.getWk29MsrpUSD().compareTo(newTekCalendarVO.getWk29MsrpUSD()) != 0){
                    newTekCalendarVO.setWk29Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk30MsrpUSD() != null && newTekCalendarVO.getWk30MsrpUSD() != null && newTekCalendarVO2.getWk30MsrpUSD().compareTo(newTekCalendarVO.getWk30MsrpUSD()) != 0){
                    newTekCalendarVO.setWk30Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk31MsrpUSD() != null && newTekCalendarVO.getWk31MsrpUSD() != null && newTekCalendarVO2.getWk31MsrpUSD().compareTo(newTekCalendarVO.getWk31MsrpUSD()) != 0){
                    newTekCalendarVO.setWk31Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk32MsrpUSD() != null && newTekCalendarVO.getWk32MsrpUSD() != null && newTekCalendarVO2.getWk32MsrpUSD().compareTo(newTekCalendarVO.getWk32MsrpUSD()) != 0){
                    newTekCalendarVO.setWk32Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk33MsrpUSD() != null && newTekCalendarVO.getWk33MsrpUSD() != null && newTekCalendarVO2.getWk33MsrpUSD().compareTo(newTekCalendarVO.getWk33MsrpUSD()) != 0){
                    newTekCalendarVO.setWk33Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk34MsrpUSD() != null && newTekCalendarVO.getWk34MsrpUSD() != null && newTekCalendarVO2.getWk34MsrpUSD().compareTo(newTekCalendarVO.getWk34MsrpUSD()) != 0){
                    newTekCalendarVO.setWk34Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk35MsrpUSD() != null && newTekCalendarVO.getWk35MsrpUSD() != null && newTekCalendarVO2.getWk35MsrpUSD().compareTo(newTekCalendarVO.getWk35MsrpUSD()) != 0){
                    newTekCalendarVO.setWk35Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk36MsrpUSD() != null && newTekCalendarVO.getWk36MsrpUSD() != null && newTekCalendarVO2.getWk36MsrpUSD().compareTo(newTekCalendarVO.getWk36MsrpUSD()) != 0){
                    newTekCalendarVO.setWk36Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk37MsrpUSD() != null && newTekCalendarVO.getWk37MsrpUSD() != null && newTekCalendarVO2.getWk37MsrpUSD().compareTo(newTekCalendarVO.getWk37MsrpUSD()) != 0){
                    newTekCalendarVO.setWk37Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk38MsrpUSD() != null && newTekCalendarVO.getWk38MsrpUSD() != null && newTekCalendarVO2.getWk38MsrpUSD().compareTo(newTekCalendarVO.getWk38MsrpUSD()) != 0){
                    newTekCalendarVO.setWk38Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk39MsrpUSD() != null && newTekCalendarVO.getWk39MsrpUSD() != null && newTekCalendarVO2.getWk39MsrpUSD().compareTo(newTekCalendarVO.getWk39MsrpUSD()) != 0){
                    newTekCalendarVO.setWk39Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk40MsrpUSD() != null && newTekCalendarVO.getWk40MsrpUSD() != null && newTekCalendarVO2.getWk40MsrpUSD().compareTo(newTekCalendarVO.getWk40MsrpUSD()) != 0){
                    newTekCalendarVO.setWk40Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk41MsrpUSD() != null && newTekCalendarVO.getWk41MsrpUSD() != null && newTekCalendarVO2.getWk41MsrpUSD().compareTo(newTekCalendarVO.getWk41MsrpUSD()) != 0){
                    newTekCalendarVO.setWk41Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk42MsrpUSD() != null && newTekCalendarVO.getWk42MsrpUSD() != null && newTekCalendarVO2.getWk42MsrpUSD().compareTo(newTekCalendarVO.getWk42MsrpUSD()) != 0){
                    newTekCalendarVO.setWk42Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk43MsrpUSD() != null && newTekCalendarVO.getWk43MsrpUSD() != null && newTekCalendarVO2.getWk43MsrpUSD().compareTo(newTekCalendarVO.getWk43MsrpUSD()) != 0){
                    newTekCalendarVO.setWk43Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk44MsrpUSD() != null && newTekCalendarVO.getWk44MsrpUSD() != null && newTekCalendarVO2.getWk44MsrpUSD().compareTo(newTekCalendarVO.getWk44MsrpUSD()) != 0){
                    newTekCalendarVO.setWk44Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk45MsrpUSD() != null && newTekCalendarVO.getWk45MsrpUSD() != null && newTekCalendarVO2.getWk45MsrpUSD().compareTo(newTekCalendarVO.getWk45MsrpUSD()) != 0){
                    newTekCalendarVO.setWk45Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk46MsrpUSD() != null && newTekCalendarVO.getWk46MsrpUSD() != null && newTekCalendarVO2.getWk46MsrpUSD().compareTo(newTekCalendarVO.getWk46MsrpUSD()) != 0){
                    newTekCalendarVO.setWk46Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk47MsrpUSD() != null && newTekCalendarVO.getWk47MsrpUSD() != null && newTekCalendarVO2.getWk47MsrpUSD().compareTo(newTekCalendarVO.getWk47MsrpUSD()) != 0){
                    newTekCalendarVO.setWk47Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk48MsrpUSD() != null && newTekCalendarVO.getWk48MsrpUSD() != null && newTekCalendarVO2.getWk48MsrpUSD().compareTo(newTekCalendarVO.getWk48MsrpUSD()) != 0){
                    newTekCalendarVO.setWk48Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk49MsrpUSD() != null && newTekCalendarVO.getWk49MsrpUSD() != null && newTekCalendarVO2.getWk49MsrpUSD().compareTo(newTekCalendarVO.getWk49MsrpUSD()) != 0){
                    newTekCalendarVO.setWk49Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk50MsrpUSD() != null && newTekCalendarVO.getWk50MsrpUSD() != null && newTekCalendarVO2.getWk50MsrpUSD().compareTo(newTekCalendarVO.getWk50MsrpUSD()) != 0){
                    newTekCalendarVO.setWk50Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk51MsrpUSD() != null && newTekCalendarVO.getWk51MsrpUSD() != null && newTekCalendarVO2.getWk51MsrpUSD().compareTo(newTekCalendarVO.getWk51MsrpUSD()) != 0){
                    newTekCalendarVO.setWk51Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk52MsrpUSD() != null && newTekCalendarVO.getWk52MsrpUSD() != null && newTekCalendarVO2.getWk52MsrpUSD().compareTo(newTekCalendarVO.getWk52MsrpUSD()) != 0){
                    newTekCalendarVO.setWk52Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk53MsrpUSD() != null && newTekCalendarVO.getWk53MsrpUSD() != null && newTekCalendarVO2.getWk53MsrpUSD().compareTo(newTekCalendarVO.getWk53MsrpUSD()) != 0){
                    newTekCalendarVO.setWk53Status(tekConstants.WK_STATUS_ERROR);
                }
                //day状态判断
                if(newTekCalendarVO2.getWk1Day1() != null && newTekCalendarVO.getWk1Day1() != null && newTekCalendarVO2.getWk1Day1().compareTo(newTekCalendarVO.getWk1Day1()) != 0){
                    newTekCalendarVO.setWk1Day1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk1Day2() != null && newTekCalendarVO.getWk1Day2() != null && newTekCalendarVO2.getWk1Day2().compareTo(newTekCalendarVO.getWk1Day2()) != 0){
                    newTekCalendarVO.setWk1Day2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk1Day3() != null && newTekCalendarVO.getWk1Day3() != null && newTekCalendarVO2.getWk1Day3().compareTo(newTekCalendarVO.getWk1Day3()) != 0){
                    newTekCalendarVO.setWk1Day3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk1Day4() != null && newTekCalendarVO.getWk1Day4() != null && newTekCalendarVO2.getWk1Day4().compareTo(newTekCalendarVO.getWk1Day4()) != 0){
                    newTekCalendarVO.setWk1Day4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk1Day5() != null && newTekCalendarVO.getWk1Day5() != null && newTekCalendarVO2.getWk1Day5().compareTo(newTekCalendarVO.getWk1Day5()) != 0){
                    newTekCalendarVO.setWk1Day5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk1Day6() != null && newTekCalendarVO.getWk1Day6() != null && newTekCalendarVO2.getWk1Day6().compareTo(newTekCalendarVO.getWk1Day6()) != 0){
                    newTekCalendarVO.setWk1Day6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk1Day7() != null && newTekCalendarVO.getWk1Day7() != null && newTekCalendarVO2.getWk1Day7().compareTo(newTekCalendarVO.getWk1Day7()) != 0){
                    newTekCalendarVO.setWk1Day7Status(tekConstants.WK_STATUS_ERROR);
                }

                if(newTekCalendarVO2.getWk2Day1() != null && newTekCalendarVO.getWk2Day1() != null && newTekCalendarVO2.getWk2Day1().compareTo(newTekCalendarVO.getWk2Day1()) != 0){
                    newTekCalendarVO.setWk2Day1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk2Day2() != null && newTekCalendarVO.getWk2Day2() != null && newTekCalendarVO2.getWk2Day2().compareTo(newTekCalendarVO.getWk2Day2()) != 0){
                    newTekCalendarVO.setWk2Day2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk2Day3() != null && newTekCalendarVO.getWk2Day3() != null && newTekCalendarVO2.getWk2Day3().compareTo(newTekCalendarVO.getWk2Day3()) != 0){
                    newTekCalendarVO.setWk2Day3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk2Day4() != null && newTekCalendarVO.getWk2Day4() != null && newTekCalendarVO2.getWk2Day4().compareTo(newTekCalendarVO.getWk2Day4()) != 0){
                    newTekCalendarVO.setWk2Day4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk2Day5() != null && newTekCalendarVO.getWk2Day5() != null && newTekCalendarVO2.getWk2Day5().compareTo(newTekCalendarVO.getWk2Day5()) != 0){
                    newTekCalendarVO.setWk2Day5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk2Day6() != null && newTekCalendarVO.getWk2Day6() != null && newTekCalendarVO2.getWk2Day6().compareTo(newTekCalendarVO.getWk2Day6()) != 0){
                    newTekCalendarVO.setWk2Day6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk2Day7() != null && newTekCalendarVO.getWk2Day7() != null && newTekCalendarVO2.getWk2Day7().compareTo(newTekCalendarVO.getWk2Day7()) != 0){
                    newTekCalendarVO.setWk2Day7Status(tekConstants.WK_STATUS_ERROR);
                }

                if(newTekCalendarVO2.getWk3Day1() != null && newTekCalendarVO.getWk3Day1() != null && newTekCalendarVO2.getWk3Day1().compareTo(newTekCalendarVO.getWk3Day1()) != 0){
                    newTekCalendarVO.setWk3Day1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk3Day2() != null && newTekCalendarVO.getWk3Day2() != null && newTekCalendarVO2.getWk3Day2().compareTo(newTekCalendarVO.getWk3Day2()) != 0){
                    newTekCalendarVO.setWk3Day2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk3Day3() != null && newTekCalendarVO.getWk3Day3() != null && newTekCalendarVO2.getWk3Day3().compareTo(newTekCalendarVO.getWk3Day3()) != 0){
                    newTekCalendarVO.setWk3Day3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk3Day4() != null && newTekCalendarVO.getWk3Day4() != null && newTekCalendarVO2.getWk3Day4().compareTo(newTekCalendarVO.getWk3Day4()) != 0){
                    newTekCalendarVO.setWk3Day4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk3Day5() != null && newTekCalendarVO.getWk3Day5() != null && newTekCalendarVO2.getWk3Day5().compareTo(newTekCalendarVO.getWk3Day5()) != 0){
                    newTekCalendarVO.setWk3Day5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk3Day6() != null && newTekCalendarVO.getWk3Day6() != null && newTekCalendarVO2.getWk3Day6().compareTo(newTekCalendarVO.getWk3Day6()) != 0){
                    newTekCalendarVO.setWk3Day6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk3Day7() != null && newTekCalendarVO.getWk3Day7() != null && newTekCalendarVO2.getWk3Day7().compareTo(newTekCalendarVO.getWk3Day7()) != 0){
                    newTekCalendarVO.setWk3Day7Status(tekConstants.WK_STATUS_ERROR);
                }

                if(newTekCalendarVO2.getWk4Day1() != null && newTekCalendarVO.getWk4Day1() != null && newTekCalendarVO2.getWk4Day1().compareTo(newTekCalendarVO.getWk4Day1()) != 0){
                    newTekCalendarVO.setWk4Day1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk4Day2() != null && newTekCalendarVO.getWk4Day2() != null && newTekCalendarVO2.getWk4Day2().compareTo(newTekCalendarVO.getWk4Day2()) != 0){
                    newTekCalendarVO.setWk4Day2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk4Day3() != null && newTekCalendarVO.getWk4Day3() != null && newTekCalendarVO2.getWk4Day3().compareTo(newTekCalendarVO.getWk4Day3()) != 0){
                    newTekCalendarVO.setWk4Day3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk4Day4() != null && newTekCalendarVO.getWk4Day4() != null && newTekCalendarVO2.getWk4Day4().compareTo(newTekCalendarVO.getWk4Day4()) != 0){
                    newTekCalendarVO.setWk4Day4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk4Day5() != null && newTekCalendarVO.getWk4Day5() != null && newTekCalendarVO2.getWk4Day5().compareTo(newTekCalendarVO.getWk4Day5()) != 0){
                    newTekCalendarVO.setWk4Day5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk4Day6() != null && newTekCalendarVO.getWk4Day6() != null && newTekCalendarVO2.getWk4Day6().compareTo(newTekCalendarVO.getWk4Day6()) != 0){
                    newTekCalendarVO.setWk4Day6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk4Day7() != null && newTekCalendarVO.getWk4Day7() != null && newTekCalendarVO2.getWk4Day7().compareTo(newTekCalendarVO.getWk4Day7()) != 0){
                    newTekCalendarVO.setWk4Day7Status(tekConstants.WK_STATUS_ERROR);
                }

                if(newTekCalendarVO2.getWk5Day1() != null && newTekCalendarVO.getWk5Day1() != null && newTekCalendarVO2.getWk5Day1().compareTo(newTekCalendarVO.getWk5Day1()) != 0){
                    newTekCalendarVO.setWk5Day1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk5Day2() != null && newTekCalendarVO.getWk5Day2() != null && newTekCalendarVO2.getWk5Day2().compareTo(newTekCalendarVO.getWk5Day2()) != 0){
                    newTekCalendarVO.setWk5Day2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk5Day3() != null && newTekCalendarVO.getWk5Day3() != null && newTekCalendarVO2.getWk5Day3().compareTo(newTekCalendarVO.getWk5Day3()) != 0){
                    newTekCalendarVO.setWk5Day3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk5Day4() != null && newTekCalendarVO.getWk5Day4() != null && newTekCalendarVO2.getWk5Day4().compareTo(newTekCalendarVO.getWk5Day4()) != 0){
                    newTekCalendarVO.setWk5Day4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk5Day5() != null && newTekCalendarVO.getWk5Day5() != null && newTekCalendarVO2.getWk5Day5().compareTo(newTekCalendarVO.getWk5Day5()) != 0){
                    newTekCalendarVO.setWk5Day5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk5Day6() != null && newTekCalendarVO.getWk5Day6() != null && newTekCalendarVO2.getWk5Day6().compareTo(newTekCalendarVO.getWk5Day6()) != 0){
                    newTekCalendarVO.setWk5Day6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk5Day7() != null && newTekCalendarVO.getWk5Day7() != null && newTekCalendarVO2.getWk5Day7().compareTo(newTekCalendarVO.getWk5Day7()) != 0){
                    newTekCalendarVO.setWk5Day7Status(tekConstants.WK_STATUS_ERROR);
                }

                if(newTekCalendarVO2.getWk6Day1() != null && newTekCalendarVO.getWk6Day1() != null && newTekCalendarVO2.getWk6Day1().compareTo(newTekCalendarVO.getWk6Day1()) != 0){
                    newTekCalendarVO.setWk6Day1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk6Day2() != null && newTekCalendarVO.getWk6Day2() != null && newTekCalendarVO2.getWk6Day2().compareTo(newTekCalendarVO.getWk6Day2()) != 0){
                    newTekCalendarVO.setWk6Day2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk6Day3() != null && newTekCalendarVO.getWk6Day3() != null && newTekCalendarVO2.getWk6Day3().compareTo(newTekCalendarVO.getWk6Day3()) != 0){
                    newTekCalendarVO.setWk6Day3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk6Day4() != null && newTekCalendarVO.getWk6Day4() != null && newTekCalendarVO2.getWk6Day4().compareTo(newTekCalendarVO.getWk6Day4()) != 0){
                    newTekCalendarVO.setWk6Day4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk6Day5() != null && newTekCalendarVO.getWk6Day5() != null && newTekCalendarVO2.getWk6Day5().compareTo(newTekCalendarVO.getWk6Day5()) != 0){
                    newTekCalendarVO.setWk6Day5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk6Day6() != null && newTekCalendarVO.getWk6Day6() != null && newTekCalendarVO2.getWk6Day6().compareTo(newTekCalendarVO.getWk6Day6()) != 0){
                    newTekCalendarVO.setWk6Day6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk6Day7() != null && newTekCalendarVO.getWk6Day7() != null && newTekCalendarVO2.getWk6Day7().compareTo(newTekCalendarVO.getWk6Day7()) != 0){
                    newTekCalendarVO.setWk6Day7Status(tekConstants.WK_STATUS_ERROR);
                }

                if(newTekCalendarVO2.getWk7Day1() != null && newTekCalendarVO.getWk7Day1() != null && newTekCalendarVO2.getWk7Day1().compareTo(newTekCalendarVO.getWk7Day1()) != 0){
                    newTekCalendarVO.setWk7Day1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk7Day2() != null && newTekCalendarVO.getWk7Day2() != null && newTekCalendarVO2.getWk7Day2().compareTo(newTekCalendarVO.getWk7Day2()) != 0){
                    newTekCalendarVO.setWk7Day2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk7Day3() != null && newTekCalendarVO.getWk7Day3() != null && newTekCalendarVO2.getWk7Day3().compareTo(newTekCalendarVO.getWk7Day3()) != 0){
                    newTekCalendarVO.setWk7Day3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk7Day4() != null && newTekCalendarVO.getWk7Day4() != null && newTekCalendarVO2.getWk7Day4().compareTo(newTekCalendarVO.getWk7Day4()) != 0){
                    newTekCalendarVO.setWk7Day4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk7Day5() != null && newTekCalendarVO.getWk7Day5() != null && newTekCalendarVO2.getWk7Day5().compareTo(newTekCalendarVO.getWk7Day5()) != 0){
                    newTekCalendarVO.setWk7Day5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk7Day6() != null && newTekCalendarVO.getWk7Day6() != null && newTekCalendarVO2.getWk7Day6().compareTo(newTekCalendarVO.getWk7Day6()) != 0){
                    newTekCalendarVO.setWk7Day6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk7Day7() != null && newTekCalendarVO.getWk7Day7() != null && newTekCalendarVO2.getWk7Day7().compareTo(newTekCalendarVO.getWk7Day7()) != 0){
                    newTekCalendarVO.setWk7Day7Status(tekConstants.WK_STATUS_ERROR);
                }

                if(newTekCalendarVO2.getWk8Day1() != null && newTekCalendarVO.getWk8Day1() != null && newTekCalendarVO2.getWk8Day1().compareTo(newTekCalendarVO.getWk8Day1()) != 0){
                    newTekCalendarVO.setWk8Day1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk8Day2() != null && newTekCalendarVO.getWk8Day2() != null && newTekCalendarVO2.getWk8Day2().compareTo(newTekCalendarVO.getWk8Day2()) != 0){
                    newTekCalendarVO.setWk8Day2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk8Day3() != null && newTekCalendarVO.getWk8Day3() != null && newTekCalendarVO2.getWk8Day3().compareTo(newTekCalendarVO.getWk8Day3()) != 0){
                    newTekCalendarVO.setWk8Day3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk8Day4() != null && newTekCalendarVO.getWk8Day4() != null && newTekCalendarVO2.getWk8Day4().compareTo(newTekCalendarVO.getWk8Day4()) != 0){
                    newTekCalendarVO.setWk8Day4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk8Day5() != null && newTekCalendarVO.getWk8Day5() != null && newTekCalendarVO2.getWk8Day5().compareTo(newTekCalendarVO.getWk8Day5()) != 0){
                    newTekCalendarVO.setWk8Day5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk8Day6() != null && newTekCalendarVO.getWk8Day6() != null && newTekCalendarVO2.getWk8Day6().compareTo(newTekCalendarVO.getWk8Day6()) != 0){
                    newTekCalendarVO.setWk8Day6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk8Day7() != null && newTekCalendarVO.getWk8Day7() != null && newTekCalendarVO2.getWk8Day7().compareTo(newTekCalendarVO.getWk8Day7()) != 0){
                    newTekCalendarVO.setWk8Day7Status(tekConstants.WK_STATUS_ERROR);
                }

                if(newTekCalendarVO2.getWk9Day1() != null && newTekCalendarVO.getWk9Day1() != null && newTekCalendarVO2.getWk9Day1().compareTo(newTekCalendarVO.getWk9Day1()) != 0){
                    newTekCalendarVO.setWk9Day1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk9Day2() != null && newTekCalendarVO.getWk9Day2() != null && newTekCalendarVO2.getWk9Day2().compareTo(newTekCalendarVO.getWk9Day2()) != 0){
                    newTekCalendarVO.setWk9Day2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk9Day3() != null && newTekCalendarVO.getWk9Day3() != null && newTekCalendarVO2.getWk9Day3().compareTo(newTekCalendarVO.getWk9Day3()) != 0){
                    newTekCalendarVO.setWk9Day3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk9Day4() != null && newTekCalendarVO.getWk9Day4() != null && newTekCalendarVO2.getWk9Day4().compareTo(newTekCalendarVO.getWk9Day4()) != 0){
                    newTekCalendarVO.setWk9Day4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk9Day5() != null && newTekCalendarVO.getWk9Day5() != null && newTekCalendarVO2.getWk9Day5().compareTo(newTekCalendarVO.getWk9Day5()) != 0){
                    newTekCalendarVO.setWk9Day5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk9Day6() != null && newTekCalendarVO.getWk9Day6() != null && newTekCalendarVO2.getWk9Day6().compareTo(newTekCalendarVO.getWk9Day6()) != 0){
                    newTekCalendarVO.setWk9Day6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk9Day7() != null && newTekCalendarVO.getWk9Day7() != null && newTekCalendarVO2.getWk9Day7().compareTo(newTekCalendarVO.getWk9Day7()) != 0){
                    newTekCalendarVO.setWk9Day7Status(tekConstants.WK_STATUS_ERROR);
                }

                if(newTekCalendarVO2.getWk10Day1() != null && newTekCalendarVO.getWk10Day1() != null && newTekCalendarVO2.getWk10Day1().compareTo(newTekCalendarVO.getWk10Day1()) != 0){
                    newTekCalendarVO.setWk10Day1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk10Day2() != null && newTekCalendarVO.getWk10Day2() != null && newTekCalendarVO2.getWk10Day2().compareTo(newTekCalendarVO.getWk10Day2()) != 0){
                    newTekCalendarVO.setWk10Day2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk10Day3() != null && newTekCalendarVO.getWk10Day3() != null && newTekCalendarVO2.getWk10Day3().compareTo(newTekCalendarVO.getWk10Day3()) != 0){
                    newTekCalendarVO.setWk10Day3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk10Day4() != null && newTekCalendarVO.getWk10Day4() != null && newTekCalendarVO2.getWk10Day4().compareTo(newTekCalendarVO.getWk10Day4()) != 0){
                    newTekCalendarVO.setWk10Day4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk10Day5() != null && newTekCalendarVO.getWk10Day5() != null && newTekCalendarVO2.getWk10Day5().compareTo(newTekCalendarVO.getWk10Day5()) != 0){
                    newTekCalendarVO.setWk10Day5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk10Day6() != null && newTekCalendarVO.getWk10Day6() != null && newTekCalendarVO2.getWk10Day6().compareTo(newTekCalendarVO.getWk10Day6()) != 0){
                    newTekCalendarVO.setWk10Day6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk10Day7() != null && newTekCalendarVO.getWk10Day7() != null && newTekCalendarVO2.getWk10Day7().compareTo(newTekCalendarVO.getWk10Day7()) != 0){
                    newTekCalendarVO.setWk10Day7Status(tekConstants.WK_STATUS_ERROR);
                }

                if(newTekCalendarVO2.getWk11Day1() != null && newTekCalendarVO.getWk11Day1() != null && newTekCalendarVO2.getWk11Day1().compareTo(newTekCalendarVO.getWk11Day1()) != 0){
                    newTekCalendarVO.setWk11Day1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk11Day2() != null && newTekCalendarVO.getWk11Day2() != null && newTekCalendarVO2.getWk11Day2().compareTo(newTekCalendarVO.getWk11Day2()) != 0){
                    newTekCalendarVO.setWk11Day2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk11Day3() != null && newTekCalendarVO.getWk11Day3() != null && newTekCalendarVO2.getWk11Day3().compareTo(newTekCalendarVO.getWk11Day3()) != 0){
                    newTekCalendarVO.setWk11Day3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk11Day4() != null && newTekCalendarVO.getWk11Day4() != null && newTekCalendarVO2.getWk11Day4().compareTo(newTekCalendarVO.getWk11Day4()) != 0){
                    newTekCalendarVO.setWk11Day4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk11Day5() != null && newTekCalendarVO.getWk11Day5() != null && newTekCalendarVO2.getWk11Day5().compareTo(newTekCalendarVO.getWk11Day5()) != 0){
                    newTekCalendarVO.setWk11Day5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk11Day6() != null && newTekCalendarVO.getWk11Day6() != null && newTekCalendarVO2.getWk11Day6().compareTo(newTekCalendarVO.getWk11Day6()) != 0){
                    newTekCalendarVO.setWk11Day6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk11Day7() != null && newTekCalendarVO.getWk11Day7() != null && newTekCalendarVO2.getWk11Day7().compareTo(newTekCalendarVO.getWk11Day7()) != 0){
                    newTekCalendarVO.setWk11Day7Status(tekConstants.WK_STATUS_ERROR);
                }

                if(newTekCalendarVO2.getWk12Day1() != null && newTekCalendarVO.getWk12Day1() != null && newTekCalendarVO2.getWk12Day1().compareTo(newTekCalendarVO.getWk12Day1()) != 0){
                    newTekCalendarVO.setWk12Day1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk12Day2() != null && newTekCalendarVO.getWk12Day2() != null && newTekCalendarVO2.getWk12Day2().compareTo(newTekCalendarVO.getWk12Day2()) != 0){
                    newTekCalendarVO.setWk12Day2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk12Day3() != null && newTekCalendarVO.getWk12Day3() != null && newTekCalendarVO2.getWk12Day3().compareTo(newTekCalendarVO.getWk12Day3()) != 0){
                    newTekCalendarVO.setWk12Day3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk12Day4() != null && newTekCalendarVO.getWk12Day4() != null && newTekCalendarVO2.getWk12Day4().compareTo(newTekCalendarVO.getWk12Day4()) != 0){
                    newTekCalendarVO.setWk12Day4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk12Day5() != null && newTekCalendarVO.getWk12Day5() != null && newTekCalendarVO2.getWk12Day5().compareTo(newTekCalendarVO.getWk12Day5()) != 0){
                    newTekCalendarVO.setWk12Day5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk12Day6() != null && newTekCalendarVO.getWk12Day6() != null && newTekCalendarVO2.getWk12Day6().compareTo(newTekCalendarVO.getWk12Day6()) != 0){
                    newTekCalendarVO.setWk12Day6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk12Day7() != null && newTekCalendarVO.getWk12Day7() != null && newTekCalendarVO2.getWk12Day7().compareTo(newTekCalendarVO.getWk12Day7()) != 0){
                    newTekCalendarVO.setWk12Day7Status(tekConstants.WK_STATUS_ERROR);
                }

                if(newTekCalendarVO2.getWk13Day1() != null && newTekCalendarVO.getWk13Day1() != null && newTekCalendarVO2.getWk13Day1().compareTo(newTekCalendarVO.getWk13Day1()) != 0){
                    newTekCalendarVO.setWk13Day1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk13Day2() != null && newTekCalendarVO.getWk13Day2() != null && newTekCalendarVO2.getWk13Day2().compareTo(newTekCalendarVO.getWk13Day2()) != 0){
                    newTekCalendarVO.setWk13Day2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk13Day3() != null && newTekCalendarVO.getWk13Day3() != null && newTekCalendarVO2.getWk13Day3().compareTo(newTekCalendarVO.getWk13Day3()) != 0){
                    newTekCalendarVO.setWk13Day3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk13Day4() != null && newTekCalendarVO.getWk13Day4() != null && newTekCalendarVO2.getWk13Day4().compareTo(newTekCalendarVO.getWk13Day4()) != 0){
                    newTekCalendarVO.setWk13Day4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk13Day5() != null && newTekCalendarVO.getWk13Day5() != null && newTekCalendarVO2.getWk13Day5().compareTo(newTekCalendarVO.getWk13Day5()) != 0){
                    newTekCalendarVO.setWk13Day5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk13Day6() != null && newTekCalendarVO.getWk13Day6() != null && newTekCalendarVO2.getWk13Day6().compareTo(newTekCalendarVO.getWk13Day6()) != 0){
                    newTekCalendarVO.setWk13Day6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk13Day7() != null && newTekCalendarVO.getWk13Day7() != null && newTekCalendarVO2.getWk13Day7().compareTo(newTekCalendarVO.getWk13Day7()) != 0){
                    newTekCalendarVO.setWk13Day7Status(tekConstants.WK_STATUS_ERROR);
                }

                if(newTekCalendarVO2.getWk14Day1() != null && newTekCalendarVO.getWk14Day1() != null && newTekCalendarVO2.getWk14Day1().compareTo(newTekCalendarVO.getWk14Day1()) != 0){
                    newTekCalendarVO.setWk14Day1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk14Day2() != null && newTekCalendarVO.getWk14Day2() != null && newTekCalendarVO2.getWk14Day2().compareTo(newTekCalendarVO.getWk14Day2()) != 0){
                    newTekCalendarVO.setWk14Day2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk14Day3() != null && newTekCalendarVO.getWk14Day3() != null && newTekCalendarVO2.getWk14Day3().compareTo(newTekCalendarVO.getWk14Day3()) != 0){
                    newTekCalendarVO.setWk14Day3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk14Day4() != null && newTekCalendarVO.getWk14Day4() != null && newTekCalendarVO2.getWk14Day4().compareTo(newTekCalendarVO.getWk14Day4()) != 0){
                    newTekCalendarVO.setWk14Day4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk14Day5() != null && newTekCalendarVO.getWk14Day5() != null && newTekCalendarVO2.getWk14Day5().compareTo(newTekCalendarVO.getWk14Day5()) != 0){
                    newTekCalendarVO.setWk14Day5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk14Day6() != null && newTekCalendarVO.getWk14Day6() != null && newTekCalendarVO2.getWk14Day6().compareTo(newTekCalendarVO.getWk14Day6()) != 0){
                    newTekCalendarVO.setWk14Day6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk14Day7() != null && newTekCalendarVO.getWk14Day7() != null && newTekCalendarVO2.getWk14Day7().compareTo(newTekCalendarVO.getWk14Day7()) != 0){
                    newTekCalendarVO.setWk14Day7Status(tekConstants.WK_STATUS_ERROR);
                }

                if(newTekCalendarVO2.getWk15Day1() != null && newTekCalendarVO.getWk15Day1() != null && newTekCalendarVO2.getWk15Day1().compareTo(newTekCalendarVO.getWk15Day1()) != 0){
                    newTekCalendarVO.setWk15Day1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk15Day2() != null && newTekCalendarVO.getWk15Day2() != null && newTekCalendarVO2.getWk15Day2().compareTo(newTekCalendarVO.getWk15Day2()) != 0){
                    newTekCalendarVO.setWk15Day2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk15Day3() != null && newTekCalendarVO.getWk15Day3() != null && newTekCalendarVO2.getWk15Day3().compareTo(newTekCalendarVO.getWk15Day3()) != 0){
                    newTekCalendarVO.setWk15Day3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk15Day4() != null && newTekCalendarVO.getWk15Day4() != null && newTekCalendarVO2.getWk15Day4().compareTo(newTekCalendarVO.getWk15Day4()) != 0){
                    newTekCalendarVO.setWk15Day4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk15Day5() != null && newTekCalendarVO.getWk15Day5() != null && newTekCalendarVO2.getWk15Day5().compareTo(newTekCalendarVO.getWk15Day5()) != 0){
                    newTekCalendarVO.setWk15Day5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk15Day6() != null && newTekCalendarVO.getWk15Day6() != null && newTekCalendarVO2.getWk15Day6().compareTo(newTekCalendarVO.getWk15Day6()) != 0){
                    newTekCalendarVO.setWk15Day6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk15Day7() != null && newTekCalendarVO.getWk15Day7() != null && newTekCalendarVO2.getWk15Day7().compareTo(newTekCalendarVO.getWk15Day7()) != 0){
                    newTekCalendarVO.setWk15Day7Status(tekConstants.WK_STATUS_ERROR);
                }

                if(newTekCalendarVO2.getWk16Day1() != null && newTekCalendarVO.getWk16Day1() != null && newTekCalendarVO2.getWk16Day1().compareTo(newTekCalendarVO.getWk16Day1()) != 0){
                    newTekCalendarVO.setWk16Day1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk16Day2() != null && newTekCalendarVO.getWk16Day2() != null && newTekCalendarVO2.getWk16Day2().compareTo(newTekCalendarVO.getWk16Day2()) != 0){
                    newTekCalendarVO.setWk16Day2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk16Day3() != null && newTekCalendarVO.getWk16Day3() != null && newTekCalendarVO2.getWk16Day3().compareTo(newTekCalendarVO.getWk16Day3()) != 0){
                    newTekCalendarVO.setWk16Day3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk16Day4() != null && newTekCalendarVO.getWk16Day4() != null && newTekCalendarVO2.getWk16Day4().compareTo(newTekCalendarVO.getWk16Day4()) != 0){
                    newTekCalendarVO.setWk16Day4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk16Day5() != null && newTekCalendarVO.getWk16Day5() != null && newTekCalendarVO2.getWk16Day5().compareTo(newTekCalendarVO.getWk16Day5()) != 0){
                    newTekCalendarVO.setWk16Day5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk16Day6() != null && newTekCalendarVO.getWk16Day6() != null && newTekCalendarVO2.getWk16Day6().compareTo(newTekCalendarVO.getWk16Day6()) != 0){
                    newTekCalendarVO.setWk16Day6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk16Day7() != null && newTekCalendarVO.getWk16Day7() != null && newTekCalendarVO2.getWk16Day7().compareTo(newTekCalendarVO.getWk16Day7()) != 0){
                    newTekCalendarVO.setWk16Day7Status(tekConstants.WK_STATUS_ERROR);
                }

                if(newTekCalendarVO2.getWk17Day1() != null && newTekCalendarVO.getWk17Day1() != null && newTekCalendarVO2.getWk17Day1().compareTo(newTekCalendarVO.getWk17Day1()) != 0){
                    newTekCalendarVO.setWk17Day1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk17Day2() != null && newTekCalendarVO.getWk17Day2() != null && newTekCalendarVO2.getWk17Day2().compareTo(newTekCalendarVO.getWk17Day2()) != 0){
                    newTekCalendarVO.setWk17Day2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk17Day3() != null && newTekCalendarVO.getWk17Day3() != null && newTekCalendarVO2.getWk17Day3().compareTo(newTekCalendarVO.getWk17Day3()) != 0){
                    newTekCalendarVO.setWk17Day3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk17Day4() != null && newTekCalendarVO.getWk17Day4() != null && newTekCalendarVO2.getWk17Day4().compareTo(newTekCalendarVO.getWk17Day4()) != 0){
                    newTekCalendarVO.setWk17Day4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk17Day5() != null && newTekCalendarVO.getWk17Day5() != null && newTekCalendarVO2.getWk17Day5().compareTo(newTekCalendarVO.getWk17Day5()) != 0){
                    newTekCalendarVO.setWk17Day5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk17Day6() != null && newTekCalendarVO.getWk17Day6() != null && newTekCalendarVO2.getWk17Day6().compareTo(newTekCalendarVO.getWk17Day6()) != 0){
                    newTekCalendarVO.setWk17Day6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk17Day7() != null && newTekCalendarVO.getWk17Day7() != null && newTekCalendarVO2.getWk17Day7().compareTo(newTekCalendarVO.getWk17Day7()) != 0){
                    newTekCalendarVO.setWk17Day7Status(tekConstants.WK_STATUS_ERROR);
                }

                if(newTekCalendarVO2.getWk19Day1() != null && newTekCalendarVO.getWk19Day1() != null && newTekCalendarVO2.getWk19Day1().compareTo(newTekCalendarVO.getWk19Day1()) != 0){
                    newTekCalendarVO.setWk19Day1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk19Day2() != null && newTekCalendarVO.getWk19Day2() != null && newTekCalendarVO2.getWk19Day2().compareTo(newTekCalendarVO.getWk19Day2()) != 0){
                    newTekCalendarVO.setWk19Day2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk19Day3() != null && newTekCalendarVO.getWk19Day3() != null && newTekCalendarVO2.getWk19Day3().compareTo(newTekCalendarVO.getWk19Day3()) != 0){
                    newTekCalendarVO.setWk19Day3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk19Day4() != null && newTekCalendarVO.getWk19Day4() != null && newTekCalendarVO2.getWk19Day4().compareTo(newTekCalendarVO.getWk19Day4()) != 0){
                    newTekCalendarVO.setWk19Day4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk19Day5() != null && newTekCalendarVO.getWk19Day5() != null && newTekCalendarVO2.getWk19Day5().compareTo(newTekCalendarVO.getWk19Day5()) != 0){
                    newTekCalendarVO.setWk19Day5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk19Day6() != null && newTekCalendarVO.getWk19Day6() != null && newTekCalendarVO2.getWk19Day6().compareTo(newTekCalendarVO.getWk19Day6()) != 0){
                    newTekCalendarVO.setWk19Day6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk19Day7() != null && newTekCalendarVO.getWk19Day7() != null && newTekCalendarVO2.getWk19Day7().compareTo(newTekCalendarVO.getWk19Day7()) != 0){
                    newTekCalendarVO.setWk19Day7Status(tekConstants.WK_STATUS_ERROR);
                }

                if(newTekCalendarVO2.getWk20Day1() != null && newTekCalendarVO.getWk20Day1() != null && newTekCalendarVO2.getWk20Day1().compareTo(newTekCalendarVO.getWk20Day1()) != 0){
                    newTekCalendarVO.setWk20Day1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk20Day2() != null && newTekCalendarVO.getWk20Day2() != null && newTekCalendarVO2.getWk20Day2().compareTo(newTekCalendarVO.getWk20Day2()) != 0){
                    newTekCalendarVO.setWk20Day2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk20Day3() != null && newTekCalendarVO.getWk20Day3() != null && newTekCalendarVO2.getWk20Day3().compareTo(newTekCalendarVO.getWk20Day3()) != 0){
                    newTekCalendarVO.setWk20Day3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk20Day4() != null && newTekCalendarVO.getWk20Day4() != null && newTekCalendarVO2.getWk20Day4().compareTo(newTekCalendarVO.getWk20Day4()) != 0){
                    newTekCalendarVO.setWk20Day4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk20Day5() != null && newTekCalendarVO.getWk20Day5() != null && newTekCalendarVO2.getWk20Day5().compareTo(newTekCalendarVO.getWk20Day5()) != 0){
                    newTekCalendarVO.setWk20Day5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk20Day6() != null && newTekCalendarVO.getWk20Day6() != null && newTekCalendarVO2.getWk20Day6().compareTo(newTekCalendarVO.getWk20Day6()) != 0){
                    newTekCalendarVO.setWk20Day6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk20Day7() != null && newTekCalendarVO.getWk20Day7() != null && newTekCalendarVO2.getWk20Day7().compareTo(newTekCalendarVO.getWk20Day7()) != 0){
                    newTekCalendarVO.setWk20Day7Status(tekConstants.WK_STATUS_ERROR);
                }

                if(newTekCalendarVO2.getWk21Day1() != null && newTekCalendarVO.getWk21Day1() != null && newTekCalendarVO2.getWk21Day1().compareTo(newTekCalendarVO.getWk21Day1()) != 0){
                    newTekCalendarVO.setWk21Day1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk21Day2() != null && newTekCalendarVO.getWk21Day2() != null && newTekCalendarVO2.getWk21Day2().compareTo(newTekCalendarVO.getWk21Day2()) != 0){
                    newTekCalendarVO.setWk21Day2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk21Day3() != null && newTekCalendarVO.getWk21Day3() != null && newTekCalendarVO2.getWk21Day3().compareTo(newTekCalendarVO.getWk21Day3()) != 0){
                    newTekCalendarVO.setWk21Day3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk21Day4() != null && newTekCalendarVO.getWk21Day4() != null && newTekCalendarVO2.getWk21Day4().compareTo(newTekCalendarVO.getWk21Day4()) != 0){
                    newTekCalendarVO.setWk21Day4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk21Day5() != null && newTekCalendarVO.getWk21Day5() != null && newTekCalendarVO2.getWk21Day5().compareTo(newTekCalendarVO.getWk21Day5()) != 0){
                    newTekCalendarVO.setWk21Day5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk21Day6() != null && newTekCalendarVO.getWk21Day6() != null && newTekCalendarVO2.getWk21Day6().compareTo(newTekCalendarVO.getWk21Day6()) != 0){
                    newTekCalendarVO.setWk21Day6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk21Day7() != null && newTekCalendarVO.getWk21Day7() != null && newTekCalendarVO2.getWk21Day7().compareTo(newTekCalendarVO.getWk21Day7()) != 0){
                    newTekCalendarVO.setWk21Day7Status(tekConstants.WK_STATUS_ERROR);
                }

                if(newTekCalendarVO2.getWk22Day1() != null && newTekCalendarVO.getWk22Day1() != null && newTekCalendarVO2.getWk22Day1().compareTo(newTekCalendarVO.getWk22Day1()) != 0){
                    newTekCalendarVO.setWk22Day1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk22Day2() != null && newTekCalendarVO.getWk22Day2() != null && newTekCalendarVO2.getWk22Day2().compareTo(newTekCalendarVO.getWk22Day2()) != 0){
                    newTekCalendarVO.setWk22Day2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk22Day3() != null && newTekCalendarVO.getWk22Day3() != null && newTekCalendarVO2.getWk22Day3().compareTo(newTekCalendarVO.getWk22Day3()) != 0){
                    newTekCalendarVO.setWk22Day3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk22Day4() != null && newTekCalendarVO.getWk22Day4() != null && newTekCalendarVO2.getWk22Day4().compareTo(newTekCalendarVO.getWk22Day4()) != 0){
                    newTekCalendarVO.setWk22Day4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk22Day5() != null && newTekCalendarVO.getWk22Day5() != null && newTekCalendarVO2.getWk22Day5().compareTo(newTekCalendarVO.getWk22Day5()) != 0){
                    newTekCalendarVO.setWk22Day5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk22Day6() != null && newTekCalendarVO.getWk22Day6() != null && newTekCalendarVO2.getWk22Day6().compareTo(newTekCalendarVO.getWk22Day6()) != 0){
                    newTekCalendarVO.setWk22Day6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk22Day7() != null && newTekCalendarVO.getWk22Day7() != null && newTekCalendarVO2.getWk22Day7().compareTo(newTekCalendarVO.getWk22Day7()) != 0){
                    newTekCalendarVO.setWk22Day7Status(tekConstants.WK_STATUS_ERROR);
                }

                if(newTekCalendarVO2.getWk23Day1() != null && newTekCalendarVO.getWk23Day1() != null && newTekCalendarVO2.getWk23Day1().compareTo(newTekCalendarVO.getWk23Day1()) != 0){
                    newTekCalendarVO.setWk23Day1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk23Day2() != null && newTekCalendarVO.getWk23Day2() != null && newTekCalendarVO2.getWk23Day2().compareTo(newTekCalendarVO.getWk23Day2()) != 0){
                    newTekCalendarVO.setWk23Day2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk23Day3() != null && newTekCalendarVO.getWk23Day3() != null && newTekCalendarVO2.getWk23Day3().compareTo(newTekCalendarVO.getWk23Day3()) != 0){
                    newTekCalendarVO.setWk23Day3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk23Day4() != null && newTekCalendarVO.getWk23Day4() != null && newTekCalendarVO2.getWk23Day4().compareTo(newTekCalendarVO.getWk23Day4()) != 0){
                    newTekCalendarVO.setWk23Day4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk23Day5() != null && newTekCalendarVO.getWk23Day5() != null && newTekCalendarVO2.getWk23Day5().compareTo(newTekCalendarVO.getWk23Day5()) != 0){
                    newTekCalendarVO.setWk23Day5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk23Day6() != null && newTekCalendarVO.getWk23Day6() != null && newTekCalendarVO2.getWk23Day6().compareTo(newTekCalendarVO.getWk23Day6()) != 0){
                    newTekCalendarVO.setWk23Day6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk23Day7() != null && newTekCalendarVO.getWk23Day7() != null && newTekCalendarVO2.getWk23Day7().compareTo(newTekCalendarVO.getWk23Day7()) != 0){
                    newTekCalendarVO.setWk23Day7Status(tekConstants.WK_STATUS_ERROR);
                }

                if(newTekCalendarVO2.getWk24Day1() != null && newTekCalendarVO.getWk24Day1() != null && newTekCalendarVO2.getWk24Day1().compareTo(newTekCalendarVO.getWk24Day1()) != 0){
                    newTekCalendarVO.setWk24Day1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk24Day2() != null && newTekCalendarVO.getWk24Day2() != null && newTekCalendarVO2.getWk24Day2().compareTo(newTekCalendarVO.getWk24Day2()) != 0){
                    newTekCalendarVO.setWk24Day2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk24Day3() != null && newTekCalendarVO.getWk24Day3() != null && newTekCalendarVO2.getWk24Day3().compareTo(newTekCalendarVO.getWk24Day3()) != 0){
                    newTekCalendarVO.setWk24Day3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk24Day4() != null && newTekCalendarVO.getWk24Day4() != null && newTekCalendarVO2.getWk24Day4().compareTo(newTekCalendarVO.getWk24Day4()) != 0){
                    newTekCalendarVO.setWk24Day4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk24Day5() != null && newTekCalendarVO.getWk24Day5() != null && newTekCalendarVO2.getWk24Day5().compareTo(newTekCalendarVO.getWk24Day5()) != 0){
                    newTekCalendarVO.setWk24Day5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk24Day6() != null && newTekCalendarVO.getWk24Day6() != null && newTekCalendarVO2.getWk24Day6().compareTo(newTekCalendarVO.getWk24Day6()) != 0){
                    newTekCalendarVO.setWk24Day6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk24Day7() != null && newTekCalendarVO.getWk24Day7() != null && newTekCalendarVO2.getWk24Day7().compareTo(newTekCalendarVO.getWk24Day7()) != 0){
                    newTekCalendarVO.setWk24Day7Status(tekConstants.WK_STATUS_ERROR);
                }

                if(newTekCalendarVO2.getWk25Day1() != null && newTekCalendarVO.getWk25Day1() != null && newTekCalendarVO2.getWk25Day1().compareTo(newTekCalendarVO.getWk25Day1()) != 0){
                    newTekCalendarVO.setWk25Day1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk25Day2() != null && newTekCalendarVO.getWk25Day2() != null && newTekCalendarVO2.getWk25Day2().compareTo(newTekCalendarVO.getWk25Day2()) != 0){
                    newTekCalendarVO.setWk25Day2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk25Day3() != null && newTekCalendarVO.getWk25Day3() != null && newTekCalendarVO2.getWk25Day3().compareTo(newTekCalendarVO.getWk25Day3()) != 0){
                    newTekCalendarVO.setWk25Day3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk25Day4() != null && newTekCalendarVO.getWk25Day4() != null && newTekCalendarVO2.getWk25Day4().compareTo(newTekCalendarVO.getWk25Day4()) != 0){
                    newTekCalendarVO.setWk25Day4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk25Day5() != null && newTekCalendarVO.getWk25Day5() != null && newTekCalendarVO2.getWk25Day5().compareTo(newTekCalendarVO.getWk25Day5()) != 0){
                    newTekCalendarVO.setWk25Day5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk25Day6() != null && newTekCalendarVO.getWk25Day6() != null && newTekCalendarVO2.getWk25Day6().compareTo(newTekCalendarVO.getWk25Day6()) != 0){
                    newTekCalendarVO.setWk25Day6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk25Day7() != null && newTekCalendarVO.getWk25Day7() != null && newTekCalendarVO2.getWk25Day7().compareTo(newTekCalendarVO.getWk25Day7()) != 0){
                    newTekCalendarVO.setWk25Day7Status(tekConstants.WK_STATUS_ERROR);
                }

                if(newTekCalendarVO2.getWk26Day1() != null && newTekCalendarVO.getWk26Day1() != null && newTekCalendarVO2.getWk26Day1().compareTo(newTekCalendarVO.getWk26Day1()) != 0){
                    newTekCalendarVO.setWk26Day1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk26Day2() != null && newTekCalendarVO.getWk26Day2() != null && newTekCalendarVO2.getWk26Day2().compareTo(newTekCalendarVO.getWk26Day2()) != 0){
                    newTekCalendarVO.setWk26Day2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk26Day3() != null && newTekCalendarVO.getWk26Day3() != null && newTekCalendarVO2.getWk26Day3().compareTo(newTekCalendarVO.getWk26Day3()) != 0){
                    newTekCalendarVO.setWk26Day3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk26Day4() != null && newTekCalendarVO.getWk26Day4() != null && newTekCalendarVO2.getWk26Day4().compareTo(newTekCalendarVO.getWk26Day4()) != 0){
                    newTekCalendarVO.setWk26Day4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk26Day5() != null && newTekCalendarVO.getWk26Day5() != null && newTekCalendarVO2.getWk26Day5().compareTo(newTekCalendarVO.getWk26Day5()) != 0){
                    newTekCalendarVO.setWk26Day5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk26Day6() != null && newTekCalendarVO.getWk26Day6() != null && newTekCalendarVO2.getWk26Day6().compareTo(newTekCalendarVO.getWk26Day6()) != 0){
                    newTekCalendarVO.setWk26Day6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk26Day7() != null && newTekCalendarVO.getWk26Day7() != null && newTekCalendarVO2.getWk26Day7().compareTo(newTekCalendarVO.getWk26Day7()) != 0){
                    newTekCalendarVO.setWk26Day7Status(tekConstants.WK_STATUS_ERROR);
                }

                if(newTekCalendarVO2.getWk27Day1() != null && newTekCalendarVO.getWk27Day1() != null && newTekCalendarVO2.getWk27Day1().compareTo(newTekCalendarVO.getWk27Day1()) != 0){
                    newTekCalendarVO.setWk27Day1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk27Day2() != null && newTekCalendarVO.getWk27Day2() != null && newTekCalendarVO2.getWk27Day2().compareTo(newTekCalendarVO.getWk27Day2()) != 0){
                    newTekCalendarVO.setWk27Day2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk27Day3() != null && newTekCalendarVO.getWk27Day3() != null && newTekCalendarVO2.getWk27Day3().compareTo(newTekCalendarVO.getWk27Day3()) != 0){
                    newTekCalendarVO.setWk27Day3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk27Day4() != null && newTekCalendarVO.getWk27Day4() != null && newTekCalendarVO2.getWk27Day4().compareTo(newTekCalendarVO.getWk27Day4()) != 0){
                    newTekCalendarVO.setWk27Day4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk27Day5() != null && newTekCalendarVO.getWk27Day5() != null && newTekCalendarVO2.getWk27Day5().compareTo(newTekCalendarVO.getWk27Day5()) != 0){
                    newTekCalendarVO.setWk27Day5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk27Day6() != null && newTekCalendarVO.getWk27Day6() != null && newTekCalendarVO2.getWk27Day6().compareTo(newTekCalendarVO.getWk27Day6()) != 0){
                    newTekCalendarVO.setWk27Day6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk27Day7() != null && newTekCalendarVO.getWk27Day7() != null && newTekCalendarVO2.getWk27Day7().compareTo(newTekCalendarVO.getWk27Day7()) != 0){
                    newTekCalendarVO.setWk27Day7Status(tekConstants.WK_STATUS_ERROR);
                }

                if(newTekCalendarVO2.getWk28Day1() != null && newTekCalendarVO.getWk28Day1() != null && newTekCalendarVO2.getWk28Day1().compareTo(newTekCalendarVO.getWk28Day1()) != 0){
                    newTekCalendarVO.setWk28Day1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk28Day2() != null && newTekCalendarVO.getWk28Day2() != null && newTekCalendarVO2.getWk28Day2().compareTo(newTekCalendarVO.getWk28Day2()) != 0){
                    newTekCalendarVO.setWk28Day2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk28Day3() != null && newTekCalendarVO.getWk28Day3() != null && newTekCalendarVO2.getWk28Day3().compareTo(newTekCalendarVO.getWk28Day3()) != 0){
                    newTekCalendarVO.setWk28Day3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk28Day4() != null && newTekCalendarVO.getWk28Day4() != null && newTekCalendarVO2.getWk28Day4().compareTo(newTekCalendarVO.getWk28Day4()) != 0){
                    newTekCalendarVO.setWk28Day4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk28Day5() != null && newTekCalendarVO.getWk28Day5() != null && newTekCalendarVO2.getWk28Day5().compareTo(newTekCalendarVO.getWk28Day5()) != 0){
                    newTekCalendarVO.setWk28Day5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk28Day6() != null && newTekCalendarVO.getWk28Day6() != null && newTekCalendarVO2.getWk28Day6().compareTo(newTekCalendarVO.getWk28Day6()) != 0){
                    newTekCalendarVO.setWk28Day6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk28Day7() != null && newTekCalendarVO.getWk28Day7() != null && newTekCalendarVO2.getWk28Day7().compareTo(newTekCalendarVO.getWk28Day7()) != 0){
                    newTekCalendarVO.setWk28Day7Status(tekConstants.WK_STATUS_ERROR);
                }

                if(newTekCalendarVO2.getWk29Day1() != null && newTekCalendarVO.getWk29Day1() != null && newTekCalendarVO2.getWk29Day1().compareTo(newTekCalendarVO.getWk29Day1()) != 0){
                    newTekCalendarVO.setWk29Day1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk29Day2() != null && newTekCalendarVO.getWk29Day2() != null && newTekCalendarVO2.getWk29Day2().compareTo(newTekCalendarVO.getWk29Day2()) != 0){
                    newTekCalendarVO.setWk29Day2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk29Day3() != null && newTekCalendarVO.getWk29Day3() != null && newTekCalendarVO2.getWk29Day3().compareTo(newTekCalendarVO.getWk29Day3()) != 0){
                    newTekCalendarVO.setWk29Day3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk29Day4() != null && newTekCalendarVO.getWk29Day4() != null && newTekCalendarVO2.getWk29Day4().compareTo(newTekCalendarVO.getWk29Day4()) != 0){
                    newTekCalendarVO.setWk29Day4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk29Day5() != null && newTekCalendarVO.getWk29Day5() != null && newTekCalendarVO2.getWk29Day5().compareTo(newTekCalendarVO.getWk29Day5()) != 0){
                    newTekCalendarVO.setWk29Day5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk29Day6() != null && newTekCalendarVO.getWk29Day6() != null && newTekCalendarVO2.getWk29Day6().compareTo(newTekCalendarVO.getWk29Day6()) != 0){
                    newTekCalendarVO.setWk29Day6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk29Day7() != null && newTekCalendarVO.getWk29Day7() != null && newTekCalendarVO2.getWk29Day7().compareTo(newTekCalendarVO.getWk29Day7()) != 0){
                    newTekCalendarVO.setWk29Day7Status(tekConstants.WK_STATUS_ERROR);
                }

                if(newTekCalendarVO2.getWk30Day1() != null && newTekCalendarVO.getWk30Day1() != null && newTekCalendarVO2.getWk30Day1().compareTo(newTekCalendarVO.getWk30Day1()) != 0){
                    newTekCalendarVO.setWk30Day1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk30Day2() != null && newTekCalendarVO.getWk30Day2() != null && newTekCalendarVO2.getWk30Day2().compareTo(newTekCalendarVO.getWk30Day2()) != 0){
                    newTekCalendarVO.setWk30Day2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk30Day3() != null && newTekCalendarVO.getWk30Day3() != null && newTekCalendarVO2.getWk30Day3().compareTo(newTekCalendarVO.getWk30Day3()) != 0){
                    newTekCalendarVO.setWk30Day3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk30Day4() != null && newTekCalendarVO.getWk30Day4() != null && newTekCalendarVO2.getWk30Day4().compareTo(newTekCalendarVO.getWk30Day4()) != 0){
                    newTekCalendarVO.setWk30Day4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk30Day5() != null && newTekCalendarVO.getWk30Day5() != null && newTekCalendarVO2.getWk30Day5().compareTo(newTekCalendarVO.getWk30Day5()) != 0){
                    newTekCalendarVO.setWk30Day5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk30Day6() != null && newTekCalendarVO.getWk30Day6() != null && newTekCalendarVO2.getWk30Day6().compareTo(newTekCalendarVO.getWk30Day6()) != 0){
                    newTekCalendarVO.setWk30Day6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk30Day7() != null && newTekCalendarVO.getWk30Day7() != null && newTekCalendarVO2.getWk30Day7().compareTo(newTekCalendarVO.getWk30Day7()) != 0){
                    newTekCalendarVO.setWk30Day7Status(tekConstants.WK_STATUS_ERROR);
                }

                if(newTekCalendarVO2.getWk31Day1() != null && newTekCalendarVO.getWk31Day1() != null && newTekCalendarVO2.getWk31Day1().compareTo(newTekCalendarVO.getWk31Day1()) != 0){
                    newTekCalendarVO.setWk31Day1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk31Day2() != null && newTekCalendarVO.getWk31Day2() != null && newTekCalendarVO2.getWk31Day2().compareTo(newTekCalendarVO.getWk31Day2()) != 0){
                    newTekCalendarVO.setWk31Day2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk31Day3() != null && newTekCalendarVO.getWk31Day3() != null && newTekCalendarVO2.getWk31Day3().compareTo(newTekCalendarVO.getWk31Day3()) != 0){
                    newTekCalendarVO.setWk31Day3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk31Day4() != null && newTekCalendarVO.getWk31Day4() != null && newTekCalendarVO2.getWk31Day4().compareTo(newTekCalendarVO.getWk31Day4()) != 0){
                    newTekCalendarVO.setWk31Day4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk31Day5() != null && newTekCalendarVO.getWk31Day5() != null && newTekCalendarVO2.getWk31Day5().compareTo(newTekCalendarVO.getWk31Day5()) != 0){
                    newTekCalendarVO.setWk31Day5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk31Day6() != null && newTekCalendarVO.getWk31Day6() != null && newTekCalendarVO2.getWk31Day6().compareTo(newTekCalendarVO.getWk31Day6()) != 0){
                    newTekCalendarVO.setWk31Day6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk31Day7() != null && newTekCalendarVO.getWk31Day7() != null && newTekCalendarVO2.getWk31Day7().compareTo(newTekCalendarVO.getWk31Day7()) != 0){
                    newTekCalendarVO.setWk31Day7Status(tekConstants.WK_STATUS_ERROR);
                }

                if(newTekCalendarVO2.getWk32Day1() != null && newTekCalendarVO.getWk32Day1() != null && newTekCalendarVO2.getWk32Day1().compareTo(newTekCalendarVO.getWk32Day1()) != 0){
                    newTekCalendarVO.setWk32Day1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk32Day2() != null && newTekCalendarVO.getWk32Day2() != null && newTekCalendarVO2.getWk32Day2().compareTo(newTekCalendarVO.getWk32Day2()) != 0){
                    newTekCalendarVO.setWk32Day2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk32Day3() != null && newTekCalendarVO.getWk32Day3() != null && newTekCalendarVO2.getWk32Day3().compareTo(newTekCalendarVO.getWk32Day3()) != 0){
                    newTekCalendarVO.setWk32Day3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk32Day4() != null && newTekCalendarVO.getWk32Day4() != null && newTekCalendarVO2.getWk32Day4().compareTo(newTekCalendarVO.getWk32Day4()) != 0){
                    newTekCalendarVO.setWk32Day4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk32Day5() != null && newTekCalendarVO.getWk32Day5() != null && newTekCalendarVO2.getWk32Day5().compareTo(newTekCalendarVO.getWk32Day5()) != 0){
                    newTekCalendarVO.setWk32Day5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk32Day6() != null && newTekCalendarVO.getWk32Day6() != null && newTekCalendarVO2.getWk32Day6().compareTo(newTekCalendarVO.getWk32Day6()) != 0){
                    newTekCalendarVO.setWk32Day6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk32Day7() != null && newTekCalendarVO.getWk32Day7() != null && newTekCalendarVO2.getWk32Day7().compareTo(newTekCalendarVO.getWk32Day7()) != 0){
                    newTekCalendarVO.setWk32Day7Status(tekConstants.WK_STATUS_ERROR);
                }

                if(newTekCalendarVO2.getWk33Day1() != null && newTekCalendarVO.getWk33Day1() != null && newTekCalendarVO2.getWk33Day1().compareTo(newTekCalendarVO.getWk33Day1()) != 0){
                    newTekCalendarVO.setWk33Day1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk33Day2() != null && newTekCalendarVO.getWk33Day2() != null && newTekCalendarVO2.getWk33Day2().compareTo(newTekCalendarVO.getWk33Day2()) != 0){
                    newTekCalendarVO.setWk33Day2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk33Day3() != null && newTekCalendarVO.getWk33Day3() != null && newTekCalendarVO2.getWk33Day3().compareTo(newTekCalendarVO.getWk33Day3()) != 0){
                    newTekCalendarVO.setWk33Day3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk33Day4() != null && newTekCalendarVO.getWk33Day4() != null && newTekCalendarVO2.getWk33Day4().compareTo(newTekCalendarVO.getWk33Day4()) != 0){
                    newTekCalendarVO.setWk33Day4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk33Day5() != null && newTekCalendarVO.getWk33Day5() != null && newTekCalendarVO2.getWk33Day5().compareTo(newTekCalendarVO.getWk33Day5()) != 0){
                    newTekCalendarVO.setWk33Day5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk33Day6() != null && newTekCalendarVO.getWk33Day6() != null && newTekCalendarVO2.getWk33Day6().compareTo(newTekCalendarVO.getWk33Day6()) != 0){
                    newTekCalendarVO.setWk33Day6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk33Day7() != null && newTekCalendarVO.getWk33Day7() != null && newTekCalendarVO2.getWk33Day7().compareTo(newTekCalendarVO.getWk33Day7()) != 0){
                    newTekCalendarVO.setWk33Day7Status(tekConstants.WK_STATUS_ERROR);
                }

                if(newTekCalendarVO2.getWk34Day1() != null && newTekCalendarVO.getWk34Day1() != null && newTekCalendarVO2.getWk34Day1().compareTo(newTekCalendarVO.getWk34Day1()) != 0){
                    newTekCalendarVO.setWk34Day1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk34Day2() != null && newTekCalendarVO.getWk34Day2() != null && newTekCalendarVO2.getWk34Day2().compareTo(newTekCalendarVO.getWk34Day2()) != 0){
                    newTekCalendarVO.setWk34Day2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk34Day3() != null && newTekCalendarVO.getWk34Day3() != null && newTekCalendarVO2.getWk34Day3().compareTo(newTekCalendarVO.getWk34Day3()) != 0){
                    newTekCalendarVO.setWk34Day3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk34Day4() != null && newTekCalendarVO.getWk34Day4() != null && newTekCalendarVO2.getWk34Day4().compareTo(newTekCalendarVO.getWk34Day4()) != 0){
                    newTekCalendarVO.setWk34Day4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk34Day5() != null && newTekCalendarVO.getWk34Day5() != null && newTekCalendarVO2.getWk34Day5().compareTo(newTekCalendarVO.getWk34Day5()) != 0){
                    newTekCalendarVO.setWk34Day5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk34Day6() != null && newTekCalendarVO.getWk34Day6() != null && newTekCalendarVO2.getWk34Day6().compareTo(newTekCalendarVO.getWk34Day6()) != 0){
                    newTekCalendarVO.setWk34Day6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk34Day7() != null && newTekCalendarVO.getWk34Day7() != null && newTekCalendarVO2.getWk34Day7().compareTo(newTekCalendarVO.getWk34Day7()) != 0){
                    newTekCalendarVO.setWk34Day7Status(tekConstants.WK_STATUS_ERROR);
                }

                if(newTekCalendarVO2.getWk35Day1() != null && newTekCalendarVO.getWk35Day1() != null && newTekCalendarVO2.getWk35Day1().compareTo(newTekCalendarVO.getWk35Day1()) != 0){
                    newTekCalendarVO.setWk35Day1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk35Day2() != null && newTekCalendarVO.getWk35Day2() != null && newTekCalendarVO2.getWk35Day2().compareTo(newTekCalendarVO.getWk35Day2()) != 0){
                    newTekCalendarVO.setWk35Day2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk35Day3() != null && newTekCalendarVO.getWk35Day3() != null && newTekCalendarVO2.getWk35Day3().compareTo(newTekCalendarVO.getWk35Day3()) != 0){
                    newTekCalendarVO.setWk35Day3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk35Day4() != null && newTekCalendarVO.getWk35Day4() != null && newTekCalendarVO2.getWk35Day4().compareTo(newTekCalendarVO.getWk35Day4()) != 0){
                    newTekCalendarVO.setWk35Day4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk35Day5() != null && newTekCalendarVO.getWk35Day5() != null && newTekCalendarVO2.getWk35Day5().compareTo(newTekCalendarVO.getWk35Day5()) != 0){
                    newTekCalendarVO.setWk35Day5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk35Day6() != null && newTekCalendarVO.getWk35Day6() != null && newTekCalendarVO2.getWk35Day6().compareTo(newTekCalendarVO.getWk35Day6()) != 0){
                    newTekCalendarVO.setWk35Day6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk35Day7() != null && newTekCalendarVO.getWk35Day7() != null && newTekCalendarVO2.getWk35Day7().compareTo(newTekCalendarVO.getWk35Day7()) != 0){
                    newTekCalendarVO.setWk35Day7Status(tekConstants.WK_STATUS_ERROR);
                }

                if(newTekCalendarVO2.getWk36Day1() != null && newTekCalendarVO.getWk36Day1() != null && newTekCalendarVO2.getWk36Day1().compareTo(newTekCalendarVO.getWk36Day1()) != 0){
                    newTekCalendarVO.setWk36Day1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk36Day2() != null && newTekCalendarVO.getWk36Day2() != null && newTekCalendarVO2.getWk36Day2().compareTo(newTekCalendarVO.getWk36Day2()) != 0){
                    newTekCalendarVO.setWk36Day2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk36Day3() != null && newTekCalendarVO.getWk36Day3() != null && newTekCalendarVO2.getWk36Day3().compareTo(newTekCalendarVO.getWk36Day3()) != 0){
                    newTekCalendarVO.setWk36Day3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk36Day4() != null && newTekCalendarVO.getWk36Day4() != null && newTekCalendarVO2.getWk36Day4().compareTo(newTekCalendarVO.getWk36Day4()) != 0){
                    newTekCalendarVO.setWk36Day4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk36Day5() != null && newTekCalendarVO.getWk36Day5() != null && newTekCalendarVO2.getWk36Day5().compareTo(newTekCalendarVO.getWk36Day5()) != 0){
                    newTekCalendarVO.setWk36Day5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk36Day6() != null && newTekCalendarVO.getWk36Day6() != null && newTekCalendarVO2.getWk36Day6().compareTo(newTekCalendarVO.getWk36Day6()) != 0){
                    newTekCalendarVO.setWk36Day6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk36Day7() != null && newTekCalendarVO.getWk36Day7() != null && newTekCalendarVO2.getWk36Day7().compareTo(newTekCalendarVO.getWk36Day7()) != 0){
                    newTekCalendarVO.setWk36Day7Status(tekConstants.WK_STATUS_ERROR);
                }

                if(newTekCalendarVO2.getWk37Day1() != null && newTekCalendarVO.getWk37Day1() != null && newTekCalendarVO2.getWk37Day1().compareTo(newTekCalendarVO.getWk37Day1()) != 0){
                    newTekCalendarVO.setWk37Day1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk37Day2() != null && newTekCalendarVO.getWk37Day2() != null && newTekCalendarVO2.getWk37Day2().compareTo(newTekCalendarVO.getWk37Day2()) != 0){
                    newTekCalendarVO.setWk37Day2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk37Day3() != null && newTekCalendarVO.getWk37Day3() != null && newTekCalendarVO2.getWk37Day3().compareTo(newTekCalendarVO.getWk37Day3()) != 0){
                    newTekCalendarVO.setWk37Day3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk37Day4() != null && newTekCalendarVO.getWk37Day4() != null && newTekCalendarVO2.getWk37Day4().compareTo(newTekCalendarVO.getWk37Day4()) != 0){
                    newTekCalendarVO.setWk37Day4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk37Day5() != null && newTekCalendarVO.getWk37Day5() != null && newTekCalendarVO2.getWk37Day5().compareTo(newTekCalendarVO.getWk37Day5()) != 0){
                    newTekCalendarVO.setWk37Day5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk37Day6() != null && newTekCalendarVO.getWk37Day6() != null && newTekCalendarVO2.getWk37Day6().compareTo(newTekCalendarVO.getWk37Day6()) != 0){
                    newTekCalendarVO.setWk37Day6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk37Day7() != null && newTekCalendarVO.getWk37Day7() != null && newTekCalendarVO2.getWk37Day7().compareTo(newTekCalendarVO.getWk37Day7()) != 0){
                    newTekCalendarVO.setWk37Day7Status(tekConstants.WK_STATUS_ERROR);
                }

                if(newTekCalendarVO2.getWk38Day1() != null && newTekCalendarVO.getWk38Day1() != null && newTekCalendarVO2.getWk38Day1().compareTo(newTekCalendarVO.getWk38Day1()) != 0){
                    newTekCalendarVO.setWk38Day1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk38Day2() != null && newTekCalendarVO.getWk38Day2() != null && newTekCalendarVO2.getWk38Day2().compareTo(newTekCalendarVO.getWk38Day2()) != 0){
                    newTekCalendarVO.setWk38Day2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk38Day3() != null && newTekCalendarVO.getWk38Day3() != null && newTekCalendarVO2.getWk38Day3().compareTo(newTekCalendarVO.getWk38Day3()) != 0){
                    newTekCalendarVO.setWk38Day3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk38Day4() != null && newTekCalendarVO.getWk38Day4() != null && newTekCalendarVO2.getWk38Day4().compareTo(newTekCalendarVO.getWk38Day4()) != 0){
                    newTekCalendarVO.setWk38Day4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk38Day5() != null && newTekCalendarVO.getWk38Day5() != null && newTekCalendarVO2.getWk38Day5().compareTo(newTekCalendarVO.getWk38Day5()) != 0){
                    newTekCalendarVO.setWk38Day5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk38Day6() != null && newTekCalendarVO.getWk38Day6() != null && newTekCalendarVO2.getWk38Day6().compareTo(newTekCalendarVO.getWk38Day6()) != 0){
                    newTekCalendarVO.setWk38Day6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk38Day7() != null && newTekCalendarVO.getWk38Day7() != null && newTekCalendarVO2.getWk38Day7().compareTo(newTekCalendarVO.getWk38Day7()) != 0){
                    newTekCalendarVO.setWk38Day7Status(tekConstants.WK_STATUS_ERROR);
                }

                if(newTekCalendarVO2.getWk39Day1() != null && newTekCalendarVO.getWk39Day1() != null && newTekCalendarVO2.getWk39Day1().compareTo(newTekCalendarVO.getWk39Day1()) != 0){
                    newTekCalendarVO.setWk39Day1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk39Day2() != null && newTekCalendarVO.getWk39Day2() != null && newTekCalendarVO2.getWk39Day2().compareTo(newTekCalendarVO.getWk39Day2()) != 0){
                    newTekCalendarVO.setWk39Day2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk39Day3() != null && newTekCalendarVO.getWk39Day3() != null && newTekCalendarVO2.getWk39Day3().compareTo(newTekCalendarVO.getWk39Day3()) != 0){
                    newTekCalendarVO.setWk39Day3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk39Day4() != null && newTekCalendarVO.getWk39Day4() != null && newTekCalendarVO2.getWk39Day4().compareTo(newTekCalendarVO.getWk39Day4()) != 0){
                    newTekCalendarVO.setWk39Day4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk39Day5() != null && newTekCalendarVO.getWk39Day5() != null && newTekCalendarVO2.getWk39Day5().compareTo(newTekCalendarVO.getWk39Day5()) != 0){
                    newTekCalendarVO.setWk39Day5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk39Day6() != null && newTekCalendarVO.getWk39Day6() != null && newTekCalendarVO2.getWk39Day6().compareTo(newTekCalendarVO.getWk39Day6()) != 0){
                    newTekCalendarVO.setWk39Day6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk39Day7() != null && newTekCalendarVO.getWk39Day7() != null && newTekCalendarVO2.getWk39Day7().compareTo(newTekCalendarVO.getWk39Day7()) != 0){
                    newTekCalendarVO.setWk39Day7Status(tekConstants.WK_STATUS_ERROR);
                }

                if(newTekCalendarVO2.getWk40Day1() != null && newTekCalendarVO.getWk40Day1() != null && newTekCalendarVO2.getWk40Day1().compareTo(newTekCalendarVO.getWk40Day1()) != 0){
                    newTekCalendarVO.setWk40Day1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk40Day2() != null && newTekCalendarVO.getWk40Day2() != null && newTekCalendarVO2.getWk40Day2().compareTo(newTekCalendarVO.getWk40Day2()) != 0){
                    newTekCalendarVO.setWk40Day2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk40Day3() != null && newTekCalendarVO.getWk40Day3() != null && newTekCalendarVO2.getWk40Day3().compareTo(newTekCalendarVO.getWk40Day3()) != 0){
                    newTekCalendarVO.setWk40Day3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk40Day4() != null && newTekCalendarVO.getWk40Day4() != null && newTekCalendarVO2.getWk40Day4().compareTo(newTekCalendarVO.getWk40Day4()) != 0){
                    newTekCalendarVO.setWk40Day4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk40Day5() != null && newTekCalendarVO.getWk40Day5() != null && newTekCalendarVO2.getWk40Day5().compareTo(newTekCalendarVO.getWk40Day5()) != 0){
                    newTekCalendarVO.setWk40Day5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk40Day6() != null && newTekCalendarVO.getWk40Day6() != null && newTekCalendarVO2.getWk40Day6().compareTo(newTekCalendarVO.getWk40Day6()) != 0){
                    newTekCalendarVO.setWk40Day6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk40Day7() != null && newTekCalendarVO.getWk40Day7() != null && newTekCalendarVO2.getWk40Day7().compareTo(newTekCalendarVO.getWk40Day7()) != 0){
                    newTekCalendarVO.setWk40Day7Status(tekConstants.WK_STATUS_ERROR);
                }

                if(newTekCalendarVO2.getWk41Day1() != null && newTekCalendarVO.getWk41Day1() != null && newTekCalendarVO2.getWk41Day1().compareTo(newTekCalendarVO.getWk41Day1()) != 0){
                    newTekCalendarVO.setWk41Day1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk41Day2() != null && newTekCalendarVO.getWk41Day2() != null && newTekCalendarVO2.getWk41Day2().compareTo(newTekCalendarVO.getWk41Day2()) != 0){
                    newTekCalendarVO.setWk41Day2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk41Day3() != null && newTekCalendarVO.getWk41Day3() != null && newTekCalendarVO2.getWk41Day3().compareTo(newTekCalendarVO.getWk41Day3()) != 0){
                    newTekCalendarVO.setWk41Day3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk41Day4() != null && newTekCalendarVO.getWk41Day4() != null && newTekCalendarVO2.getWk41Day4().compareTo(newTekCalendarVO.getWk41Day4()) != 0){
                    newTekCalendarVO.setWk41Day4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk41Day5() != null && newTekCalendarVO.getWk41Day5() != null && newTekCalendarVO2.getWk41Day5().compareTo(newTekCalendarVO.getWk41Day5()) != 0){
                    newTekCalendarVO.setWk41Day5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk41Day6() != null && newTekCalendarVO.getWk41Day6() != null && newTekCalendarVO2.getWk41Day6().compareTo(newTekCalendarVO.getWk41Day6()) != 0){
                    newTekCalendarVO.setWk41Day6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk41Day7() != null && newTekCalendarVO.getWk41Day7() != null && newTekCalendarVO2.getWk41Day7().compareTo(newTekCalendarVO.getWk41Day7()) != 0){
                    newTekCalendarVO.setWk41Day7Status(tekConstants.WK_STATUS_ERROR);
                }

                if(newTekCalendarVO2.getWk42Day1() != null && newTekCalendarVO.getWk42Day1() != null && newTekCalendarVO2.getWk42Day1().compareTo(newTekCalendarVO.getWk42Day1()) != 0){
                    newTekCalendarVO.setWk42Day1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk42Day2() != null && newTekCalendarVO.getWk42Day2() != null && newTekCalendarVO2.getWk42Day2().compareTo(newTekCalendarVO.getWk42Day2()) != 0){
                    newTekCalendarVO.setWk42Day2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk42Day3() != null && newTekCalendarVO.getWk42Day3() != null && newTekCalendarVO2.getWk42Day3().compareTo(newTekCalendarVO.getWk42Day3()) != 0){
                    newTekCalendarVO.setWk42Day3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk42Day4() != null && newTekCalendarVO.getWk42Day4() != null && newTekCalendarVO2.getWk42Day4().compareTo(newTekCalendarVO.getWk42Day4()) != 0){
                    newTekCalendarVO.setWk42Day4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk42Day5() != null && newTekCalendarVO.getWk42Day5() != null && newTekCalendarVO2.getWk42Day5().compareTo(newTekCalendarVO.getWk42Day5()) != 0){
                    newTekCalendarVO.setWk42Day5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk42Day6() != null && newTekCalendarVO.getWk42Day6() != null && newTekCalendarVO2.getWk42Day6().compareTo(newTekCalendarVO.getWk42Day6()) != 0){
                    newTekCalendarVO.setWk42Day6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk42Day7() != null && newTekCalendarVO.getWk42Day7() != null && newTekCalendarVO2.getWk42Day7().compareTo(newTekCalendarVO.getWk42Day7()) != 0){
                    newTekCalendarVO.setWk42Day7Status(tekConstants.WK_STATUS_ERROR);
                }

                if(newTekCalendarVO2.getWk43Day1() != null && newTekCalendarVO.getWk43Day1() != null && newTekCalendarVO2.getWk43Day1().compareTo(newTekCalendarVO.getWk43Day1()) != 0){
                    newTekCalendarVO.setWk43Day1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk43Day2() != null && newTekCalendarVO.getWk43Day2() != null && newTekCalendarVO2.getWk43Day2().compareTo(newTekCalendarVO.getWk43Day2()) != 0){
                    newTekCalendarVO.setWk43Day2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk43Day3() != null && newTekCalendarVO.getWk43Day3() != null && newTekCalendarVO2.getWk43Day3().compareTo(newTekCalendarVO.getWk43Day3()) != 0){
                    newTekCalendarVO.setWk43Day3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk43Day4() != null && newTekCalendarVO.getWk43Day4() != null && newTekCalendarVO2.getWk43Day4().compareTo(newTekCalendarVO.getWk43Day4()) != 0){
                    newTekCalendarVO.setWk43Day4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk43Day5() != null && newTekCalendarVO.getWk43Day5() != null && newTekCalendarVO2.getWk43Day5().compareTo(newTekCalendarVO.getWk43Day5()) != 0){
                    newTekCalendarVO.setWk43Day5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk43Day6() != null && newTekCalendarVO.getWk43Day6() != null && newTekCalendarVO2.getWk43Day6().compareTo(newTekCalendarVO.getWk43Day6()) != 0){
                    newTekCalendarVO.setWk43Day6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk43Day7() != null && newTekCalendarVO.getWk43Day7() != null && newTekCalendarVO2.getWk43Day7().compareTo(newTekCalendarVO.getWk43Day7()) != 0){
                    newTekCalendarVO.setWk43Day7Status(tekConstants.WK_STATUS_ERROR);
                }

                if(newTekCalendarVO2.getWk44Day1() != null && newTekCalendarVO.getWk44Day1() != null && newTekCalendarVO2.getWk44Day1().compareTo(newTekCalendarVO.getWk44Day1()) != 0){
                    newTekCalendarVO.setWk44Day1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk44Day2() != null && newTekCalendarVO.getWk44Day2() != null && newTekCalendarVO2.getWk44Day2().compareTo(newTekCalendarVO.getWk44Day2()) != 0){
                    newTekCalendarVO.setWk44Day2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk44Day3() != null && newTekCalendarVO.getWk44Day3() != null && newTekCalendarVO2.getWk44Day3().compareTo(newTekCalendarVO.getWk44Day3()) != 0){
                    newTekCalendarVO.setWk44Day3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk44Day4() != null && newTekCalendarVO.getWk44Day4() != null && newTekCalendarVO2.getWk44Day4().compareTo(newTekCalendarVO.getWk44Day4()) != 0){
                    newTekCalendarVO.setWk44Day4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk44Day5() != null && newTekCalendarVO.getWk44Day5() != null && newTekCalendarVO2.getWk44Day5().compareTo(newTekCalendarVO.getWk44Day5()) != 0){
                    newTekCalendarVO.setWk44Day5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk44Day6() != null && newTekCalendarVO.getWk44Day6() != null && newTekCalendarVO2.getWk44Day6().compareTo(newTekCalendarVO.getWk44Day6()) != 0){
                    newTekCalendarVO.setWk44Day6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk44Day7() != null && newTekCalendarVO.getWk44Day7() != null && newTekCalendarVO2.getWk44Day7().compareTo(newTekCalendarVO.getWk44Day7()) != 0){
                    newTekCalendarVO.setWk44Day7Status(tekConstants.WK_STATUS_ERROR);
                }

                if(newTekCalendarVO2.getWk45Day1() != null && newTekCalendarVO.getWk45Day1() != null && newTekCalendarVO2.getWk45Day1().compareTo(newTekCalendarVO.getWk45Day1()) != 0){
                    newTekCalendarVO.setWk45Day1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk45Day2() != null && newTekCalendarVO.getWk45Day2() != null && newTekCalendarVO2.getWk45Day2().compareTo(newTekCalendarVO.getWk45Day2()) != 0){
                    newTekCalendarVO.setWk45Day2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk45Day3() != null && newTekCalendarVO.getWk45Day3() != null && newTekCalendarVO2.getWk45Day3().compareTo(newTekCalendarVO.getWk45Day3()) != 0){
                    newTekCalendarVO.setWk45Day3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk45Day4() != null && newTekCalendarVO.getWk45Day4() != null && newTekCalendarVO2.getWk45Day4().compareTo(newTekCalendarVO.getWk45Day4()) != 0){
                    newTekCalendarVO.setWk45Day4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk45Day5() != null && newTekCalendarVO.getWk45Day5() != null && newTekCalendarVO2.getWk45Day5().compareTo(newTekCalendarVO.getWk45Day5()) != 0){
                    newTekCalendarVO.setWk45Day5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk45Day6() != null && newTekCalendarVO.getWk45Day6() != null && newTekCalendarVO2.getWk45Day6().compareTo(newTekCalendarVO.getWk45Day6()) != 0){
                    newTekCalendarVO.setWk45Day6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk45Day7() != null && newTekCalendarVO.getWk45Day7() != null && newTekCalendarVO2.getWk45Day7().compareTo(newTekCalendarVO.getWk45Day7()) != 0){
                    newTekCalendarVO.setWk45Day7Status(tekConstants.WK_STATUS_ERROR);
                }

                if(newTekCalendarVO2.getWk46Day1() != null && newTekCalendarVO.getWk46Day1() != null && newTekCalendarVO2.getWk46Day1().compareTo(newTekCalendarVO.getWk46Day1()) != 0){
                    newTekCalendarVO.setWk46Day1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk46Day2() != null && newTekCalendarVO.getWk46Day2() != null && newTekCalendarVO2.getWk46Day2().compareTo(newTekCalendarVO.getWk46Day2()) != 0){
                    newTekCalendarVO.setWk46Day2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk46Day3() != null && newTekCalendarVO.getWk46Day3() != null && newTekCalendarVO2.getWk46Day3().compareTo(newTekCalendarVO.getWk46Day3()) != 0){
                    newTekCalendarVO.setWk46Day3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk46Day4() != null && newTekCalendarVO.getWk46Day4() != null && newTekCalendarVO2.getWk46Day4().compareTo(newTekCalendarVO.getWk46Day4()) != 0){
                    newTekCalendarVO.setWk46Day4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk46Day5() != null && newTekCalendarVO.getWk46Day5() != null && newTekCalendarVO2.getWk46Day5().compareTo(newTekCalendarVO.getWk46Day5()) != 0){
                    newTekCalendarVO.setWk46Day5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk46Day6() != null && newTekCalendarVO.getWk46Day6() != null && newTekCalendarVO2.getWk46Day6().compareTo(newTekCalendarVO.getWk46Day6()) != 0){
                    newTekCalendarVO.setWk46Day6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk46Day7() != null && newTekCalendarVO.getWk46Day7() != null && newTekCalendarVO2.getWk46Day7().compareTo(newTekCalendarVO.getWk46Day7()) != 0){
                    newTekCalendarVO.setWk46Day7Status(tekConstants.WK_STATUS_ERROR);
                }

                if(newTekCalendarVO2.getWk47Day1() != null && newTekCalendarVO.getWk47Day1() != null && newTekCalendarVO2.getWk47Day1().compareTo(newTekCalendarVO.getWk47Day1()) != 0){
                    newTekCalendarVO.setWk47Day1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk47Day2() != null && newTekCalendarVO.getWk47Day2() != null && newTekCalendarVO2.getWk47Day2().compareTo(newTekCalendarVO.getWk47Day2()) != 0){
                    newTekCalendarVO.setWk47Day2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk47Day3() != null && newTekCalendarVO.getWk47Day3() != null && newTekCalendarVO2.getWk47Day3().compareTo(newTekCalendarVO.getWk47Day3()) != 0){
                    newTekCalendarVO.setWk47Day3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk47Day4() != null && newTekCalendarVO.getWk47Day4() != null && newTekCalendarVO2.getWk47Day4().compareTo(newTekCalendarVO.getWk47Day4()) != 0){
                    newTekCalendarVO.setWk47Day4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk47Day5() != null && newTekCalendarVO.getWk47Day5() != null && newTekCalendarVO2.getWk47Day5().compareTo(newTekCalendarVO.getWk47Day5()) != 0){
                    newTekCalendarVO.setWk47Day5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk47Day6() != null && newTekCalendarVO.getWk47Day6() != null && newTekCalendarVO2.getWk47Day6().compareTo(newTekCalendarVO.getWk47Day6()) != 0){
                    newTekCalendarVO.setWk47Day6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk47Day7() != null && newTekCalendarVO.getWk47Day7() != null && newTekCalendarVO2.getWk47Day7().compareTo(newTekCalendarVO.getWk47Day7()) != 0){
                    newTekCalendarVO.setWk47Day7Status(tekConstants.WK_STATUS_ERROR);
                }

                if(newTekCalendarVO2.getWk48Day1() != null && newTekCalendarVO.getWk48Day1() != null && newTekCalendarVO2.getWk48Day1().compareTo(newTekCalendarVO.getWk48Day1()) != 0){
                    newTekCalendarVO.setWk48Day1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk48Day2() != null && newTekCalendarVO.getWk48Day2() != null && newTekCalendarVO2.getWk48Day2().compareTo(newTekCalendarVO.getWk48Day2()) != 0){
                    newTekCalendarVO.setWk48Day2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk48Day3() != null && newTekCalendarVO.getWk48Day3() != null && newTekCalendarVO2.getWk48Day3().compareTo(newTekCalendarVO.getWk48Day3()) != 0){
                    newTekCalendarVO.setWk48Day3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk48Day4() != null && newTekCalendarVO.getWk48Day4() != null && newTekCalendarVO2.getWk48Day4().compareTo(newTekCalendarVO.getWk48Day4()) != 0){
                    newTekCalendarVO.setWk48Day4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk48Day5() != null && newTekCalendarVO.getWk48Day5() != null && newTekCalendarVO2.getWk48Day5().compareTo(newTekCalendarVO.getWk48Day5()) != 0){
                    newTekCalendarVO.setWk48Day5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk48Day6() != null && newTekCalendarVO.getWk48Day6() != null && newTekCalendarVO2.getWk48Day6().compareTo(newTekCalendarVO.getWk48Day6()) != 0){
                    newTekCalendarVO.setWk48Day6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk48Day7() != null && newTekCalendarVO.getWk48Day7() != null && newTekCalendarVO2.getWk48Day7().compareTo(newTekCalendarVO.getWk48Day7()) != 0){
                    newTekCalendarVO.setWk48Day7Status(tekConstants.WK_STATUS_ERROR);
                }

                if(newTekCalendarVO2.getWk49Day1() != null && newTekCalendarVO.getWk49Day1() != null && newTekCalendarVO2.getWk49Day1().compareTo(newTekCalendarVO.getWk49Day1()) != 0){
                    newTekCalendarVO.setWk49Day1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk49Day2() != null && newTekCalendarVO.getWk49Day2() != null && newTekCalendarVO2.getWk49Day2().compareTo(newTekCalendarVO.getWk49Day2()) != 0){
                    newTekCalendarVO.setWk49Day2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk49Day3() != null && newTekCalendarVO.getWk49Day3() != null && newTekCalendarVO2.getWk49Day3().compareTo(newTekCalendarVO.getWk49Day3()) != 0){
                    newTekCalendarVO.setWk49Day3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk49Day4() != null && newTekCalendarVO.getWk49Day4() != null && newTekCalendarVO2.getWk49Day4().compareTo(newTekCalendarVO.getWk49Day4()) != 0){
                    newTekCalendarVO.setWk49Day4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk49Day5() != null && newTekCalendarVO.getWk49Day5() != null && newTekCalendarVO2.getWk49Day5().compareTo(newTekCalendarVO.getWk49Day5()) != 0){
                    newTekCalendarVO.setWk49Day5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk49Day6() != null && newTekCalendarVO.getWk49Day6() != null && newTekCalendarVO2.getWk49Day6().compareTo(newTekCalendarVO.getWk49Day6()) != 0){
                    newTekCalendarVO.setWk49Day6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk49Day7() != null && newTekCalendarVO.getWk49Day7() != null && newTekCalendarVO2.getWk49Day7().compareTo(newTekCalendarVO.getWk49Day7()) != 0){
                    newTekCalendarVO.setWk49Day7Status(tekConstants.WK_STATUS_ERROR);
                }

                if(newTekCalendarVO2.getWk50Day1() != null && newTekCalendarVO.getWk50Day1() != null && newTekCalendarVO2.getWk50Day1().compareTo(newTekCalendarVO.getWk50Day1()) != 0){
                    newTekCalendarVO.setWk50Day1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk50Day2() != null && newTekCalendarVO.getWk50Day2() != null && newTekCalendarVO2.getWk50Day2().compareTo(newTekCalendarVO.getWk50Day2()) != 0){
                    newTekCalendarVO.setWk50Day2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk50Day3() != null && newTekCalendarVO.getWk50Day3() != null && newTekCalendarVO2.getWk50Day3().compareTo(newTekCalendarVO.getWk50Day3()) != 0){
                    newTekCalendarVO.setWk50Day3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk50Day4() != null && newTekCalendarVO.getWk50Day4() != null && newTekCalendarVO2.getWk50Day4().compareTo(newTekCalendarVO.getWk50Day4()) != 0){
                    newTekCalendarVO.setWk50Day4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk50Day5() != null && newTekCalendarVO.getWk50Day5() != null && newTekCalendarVO2.getWk50Day5().compareTo(newTekCalendarVO.getWk50Day5()) != 0){
                    newTekCalendarVO.setWk50Day5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk50Day6() != null && newTekCalendarVO.getWk50Day6() != null && newTekCalendarVO2.getWk50Day6().compareTo(newTekCalendarVO.getWk50Day6()) != 0){
                    newTekCalendarVO.setWk50Day6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk50Day7() != null && newTekCalendarVO.getWk50Day7() != null && newTekCalendarVO2.getWk50Day7().compareTo(newTekCalendarVO.getWk50Day7()) != 0){
                    newTekCalendarVO.setWk50Day7Status(tekConstants.WK_STATUS_ERROR);
                }

                if(newTekCalendarVO2.getWk51Day1() != null && newTekCalendarVO.getWk51Day1() != null && newTekCalendarVO2.getWk51Day1().compareTo(newTekCalendarVO.getWk51Day1()) != 0){
                    newTekCalendarVO.setWk51Day1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk51Day2() != null && newTekCalendarVO.getWk51Day2() != null && newTekCalendarVO2.getWk51Day2().compareTo(newTekCalendarVO.getWk51Day2()) != 0){
                    newTekCalendarVO.setWk51Day2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk51Day3() != null && newTekCalendarVO.getWk51Day3() != null && newTekCalendarVO2.getWk51Day3().compareTo(newTekCalendarVO.getWk51Day3()) != 0){
                    newTekCalendarVO.setWk51Day3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk51Day4() != null && newTekCalendarVO.getWk51Day4() != null && newTekCalendarVO2.getWk51Day4().compareTo(newTekCalendarVO.getWk51Day4()) != 0){
                    newTekCalendarVO.setWk51Day4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk51Day5() != null && newTekCalendarVO.getWk51Day5() != null && newTekCalendarVO2.getWk51Day5().compareTo(newTekCalendarVO.getWk51Day5()) != 0){
                    newTekCalendarVO.setWk51Day5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk51Day6() != null && newTekCalendarVO.getWk51Day6() != null && newTekCalendarVO2.getWk51Day6().compareTo(newTekCalendarVO.getWk51Day6()) != 0){
                    newTekCalendarVO.setWk51Day6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk51Day7() != null && newTekCalendarVO.getWk51Day7() != null && newTekCalendarVO2.getWk51Day7().compareTo(newTekCalendarVO.getWk51Day7()) != 0){
                    newTekCalendarVO.setWk51Day7Status(tekConstants.WK_STATUS_ERROR);
                }

                if(newTekCalendarVO2.getWk52Day1() != null && newTekCalendarVO.getWk52Day1() != null && newTekCalendarVO2.getWk52Day1().compareTo(newTekCalendarVO.getWk52Day1()) != 0){
                    newTekCalendarVO.setWk52Day1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk52Day2() != null && newTekCalendarVO.getWk52Day2() != null && newTekCalendarVO2.getWk52Day2().compareTo(newTekCalendarVO.getWk52Day2()) != 0){
                    newTekCalendarVO.setWk52Day2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk52Day3() != null && newTekCalendarVO.getWk52Day3() != null && newTekCalendarVO2.getWk52Day3().compareTo(newTekCalendarVO.getWk52Day3()) != 0){
                    newTekCalendarVO.setWk52Day3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk52Day4() != null && newTekCalendarVO.getWk52Day4() != null && newTekCalendarVO2.getWk52Day4().compareTo(newTekCalendarVO.getWk52Day4()) != 0){
                    newTekCalendarVO.setWk52Day4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk52Day5() != null && newTekCalendarVO.getWk52Day5() != null && newTekCalendarVO2.getWk52Day5().compareTo(newTekCalendarVO.getWk52Day5()) != 0){
                    newTekCalendarVO.setWk52Day5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk52Day6() != null && newTekCalendarVO.getWk52Day6() != null && newTekCalendarVO2.getWk52Day6().compareTo(newTekCalendarVO.getWk52Day6()) != 0){
                    newTekCalendarVO.setWk52Day6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk52Day7() != null && newTekCalendarVO.getWk52Day7() != null && newTekCalendarVO2.getWk52Day7().compareTo(newTekCalendarVO.getWk52Day7()) != 0){
                    newTekCalendarVO.setWk52Day7Status(tekConstants.WK_STATUS_ERROR);
                }

                if(newTekCalendarVO2.getWk53Day1() != null && newTekCalendarVO.getWk53Day1() != null && newTekCalendarVO2.getWk53Day1().compareTo(newTekCalendarVO.getWk53Day1()) != 0){
                    newTekCalendarVO.setWk53Day1Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk53Day2() != null && newTekCalendarVO.getWk53Day2() != null && newTekCalendarVO2.getWk53Day2().compareTo(newTekCalendarVO.getWk53Day2()) != 0){
                    newTekCalendarVO.setWk53Day2Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk53Day3() != null && newTekCalendarVO.getWk53Day3() != null && newTekCalendarVO2.getWk53Day3().compareTo(newTekCalendarVO.getWk53Day3()) != 0){
                    newTekCalendarVO.setWk53Day3Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk53Day4() != null && newTekCalendarVO.getWk53Day4() != null && newTekCalendarVO2.getWk53Day4().compareTo(newTekCalendarVO.getWk53Day4()) != 0){
                    newTekCalendarVO.setWk53Day4Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk53Day5() != null && newTekCalendarVO.getWk53Day5() != null && newTekCalendarVO2.getWk53Day5().compareTo(newTekCalendarVO.getWk53Day5()) != 0){
                    newTekCalendarVO.setWk53Day5Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk53Day6() != null && newTekCalendarVO.getWk53Day6() != null && newTekCalendarVO2.getWk53Day6().compareTo(newTekCalendarVO.getWk53Day6()) != 0){
                    newTekCalendarVO.setWk53Day6Status(tekConstants.WK_STATUS_ERROR);
                }
                if(newTekCalendarVO2.getWk53Day7() != null && newTekCalendarVO.getWk53Day7() != null && newTekCalendarVO2.getWk53Day7().compareTo(newTekCalendarVO.getWk53Day7()) != 0){
                    newTekCalendarVO.setWk53Day7Status(tekConstants.WK_STATUS_ERROR);
                }
            }
        }
        return newTekCalendarVO;
    }

}
