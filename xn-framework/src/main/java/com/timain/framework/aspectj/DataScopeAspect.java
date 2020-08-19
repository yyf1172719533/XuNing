package com.timain.framework.aspectj;

import com.timain.common.annotation.DataScope;
import com.timain.common.core.domain.BaseEntity;
import com.timain.common.utils.StringUtils;
import com.timain.framework.utils.ShiroUtils;
import com.timain.system.domain.SysRole;
import com.timain.system.domain.SysUser;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 数据过滤处理
 * @Author: yyf1172719533
 * @Date: 2020/8/18 13:53
 * @Version: 1.0
 */
@Aspect
@Component
public class DataScopeAspect {

    /**
     * 全部数据权限
     */
    public static final String DATA_SCOPE_ALL = "1";

    /**
     * 自定数据权限
     */
    public static final String DATA_SCOPE_CUSTOM = "2";

    /**
     * 部门数据权限
     */
    public static final String DATA_SCOPE_DEPT = "3";

    /**
     * 部门及以下数据权限
     */
    public static final String DATA_SCOPE_DEPT_AND_CHILD = "4";

    /**
     * 仅本人数据权限
     */
    public static final String DATA_SCOPE_SELF = "5";

    /**
     * 数据权限过滤关键字
     */
    public static final String DATA_SCOPE = "dataScope";

    //配置织入点
    @Pointcut("@annotation(com.timain.common.annotation.DataScope)")
    public void dataScopePointCut() {

    }

    @Before("dataScopePointCut()")
    public void doBefore(JoinPoint point) {
        handleDataScope(point);
    }

    protected void handleDataScope(final JoinPoint point) {
        //获得注解
        DataScope controllerDataScope = getAnnotationLog(point);
        if (null == controllerDataScope) {
            return;
        }
        //获取当前用户
        SysUser currentUser = ShiroUtils.getSysUser();
        if (null != currentUser) {
            //如果不是超级管理员，则不过滤数据
            if (!currentUser.isAdmin()) {
                dataScopeFilter(point, currentUser, controllerDataScope.deptAlias(), controllerDataScope.userAlias());
            }
        }
    }

    /**
     * 数据范围过滤
     * @param point 切点
     * @param user 用户
     * @param deptAlias 部门别名
     * @param userAlias 用户别名
     */
    public static void dataScopeFilter(JoinPoint point, SysUser user, String deptAlias, String userAlias) {
        StringBuilder builder = new StringBuilder();
        for (SysRole role : user.getRoles()) {
            String dataScope = role.getDataScope();
            if (DATA_SCOPE_ALL.equals(dataScope)) {
                builder = new StringBuilder();
                break;
            } else if (DATA_SCOPE_CUSTOM.equals(dataScope)) {
                builder.append(StringUtils.format(" OR {}.dept_id IN ( SELECT dept_id FROM sys_role_dept WHERE role_id = {} ) ", deptAlias, role.getRoleId()));
            } else if (DATA_SCOPE_DEPT.equals(dataScope)) {
                builder.append(StringUtils.format(" OR {}.dept_id = {} ", deptAlias, user.getDeptId()));
            } else if (DATA_SCOPE_DEPT_AND_CHILD.equals(dataScope)) {
                builder.append(StringUtils.format(
                        " OR {}.dept_id IN ( SELECT dept_id FROM sys_dept WHERE dept_id = {} or find_in_set( {} , ancestors ) )",
                        deptAlias, user.getDeptId(), user.getDeptId()));
            } else if (DATA_SCOPE_SELF.equals(dataScope)) {
                if (StringUtils.isNotBlank(userAlias)) {
                    builder.append(StringUtils.format(" OR {}.user_id = {} ", userAlias, user.getUserId()));
                } else {
                    //数据权限为仅本人且没有userAlias别名不查询任何数据
                    builder.append(" OR 1=0 ");
                }
            }
        }
        if (StringUtils.isNotBlank(builder.toString())) {
            BaseEntity baseEntity = (BaseEntity) point.getArgs()[0];
            baseEntity.getParams().put(DATA_SCOPE, " AND (" + builder.substring(4) + ")");
        }
    }

    /**
     * 是否存在注解，如果存在就获取
     * @param point
     * @return
     */
    private DataScope getAnnotationLog(JoinPoint point) {
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (null != method) {
            return method.getAnnotation(DataScope.class);
        }
        return null;
    }
}
