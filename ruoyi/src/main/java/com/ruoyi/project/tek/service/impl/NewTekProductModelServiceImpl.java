package com.ruoyi.project.tek.service.impl;

import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.exception.SKUException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.tek.domain.NewTekCategory;
import com.ruoyi.project.tek.domain.NewTekCountryCode;
import com.ruoyi.project.tek.domain.NewTekDispose;
import com.ruoyi.project.tek.domain.NewTekModel;
import com.ruoyi.project.tek.domain.NewTekProductModel;
import com.ruoyi.project.tek.domain.TekSkuToMatnr;
import com.ruoyi.project.tek.mapper.NewTekCategoryMapper;
import com.ruoyi.project.tek.mapper.NewTekCountryCodeMapper;
import com.ruoyi.project.tek.mapper.NewTekDisposeMapper;
import com.ruoyi.project.tek.mapper.NewTekModelMapper;
import com.ruoyi.project.tek.mapper.NewTekProductModelMapper;
import com.ruoyi.project.tek.mapper.TekSkuToMatnrMapper;
import com.ruoyi.project.tek.request.QueryCategoryReq;
import com.ruoyi.project.tek.request.QueryProductModelReq;
import com.ruoyi.project.tek.service.INewTekProductModelService;
import com.ruoyi.project.tek.service.ITekSkuToMatnrService;
import com.ruoyi.project.tek.vo.NewTekProductModelVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品 服务实现类
 * </p>
 *
 * @author steven.guo
 * @since 2020-05-28
 */
@Service
public class NewTekProductModelServiceImpl implements INewTekProductModelService {

    private static final Logger log = LoggerFactory.getLogger(NewTekProductModelServiceImpl.class);

    @Autowired
    private NewTekProductModelMapper newTekProductModelMapper;

    @Autowired
    private NewTekModelMapper newTekModelMapper;

    @Autowired
    private NewTekCategoryMapper newTekCategoryMapper;

    @Autowired
    private NewTekDisposeMapper newTekDisposeMapper;

    @Autowired
    private NewTekCountryCodeMapper newTekCountryCodeMapper;

    @Autowired
    private ITekSkuToMatnrService tekSkuToMatnrService;


    @Override
    public List<NewTekProductModelVO> getNewTekProductModel(QueryProductModelReq req) {
        return newTekProductModelMapper.getNewTekProductModel(req);
    }

    @Override
    public int insertNewTekProductModel(NewTekProductModel newTekProductModel) {
        NewTekProductModel newTekProductModel1 = new NewTekProductModel();
        if (newTekProductModel != null) {
            Long modelId = newTekProductModel.getModelId();
            String sku = newTekProductModel.getSku();
//            if(sku != null){
//                List<TblGoods> tblGoodsList = tblGoodsMapper.getTblGoodsBySku(sku);
//                if(tblGoodsList.get(0) != null){
//                    newTekProductModel1.setProductId(tblGoodsList.get(0).getId());
//                }
//            }
            newTekProductModel1.setSku(sku);
            newTekProductModel1.setModelId(modelId);
            newTekProductModel1.setChannelType(newTekProductModel.getChannelType());
            newTekProductModel1.setCategoryId(newTekProductModel.getCategoryId());
            newTekProductModel1.setDisposeId(newTekProductModel.getDisposeId());
            newTekProductModel1.setCountryName(newTekProductModel.getCountryName());
            newTekProductModel1.setRemark(newTekProductModel.getRemark());
            newTekProductModel1.setCreateBy(SecurityUtils.getUsername());
            newTekProductModel1.setUpdateBy(SecurityUtils.getUsername());
        }
        int rows = newTekProductModelMapper.insertNewTekProductModel(newTekProductModel1);
        return rows;
    }

    @Override
    public NewTekProductModelVO getNewTekProductModelByProductId(Long productId) {
        return newTekProductModelMapper.getNewTekProductModelByProductId(productId);
    }

    @Override
    public List<NewTekProductModelVO> getNewTekProductModelForPage(QueryProductModelReq req) {
        List<NewTekProductModelVO> newTekProductModelVOList = newTekProductModelMapper.getNewTekProductModelForPage(req);
        for (NewTekProductModelVO newTekProductModelVO : newTekProductModelVOList) {
//            Long productId = newTekProductModelVO.getProductId();
//            TblGoods tblGoods = tblGoodsMapper.getTblGoodsById(productId);
//            newTekProductModelVO.setProductName(tblGoods.getItemAttributesTitle());

            NewTekCategory newTekCategory = newTekCategoryMapper.getNewTekCategoryById(newTekProductModelVO.getCategoryId());
            newTekProductModelVO.setCategoryName(newTekCategory.getCategoryName());

            NewTekModel newTekModel = newTekModelMapper.getNewTekModelById(newTekProductModelVO.getModelId());
            newTekProductModelVO.setModelName(newTekModel.getModelName());

            NewTekDispose newTekDispose = newTekDisposeMapper.getNewTekDisposeById(newTekProductModelVO.getDisposeId());
            newTekProductModelVO.setDisposeName(newTekDispose.getDisposeName());

            newTekProductModelVO.setCountryName(newTekProductModelVO.getCountryName().split("_")[0]);
        }
        return newTekProductModelVOList;
    }

    @Override
    public List<NewTekProductModelVO> getNewTekProductModelForPagePermission(QueryProductModelReq req) {
        List<NewTekProductModelVO> newTekProductModelVOList = newTekProductModelMapper.getNewTekProductModelForPagePermission(req);
        for (NewTekProductModelVO newTekProductModelVO : newTekProductModelVOList) {
//            Long productId = newTekProductModelVO.getProductId();
//            TblGoods tblGoods = tblGoodsMapper.getTblGoodsById(productId);
//            newTekProductModelVO.setProductName(tblGoods.getItemAttributesTitle());

            NewTekCategory newTekCategory = newTekCategoryMapper.getNewTekCategoryById(newTekProductModelVO.getCategoryId());
            newTekProductModelVO.setCategoryName(newTekCategory.getCategoryName());

            NewTekModel newTekModel = newTekModelMapper.getNewTekModelById(newTekProductModelVO.getModelId());
            newTekProductModelVO.setModelName(newTekModel.getModelName());

            NewTekDispose newTekDispose = newTekDisposeMapper.getNewTekDisposeById(newTekProductModelVO.getDisposeId());
            newTekProductModelVO.setDisposeName(newTekDispose.getDisposeName());

            newTekProductModelVO.setCountryName(newTekProductModelVO.getCountryName().split("_")[0]);
        }
        return newTekProductModelVOList;
    }

    @Override
    public NewTekProductModel getNewTekProductModelById(Long id) {
        return newTekProductModelMapper.getNewTekProductModelById(id);
    }

    @Override
    public int updateNewTekProductModelById(NewTekProductModel newTekProductModel) {
        return newTekProductModelMapper.updateNewTekProductModelById(newTekProductModel);
    }

    @Override
    public String importSKU(List<NewTekProductModel> skuList, Boolean isUpdateSupport) {
        if (StringUtils.isNull(skuList) || skuList.size() == 0) {
            throw new SKUException("导入SKU数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (NewTekProductModel productModel : skuList) {
            try {
                int failureNumNew = failureNum + 1;
                String sku = productModel.getSku();
                String countryName = productModel.getCountryName();
                List<TekSkuToMatnr> tekSkuToMatnrList = tekSkuToMatnrService.queryTekSkuByGoodsSKU(sku);
                if (tekSkuToMatnrList.size() == 0 || tekSkuToMatnrList == null) {
//                    failureNum++;
                    failureNum = failureNumNew;
                    failureMsg.append("<br/>" + failureNum + "、SKU " + productModel.getSku() + "导入失败，系统中没有此SKU！");
                }
                NewTekCountryCode newTekCountryCode1 = newTekCountryCodeMapper.getCountryCodeByCode(countryName.split("_")[0]);
                if (newTekCountryCode1 == null) {
//                    failureNum++;
                    failureNum = failureNumNew;
                    failureMsg.append("<br/>" + failureNum + "、SKU " + productModel.getSku() + "导入失败，系统中没有此Country！");
                }
                NewTekProductModel newTekProductModel2 = new NewTekProductModel();
                newTekProductModel2.setSku(sku);
                newTekProductModel2.setCountryName(productModel.getCountryName());
                List<NewTekProductModel> newTekProductModelList = newTekProductModelMapper.getProductModelByCategoryAndModelAndDisposeSortId(newTekProductModel2);
                if (newTekProductModelList.size() > 0 && newTekProductModelList != null) {
                    NewTekProductModel newTekProductModel3 = newTekProductModelList.get(0);
                    if (!productModel.getId().equals(newTekProductModel3.getId())) {
//                        failureNum++;
                        failureNum = failureNumNew;
                        failureMsg.append("<br/>" + failureNum + "、SKU " + productModel.getSku() + "导入失败，同一个SKU同一个Country下只能有一个ChannelType！");
                    }
                }
                newTekProductModel2.setCountryName(null);
                List<NewTekProductModel> newTekProductModelList1 = newTekProductModelMapper.getProductModelByCategoryAndModelAndDisposeSortId(newTekProductModel2);
                if (newTekProductModelList1.size() > 0 && newTekProductModelList1 != null) {
                    NewTekProductModel newTekProductModel3 = newTekProductModelList1.get(0);
                    if (!newTekProductModel3.getCategoryId().equals(productModel.getCategoryId()) || !newTekProductModel3.getModelId().equals(productModel.getModelId()) || !newTekProductModel3.getDisposeId().equals(productModel.getDisposeId())) {
//                        failureNum++;
                        failureNum = failureNumNew;
                        failureMsg.append("<br/>" + failureNum + "、SKU " + productModel.getSku() + "导入失败，品类、系列、配置必须与之前的一致！");
                    }
                }
                // 验证是否存在这个sku对应关系
                QueryProductModelReq req = new QueryProductModelReq();
                req.setCountryName(productModel.getCountryName());
                req.setSku(productModel.getSku());
                List<NewTekProductModelVO> productModelVOList = newTekProductModelMapper.getNewTekProductModelForPage(req);
                if (productModelVOList.size() == 0 || productModelVOList == null) {
                    QueryCategoryReq categoryReq = new QueryCategoryReq();
                    categoryReq.setCategoryName(productModel.getCategoryName());
                    List<NewTekCategory> categoryList = newTekCategoryMapper.queryNewTekCategoryAll(categoryReq);
                    if (categoryList.size() > 0 && categoryList != null) {
                        NewTekModel newTekModel = new NewTekModel();
                        newTekModel.setModelName(productModel.getModelName());
                        newTekModel.setCategoryId(categoryList.get(0).getId());
                        List<NewTekModel> newTekModelList = newTekModelMapper.getModelByCategoryAndModelName(newTekModel);
                        if (newTekModelList.size() > 0 && newTekModelList != null) {
                            NewTekDispose newTekDispose = new NewTekDispose();
                            newTekDispose.setDisposeGroupId(categoryList.get(0).getDisposeGroupId());
                            newTekDispose.setDisposeName(productModel.getDisposeName());
                            NewTekDispose newTekDispose1 = newTekDisposeMapper.getNewTekDisposeByCategoryAndModelAndSortId(newTekDispose);
                            if (newTekDispose1 != null) {
                                productModel.setCategoryId(categoryList.get(0).getId());
                                productModel.setModelId(newTekModelList.get(0).getId());
                                productModel.setDisposeId(newTekDispose1.getId());
                                String country = productModel.getCountryName();
                                NewTekCountryCode newTekCountryCode = newTekCountryCodeMapper.getCountryCodeByCode(country);
                                if (newTekCountryCode != null) {
                                    productModel.setCountryName(newTekCountryCode.getCode() + "_" + newTekCountryCode.getChineseName() + "_" + newTekCountryCode.getEnglishName());
                                }
                                this.insertNewTekProductModel(productModel);
                                successNum++;
                                successMsg.append("<br/>" + successNum + "、SKU " + productModel.getSku() + " 导入成功");
                            } else {
//                                failureNum++;
                                failureMsg.append("<br/>" + failureNum + "、SKU " + productModel.getSku() + "导入失败，没有查询到对应配置！");
                            }
                        } else {
//                            failureNum++;
                            failureMsg.append("<br/>" + failureNum + "、SKU " + productModel.getSku() + "导入失败，没有查询到对应系列！");
                        }
                    } else {
//                        failureNum++;
                        failureNum = failureNumNew;
                        failureMsg.append("<br/>" + failureNum + "、SKU " + productModel.getSku() + "导入失败，没有查询到对应品类！");
                    }
                } else if (isUpdateSupport) {
                    // TODO: 2020/7/16  
//                    user.setUpdateBy(operName);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、SKU " + productModel.getSku() + " 更新成功");
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、SKU " + productModel.getSku() + " 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、sku " + productModel.getSku() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new CustomException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    @Override
    public List<NewTekProductModel> getProductModelByCategoryAndModelAndDisposeSortId(NewTekProductModel newTekProductModel) {
        return newTekProductModelMapper.getProductModelByCategoryAndModelAndDisposeSortId(newTekProductModel);
    }

    @Override
    public int deleteProductModelByIds(Long[] ids) {
        return newTekProductModelMapper.deleteProductModelByIds(ids);
    }

    @Override
    public List<NewTekProductModel> getProductModelBySKU(String sku) {
        return newTekProductModelMapper.getProductModelBySKU(sku);
    }

    @Override
    public List<NewTekProductModel> getAllCountry() {
        return newTekProductModelMapper.getAllCountry();
    }
}
