package com.ruoyi.project.tek.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.project.tek.domain.NewTekCustomer;
import com.ruoyi.project.tek.domain.NewTekSpecialEvent;
import com.ruoyi.project.tek.request.QueryCustomerReq;
import com.ruoyi.project.tek.request.QuerySpecialEventReq;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 销售计划项目-特殊事件表 Mapper 接口
 * </p>
 *
 * @author steven.guo
 * @since 2020-05-28
 */
@Repository
public interface NewTekSpecialEventMapper extends BaseMapper<NewTekSpecialEvent> {

    int insertNewTekSpecialEvent(NewTekSpecialEvent newTekSpecialEvent);

    int updateNewTekSpecialEvent(NewTekSpecialEvent newTekSpecialEvent);

    NewTekSpecialEvent getNewTekSpecialEventById(Long id);

    int deleteSpecialEventByIds(Long[] ids);

    List<NewTekSpecialEvent> getAllSpecialEvent(QuerySpecialEventReq req);


}
