package com.shuicai.loghelper.utils;

import android.content.Context;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuruqiao on 2017/1/5.
 * e-mail:563325724@qq.com
 */

public class FileUtils {

    public enum CodingType {
        GBK("gbk", 0),
        UTF_8("utf-8", 1);

        public final String name;
        public final int index;

        CodingType(String name, int index) {
            this.name = name;
            this.index = index;

        }

    }

    /**
     * 获取文件大小
     *
     * @param filePath 文件路径
     * @return
     */
    public static long getFileSize(String filePath) {
        long size = 0;
        File file = new File(filePath);
        if (file.exists()) {
            size = file.length();
        }
        return size;

    }

    /**
     * 获取文件夹大小
     *
     * @param dir
     * @return
     */
    public static long getDirSize(File dir) {
        if (dir == null || !dir.isDirectory()) {
            return 0;
        }
        long dirSize = 0;
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                dirSize += file.length();
                dirSize += getDirSize(file);
            } else {
                dirSize += file.length();
            }
        }
        return dirSize;
    }

    /**
     * 获取目标文件夹文件个数
     *
     * @param dir
     * @return
     */
    public static long getFileCount(File dir) {
        long count = 0;
        if (dir == null || !dir.isDirectory()) {
            return 0;
        }
        File[] files = dir.listFiles();
        count = files.length;
        for (File file : files) {

            if (file.isDirectory()) {
                count = count + getFileCount(dir);
                count--;
            }

        }
        return count;

    }

    /**
     * 删除文件
     *
     * @param path
     * @return
     */
    public static boolean deleteFile(String path) {

        File file = new File(path);
        return deleteFile(file);

    }

    /**
     * 删除文件
     *
     * @param file
     * @return
     */
    public static boolean deleteFile(File file) {

        if (file.exists() && file.isFile()) {
            boolean delete = file.delete();
            return delete;
        } else {
            return false;
        }

    }


    /**
     * 重命名
     *
     * @param oldName
     * @param newName
     * @return
     */
    public static boolean rename(String oldName, String newName) {
        File f = new File(oldName);
        return f.renameTo(new File(newName));
    }

    /**
     * 获取文件扩展名
     *
     * @param fileName
     * @return
     */
    public static String getExtendName(String fileName) {
        if (TextUtils.isEmpty(fileName))
            return "";
        int point = fileName.lastIndexOf('.');
        return fileName.substring(point + 1);
    }

    /**
     * 获取文件扩展名
     *
     * @param fileName
     * @return
     */
    public static String getExtendName(File file) {
        String fileName = file.getName();
        if (TextUtils.isEmpty(fileName))
            return "";
        int point = fileName.lastIndexOf('.');
        return fileName.substring(point + 1);
    }


    /**
     * 根据文件的绝对路径获取文件名但不包含扩展名
     *
     * @param filePath
     * @return
     */
    public static String getFileNameWithoutExtendName(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return "";
        }
        int point = filePath.lastIndexOf('.');
        return filePath.substring(filePath.lastIndexOf(File.separator) + 1,
                point);
    }

    /**
     * 根据文件绝对路径获取文件名
     *
     * @param filePath
     * @return
     */
    public static String getFileName(String filePath) {
        if (TextUtils.isEmpty(filePath))
            return "unknown";
        int lastIndex = filePath.lastIndexOf(File.separator);
        if (lastIndex != -1) {
            String fileName = filePath.substring(lastIndex + 1);
            if (fileName.isEmpty()) {
                return "unknown";
            }
            return fileName;
        }
        return filePath;
    }


    public static InputStream getInputStream(String path) {
        File file = new File(path);
        if (file.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                return fileInputStream;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取输出流
     *
     * @param path
     * @return
     */
    public static FileOutputStream getOutStream(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (file.exists()) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                return fileOutputStream;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 读取文件获取字符串
     *
     * @param path
     * @param codeType
     * @return
     */
    public static String readString(String path, CodingType codeType) {

        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(getInputStream(path), codeType.name);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            return sb.toString();
        } catch (Exception e) {
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return null;
    }   /**
     * 读取文件获取字符串
     *
     * @param path
     * @param codeType
     * @return
     */
    public static List<String> readString(String path, CodingType codeType, int count) {

        InputStreamReader inputStreamReader = null;
        ArrayList<String> strings= new ArrayList<>();
        try {
            inputStreamReader = new InputStreamReader(getInputStream(path), codeType.name);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = null;
            while ((line = reader.readLine()) != null) {

                strings.add(line);
            }
            return strings;
        } catch (Exception e) {
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 从流中读取字符串
     *
     * @param is
     * @return
     */
    public static String readString(InputStream is) {

        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            return sb.toString();
        } catch (Exception e) {
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 将文本写入文件中
     *
     * @param targetFilePath
     * @param codeType
     * @param content
     */
    public static void writeString(String targetFilePath, CodingType codeType, String content) {
        if (TextUtils.isEmpty(content)) {
            return;
        }
        FileOutputStream outStream = null;
        try {
            byte[] bytes = content.getBytes(codeType.name);
            outStream = getOutStream(targetFilePath);
            outStream.write(bytes);
            outStream.close();

        } catch (Exception e) {
            e.printStackTrace();
            if (outStream != null) {
                try {
                    outStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 写文本文件 在Android系统中，文件保存在 /data/data/PACKAGE_NAME/files 目录下
     *
     * @param context
     * @param fileName
     * @param content
     */
    public static void write(Context context, String fileName, String content) {
        if (content == null)
            content = "";

        try {
            FileOutputStream fos = context.openFileOutput(fileName,
                    Context.MODE_PRIVATE);
            fos.write(content.getBytes());

            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取文本文件 /data/data/PACKAGE_NAME/files
     *
     * @param context
     * @param fileName
     * @return
     */
    public static String read(Context context, String fileName) {
        try {
            FileInputStream in = context.openFileInput(fileName);
            return readString(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


}
