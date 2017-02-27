package com.shuicai.loghelper.log;

/**
 * Created by zhuruqiao on 2017/2/22.
 */

public enum LogType {
    TYPE_V("*:v"),
    TYPE_D("*:d"),
    TYPE_I("*:i"),
    TYPE_W("*:w"),
    TYPE_E("*:e");

    public final String name;

    private LogType(String name){
        this.name=name;
    }


}
