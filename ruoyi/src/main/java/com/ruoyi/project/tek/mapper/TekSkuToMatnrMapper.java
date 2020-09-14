package com.ruoyi.project.tek.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.project.tek.domain.TekSkuToMatnr;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 字典 Mapper 接口
 * </p>
 *
 * @author steven.guo
 * @since 2020-05-29
 */
@Repository
public interface TekSkuToMatnrMapper extends BaseMapper<TekSkuToMatnr> {

    List<TekSkuToMatnr> queryTekSkuAll();

    List<TekSkuToMatnr> queryTekSkuByGoodsSKU(String goodsSku);

}
