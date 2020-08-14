package com.timain.common.exception.user;

/**
 * 角色锁定异常类
 * @author yyf
 * @version 1.0
 * @date 2020/8/14 11:05
 */
public class RoleBlockedException extends UserException {

    private static final long serialVersionUID = 1L;
    
    public RoleBlockedException() {
        super("role.blocked", null);
    }
}
