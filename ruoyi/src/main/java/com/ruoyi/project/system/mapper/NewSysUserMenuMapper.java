package com.ruoyi.project.system.mapper;

import com.ruoyi.project.system.domain.NewSysUserMenu;
import com.ruoyi.project.system.domain.SysRoleMenu;

import java.util.List;

/**
 * 用户与菜单关联表 数据层
 * 
 * @author ruoyi
 */
public interface NewSysUserMenuMapper
{
    /**
     * 查询菜单使用数量
     * 
     * @param menuId 菜单ID
     * @return 结果
     */
    public int checkMenuExistUser(Long menuId);

    /**
     * 通过用户ID删除角色和菜单关联
     * 
     * @param userId 角色ID
     * @return 结果
     */
    public int deleteUserMenuByUserId(Long userId);

    /**
     * 批量新增用户菜单信息
     * 
     * @param userMenuList 用户菜单列表
     * @return 结果
     */
    public int batchUserMenu(List<NewSysUserMenu> userMenuList);
}
