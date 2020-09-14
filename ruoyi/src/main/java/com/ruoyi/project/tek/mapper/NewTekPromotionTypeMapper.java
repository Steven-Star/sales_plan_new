package com.ruoyi.project.tek.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.project.tek.domain.NewTekCategory;
import com.ruoyi.project.tek.domain.NewTekCountryCode;
import com.ruoyi.project.tek.domain.NewTekPromotionType;
import com.ruoyi.project.tek.domain.NewTekSpecialEvent;
import com.ruoyi.project.tek.request.QueryCategoryReq;
import com.ruoyi.project.tek.request.QuerySpecialEventReq;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 国家和地区代码表 Mapper 接口
 * </p>
 *
 * @author steven.guo
 * @since 2020-06-29
 */
@Repository
public interface NewTekPromotionTypeMapper extends BaseMapper<NewTekPromotionType> {

    List<NewTekPromotionType> queryNewTekPromotionTypeAll(NewTekPromotionType newTekPromotionType);

    int insertNewTekPromotionType(NewTekPromotionType newTekPromotionType);

    int updateNewTekPromotionType(NewTekPromotionType newTekPromotionType);

    NewTekPromotionType getNewTekPromotionTypeById(Long id);

    int deletePromotionTypeByIds(Long[] ids);

    List<NewTekPromotionType> getAllPromotionType();

}
