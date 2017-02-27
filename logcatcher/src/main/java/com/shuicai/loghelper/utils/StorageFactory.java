package com.shuicai.loghelper.utils;

import android.os.Environment;
import android.support.annotation.IntDef;

import java.io.File;
import java.io.IOException;

/**
 * Created by zhuruqiao on 2017/2/27.
 * e-mail:563325724@qq.com
 */

public class StorageFactory {

    public static final int TYPE_LOG = 0;
    public static final int TYPE_CRASH = 1;

    @IntDef({TYPE_LOG,TYPE_CRASH})
    public @interface FileType{

    }

    private static String ROOT_PATH;

    private static String LOG_FILE_PATH;
    private static String CRASH_FILE_PATH;

    //日志缓存文件
    private static final String LOG_FILE_NAME = "log.txt";

    //崩溃缓存文件
    private static final String CRASH_FILE_NAME = "crash.txt";

    static {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            ROOT_PATH = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + File.separator;
        }
        if (ROOT_PATH != null) {
            LOG_FILE_PATH = mkFile(ROOT_PATH + LOG_FILE_NAME);
            CRASH_FILE_PATH = mkFile(ROOT_PATH + CRASH_FILE_NAME);
        }
    }

    public static String mkFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                boolean success = file.createNewFile();
                if (success) {
                    return file.getAbsolutePath();
                }
            } catch (IOException e) {
                e.printStackTrace();

            } finally {
                return null;
            }
        } else {
            return file.getAbsolutePath();
        }
    }

    public static String getFilePath(@FileType int type) {
        switch (type) {
            case TYPE_LOG:
                return LOG_FILE_PATH;
            case TYPE_CRASH:
                return CRASH_FILE_PATH;
            default:
                return ROOT_PATH;
        }
    }


}
