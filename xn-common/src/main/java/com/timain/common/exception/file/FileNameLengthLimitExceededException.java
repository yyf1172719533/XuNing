package com.timain.common.exception.file;

/**
 * 文件名称超长限制异常类
 * @author yyf
 * @version 1.0
 * @date 2020/8/14 10:55
 */
public class FileNameLengthLimitExceededException extends FileException {

    private static final long serialVersionUID = 1L;

    public FileNameLengthLimitExceededException(int defaultFileNameLength) {
        super("upload.filename.exceed.length", new Object[] { defaultFileNameLength });
    }
}
