package com.shuicai.loghelper.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.shuicai.loghelper.db.bean.NormalLogInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuruqiao on 2017/2/27.
 * e-mail:563325724@qq.com
 */

public class LogDao {

    private static SQLiteDatabase mWritableDB;
    private static SQLiteDatabase mReadableDB;


    private static SQLiteDatabase getWritableDB(Context ctx) {
        if (mWritableDB == null || !mWritableDB.isOpen()) {
            DBContext dbContext = new DBContext(ctx);
            SDCardDBHelper helper = SDCardDBHelper.getInstance(dbContext);
            mWritableDB = helper.getWritableDatabase();
        }
        return mWritableDB;

    }

    private static SQLiteDatabase getReadableDB(Context ctx) {
        if (mReadableDB == null || !mReadableDB.isOpen()) {
            DBContext dbContext = new DBContext(ctx);
            SDCardDBHelper helper = SDCardDBHelper.getInstance(dbContext);
            mReadableDB = helper.getReadableDatabase();
        }
        return mReadableDB;

    }

    /**
     * 插入日志
     *
     * @param normalLogInfo 日志内容 对象
     * @return true if success
     */
    public static synchronized boolean insertNormalLog(NormalLogInfo normalLogInfo, Context context) {

        SQLiteDatabase writableDB = getWritableDB(context);

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBSQLManager.LogTable.Column.TIME.name, normalLogInfo.time);
        contentValues.put(DBSQLManager.LogTable.Column.TYPE.name, normalLogInfo.type);
        contentValues.put(DBSQLManager.LogTable.Column.CONTENT.name, normalLogInfo.content);
        long insert = writableDB.insert(DBSQLManager.LogTable.TABLE_NAME, null, contentValues);

        writableDB.close();

        if (insert != -1) {
            Log.i("xiaoqiao", "日志写入成功");
            return true;
        } else {
            return false;
        }


    }

    /**
     * 删除普通日志
     *
     * @param normalLogInfo 日志
     * @param context
     * @return true is success
     */
    public static synchronized boolean deleteNormalLog(NormalLogInfo normalLogInfo, Context context) {
        SQLiteDatabase writableDB = null;
        try {
            writableDB = getWritableDB(context);
            int delete = writableDB.delete(DBSQLManager.LogTable.TABLE_NAME, DBSQLManager.LogTable.Column.ID.name + "=?", new String[]{normalLogInfo.id + ""});
            writableDB.close();
            if (delete == 1) {
                Log.i("xiaoqiao", "删除成功");
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            if (writableDB != null) {
                writableDB.close();
            }
        }
        return false;
    }

    /**
     * 查询普通日志
     *
     * @param context 上下文
     * @param count   日志的条数
     */
    public static synchronized List<NormalLogInfo> queryNormalLog(Context context, int count) {
        SQLiteDatabase readableDB = getReadableDB(context);
        Cursor query = readableDB.query(DBSQLManager.LogTable.TABLE_NAME, null, null, null, null, null, null, "0 ," + count);
        if (query != null && query.getCount() != 0) {
            ArrayList<NormalLogInfo> normalLogInfos = new ArrayList<NormalLogInfo>();
            while (query.moveToNext()) {
                NormalLogInfo normalLogInfo = new NormalLogInfo();
                normalLogInfo.id = query.getInt(DBSQLManager.LogTable.Column.ID.index);
                normalLogInfo.time = query.getString(DBSQLManager.LogTable.Column.TIME.index);
                normalLogInfo.type = query.getString(DBSQLManager.LogTable.Column.TYPE.index);
                normalLogInfo.content = query.getString(DBSQLManager.LogTable.Column.CONTENT.index);
                normalLogInfos.add(normalLogInfo);
            }
            query.close();
            readableDB.close();
            return normalLogInfos;
        } else {
            return null;
        }

    }

    /**
     * 删除所有常规日志
     *
     * @param context
     * @return 删除日志的条数
     */
    public static synchronized long deleteAllNormalLog(Context context) {
        SQLiteDatabase writableDB = null;
        try {
            writableDB = getWritableDB(context);
            int delete = writableDB.delete(DBSQLManager.LogTable.TABLE_NAME, null, null);
            writableDB.close();
            if (delete != 0) {
                Log.i("xiaoqiao", "删除成功");
                return delete;
            } else {
                return 0;
            }

        } catch (Exception e) {
            if (writableDB != null) {
                writableDB.close();
            }
        }
        return 0;
    }

    /**
     * 获取日志总条数
     *
     * @param context
     * @return
     */
    public static synchronized long getLogCount(Context context) {
        SQLiteDatabase readableDB = null;
        try {
            readableDB = getReadableDB(context);
            Cursor query = readableDB.query(DBSQLManager.LogTable.TABLE_NAME, null, null, null, null, null, null);
            if (query != null) {
                int count = query.getCount();
                query.close();
                return count;
            }

        } catch (Exception e) {
            if (readableDB != null) {
                readableDB.close();
            }
        }
        return 0;
    }

    /**
     * 查询日志
     *
     * @param context
     * @param iStart  起始点
     * @param iCount
     * @return
     */
    public static synchronized List<NormalLogInfo> queryNormalLog(Context context, int iStart, int iCount) {
        SQLiteDatabase readableDB = getReadableDB(context);
        Cursor query = readableDB.query(DBSQLManager.LogTable.TABLE_NAME, null, null, null, null, null, null, iStart + "," + iCount);
        if (query != null && query.getCount() != 0) {
            ArrayList<NormalLogInfo> normalLogInfos = new ArrayList<NormalLogInfo>();
            while (query.moveToNext()) {
                NormalLogInfo normalLogInfo = new NormalLogInfo();
                normalLogInfo.id = query.getInt(DBSQLManager.LogTable.Column.ID.index);
                normalLogInfo.time = query.getString(DBSQLManager.LogTable.Column.TIME.index);
                normalLogInfo.type = query.getString(DBSQLManager.LogTable.Column.TYPE.index);
                normalLogInfo.content = query.getString(DBSQLManager.LogTable.Column.CONTENT.index);
                normalLogInfos.add(normalLogInfo);
            }
            query.close();
            readableDB.close();
            return normalLogInfos;
        } else {
            return null;
        }
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
