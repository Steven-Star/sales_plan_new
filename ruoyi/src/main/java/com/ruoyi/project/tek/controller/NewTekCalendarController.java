package com.ruoyi.project.tek.controller;

import com.github.pagehelper.PageInfo;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.framework.security.service.TokenService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.page.PageDomain;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.framework.web.page.TableSupport;
import com.ruoyi.project.system.domain.SysUser;
import com.ruoyi.project.tek.domain.NewTekCountryCode;
import com.ruoyi.project.tek.request.QueryCalendarReq;
import com.ruoyi.project.tek.service.INewTekCountryCodeService;
import com.ruoyi.project.tek.service.ITblGoodsService;
import com.ruoyi.project.tek.vo.NewTekCalendarVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;

/**
 * 销售计划项目-建议零售价列表页接口
 *
 * @author steven
 */
@RestController
@RequestMapping("/tek/calendar")
public class NewTekCalendarController extends BaseController {
    @Autowired
    private ITblGoodsService tblGoodsService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private INewTekCountryCodeService newTekCountryCodeService;

    @PreAuthorize("@ss.hasPermi('dashboard:data:list')")
    @GetMapping(value = "queryCalendarAll")
    public TableDataInfo queryCalendarAll(QueryCalendarReq req) {
        List<NewTekCalendarVO> newTekCalendarVOList = null;
        TableDataInfo rspData = new TableDataInfo();
        try {
            if (req.getModelName() != null) {
                req.setDisposeName(req.getModelName());
            }
            if (req.getSkuEncode() != null) {
                String sku = URLDecoder.decode(req.getSkuEncode(), "UTF-8");
                req.setSku(sku);
            }
            String countrys = "";
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            SysUser sysUser = loginUser.getUser();
            if (sysUser.getUserId() == 1L) {
                newTekCalendarVOList = tblGoodsService.queryCalendarAllData(req);
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
                String customers = sysUser.getCustomerNames();
                List<String> customerList = Arrays.asList(customers.split(","));
                req.setCustomerList(customerList);
                newTekCalendarVOList = tblGoodsService.queryCalendarAllDataForPermission(req);
            }
            PageDomain pageDomain = TableSupport.buildPageRequest();
            Integer pageNum = pageDomain.getPageNum();
            Integer pageSize = pageDomain.getPageSize();
            int total = newTekCalendarVOList.size();
            int startIndex = (pageNum - 1) * pageSize;
            int endIndex = Math.min(startIndex + pageSize, total);
            rspData.setCode(HttpStatus.SUCCESS);
            rspData.setMsg("查询成功");
            rspData.setRows(newTekCalendarVOList.subList(startIndex, endIndex));
            rspData.setTotal(new PageInfo(newTekCalendarVOList).getTotal());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rspData;
    }


}