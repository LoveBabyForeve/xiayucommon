package com.xiayule.commonlibrary.prompt.fingerprint;

import android.app.Activity;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;

import androidx.annotation.RequiresApi;

/**
 * @Description: 指纹 API23
 * @Author: 下雨了
 * @CreateDate: 2020/7/9 15:39
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/7/9 15:39
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class BiometricPromptApi23 implements IBiometricPromptImpl {
    private Context mContext;

    // 指纹管理器
    private FingerprintManager mFingerprintManager;
    //  取消信号
    private CancellationSignal mCancellationSignal;
    // 回调信息
    private OnBiometricIdentifyCallback onBiometricIdentifyCallback;
    // 正在认证回调
    private FingerprintManager.AuthenticationCallback mFmAuthCallback = new FingerprintManageCallbackImpl();
    // 自定义弹窗
    private BiometricPromptDialog mDialog;

    public BiometricPromptApi23(Context context) {
        this.mContext = context;
        mFingerprintManager = getFingerprintManager(mContext);
    }

    @Override
    public void authenticate(CancellationSignal cancel, OnBiometricIdentifyCallback callback) {
        //指纹识别的回调
        onBiometricIdentifyCallback = callback;
        /**
         * 我实现了一个自定义dialog，
         * BiometricPromptDialog.OnBiometricPromptDialogActionCallback 是自定义dialog的回调
         */
        mCancellationSignal = cancel;

        mDialog = BiometricPromptDialog.getInstance();
        mDialog.setOnBiometricPromptDialogActionCallback(new BiometricPromptDialog.OnBiometricPromptDialogActionCallback() {
            @Override
            public void onDialogDismiss() {
                //当dialog消失的时候，包括点击userPassword、点击cancel、和识别成功之后
                if (mCancellationSignal != null && !mCancellationSignal.isCanceled()) {
                    mCancellationSignal.cancel();
                }
            }

            @Override
            public void onUsePassword() {
                //一些情况下，用户还可以选择使用密码
                if (onBiometricIdentifyCallback != null) {
                    onBiometricIdentifyCallback.onUsePassword();
                }
            }

            @Override
            public void onCancel() {
                //点击cancel键
                if (onBiometricIdentifyCallback != null) {
                    onBiometricIdentifyCallback.onCancel();
                }
            }
        });

        mDialog.show(((Activity) mContext).getFragmentManager(), "BiometricPromptApi23");

        if (mCancellationSignal == null) {
            mCancellationSignal = new CancellationSignal();
        }
        mCancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() {
            @Override
            public void onCancel() {
                mDialog.dismiss();
            }
        });
        try {
            CryptoObjectHelper cryptoObjectHelper = new CryptoObjectHelper();
            getFingerprintManager(mContext).authenticate(cryptoObjectHelper.buildCryptoObject(),
                    mCancellationSignal, 0, mFmAuthCallback, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取指纹管理器
     */
    private FingerprintManager getFingerprintManager(Context context) {
        if (mFingerprintManager == null) {
            mFingerprintManager = context.getSystemService(FingerprintManager.class);
        }
        return mFingerprintManager;
    }

    /**
     * 认证回调信息
     */
    private class FingerprintManageCallbackImpl extends FingerprintManager.AuthenticationCallback {
        // 处理了用户点击取消了指纹验证的逻辑
        @Override
        public void onAuthenticationError(int errorCode, CharSequence errString) {
            super.onAuthenticationError(errorCode, errString);
            mDialog.setState(BiometricPromptDialog.STATE_ERROR);
            onBiometricIdentifyCallback.onError(errorCode, errString.toString());
        }

        // 处理了授权帮助的提示
        @Override
        public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
            super.onAuthenticationHelp(helpCode, helpString);
            mDialog.setState(BiometricPromptDialog.STATE_NORMAL);
            onBiometricIdentifyCallback.onFailed();
        }
        // 处理了授权成功后跳转到主页面
        @Override
        public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
            super.onAuthenticationSucceeded(result);
            mDialog.setState(BiometricPromptDialog.STATE_SUCCEED);
            onBiometricIdentifyCallback.onSucceeded();
        }
        // 处理了授权失败再试一次的提示
        @Override
        public void onAuthenticationFailed() {
            super.onAuthenticationFailed();
            mDialog.setState(BiometricPromptDialog.STATE_FAILED);
            onBiometricIdentifyCallback.onFailed();
        }
    }

    /* =============================================== 对外公开的方法 ================================================== */

    /**
     * 确定是否至少有一个指纹登记
     */
    public boolean hasEnrolledFingerprints() {
        if (mFingerprintManager != null) {
            return mFingerprintManager.hasEnrolledFingerprints();
        }
        return false;
    }

    /**
     * 确定指纹硬件是否存在且功能正常
     * 判断设备是否支持指纹解锁
     */
    public boolean isHardwareDetected() {
        if (mFingerprintManager != null) {
            return mFingerprintManager.isHardwareDetected();
        }
        return false;
    }


}
