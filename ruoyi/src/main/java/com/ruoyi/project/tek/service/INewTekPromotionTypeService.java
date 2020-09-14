package com.ruoyi.project.tek.service;

import com.ruoyi.project.tek.domain.NewTekCategory;
import com.ruoyi.project.tek.domain.NewTekPromotionType;
import com.ruoyi.project.tek.request.QueryCategoryReq;

import java.util.List;

/**
 * <p>
 * 产品品类 服务类
 * </p>
 *
 * @author steven.guo
 * @since 2020-06-16
 */
public interface INewTekPromotionTypeService {

    List<NewTekPromotionType> queryNewTekPromotionTypeAll(NewTekPromotionType newTekPromotionType);

    int insertNewTekPromotionType(NewTekPromotionType newTekPromotionType);

    int updateNewTekPromotionType(NewTekPromotionType newTekPromotionType);

    NewTekPromotionType getNewTekPromotionTypeById(Long id);

    int deletePromotionTypeByIds(Long[] ids);

    List<NewTekPromotionType> getAllPromotionType();

    boolean checkPromotionTypeUnique(String promotionType);

}
