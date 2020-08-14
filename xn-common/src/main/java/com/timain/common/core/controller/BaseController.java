package com.timain.common.core.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.timain.common.core.domain.AjaxResult;
import com.timain.common.core.page.PageDomain;
import com.timain.common.core.page.TableDataInfo;
import com.timain.common.core.page.TableSupport;
import com.timain.common.utils.DateUtils;
import com.timain.common.utils.ServletUtils;
import com.timain.common.utils.StringUtils;
import com.timain.common.utils.sql.SqlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;


/**
 * web层通用数据处理
 * @author yyf
 * @version 1.0
 * @date 2020/7/30 14:08
 */
public class BaseController {

    protected final Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型.
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(DateUtils.parseDate(text));
            }
        });
    }

    /**
     * 设置请求分页数据
     */
    protected void startPage() {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize)) {
            String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
            PageHelper.startPage(pageNum, pageSize, orderBy);
        }
    }

    /**
     * 设置请求排序数据
     */
    protected void startOrderBy() {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        if (StringUtils.isNotEmpty(pageDomain.getOrderBy())) {
            String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
            PageHelper.orderBy(orderBy);
        }
    }

    /**
     * 获取request
     * @return
     */
    public HttpServletRequest getRequest() {
        return ServletUtils.getRequest();
    }

    /**
     * 获取response
     * @return
     */
    public HttpServletResponse getResponse() {
        return ServletUtils.getResponse();
    }

    /**
     * 获取session
     * @return
     */
    public HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * 响应请求分页数据
     * @param list
     * @return
     */
    protected TableDataInfo getDataInfo(List<?> list) {
        TableDataInfo dataInfo = new TableDataInfo();
        dataInfo.setCode(0);
        dataInfo.setRows(list);
        dataInfo.setTotal(new PageInfo<>(list).getTotal());
        return dataInfo;
    }

    /**
     * 响应返回结果
     * @param rows
     * @return
     */
    protected AjaxResult toAjax(int rows) {
        return rows > 0 ? success() : error();
    }

    /**
     * 响应返回结果
     * @param result
     * @return
     */
    protected AjaxResult toAjax(boolean result) {
        return result ? success() : error();
    }

    /**
     * 返回成功
     * @return
     */
    public AjaxResult success() {
        return AjaxResult.success();
    }

    /**
     * 返回失败
     * @return
     */
    public AjaxResult error() {
        return AjaxResult.error();
    }

    /**
     * 返回成功信息
     * @param message
     * @return
     */
    public AjaxResult success(String message) {
        return AjaxResult.success(message);
    }

    /**
     * 返回失败信息
     * @param message
     * @return
     */
    public AjaxResult error(String message) {
        return AjaxResult.error(message);
    }

    /**
     * 返回错误码信息
     * @param type
     * @param message
     * @return
     */
    public AjaxResult error(AjaxResult.Type type, String message) {
        return new AjaxResult(type, message);
    }

    /**
     * 页面跳转
     * @param url
     * @return
     */
    public String redirect(String url) {
        return StringUtils.format("redirect:{}", url);
    }
}
