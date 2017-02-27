package com.zhuruqiao.www.crashcatch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.shuicai.loghelper.db.LogDao;
import com.shuicai.loghelper.log.LogType;
import com.shuicai.loghelper.log.LogCatcher;

public class MainActivity extends AppCompatActivity {

    private LogCatcher logcatHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         logcatHelper = LogCatcher.getInstance();
        LogDao.getWritableDB(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true){
                        Thread.sleep(2000);
                        Log.i("哈哈","hehe");
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                logcatHelper.start(LogType.TYPE_I);

            }
        }).start();
    }
}
