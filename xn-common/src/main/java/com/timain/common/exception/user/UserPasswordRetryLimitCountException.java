package com.timain.common.exception.user;

/**
 * 用户错误记数异常类
 * @author yyf
 * @version 1.0
 * @date 2020/8/14 11:10
 */
public class UserPasswordRetryLimitCountException extends UserException {

    private static final long serialVersionUID = 1L;
    
    public UserPasswordRetryLimitCountException(int retryLimitCount) {
        super("user.password.retry.limit.count", new Object[] { retryLimitCount });
    }
}
