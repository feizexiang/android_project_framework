package com.fzx.framework.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 时间格式
 *
 * @author fzx
 * @date 2015年6月17日 下午2:04:36
 */
public class DateTimeUtil {
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String HH_MM_SS = "HH:mm:ss";
    public static final String TIME_FORMAT_C = "yyyyMMddHHmmssSSS";
    public static final String TIME_FORMAT_B = "yyyyMMddHHmmss";// yyyyMMddHHmmss
    public static final String TIME_FORMAT_A = "yyyy-MM-dd HH:mm:ss:SSS";
    public static final String TIME_FORMAT_D = "yyyy-MM-dd HH:mm:ss";
    // 2017-01-28T10:11:16
    public static final String TIME_FORMAT_F = "yyyy-MM-dd'T'HH:mm:ss";

    /**
     * 按照格式C生成当前时间字符串
     *
     * @return
     */
    public synchronized static String getDateTimeNowC() {
        return getDateTime(new Date(), TIME_FORMAT_C);
    }

    /**
     * 按照具体格式获取当前时间字符串
     *
     * @param format
     * @return
     */
    public synchronized static String getDateTimeNow(String format) {
        return getDateTime(new Date(), format);
    }

    /**
     * 按照时间格式化字符串
     *
     * @param time
     * @param oldFormat
     * @return
     */
    public synchronized static String formatDataTimeString(String time, String oldFormat, String newFormat) {
        try {
            SimpleDateFormat oldDF = new SimpleDateFormat(oldFormat);
            SimpleDateFormat newDF = new SimpleDateFormat(newFormat);
            return newDF.format(oldDF.parse(time));
        } catch (Exception ex) {
            return "";
        }
    }

    /**
     * 字符串按照具体格式转换为时间
     *
     * @param date
     * @param format
     * @return
     */
    public synchronized static Date getDateTime(String date, String format) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(format);
            return df.parse(date);
        } catch (Exception ex) {
            return null;
        }
    }

    public synchronized static String getDateTimeNowD() {
        return getDateTime(new Date(), TIME_FORMAT_D);
    }

    /**
     * 时间按照具体格式转换为字符串
     *
     * @param date
     * @param format
     * @return
     */
    public synchronized static String getDateTime(Date date, String format) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(format);
            return df.format(date);
        } catch (Exception ex) {
            return "";
        }
    }

    /**
     * 网络接口传输时格式化
     *
     * @param date
     * @return
     */
    public synchronized static String getDateTimeNet(String date) {
        try {
            return formatDataTimeString(date, TIME_FORMAT_F, TIME_FORMAT_D);
        } catch (Exception ex) {
            return "";
        }
    }

    /**
     * 网络接口时间格式化
     *
     * @param date
     * @param format
     * @return
     */
    public synchronized static String getDateTimeNetFormat(String date, String format) {
        try {
            return formatDataTimeString(date, TIME_FORMAT_F, format);
        } catch (Exception ex) {
            return "";
        }
    }

    /**
     * 将毫秒转成日期
     *
     * @param time
     * @param format 为空时  默认yyyy-MM-dd HH:mm:ss（HH24小时制  hh 12小时制）
     * @return
     */
    public static String getDateStr(long time, String format) {
        if (time == 0) {
            return "";
        }
        Date date = new Date(time);
        DateFormat formatter;
        if (format == null || format.equals("")) {
            formatter = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);//HH24小时制  hh 12小时制
        } else {
            formatter = new SimpleDateFormat(format);//HH24小时制  hh 12小时制
        }

        return formatter.format(date);
    }

    /**
     * 获取当天的开始和结束时间
     *
     * @param date 当前时间毫秒数
     * @return
     */
    public static long[] getDayStartAndEnd(long date) {
        long[] strToday = new long[2];
//        long current=System.currentTimeMillis();
        long zero = date / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数
        long twelve = zero + 24 * 60 * 60 * 1000 - 1;//今天23点59分59秒的毫秒数
        strToday[0] = zero;
        strToday[1] = twelve;
        return strToday;
    }

}
