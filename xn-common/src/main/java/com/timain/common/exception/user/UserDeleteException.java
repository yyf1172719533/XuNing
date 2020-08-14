package com.timain.common.exception.user;

/**
 * 用户账号删除异常类
 * @author yyf
 * @version 1.0
 * @date 2020/8/14 11:07
 */
public class UserDeleteException extends UserException {

    private static final long serialVersionUID = 1L;
    
    public UserDeleteException() {
        super("user.password.delete", null);
    }
}
