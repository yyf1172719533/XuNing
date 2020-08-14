package com.timain.common.exception.file;

import com.timain.common.exception.base.BaseException;

/**
 * 文件信息异常类
 * @author yyf
 * @version 1.0
 * @date 2020/8/14 10:52
 */
public class FileException extends BaseException {

    private static final long serialVersionUID = 1L;

    public FileException(String code, Object[] args) {
        super("file", code, args, null);
    }
}
