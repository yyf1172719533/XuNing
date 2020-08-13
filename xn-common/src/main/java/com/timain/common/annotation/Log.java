package com.timain.common.annotation;

import com.timain.common.enums.BusinessType;
import com.timain.common.enums.OperatorType;

import java.lang.annotation.*;

/**
 * 自定义操作日志记录注解
 * @author yyf
 * @version 1.0
 * @date 2020/8/13 9:34
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 模块
     * @return
     */
    String title() default "";

    /**
     * 功能
     * @return
     */
    BusinessType businessType() default BusinessType.OTHER;

    /**
     * 操作人类别
     * @return
     */
    OperatorType operatorType() default OperatorType.MANAGE;

    /**
     * 是否保存请求的参数
     * @return
     */
    boolean isSaveRequestData() default true;
}
