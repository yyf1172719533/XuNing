package com.timain.common.exception.file;

/**
 * 文件名大小限制异常类
 * @author yyf
 * @version 1.0
 * @date 2020/8/14 10:57
 */
public class FileSizeLimitExceededException extends FileException {

    private static final long serialVersionUID = 1L;

    public FileSizeLimitExceededException(long defaultMaxSize) {
        super("upload.exceed.maxSize", new Object[] { defaultMaxSize });
    }
}
