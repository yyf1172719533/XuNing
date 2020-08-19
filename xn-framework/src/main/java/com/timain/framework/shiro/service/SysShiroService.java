package com.timain.framework.shiro.service;

import com.timain.common.utils.StringUtils;
import com.timain.framework.shiro.session.OnlineSession;
import com.timain.system.domain.SysUserOnline;
import com.timain.system.service.SysUserOnlineService;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 会话db操作处理
 * @Author: yyf1172719533
 * @Date: 2020/8/19 10:22
 * @Version: 1.0
 */
@Component
public class SysShiroService {

    @Autowired
    private SysUserOnlineService onlineService;

    /**
     * 删除会话
     * @param onlineSession 会话信息
     */
    public void deleteSession(OnlineSession onlineSession) {
        onlineService.deleteOnlineById(String.valueOf(onlineSession.getId()));
    }

    /**
     * 获取会话信息
     * @param sessionId 会话ID
     * @return
     */
    public Session getSession(Serializable sessionId) {
        SysUserOnline userOnline = onlineService.queryOnlineById(String.valueOf(sessionId));
        return StringUtils.isNull(userOnline) ? null : createSession(userOnline);
    }

    public Session createSession(SysUserOnline userOnline) {
        OnlineSession onlineSession = new OnlineSession();
        if (StringUtils.isNotNull(userOnline)) {
            onlineSession.setId(userOnline.getSessionId());
            onlineSession.setHost(userOnline.getIpAddr());
            onlineSession.setBrowser(userOnline.getBrowser());
            onlineSession.setOs(userOnline.getOs());
            onlineSession.setDeptName(userOnline.getDeptName());
            onlineSession.setLoginName(userOnline.getLoginName());
            onlineSession.setStartTimestamp(userOnline.getStartTimestamp());
            onlineSession.setLastAccessTime(userOnline.getLastAccessTime());
            onlineSession.setTimeout(userOnline.getExpireTime());
        }
        return onlineSession;
    }
}
