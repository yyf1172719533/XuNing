package com.timain.common.utils.sql;

import com.timain.common.exception.base.BaseException;
import com.timain.common.utils.StringUtils;

/**
 * sql操作工具类
 * @author yyf
 * @version 1.0
 * @date 2020/7/28 11:11
 */
public class SqlUtil {

    /**
     * 仅支持字母、数字、下划线、空格、逗号、小数点（支持多个字段排序）
     */
    public static String SQL_PATTERN = "[a-zA-Z0-9_\\ \\,\\.]+";

    /**
     * 检查字符，防止注入绕过
     * @param value
     * @return
     */
    public static String escapeOrderBySql(String value) {
        if (StringUtils.isNotEmpty(value) && !isValidOrderBySql(value)) {
            throw new BaseException("参数不符合规范，不能进行查询");
        }
        return value;
    }

    /**
     * 验证 order by 语法是否符合规范
     * @param value
     * @return
     */
    public static boolean isValidOrderBySql(String value) {
        return value.matches(SQL_PATTERN);
    }
}
