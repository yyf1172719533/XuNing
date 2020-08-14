package com.timain.common.exception;

/**
 * 业务异常
 * @author yyf
 * @version 1.0
 * @date 2020/8/14 9:55
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
    protected final String message;
    
    public BusinessException(String message) {
        this.message = message;
    }
    
    public BusinessException(Throwable e, String message) {
        super(message, e);
        this.message = message;
    }
    
    @Override
    public String getMessage() {
        return message;
    }
}
