package com.timain.common.core.text;

import com.timain.common.utils.StringUtils;

/**
 * 字符串格式化
 * @author yyf
 * @version 1.0
 * @date 2020/8/6 14:46
 */
public class StrFormatter {
    
    public static final String EMPTY_JSON = "{}";
    public static final char C_BACKSLASH = '\\';
    public static final char C_DELIM_START = '{';
    public static final char C_DELIM_END = '}';

    /**
     * 格式化字符串
     * @param strPattern 字符串模板
     * @param argArray 参数列表
     * @return
     */
    public static String format(final String strPattern,final Object... argArray) {
        if (StringUtils.isEmpty(strPattern) || StringUtils.isEmpty(argArray)) {
            return strPattern;
        }
        final int strPatternLength = strPattern.length();
        
        //初始化定义好的长度以获得更好的性能
        StringBuilder sb = new StringBuilder(strPatternLength + 50);
        int handlePosition = 0;
        int delimIndex;   //占位符所在位置
        for (int argIndex = 0; argIndex < argArray.length; argIndex++) {
            delimIndex = strPattern.indexOf(EMPTY_JSON, handlePosition);
            if (delimIndex == -1) {
                if (handlePosition == 0) {
                    return strPattern;
                } else {
                    sb.append(strPattern, handlePosition, strPatternLength);
                    return sb.toString();
                }
            } else {
                if (delimIndex > 0 && strPattern.charAt(delimIndex - 1) == C_BACKSLASH) {
                    if (delimIndex > 1 && strPattern.charAt(delimIndex -2) == C_BACKSLASH) {
                        //转义符之前还有一个转义符，占位符依旧有效
                        sb.append(strPattern, handlePosition, delimIndex - 1);
                        sb.append(Convert.utf8Str(argArray[argIndex]));
                        handlePosition = delimIndex +2;
                    } else {
                        //占位符被转义
                        argIndex--;
                        sb.append(strPattern, handlePosition, delimIndex - 1);
                        sb.append(C_DELIM_START);
                        handlePosition = delimIndex + 1;
                    }
                } else {
                    //正常占位符
                    sb.append(strPattern, handlePosition, delimIndex);
                    sb.append(Convert.utf8Str(argArray[argIndex]));
                    handlePosition = delimIndex +2;
                }
            }
        }
        //加入最后一个占位符后所有的字符
        sb.append(strPattern, handlePosition, strPattern.length());
        return sb.toString();
    }
}
