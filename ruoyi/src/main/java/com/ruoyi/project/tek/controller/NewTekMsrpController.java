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
import com.ruoyi.project.tek.domain.NewTekMsrp;
import com.ruoyi.project.tek.domain.NewTekProductModel;
import com.ruoyi.project.tek.request.QueryMsrpReq;
import com.ruoyi.project.tek.service.INewTekCountryCodeService;
import com.ruoyi.project.tek.service.INewTekMsrpService;
import com.ruoyi.project.tek.service.INewTekProductModelService;
import com.ruoyi.project.tek.vo.NewTekMsrpVO;
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author steven.guo
 * @version 1.0
 * @description:
 * @date 2020/6/3 10:55
 */
@RestController
@RequestMapping("/tek/newTekMsrp")
public class NewTekMsrpController extends BaseController {

    @Autowired
    private INewTekMsrpService newTekMsrpService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private INewTekCountryCodeService newTekCountryCodeService;
    @Autowired
    private INewTekProductModelService newTekProductModelService;

    @PreAuthorize("@ss.hasPermi('product:msrp:list')")
    @GetMapping(value = "/queryNewTekMsrpByPage")
    public TableDataInfo queryNewTekMsrpByPage(QueryMsrpReq req) {
        List<NewTekMsrpVO> newTekMsrpVOList = null;
        String countrys = "";
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser sysUser = loginUser.getUser();
        if (sysUser.getUserId() == 1L) {
            startPage();
            newTekMsrpVOList = newTekMsrpService.queryNewTekMsrpByPage(req);
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
            newTekMsrpVOList = newTekMsrpService.queryNewTekMsrpByPageForPermission(req);
        }
        return getDataTable(newTekMsrpVOList);
    }

    /**
     * 根据主键ID获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('product:msrp:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return AjaxResult.success(newTekMsrpService.getNewTekMsrpVOById(id));
    }

    /**
     * 新增
     */
    @PreAuthorize("@ss.hasPermi('product:msrp:add')")
    @Log(title = "产品建议零售价管理", businessType = BusinessType.INSERT)
    @PostMapping(value = "add")
    public AjaxResult add(@Validated @RequestBody NewTekMsrp newTekMsrp) {
        int rows = 0;
        BigDecimal msrpOther = newTekMsrp.getMsrpOther();
        if (newTekMsrp.getMsrpOther() != null && (msrpOther.compareTo(new BigDecimal(31)) == -1 || msrpOther.compareTo(new BigDecimal(101)) == 1)) {
            return AjaxResult.error("添加其他建议零售价失败，不能小于30或者大于100！");
        }
        //一个SKU在一个有效期内只能有一个价格，判断
        String sku = newTekMsrp.getSku();
        String country = newTekMsrp.getCountry();
        Date validFrom = newTekMsrp.getValidFrom();
        Date validTo = newTekMsrp.getValidTo();

        List<NewTekProductModel> productModelList = newTekProductModelService.getProductModelBySKU(sku);
        if (productModelList.size() == 0 || productModelList == null) {
            return AjaxResult.error("此SKU尚未维护！");
        }
        NewTekCountryCode newTekCountryCode = newTekCountryCodeService.getCountryCodeByCode(country.split("_")[0]);
        if (newTekCountryCode == null) {
            return AjaxResult.error("只能选择Country！");
        }

        NewTekMsrp newTekMsrp1 = new NewTekMsrp();
        newTekMsrp1.setSku(sku);
        newTekMsrp1.setCountry(country);
        List<NewTekMsrp> newTekMsrpList = newTekMsrpService.getNewTekMsrpByCustomerAndProductAndSku(newTekMsrp1);
        if (newTekMsrpList.size() > 0 && newTekMsrpList != null) {
            for (NewTekMsrp newTekMsrp2 : newTekMsrpList) {
                if (newTekMsrp2.getCountry().equals(country)) {
                    if ((validFrom.compareTo(newTekMsrp2.getValidFrom()) > 0 && validFrom.compareTo(newTekMsrp2.getValidTo()) < 0)
                            || (validTo.compareTo(newTekMsrp2.getValidFrom()) > 0 && validTo.compareTo(newTekMsrp2.getValidTo()) < 0)) {
                        return AjaxResult.error("一个SKU在一个有效期内只能有一个价格！");
                    }
                }
            }
        }
        newTekMsrp.setCreateBy(SecurityUtils.getUsername());
        newTekMsrp.setUpdateBy(SecurityUtils.getUsername());
        Calendar c = Calendar.getInstance();
        c.setTime(validFrom);
        int yearr = c.get(Calendar.YEAR);
        newTekMsrp.setYear(String.valueOf(yearr));
        rows = newTekMsrpService.insertNewTekMsrp(newTekMsrp);
        return toAjax(rows);
    }

    /**
     * 修改产品建议零售价
     */
    @PreAuthorize("@ss.hasPermi('product:msrp:edit')")
    @Log(title = "产品建议零售价管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult editMsrpOther(@RequestBody NewTekMsrp newTekMsrp) {
        String sku = newTekMsrp.getSku();
        String country = newTekMsrp.getCountry();
        List<NewTekProductModel> productModelList = newTekProductModelService.getProductModelBySKU(sku);
        if (productModelList.size() == 0 || productModelList == null) {
            return AjaxResult.error("此SKU尚未维护！");
        }
        NewTekCountryCode newTekCountryCode = newTekCountryCodeService.getCountryCodeByCode(country.split("_")[0]);
        if (newTekCountryCode == null) {
            return AjaxResult.error("只能选择Country！");
        }
        newTekMsrp.setUpdateBy(SecurityUtils.getUsername());
        return AjaxResult.success(newTekMsrpService.updateNewTekMsrpOther(newTekMsrp));
    }


    /**
     * 删除产品系列
     */
    @PreAuthorize("@ss.hasPermi('product:msrp:remove')")
    @Log(title = "产品建议零售价管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(newTekMsrpService.deleteMsrpByIds(ids));
    }

    @Log(title = "产品建议零售价管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('product:msrp:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<NewTekMsrp> util = new ExcelUtil<NewTekMsrp>(NewTekMsrp.class);
        List<NewTekMsrp> msrpList = util.importExcel(file.getInputStream());
        String message = newTekMsrpService.importMSRP(msrpList, updateSupport);
        return AjaxResult.success(message);
    }

    @GetMapping("/importTemplate")
    public AjaxResult importTemplate() {
        ExcelUtil<NewTekMsrp> util = new ExcelUtil<NewTekMsrp>(NewTekMsrp.class);
        return util.importTemplateExcel("MSRP数据");
    }

    @Log(title = "产品建议零售价管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('product:msrp:export')")
    @GetMapping("/export")
    public AjaxResult export(QueryMsrpReq req) {
        List<NewTekMsrpVO> list = null;
        String countrys = "";
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser sysUser = loginUser.getUser();
        if (sysUser.getUserId() == 1L) {
            list = newTekMsrpService.queryNewTekMsrpByPage(req);
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
            list = newTekMsrpService.queryNewTekMsrpByPageForPermission(req);
        }
        for (NewTekMsrpVO newTekMsrpVO : list) {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String create = sf.format(newTekMsrpVO.getCreateTime());
            String update = sf.format(newTekMsrpVO.getUpdateTime());
            newTekMsrpVO.setCreateTimeStr(create);
            newTekMsrpVO.setUpdateTimeStr(update);

            SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
            String ValidFrom = sf1.format(newTekMsrpVO.getValidFrom());
            String ValidTo = sf1.format(newTekMsrpVO.getValidTo());
            newTekMsrpVO.setValidFromStr(ValidFrom);
            newTekMsrpVO.setValidToStr(ValidTo);
        }
        ExcelUtil<NewTekMsrpVO> util = new ExcelUtil<NewTekMsrpVO>(NewTekMsrpVO.class);
        return util.exportExcel(list, "MSRP数据");
    }

}
