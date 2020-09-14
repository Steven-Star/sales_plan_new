package com.ruoyi.project.tek.controller;

import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.framework.security.service.TokenService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.system.domain.SysUser;
import com.ruoyi.project.tek.domain.NewTekCountryCode;
import com.ruoyi.project.tek.domain.NewTekCurrencyCode;
import com.ruoyi.project.tek.domain.NewTekProductModel;
import com.ruoyi.project.tek.service.INewTekCountryCodeService;
import com.ruoyi.project.tek.service.INewTekCurrencyCodeService;
import com.ruoyi.project.tek.service.INewTekProductModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @author steven.guo
 * @version 1.0
 * @description:
 * @date 2020/7/08 13:39
 */
@RestController
@RequestMapping("/tek/newTekCountryCode")
public class NewTekCountryCodeController extends BaseController {

    @Autowired
    private INewTekCountryCodeService newTekCountryCodeService;
    @Autowired
    private INewTekProductModelService newTekProductModelService;
    @Autowired
    private TokenService tokenService;


    @GetMapping(value = "/queryNewTekCountryCodeAll")
    public AjaxResult queryNewTekCountryCodeAll(String code) {
        String codeFinal = "";
        if (code != null) {
            List<NewTekProductModel> newTekProductModelList = newTekProductModelService.getProductModelBySKU(code);
            for (int i = 0; i < newTekProductModelList.size(); i++) {
                String country = newTekProductModelList.get(i).getCountryName();
                String coutrySplit = country.split("_")[0];
                if (i == newTekProductModelList.size() - 1) {
                    codeFinal += coutrySplit;
                } else {
                    codeFinal += coutrySplit + ",";
                }
            }
        }
        List<NewTekCountryCode> newTekCountryCodeList = newTekCountryCodeService.queryNewTekCountryCodeAll(codeFinal);
        for (NewTekCountryCode newTekCountryCode : newTekCountryCodeList) {
            newTekCountryCode.setChineseName(newTekCountryCode.getCode() + "_" + newTekCountryCode.getChineseName() + "_" + newTekCountryCode.getEnglishName());
        }
        return AjaxResult.success("success", newTekCountryCodeList);
    }

    @GetMapping(value = "/queryCountryCodeForPermission")
    public AjaxResult queryCountryCodeForPermission() {
        List<NewTekCountryCode> newTekCountryCodeList = null;
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser sysUser = loginUser.getUser();
        if (sysUser.getUserId() == 1L) {
            newTekCountryCodeList = newTekCountryCodeService.queryNewTekCountryCodeAll(null);
        } else {
            String countryNames = sysUser.getCountryNames();
            List<String> countryNameList = Arrays.asList(countryNames.split(","));
            NewTekCountryCode newTekCountryCode = new NewTekCountryCode();
            newTekCountryCode.setCountryNameList(countryNameList);
            newTekCountryCodeList = newTekCountryCodeService.queryNewTekCountryCodeAllForPermission(newTekCountryCode);
        }

        for (NewTekCountryCode newTekCountryCode : newTekCountryCodeList) {
            newTekCountryCode.setChineseName(newTekCountryCode.getCode() + "_" + newTekCountryCode.getChineseName() + "_" + newTekCountryCode.getEnglishName());
        }
        return AjaxResult.success("success", newTekCountryCodeList);
    }

    /**
     * 根据ID获取详细信息
     */
//    @PreAuthorize("@ss.hasPermi('system:role:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return AjaxResult.success(newTekCountryCodeService.getNewTekCountryCodeById(id));
    }

}
