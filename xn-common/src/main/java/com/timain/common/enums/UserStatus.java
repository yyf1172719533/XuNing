package com.timain.common.enums;

/**
 * 用户状态
 * @Author yyf
 * @Version 1.0
 * @Date 2020/7/18 0018 21:10
 */
public enum UserStatus {

    OK("0", "正常"),
    DISABLE("1", "停用"),
    DELETED("2", "删除");

    private final String code;
    private final String info;

    UserStatus(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
