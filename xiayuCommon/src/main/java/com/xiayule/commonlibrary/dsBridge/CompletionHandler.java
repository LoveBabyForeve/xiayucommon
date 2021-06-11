package com.xiayule.commonlibrary.dsBridge;

/**
 * @Description: 完成处理器
 * @Author: 下雨了
 * @CreateDate: 2020/6/11 10:48
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/6/11 10:48
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public interface CompletionHandler<T> {
    void complete(T retValue);

    void complete();

    void setProgressData(T value);
}
