package com.ruoyi.project.tek.controller;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.tek.domain.NewTekCategory;
import com.ruoyi.project.tek.domain.NewTekDispose;
import com.ruoyi.project.tek.domain.NewTekProductModel;
import com.ruoyi.project.tek.request.QueryDisposeReq;
import com.ruoyi.project.tek.service.INewTekCategoryService;
import com.ruoyi.project.tek.service.INewTekDisposeService;
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
 * @date 2020/6/16 16:31
 */
@RestController
@RequestMapping("/tek/newTekDispose")
public class NewTekDisposeController extends BaseController {

    @Autowired
    private INewTekDisposeService newTekDisposeService;
    @Autowired
    private INewTekProductModelService newTekProductModelService;
    @Autowired
    private INewTekCategoryService newTekCategoryService;


    @PreAuthorize("@ss.hasPermi('product:dispose:list')")
    @GetMapping(value = "/queryNewTekDisposeAll")
    public TableDataInfo queryNewTekDisposeAll(QueryDisposeReq req) {
        startPage();
        List<NewTekDispose> newTekDisposeList = newTekDisposeService.queryNewTekDisposeAll(req);
        return getDataTable(newTekDisposeList);
    }

    /**
     * 根据产品系列编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('product:dispose:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return AjaxResult.success(newTekDisposeService.getNewTekDisposeById(id));
    }


    /**
     * 新增产品配置信息
     */
    @PreAuthorize("@ss.hasPermi('product:dispose:add')")
    @Log(title = "产品配置管理", businessType = BusinessType.INSERT)
    @PostMapping(value = "add")
    public AjaxResult add(@Validated @RequestBody NewTekDispose newTekDispose) {
        QueryDisposeReq req = new QueryDisposeReq();
        req.setDisposeName(newTekDispose.getDisposeName());
        List<NewTekDispose> disposeList = newTekDisposeService.queryNewTekDisposeAll(req);
        if (disposeList.size() > 0 && disposeList != null) {
            for (NewTekDispose newTekDispose1 : disposeList) {
//                if(newTekDispose1.getDisposeName().equals(newTekDispose.getDisposeName()) && newTekDispose1.getCategoryId().equals(newTekDispose.getCategoryId()) && newTekDispose1.getModelId().equals(newTekDispose.getModelId())){
                if (newTekDispose1.getDisposeName().equals(newTekDispose.getDisposeName()) && newTekDispose1.getDisposeGroupName().equals(newTekDispose.getDisposeGroupName())) {
                    return AjaxResult.error("新增产品配置失败, " + newTekDispose.getDisposeName() + " 已存在!");
                }
            }
        }
        NewTekDispose newTekDispose1 = newTekDisposeService.getNewTekDisposeByCategoryAndModelAndSortId(newTekDispose);
        if (newTekDispose1 != null) {
            return AjaxResult.error("新增产品配置失败, " + newTekDispose.getDisposeSortId() + " 排序已存在");
        }
        newTekDispose.setCreateBy(SecurityUtils.getUsername());
        newTekDispose.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(newTekDisposeService.insertNewTekDispose(newTekDispose));
    }


    /**
     * 修改产品系列
     */
    @PreAuthorize("@ss.hasPermi('product:dispose:edit')")
    @Log(title = "产品配置管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody NewTekDispose newTekDispose) {
        NewTekDispose newTekDispose2 = newTekDisposeService.getNewTekDisposeByCategoryAndModelAndSortId(newTekDispose);
        if (newTekDispose2 != null && newTekDispose2.getId() != newTekDispose.getId()) {
            return AjaxResult.error("修改产品配置失败, " + newTekDispose.getDisposeSortId() + " 已存在!");
        }
        newTekDispose.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(newTekDisposeService.updateNewTekDispose(newTekDispose));
    }

    /**
     * 删除产品系列
     */
    @PreAuthorize("@ss.hasPermi('product:dispose:remove')")
    @Log(title = "产品配置管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        for (Long id : ids) {
            NewTekDispose newTekDispose = newTekDisposeService.getNewTekDisposeById(id);
            NewTekProductModel newTekProductModel = new NewTekProductModel();
            newTekProductModel.setDisposeId(id);
            List<NewTekProductModel> productModelList = newTekProductModelService.getProductModelByCategoryAndModelAndDisposeSortId(newTekProductModel);
            if (productModelList.size() > 0 && productModelList != null) {
                return AjaxResult.error("不能删除产品配置'" + newTekDispose.getDisposeName() + "'，已存在！");
            }
        }
        return toAjax(newTekDisposeService.deleteDisposeByIds(ids));
    }

    /**
     * 查询能用的所有产品配置
     */
    @GetMapping("/getAllDispose/{modelId}")
    public AjaxResult getAllDispose(@PathVariable String modelId) {
        Long modelIdNew = null;
        List<NewTekDispose> newTekDisposeList = null;
        if (!modelId.equals(null) && !modelId.equals("") && !modelId.equals("undefined") && !modelId.equals("默认")) {
            if (modelId.contains("_") && modelId.split("_")[1].equals("1")) {
                NewTekCategory newTekCategory = newTekCategoryService.getNewTekCategoryById(Long.parseLong(modelId.split("_")[0]));
                newTekDisposeList = newTekDisposeService.getAllDisposeByGroupId(newTekCategory.getDisposeGroupId());
            } else {
                modelIdNew = Long.parseLong(modelId);
                newTekDisposeList = newTekDisposeService.getAllDispose(modelIdNew);
            }
        } else {
            newTekDisposeList = newTekDisposeService.getAllDispose(modelIdNew);
        }
        return AjaxResult.success(newTekDisposeList);
    }

    @Log(title = "产品配置管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('product:dispose:export')")
    @GetMapping("/export")
    public AjaxResult export(QueryDisposeReq req) {
        List<NewTekDispose> list = newTekDisposeService.queryNewTekDisposeAll(req);
        for (NewTekDispose newTekDispose : list) {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String create = sf.format(newTekDispose.getCreateTime());
            String update = sf.format(newTekDispose.getUpdateTime());
            newTekDispose.setCreateTimeStr(create);
            newTekDispose.setUpdateTimeStr(update);
        }
        ExcelUtil<NewTekDispose> util = new ExcelUtil<NewTekDispose>(NewTekDispose.class);
        return util.exportExcel(list, "Configuration数据");
    }

}
