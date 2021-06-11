package com.xiayule.commonlibrary.logcat.utils;

import com.xiayule.commonlibrary.utlis.KLog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.io.Serializable;

/**
 * @Description: 日志保存文件管理
 * @Author: 下雨了
 * @CreateDate: 2020-12-03 13:58
 * @UpdateUser: 更新者
 * @UpdateDate: 2020-12-03 13:58
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class LogcatUtils {
    public static final String TAG = "LogcatUtils";

    /**
     * 保存日志
     *
     * @param ser      内容
     * @param filePath 文件保存路径
     * @return {@code true}:成功 <br> {@code false}:失败
     */
    public static boolean saveLogcat(Serializable ser, String filePath) {
        File file = new File(filePath);
        return saveLogcat(ser, file);
    }

    /**
     * 保存日志
     *
     * @param ser  内容
     * @param file 文件保存路径
     * @return {@code true}:成功 <br> {@code false}:失败
     */
    public static boolean saveLogcat(Serializable ser, File file) {
        return saveLogcat(ser, file, false);
    }

    /**
     * 保存日志
     *
     * @param ser      内容
     * @param filePath 文件保存路径
     * @param isAdd    是否追加  {@code true}:追加 <br> {@code false}:不追加
     * @return {@code true}:成功 <br> {@code false}:失败
     */
    public static boolean saveLogcat(Serializable ser, String filePath, boolean isAdd) {
        return saveLogcat(ser, new File(filePath), isAdd);
    }

    /**
     * 保存日志
     *
     * @param ser   内容
     * @param file  文件保存路径
     * @param isAdd 是否要追加内容
     * @return {@code true}:成功 <br> {@code false}:失败
     */
    public static boolean saveLogcat(Serializable ser, File file, boolean isAdd) {
        FileOutputStream fos = null;
        PrintStream ps = null;
        try {
            fos = new FileOutputStream(file, isAdd);
            ps = new PrintStream(fos, false, "UTF-8");
            ps.println(ser);
            ps.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 追加内容(已弃用，追加的数据不会自动换行)
     *
     * @param ser  内容
     * @param file 文件保存路径
     * @return {@code true}:成功 <br> {@code false}:失败
     */
    @Deprecated
    public static boolean addContent(Serializable ser, File file) {
        RandomAccessFile randomFile = null;
        try {
            // 打开一个随机访问文件流，按读写方式
            randomFile = new RandomAccessFile(file, "rw");
            // 文件长度，字节数
            long fileLength = randomFile.length();
            // 将写文件指针移到文件尾。
            randomFile.seek(fileLength);
            randomFile.writeBytes((String) ser);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (randomFile != null) {
                try {
                    randomFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 读取日志
     *
     * @param filePath 文件路径
     * @return 返回内容
     */
    public static Serializable readLogcat(String filePath) {
        File file = new File(filePath);
        return readLogcat(file);
    }

    /**
     * 读取日志
     *
     * @param file 文件路径
     * @return 返回内容
     */
    public static Serializable readLogcat(File file) {
        if (file == null || !file.exists() || file.isDirectory()) {
            return null;
        }
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            return (Serializable) ois.readObject();


        } catch (IOException e) {
            if (e instanceof InvalidClassException) {
                file.delete();
            }
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;

        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    KLog.e(TAG, e.toString());
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    KLog.e(TAG, e.toString());
                }
            }
        }
    }

    /**
     * 删除日志
     *
     * @param file 文件夹或者文件路径
     * @return {@code true}:成功 <br> {@code false}:失败
     */
    public static void clearLogcat(File file) {
        FileUtils.deleteDirectory(file);
    }


}
