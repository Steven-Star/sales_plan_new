package com.ruoyi.project.tek.controller;

import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.tek.domain.NewTekCurrencyCode;
import com.ruoyi.project.tek.service.INewTekCurrencyCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author steven.guo
 * @version 1.0
 * @description:
 * @date 2020/6/29 11:29
 */
@RestController
@RequestMapping("/tek/newTekCurrencyCode")
public class NewTekCurrencyCodeController extends BaseController {

    @Autowired
    private INewTekCurrencyCodeService newTekCurrencyCodeService;


    @GetMapping(value = "/queryNewTekCurrencyCodeAll")
    public AjaxResult queryNewTekCurrencyCodeAll(String country) {
        List<NewTekCurrencyCode> newTekCurrencyCodeList = newTekCurrencyCodeService.queryNewTekCurrencyCodeAll(country);
        return AjaxResult.success("success", newTekCurrencyCodeList);
    }

    /**
     * 根据ID获取详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return AjaxResult.success(newTekCurrencyCodeService.getNewTekCurrencyCodeById(id));
    }

    @GetMapping(value = "/getCurrencyCodeByCountry")
    public AjaxResult getCurrencyCodeByCountry(String country) {
        if (country.split("_").length > 1) {
            country = country.split("_")[0];
        }
        NewTekCurrencyCode newTekCurrencyCode = newTekCurrencyCodeService.getCurrencyCodeByCountry(country);
        return AjaxResult.success("success", newTekCurrencyCode);
    }

}
