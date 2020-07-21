package com.timain.common.annotation;

import java.lang.annotation.*;

/**
 * 数据权限过滤注解
 * @Author yyf
 * @Version 1.0
 * @Date 2020/7/21 0021 19:59
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope {

    /**
     * 部门表的别名
     * @return
     */
    String deptAlias() default "";

    /**
     * 用户表的别名
     * @return
     */
    String userAlias() default "";
}
