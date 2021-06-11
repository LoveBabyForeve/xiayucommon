package com.xiayule.commonlibrary.http.network;

/**
 * @Description: 网络错误异常
 * @Author: 下雨了
 * @CreateDate: 2020-12-24 16:51:46
 * @UpdateUser: 更新者
 * @UpdateDate: 2020-12-24 16:51:46
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class NetworkException extends Exception {

    public int code;
    public String message;

    public NetworkException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }



}
