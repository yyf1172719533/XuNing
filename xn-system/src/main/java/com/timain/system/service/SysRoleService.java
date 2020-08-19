package com.timain.system.service;

import com.timain.system.domain.SysRole;
import com.timain.system.domain.SysUserRole;

import java.util.List;
import java.util.Set;

/**
 * 角色业务层
 * @Author: yyf1172719533
 * @Date: 2020/8/19 8:55
 * @Version: 1.0
 */
public interface SysRoleService {

    /**
     * 根据条件分页查询角色数据
     * @param role 角色信息
     * @return 角色数据集合信息
     */
    List<SysRole> queryRoleList(SysRole role);

    /**
     * 根据用户ID查询角色
     * @param userId 用户ID
     * @return 权限列表
     */
    Set<String> queryRoleKeys(Long userId);

    /**
     * 根据用户ID查询角色
     * @param userId 用户ID
     * @return 角色列表
     */
    List<SysRole> queryRolesByUserId(Long userId);

    /**
     * 查询所有角色
     * @return
     */
    List<SysRole> queryRoleAll();

    /**
     * 通过角色ID查询角色
     * @param roleId 角色ID
     * @return 角色对象信息
     */
    SysRole queryRoleById(Long roleId);

    /**
     * 通过角色ID删除角色
     * @param roleId 角色ID
     * @return
     */
    boolean deleteRoleById(Long roleId);

    /**
     * 批量删除角色用户信息
     * @param ids
     * @return
     */
    int deleteRoleByIds(String ids);

    /**
     * 保存角色信息
     * @param role 角色信息
     * @return
     */
    int saveRole(SysRole role);

    /**
     * 修改角色信息
     * @param role 角色信息
     * @return
     */
    int updateRole(SysRole role);

    /**
     * 修改数据权限信息
     * @param role 角色信息
     * @return
     */
    int authDataScope(SysRole role);

    /**
     * 校验角色名称是否唯一
     * @param role 角色信息
     * @return
     */
    String checkRoleNameUnique(SysRole role);

    /**
     * 校验角色权限是否唯一
     * @param role 角色信息
     * @return
     */
    String checkRoleKeyUnique(SysRole role);

    /**
     * 校验角色是否允许操作
     * @param role 角色信息
     */
    void checkRoleAllowed(SysRole role);

    /**
     * 通过角色ID查询角色使用数量
     * @param roleId 角色ID
     * @return
     */
    int queryCountUserRoleByRoleId(Long roleId);

    /**
     * 角色状态修改
     * @param role 角色信息
     * @return
     */
    int changeStatus(SysRole role);

    /**
     * 取消授权用户角色
     * @param userRole 用户和角色关联信息
     * @return
     */
    int deleteAuthUser(SysUserRole userRole);

    /**
     * 批量取消授权用户角色
     * @param roleId 角色ID
     * @param userIds 需要删除的用户数据ID
     * @return
     */
    int deleteAuthUsers(Long roleId, String userIds);

    /**
     * 批量选择授权用户角色
     * @param roleId 角色ID
     * @param userIds 需要删除的用户数据ID
     * @return
     */
    int saveAuthUsers(Long roleId, String userIds);
}
