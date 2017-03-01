package com.zhuruqiao.www.crashcatch;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.shuicai.loghelper.db.LogDao;
import com.shuicai.loghelper.db.SDCardDBHelper;
import com.shuicai.loghelper.db.bean.NormalLogInfo;
import com.shuicai.loghelper.log.LogCatcher;
import com.shuicai.loghelper.log.LogType;
import com.shuicai.loghelper.utils.StorageUtils;

import java.io.File;
import java.util.List;

public class MainActivity extends Activity {

    private LogCatcher logcatHelper;
    private Button btLogInfo;
    private TextView tvInfo;

    private Button btDelete;
    private TextView tvDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //模拟发送日志
        sendLog();

        //开启日志监听线程
        logcatHelper = LogCatcher.getInstance(this);
        logcatHelper.start(LogType.TYPE_I);

        //获取日志信息
        btLogInfo = (Button) findViewById(R.id.btLogInfo);
        tvInfo = (TextView) findViewById(R.id.tvInfo);
        btLogInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvInfo.setText("");
                tvInfo.setText("占用内存：");
                tvInfo.append(getLogFileSize());
                tvInfo.append("条数：");
                tvInfo.append(LogDao.getLogCount(MainActivity.this) + "");
            }
        });

        //删除日志
        btDelete = (Button) findViewById(R.id.btDelete);
        tvDelete = (TextView) findViewById(R.id.tvDelete);
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvDelete.setText("共删除所有：" + LogDao.deleteAllNormalLog(MainActivity.this) + "条日志");
            }
        });

        //查询日志
        View btGetLog = findViewById(R.id.btGetLog);
        final EditText etStart = (EditText) findViewById(R.id.etStart);
        final EditText etCount = (EditText) findViewById(R.id.etCount);
        final TextView tvDetail = (TextView) findViewById(R.id.tvDetail);
        btGetLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String start = etStart.getText().toString().trim();
                String count = etCount.getText().toString().trim();

                int iStart = Integer.parseInt(start);
                int iCount = Integer.parseInt(count);
                List<NormalLogInfo> normalLogInfos = LogDao.queryNormalLog(MainActivity.this, iStart, iCount);
                //清空原有日志
                tvDetail.setText("");
                for (NormalLogInfo normalLogInfo : normalLogInfos) {
                    tvDetail.append(normalLogInfo.content+"\n");
                }

            }
        });

    }

    /**
     * 模拟发送日志
     */
    private void sendLog() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(20);
                        Log.i("哈哈", "我是日志我是日志我是日志我是日志");
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private String getLogFileSize() {
        String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String dbPath = absolutePath + "/database/" + SDCardDBHelper.DATABASE_NAME;
        File file = new File(dbPath);
        if (file.exists() && file.isFile()) {
            long length = file.length();

            return StorageUtils.formatSize(length);

        } else {
            return "";
        }

    }
}
