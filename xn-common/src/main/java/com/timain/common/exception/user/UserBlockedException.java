package com.timain.common.exception.user;

/**
 * 用户锁定异常类
 * @author yyf
 * @version 1.0
 * @date 2020/8/14 11:06
 */
public class UserBlockedException extends UserException {

    private static final long serialVersionUID = 1L;

    public UserBlockedException() {
        super("user.blocked", null);
    }
}
