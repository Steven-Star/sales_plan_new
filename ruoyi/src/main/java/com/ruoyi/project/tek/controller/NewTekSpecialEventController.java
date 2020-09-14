package com.ruoyi.project.tek.controller;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.tek.domain.NewTekSpecialEvent;
import com.ruoyi.project.tek.request.QuerySpecialEventReq;
import com.ruoyi.project.tek.service.INewTekSpecialEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author steven.guo
 * @version 1.0
 * @description:
 * @date 2020/6/3 10:55
 * <p>
 * 废弃
 */
@RestController
@RequestMapping("/tek/specialEvent")
public class NewTekSpecialEventController extends BaseController {

    @Autowired
    private INewTekSpecialEventService newTekSpecialEventService;


    @GetMapping(value = "/queryNewTekSpecialEventAll")
    public TableDataInfo queryNewTekSpecialEventAll(QuerySpecialEventReq req) {
        startPage();
        List<NewTekSpecialEvent> newTekSpecialEventList = newTekSpecialEventService.getAllSpecialEvent(req);
        return getDataTable(newTekSpecialEventList);
    }

    /**
     * 根据产品系列编号获取详细信息
     */
//    @PreAuthorize("@ss.hasPermi('system:role:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        NewTekSpecialEvent newTekSpecialEvent = newTekSpecialEventService.getNewTekSpecialEventById(id);
        return AjaxResult.success(newTekSpecialEvent);
    }


    //    @PreAuthorize("@ss.hasPermi('system:user:add')")
    @Log(title = "特殊事件管理", businessType = BusinessType.INSERT)
    @PostMapping(value = "add")
    public AjaxResult add(@Validated @RequestBody NewTekSpecialEvent newTekSpecialEvent) {
        newTekSpecialEvent.setCreateBy(SecurityUtils.getUsername());
        newTekSpecialEvent.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(newTekSpecialEventService.insertNewTekSpecialEvent(newTekSpecialEvent));
    }


    //    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "特殊事件管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody NewTekSpecialEvent newTekSpecialEvent) {
        newTekSpecialEvent.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(newTekSpecialEventService.updateNewTekSpecialEvent(newTekSpecialEvent));
    }


    /**
     * 批量删除客户信息
     */
//    @PreAuthorize("@ss.hasPermi('system:role:remove')")
//    @Log(title = "客户信息管理", businessType = BusinessType.DELETE)
//    @DeleteMapping("/{ids}")
//    public AjaxResult remove(@PathVariable Long[] ids)
//    {
//        return toAjax(newTekCustomerService.deleteCustomerByIds(ids));
//    }
//

    /**
     * 查询能用的所有客户
     */
    @GetMapping("/getAllSpecialEvent")
    public AjaxResult getAllSpecialEvent() {
        QuerySpecialEventReq req = new QuerySpecialEventReq();
        List<NewTekSpecialEvent> newTekSpecialEventList = newTekSpecialEventService.getAllSpecialEvent(req);
        return AjaxResult.success(newTekSpecialEventList);
    }

}
