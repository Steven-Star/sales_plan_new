package com.ruoyi.project.tek.controller;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.tek.domain.NewTekCategory;
import com.ruoyi.project.tek.domain.NewTekDisposeGroup;
import com.ruoyi.project.tek.domain.NewTekModel;
import com.ruoyi.project.tek.domain.NewTekProductModel;
import com.ruoyi.project.tek.request.QueryCategoryReq;
import com.ruoyi.project.tek.service.INewTekCategoryService;
import com.ruoyi.project.tek.service.INewTekDisposeGroupService;
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
 * @date 2020/6/16 16:28
 */
@RestController
@RequestMapping("/tek/newTekCategory")
public class NewTekCategoryController extends BaseController {

    @Autowired
    private INewTekCategoryService newTekCategoryService;
    @Autowired
    private INewTekModelService newTekModelService;
    @Autowired
    private INewTekProductModelService newTekProductModelService;
    @Autowired
    private INewTekDisposeGroupService newTekDisposeGroupService;

    @PreAuthorize("@ss.hasPermi('product:category:list')")
    @GetMapping(value = "/queryNewTekCategoryAll")
    public TableDataInfo queryNewTekCategoryAll(QueryCategoryReq req) {
        startPage();
        List<NewTekCategory> newTekCategoryList = newTekCategoryService.queryNewTekCategoryAll(req);
        for (NewTekCategory newTekCategory : newTekCategoryList) {
            NewTekDisposeGroup newTekDisposeGroup = newTekDisposeGroupService.getNewTekDisposeGroupById(newTekCategory.getDisposeGroupId());
            newTekCategory.setDisposeGroupName(newTekDisposeGroup.getDisposeGroupName());
        }
        return getDataTable(newTekCategoryList);
    }

    /**
     * 根据产品品类编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('product:category:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return AjaxResult.success(newTekCategoryService.getNewTekCategoryById(id));
    }

    /**
     * 新增产品品类信息
     */
    @PreAuthorize("@ss.hasPermi('product:category:add')")
    @Log(title = "产品品类管理", businessType = BusinessType.INSERT)
    @PostMapping(value = "add")
    public AjaxResult add(@Validated @RequestBody NewTekCategory newTekCategory) {
        QueryCategoryReq req = new QueryCategoryReq();
        req.setCategoryName(newTekCategory.getCategoryName());
        List<NewTekCategory> categoryList = newTekCategoryService.queryNewTekCategoryAll(req);
        if (categoryList.size() > 0 && categoryList != null) {
            return AjaxResult.error("新增产品品类'" + newTekCategory.getCategoryName() + "'失败，已存在");
        }
        newTekCategory.setCreateBy(SecurityUtils.getUsername());
        newTekCategory.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(newTekCategoryService.insertNewTekCategory(newTekCategory));
    }

    /**
     * 修改产品品类
     */
    @PreAuthorize("@ss.hasPermi('product:category:edit')")
    @Log(title = "产品品类管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody NewTekCategory newTekCategory) {
        QueryCategoryReq req = new QueryCategoryReq();
        NewTekCategory newTekCategory1 = newTekCategoryService.queryNewTekCategoryByName(newTekCategory.getCategoryName());
        if (newTekCategory1 != null) {
            return AjaxResult.error("修改产品品类'" + newTekCategory.getCategoryName() + "'失败，已存在");
        }
        newTekCategory.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(newTekCategoryService.updateNewTekCategory(newTekCategory));
    }

    /**
     * 删除产品品类
     */
    @PreAuthorize("@ss.hasPermi('product:category:remove')")
    @Log(title = "产品品类管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        for (Long id : ids) {
            NewTekCategory newTekCategory = newTekCategoryService.getNewTekCategoryById(id);
            NewTekModel newTekModel = new NewTekModel();
            newTekModel.setCategoryId(id);
            List<NewTekModel> newTekModel1List = newTekModelService.getModelByCategoryAndModelName(newTekModel);
            if (newTekModel1List.size() > 0 && newTekModel1List != null) {
                return AjaxResult.error("不能删除产品品类'" + newTekCategory.getCategoryName() + "'，已存在！");
            }
            NewTekProductModel newTekProductModel = new NewTekProductModel();
            newTekProductModel.setCategoryId(id);
            List<NewTekProductModel> productModelList = newTekProductModelService.getProductModelByCategoryAndModelAndDisposeSortId(newTekProductModel);
            if (productModelList.size() > 0 && productModelList != null) {
                return AjaxResult.error("不能删除产品品类'" + newTekCategory.getCategoryName() + "'，已存在！");
            }
        }
        return toAjax(newTekCategoryService.deleteCategoryByIds(ids));
    }

    /**
     * 查询能用的所有产品品类
     */
    @GetMapping("/getAllCategory")
    public AjaxResult getAllCategory() {
        List<NewTekCategory> newTekCategoryList = newTekCategoryService.getAllCategory();
        return AjaxResult.success(newTekCategoryList);
    }

    @Log(title = "产品品类管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('product:category:export')")
    @GetMapping("/export")
    public AjaxResult export(QueryCategoryReq req) {
        List<NewTekCategory> list = newTekCategoryService.queryNewTekCategoryAll(req);
        for (NewTekCategory newTekCategory : list) {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String create = sf.format(newTekCategory.getCreateTime());
            String update = sf.format(newTekCategory.getUpdateTime());
            newTekCategory.setCreateTimeStr(create);
            newTekCategory.setUpdateTimeStr(update);
        }
        ExcelUtil<NewTekCategory> util = new ExcelUtil<NewTekCategory>(NewTekCategory.class);
        return util.exportExcel(list, "Category数据");
    }

}
