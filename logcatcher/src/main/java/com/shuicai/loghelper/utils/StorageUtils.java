/*
 * Copyright (C) 2012 www.amsoft.cn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shuicai.loghelper.utils;

/**
 * 磁盘管理查询工具类
 */
public class StorageUtils {

    private static final long UNIT = 1024;

    /**
     * 将内存大小转换成带相应单位的值
     *
     * @param size
     * @return
     */
    public static String formatSize(long size,int decimalCount) {

        double l;
        String s = "%." + decimalCount + "f";
        if (size >= UNIT << 16) {              //G
            l = size * 1.0 / (UNIT << 16);
            return String.format(s+" GB", l);
        } else if (size >= UNIT << 8) {        //MB
            l = size * 1.0 / (UNIT << 8);
            return String.format(s+" MB", l);
        } else if (size >= UNIT) {             //KB
            l = size * 1.0 / UNIT;
            return String.format(s+" KB", l);
        } else if (size > 0) {                 //B
            l = size;
            return String.format("%.0f B", l);
        } else {
            return "The size is less than zero ";
        }

    }

    public static String formatSize(long size){
       return formatSize(size,2);
    }


}
