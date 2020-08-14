package com.timain.common.exception.user;

/**
 * 用户不存在异常类
 * @author yyf
 * @version 1.0
 * @date 2020/8/14 11:08
 */
public class UserNotExistsException extends UserException {

    private static final long serialVersionUID = 1L;

    public UserNotExistsException() {
        super("user.not.exists", null);
    }
}
