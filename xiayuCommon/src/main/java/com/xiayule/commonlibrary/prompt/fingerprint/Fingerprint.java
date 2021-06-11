package com.xiayule.commonlibrary.prompt.fingerprint;

import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import androidx.annotation.NonNull;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;
import androidx.core.os.CancellationSignal;

import com.xiayule.commonlibrary.utlis.ToastUtils;

import java.security.KeyStore;
import java.security.KeyStoreException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * @Description: 指纹  初版 (已经弃用)
 * @Author: 下雨了
 * @CreateDate: 2020/7/7 10:25
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/7/7 10:25
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@Deprecated
public class Fingerprint {
    private static final String DEFAUL_KEY_NAME = "AndroidKey";
    private static final String KEYSTORE_NAME = "AndroidKeyStore";
    private Context mContext;
    private static Fingerprint mFingerprint;

    private Fingerprint(Context mContext) {
        this.mContext = mContext;
    }

    public static synchronized Fingerprint getInstance(Context context) {
        if (mFingerprint == null) {
            mFingerprint = new Fingerprint(context);
        }
        return mFingerprint;
    }

    private OnBiometricIdentifyCallback onBiometricIdentifyCallback;

    /**
     * 设置结果回调 接口
     */
    public void setOnBiometricIdentifyCallback(OnBiometricIdentifyCallback onBiometricIdentifyCallback) {
        this.onBiometricIdentifyCallback = onBiometricIdentifyCallback;
    }


    /*===================================对外公开的方法=======================================*/

    private boolean isSelfCancelled; // 是否 停止指纹监听

    private CancellationSignal cancellationSignal; // 取消信号

    private FingerprintManagerCompat fingerprintManager; // 指纹管理器
//    private FingerprintManager fingerprintManager; // 指纹管理器

    private KeyStore keyStore; // 密钥存储库

    /**
     * 判断是否支持指纹的逻辑
     */
    public boolean supportFingerprint() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            // M=23   6.0以下不支持指纹
            ToastUtils.showShort("您的系统版本太低，不支持指纹功能");
            return false;
        } else {
            KeyguardManager keyguardManager = mContext.getSystemService(KeyguardManager.class);
            fingerprintManager = FingerprintManagerCompat.from(mContext);
//            fingerprintManager = mContext.getSystemService(FingerprintManager.class);
            boolean b = fingerprintManager.isHardwareDetected();//判断设备是否支持指纹解锁

            if (!fingerprintManager.isHardwareDetected()) {
                ToastUtils.showShort("您的手机不支持指纹功能");
                return false;
            } else {
                if (!keyguardManager.isKeyguardSecure()) {
                    ToastUtils.showShort("您还未设置锁屏，请先设置锁屏并添加一个指纹");
                    return false;
                } else if (!fingerprintManager.hasEnrolledFingerprints()) {
                    ToastUtils.showShort("您至少需要在系统设置中添加一个指纹");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 第一步：
     * 新建一个KeyStore密钥库存放密钥
     * KeyGenerator密钥生成工具，成一个对称加密的key
     */
    public void initKey() {
        try {
            // 新建一个KeyStore密钥库存放密钥
            keyStore = KeyStore.getInstance(KEYSTORE_NAME);
            keyStore.load(null);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, KEYSTORE_NAME);

                KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder(DEFAUL_KEY_NAME,
                        KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                        .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                        .setUserAuthenticationRequired(true);

                keyGenerator.init(builder.build());
                keyGenerator.generateKey();

            } else {
                ToastUtils.showShort("您的系统版本太低，不支持指纹功能");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 第二步：生成一个 Cipher(密码)对象
     */

    public Cipher initCipher() {
        Cipher cipher = null;
        try {
//            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME, null);
            SecretKey key = (SecretKey) keyStore.getKey(DEFAUL_KEY_NAME, null);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
//                        + KeyProperties.KEY_ALGORITHM_RSA + "/"
                        + KeyProperties.BLOCK_MODE_CBC + "/"
                        + KeyProperties.ENCRYPTION_PADDING_PKCS7);
//                GCMParameterSpec spec = new GCMParameterSpec(128, cipher.getIV());
                cipher.init(Cipher.ENCRYPT_MODE | Cipher.DECRYPT_MODE, key);

                // 弹出验证指纹的 Dialog
//                showFingerPrintDialog(cipher);

                return cipher;
            } else {
                ToastUtils.showShort("您的系统版本太低，不支持指纹功能");
            }
        } catch (Exception e) {
            try {
                keyStore.deleteEntry(DEFAUL_KEY_NAME);
            } catch (KeyStoreException ex) {
                ex.printStackTrace();
            }
        }
        return cipher;
    }


    /**
     * 第三步：当界面可见时处理指纹认证监听，界面不可见时停止指纹认证的监听
     */

    /**
     * 开始指纹认证监听
     */
    public void startListening(Cipher cipher) {
        isSelfCancelled = false;
        cancellationSignal = new CancellationSignal();
//        fingerprintManager = FingerprintManagerCompat.from(mContext);

        /**
         * authenticate()方法接收五个参数，
         * 第一个参数是CryptoObject对象，传入Cipher对象就可以了。
         * 第二个参数是可选参数，官方的建议是直接传0就可以了。
         * 第三个参数是CancellationSignal对象，可以使用它来取消指纹认证操作。
         * 第四个参数用于接收指纹认证的回调，上述代码中将所有的回调可能都进行了界面提示。
         * 第五个参数用于指定处理回调的Handler，这里直接传null表示回调到主线程即可
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            fingerprintManager.authenticate(new FingerprintManagerCompat.CryptoObject(cipher),
                    0, cancellationSignal,
                    new FingerprintManagerCompat.AuthenticationCallback() {

                        // 处理了用户点击取消了指纹验证的逻辑
                        @Override
                        public void onAuthenticationError(int errorCode, CharSequence errString) {
                            if (!isSelfCancelled) {
                                if (errorCode == FingerprintManager.FINGERPRINT_ERROR_LOCKOUT) {
                                    ToastUtils.showShort(errString);
                                }
                            }
                        }

                        // 处理了授权帮助的提示
                        @Override
                        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
                            onBiometricIdentifyCallback.onError(helpMsgId, helpString.toString());
                            ToastUtils.showShort(helpString);
                        }

                        // 处理了授权成功后跳转到主页面
                        @Override
                        public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
                            onBiometricIdentifyCallback.onSucceeded();
                            ToastUtils.showShort("认证成功");
                        }

                        // 处理了授权失败再试一次的提示
                        @Override
                        public void onAuthenticationFailed() {
                            ToastUtils.showLong("指纹认证失败，请再试一次");
                        }
                    }, handler); //也可以置为null,系统自动处理
        }
    }

    /**
     * 处理callback接口后，界面的处理，默认是主线程handler
     */
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:   //验证错误
                    //todo 界面处理
                    handleErrorCode(msg.arg1);
                    break;
                case 2:   //验证成功
                    //todo 界面处理
                    cancellationSignal = null;
                    break;
                case 3:    //验证失败
                    //todo 界面处理
                    cancellationSignal = null;
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };


    /**
     * 对应不同的错误，可以有不同的操作
     */
    private void handleErrorCode(int code) {
        switch (code) {
            case FingerprintManager.FINGERPRINT_ERROR_CANCELED:
                //todo 指纹传感器不可用，该操作被取消
                break;
            case FingerprintManager.FINGERPRINT_ERROR_HW_UNAVAILABLE:
                //todo 当前设备不可用，请稍后再试
                break;
            case FingerprintManager.FINGERPRINT_ERROR_LOCKOUT:
                //todo 由于太多次尝试失败导致被锁，该操作被取消
                break;
            case FingerprintManager.FINGERPRINT_ERROR_NO_SPACE:
                //todo 没有足够的存储空间保存这次操作，该操作不能完成
                break;
            case FingerprintManager.FINGERPRINT_ERROR_TIMEOUT:
                //todo 操作时间太长，一般为30秒
                break;
            case FingerprintManager.FINGERPRINT_ERROR_UNABLE_TO_PROCESS:
                //todo 传感器不能处理当前指纹图片
                break;
        }
    }

    /**
     * 停止指纹认证监听
     */

    public void stopListening(Cipher cipher) {
        if (cancellationSignal != null) {
            cancellationSignal.cancel();
            cancellationSignal = null;
            isSelfCancelled = true;
        }
    }


    private void startActivity(String pcgName, String clsName) {
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName(pcgName, clsName);
        intent.setAction(Intent.ACTION_VIEW);
        mContext.startActivity(intent);
    }

}
