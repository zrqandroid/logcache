package com.shuicai.loghelper.db;

import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.shuicai.loghelper.db.bean.NormalLogInfo;

import java.util.ArrayList;
import java.util.List;

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
     * 插入日志
     *
     * @param content 日志内容
     * @return true if success
     */
    public static boolean insertNormalLog(String content, Context context) {

        SQLiteDatabase writableDB = getWritableDB(context);

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBSQLManager.LogTable.Column.TIME.name, "");
        contentValues.put(DBSQLManager.LogTable.Column.TYPE.name, "");
        contentValues.put(DBSQLManager.LogTable.Column.CONTENT.name, content);
        long insert = writableDB.insert(DBSQLManager.LogTable.TABLE_NAME, null, contentValues);

        writableDB.close();

        if (insert != -1) {
            return true;
        } else {
            return false;
        }


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
    public List<NormalLogInfo> queryNormalLog( Context context, int count) {
        SQLiteDatabase writableDB = getWritableDB(context);
        Cursor query = writableDB.query(DBSQLManager.LogTable.TABLE_NAME, null, null, null, null, null, null, "LIMIT " + count);
        if (query!=null&&query.getCount()!=0){
            ArrayList<NormalLogInfo> normalLogInfos = new ArrayList<>();
            while (query.moveToNext()){
                NormalLogInfo normalLogInfo = new NormalLogInfo();
                normalLogInfo.id = query.getInt(DBSQLManager.LogTable.Column.ID.index);
                normalLogInfo.time = query.getLong(DBSQLManager.LogTable.Column.TIME.index);
                normalLogInfo.type = query.getString(DBSQLManager.LogTable.Column.TYPE.index);
                normalLogInfo.content = query.getString(DBSQLManager.LogTable.Column.CONTENT.index);
                normalLogInfos.add(normalLogInfo);
            }
            query.close();
            writableDB.close();
            return normalLogInfos;
        }else {
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
