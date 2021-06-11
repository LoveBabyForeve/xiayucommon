package com.xiayule.commonlibrary.prompt.fingerprint;

import android.app.KeyguardManager;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;

import androidx.annotation.NonNull;

import com.xiayule.commonlibrary.utlis.ToastUtils;

/**
 * @Description: 管理 生物识别
 * @Author: 下雨了
 * @CreateDate: 2020/7/9 16:19
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/7/9 16:19
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class BiometricPromptManager {

    private Context mContext;
    private static BiometricPromptManager biometricPromptManager;
//    private CancellationSignal cancellationSignal;

    private IBiometricPromptImpl mImpl;

    public BiometricPromptManager(Context mContext) {
        this.mContext = mContext;
    }

    public static synchronized BiometricPromptManager getInstance(Context context) {
        if (biometricPromptManager == null) {
            biometricPromptManager = new BiometricPromptManager(context);
        }
        return biometricPromptManager;
    }

    /**
     * 初始化指纹识别
     */
    public void init(){
        if (isAboveApi28()) {
            mImpl = new BiometricPromptApi28(mContext);
        } else if (isAboveApi23()) {
            mImpl = new BiometricPromptApi23(mContext);
        }
    }


    private OnBiometricIdentifyCallback onBiometricIdentifyCallback;

    /**
     * 设置结果回调 接口
     */
    public void setOnBiometricIdentifyCallback(OnBiometricIdentifyCallback onBiometricIdentifyCallback) {
        this.onBiometricIdentifyCallback = onBiometricIdentifyCallback;
    }


    // 判断版本号
    private boolean isAboveApi28() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P;
    }

    private boolean isAboveApi23() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /* =============================================== 对外公开的方法 ================================================== */

    /**
     * 进行验证
     */
    public void authenticate(@NonNull OnBiometricIdentifyCallback callback) {
//        this.cancellationSignal = new CancellationSignal();
        mImpl.authenticate(new CancellationSignal(), callback);
    }

    public void authenticate(@NonNull CancellationSignal cancel,
                             @NonNull OnBiometricIdentifyCallback callback) {
//        this.cancellationSignal = cancel;
        mImpl.authenticate(cancel, callback);
    }


    /**
     * 确定是否至少有一个指纹登记
     */
    public boolean hasEnrolledFingerprints() {
        if (isAboveApi28()) {
            //TODO 这是Api23的判断方法，也许以后有针对Api28的判断方法
            final FingerprintManager manager = mContext.getSystemService(FingerprintManager.class);
            return manager != null && manager.hasEnrolledFingerprints();
        } else if (isAboveApi23()) {
            return ((BiometricPromptApi23) mImpl).hasEnrolledFingerprints();
        } else {
            return false;
        }
    }

    /**
     * 您还未设置锁屏
     */
    public boolean isKeyguardSecure() {
        KeyguardManager keyguardManager = (KeyguardManager) mContext.getSystemService(Context.KEYGUARD_SERVICE);
        if (keyguardManager.isKeyguardSecure()) {
            return true;
        }
        return false;
    }


    /**
     * 确定指纹硬件是否存在且功能正常
     * 判断设备是否支持指纹解锁
     */
    public boolean isHardwareDetected() {
        if (isAboveApi28()) {
            //TODO 这是Api23的判断方法，也许以后有针对Api28的判断方法
            final FingerprintManager fm = mContext.getSystemService(FingerprintManager.class);
            return fm != null && fm.isHardwareDetected();
        } else if (isAboveApi23()) {
            return ((BiometricPromptApi23) mImpl).isHardwareDetected();
        } else {
            return false;
        }
    }

    /**
     * 判断是否支持指纹的逻辑
     */
    public boolean supportFingerprint() {
        if (!isAboveApi23()) {
            ToastUtils.showShort("您的系统版本太低，不支持指纹功能");
            return false;
        } else {
            if (!isHardwareDetected()) {
                ToastUtils.showShort("您的手机不支持指纹功能");
                return false;
            } else {
                if (!isKeyguardSecure()) {
                    ToastUtils.showShort("您还未设置锁屏，请先设置锁屏并添加一个指纹");
                    return false;
                } else if (!hasEnrolledFingerprints()) {
                    ToastUtils.showShort("您至少需要在系统设置中添加一个指纹");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 设备是否支持生物识别
     */
    public boolean isBiometricPromptEnable() {
        return isAboveApi23()
                && isHardwareDetected()
                && hasEnrolledFingerprints()
                && isKeyguardSecure();
    }

    /**
     * 停止指纹认证监听
     */
//    public void stopListening() {
//        if (cancellationSignal != null) {
//            cancellationSignal.cancel();
//            cancellationSignal = null;
//        }
//    }

}
