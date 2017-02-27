package com.shuicai.loghelper.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhuruqiao on 2017/2/22.
 */

public class DateUtils {
    public static String getDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
