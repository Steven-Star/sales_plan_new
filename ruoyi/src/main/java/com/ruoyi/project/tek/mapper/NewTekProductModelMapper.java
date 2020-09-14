package com.ruoyi.project.tek.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.project.tek.domain.NewTekModel;
import com.ruoyi.project.tek.domain.NewTekProductModel;
import com.ruoyi.project.tek.domain.TblGoods;
import com.ruoyi.project.tek.request.QueryProductModelReq;
import com.ruoyi.project.tek.vo.NewTekProductModelVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 销售计划项目-产品和系列的关系表 Mapper 接口
 * </p>
 *
 * @author steven.guo
 * @since 2020-06-02
 */
@Repository
public interface NewTekProductModelMapper extends BaseMapper<NewTekProductModel> {

    List<NewTekProductModel> getNewTekProductModelByModelId(Long modelId);

    List<NewTekProductModelVO> getNewTekProductModel(QueryProductModelReq req);

    int insertNewTekProductModel(NewTekProductModel newTekProductModel);

    NewTekProductModelVO getNewTekProductModelByProductId(Long productId);

    List<NewTekProductModelVO> getNewTekProductModelForPage(QueryProductModelReq req);

    List<NewTekProductModelVO> getNewTekProductModelForPagePermission(QueryProductModelReq req);

    NewTekProductModel getNewTekProductModelById(Long id);

    int updateNewTekProductModelById(NewTekProductModel newTekProductModel);

    List<NewTekProductModel> getProductModelByCategoryAndModelAndDisposeSortId(NewTekProductModel newTekProductModel);

    int deleteProductModelByIds(Long[] ids);

    List<NewTekProductModel> getProductModelBySKU(String sku);

    List<NewTekProductModel> getAllCountry();

}
