package com.shuicai.loghelper.log;

import android.content.Context;

import com.shuicai.loghelper.db.LogDao;
import com.shuicai.loghelper.db.bean.NormalLogInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * log日志统计保存
 *
 * @author way
 */

public class LogCatcher {

    private static LogCatcher instance;
    private LogDumper mLogDumper = null;
    private int mPId;
    private static final int BUFFER_SIZE = 1024;
    private Context mContext;


    public static LogCatcher getInstance(Context context) {
        if (instance == null) {
            instance = new LogCatcher(context);
        }
        return instance;
    }

    private LogCatcher(Context context) {
        mContext = context;
        mPId = android.os.Process.myPid();
    }

    public void start(LogType type) {
        if (mLogDumper == null)
            mLogDumper = new LogDumper(String.valueOf(mPId), type);
        if (!mLogDumper.isAlive()) {
            mLogDumper.start();
        }
    }

    public void stop() {
        if (mLogDumper != null) {
            mLogDumper.stopLogs();
            mLogDumper = null;
        }
    }

    private class LogDumper extends Thread {

        private Process logcatProc;
        private BufferedReader mReader;
        private boolean mRunning = true;
        String cmds;
        private String mPID;

        public LogDumper(String pid, LogType type) {
            mPID = pid;

            /**
             *
             * 日志等级：*:v , *:d , *:w , *:e , *:f , *:s 
             *
             * 显示当前mPID程序的 E和W等级的日志. 
             *
             * */

            // cmds = "logcat *:e *:w | grep \"(" + mPID + ")\"";  
            // cmds = "logcat  | grep \"(" + mPID + ")\"";//打印所有日志信息  
            // cmds = "logcat -s way";//打印标签过滤信息  
//            cmds = "logcat *:e *:i | grep \"(" + mPID + ")\"";
            cmds = "logcat " + type.name + " | grep \"(" + mPID + ")\"";

        }

        public void stopLogs() {
            mRunning = false;
        }

        @Override
        public void run() {
            try {
                logcatProc = Runtime.getRuntime().exec(cmds);
                mReader = new BufferedReader(new InputStreamReader(
                        logcatProc.getInputStream()), BUFFER_SIZE);
                String line;
                while (mRunning && (line = mReader.readLine()) != null) {
                    if (!mRunning) {
                        break;
                    }
                    if (line.length() == 0) {
                        continue;
                    }
                    if (line.contains(mPID)) {
                        LogDao.insertNormalLog(getNormalLogInfo(line), mContext);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (logcatProc != null) {
                    logcatProc.destroy();
                    logcatProc = null;
                }
                if (mReader != null) {
                    try {
                        mReader.close();
                        mReader = null;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

        }

    }

    /**
     * 封装日志到对象中
     *
     * @param line
     * @return
     */
    private NormalLogInfo getNormalLogInfo(String line) {
        NormalLogInfo normalLogInfo = new NormalLogInfo();
        normalLogInfo.content = line;
        normalLogInfo.time = getTimeFromLog(line);
        return normalLogInfo;
    }

    /**
     * 从日志中截取时间
     *
     * @param log
     * @return
     */
    public static String getTimeFromLog(String log) {
        try {
            String[] split = log.split(" ");
            return split[0] + " " + split[1];
        } catch (Exception e) {
            //如果解析时间失败 获取本地时间作为日志时间
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss.SS");
            return simpleDateFormat.format(new Date());
        }

//获取毫秒值
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss.SS");
//        try {
//            Date parse = simpleDateFormat.parse(split[0] + " " + split[1]);
//            return parse.getTime();
//        } catch (ParseException e) {
//            e.printStackTrace();
//            return System.currentTimeMillis();
//        }
    }

}  
