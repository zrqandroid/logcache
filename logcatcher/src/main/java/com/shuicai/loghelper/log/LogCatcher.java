package com.shuicai.loghelper.log;

import com.shuicai.loghelper.utils.StorageFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

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


    public static LogCatcher getInstance() {
        if (instance == null) {
            instance = new LogCatcher();
        }
        return instance;
    }

    private LogCatcher() {
        mPId = android.os.Process.myPid();
    }

    public void start(LogType type) {
        if (mLogDumper == null)
            mLogDumper = new LogDumper(String.valueOf(mPId), type, StorageFactory.getFilePath(StorageFactory.TYPE_LOG));
        mLogDumper.start();
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
        private FileOutputStream os;

        public LogDumper(String pid, LogType type, String path) {
            mPID = pid;
            try {
                os = new FileOutputStream(new File(path));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


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
                    if (os != null && line.contains(mPID)) {
                        os.write((line + "\n").getBytes());
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
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    os = null;
                }

            }

        }

    }

}  
