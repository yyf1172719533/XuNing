package com.timain.system.service;

import com.timain.common.core.domain.Ztree;
import com.timain.system.domain.SysMenu;
import com.timain.system.domain.SysRole;
import com.timain.system.domain.SysUser;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 菜单 业务层
 * @Author: yyf1172719533
 * @Date: 2020/8/19 8:54
 * @Version: 1.0
 */
public interface SysMenuService {

    /**
     * 根据用户ID查询菜单
     * @param user 用户信息
     * @return 菜单列表
     */
    List<SysMenu> queryMenusByUser(SysUser user);

    /**
     * 查询菜单列表
     * @param menu 菜单信息
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<SysMenu> queryMenuList(SysMenu menu, Long userId);

    /**
     * 查询菜单集合
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<SysMenu> queryMenuAll(Long userId);

    /**
     * 根据用户ID查询权限
     * @param userId 用户ID
     * @return 权限列表
     */
    Set<String> queryPermsByUserId(Long userId);

    /**
     * 根据角色ID查询菜单
     * @param role 角色对象
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<Ztree> queryMenuByRole(SysRole role, Long userId);

    /**
     * 查询所有菜单信息
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<Ztree> queryMenuTree(Long userId);

    /**
     * 查询系统所有权限
     * @param userId 用户ID
     * @return 权限列表
     */
    Map<String, String> queryPermsAll(Long userId);

    /**
     * 删除菜单管理信息
     * @param menuId 菜单ID
     * @return
     */
    int deleteMenuById(Long menuId);

    /**
     * 根据菜单ID查询信息
     * @param menuId 菜单ID
     * @return 菜单信息
     */
    SysMenu queryMenuById(Long menuId);

    /**
     * 查询菜单数量
     * @param parentId 菜单父ID
     * @return
     */
    int queryMenuCountByParentId(Long parentId);

    /**
     * 查询菜单使用数量
     * @param menuId 菜单ID
     * @return
     */
    int queryRoleCountMenuByMenuId(Long menuId);

    /**
     * 保存菜单信息
     * @param menu 菜单信息
     * @return
     */
    int saveMenu(SysMenu menu);

    /**
     * 修改菜单信息
     * @param menu 菜单信息
     * @return
     */
    int updateMenu(SysMenu menu);

    /**
     * 校验菜单名称是否唯一
     * @param menu 菜单信息
     * @return
     */
    String checkMenuNameUnique(SysMenu menu);
}
