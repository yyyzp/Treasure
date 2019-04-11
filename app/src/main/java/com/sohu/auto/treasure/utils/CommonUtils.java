package com.sohu.auto.treasure.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by aiyou on 2019/4/10.
 */

public class CommonUtils {
    public final static String SDF_YYYY_MM_DD_HH_MM = "yyyy.MM.dd HH:mm";
    public final static String SDF_YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * 将时间戳转换成 f 格式
     *
     * @param time
     * @param f
     * @return
     */
    public static String formatDate(long time, String f) {
        SimpleDateFormat format = new SimpleDateFormat(f);
        Date date = new Date(time);
        return format.format(date);
    }
}
