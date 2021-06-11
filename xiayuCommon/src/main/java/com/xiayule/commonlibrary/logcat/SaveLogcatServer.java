package com.xiayule.commonlibrary.logcat;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.xiayule.commonlibrary.logcat.upload.UploadLogManager;
import com.xiayule.commonlibrary.logcat.utils.LogcatUtils;
import com.xiayule.commonlibrary.utlis.SPUtils;

import java.io.Serializable;

/**
 * @Description: 保存日志文件服务
 * @Author: 下雨了
 * @CreateDate: 2021-01-12 9:57
 * @UpdateUser: 更新者
 * @UpdateDate: 2021-01-12 9:57
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class SaveLogcatServer extends IntentService {
    private static final String TAG = "LogcatServer";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public SaveLogcatServer() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        assert intent != null;
        Serializable ser = intent.getSerializableExtra("ser");
        String file = intent.getStringExtra("file");
        boolean isAdd = intent.getBooleanExtra("isAdd", false);

        // 文件保存成功
        boolean b = LogcatUtils.saveLogcat(ser, file, isAdd);

        if (b) {
            if (!SPUtils.getInstance().contains(SavaLogcatManager.LOGCAT_NUM)) {
                SPUtils.getInstance().put(SavaLogcatManager.LOGCAT_NUM, 0);
            } else {
                SPUtils.getInstance().put(SavaLogcatManager.LOGCAT_NUM, SPUtils.getInstance().getInt(SavaLogcatManager.LOGCAT_NUM) + 1);
            }
        }
        UploadLogManager.getInstance().upLoadLogFile();

    }

}
