package com.timain.common.core.page;

import com.timain.common.constant.Constants;
import com.timain.common.utils.ServletUtils;

/**
 * 表格数据处理
 * @author yyf
 * @version 1.0
 * @date 2020/7/30 14:15
 */
public class TableSupport {

    /**
     * 封装分页对象
     * @return
     */
    public static PageDomain getPageDomain() {
        PageDomain pageDomain = new PageDomain();
        pageDomain.setPageNum(ServletUtils.getParameterToInt(Constants.PAGE_NUM));
        pageDomain.setPageSize(ServletUtils.getParameterToInt(Constants.PAGE_SIZE));
        pageDomain.setOrderByColumn(ServletUtils.getParameter(Constants.ORDER_BY_COLUMN));
        pageDomain.setIsAsc(ServletUtils.getParameter(Constants.IS_ASC));
        return pageDomain;
    }
    
    public static PageDomain buildPageRequest() {
        return getPageDomain();
    }
}
