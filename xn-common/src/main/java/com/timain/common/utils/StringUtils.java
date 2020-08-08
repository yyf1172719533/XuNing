package com.timain.common.utils;

import com.timain.common.core.text.StrFormatter;

import java.util.Collection;
import java.util.Map;


/**
 * 字符串工具类
 * @Author yyf
 * @Version 1.0
 * @Date 2020/7/19 0019 18:21
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    /** 空字符串 */
    private static final String NULLSTR = "";

    /** 下划线 */
    private static final char SEPARATOR  = '_';

    /**
     * 获取参数不为空值
     * @param value
     * @param defaultValue
     * @param <T>
     * @return
     */
    public static <T> T nvl(T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }

    /**
     * 判断一个Collection是否为空， 包含List，Set，Queue
     * @param coll
     * @return
     */
    public static boolean isEmpty(Collection<?> coll) {
        return isNull(coll) || coll.isEmpty();
    }

    /**
     * 判断一个Collection是否非空，包含List，Set，Queue
     * @param coll
     * @return
     */
    public static boolean isNotEmpty(Collection<?> coll) {
        return !isEmpty(coll);
    }

    /**
     * 判断一个对象数组是否为空
     * @param objects
     * @return
     */
    public static boolean isEmpty(Object[] objects) {
        return isNull(objects) || (objects.length == 0);
    }

    /**
     * 判断一个对象数组是否非空
     * @param objects
     * @return
     */
    public static boolean isNotEmpty(Object[] objects) {
        return !isEmpty(objects);
    }

    /**
     * 判断一个Map是否为空
     * @param map
     * @return
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return isNull(map) || map.isEmpty();
    }

    /**
     * 判断一个Map是否非空
     * @param map
     * @return
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * 判断一个字符串是否为空串
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return isNull(str) || NULLSTR.equals(str.trim());
    }

    /**
     * 判断一个字符串是否为非空串
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 判断一个对象是否为空
     * @param object
     * @return
     */
    public static boolean isNull(Object object) {
        return object == null;
    }

    /**
     * 判断一个对象是否非空
     * @param object
     * @return
     */
    public static boolean isNotNull(Object object) {
        return !isNull(object);
    }

    /**
     * 判断一个对象是否是数组类型（Java基本型别的数组）
     * @param object
     * @return
     */
    public static boolean isArray(Object object) {
        return isNotNull(object) && object.getClass().isArray();
    }

    /**
     * 去空格
     * @param str
     * @return
     */
    public static String trim(String str) {
        return null == str ? "" : str.trim();
    }

    /**
     * 截取字符串
     * @param str
     * @param start
     * @return
     */
    public static String substring(final String str, int start) {
        if (null == str) {
            return NULLSTR;
        }
        if (start < 0) {
            start = 0;
        }
        if (start > str.length()) {
            return NULLSTR;
        }
        if (start < 0) {
            start = str.length() + start;
        }
        return str.substring(start);
    }

    /**
     * 截取字符串
     * @param str
     * @param start
     * @param end
     * @return
     */
    public static String substring(final String str, int start, int end) {
        if (start < 0) {
            start = str.length() + start;
        }
        if (end < 0) {
            end = str.length() + end;
        }
        if (end > str.length()) {
            end = str.length();
        }
        if (start > end) {
            return NULLSTR;
        }
        if (start < 0) {
            start = 0;
        }
        if (end < 0) {
            end = 0;
        }
        return str.substring(start, end);
    }

    /**
     * 格式化文本
     * @param template 文本模板，被替换的部分用 {} 表示
     * @param params 参数值
     * @return
     */
    public static String format(String template, Object... params) {
        if (isEmpty(params) || isEmpty(template)) {
            return template;
        }
        return StrFormatter.format(template, params);
    }

    /**
     * 下划线转驼峰命名
     * @param str
     * @return
     */
    public static String toUnderScoreCase(String str) {
        if (null == str) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        // 前置字符是否大写
        boolean preCharIsUpperCase = true;
        // 当前字符是否大写
        boolean currCharIsUpperCase = true;
        // 下一字符是否大写
        boolean nextCharIsUpperCase = true;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (i > 0) {
                preCharIsUpperCase = Character.isUpperCase(str.charAt(i - 1));
            } else {
                preCharIsUpperCase = false;
            }
            currCharIsUpperCase = Character.isUpperCase(c);
            if (i < (str.length() - 1)) {
                nextCharIsUpperCase = Character.isUpperCase(str.charAt(i + 1));
            }
            if (preCharIsUpperCase && currCharIsUpperCase && !nextCharIsUpperCase) {
                sb.append(SEPARATOR);
            } else if (i != 0 && !preCharIsUpperCase && currCharIsUpperCase) {
                sb.append(SEPARATOR);
            }
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }

    /**
     * 判断是否包含字符串
     * @param str 验证字符串
     * @param args 字符串组
     * @return
     */
    public static boolean inStringIgnoreCase(String str, String... args) {
        if (null != str && null != args) {
            for (String s : args) {
                if (str.equalsIgnoreCase(trim(s))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。 例如：HELLO_WORLD->HelloWorld.
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String convertToCamelCase(String name) {
        StringBuilder builder = new StringBuilder();
        if (null == name || name.isEmpty()) {
            return "";
        } else if (!name.contains("_")) {
            // 不含下划线，仅将首字母大写
            return name.substring(0, 1).toUpperCase() + name.substring(1);
        }
        // 用下划线将原始字符串分割
        String[] camels = name.split("_");
        for (String camel : camels) {
            if (camel.isEmpty()) {
                continue;
            }
            // 首字母大写
            builder.append(camel.substring(0, 1).toUpperCase());
            builder.append(camel.substring(1).toLowerCase());
        }
        return builder.toString();
    }

    /**
     * 驼峰式命名法
     * @param s
     * @return
     */
    public static String toCamelCase(String s) {
        if (null == s) {
            return null;
        }
        if (s.indexOf(SEPARATOR) == -1) {
            return s;
        }
        s = s.toLowerCase();
        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    @SuppressWarnings("unchecked")
    public static <T> T cast(Object object) {
        return (T) object;
    }
}
