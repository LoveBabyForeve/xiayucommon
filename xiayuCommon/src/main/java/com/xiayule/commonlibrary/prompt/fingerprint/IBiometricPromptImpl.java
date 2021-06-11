package com.xiayule.commonlibrary.prompt.fingerprint;

import android.os.CancellationSignal;

import androidx.annotation.NonNull;

/**
 * @Description: 生物认证回调   兼容api28
 * @Author: 下雨了
 * @CreateDate: 2020/7/9 15:40
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/7/9 15:40
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
interface IBiometricPromptImpl {
    //进行指纹验证
    void authenticate(@NonNull CancellationSignal cancel,
                      @NonNull OnBiometricIdentifyCallback callback);

}
