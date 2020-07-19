package com.timain.common.enums;

/**
 * 用户会话
 * @Author yyf
 * @Version 1.0
 * @Date 2020/7/18 0018 21:07
 */
public enum OnlineStatus {

    /** 用户状态 */
    on_line("在线"),
    off_line("离线");

    private final String info;

    private OnlineStatus(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
