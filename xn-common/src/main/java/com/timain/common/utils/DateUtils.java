package com.timain.common.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 * @author yyf
 * @version 1.0
 * @date 2020/7/28 14:19
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy_MM_dd HH:mm:ss";

    public static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"
    };

    /**
     * 获取当前Date型日期
     * @return 当前日期
     */
    public static Date getNowDate() {
        return new Date();
    }

    public static String getDate() {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow() {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format) {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date) {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr(final String format, final Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String str) {
        try {
            return new SimpleDateFormat(format).parse(str);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static final String datePath() {
        Date date = new Date();
        return DateFormatUtils.format(date, "yyyy/MM/dd");
    }

    public static final String dateTime() {
        Date date = new Date();
        return DateFormatUtils.format(date, "yyyyMMdd");
    }

    /**
     * 日期型字符串转化为日期格式
     * @param str
     * @return
     */
    public static Date parseDate(Object str) {
        if (null == str) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取服务器启动时间
     * @return
     */
    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算两个时间差
     * @param endDate
     * @param nowDate
     * @return
     */
    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        //获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        //计算差多少天
        long day = diff / nd;
        //计算差多少小时
        long hour = diff % nd / nh;
        //计算差多少分钟
        long min = diff % nd % nh / nm;
        return day + "天" + hour + "小时" + min + "分钟";
    }
}
