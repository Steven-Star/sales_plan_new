package com.ruoyi.project.tek.service;

import com.ruoyi.project.tek.domain.NewTekSpecialEvent;
import com.ruoyi.project.tek.request.QuerySpecialEventReq;

import java.util.List;

/**
 * <p>
 * 商品 服务类
 * </p>
 *
 * @author steven.guo
 * @since 2020-06-07
 */
public interface INewTekSpecialEventService {

    int insertNewTekSpecialEvent(NewTekSpecialEvent newTekSpecialEvent);

    int updateNewTekSpecialEvent(NewTekSpecialEvent newTekSpecialEvent);

    NewTekSpecialEvent getNewTekSpecialEventById(Long id);

    int deleteSpecialEventByIds(Long[] ids);

    List<NewTekSpecialEvent> getAllSpecialEvent(QuerySpecialEventReq req);

}
