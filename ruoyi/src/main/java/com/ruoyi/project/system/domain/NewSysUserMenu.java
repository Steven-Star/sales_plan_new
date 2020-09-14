package com.ruoyi.project.system.domain;

import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 用户和菜单关联 new_sys_user_menu
 * 
 * @author steven.guo
 */
@Data
@ToString
public class NewSysUserMenu
{
    /** 用户ID */
    private Long userId;
    
    /** 菜单ID */
    private Long menuId;

}
