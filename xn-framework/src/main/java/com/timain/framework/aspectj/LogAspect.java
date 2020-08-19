package com.timain.framework.aspectj;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.PropertyPreFilters;
import com.timain.common.annotation.Log;
import com.timain.common.enums.BusinessStatus;
import com.timain.common.json.Json;
import com.timain.common.utils.ServletUtils;
import com.timain.common.utils.StringUtils;
import com.timain.framework.manager.AsyncManager;
import com.timain.framework.manager.factory.AsyncFactory;
import com.timain.framework.utils.ShiroUtils;
import com.timain.system.domain.SysOperLog;
import com.timain.system.domain.SysUser;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 操作日志记录处理
 * @Author: yyf1172719533
 * @Date: 2020/8/18 14:32
 * @Version: 1.0
 */
@Aspect
@Component
public class LogAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    /** 排除敏感属性字段 */
    public static final String[] EXCLUDE_PROPERTIES = { "password", "oldPassword", "newPassword", "confirmPassword" };

    /**
     * 配置织入点
     */
    @Pointcut("@annotation(com.timain.common.annotation.Log)")
    public void logPointCut() {

    }

    /**
     * 处理完请求后执行
     * @param point 切点
     * @param jsonResult
     */
    @AfterReturning(pointcut = "logPointCut()", returning = "jsonResult")
    public void doAfterReturning(JoinPoint point, Object jsonResult) {
        handleLog(point, null, jsonResult);
    }

    /**
     * 拦截异常操作
     * @param point 切点
     * @param e 异常
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint point, Exception e) {
        handleLog(point, e, null);
    }

    /**
     * 拦截异常操作
     * @param point
     * @param e
     * @param jsonResult
     */
    protected void handleLog(final JoinPoint point, final Exception e, Object jsonResult) {
        try {
            //获得注解
            Log controllerLog = getAnnotationLog(point);
            if (null == controllerLog) {
                return;
            }
            //获取当前用户
            SysUser currentUser = ShiroUtils.getSysUser();

            // *========数据库日志=========*//
            SysOperLog sysOperLog = new SysOperLog();
            sysOperLog.setStatus(BusinessStatus.SUCCESS.ordinal());
            //请求的地址
            String ip = ShiroUtils.getIp();
            sysOperLog.setOperIp(ip);
            //返回参数
            sysOperLog.setJsonResult(StringUtils.substring(Json.marshal(jsonResult), 0, 2000));

            sysOperLog.setOperUrl(ServletUtils.getRequest().getRequestURI());
            if (null != currentUser) {
                sysOperLog.setOperName(currentUser.getLoginName());
                if (StringUtils.isNotNull(currentUser.getDept()) && StringUtils.isNotEmpty(currentUser.getDept().getDeptName())) {
                    sysOperLog.setDeptName(currentUser.getDept().getDeptName());
                }
            }
            if (null != e) {
                sysOperLog.setStatus(BusinessStatus.FAIL.ordinal());
                sysOperLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            }
            //设置方法名称
            String className = point.getTarget().getClass().getName();
            String methodName = point.getSignature().getName();
            sysOperLog.setMethod(className + "." + methodName + "()");
            //设置请求方式
            sysOperLog.setRequestMethod(ServletUtils.getRequest().getMethod());
            //处理设置注解上的参数
            getControllerMethodDescription(controllerLog, sysOperLog);
            //保存数据库
            AsyncManager.me().execute(AsyncFactory.recordOper(sysOperLog));
        } catch (Exception exception) {
            logger.error("==前置通知异常==");
            logger.error("异常信息:{}", exception.getMessage());
            exception.printStackTrace();
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     * @param log 日志
     * @param operLog 操作日志
     */
    public void getControllerMethodDescription(Log log, SysOperLog operLog) {
        //设置action动作
        operLog.setBusinessType(log.businessType().ordinal());
        //设置标题
        operLog.setTitle(log.title());
        //设置操作人类别
        operLog.setOperatorType(log.operatorType().ordinal());
        //是否需要保存request，参数和值
        if (log.isSaveRequestData()) {
            //获取参数的信息，传入到数据库中.
            setRequestValue(operLog);
        }
    }

    /**
     * 获取请求的参数，放到log中
     * @param operLog 操作日志
     */
    private void setRequestValue(SysOperLog operLog) {
        Map<String, String[]> map = ServletUtils.getRequest().getParameterMap();
        if (StringUtils.isNotEmpty(map)) {
            PropertyPreFilters.MySimplePropertyPreFilter propertyPreFilter = new PropertyPreFilters().addFilter();
            propertyPreFilter.addExcludes(EXCLUDE_PROPERTIES);
            String params = JSONObject.toJSONString(map, propertyPreFilter);
            operLog.setOperParam(StringUtils.substring(params, 0, 2000));
        }
    }

    /**
     * 是否存在注解，如果存在就获取
     * @param point
     * @return
     */
    private Log getAnnotationLog(JoinPoint point) {
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (null != method) {
            return method.getAnnotation(Log.class);
        }
        return null;
    }
}
