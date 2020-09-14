package com.ruoyi.project.tek.vo;

import lombok.Data;

import java.util.List;

/**
 * @author steven.guo
 * @version 1.0
 * @description:
 * @date 2020/6/28 14:51
 */
@Data
public class DashboardVO {

    List<NewTekCalendarVO> newTekCalendarVOList;

    List<String> customerList;

    List<String> skuList;

}
