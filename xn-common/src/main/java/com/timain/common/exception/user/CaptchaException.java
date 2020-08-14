package com.timain.common.exception.user;

/**
 * 验证码错误异常类
 * @author yyf
 * @version 1.0
 * @date 2020/8/14 11:04
 */
public class CaptchaException extends UserException {

    private static final long serialVersionUID = 1L;
    
    public CaptchaException() {
        super("user.jcaptcha.error", null);
    }
}
