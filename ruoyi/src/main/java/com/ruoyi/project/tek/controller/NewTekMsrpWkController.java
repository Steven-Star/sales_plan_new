package com.ruoyi.project.tek.controller;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.framework.security.service.TokenService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.system.domain.SysUser;
import com.ruoyi.project.tek.domain.NewTekCountryCode;
import com.ruoyi.project.tek.domain.NewTekCustomer;
import com.ruoyi.project.tek.domain.NewTekMsrp;
import com.ruoyi.project.tek.domain.NewTekMsrpWk;
import com.ruoyi.project.tek.domain.NewTekProductModel;
import com.ruoyi.project.tek.request.QueryCustomerReq;
import com.ruoyi.project.tek.request.QueryMsrpWKReq;
import com.ruoyi.project.tek.service.INewTekCountryCodeService;
import com.ruoyi.project.tek.service.INewTekCustomerService;
import com.ruoyi.project.tek.service.INewTekMsrpService;
import com.ruoyi.project.tek.service.INewTekMsrpWkService;
import com.ruoyi.project.tek.service.INewTekProductModelService;
import com.ruoyi.project.tek.vo.NewTekMsrpWKVO;
import com.ruoyi.project.tek.vo.NewTekPromotionListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author steven.guo
 * @version 1.0
 * @description:
 * @date 2020/6/19 09:10
 */
@RestController
@RequestMapping("/tek/newTekMsrpWk")
public class NewTekMsrpWkController extends BaseController {

    @Autowired
    private INewTekMsrpWkService newTekMsrpWkService;
    @Autowired
    private INewTekMsrpService newTekMsrpService;
    @Autowired
    private INewTekCustomerService newTekCustomerService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private INewTekCountryCodeService newTekCountryCodeService;
    @Autowired
    private INewTekProductModelService newTekProductModelService;


    @PreAuthorize("@ss.hasPermi('customer:sellingPrice:list')")
    @GetMapping(value = "/queryNewTekMsrpWKByPage")
    public TableDataInfo queryNewTekMsrpWKByPage(QueryMsrpWKReq req) {
        List<NewTekMsrpWKVO> newTekMsrpWkList = null;
        String countrys = "";
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser sysUser = loginUser.getUser();
        if (sysUser.getUserId() == 1L) {
            startPage();
            newTekMsrpWkList = newTekMsrpWkService.queryNewTekMsrpWKByPage(req);
        } else {
            String countryNames = sysUser.getCountryNames();
            List<String> countryNameList = Arrays.asList(countryNames.split(","));
            for (int i = 0; i < countryNameList.size(); i++) {
                if (i == countryNameList.size() - 1) {
                    NewTekCountryCode newTekCountryCode = newTekCountryCodeService.getCountryCodeByCode(countryNameList.get(i));
                    countrys += newTekCountryCode.getCode() + "_" + newTekCountryCode.getChineseName() + "_" + newTekCountryCode.getEnglishName();
                } else {
                    NewTekCountryCode newTekCountryCode = newTekCountryCodeService.getCountryCodeByCode(countryNameList.get(i));
                    countrys += newTekCountryCode.getCode() + "_" + newTekCountryCode.getChineseName() + "_" + newTekCountryCode.getEnglishName() + ",";
                }
            }
            List<String> countryNameListNew = Arrays.asList(countrys.split(","));
            req.setCountryNameList(countryNameListNew);
            String customers = sysUser.getCustomerNames();
            List<String> customerList = Arrays.asList(customers.split(","));
            req.setCustomerList(customerList);
            startPage();
            newTekMsrpWkList = newTekMsrpWkService.queryNewTekMsrpWKByPageForPermission(req);
        }
        return getDataTable(newTekMsrpWkList);
    }

    /**
     * promotion calendar中点击日期或者promotion type显示对应的所有详情
     */
    @GetMapping(value = "/queryByDateAndPromotionType")
    public TableDataInfo queryByDateAndPromotionType(QueryMsrpWKReq req) {
        List<NewTekMsrpWk> newTekMsrpWkList = null;
        try {
            String countrys = "";
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            SysUser sysUser = loginUser.getUser();
            if (sysUser.getUserId() == 1L) {
                startPage();
                newTekMsrpWkList = newTekMsrpWkService.queryByDateAndPromotionType(req);
            } else {
                String countryNames = sysUser.getCountryNames();
                List<String> countryNameList = Arrays.asList(countryNames.split(","));
                for (int i = 0; i < countryNameList.size(); i++) {
                    if (i == countryNameList.size() - 1) {
                        NewTekCountryCode newTekCountryCode = newTekCountryCodeService.getCountryCodeByCode(countryNameList.get(i));
                        countrys += newTekCountryCode.getCode() + "_" + newTekCountryCode.getChineseName() + "_" + newTekCountryCode.getEnglishName();
                    } else {
                        NewTekCountryCode newTekCountryCode = newTekCountryCodeService.getCountryCodeByCode(countryNameList.get(i));
                        countrys += newTekCountryCode.getCode() + "_" + newTekCountryCode.getChineseName() + "_" + newTekCountryCode.getEnglishName() + ",";
                    }
                }
                List<String> countryNameListNew = Arrays.asList(countrys.split(","));
                req.setCountryNameList(countryNameListNew);
                String customers = sysUser.getCustomerNames();
                List<String> customerList = Arrays.asList(customers.split(","));
                req.setCustomerList(customerList);
                startPage();
                newTekMsrpWkList = newTekMsrpWkService.queryByDateAndPromotionTypeForPermission(req);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getDataTable(newTekMsrpWkList);
    }

    /**
     * Promotion List 列表
     */
    @GetMapping(value = "/queryPromotionListByPage")
    @PreAuthorize("@ss.hasPermi('promotion:list:list')")
    public TableDataInfo queryPromotionListByPage(QueryMsrpWKReq req) {
        List<NewTekPromotionListVO> promotionListVOList = null;
        String countrys = "";
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser sysUser = loginUser.getUser();
        if (sysUser.getUserId() == 1L) {
            startPage();
            promotionListVOList = newTekMsrpWkService.queryPromotionListByPage(req);
        } else {
            String countryNames = sysUser.getCountryNames();
            List<String> countryNameList = Arrays.asList(countryNames.split(","));
            for (int i = 0; i < countryNameList.size(); i++) {
                if (i == countryNameList.size() - 1) {
                    NewTekCountryCode newTekCountryCode = newTekCountryCodeService.getCountryCodeByCode(countryNameList.get(i));
                    countrys += newTekCountryCode.getCode() + "_" + newTekCountryCode.getChineseName() + "_" + newTekCountryCode.getEnglishName();
                } else {
                    NewTekCountryCode newTekCountryCode = newTekCountryCodeService.getCountryCodeByCode(countryNameList.get(i));
                    countrys += newTekCountryCode.getCode() + "_" + newTekCountryCode.getChineseName() + "_" + newTekCountryCode.getEnglishName() + ",";
                }
            }
            List<String> countryNameListNew = Arrays.asList(countrys.split(","));
            req.setCountryNameList(countryNameListNew);
            String customers = sysUser.getCustomerNames();
            List<String> customerList = Arrays.asList(customers.split(","));
            req.setCustomerList(customerList);
            startPage();
            promotionListVOList = newTekMsrpWkService.queryPromotionListByPageForPermission(req);
        }
        return getDataTable(promotionListVOList);
    }

    /**
     * Promotion Calendar 列表
     */
    @GetMapping(value = "/queryPromotionCalendarList")
    public AjaxResult queryPromotionCalendarList(String country) throws Exception {
        List<NewTekMsrpWk> newTekMsrpWkList = null;
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser sysUser = loginUser.getUser();
        if (sysUser.getUserId() == 1L) {
            NewTekMsrpWk newTekMsrpWk = new NewTekMsrpWk();
            if (country == null) {
                newTekMsrpWk.setCountry("US");
            } else {
                newTekMsrpWk.setCountry(country);
            }
            newTekMsrpWkList = newTekMsrpWkService.queryPromotionCalendarList(newTekMsrpWk);
        } else {
            String countryNames = sysUser.getCountryNames();
            List<String> countryList = Arrays.asList(countryNames.split(","));
            NewTekMsrpWk newTekMsrpWk = new NewTekMsrpWk();
            if (country == null) {
//                List<String> coutryAllList = new ArrayList<>();
//                for(String countryNew : countryList){
//                    NewTekCountryCode newTekCountryCode = newTekCountryCodeService.getCountryCodeByCode(countryNew);
//                    coutryAllList.add(newTekCountryCode.getCode() + "_" + newTekCountryCode.getChineseName() + "_" + newTekCountryCode.getEnglishName());
//                }
//                newTekMsrpWk.setCountryNameList(coutryAllList);
                newTekMsrpWk.setCountry(countryNames.split(",")[0]);
            } else if (country.equals("")) {
                List<String> coutryAllList = new ArrayList<>();
                for(String countryNew : countryList){
                    NewTekCountryCode newTekCountryCode = newTekCountryCodeService.getCountryCodeByCode(countryNew);
                    coutryAllList.add(newTekCountryCode.getCode() + "_" + newTekCountryCode.getChineseName() + "_" + newTekCountryCode.getEnglishName());
                }
                newTekMsrpWk.setCountryNameList(coutryAllList);
            } else {
                if (!country.contains("_")) {
                    NewTekCountryCode newTekCountryCode = newTekCountryCodeService.getCountryCodeByCode(country);
                    newTekMsrpWk.setCountry(newTekCountryCode.getCode() + "_" + newTekCountryCode.getChineseName() + "_" + newTekCountryCode.getEnglishName());
                } else {
                    newTekMsrpWk.setCountry(country);
                }
            }
            String customers = sysUser.getCustomerNames();
            List<String> customerList = Arrays.asList(customers.split(","));
            newTekMsrpWk.setCustomerList(customerList);
            newTekMsrpWkList = newTekMsrpWkService.queryPromotionCalendarListForPermission(newTekMsrpWk);
        }
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        for (NewTekMsrpWk newTekMsrpWk1 : newTekMsrpWkList) {
            String[] validFromWKGroupArr = newTekMsrpWk1.getValidFromWKGroup().split(",");
            String[] validToWKGroupArr = newTekMsrpWk1.getValidToWKGroup().split(",");
            String validFromWKFinal = null;
            String validToWKFinal = null;
            for (int i = 0; i < validFromWKGroupArr.length; i++) {
                if (i <= validFromWKGroupArr.length - 2) {
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
                if (validToWKGroupArr[i].equals("9999-12-31")) {
                    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    validToWKFinal = LocalDate.now().plusYears(5L).format(fmt);
                } else {
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
            }
            if (validFromWKFinal != null) {
                newTekMsrpWk1.setValidFromWK(validFromWKFinal);
            }
            if (validToWKFinal != null) {
                newTekMsrpWk1.setValidToWK(validToWKFinal);
            }
        }
        List<NewTekMsrpWk> newTekMsrpWkList1 = new ArrayList<>();
        for (NewTekMsrpWk newTekMsrpWk1 : newTekMsrpWkList) {
            String validFromWK = newTekMsrpWk1.getValidFromWK();
            String validToWK = newTekMsrpWk1.getValidToWK();
            List<String> dateList = DateUtils.addDates(validFromWK, validToWK);
            for (String date : dateList) {
                NewTekMsrpWk newTekMsrpWk2 = new NewTekMsrpWk();
                newTekMsrpWk2.setValidFromWK(date);
                newTekMsrpWk2.setPromotionType(newTekMsrpWk1.getCustomerName() + "-" + newTekMsrpWk1.getCountry().split("_")[0] + "-" + newTekMsrpWk1.getPromotionType());
                newTekMsrpWkList1.add(newTekMsrpWk2);
            }
        }
        return AjaxResult.success(newTekMsrpWkList1);
    }

    /**
     * 根据主键ID获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('customer:sellingPrice:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return AjaxResult.success(newTekMsrpWkService.getNewTekMsrpWKById(id));
    }

    /**
     * 新增
     */
    @PreAuthorize("@ss.hasPermi('customer:sellingPrice:add')")
    @Log(title = "产品周建议零售价管理", businessType = BusinessType.INSERT)
    @PostMapping(value = "add")
    public AjaxResult add(@Validated @RequestBody NewTekMsrpWk newTekMsrpWk) {
        String validFromWK = newTekMsrpWk.getValidFromWK();
        String validToWK = newTekMsrpWk.getValidToWK();
        String fromYear = validFromWK.split("-")[0];
        newTekMsrpWk.setYear(fromYear);
        LocalDate now = LocalDate.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fromWK = LocalDate.parse(validFromWK, fmt);
        LocalDate toWK = LocalDate.parse(validToWK, fmt);
        if (fromWK.compareTo(now) == -1 || toWK.compareTo(now) == -1) {
            return AjaxResult.error("当前时间只能维护未来周的Selling Price");
        }
        String customerName = newTekMsrpWk.getCustomerName();
        String country = newTekMsrpWk.getCountry();
        String sku = newTekMsrpWk.getSku();
        List<NewTekProductModel> productModelList = newTekProductModelService.getProductModelBySKU(sku);
        if (productModelList.size() == 0 || productModelList == null) {
            return AjaxResult.error("此SKU尚未维护！");
        }
        NewTekCountryCode newTekCountryCode = newTekCountryCodeService.getCountryCodeByCode(country.split("_")[0]);
        if (newTekCountryCode == null) {
            return AjaxResult.error("只能选择Country！");
        }
        List<NewTekCustomer> customerListForName = newTekCustomerService.getNewTekCustomerByCustomerName(customerName);
        if (customerListForName.size() == 0 || customerListForName == null) {
            return AjaxResult.error("只能选择CustomerName！");
        }
        QueryCustomerReq req = new QueryCustomerReq();
        req.setCountry(country);
        req.setCustomerName(customerName);
        List<NewTekCustomer> customerList = newTekCustomerService.getAllCustomer(req);
        if (customerList.size() > 0 && customerList != null) {
            newTekMsrpWk.setCustomerId(customerList.get(0).getId());
        }
        //根据Country、Customer、SKU、FROM时间、TO时间五个参数查询Selling Price，如果有数据，直接提示不给新增
        List<NewTekMsrpWk> newTekMsrpWkListCheck = newTekMsrpWkService.checkMsrpWkByCustomerAndSkuAndCountryAndFromAndTo(newTekMsrpWk);
        if (newTekMsrpWkListCheck.size() > 0 && newTekMsrpWkListCheck != null) {
            return AjaxResult.error("相同记录已存在，请重新输入！");
        }
        //根据sku、customerId、year、country查询已维护的周价格，如果没有冲突的周，就可以插入，如果有冲突，就报错
        List<NewTekMsrpWk> newTekMsrpWkList = newTekMsrpWkService.getMsrpWkByCustomerIdAndSku(newTekMsrpWk);
        if (newTekMsrpWkList.size() > 0 && newTekMsrpWkList != null) {
            for (NewTekMsrpWk newTekMsrpWk1 : newTekMsrpWkList) {
                LocalDate fromWKExist = LocalDate.parse(newTekMsrpWk1.getValidFromWK(), fmt);
                LocalDate toWKExist = LocalDate.parse(newTekMsrpWk1.getValidToWK(), fmt);
                if (fromWK.compareTo(fromWKExist) == 0 || toWK.compareTo(toWKExist) == 0 ||
                (fromWK.compareTo(fromWKExist) > 0 && toWK.compareTo(toWKExist) < 0) ||
                (fromWK.compareTo(fromWKExist) > 0 && toWK.compareTo(toWKExist) > 0) ||
                fromWK.compareTo(toWKExist) == 0) {
                    return AjaxResult.error("不能维护已存在的周期价格！");
                }
            }
        }
        NewTekMsrp newTekMsrp = new NewTekMsrp();
        newTekMsrp.setSku(sku);
        List<NewTekMsrp> newTekMsrpList = newTekMsrpService.getNewTekMsrpByCustomerAndProductAndSku(newTekMsrp);
        if (newTekMsrpList.size() > 0 && newTekMsrpList != null) {
            newTekMsrpWk.setMsrpId(newTekMsrpList.get(0).getId());
        }
        newTekMsrpWk.setCreateBy(SecurityUtils.getUsername());
        newTekMsrpWk.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(newTekMsrpWkService.insertNewTekMsrpWk(newTekMsrpWk));
    }


    /**
     * 修改产品周建议零售价
     */
    @PreAuthorize("@ss.hasPermi('customer:sellingPrice:edit')")
    @Log(title = "产品周建议零售价管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult editMsrpWK(@RequestBody NewTekMsrpWk newTekMsrpWk) {
        int count = 0;
        try {
            BigDecimal sellingPrice = newTekMsrpWk.getSellingPrice();
            Long id = newTekMsrpWk.getId();
            NewTekMsrpWKVO newTekMsrpWKVO = newTekMsrpWkService.getNewTekMsrpWKById(id);
            String customerName = newTekMsrpWk.getCustomerName();
            String country = newTekMsrpWk.getCountry();
            String sku = newTekMsrpWk.getSku();
            List<NewTekProductModel> productModelList = newTekProductModelService.getProductModelBySKU(sku);
            if (productModelList.size() == 0 || productModelList == null) {
                return AjaxResult.error("此SKU尚未维护！");
            }
            NewTekCountryCode newTekCountryCode = newTekCountryCodeService.getCountryCodeByCode(country.split("_")[0]);
            if (newTekCountryCode == null) {
                return AjaxResult.error("只能选择Country！");
            }
            List<NewTekCustomer> customerListForName = newTekCustomerService.getNewTekCustomerByCustomerName(customerName);
            if (customerListForName.size() == 0 || customerListForName == null) {
                return AjaxResult.error("只能选择CustomerName！");
            }
            //根据Country、Customer、SKU、FROM时间、TO时间五个参数查询Selling Price，如果有数据，直接提示不给新增
            List<NewTekMsrpWk> newTekMsrpWkListCheck = newTekMsrpWkService.checkMsrpWkByCustomerAndSkuAndCountryAndFromAndTo(newTekMsrpWk);
            if (newTekMsrpWkListCheck.size() > 0 && newTekMsrpWkListCheck != null) {
                return AjaxResult.error("相同记录已存在，请重新输入！");
            }
            //本来的值
            String year = newTekMsrpWKVO.getYear();
            String validFromWK = newTekMsrpWKVO.getValidFromWK();
            String validToWK = newTekMsrpWKVO.getValidToWK();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            //修改的值
            String validFromWKNew = newTekMsrpWk.getValidFromWK();
            String validToWKNew = newTekMsrpWk.getValidToWK();
            String remark = newTekMsrpWk.getRemark();
            Date validFrom = sf.parse(validFromWKNew);
            Date validTo = sf.parse(validToWKNew);
            Date date = new Date();
            String now = sf.format(date);
            if ((validFrom.compareTo(sf.parse(validFromWK)) == -1 && validFrom.compareTo(sf.parse(now)) == -1) || validTo.compareTo(sf.parse(validToWK)) == -1) {
                return AjaxResult.error("不能修改历史数据");
            }
            NewTekMsrpWk newTekMsrpWk1 = new NewTekMsrpWk();
            newTekMsrpWk1.setCountry(newTekMsrpWKVO.getCountry());
            newTekMsrpWk1.setSku(newTekMsrpWKVO.getSku());
            newTekMsrpWk1.setCustomerId(newTekMsrpWKVO.getCustomerId());
            newTekMsrpWk1.setValidFromWK(validFromWKNew);
            newTekMsrpWk1.setValidToWK(validToWKNew);
            List<NewTekMsrpWk> newTekMsrpWkList = newTekMsrpWkService.getMsrpWkByCustomerIdAndSku(newTekMsrpWk1);
            for (NewTekMsrpWk newTekMsrpWk2 : newTekMsrpWkList) {
                String validFromWK2 = newTekMsrpWk2.getValidFromWK();
                String validToWK2 = newTekMsrpWk2.getValidToWK();
                Date validFromWK2Date = sf.parse(validFromWK2);
                Date validToWK2Date = sf.parse(validToWK2);
                //比较要修改的日期是不是已经存在一个价格区间了，如果已有，不给修改
                if ((validFrom.compareTo(validFromWK2Date) >= 0 && validFrom.compareTo(validToWK2Date) <= 0 && newTekMsrpWk2.getId() != id) || (validTo.compareTo(validFromWK2Date) >= 0 && validTo.compareTo(validToWK2Date) <= 0 && newTekMsrpWk2.getId() != id)) {
                    return AjaxResult.error("不能修改已存在的价格区间");
                }
                //不修改时间，只修改其他内容
                if (newTekMsrpWk2.getId().equals(newTekMsrpWk.getId()) && validFrom.compareTo(validFromWK2Date) == 0 && validTo.compareTo(validToWK2Date) == 0) {
                    newTekMsrpWk2.setSku(newTekMsrpWk.getSku());
                    newTekMsrpWk2.setCountry(country);
                    newTekMsrpWk2.setCustomerName(customerName);
                    newTekMsrpWk2.setCurrencyType(newTekMsrpWk.getCurrencyType());
                    newTekMsrpWk2.setPromotionType(newTekMsrpWk.getPromotionType());
                    newTekMsrpWk2.setSellingPrice(newTekMsrpWk.getSellingPrice());
                    newTekMsrpWk2.setRemark(remark);
                    newTekMsrpWk2.setUpdateBy(SecurityUtils.getUsername());
                    count = newTekMsrpWkService.updateNewTekMsrpWk(newTekMsrpWk2);
                }
                //s1:修改的开始时间大于原开始时间，且结束时间没有变，则总共二条：原开始时间-现开始时间的selling Price记录(价格不变)，修改的开始时间+1天-原结束时间记录(价格跟着修改价格走)
                if (validFrom.compareTo(validFromWK2Date) > 0 && validTo.compareTo(validToWK2Date) == 0) {
                    NewTekMsrpWk newTekMsrpWk3 = new NewTekMsrpWk();
                    c.setTime(validFrom);
                    c.add(Calendar.DAY_OF_MONTH, 1);
                    Date tomorrow = c.getTime();
                    newTekMsrpWk3.setYear(sf.format(tomorrow).split("-")[0]);
                    newTekMsrpWk3.setValidFromWK(sf.format(tomorrow));
                    newTekMsrpWk3.setValidToWK(validToWK2);
                    newTekMsrpWk3.setSellingPrice(sellingPrice);
                    newTekMsrpWk3.setCurrencyType(newTekMsrpWk.getCurrencyType());
                    newTekMsrpWk3.setSku(newTekMsrpWk.getSku());
                    newTekMsrpWk3.setCountry(country);
                    newTekMsrpWk3.setCustomerName(customerName);
                    newTekMsrpWk3.setCreateBy(SecurityUtils.getUsername());
                    newTekMsrpWk3.setUpdateBy(SecurityUtils.getUsername());
                    QueryCustomerReq req = new QueryCustomerReq();
                    req.setCountry(country);
                    req.setCustomerName(customerName);
                    List<NewTekCustomer> customerList = newTekCustomerService.getAllCustomer(req);
                    if (customerList.size() > 0 && customerList != null) {
                        newTekMsrpWk3.setCustomerId(customerList.get(0).getId());
                    }
                    newTekMsrpWkService.insertNewTekMsrpWk(newTekMsrpWk3);

                    newTekMsrpWk2.setValidToWK(validToWKNew);
                    newTekMsrpWk2.setUpdateBy(SecurityUtils.getUsername());
                    newTekMsrpWk2.setRemark(remark);
                    count = newTekMsrpWkService.updateNewTekMsrpWk(newTekMsrpWk2);
                }
                //s2:修改的结束时间小于原结束时间，且开始时间没有变，则总共二条：原开始时间-现结束时间的selling price记录（价格要变），修改原记录的开始时间为现结束时间+1天-原结束时间（价格不变）
                if (validTo.compareTo(validToWK2Date) < 0 && validFrom.compareTo(validFromWK2Date) == 0) {
                    NewTekMsrpWk newTekMsrpWk4 = new NewTekMsrpWk();
                    c.setTime(validTo);
                    c.add(Calendar.DAY_OF_MONTH, 1);
                    Date tomorrow = c.getTime();
                    newTekMsrpWk4.setYear(validFromWK.split("-")[0]);
                    newTekMsrpWk4.setValidFromWK(validFromWK);
                    newTekMsrpWk4.setValidToWK(validToWKNew);
                    newTekMsrpWk4.setSellingPrice(sellingPrice);
                    newTekMsrpWk4.setCurrencyType(newTekMsrpWk.getCurrencyType());
                    newTekMsrpWk4.setSku(newTekMsrpWk.getSku());
                    newTekMsrpWk4.setCountry(country);
                    newTekMsrpWk4.setCustomerName(customerName);
                    newTekMsrpWk4.setCreateBy(SecurityUtils.getUsername());
                    newTekMsrpWk4.setUpdateBy(SecurityUtils.getUsername());
                    QueryCustomerReq req = new QueryCustomerReq();
                    req.setCountry(country);
                    req.setCustomerName(customerName);
                    List<NewTekCustomer> customerList = newTekCustomerService.getAllCustomer(req);
                    if (customerList.size() > 0 && customerList != null) {
                        newTekMsrpWk4.setCustomerId(customerList.get(0).getId());
                    }
                    newTekMsrpWkService.insertNewTekMsrpWk(newTekMsrpWk4);

                    newTekMsrpWk2.setValidFromWK(sf.format(tomorrow));
                    newTekMsrpWk2.setUpdateBy(SecurityUtils.getUsername());
                    newTekMsrpWk2.setRemark(remark);
                    count = newTekMsrpWkService.updateNewTekMsrpWk(newTekMsrpWk2);
                }
                //s3:修改的开始时间大于原开始时间，修改的结束时间小于原结束时间，则总共三条：原开始时间-现开始时间天的selling price记录，现开始时间+1天-现结束时间的记录，现结束时间+1天-原结束时间的记录
                if (validFrom.compareTo(validFromWK2Date) > 0 && validTo.compareTo(validToWK2Date) < 0) {
                    NewTekMsrpWk newTekMsrpWk5 = new NewTekMsrpWk();
                    c.setTime(validFrom);
                    c.add(Calendar.DAY_OF_MONTH, 1);
                    Date tomorrow = c.getTime();
                    newTekMsrpWk5.setYear(validFromWK.split("-")[0]);
                    newTekMsrpWk5.setValidFromWK(validFromWK);
                    newTekMsrpWk5.setValidToWK(validFromWKNew);
                    newTekMsrpWk5.setSellingPrice(sellingPrice);
                    newTekMsrpWk5.setCurrencyType(newTekMsrpWk.getCurrencyType());
                    newTekMsrpWk5.setSku(newTekMsrpWk.getSku());
                    newTekMsrpWk5.setCountry(country);
                    newTekMsrpWk5.setCustomerName(customerName);
                    newTekMsrpWk5.setCreateBy(SecurityUtils.getUsername());
                    newTekMsrpWk5.setUpdateBy(SecurityUtils.getUsername());
                    QueryCustomerReq req = new QueryCustomerReq();
                    req.setCountry(country);
                    req.setCustomerName(customerName);
                    List<NewTekCustomer> customerList = newTekCustomerService.getAllCustomer(req);
                    if (customerList.size() > 0 && customerList != null) {
                        newTekMsrpWk5.setCustomerId(customerList.get(0).getId());
                    }
                    newTekMsrpWkService.insertNewTekMsrpWk(newTekMsrpWk5);

                    NewTekMsrpWk newTekMsrpWk6 = new NewTekMsrpWk();
                    c.setTime(validTo);
                    c.add(Calendar.DAY_OF_MONTH, 1);
                    Date tomorrow1 = c.getTime();
                    newTekMsrpWk6.setYear(sf.format(tomorrow1).split("-")[0]);
                    newTekMsrpWk6.setValidFromWK(sf.format(tomorrow1));
                    newTekMsrpWk6.setValidToWK(validToWK);
                    newTekMsrpWk6.setSellingPrice(sellingPrice);
                    newTekMsrpWk6.setCurrencyType(newTekMsrpWk.getCurrencyType());
                    newTekMsrpWk6.setSku(newTekMsrpWk.getSku());
                    newTekMsrpWk6.setCountry(country);
                    newTekMsrpWk6.setCustomerName(customerName);
                    newTekMsrpWk6.setCreateBy(SecurityUtils.getUsername());
                    newTekMsrpWk6.setUpdateBy(SecurityUtils.getUsername());
                    QueryCustomerReq req1 = new QueryCustomerReq();
                    req1.setCountry(country);
                    req1.setCustomerName(customerName);
                    List<NewTekCustomer> customerList1 = newTekCustomerService.getAllCustomer(req1);
                    if (customerList1.size() > 0 && customerList1 != null) {
                        newTekMsrpWk6.setCustomerId(customerList1.get(0).getId());
                    }
                    newTekMsrpWkService.insertNewTekMsrpWk(newTekMsrpWk6);

                    newTekMsrpWk2.setValidFromWK(sf.format(tomorrow));
                    newTekMsrpWk2.setValidToWK(validToWKNew);
                    newTekMsrpWk2.setUpdateBy(SecurityUtils.getUsername());
                    newTekMsrpWk2.setRemark(remark);
                    count = newTekMsrpWkService.updateNewTekMsrpWk(newTekMsrpWk2);
                }

                //s4:修改的开始时间大于原开始时间，大于原结束时间，且修改的结束时间大于修改的开始时间，就只改变时间，不改变其他
                if (validFrom.compareTo(validFromWK2Date) > 0 && validFrom.compareTo(validToWK2Date) > 0 && validTo.compareTo(validFrom) > 0) {
                    newTekMsrpWk2.setValidFromWK(validFromWKNew);
                    newTekMsrpWk2.setValidToWK(validToWKNew);
                    newTekMsrpWk2.setUpdateBy(SecurityUtils.getUsername());
                    count = newTekMsrpWkService.updateNewTekMsrpWk(newTekMsrpWk2);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.success(count);
    }

    /**
     * 删除产品系列
     */
    @PreAuthorize("@ss.hasPermi('customer:sellingPrice:remove')")
    @Log(title = "产品周建议零售价管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(newTekMsrpWkService.deleteMsrpWKByIds(ids));
    }

    @Log(title = "产品周建议零售价管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('customer:sellingPrice:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<NewTekMsrpWk> util = new ExcelUtil<NewTekMsrpWk>(NewTekMsrpWk.class);
        List<NewTekMsrpWk> msrpWkList = util.importExcel(file.getInputStream());
        String message = newTekMsrpWkService.importSellingPrice(msrpWkList, updateSupport);
        return AjaxResult.success(message);
    }

    @GetMapping("/importTemplate")
    public AjaxResult importTemplate() {
        ExcelUtil<NewTekMsrpWk> util = new ExcelUtil<NewTekMsrpWk>(NewTekMsrpWk.class);
        return util.importTemplateExcel("SellingPrice数据");
    }

    @Log(title = "产品周建议零售价管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('customer:sellingPrice:export')")
    @GetMapping("/export")
    public AjaxResult export(QueryMsrpWKReq req) {
//        List<NewTekMsrpWKVO> list = newTekMsrpWkService.queryNewTekMsrpWKByPage(req);
        List<NewTekMsrpWKVO> list = null;
        String countrys = "";
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser sysUser = loginUser.getUser();
        if (sysUser.getUserId() == 1L) {
            list = newTekMsrpWkService.queryNewTekMsrpWKByPage(req);
        } else {
            String countryNames = sysUser.getCountryNames();
            List<String> countryNameList = Arrays.asList(countryNames.split(","));
            for (int i = 0; i < countryNameList.size(); i++) {
                if (i == countryNameList.size() - 1) {
                    NewTekCountryCode newTekCountryCode = newTekCountryCodeService.getCountryCodeByCode(countryNameList.get(i));
                    countrys += newTekCountryCode.getCode() + "_" + newTekCountryCode.getChineseName() + "_" + newTekCountryCode.getEnglishName();
                } else {
                    NewTekCountryCode newTekCountryCode = newTekCountryCodeService.getCountryCodeByCode(countryNameList.get(i));
                    countrys += newTekCountryCode.getCode() + "_" + newTekCountryCode.getChineseName() + "_" + newTekCountryCode.getEnglishName() + ",";
                }
            }
            List<String> countryNameListNew = Arrays.asList(countrys.split(","));
            req.setCountryNameList(countryNameListNew);
            String customers = sysUser.getCustomerNames();
            List<String> customerList = Arrays.asList(customers.split(","));
            req.setCustomerList(customerList);
            list = newTekMsrpWkService.queryNewTekMsrpWKByPageForPermission(req);
        }
        for (NewTekMsrpWKVO newTekMsrpWKVO : list) {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String create = sf.format(newTekMsrpWKVO.getCreateTime());
            String update = sf.format(newTekMsrpWKVO.getUpdateTime());
            newTekMsrpWKVO.setCreateTimeStr(create);
            newTekMsrpWKVO.setUpdateTimeStr(update);
        }
        ExcelUtil<NewTekMsrpWKVO> util = new ExcelUtil<NewTekMsrpWKVO>(NewTekMsrpWKVO.class);
        return util.exportExcel(list, "SellingPrice导出数据");
    }

    @Log(title = "产品周建议零售价管理", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('system:config:export')")
    @GetMapping("/exportPromotionList")
    public AjaxResult exportPromotionList(QueryMsrpWKReq req) {
//        List<NewTekPromotionListVO> list = newTekMsrpWkService.queryPromotionListByPage(req);
        List<NewTekPromotionListVO> list = null;
        String countrys = "";
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser sysUser = loginUser.getUser();
        if (sysUser.getUserId() == 1L) {
            list = newTekMsrpWkService.queryPromotionListByPage(req);
        } else {
            String countryNames = sysUser.getCountryNames();
            List<String> countryNameList = Arrays.asList(countryNames.split(","));
            for (int i = 0; i < countryNameList.size(); i++) {
                if (i == countryNameList.size() - 1) {
                    NewTekCountryCode newTekCountryCode = newTekCountryCodeService.getCountryCodeByCode(countryNameList.get(i));
                    countrys += newTekCountryCode.getCode() + "_" + newTekCountryCode.getChineseName() + "_" + newTekCountryCode.getEnglishName();
                } else {
                    NewTekCountryCode newTekCountryCode = newTekCountryCodeService.getCountryCodeByCode(countryNameList.get(i));
                    countrys += newTekCountryCode.getCode() + "_" + newTekCountryCode.getChineseName() + "_" + newTekCountryCode.getEnglishName() + ",";
                }
            }
            List<String> countryNameListNew = Arrays.asList(countrys.split(","));
            req.setCountryNameList(countryNameListNew);
            String customers = sysUser.getCustomerNames();
            List<String> customerList = Arrays.asList(customers.split(","));
            req.setCustomerList(customerList);
            list = newTekMsrpWkService.queryPromotionListByPageForPermission(req);
        }
        for (NewTekPromotionListVO promotionListVO : list) {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String create = sf.format(promotionListVO.getCreateTime());
            String update = sf.format(promotionListVO.getUpdateTime());
            promotionListVO.setCreateTimeStr(create);
            promotionListVO.setUpdateTimeStr(update);
        }
        ExcelUtil<NewTekPromotionListVO> util = new ExcelUtil<NewTekPromotionListVO>(NewTekPromotionListVO.class);
        return util.exportExcel(list, "PromotionList数据");
    }

    @GetMapping("/queryMsrpWKCountryList")
    public AjaxResult queryMsrpWKCountryList() {
        List<NewTekMsrpWk> list = newTekMsrpWkService.queryMsrpWKCountryList();
        for (NewTekMsrpWk newTekMsrpWk : list) {
            newTekMsrpWk.setCountry(newTekMsrpWk.getCountry().split("_")[0]);
        }
        return AjaxResult.success(list);
    }
}
