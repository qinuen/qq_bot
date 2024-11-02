package com.record.record_history.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    // 定义日期时间格式
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    // 创建 SimpleDateFormat 实例
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT);
    private static final SimpleDateFormat dateTimeFormatter = new SimpleDateFormat(DATE_TIME_FORMAT);

    // 生成当前日期的字符串格式（年月日）
    public static String getCurrentDateString() {
        return dateFormatter.format(new Date());
    }

    // 生成当前日期时间的字符串格式（年月日时分秒）
    public static String getCurrentDateTimeString() {
        return dateTimeFormatter.format(new Date());
    }

    // 生成指定日期的字符串格式（年月日）
    public static String formatDate(Date date) {
        return dateFormatter.format(date);
    }

    // 生成指定日期时间的字符串格式（年月日时分秒）
    public static String formatDateTime(Date date) {
        return dateTimeFormatter.format(date);
    }

    // 解析字符串（年月日）为 Date 对象
    public static Date parseDate(String dateStr) throws ParseException {
        return dateFormatter.parse(dateStr);
    }

    // 解析字符串（年月日时分秒）为 Date 对象
    public static Date parseDateTime(String dateTimeStr) throws ParseException {
        return dateTimeFormatter.parse(dateTimeStr);
    }

    // 将 Date 对象格式化为字符串（年月日）
    public static String dateToString(Date date) {
        return formatDate(date);
    }

    // 将 Date 对象格式化为字符串（年月日时分秒）
    public static String dateTimeToString(Date date) {
        return formatDateTime(date);
    }

    // 将字符串（年月日）转换为 Date 对象
    public static Date stringToDate(String dateStr) throws ParseException {
        return parseDate(dateStr);
    }

    // 将字符串（年月日时分秒）转换为 Date 对象
    public static Date stringToDateTime(String dateTimeStr) throws ParseException {
        return parseDateTime(dateTimeStr);
    }
}
