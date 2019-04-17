package com.haha.cmis.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by hah on 2018/6/21.
 */

public class DateTimeHelper {
    /**
     * 日期格式：yyyy-MM-dd HH:mm:ss
     **/
    public static final String DF_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    /**
     * 日期格式：yyyy-MM-dd HH:mm
     **/
    public static final String DF_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    /**
     * 日期格式：yyyy-MM-dd
     **/
    public static final String DF_YYYY_MM_DD = "yyyy-MM-dd";
    /**
     * 日期格式：HH:mm:ss
     **/
    public static final String DF_HH_MM_SS = "HH:mm:ss";
    /**
     * 日期格式：HH:mm
     **/
    public static final String DF_HH_MM = "HH:mm";
    private final static long minute = 60 * 1000;// 1分钟
    private final static long hour = 60 * minute;// 1小时
    private final static long day = 24 * hour;// 1天
    private final static long month = 31 * day;// 月
    private final static long year = 12 * month;// 年

    //获取当前时间
    public static String getcurrentDate() {
        Calendar calendar = Calendar.getInstance();
        long curdate = System.currentTimeMillis();
        Date nowDate = new Date(curdate);
        SimpleDateFormat sdf = new SimpleDateFormat(DF_YYYY_MM_DD_HH_MM_SS, Locale.CHINA);
        return sdf.format(nowDate);
    }

}
