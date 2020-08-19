package com.timain.framework.shiro.session;

import com.timain.common.utils.IpUtils;
import com.timain.common.utils.ServletUtils;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.web.session.mgt.WebSessionContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义sessionFactory会话
 * @Author: yyf1172719533
 * @Date: 2020/8/19 15:57
 * @Version: 1.0
 */
@Component
public class OnlineSessionFactory implements SessionFactory {

    @Override
    public Session createSession(SessionContext sessionContext) {
        OnlineSession onlineSession = new OnlineSession();
        if (null != sessionContext && sessionContext instanceof WebSessionContext) {
            WebSessionContext context = (WebSessionContext) sessionContext;
            HttpServletRequest request = (HttpServletRequest) context.getServletRequest();
            if (null != request) {
                UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
                //获取客户端操作系统
                String os = userAgent.getOperatingSystem().getName();
                //获取客户端浏览器
                String browser = userAgent.getBrowser().getName();
                onlineSession.setHost(IpUtils.getIpAddr(request));
                onlineSession.setBrowser(browser);
                onlineSession.setOs(os);
            }
        }
        return onlineSession;
    }
}
