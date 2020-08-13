package com.timain.common.annotation;

import java.lang.annotation.*;

/**
 * 自定义注解防止表单重复提交
 * @author yyf
 * @version 1.0
 * @date 2020/8/13 9:39
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RepeatSubmit {
}
