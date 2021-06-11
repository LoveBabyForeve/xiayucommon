package com.xiayule.commonlibrary.logcat.utils;

import com.xiayule.commonlibrary.utlis.DateUtils;
import com.xiayule.commonlibrary.utlis.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description: 日志 文件 操作类
 * @Author: 下雨了
 * @CreateDate: 2020-11-26 17:10
 * @UpdateUser: 更新者
 * @UpdateDate: 2020-11-26 17:10
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class FileUtils {
    private static final String TAG = "FileUtil";

    /**
     * 返回 系统缓存 路径
     */
    public static File getLogcatCacheDir() {
        File dir = new File(Utils.getContext().getCacheDir() + File.separator);
        if (!dir.exists()) {
            dir.mkdir();
        }
        return dir;
    }

    /**
     * 返回zip压缩文件名称
     */
    public static File getLogcatCacheFile() {
        String fileName = DateUtils.getCurrDate(DateUtils.FORMAT_ONE_TWO);
        return new File(getLogcatCacheDir() + File.separator + "Log_" + fileName + "." + "zip");
    }


    /**
     * 返回 文件存储路径
     *
     * @param folderName 文件夹名称
     */
    public static File getLogcatCacheDir(String folderName) {
        File dir = new File(Utils.getContext().getCacheDir() + File.separator + "Log" + File.separator + folderName);
        if (!dir.exists()) {
            //   注意这里是 mkdirs()方法  可以创建多个文件夹
            dir.mkdirs();
        }
        return dir;
    }

    /**
     * 返回文件名称
     *
     * @param folderName 文件夹名称
     * @param fileName   文件名称
     * @param type       文件格式类型
     */
    public static File getLogcatCacheFile(String folderName, String fileName, String type) {
        String fileNameTime = DateUtils.getCurrent() + "_" + (int) ((Math.random() * 9 + 1) * 100000);
//        String fileNameTime = DateUtil.getCurrDate(DateUtil.FORMAT_ONE_TWO) + "_" + new Random().nextInt(899999) + 100000;
        return new File(getLogcatCacheDir(folderName) + File.separator + fileName + "_" + fileNameTime + "." + type);
    }

    /**
     * 返回目录下所有文件
     */
    public static List<File> getDirectory(File file) {
        List<File> arrayList = new ArrayList<>();
        File[] files = file.listFiles();
        arrayList.addAll(Arrays.asList(files));
//        for (File file1 : files) {
//            //判断是否为文件
//            if (file1.isFile()) {
//                arrayList.add(file1);
//            }
//            //判断是否为文件夹
//            if (file1.isDirectory()) {
//                // 递归循环
//                getDirectory(file1);
//            }
//            arrayList.add(file1);
//        }
        return arrayList;
    }

    /**
     * 删除目录和其下的所有文件
     *
     * @param file 文件夹或者文件路径
     */
    public static void deleteDirectory(File file) {
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            for (File f : listFiles) {
                deleteDirectory(f);
            }
            file.delete();
        } else {
            file.delete();
        }
    }


    /**
     * 获取文件夹的大小
     *
     * @param directory 需要测量大小的文件夹
     * @return 返回文件夹大小，单位byte
     */
    public static long folderSize(File directory) {
        long length = 0;
        for (File file : directory.listFiles()) {
            if (file.isFile())
                length += file.length();
            else
                length += folderSize(file);
        }
        return length;
    }

    /**
     * 判断文件是否存在
     *
     * @param strFile 文件路径
     * @return {@code true}:存在 <br> {@code false}: 不存在
     */
    public static boolean fileIsExists(String strFile) {
        return fileIsExists(new File(strFile));
    }

    /**
     * 判断文件是否存在
     *
     * @param file 文件路径
     * @return {@code true}:存在 <br> {@code false}: 不存在
     */
    public static boolean fileIsExists(File file) {
        try {
            if (!file.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
