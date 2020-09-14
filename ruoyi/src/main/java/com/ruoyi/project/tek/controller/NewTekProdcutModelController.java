package com.ruoyi.project.tek.controller;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.framework.security.service.TokenService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.system.domain.SysUser;
import com.ruoyi.project.tek.domain.NewTekCountryCode;
import com.ruoyi.project.tek.domain.NewTekMsrp;
import com.ruoyi.project.tek.domain.NewTekMsrpWk;
import com.ruoyi.project.tek.domain.NewTekProductModel;
import com.ruoyi.project.tek.domain.TekSkuToMatnr;
import com.ruoyi.project.tek.request.QueryProductModelReq;
import com.ruoyi.project.tek.service.INewTekCountryCodeService;
import com.ruoyi.project.tek.service.INewTekModelService;
import com.ruoyi.project.tek.service.INewTekMsrpService;
import com.ruoyi.project.tek.service.INewTekMsrpWkService;
import com.ruoyi.project.tek.service.INewTekProductModelService;
import com.ruoyi.project.tek.service.ITekSkuToMatnrService;
import com.ruoyi.project.tek.vo.NewTekProductModelVO;
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
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

/**
 * @author steven.guo
 * @version 1.0
 * @description:
 * @date 2020/6/3 10:55
 */
@RestController
@RequestMapping("/tek/newTekProductModel")
public class NewTekProdcutModelController extends BaseController {

    @Autowired
    private INewTekProductModelService newTekProductModelService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private INewTekMsrpService newTekMsrpService;
    @Autowired
    private INewTekMsrpWkService newTekMsrpWkService;
    @Autowired
    private INewTekCountryCodeService newTekCountryCodeService;
    @Autowired
    private ITekSkuToMatnrService tekSkuToMatnrService;


    @PreAuthorize("@ss.hasPermi('product:sku:list')")
    @GetMapping(value = "/getNewTekProductModel")
    public TableDataInfo getNewTekProductModel(QueryProductModelReq req) {
        List<NewTekProductModelVO> newTekProductModelVOList = null;
        String countrys = "";
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser sysUser = loginUser.getUser();
        if (sysUser.getUserId() == 1L) {
            startPage();
            newTekProductModelVOList = newTekProductModelService.getNewTekProductModelForPage(req);
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
            startPage();
            newTekProductModelVOList = newTekProductModelService.getNewTekProductModelForPagePermission(req);
        }
        return getDataTable(newTekProductModelVOList);
    }

    /**
     * 根据产品关联编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('product:sku:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return AjaxResult.success(newTekProductModelService.getNewTekProductModelById(id));
    }

    @Log(title = "产品关联系列管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('product:sku:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<NewTekProductModel> util = new ExcelUtil<NewTekProductModel>(NewTekProductModel.class);
        List<NewTekProductModel> skuList = util.importExcel(file.getInputStream());
        String message = newTekProductModelService.importSKU(skuList, updateSupport);
        return AjaxResult.success(message);
    }

    @GetMapping("/importTemplate")
    public AjaxResult importTemplate() {
        ExcelUtil<NewTekProductModel> util = new ExcelUtil<NewTekProductModel>(NewTekProductModel.class);
        return util.importTemplateExcel("SKU数据");
    }

    /**
     * 新增产品关联系列信息
     */
    @PreAuthorize("@ss.hasPermi('product:sku:add')")
    @Log(title = "产品关联系列管理", businessType = BusinessType.INSERT)
    @PostMapping(value = "add")
    public AjaxResult add(@Validated @RequestBody NewTekProductModel newTekProductModel) {
        String sku = newTekProductModel.getSku();
        String countryName = newTekProductModel.getCountryName();
        List<TekSkuToMatnr> tekSkuToMatnrList = tekSkuToMatnrService.queryTekSkuByGoodsSKU(sku);
        if (tekSkuToMatnrList.size() == 0 || tekSkuToMatnrList == null) {
            return AjaxResult.error("只能选择SKU！");
        }
        NewTekCountryCode newTekCountryCode = newTekCountryCodeService.getCountryCodeByCode(countryName.split("_")[0]);
        if (newTekCountryCode == null) {
            return AjaxResult.error("只能选择Country！");
        }
        NewTekProductModel newTekProductModel2 = new NewTekProductModel();
        newTekProductModel2.setSku(sku);
        newTekProductModel2.setCountryName(newTekProductModel.getCountryName());
        List<NewTekProductModel> newTekProductModelList = newTekProductModelService.getProductModelByCategoryAndModelAndDisposeSortId(newTekProductModel2);
        if (newTekProductModelList.size() > 0 && newTekProductModelList != null) {
            NewTekProductModel newTekProductModel3 = newTekProductModelList.get(0);
            if (!newTekProductModel.getId().equals(newTekProductModel3.getId())) {
                return AjaxResult.error("同一个SKU同一个Country下只能有一个ChannelType！");
            }
        }
        newTekProductModel2.setCountryName(null);
        List<NewTekProductModel> newTekProductModelList1 = newTekProductModelService.getProductModelByCategoryAndModelAndDisposeSortId(newTekProductModel2);
        if (newTekProductModelList1.size() > 0 && newTekProductModelList1 != null) {
            NewTekProductModel newTekProductModel3 = newTekProductModelList1.get(0);
            if (!newTekProductModel3.getCategoryId().equals(newTekProductModel.getCategoryId()) || !newTekProductModel3.getModelId().equals(newTekProductModel.getModelId()) || !newTekProductModel3.getDisposeId().equals(newTekProductModel.getDisposeId())) {
                return AjaxResult.error("品类、系列、配置必须与之前的一致！");
            }
        }
        newTekProductModel.setCreateBy(SecurityUtils.getUsername());
        newTekProductModel.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(newTekProductModelService.insertNewTekProductModel(newTekProductModel));
    }

    /**
     * 修改产品关联
     */
    @PreAuthorize("@ss.hasPermi('product:sku:edit')")
    @Log(title = "产品关联系列管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody NewTekProductModel newTekProductModel) {
        String sku = newTekProductModel.getSku();
        String countryName = newTekProductModel.getCountryName();
        List<TekSkuToMatnr> tekSkuToMatnrList = tekSkuToMatnrService.queryTekSkuByGoodsSKU(sku);
        if (tekSkuToMatnrList.size() == 0 || tekSkuToMatnrList == null) {
            return AjaxResult.error("只能选择SKU！");
        }
        NewTekCountryCode newTekCountryCode = newTekCountryCodeService.getCountryCodeByCode(countryName.split("_")[0]);
        if (newTekCountryCode == null) {
            return AjaxResult.error("只能选择Country！");
        }
        NewTekProductModel newTekProductModel2 = new NewTekProductModel();
        newTekProductModel2.setSku(newTekProductModel.getSku());
        newTekProductModel2.setCountryName(newTekProductModel.getCountryName());
        List<NewTekProductModel> newTekProductModelList = newTekProductModelService.getProductModelByCategoryAndModelAndDisposeSortId(newTekProductModel2);
        if (newTekProductModelList.size() > 0 && newTekProductModelList != null) {
            NewTekProductModel newTekProductModel3 = newTekProductModelList.get(0);
            if (!newTekProductModel.getId().equals(newTekProductModel3.getId())) {
                return AjaxResult.error("同一个SKU同一个Country下只能有一个！");
            }
        }
        newTekProductModel2.setCountryName(null);
        List<NewTekProductModel> newTekProductModelList1 = newTekProductModelService.getProductModelByCategoryAndModelAndDisposeSortId(newTekProductModel2);
        if (newTekProductModelList1.size() > 0 && newTekProductModelList1 != null) {
            if (newTekProductModelList1.size() > 1) {
                for (NewTekProductModel newTekProductModel3 : newTekProductModelList1) {
                    if (!newTekProductModel3.getId().equals(newTekProductModel.getId())) {
                        if (!newTekProductModel3.getCategoryId().equals(newTekProductModel.getCategoryId()) || !newTekProductModel3.getModelId().equals(newTekProductModel.getModelId()) || !newTekProductModel3.getDisposeId().equals(newTekProductModel.getDisposeId())) {
                            return AjaxResult.error("品类、系列、配置必须与之前的一致！");
                        }
                    }
                }
            }
        }
        newTekProductModel.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(newTekProductModelService.updateNewTekProductModelById(newTekProductModel));
    }

    /**
     * 删除产品系列
     */
    @PreAuthorize("@ss.hasPermi('product:sku:remove')")
    @Log(title = "产品关联系列管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        for (Long id : ids) {
            NewTekProductModel newTekProductModel = newTekProductModelService.getNewTekProductModelById(id);
            NewTekMsrp newTekMsrp = new NewTekMsrp();
            newTekMsrp.setSku(newTekProductModel.getSku());
            List<NewTekMsrp> newTekMsrpList = newTekMsrpService.getNewTekMsrpByCustomerAndProductAndSku(newTekMsrp);
            if (newTekMsrpList.size() > 0 && newTekMsrpList != null) {
                return AjaxResult.error("MSRP已维护，不能删除！");
            }
            NewTekMsrpWk newTekMsrpWk = new NewTekMsrpWk();
            newTekMsrpWk.setSku(newTekProductModel.getSku());
            List<NewTekMsrpWk> newTekMsrpWkList = newTekMsrpWkService.getMsrpWkByCustomerIdAndSku(newTekMsrpWk);
            if (newTekMsrpWkList.size() > 0 && newTekMsrpWkList != null) {
                return AjaxResult.error("Selling Price已维护，不能删除！");
            }
        }
        return toAjax(newTekProductModelService.deleteProductModelByIds(ids));
    }

    /**
     * 查询所有已维护的sku
     */
    @GetMapping(value = "/getAllProductModel")
    public AjaxResult getAllProductModel(QueryProductModelReq req) {
//        QueryProductModelReq req = new QueryProductModelReq();
        List<NewTekProductModelVO> newTekProductModelVOList = newTekProductModelService.getNewTekProductModelForPage(req);
        return AjaxResult.success(newTekProductModelVOList);
    }

    /**
     * 根据SKU查询产品信息
     */
    @GetMapping(value = "/getProductModelBySKU")
    public AjaxResult getProductModelBySKU(String sku) {
        List<NewTekProductModel> newTekProductModelList = newTekProductModelService.getProductModelBySKU(sku);
        return AjaxResult.success(newTekProductModelList);
    }

    @Log(title = "产品关联系列管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('product:sku:export')")
    @GetMapping("/export")
    public AjaxResult export(QueryProductModelReq req) {
        List<NewTekProductModelVO> list = null;
        String countrys = "";
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser sysUser = loginUser.getUser();
        if (sysUser.getUserId() == 1L) {
            list = newTekProductModelService.getNewTekProductModelForPage(req);
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
            list = newTekProductModelService.getNewTekProductModelForPagePermission(req);
        }
        for (NewTekProductModelVO newTekProductModelVO : list) {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String create = sf.format(newTekProductModelVO.getCreateDate());
            String update = sf.format(newTekProductModelVO.getUpdateDate());
            newTekProductModelVO.setCreateTimeStr(create);
            newTekProductModelVO.setUpdateTimeStr(update);
        }
        ExcelUtil<NewTekProductModelVO> util = new ExcelUtil<NewTekProductModelVO>(NewTekProductModelVO.class);
        return util.exportExcel(list, "SKU数据");
    }

    /**
     * 权限中，查询所有可选择的国家
     */
    @GetMapping(value = "/getAllCountry")
    public AjaxResult getAllCountry() {
        List<NewTekProductModel> countryList = newTekProductModelService.getAllCountry();
        for (NewTekProductModel newTekProductModel : countryList) {
            String country = newTekProductModel.getCountryName().split("_")[0];
            newTekProductModel.setCountryName(country);
        }
        return AjaxResult.success(countryList);
    }

}
