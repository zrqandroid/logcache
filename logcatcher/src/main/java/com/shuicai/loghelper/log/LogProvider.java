package com.shuicai.loghelper.log;

import com.shuicai.loghelper.utils.FileUtils;
import com.shuicai.loghelper.utils.StorageFactory;

import java.util.List;

/**
 * Created by zhuruqiao on 2017/2/27.
 * e-mail:563325724@qq.com
 */

public class LogProvider {

    /**
     * 获取日志
     *
     * @param count 日志条数
     * @return
     */
    public List<String> getLog(int count) {
        String logPath = StorageFactory.getFilePath(StorageFactory.TYPE_LOG);
        List<String> logs = FileUtils.readString(logPath, FileUtils.CodingType.UTF_8, count);
        return logs;

    }
}
