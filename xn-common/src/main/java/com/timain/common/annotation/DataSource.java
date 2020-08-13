package com.timain.common.annotation;

import com.timain.common.enums.DataSourceType;

import java.lang.annotation.*;

/**
 * 自定义多数据源切换注解
 * @author yyf
 * @version 1.0
 * @date 2020/8/13 9:31
 */
@Target({ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DataSource {

    /**
     * 切换数据源名称
     * @return
     */
    DataSourceType value() default DataSourceType.MASTER;
}
