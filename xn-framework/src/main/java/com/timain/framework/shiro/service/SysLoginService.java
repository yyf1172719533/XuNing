package com.timain.framework.shiro.service;

import com.timain.common.constant.Constants;
import com.timain.common.constant.ShiroConstants;
import com.timain.common.constant.UserConstants;
import com.timain.common.enums.UserStatus;
import com.timain.common.exception.user.*;
import com.timain.common.utils.DateUtils;
import com.timain.common.utils.MessageUtils;
import com.timain.common.utils.ServletUtils;
import com.timain.framework.manager.AsyncManager;
import com.timain.framework.manager.factory.AsyncFactory;
import com.timain.framework.utils.ShiroUtils;
import com.timain.system.domain.SysUser;
import com.timain.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 登录校验方法
 * @Author: yyf1172719533
 * @Date: 2020/8/19 14:01
 * @Version: 1.0
 */
@Component
public class SysLoginService {

    @Autowired
    private SysPasswordService passwordService;
    @Autowired
    private SysUserService userService;

    /**
     * 登录
     * @param loginName 登录名称
     * @param password 密码
     * @return
     */
    public SysUser login(String loginName, String password) {
        //验证码校验
        if (!StringUtils.isEmpty(ServletUtils.getRequest().getAttribute(ShiroConstants.CURRENT_CAPTCHA))) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginName, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
            throw new CaptchaException();
        }
        //用户名或密码为空  错误
        if (StringUtils.isEmpty(loginName) || StringUtils.isEmpty(password)) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginName, Constants.LOGIN_FAIL, MessageUtils.message("not.null")));
            throw new UserNotExistsException();
        }
        //密码不在指定长度范围内  错误
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginName, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
            throw new UserPasswordNotMatchException();
        }
        //用户名不在长度范围内  错误
        if (loginName.length() < UserConstants.USERNAME_MIN_LENGTH || loginName.length() > UserConstants.USERNAME_MAX_LENGTH) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginName, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
            throw new UserPasswordNotMatchException();
        }
        //查询用户信息
        SysUser user = userService.queryUserByLoginName(loginName);
        if (user == null) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginName, Constants.LOGIN_FAIL, MessageUtils.message("user.not.exists")));
            throw new UserNotExistsException();
        }
        if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginName, Constants.LOGIN_FAIL, MessageUtils.message("user.password.delete")));
            throw new UserDeleteException();
        }
        if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginName, Constants.LOGIN_FAIL, MessageUtils.message("user.blocked", user.getRemark())));
            throw new UserBlockedException();
        }
        passwordService.validate(user, password);

        AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginName, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        recordLoginInfo(user);
        return user;
    }

    /**
     * 记录登录信息
     * @param user
     */
    public void recordLoginInfo(SysUser user) {
        user.setLoginIp(ShiroUtils.getIp());
        user.setLoginDate(DateUtils.getNowDate());
        userService.updateUserInfo(user);
    }
}
