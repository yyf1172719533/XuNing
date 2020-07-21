package com.timain.system.service.impl;

import com.timain.common.annotation.DataScope;
import com.timain.common.utils.StringUtils;
import com.timain.system.domain.SysUser;
import com.timain.system.domain.SysUserRole;
import com.timain.system.mapper.SysUserMapper;
import com.timain.system.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author yyf
 * @Version 1.0
 * @Date 2020/7/21 0021 19:55
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysUserServiceImpl implements SysUserService {

    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    private SysUserMapper userMapper;


    /**
     * 根据条件分页查询用户列表
     *
     * @param user
     * @return
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> queryUserList(SysUser user) {
        return userMapper.selectUserList(user);
    }

    /**
     * 根据条件分页查询已分配用户角色列表
     *
     * @param user
     * @return
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> queryAllocatedList(SysUser user) {
        return userMapper.selectAllocatedList(user);
    }

    /**
     * 根据条件分页查询未分配用户角色列表
     *
     * @param user
     * @return
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> queryUnallocatedList(SysUser user) {
        return userMapper.selectUnallocatedList(user);
    }

    /**
     * 通过用户昵称查询用户
     *
     * @param loginName
     * @return
     */
    @Override
    public SysUser queryUserByLoginName(String loginName) {
        return userMapper.selectUserByLoginName(loginName);
    }

    /**
     * 根据手机号码查询用户
     *
     * @param phoneNumber
     * @return
     */
    @Override
    public SysUser queryUserByPhoneNumber(String phoneNumber) {
        return userMapper.selectUserByPhoneNumber(phoneNumber);
    }

    /**
     * 根据邮箱查询用户
     *
     * @param email
     * @return
     */
    @Override
    public SysUser queryUserByEmail(String email) {
        return userMapper.selectUserByEmail(email);
    }

    /**
     * 通过用户ID查询用户
     *
     * @param userId
     * @return
     */
    @Override
    public SysUser queryUserByUserId(Long userId) {
        return userMapper.selectUserById(userId);
    }

    /**
     * 通过用户ID查询用户和角色关联信息
     *
     * @param userId
     * @return
     */
    @Override
    public List<SysUserRole> queryUserRoleByUserId(Long userId) {
        return null;
    }

    /**
     * 通过用户ID删除用户
     *
     * @param userId
     * @return
     */
    @Override
    public int deleteUserByUserId(Long userId) {
        return 0;
    }

    /**
     * 批量删除用户信息
     *
     * @param ids
     * @return
     * @throws Exception
     */
    @Override
    public int deleteUserByUserIds(String ids) throws Exception {
        return 0;
    }

    /**
     * 保存用户信息
     *
     * @param user
     * @return
     */
    @Override
    public int saveUserInfo(SysUser user) {
        return 0;
    }

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    @Override
    public boolean registerUser(SysUser user) {
        return false;
    }

    /**
     * 保存用户信息
     *
     * @param user
     * @return
     */
    @Override
    public int updateUser(SysUser user) {
        return 0;
    }

    /**
     * 修改用户详细信息
     *
     * @param user
     * @return
     */
    @Override
    public int updateUserInfo(SysUser user) {
        return 0;
    }

    /**
     * 用户授权角色
     *
     * @param userId
     * @param roleIds
     */
    @Override
    public void saveUserAuth(Long userId, Long[] roleIds) {

    }

    /**
     * 修改用户密码信息
     *
     * @param user
     * @return
     */
    @Override
    public int resetUserPwd(SysUser user) {
        return 0;
    }

    /**
     * 校验用户昵称是否唯一
     *
     * @param loginName
     * @return
     */
    @Override
    public String checkLoginNameUnique(String loginName) {
        return null;
    }

    /**
     * 校验手机号码是否唯一
     *
     * @param user
     * @return
     */
    @Override
    public String checkPhoneNumberUnique(SysUser user) {
        return null;
    }

    /**
     * 校验email是否唯一
     *
     * @param user
     * @return
     */
    @Override
    public String checkEmailUnique(SysUser user) {
        return null;
    }

    /**
     * 校验用户是否允许操作
     *
     * @param user
     */
    @Override
    public void checkUserAllowed(SysUser user) {

    }

    /**
     * 根据用户ID查询用户所属角色组
     *
     * @param userId
     * @return
     */
    @Override
    public String queryUserRoleGroup(Long userId) {
        return null;
    }

    /**
     * 根据用户ID查询用户所属岗位组
     *
     * @param userId
     * @return
     */
    @Override
    public String queryUserPostGroup(Long userId) {
        return null;
    }

    /**
     * 导入用户数据
     *
     * @param userList        用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName        操作用户
     * @return
     */
    @Override
    public String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(userList) || userList.size() == 0) {

        }
        return null;
    }

    /**
     * 用户状态修改
     *
     * @param user
     * @return
     */
    @Override
    public int changeStatus(SysUser user) {
        return userMapper.updateUser(user);
    }
}
