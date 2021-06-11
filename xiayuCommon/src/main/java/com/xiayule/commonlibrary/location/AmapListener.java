package com.xiayule.commonlibrary.location;

import com.amap.api.location.AMapLocation;

import java.util.concurrent.ExecutionException;

/**
 * @Description: 获取定位结果
 * @Author: 下雨了
 * @CreateDate: 2020/5/9 11:10
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/9 11:10
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public interface AmapListener {
    void onSuccess(AMapLocation aMapLocation) throws ExecutionException, InterruptedException;

    void onError(String s);
}
