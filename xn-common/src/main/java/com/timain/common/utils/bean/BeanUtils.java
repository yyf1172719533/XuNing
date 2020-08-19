package com.timain.common.utils.bean;

import org.springframework.beans.BeansException;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Bean 工具类
 * @Author yyf
 * @Version 1.0
 * @Date 2020/7/23 0023 21:15
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {

    /** Bean方法名中属性名开始的下标 */
    private static final int BEAN_METHOD_PROP_INDEX = 3;

    /** * 匹配getter方法的正则表达式 */
    private static final Pattern GET_PATTERN = Pattern.compile("get(\\p{javaUpperCase}\\w*)");

    /** * 匹配setter方法的正则表达式 */
    private static final Pattern SET_PATTERN = Pattern.compile("set(\\p{javaUpperCase}\\w*)");

    /**
     * Bean属性复制工具方法
     * @param dest
     * @param src
     */
    public static void copyBeanProp(Object dest, Object src) {
        try {
            copyProperties(src, dest);
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取对象的setter方法.
     * @param obj
     * @return
     */
    public static List<Method> getSetterMethods(Object obj) {
        //setter方法列表
        List<Method> setterMethods = new ArrayList<>();
        //获取所有方法
        Method[] methods = obj.getClass().getMethods();
        //查找setter方法
        for (Method method : methods) {
            Matcher m = SET_PATTERN.matcher(method.getName());
            if (m.matches() && (method.getParameterTypes().length == 1)) {
                setterMethods.add(method);
            }
        }
        return setterMethods;
    }

    /**
     * 获取对象的getter方法.
     * @param obj
     * @return
     */
    public static List<Method> getGetterMethods(Object obj) {
        //getter方法列表
        List<Method> getterMethods = new ArrayList<Method>();
        //获取所有方法
        Method[] methods = obj.getClass().getMethods();
        //查找getter方法
        for (Method method : methods) {
            Matcher m = GET_PATTERN.matcher(method.getName());
            if (m.matches() && (method.getParameterTypes().length == 0)) {
                getterMethods.add(method);
            }
        }
        return getterMethods;
    }

    /**
     * 检查Bean方法名中的属性名是否相等。<br>
     * 如getName()和setName()属性名一样，getName()和setAge()属性名不一样。
     * @param m1 方法名1
     * @param m2 方法名2
     * @return
     */
    public static boolean isMethodPropEquals(String m1, String m2) {
        return m1.substring(BEAN_METHOD_PROP_INDEX).equals(m2.substring(BEAN_METHOD_PROP_INDEX));
    }
}
