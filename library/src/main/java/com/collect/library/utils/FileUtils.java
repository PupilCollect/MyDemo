package com.collect.library.utils;

import android.os.Environment;

import java.io.File;

public class FileUtils {

    public static final String TAG = FileUtils.class.getSimpleName();

    //删除文件夹（前提：文件夹为空以及InputStream和OutputStream等一些数据文件流关掉【close()】，否则文件无法删除）
    //删除文件夹
    private static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //删除指定文件夹下的所有文件
    private static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    // 第一种: 获取 /storage/emulated/0
    public static File sdCardIsAvailable() {
        //首先判断外部存储是否可用
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File sd = new File(Environment.getExternalStorageDirectory().getPath());
            //Log.e(TAG, "sd = " + sd);//sd = /storage/emulated/0
            return sd;
        } else {
            return null;
        }
    }

    // 第二种: 获取   /storage/emulated/0(类似上面)
    static File getSDPath() {
        File sdDir = null;
        // 判断sd卡是否存在
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取根目录
        }
        return sdDir;
    }

    // 获取 /storage/emulated/0/forestryFile
    static File getSavePath() {
        File sdPath = getSDPath();
        File hexiData = null;
        if (sdPath != null) {
            hexiData = new File(sdPath, "forestryFile");
            if (!hexiData.exists()) {
                hexiData.mkdirs();
            }
        }
        return hexiData;
    }

    public static File getSavePath(String folderName) {
        File savePath = getSavePath();
        File file = new File(savePath, folderName);
        return file;
    }
}
