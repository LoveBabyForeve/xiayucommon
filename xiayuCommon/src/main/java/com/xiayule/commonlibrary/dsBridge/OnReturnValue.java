package com.xiayule.commonlibrary.dsBridge;

/**
 * @Description: 返回值
 * @Author: 下雨了
 * @CreateDate: 2020/6/11 10:55
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/6/11 10:55
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public interface OnReturnValue<T> {
    void onValue(T retValue);
}
