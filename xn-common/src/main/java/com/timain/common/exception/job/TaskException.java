package com.timain.common.exception.job;

/**
 * 计划策略异常
 * @author yyf
 * @version 1.0
 * @date 2020/8/14 10:47
 */
public class TaskException extends Exception {

    private static final long serialVersionUID = 1L;
    
    private Code code;
    
    public TaskException(String msg, Code code) {
        this(msg, code, null);
    }
    
    public TaskException(String msg, Code code, Exception e) {
        super(msg, e);
        this.code = code;
    }
    
    public Code getCode() {
        return code;
    }
    
    public enum Code{
        TASK_EXISTS, 
        NO_TASK_EXISTS, 
        TASK_ALREADY_STARTED,
        UNKNOWN, 
        CONFIG_ERROR, 
        TASK_NODE_NOT_AVAILABLE
    }
}
