package com.ruoyi.project.tek.service.impl;

import com.ruoyi.project.tek.domain.NewTekSpecialEvent;
import com.ruoyi.project.tek.mapper.NewTekSpecialEventMapper;
import com.ruoyi.project.tek.request.QuerySpecialEventReq;
import com.ruoyi.project.tek.service.INewTekSpecialEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * @author steven.guo
 * @version 1.0
 * @description:
 * @date 2020/6/7 19:04
 */
@Service
public class NewTekSpecialEventServiceImpl implements INewTekSpecialEventService {

    @Autowired
    private NewTekSpecialEventMapper newTekSpecialEventMapper;


    @Override
    public int insertNewTekSpecialEvent(NewTekSpecialEvent newTekSpecialEvent) {
        String day = newTekSpecialEvent.getEventDay();
        String year = day.split("-")[0];
        newTekSpecialEvent.setYear(year);
        int rows = newTekSpecialEventMapper.insertNewTekSpecialEvent(newTekSpecialEvent);
        return rows;
    }

    @Override
    public int updateNewTekSpecialEvent(NewTekSpecialEvent newTekSpecialEvent) {
        int rows = newTekSpecialEventMapper.updateNewTekSpecialEvent(newTekSpecialEvent);
        return rows;
    }

    @Override
    public NewTekSpecialEvent getNewTekSpecialEventById(Long id) {
        return newTekSpecialEventMapper.getNewTekSpecialEventById(id);
    }

    @Override
    public int deleteSpecialEventByIds(Long[] ids) {
        return 0;
    }

    @Override
    public List<NewTekSpecialEvent> getAllSpecialEvent(QuerySpecialEventReq req) {
        if (req.getYear() == null) {
            LocalDate now = LocalDate.now();
            String date = String.valueOf(now.getYear());
            req.setYear(date);
        }
        return newTekSpecialEventMapper.getAllSpecialEvent(req);
    }
}
