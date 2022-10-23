package com.snail.officialaccount.util;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @version 1.0
 * @Author jiaSong.pi
 * @Date 2022/9/18 22:12
 */
@Slf4j
public class TimeUtil {
    private static final String dateTime = "yyyy-MM-dd HH:mm:ss";
    private static final String date = "yyy-MM-dd";
    private static final String time = "HH:mm:ss";

    /**
     * 获取当前时间
     * @return 时间格式
     */
    public static String getDateTimeNow() {
        return DateTimeFormatter.ofPattern(dateTime).format(LocalDate.now());
    }

    /**
     * 获取当前日期
     * @return 日期
     */
    public static String getDateNow() {
        return DateTimeFormatter.ofPattern(date).format(LocalDate.now());
    }

    /**
     * 获取当前时间
     * @return 时间
     */
    public static String getTimeNow() {
        return DateTimeFormatter.ofPattern(time).format(LocalDate.now());
    }

}
