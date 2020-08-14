package com.timain.common.exception.user;

import com.timain.common.exception.base.BaseException;

/**
 * 用户信息异常类
 * @author yyf
 * @version 1.0
 * @date 2020/8/14 10:00
 */
public class UserException extends BaseException {

    private static final long serialVersionUID = 1L;
    
    public UserException(String code, Object[] args) {
        super("user", code, args, null);
    }
}
