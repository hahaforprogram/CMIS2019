package com.haha.cmis.utils;

import android.os.Environment;
import android.util.Log;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Haha
 * @date 2017/10/10
 */

public class FileHelper {

    public FileHelper() {
    }

    /**
     * 创建目录
     */
    public static String createDirectory(String strPath) {
        File strSDdir = null;
        String strRootDir = "";
        Log.d("FileHelper", "createDirectory: " + strPath);
        /**判断sd卡是否存在*/
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            /**获取跟目录*/
            strSDdir = Environment.getExternalStorageDirectory();
        }
        strRootDir = strSDdir.toString() + "/";

        try {
            String path = strRootDir + strPath + "/";
            File appChildDir = new File(path);
            /**不存在目录*/
            if (!appChildDir.exists() && !appChildDir.isDirectory()) {
                appChildDir.mkdirs();
            }
            return path;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return strPath;
    }

    /**
     * 创建文件
     */
    public static boolean createFile(byte[] bytes, String strFilePath, String strFilename, String strFileType) {
        String FileName = strFilename + strFileType;
        if (isFileExists(strFilePath + FileName)) {
            Log.d("createfile", "createFile: exists " + strFilePath + FileName);
            return true;
        }
        // 创建FileOutputStream对象
        FileOutputStream outputStream = null;
        // 创建BufferedOutputStream对象
        BufferedOutputStream bufferedOutputStream = null;
        File file = new File(strFilePath, FileName);
        try {
            // 如果文件存在则删除
            if (file.exists()) {
                return true;
            }
            // 在文件系统中根据路径创建一个新的空文件
            file.createNewFile();
            // 获取FileOutputStream对象
            outputStream = new FileOutputStream(file);
            // 获取BufferedOutputStream对象
            bufferedOutputStream = new BufferedOutputStream(outputStream);
            // 往文件所在的缓冲输出流中写byte数据
            bufferedOutputStream.write(bytes);
            // 刷出缓冲输出流，该步很关键，要是不执行flush()方法，那么文件的内容是空的。
            bufferedOutputStream.flush();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            bytes = null;
            // 关闭创建的流对象
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 文件是否存在
     */
    public static boolean isFileExists(String strFilename) {
        try {
            File file = new File(strFilename);
            if (file.exists()) {
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }


}
