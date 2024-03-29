package com.xiayule.commonlibrary.logcat;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: 日志文件管理 所有的logcat
 * @Author: 下雨了
 * @CreateDate: 2020-10-26 16:45
 * @UpdateUser: 更新者
 * @UpdateDate: 2020-10-26 16:45
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

/**
 * 使用方法
 * LogcatFileManager.getInstance().startLogcatManager(this);
 */
@Deprecated
public class LogcatFileManager {
    // 实例
    private static LogcatFileManager LogcatFileManager = null;

    public static synchronized LogcatFileManager getInstance() {
        if (LogcatFileManager == null) {
            LogcatFileManager = new LogcatFileManager();
        }
        return LogcatFileManager;
    }


    private static String PATH_LOGCAT;
    private LogDumper mLogDumper = null;
    private int mPId;
    private SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private LogcatFileManager() {
        mPId = android.os.Process.myPid();
    }


    public void startLogcatManager(Context context) {
        String folderPath = null;
        //如果SD不可用，则存储在沙盒中
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            folderPath = context.getExternalCacheDir().getAbsolutePath() + File.separator + "XiaoNeng-Logcat";
        } else {
            folderPath = context.getCacheDir().getAbsolutePath() + File.separator + "XiaoNeng-Logcat";
        }
        LogcatFileManager.getInstance().start(folderPath);
    }


    public void stopLogcatManager() {
        LogcatFileManager.getInstance().stop();
    }


    private void setFolderPath(String folderPath) {
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        if (!folder.isDirectory()) {
            throw new IllegalArgumentException("The logcat folder path is not a directory: " +
                    folderPath);
        }


        PATH_LOGCAT = folderPath.endsWith("/") ? folderPath : folderPath + "/";
//        LogUtils.d(PATH_LOGCAT);
    }


    public void start(String saveDirectoy) {
        setFolderPath(saveDirectoy);
        if (mLogDumper == null) {
            mLogDumper = new LogDumper(String.valueOf(mPId), PATH_LOGCAT);
        }
        mLogDumper.start();
    }


    public void stop() {
        if (mLogDumper != null) {
            mLogDumper.stopLogs();
            mLogDumper = null;
        }
    }


    private class LogDumper extends Thread {
        private Process logcatProc;
        private BufferedReader mReader = null;
        private boolean mRunning = true;
        String cmds = null;
        private String mPID;
        private FileOutputStream out = null;


        public LogDumper(String pid, String dir) {
            mPID = pid;
            try {
                out = new FileOutputStream(new File(dir, "logcat-" + simpleDateFormat1.format(new
                        Date()) + ".log"), true);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


            /**
             * * * log level：*:v , *:d , *:w , *:e , *:f , *:s * * Show the
             * current mPID process level of E and W log. * *
             */
            // cmds = "logcat *:e *:w | grep \"(" + mPID + ")\"";
            cmds = "logcat   *:e *:w *:d | grep \"(" + mPID + ")\"";
        }


        public void stopLogs() {
            mRunning = false;
        }


        @Override
        public void run() {
            try {
                logcatProc = Runtime.getRuntime().exec(cmds);
                mReader = new BufferedReader(new InputStreamReader(logcatProc.getInputStream()),
                        1024);
                String line = null;
                while (mRunning && (line = mReader.readLine()) != null) {
                    if (!mRunning) {
                        break;
                    }
                    if (line.length() == 0) {
                        continue;
                    }
                    if (out != null && line.contains(mPID)) {
                        out.write((simpleDateFormat2.format(new Date()) + "  " + line + "\n")
                                .getBytes());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (logcatProc != null) {
                    logcatProc.destroy();
                    logcatProc = null;
                }
                if (mReader != null) {
                    try {
                        mReader.close();
                        mReader = null;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    out = null;
                }
            }
        }
    }
}
