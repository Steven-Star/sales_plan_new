package com.ruoyi.framework.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.constant.tekConstants;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.http.HttpUtils;
import com.ruoyi.project.tek.domain.NewTekCurrencyData;
import com.ruoyi.project.tek.mapper.NewTekCurrencyDataMapper;
import com.ruoyi.project.tek.service.impl.TblGoodsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * tek定时任务调度
 * 
 * @author steven
 */
@Component("tekTask")
public class tekTask {

    @Autowired
    private NewTekCurrencyDataMapper newTekCurrencyDataMapper;

    public void initCurrencyEveryDay() {
        String usdToCny1 = currencyTransf("USD","AED");
        String usdTorub1 = currencyTransf("USD","RUB");
        String usdToCad1 = currencyTransf("USD","CAD");
        String usdToKrw1 = currencyTransf("USD","KRW");
        String usdToZar1 = currencyTransf("USD","ZAR");
        String usdToEur1 = currencyTransf("USD","EUR");
        String usdToInr1 = currencyTransf("USD","INR");
        String usdToDjf1 = currencyTransf("USD","DJF");
        String usdToAud1 = currencyTransf("USD","AUD");
        String usdToGbp1 = currencyTransf("USD","GBP");
        String usdToNzd1 = currencyTransf("USD","NZD");
        String usdToSgd1 = currencyTransf("USD","SGD");
        String usdToJpy1 = currencyTransf("USD","JPY");
        String usdToThb1 = currencyTransf("USD","THB");
        String usdToFrf1 = currencyTransf("USD","PHP");
        String usdToMyr1 = currencyTransf("USD","MYR");

        String usdToCny = currencyTransf("AED","USD");
        String usdTorub = currencyTransf("RUB","USD");
        String usdToCad = currencyTransf("CAD","USD");
        String usdToKrw = currencyTransf("KRW","USD");
        String usdToZar = currencyTransf("ZAR","USD");
        String usdToEur = currencyTransf("EUR","USD");
        String usdToInr = currencyTransf("INR","USD");
        String usdToDjf = currencyTransf("DJF","USD");
        String usdToAud = currencyTransf("AUD","USD");
        String usdToGbp = currencyTransf("GBP","USD");
        String usdToNzd = currencyTransf("NZD","USD");
        String usdToSgd = currencyTransf("SGD","USD");
        String usdToJpy = currencyTransf("JPY","USD");
        String usdToThb = currencyTransf("THB","USD");
        String usdToFrf = currencyTransf("PHP","USD");
        String usdToMyr = currencyTransf("MYR","USD");

        NewTekCurrencyData newTekCurrencyData = new NewTekCurrencyData();
        newTekCurrencyData.setFromCountry("AED");
        newTekCurrencyData.setToCountry("USD");
        newTekCurrencyData.setCurrency(usdToCny);

        NewTekCurrencyData newTekCurrencyData1 = new NewTekCurrencyData();
        newTekCurrencyData1.setFromCountry("RUB");
        newTekCurrencyData1.setToCountry("USD");
        newTekCurrencyData1.setCurrency(usdTorub);

        NewTekCurrencyData newTekCurrencyData2 = new NewTekCurrencyData();
        newTekCurrencyData2.setFromCountry("CAD");
        newTekCurrencyData2.setToCountry("USD");
        newTekCurrencyData2.setCurrency(usdToCad);

        NewTekCurrencyData newTekCurrencyData3 = new NewTekCurrencyData();
        newTekCurrencyData3.setFromCountry("KRW");
        newTekCurrencyData3.setToCountry("USD");
        newTekCurrencyData3.setCurrency(usdToKrw);

        NewTekCurrencyData newTekCurrencyData4 = new NewTekCurrencyData();
        newTekCurrencyData4.setFromCountry("ZAR");
        newTekCurrencyData4.setToCountry("USD");
        newTekCurrencyData4.setCurrency(usdToZar);

        NewTekCurrencyData newTekCurrencyData5 = new NewTekCurrencyData();
        newTekCurrencyData5.setFromCountry("EUR");
        newTekCurrencyData5.setToCountry("USD");
        newTekCurrencyData5.setCurrency(usdToEur);

        NewTekCurrencyData newTekCurrencyData6 = new NewTekCurrencyData();
        newTekCurrencyData6.setFromCountry("INR");
        newTekCurrencyData6.setToCountry("USD");
        newTekCurrencyData6.setCurrency(usdToInr);

        NewTekCurrencyData newTekCurrencyData7 = new NewTekCurrencyData();
        newTekCurrencyData7.setFromCountry("DJF");
        newTekCurrencyData7.setToCountry("USD");
        newTekCurrencyData7.setCurrency(usdToDjf);

        NewTekCurrencyData newTekCurrencyData8 = new NewTekCurrencyData();
        newTekCurrencyData8.setFromCountry("AUD");
        newTekCurrencyData8.setToCountry("USD");
        newTekCurrencyData8.setCurrency(usdToAud);

        NewTekCurrencyData newTekCurrencyData9 = new NewTekCurrencyData();
        newTekCurrencyData9.setFromCountry("GBP");
        newTekCurrencyData9.setToCountry("USD");
        newTekCurrencyData9.setCurrency(usdToGbp);

        NewTekCurrencyData newTekCurrencyData10 = new NewTekCurrencyData();
        newTekCurrencyData10.setFromCountry("NZD");
        newTekCurrencyData10.setToCountry("USD");
        newTekCurrencyData10.setCurrency(usdToNzd);

        NewTekCurrencyData newTekCurrencyData11 = new NewTekCurrencyData();
        newTekCurrencyData11.setFromCountry("SGD");
        newTekCurrencyData11.setToCountry("USD");
        newTekCurrencyData11.setCurrency(usdToSgd);

        NewTekCurrencyData newTekCurrencyData12 = new NewTekCurrencyData();
        newTekCurrencyData12.setFromCountry("JPY");
        newTekCurrencyData12.setToCountry("USD");
        newTekCurrencyData12.setCurrency(usdToJpy);

        NewTekCurrencyData newTekCurrencyData13 = new NewTekCurrencyData();
        newTekCurrencyData13.setFromCountry("THB");
        newTekCurrencyData13.setToCountry("USD");
        newTekCurrencyData13.setCurrency(usdToThb);

        NewTekCurrencyData newTekCurrencyData14 = new NewTekCurrencyData();
        newTekCurrencyData14.setFromCountry("PHP");
        newTekCurrencyData14.setToCountry("USD");
        newTekCurrencyData14.setCurrency(usdToFrf);

        NewTekCurrencyData newTekCurrencyData15 = new NewTekCurrencyData();
        newTekCurrencyData15.setFromCountry("MYR");
        newTekCurrencyData15.setToCountry("USD");
        newTekCurrencyData15.setCurrency(usdToMyr);

        NewTekCurrencyData newTekCurrencyData16 = new NewTekCurrencyData();
        newTekCurrencyData16.setFromCountry("USD");
        newTekCurrencyData16.setToCountry("AED");
        newTekCurrencyData16.setCurrency(usdToCny1);

        NewTekCurrencyData newTekCurrencyData17 = new NewTekCurrencyData();
        newTekCurrencyData17.setFromCountry("USD");
        newTekCurrencyData17.setToCountry("RUB");
        newTekCurrencyData17.setCurrency(usdTorub1);

        NewTekCurrencyData newTekCurrencyData18 = new NewTekCurrencyData();
        newTekCurrencyData18.setFromCountry("USD");
        newTekCurrencyData18.setToCountry("CAD");
        newTekCurrencyData18.setCurrency(usdToCad1);

        NewTekCurrencyData newTekCurrencyData19 = new NewTekCurrencyData();
        newTekCurrencyData19.setFromCountry("USD");
        newTekCurrencyData19.setToCountry("KRW");
        newTekCurrencyData19.setCurrency(usdToKrw1);

        NewTekCurrencyData newTekCurrencyData20 = new NewTekCurrencyData();
        newTekCurrencyData20.setFromCountry("USD");
        newTekCurrencyData20.setToCountry("ZAR");
        newTekCurrencyData20.setCurrency(usdToZar1);

        NewTekCurrencyData newTekCurrencyData21 = new NewTekCurrencyData();
        newTekCurrencyData21.setFromCountry("USD");
        newTekCurrencyData21.setToCountry("EUR");
        newTekCurrencyData21.setCurrency(usdToEur1);

        NewTekCurrencyData newTekCurrencyData22 = new NewTekCurrencyData();
        newTekCurrencyData22.setFromCountry("USD");
        newTekCurrencyData22.setToCountry("INR");
        newTekCurrencyData22.setCurrency(usdToInr1);

        NewTekCurrencyData newTekCurrencyData23 = new NewTekCurrencyData();
        newTekCurrencyData23.setFromCountry("USD");
        newTekCurrencyData23.setToCountry("DJF");
        newTekCurrencyData23.setCurrency(usdToDjf1);

        NewTekCurrencyData newTekCurrencyData24 = new NewTekCurrencyData();
        newTekCurrencyData24.setFromCountry("USD");
        newTekCurrencyData24.setToCountry("AUD");
        newTekCurrencyData24.setCurrency(usdToAud1);

        NewTekCurrencyData newTekCurrencyData25 = new NewTekCurrencyData();
        newTekCurrencyData25.setFromCountry("USD");
        newTekCurrencyData25.setToCountry("GBP");
        newTekCurrencyData25.setCurrency(usdToGbp1);

        NewTekCurrencyData newTekCurrencyData26 = new NewTekCurrencyData();
        newTekCurrencyData26.setFromCountry("USD");
        newTekCurrencyData26.setToCountry("NZD");
        newTekCurrencyData26.setCurrency(usdToNzd1);

        NewTekCurrencyData newTekCurrencyData27 = new NewTekCurrencyData();
        newTekCurrencyData27.setFromCountry("USD");
        newTekCurrencyData27.setToCountry("SGD");
        newTekCurrencyData27.setCurrency(usdToSgd1);

        NewTekCurrencyData newTekCurrencyData28 = new NewTekCurrencyData();
        newTekCurrencyData28.setFromCountry("USD");
        newTekCurrencyData28.setToCountry("JPY");
        newTekCurrencyData28.setCurrency(usdToJpy1);

        NewTekCurrencyData newTekCurrencyData29 = new NewTekCurrencyData();
        newTekCurrencyData29.setFromCountry("USD");
        newTekCurrencyData29.setToCountry("THB");
        newTekCurrencyData29.setCurrency(usdToThb1);

        NewTekCurrencyData newTekCurrencyData30 = new NewTekCurrencyData();
        newTekCurrencyData30.setFromCountry("USD");
        newTekCurrencyData30.setToCountry("PHP");
        newTekCurrencyData30.setCurrency(usdToFrf1);

        NewTekCurrencyData newTekCurrencyData31 = new NewTekCurrencyData();
        newTekCurrencyData31.setFromCountry("USD");
        newTekCurrencyData31.setToCountry("MYR");
        newTekCurrencyData31.setCurrency(usdToMyr1);


        List<NewTekCurrencyData> currencyDataList = new ArrayList<>();
        currencyDataList.add(newTekCurrencyData);
        currencyDataList.add(newTekCurrencyData1);
        currencyDataList.add(newTekCurrencyData2);
        currencyDataList.add(newTekCurrencyData3);
        currencyDataList.add(newTekCurrencyData4);
        currencyDataList.add(newTekCurrencyData5);
        currencyDataList.add(newTekCurrencyData6);
        currencyDataList.add(newTekCurrencyData7);
        currencyDataList.add(newTekCurrencyData8);
        currencyDataList.add(newTekCurrencyData9);
        currencyDataList.add(newTekCurrencyData10);
        currencyDataList.add(newTekCurrencyData11);
        currencyDataList.add(newTekCurrencyData12);
        currencyDataList.add(newTekCurrencyData13);
        currencyDataList.add(newTekCurrencyData14);
        currencyDataList.add(newTekCurrencyData15);
        currencyDataList.add(newTekCurrencyData16);
        currencyDataList.add(newTekCurrencyData17);
        currencyDataList.add(newTekCurrencyData18);
        currencyDataList.add(newTekCurrencyData19);
        currencyDataList.add(newTekCurrencyData20);
        currencyDataList.add(newTekCurrencyData21);
        currencyDataList.add(newTekCurrencyData22);
        currencyDataList.add(newTekCurrencyData23);
        currencyDataList.add(newTekCurrencyData24);
        currencyDataList.add(newTekCurrencyData25);
        currencyDataList.add(newTekCurrencyData26);
        currencyDataList.add(newTekCurrencyData27);
        currencyDataList.add(newTekCurrencyData28);
        currencyDataList.add(newTekCurrencyData29);
        currencyDataList.add(newTekCurrencyData30);
        currencyDataList.add(newTekCurrencyData31);

        newTekCurrencyDataMapper.deleteNewTekCurrencyDataAll();
        newTekCurrencyDataMapper.insertNewTekCurrencyData(currencyDataList);
    }


    public String currencyTransf(String currencyType, String currencyCode){
        String result = null;
        String params = "key="+ tekConstants.JUHE_APPKEY+"&from=" + currencyType +"&to=" + currencyCode;
        String url = tekConstants.JUHE_CURRENCY_API_ADD;
        String getResult = HttpUtils.sendGet(url,params);
        JSONObject jsonObject = JSONObject.parseObject(getResult);
        if((Integer) jsonObject.get("error_code") == 0){
            JSONArray jsonArray = (JSONArray) jsonObject.get("result");
            JSONObject job = jsonArray.getJSONObject(0);
            result = (String)job.get("result");
        }
        return result;
    }
}
