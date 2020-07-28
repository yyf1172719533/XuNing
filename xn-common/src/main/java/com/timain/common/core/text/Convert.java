package com.timain.common.core.text;

import com.timain.common.utils.StringUtils;

/**
 * 类型转换器
 * @author yyf
 * @version 1.0
 * @date 2020/7/28 15:18
 */
public class Convert {

    /**
     * 转换为字符串
     * @param value 被转换的值
     * @param defaultValue 转换错误时的默认值
     * @return
     */
    public static String toStr(Object value, String defaultValue) {
        if (null == value) {
            return defaultValue;
        }
        if (value instanceof String) {
            return (String) value;
        }
        return value.toString();
    }

    /**
     * 转换为字符串
     * @param value 被转换的值
     * @return
     */
    public static String toStr(Object value) {
        return toStr(value, null);
    }

    /**
     * 转换为字符
     * @param value 被转换的值
     * @param defaultValue 转换错误时的默认值
     * @return
     */
    public static Character toChar(Object value, Character defaultValue) {
        if (null == value) {
            return defaultValue;
        }
        if (value instanceof Character) {
            return (Character) value;
        }
        final String valueStr = toStr(value, null);
        return StringUtils.isEmpty(valueStr) ? defaultValue : valueStr.charAt(0);
    }

    /**
     * 转换为字符
     * @param value 被转换的值
     * @return
     */
    public static Character toChar(Object value) {
        return toChar(value, null);
    }

    /**
     * 转换为byte
     * @param value 被转换的值
     * @param defaultValue 转换错误时的默认值
     * @return
     */
    public static Byte toByte(Object value, Byte defaultValue) {
        if (null == value) {
            return defaultValue;
        }
        if (value instanceof Byte) {
            return (Byte) value;
        }
        if (value instanceof Number) {
            return ((Number)value).byteValue();
        }
        final String valueStr = toStr(value, null);
        if (StringUtils.isEmpty(valueStr)) {
            return defaultValue;
        }
        try {
            return Byte.parseByte(valueStr);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * 转换为byte
     * @param value 被转换的值
     * @return
     */
    public static Byte toByte(Object value) {
        return toByte(value, null);
    }

    /**
     * 转换为int
     * @param value 被转换的值
     * @param defaultValue 转换错误时的默认值
     * @return
     */
    public static Integer toInt(Object value, Integer defaultValue) {
        if (null == value) {
            return defaultValue;
        }
        if (value instanceof Integer) {
            return (Integer) value;
        }
        if (value instanceof Number) {
            return ((Number)value).intValue();
        }
        final String valueStr = toStr(value, null);
        if (StringUtils.isEmpty(valueStr)) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(valueStr.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * 转换为int
     * @param value 被转换的值
     * @return
     */
    public static Integer toInt(Object value) {
        return toInt(value, null);
    }
}
