package com.ruoyi.project.tek.controller;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.tek.domain.NewTekDispose;
import com.ruoyi.project.tek.domain.NewTekModel;
import com.ruoyi.project.tek.domain.NewTekProductModel;
import com.ruoyi.project.tek.request.QueryModelReq;
import com.ruoyi.project.tek.service.INewTekDisposeService;
import com.ruoyi.project.tek.service.INewTekModelService;
import com.ruoyi.project.tek.service.INewTekProductModelService;
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
import java.util.List;

/**
 * @author steven.guo
 * @version 1.0
 * @description:
 * @date 2020/6/3 10:55
 */
@RestController
@RequestMapping("/tek/newTekModel")
public class NewTekModelController extends BaseController {

    @Autowired
    private INewTekModelService newTekModelService;
    @Autowired
    private INewTekDisposeService newTekDisposeService;
    @Autowired
    private INewTekProductModelService newTekProductModelService;


    @PreAuthorize("@ss.hasPermi('product:model:list')")
    @GetMapping(value = "/queryNewTekModelAll")
    public TableDataInfo queryNewTekModelAll(QueryModelReq req) {
        startPage();
        List<NewTekModel> newTekModelList = newTekModelService.queryNewTekModelAll(req);
        return getDataTable(newTekModelList);
    }

    /**
     * 根据产品系列编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('product:model:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return AjaxResult.success(newTekModelService.getNewTekModelById(id));
    }

    /**
     * 新增产品系列信息
     */
    @PreAuthorize("@ss.hasPermi('product:model:add')")
    @Log(title = "产品系列管理", businessType = BusinessType.INSERT)
    @PostMapping(value = "add")
    public AjaxResult add(@Validated @RequestBody NewTekModel newTekModel) {
        NewTekModel newTekModel1 = new NewTekModel();
        newTekModel1.setCategoryId(newTekModel.getCategoryId());
        newTekModel1.setModelSortId(newTekModel.getModelSortId());
        List<NewTekModel> newTekModelList = newTekModelService.getModelByCategoryAndModelName(newTekModel1);
        if (newTekModelList.size() > 0 && newTekModelList != null) {
            return AjaxResult.error("新增产品系列'" + newTekModel.getModelName() + "'失败，Model排序字段不能重复！");
        }
        newTekModel1.setModelName(newTekModel.getModelName());
        newTekModel1.setModelSortId(null);
        List<NewTekModel> newTekModelList1 = newTekModelService.getModelByCategoryAndModelName(newTekModel1);
        if (newTekModelList1.size() > 0 && newTekModelList1 != null) {
            return AjaxResult.error("新增产品系列'" + newTekModel.getModelName() + "'失败，已存在");
        }
        newTekModel.setCreateBy(SecurityUtils.getUsername());
        newTekModel.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(newTekModelService.insertNewTekModel(newTekModel));
    }

    /**
     * 修改产品系列
     */
    @PreAuthorize("@ss.hasPermi('product:model:edit')")
    @Log(title = "产品系列管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody NewTekModel newTekModel) {
        NewTekModel newTekModel1 = new NewTekModel();
        newTekModel1.setCategoryId(newTekModel.getCategoryId());
        newTekModel1.setModelSortId(newTekModel.getModelSortId());
        List<NewTekModel> newTekModelList = newTekModelService.getModelByCategoryAndModelName(newTekModel1);
        if (newTekModelList.size() > 0 && newTekModelList != null && !newTekModel.getId().equals(newTekModelList.get(0).getId()) && newTekModel.getModelName().equals(newTekModelList.get(0).getModelName())) {
            return AjaxResult.error("修改产品系列'" + newTekModel.getModelName() + "'失败，已存在！");
        }
        if (newTekModelList.size() > 0 && newTekModelList != null && !newTekModel.getId().equals(newTekModelList.get(0).getId()) && newTekModel.getModelSortId().equals(newTekModelList.get(0).getModelSortId())) {
            return AjaxResult.error("修改产品系列'" + newTekModel.getModelName() + "'失败，modelSortId不能重复！");
        }
        newTekModel1.setModelName(newTekModel.getModelName());
        newTekModel1.setModelSortId(null);
        List<NewTekModel> newTekModelList1 = newTekModelService.getModelByCategoryAndModelName(newTekModel1);
        if (newTekModelList1.size() > 0 && newTekModelList1 != null && newTekModel.getModelSortId().equals(newTekModelList1.get(0).getModelSortId())) {
            return AjaxResult.error("修改产品系列'" + newTekModel.getModelName() + "'失败，已存在");
        }
        if (newTekModelList1.size() > 0 && newTekModelList1 != null && newTekModel.getModelName().equals(newTekModelList1.get(0).getModelName()) && newTekModel.getModelSortId().equals(newTekModelList1.get(0).getModelSortId())) {
            return AjaxResult.error("修改产品系列'" + newTekModel.getModelName() + "'失败，modelName不能重复！");
        }
        newTekModel.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(newTekModelService.updateNewTekModel(newTekModel));
    }

    /**
     * 删除产品系列
     */
    @PreAuthorize("@ss.hasPermi('product:model:remove')")
    @Log(title = "产品系列管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        for (Long id : ids) {
            NewTekModel newTekModel = newTekModelService.getNewTekModelById(id);

            List<NewTekDispose> newTekDisposeList = newTekDisposeService.getAllDispose(id);
            if (newTekDisposeList.size() > 0 && newTekDisposeList != null) {
                return AjaxResult.error("不能删除产品系列'" + newTekModel.getModelName() + "'，已存在！");
            }
            NewTekProductModel newTekProductModel = new NewTekProductModel();
            newTekProductModel.setModelId(id);
            List<NewTekProductModel> productModelList = newTekProductModelService.getProductModelByCategoryAndModelAndDisposeSortId(newTekProductModel);
            if (productModelList.size() > 0 && productModelList != null) {
                return AjaxResult.error("不能删除产品系列'" + newTekModel.getModelName() + "'，已存在！");
            }
        }
        return toAjax(newTekModelService.deleteModelByIds(ids));
    }

    /**
     * 查询能用的所有产品系列
     */
    @GetMapping("/getAllModel/{categoryId}")
    public AjaxResult getAllModel(@PathVariable String categoryId) {
        Long categoryIdNew = null;
        if (!categoryId.equals(null) && !categoryId.equals("") && !categoryId.equals("undefined") && !categoryId.equals("默认") && !categoryId.equals("null")) {
            categoryIdNew = Long.parseLong(categoryId);
        }
        List<NewTekModel> newTekModelList = newTekModelService.getAllModel(categoryIdNew);
        return AjaxResult.success(newTekModelList);
    }

    @Log(title = "产品系列管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('product:model:export')")
    @GetMapping("/export")
    public AjaxResult export(QueryModelReq req) {
        List<NewTekModel> list = newTekModelService.queryNewTekModelAll(req);
        for (NewTekModel newTekModel : list) {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String create = sf.format(newTekModel.getCreateTime());
            String update = sf.format(newTekModel.getUpdateTime());
            newTekModel.setCreateTimeStr(create);
            newTekModel.setUpdateTimeStr(update);
        }
        ExcelUtil<NewTekModel> util = new ExcelUtil<NewTekModel>(NewTekModel.class);
        return util.exportExcel(list, "Series数据");
    }

}
