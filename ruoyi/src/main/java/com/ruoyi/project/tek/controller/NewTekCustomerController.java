package com.ruoyi.project.tek.controller;

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
import com.ruoyi.project.tek.request.QueryCustomerReq;
import com.ruoyi.project.tek.service.INewTekCountryCodeService;
import com.ruoyi.project.tek.service.INewTekCustomerService;
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

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

/**
 * @author steven.guo
 * @version 1.0
 * @description:
 * @date 2020/6/3 10:55
 */
@RestController
@RequestMapping("/tek/newTekCustomer")
public class NewTekCustomerController extends BaseController {

    @Autowired
    private INewTekCustomerService newTekCustomerService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private INewTekCountryCodeService newTekCountryCodeService;

    @PreAuthorize("@ss.hasPermi('customer:data:list')")
    @GetMapping(value = "/queryNewTekCustomerAll")
    public TableDataInfo queryNewTekCustomerAll(QueryCustomerReq req) {
        List<NewTekCustomer> newTekCustomerList = null;
        String countrys = "";
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser sysUser = loginUser.getUser();
        if (sysUser.getUserId() == 1L) {
            startPage();
            newTekCustomerList = newTekCustomerService.getAllCustomer(req);
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
            startPage();
            newTekCustomerList = newTekCustomerService.getCustomerListPageForPermission(req);
        }
        return getDataTable(newTekCustomerList);
    }

    /**
     * 根据产品系列编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('customer:data:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        NewTekCustomer newTekCustomer = newTekCustomerService.getNewTekCustomerById(id);
        return AjaxResult.success(newTekCustomer);
    }

    /**
     * 新增客户信息
     */
    @PreAuthorize("@ss.hasPermi('customer:data:add')")
    @Log(title = "客户信息管理", businessType = BusinessType.INSERT)
    @PostMapping(value = "add")
    public AjaxResult add(@Validated @RequestBody NewTekCustomer newTekCustomer) {
        String country = newTekCustomer.getCountry();
        NewTekCountryCode newTekCountryCode = newTekCountryCodeService.getCountryCodeByCode(country.split("_")[0]);
        if (newTekCountryCode == null) {
            return AjaxResult.error("只能选择Country！");
        }
        newTekCustomer.setCreateBy(SecurityUtils.getUsername());
        newTekCustomer.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(newTekCustomerService.insertNewTekCustomer(newTekCustomer));
    }
//
//

    /**
     * 修改客户信息
     */
    @PreAuthorize("@ss.hasPermi('customer:data:edit')")
    @Log(title = "客户信息管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody NewTekCustomer newTekCustomer) {
        String country = newTekCustomer.getCountry();
        NewTekCountryCode newTekCountryCode = newTekCountryCodeService.getCountryCodeByCode(country.split("_")[0]);
        if (newTekCountryCode == null) {
            return AjaxResult.error("只能选择Country！");
        }
        newTekCustomer.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(newTekCustomerService.updateNewTekCustomer(newTekCustomer));
    }
//
//

    /**
     * 批量删除客户信息
     */
    @PreAuthorize("@ss.hasPermi('customer:data:remove')")
    @Log(title = "客户信息管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(newTekCustomerService.deleteCustomerByIds(ids));
    }

    /**
     * 查询能用的所有客户
     */
    @GetMapping("/getAllCustomer/{country}")
    public AjaxResult getAllCustomer(@PathVariable("country") String country) {
        QueryCustomerReq req = new QueryCustomerReq();
        if (country != null && !"undefined".equals(country)) {
            req.setCountry(country);
            if(!country.contains("_")){
                NewTekCountryCode newTekCountryCode = newTekCountryCodeService.getCountryCodeByCode(country);
                req.setCountry(newTekCountryCode.getCode() + "_" + newTekCountryCode.getChineseName() + "_" + newTekCountryCode.getEnglishName());
            }
        }
        List<NewTekCustomer> newTekCustomerList = newTekCustomerService.getAllCustomerForSelect(req);
        return AjaxResult.success(newTekCustomerList);
    }

    @Log(title = "客户信息管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('customer:data:export')")
    @GetMapping("/export")
    public AjaxResult export(QueryCustomerReq req) {
//        List<NewTekCustomer> list = newTekCustomerService.getAllCustomer(req);
        List<NewTekCustomer> list = null;
        String countrys = "";
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser sysUser = loginUser.getUser();
        if (sysUser.getUserId() == 1L) {
            list = newTekCustomerService.getAllCustomer(req);
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
            list = newTekCustomerService.getCustomerListPageForPermission(req);
        }
        for (NewTekCustomer newTekCustomer : list) {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String create = sf.format(newTekCustomer.getCreateTime());
            String update = sf.format(newTekCustomer.getUpdateTime());
            newTekCustomer.setCreateTimeStr(create);
            newTekCustomer.setUpdateTimeStr(update);
        }
        ExcelUtil<NewTekCustomer> util = new ExcelUtil<NewTekCustomer>(NewTekCustomer.class);
        return util.exportExcel(list, "Customer数据");
    }

    /**
     * 权限中，查询所有可选择的客户
     */
    @GetMapping(value = "/getAllCustomerForPermission")
    public AjaxResult getAllCustomerForPermission() {
        List<NewTekCustomer> customerList = newTekCustomerService.getAllCustomerForPermission();
        return AjaxResult.success(customerList);
    }

    /**
     * 根据用户权限查询可选择的客户
     */
    @GetMapping(value = "/getCustomerInfoForPermission")
    public AjaxResult getCustomerInfoForPermission() {
        List<NewTekCustomer> customerList = null;
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser sysUser = loginUser.getUser();
        if (sysUser.getUserId() == 1L) {
            customerList = newTekCustomerService.getAllCustomerForPermission();
        } else {
            String customers = sysUser.getCustomerNames();
            List<String> customersList = Arrays.asList(customers.split(","));
            QueryCustomerReq req = new QueryCustomerReq();
            req.setCustomerNameList(customersList);
            customerList = newTekCustomerService.getCustomerInfoForPermission(req);
        }
        return AjaxResult.success(customerList);
    }

}
