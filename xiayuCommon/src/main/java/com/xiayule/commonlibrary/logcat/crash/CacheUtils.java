package com.xiayule.commonlibrary.logcat.crash;

import com.xiayule.commonlibrary.utlis.KLog;
import com.xiayule.commonlibrary.utlis.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.io.Serializable;

/**
 * @Description: 系统崩溃异常捕获 保存 读取 信息工具类
 * @Author: 下雨了
 * @CreateDate: 2020-11-26 16:45
 * @UpdateUser: 下雨了
 * @UpdateDate: 2020-12-11 20:08:53
 * @UpdateRemark: 已弃用 统一使用LogcatUtils
 * @Version: 1.0
 */
@Deprecated
public class CacheUtils {
    private static final String TAG = "CacheUtils";

    public static boolean saveObject(String key, Serializable ser) {
        File file = new File(Utils.getContext().getCacheDir() + "/" + key);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return saveObject(ser, file);
    }

    public static Serializable readObject(String key) {
        File file = new File(Utils.getContext().getCacheDir() + "/" + key);
        return readObject(file);
    }

    public static boolean saveObject(Serializable ser, File file) {
        FileOutputStream fos = null;
//        ObjectOutputStream oos = null;
        PrintStream ps = null;
        try {
            fos = new FileOutputStream(file);
            ps = new PrintStream(fos, false, "UTF-8");
            ps.println("╔═══════════════════════════════════ crash ═══════════════════════════════════");
            ps.print(ser);
            ps.println("╚═══════════════════════════════════  end  ═══════════════════════════════════");
            ps.flush();
//            oos = new ObjectOutputStream(fos);
//            oos.writeObject("\r\n╔═══════════════════════════════════ crash ═══════════════════════════════════\r\n");
//            oos.writeObject(ser);
//            oos.writeObject("\r\n╚═══════════════════════════════════  end  ═══════════════════════════════════\r\n");
//            oos.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (ps != null) {
                ps.close();
            }
//            if (oos != null) {
//                try {
//                    oos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Serializable readObject(File file) {
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
}
