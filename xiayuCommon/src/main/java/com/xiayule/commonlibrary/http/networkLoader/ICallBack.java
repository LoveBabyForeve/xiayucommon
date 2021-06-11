package com.xiayule.commonlibrary.http.networkLoader;

/**
 * @Description: 回调
 * @Author: 下雨了
 * @CreateDate: 2019/12/16 11:29
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/12/16 11:29
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public interface ICallBack {
    void onSuccess(String json);

    void onFail(Exception e);
}
