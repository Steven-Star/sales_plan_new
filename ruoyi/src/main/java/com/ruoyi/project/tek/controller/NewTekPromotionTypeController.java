package com.ruoyi.project.tek.controller;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.tek.domain.NewTekPromotionType;
import com.ruoyi.project.tek.service.INewTekPromotionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;

/**
 * @author steven.guo
 * @version 1.0
 * @description:
 * @date 2020/7/08 15:35
 */
@RestController
@RequestMapping("/tek/newTekPromotionType")
public class NewTekPromotionTypeController extends BaseController {

    @Autowired
    private INewTekPromotionTypeService newTekPromotionTypeService;


    @GetMapping(value = "/queryNewTekPromotionTypeAll")
    public TableDataInfo queryNewTekPromotionTypeAll(NewTekPromotionType newTekPromotionType) {
        startPage();
        List<NewTekPromotionType> newTekPromotionTypeList = newTekPromotionTypeService.queryNewTekPromotionTypeAll(newTekPromotionType);
        return getDataTable(newTekPromotionTypeList);
    }

    /**
     * 根据ID获取详细信息
     */
//    @PreAuthorize("@ss.hasPermi('system:role:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return AjaxResult.success(newTekPromotionTypeService.getNewTekPromotionTypeById(id));
    }

    /**
     * 新增
     */
//    @PreAuthorize("@ss.hasPermi('system:user:add')")
    @Log(title = "promotion type管理", businessType = BusinessType.INSERT)
    @PostMapping(value = "add")
    public AjaxResult add(@Validated @RequestBody NewTekPromotionType newTekPromotionType) {
        if (Boolean.valueOf("true").equals(newTekPromotionTypeService.checkPromotionTypeUnique(newTekPromotionType.getPromotionType()))) {
            return AjaxResult.error("新增Promotion Type'" + newTekPromotionType.getPromotionType() + "'失败，已存在");
        }
        newTekPromotionType.setCreateBy(SecurityUtils.getUsername());
        newTekPromotionType.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(newTekPromotionTypeService.insertNewTekPromotionType(newTekPromotionType));
    }

    /**
     * 修改
     */
//    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "promotion type管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody NewTekPromotionType newTekPromotionType) {
        if (Boolean.valueOf("true").equals(newTekPromotionTypeService.checkPromotionTypeUnique(newTekPromotionType.getPromotionType()))) {
            return AjaxResult.error("修改Promotion Type'" + newTekPromotionType.getPromotionType() + "'失败，已存在");
        }
        newTekPromotionType.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(newTekPromotionTypeService.updateNewTekPromotionType(newTekPromotionType));
    }

    /**
     * 删除
     */
//    @PreAuthorize("@ss.hasPermi('system:role:remove')")
    @Log(title = "promotion type管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(newTekPromotionTypeService.deletePromotionTypeByIds(ids));
    }

    /**
     * 查询能用的所有promotion type
     */
    @GetMapping("/getAllPromotionType")
    public AjaxResult getAllPromotionType() {
        List<NewTekPromotionType> newTekPromotionTypeList = newTekPromotionTypeService.getAllPromotionType();
        return AjaxResult.success(newTekPromotionTypeList);
    }

    @Log(title = "promotion type管理", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('system:config:export')")
    @GetMapping("/export")
    public AjaxResult export(NewTekPromotionType newTekPromotionType) {
        List<NewTekPromotionType> list = newTekPromotionTypeService.queryNewTekPromotionTypeAll(newTekPromotionType);
        for (NewTekPromotionType promotionType : list) {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String create = sf.format(promotionType.getCreateTime());
            String update = sf.format(promotionType.getUpdateTime());
            promotionType.setCreateTimeStr(create);
            promotionType.setUpdateTimeStr(update);
        }
        ExcelUtil<NewTekPromotionType> util = new ExcelUtil<NewTekPromotionType>(NewTekPromotionType.class);
        return util.exportExcel(list, "PromotionType数据");
    }

}
