package com.timain.system.service;

import com.timain.system.domain.SysUser;
import com.timain.system.domain.SysUserRole;

import java.util.List;

/**
 * 用户 业务层
 * @Author yyf
 * @Version 1.0
 * @Date 2020/7/19 0019 19:42
 */
public interface SysUserService {

    /**
     * 根据条件分页查询用户列表
     * @param user
     * @return
     */
    List<SysUser> queryUserList(SysUser user);

    /**
     * 根据条件分页查询已分配用户角色列表
     * @param user
     * @return
     */
    List<SysUser> queryAllocatedList(SysUser user);

    /**
     * 根据条件分页查询未分配用户角色列表
     * @param user
     * @return
     */
    List<SysUser> queryUnallocatedList(SysUser user);

    /**
     * 通过用户昵称查询用户
     * @param loginName
     * @return
     */
    SysUser queryUserByLoginName(String loginName);

    /**
     * 根据手机号码查询用户
     * @param phoneNumber
     * @return
     */
    SysUser queryUserByPhoneNumber(String phoneNumber);

    /**
     * 根据邮箱查询用户
     * @param email
     * @return
     */
    SysUser queryUserByEmail(String email);

    /**
     * 通过用户ID查询用户
     * @param userId
     * @return
     */
    SysUser queryUserByUserId(Long userId);

    /**
     * 通过用户ID查询用户和角色关联信息
     * @param userId
     * @return
     */
    List<SysUserRole> queryUserRoleByUserId(Long userId);

    /**
     * 通过用户ID删除用户
     * @param userId
     * @return
     */
    int deleteUserByUserId(Long userId);

    /**
     * 批量删除用户信息
     * @param ids
     * @return
     * @throws Exception
     */
    int deleteUserByUserIds(String ids) throws Exception;

    /**
     * 保存用户信息
     * @param user
     * @return
     */
    int saveUserInfo(SysUser user);

    /**
     * 用户注册
     * @param user
     * @return
     */
    boolean registerUser(SysUser user);

    /**
     * 保存用户信息
     * @param user
     * @return
     */
    int updateUser(SysUser user);

    /**
     * 修改用户详细信息
     * @param user
     * @return
     */
    int updateUserInfo(SysUser user);

    /**
     * 用户授权角色
     * @param userId
     * @param roleIds
     */
    void saveUserAuth(Long userId, Long[] roleIds);

    /**
     * 修改用户密码信息
     * @param user
     * @return
     */
    int resetUserPwd(SysUser user);

    /**
     * 校验用户昵称是否唯一
     * @param loginName
     * @return
     */
    String checkLoginNameUnique(String loginName);

    /**
     * 校验手机号码是否唯一
     * @param user
     * @return
     */
    String checkPhoneNumberUnique(SysUser user);

    /**
     * 校验email是否唯一
     * @param user
     * @return
     */
    String checkEmailUnique(SysUser user);

    /**
     * 校验用户是否允许操作
     * @param user
     */
    void checkUserAllowed(SysUser user);

    /**
     * 根据用户ID查询用户所属角色组
     * @param userId
     * @return
     */
    String queryUserRoleGroup(Long userId);

    /**
     * 根据用户ID查询用户所属岗位组
     * @param userId
     * @return
     */
    String queryUserPostGroup(Long userId);

    /**
     * 导入用户数据
     * @param userList 用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return
     */
    String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName);

    /**
     * 用户状态修改
     * @param user
     * @return
     */
    int changeStatus(SysUser user);
}
