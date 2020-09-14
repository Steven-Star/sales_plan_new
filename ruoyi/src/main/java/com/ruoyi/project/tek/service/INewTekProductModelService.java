package com.ruoyi.project.tek.service;

import com.ruoyi.project.system.domain.SysUser;
import com.ruoyi.project.tek.domain.NewTekProductModel;
import com.ruoyi.project.tek.request.QueryProductModelReq;
import com.ruoyi.project.tek.vo.NewTekProductModelVO;

import java.util.List;

/**
 * <p>
 * 商品 服务类
 * </p>
 *
 * @author steven.guo
 * @since 2020-05-28
 */
public interface INewTekProductModelService {

    List<NewTekProductModelVO> getNewTekProductModel(QueryProductModelReq req);

    int insertNewTekProductModel(NewTekProductModel newTekProductModel);

    NewTekProductModelVO getNewTekProductModelByProductId(Long productId);

    List<NewTekProductModelVO> getNewTekProductModelForPage(QueryProductModelReq req);

    List<NewTekProductModelVO> getNewTekProductModelForPagePermission(QueryProductModelReq req);

    NewTekProductModel getNewTekProductModelById(Long id);

    int updateNewTekProductModelById(NewTekProductModel newTekProductModel);

    /**
     * 导入SKU数据
     *
     * @param skuList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    public String importSKU(List<NewTekProductModel> skuList, Boolean isUpdateSupport);

    List<NewTekProductModel> getProductModelByCategoryAndModelAndDisposeSortId(NewTekProductModel newTekProductModel);

    int deleteProductModelByIds(Long[] ids);

    List<NewTekProductModel> getProductModelBySKU(String sku);

    /**
     * 权限设计中查询所有可以选择的国家
     * */
    List<NewTekProductModel> getAllCountry();

}
