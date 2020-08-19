package com.timain.framework.utils;

import com.timain.common.utils.StringUtils;
import com.timain.common.utils.bean.BeanUtils;
import com.timain.framework.shiro.realm.UserRealm;
import com.timain.system.domain.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;

/**
 * shiro 工具类
 * @Author: yyf1172719533
 * @Date: 2020/8/18 15:55
 * @Version: 1.0
 */
public class ShiroUtils {

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    public static void logout() {
        getSubject().logout();
    }

    public static SysUser getSysUser() {
        SysUser user = null;
        Object object = getSubject().getPrincipal();
        if (StringUtils.isNotNull(object)) {
            user = new SysUser();
            BeanUtils.copyBeanProp(user, object);
        }
        return user;
    }

    public static void setSysUser(SysUser user) {
        Subject subject = getSubject();
        PrincipalCollection principals = subject.getPrincipals();
        String realmName = principals.getRealmNames().iterator().next();
        PrincipalCollection simplePrincipalCollection = new SimplePrincipalCollection(user, realmName);
        //重新加载Principal
        subject.runAs(simplePrincipalCollection);
    }

    public static void clearCachedAuthorizationInfo() {
        RealmSecurityManager securityManager = (RealmSecurityManager) SecurityUtils.getSecurityManager();
        UserRealm realm = (UserRealm) securityManager.getRealms().iterator().next();
        realm.clearCachedAuthorizationInfo();
    }

    public static Long getUserId() {
        return getSysUser().getUserId().longValue();
    }

    public static String getLoginName() {
        return getSysUser().getLoginName();
    }

    public static String getIp() {
        return getSubject().getSession().getHost();
    }

    public static String getSessionId() {
        return String.valueOf(getSubject().getSession().getId());
    }

    /**
     * 生成随机盐
     * @return
     */
    public static String randomSalt() {
        //一个Byte占两个字节，此处生成的3字节，字符串长度为6
        SecureRandomNumberGenerator generator = new SecureRandomNumberGenerator();
        String hex = generator.nextBytes(3).toHex();
        return hex;
    }
}
