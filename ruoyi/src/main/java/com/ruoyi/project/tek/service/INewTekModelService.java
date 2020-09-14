package com.ruoyi.project.tek.service;

import com.ruoyi.project.tek.domain.NewTekModel;
import com.ruoyi.project.tek.request.QueryModelReq;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * <p>
 * 商品 服务类
 * </p>
 *
 * @author steven.guo
 * @since 2020-05-28
 */
public interface INewTekModelService {

    /**
     * 新增产品系列信息
     *
     * @param newTekModel 产品系列信息
     * @return 结果
     */
    int insertNewTekModel(NewTekModel newTekModel);

    /**
     * 修改产品系列信息
     *
     * @param newTekModel 产品系列信息
     * @return 结果
     */
    int updateNewTekModel(NewTekModel newTekModel);

    List<NewTekModel> queryNewTekModelAll(QueryModelReq req);

    NewTekModel getNewTekModelById(Long id);

    /**
     * 批量删除产品系列信息
     *
     * @param ids 需要删除的产品系列ID数组
     * @return 结果
     */
    int deleteModelByIds(Long[] ids);

    List<NewTekModel> getAllModel(Long categoryId);

    Boolean checkModelIsExist(Long[] ids);

    List<NewTekModel> getModelByCategoryAndModelName(NewTekModel newTekModel);

}
