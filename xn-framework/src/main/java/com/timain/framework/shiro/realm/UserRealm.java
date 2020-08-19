package com.timain.framework.shiro.realm;

import com.timain.common.exception.user.*;
import com.timain.framework.shiro.service.SysLoginService;
import com.timain.framework.utils.ShiroUtils;
import com.timain.system.domain.SysUser;
import com.timain.system.service.SysMenuService;
import com.timain.system.service.SysRoleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * 自定义Realm 处理登录 权限
 *
 * @Author: yyf1172719533
 * @Date: 2020/8/18 16:23
 * @Version: 1.0
 */
public class UserRealm extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(UserRealm.class);

    @Autowired
    private SysMenuService menuService;
    @Autowired
    private SysRoleService roleService;
    @Autowired
    private SysLoginService loginService;

    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SysUser user = ShiroUtils.getSysUser();
        //角色列表
        Set<String> roles = new HashSet<>();
        //菜单列表
        Set<String> menus = new HashSet<>();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //判断是否是管理员
        if (user.isAdmin()) {
            authorizationInfo.addRole("admin");
            authorizationInfo.addStringPermission("*:*:*");
        } else {
            roles = roleService.queryRoleKeys(user.getUserId());
            menus = menuService.queryPermsByUserId(user.getUserId());
            //角色加入AuthorizationInfo认证对象
            authorizationInfo.setRoles(roles);
            //权限加入AuthorizationInfo认证对象
            authorizationInfo.setStringPermissions(menus);
        }
        return authorizationInfo;
    }

    /**
     * 登录认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        String password = "";
        if (null != token.getPassword()) {
            password = new String(token.getPassword());
        }
        SysUser user = null;
        try {
            user = loginService.login(username, password);
        } catch (CaptchaException e) {
            throw new AuthenticationException(e.getMessage(), e);
        } catch (UserNotExistsException e) {
            throw new UnknownAccountException(e.getMessage(), e);
        } catch (UserPasswordNotMatchException e) {
            throw new IncorrectCredentialsException(e.getMessage(), e);
        } catch (UserPasswordRetryLimitExceedException e) {
            throw new ExcessiveAttemptsException(e.getMessage(), e);
        } catch (UserBlockedException e) {
            throw new LockedAccountException(e.getMessage(), e);
        } catch (RoleBlockedException e) {
            throw new LockedAccountException(e.getMessage(), e);
        } catch (Exception e) {
            logger.info("对用户[" + username + "]进行登录验证..验证未通过{}", e.getMessage());
            throw new AuthenticationException(e.getMessage(), e);
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, username);
        return info;
    }

    /**
     * 清理缓存权限
     */
    public void clearCachedAuthorizationInfo() {
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }
}
