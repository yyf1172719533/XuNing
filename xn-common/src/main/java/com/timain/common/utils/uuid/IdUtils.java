package com.timain.common.utils.uuid;

/**
 * ID生成器工具类
 * @author yyf
 * @version 1.0
 * @date 2020/7/28 9:52
 */
public class IdUtils {

    /**
     * 获取随机UUID
     * @return
     */
    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 获取简化的UUID，去掉"-"
     * @return
     */
    public static String simpleUUID() {
        return UUID.randomUUID().toString(true);
    }

    /**
     * 获取随机UUID，使用性能更好的ThreadLocalRandom生成UUID.
     * @return
     */
    public static String fastUUID() {
        return UUID.fastUUID().toString();
    }

    /**
     * 简化的UUID，去掉了横线，使用性能更好的ThreadLocalRandom生成UUID
     * @return
     */
    public static String fastSimpleUUID() {
        return UUID.fastUUID().toString(true);
    }
}
