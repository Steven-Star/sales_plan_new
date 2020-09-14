package com.ruoyi.project.tek.controller;

import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.tek.domain.TekSkuToMatnr;
import com.ruoyi.project.tek.service.ITekSkuToMatnrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author steven.guo
 * @version 1.0
 * @description:
 * @date 2020/6/3 10:55
 */
@RestController
@RequestMapping("/tek/skuToMatnr")
public class TekSkuToMatnrController extends BaseController {

    @Autowired
    private ITekSkuToMatnrService tekSkuToMatnrService;

    /**
     * 查询能用的所有SKU
     */
    @GetMapping("/getAllSku")
    public AjaxResult getAllSku() {
        List<TekSkuToMatnr> tekSkuToMatnrList = tekSkuToMatnrService.queryTekSkuAll();
        return AjaxResult.success(tekSkuToMatnrList);
    }

}
