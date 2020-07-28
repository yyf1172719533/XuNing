package com.timain.common.exception;

/**
 * 工具类异常
 * @author yyf
 * @version 1.0
 * @date 2020/7/28 11:03
 */
public class UtilException extends RuntimeException {

    private static final long serialVersionUID = -516085764151058572L;

    public UtilException(Throwable e) {
        super(e.getMessage(), e);
    }

    public UtilException(String message) {
        super(message);
    }

    public UtilException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
