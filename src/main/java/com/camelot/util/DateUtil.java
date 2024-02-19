package com.camelot.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Lord Camelot
 * @date 2024/2/9
 * @description
 */
public class DateUtil {

    public static final String YYYY_MM_DD_1="yyyy-MM-dd";
    public static final String YYYY_MM_DD_2="yyyy/MM/dd";
    public static final String YYYY_MM_DD_3="yyyyMMdd";
    public static final String YYYY_MM_DD_HH_MM_SS="yyyy-MM-dd HH:mm:ss";
    public static String format(Date date, String patten) {
        return new SimpleDateFormat(patten).format(date);
    }

    public static Date parse(String date, String patten) {
        try {
            return new SimpleDateFormat(patten).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
