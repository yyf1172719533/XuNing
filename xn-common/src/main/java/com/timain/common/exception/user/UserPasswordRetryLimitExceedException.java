package com.timain.common.exception.user;

/**
 * 用户错误最大次数异常类
 * @author yyf
 * @version 1.0
 * @date 2020/8/14 11:11
 */
public class UserPasswordRetryLimitExceedException extends UserException {

    private static final long serialVersionUID = 1L;
    
    public UserPasswordRetryLimitExceedException(int retryLimitCount) {
        super("user.password.retry.limit.exceed", new Object[] { retryLimitCount });
    }
}
