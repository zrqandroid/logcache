package com.shuicai.loghelper.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库管理和维护类
 **/
public class SDCardDBHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "log.db";


    public static int DATABASE_VERSION = 1;

    private static SDCardDBHelper instance;

    public static synchronized SDCardDBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new SDCardDBHelper(context);
        }
        return instance;
    }


    private SDCardDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建一般日志表
        db.execSQL(DBSQLManager.LogTable.getCreateSQL());
        //创建崩溃日志表
        db.execSQL(DBSQLManager.CrashTable.getCreateSQL());
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("ALTER TABLE person ADD COLUMN other STRING");  
    }
}