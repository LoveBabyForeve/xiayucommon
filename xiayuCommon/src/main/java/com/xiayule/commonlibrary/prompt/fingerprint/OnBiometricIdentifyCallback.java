package com.xiayule.commonlibrary.prompt.fingerprint;

/**
 * @Description: 返回指纹认证结果
 * @Author: 下雨了
 * @CreateDate: 2020/7/7 10:28
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/7/7 10:28
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public interface OnBiometricIdentifyCallback {
    // 认证成功
    void onSucceeded();

    // 认证失败错误码和原因
    void onError(int code, String reason);

    // 使用密码
    void onUsePassword();

    // 失败
    void onFailed();

    //取消
    void onCancel();

}
