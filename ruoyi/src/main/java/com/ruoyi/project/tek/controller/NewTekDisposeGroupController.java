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
import com.ruoyi.project.tek.domain.NewTekDisposeGroup;
import com.ruoyi.project.tek.request.QueryCategoryReq;
import com.ruoyi.project.tek.request.QueryDisposeReq;
import com.ruoyi.project.tek.service.INewTekCategoryService;
import com.ruoyi.project.tek.service.INewTekDisposeGroupService;
import com.ruoyi.project.tek.service.INewTekDisposeService;
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
 * @date 2020/8/19 11:12
 */
@RestController
@RequestMapping("/tek/newTekDisposeGroup")
public class NewTekDisposeGroupController extends BaseController {

    @Autowired
    private INewTekDisposeService newTekDisposeService;
    @Autowired
    private INewTekDisposeGroupService newTekDisposeGroupService;
    @Autowired
    private INewTekCategoryService newTekCategoryService;

    @PreAuthorize("@ss.hasPermi('configuration:group:list')")
    @GetMapping(value = "/queryNewTekDisposeGroupForPage")
    public TableDataInfo queryNewTekDisposeGroupForPage(NewTekDisposeGroup newTekDisposeGroup) {
        startPage();
        List<NewTekDisposeGroup> newTekDisposeGroupList = newTekDisposeGroupService.queryNewTekDisposeGroupForPage(newTekDisposeGroup);
        return getDataTable(newTekDisposeGroupList);
    }

    /**
     * 根据产品系列编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('configuration:group:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return AjaxResult.success(newTekDisposeGroupService.getNewTekDisposeGroupById(id));
    }

    /**
     * 新增产品配置组
     */
    @PreAuthorize("@ss.hasPermi('configuration:group:add')")
    @Log(title = "产品配置组管理", businessType = BusinessType.INSERT)
    @PostMapping(value = "add")
    public AjaxResult add(@Validated @RequestBody NewTekDisposeGroup newTekDisposeGroup) {
        NewTekDisposeGroup newTekDisposeGroupExist = newTekDisposeGroupService.checkDisposeGroupIsExist(newTekDisposeGroup);
        if (newTekDisposeGroupExist != null) {
            return AjaxResult.error("新增产品配置组失败, " + newTekDisposeGroup.getDisposeGroupName() + " 已存在");
        }
        newTekDisposeGroup.setCreateBy(SecurityUtils.getUsername());
        newTekDisposeGroup.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(newTekDisposeGroupService.insertNewTekDisposeGroup(newTekDisposeGroup));
    }

    /**
     * 修改配置组
     */
    @PreAuthorize("@ss.hasPermi('configuration:group:edit')")
    @Log(title = "产品配置组管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody NewTekDisposeGroup newTekDisposeGroup) {
        NewTekDisposeGroup newTekDisposeExist = newTekDisposeGroupService.checkDisposeGroupIsExist(newTekDisposeGroup);
        if (newTekDisposeExist != null) {
            return AjaxResult.error("修改产品配置组失败, " + newTekDisposeGroup.getDisposeGroupName() + " 已存在!");
        }
        newTekDisposeGroup.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(newTekDisposeGroupService.updateNewTekDisposeGroup(newTekDisposeGroup));
    }

    /**
     * 删除产品系列
     */
    @PreAuthorize("@ss.hasPermi('configuration:group:remove')")
    @Log(title = "产品配置组管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        for (Long id : ids) {
            NewTekDisposeGroup newTekDisposeGroup = newTekDisposeGroupService.getNewTekDisposeGroupById(id);
            QueryCategoryReq req = new QueryCategoryReq();
            req.setDisposeGroupId(newTekDisposeGroup.getId());
            List<NewTekCategory> newTekCategoryList = newTekCategoryService.queryNewTekCategoryAll(req);
            if (newTekCategoryList.size() > 0 && newTekCategoryList != null) {
                return AjaxResult.error("不能删除产品配置组'" + newTekDisposeGroup.getDisposeGroupName() + "'，Category中已使用！");
            }
        }
        return toAjax(newTekDisposeGroupService.deleteDisposeGroupByIds(ids));
    }

    /**
     * 查询能用的所有产品组配置
     */
    @GetMapping("/getAllDisposeGroup")
    public AjaxResult getAllDisposeGroup() {
        List<NewTekDisposeGroup> newTekDisposeGroupList = newTekDisposeGroupService.queryNewTekDisposeGroupForPage(new NewTekDisposeGroup());
        return AjaxResult.success(newTekDisposeGroupList);
    }

    @Log(title = "产品配置组管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('configuration:group:export')")
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
