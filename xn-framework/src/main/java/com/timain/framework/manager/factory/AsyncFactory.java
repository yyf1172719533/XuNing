package com.timain.framework.manager.factory;

import com.timain.common.utils.AddressUtils;
import com.timain.common.utils.spring.SpringUtils;
import com.timain.framework.shiro.session.OnlineSession;
import com.timain.system.domain.SysOperLog;
import com.timain.system.domain.SysUserOnline;
import com.timain.system.service.SysOperLogService;
import com.timain.system.service.SysUserOnlineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;

/**
 * 异步工厂（产生任务用）
 * @Author: yyf1172719533
 * @Date: 2020/8/19 14:29
 * @Version: 1.0
 */
public class AsyncFactory {

    private static final Logger sys_user_logger = LoggerFactory.getLogger("sys-user");

    /**
     * 同步session到数据库
     * @param session 在线用户会话
     * @return 任务task
     */
    public static TimerTask syncSessionToDb(final OnlineSession session) {
        return new TimerTask() {
            @Override
            public void run() {
                SysUserOnline userOnline = new SysUserOnline();
                userOnline.setSessionId(String.valueOf(session.getId()));
                userOnline.setDeptName(session.getDeptName());
                userOnline.setLoginName(session.getLoginName());
                userOnline.setStartTimestamp(session.getStartTimestamp());
                userOnline.setLastAccessTime(session.getLastAccessTime());
                userOnline.setExpireTime(session.getTimeout());
                userOnline.setIpAddr(session.getHost());
                userOnline.setLoginLocation(AddressUtils.getRealAddressByIP(session.getHost()));
                userOnline.setBrowser(session.getBrowser());
                userOnline.setOs(session.getOs());
                userOnline.setStatus(session.getStatus());
                SpringUtils.getBean(SysUserOnlineService.class).saveOnline(userOnline);
            }
        };
    }

    /**
     * 操作日志记录
     * @param operLog 操作日志信息
     * @return 任务task
     */
    public static TimerTask recordOper(final SysOperLog operLog) {
        return new TimerTask() {
            @Override
            public void run() {
                //远程查询操作地点
                operLog.setOperLocation(AddressUtils.getRealAddressByIP(operLog.getOperIp()));
                //SpringUtils.getBean(SysOperLogService.class).saveOperLog(operLog);
                //TODO
            }
        };
    }

    public static TimerTask recordLogininfor(final String userName, final String status, final String message, final Object... args) {
        return new TimerTask() {
            @Override
            public void run() {
                //TODO
            }
        };
    }
}
