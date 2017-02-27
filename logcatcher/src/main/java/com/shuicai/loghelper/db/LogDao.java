package com.shuicai.loghelper.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by zhuruqiao on 2017/2/27.
 * e-mail:563325724@qq.com
 */

public class LogDao {


    public static SQLiteDatabase getWritableDB(Context ctx) {
        DBContext dbContext = new DBContext(ctx);
        SDCardDBHelper helper = SDCardDBHelper.getInstance(dbContext);
        SQLiteDatabase mWritableDB = helper.getWritableDatabase();
        return mWritableDB;

    }

    /**
     * 插入普通日志
     *
     * @param content
     */
    public void insertNormalLog(String content) {

    }

    /**
     * 删除普通日志
     *
     * @param content
     */
    public void deleteNormalLog(String content) {

    }

    /**
     * 查询普通日志
     *
     * @param content
     */
    public void queryNormalLog(String content) {

    }

    /**
     * 插入崩溃日志
     *
     * @param content
     */
    public void insertCrashLog(String content) {

    }

    /**
     * 删除崩溃日志
     *
     * @param content
     */
    public void deleteCrashLog(String content) {

    }

    /**
     * 读取崩溃日志
     *
     * @param content
     */
    public void queryCrashLog(String content) {

    }

}
