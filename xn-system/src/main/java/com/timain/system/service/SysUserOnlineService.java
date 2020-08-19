package com.timain.system.service;

import com.timain.system.domain.SysUserOnline;

import java.util.Date;
import java.util.List;

/**
 * 在线用户 服务层
 * @Author: yyf1172719533
 * @Date: 2020/8/19 10:21
 * @Version: 1.0
 */
public interface SysUserOnlineService {

    /**
     * 根据会话ID查询信息
     * @param sessionId 会话ID
     * @return 在线用户信息
     */
    SysUserOnline queryOnlineById(String sessionId);

    /**
     * 根据会话ID删除信息
     * @param sessionId 会话ID
     */
    void deleteOnlineById(String sessionId);

    /**
     * 批量删除信息
     * @param sessionIds 会话ID集合
     */
    void batchDeleteOnline(List<String> sessionIds);

    /**
     * 保存会话信息
     * @param online 会话信息
     */
    void saveOnline(SysUserOnline online);

    /**
     * 查询会话集合
     * @param userOnline 分页参数
     * @return 会话集合
     */
    List<SysUserOnline> queryUserOnlineList(SysUserOnline userOnline);

    /**
     * 强退用户
     * @param sessionId 会话ID
     */
    void forceLogout(String sessionId);

    /**
     * 清理用户缓存
     * @param loginName 登录名称
     * @param sessionId 会话ID
     */
    void removeUserCache(String loginName, String sessionId);

    /**
     * 查询会话集合
     * @param expiredDate 有效期
     * @return 会话集合
     */
    List<SysUserOnline> queryOnlineByExpired(Date expiredDate);
}
