package com.timain.common.exception.user;

/**
 * 用户密码不正确或不符合规范异常类
 * @author yyf
 * @version 1.0
 * @date 2020/8/14 11:09
 */
public class UserPasswordNotMatchException extends UserException {

    private static final long serialVersionUID = 1L;
    
    public UserPasswordNotMatchException() {
        super("user.password.not.match", null);
    }
}
