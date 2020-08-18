package com.timain.framework.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 程序注解配置
 * @author yyf1172719533
 */
@Configuration
@EnableAspectJAutoProxy(exposeProxy = true)
@MapperScan("com.timain.**.mapper")
public class ApplicationConfig {
}
