package com.timain.system.mapper;

import com.timain.system.domain.SysUser;

import java.util.List;

/**
 * 用户表 数据层
 * @Author yyf
 * @Version 1.0
 * @Date 2020/7/19 0019 19:17
 */
public interface SysUserMapper {

    /**
     * 根据条件分页查询用户列表
     * @param sysUser 用户信息
     * @return 用户信息集合
     */
    List<SysUser> selectUserList(SysUser sysUser);

    /**
     * 根据条件分页查询已分配用户角色列表
     * @param sysUser 用户信息
     * @return 用户信息集合
     */
    List<SysUser> selectAllocatedList(SysUser sysUser);

    /**
     * 根据条件分页查询未分配用户角色列表
     * @param sysUser 用户信息
     * @return 用户信息集合
     */
    List<SysUser> selectUnallocatedList(SysUser sysUser);

    /**
     * 根据用户昵称查询用户
     * @param loginName 用户昵称
     * @return 用户信息
     */
    SysUser selectUserByLoginName(String loginName);

    /**
     * 根据手机号码查询用户
     * @param phoneNumber 手机号码
     * @return 用户信息
     */
    SysUser selectUserByPhoneNumber(String phoneNumber);

    /**
     * 根据邮箱查询用户
     * @param email 邮箱
     * @return 用户信息
     */
    SysUser selectUserByEmail(String email);

    /**
     * 通过用户ID查询用户
     * @param userId 用户ID
     * @return 用户信息
     */
    SysUser selectUserById(Long userId);

    /**
     * 通过用户ID删除用户
     * @param userId 用户ID
     * @return 结果
     */
    int deleteUserById(Long userId);

    /**
     * 批量删除用户信息
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteUserByIds(Long[] ids);

    /**
     * 修改用户信息
     * @param sysUser
     * @return
     */
    int updateUser(SysUser sysUser);

    /**
     * 添加用户信息
     * @param sysUser
     * @return
     */
    int insertUser(SysUser sysUser);

    /**
     * 校验用户昵称是否唯一
     * @param loginName
     * @return
     */
    int checkLoginNameUnique(String loginName);

    /**
     * 校验手机号码是否唯一
     * @param phoneNumber
     * @return
     */
    int checkPhoneNumberUnique(String phoneNumber);

    /**
     * 校验邮箱是否唯一
     * @param email
     * @return
     */
    int checkEmailUnique(String email);
}
