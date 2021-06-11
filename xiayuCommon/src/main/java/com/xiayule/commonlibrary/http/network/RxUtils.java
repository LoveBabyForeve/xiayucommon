package com.xiayule.commonlibrary.http.network;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 封装线程的相关操作
 */


public class RxUtils {

    //封装 Rx 线程切换的相关操作
    public static <T> ObservableTransformer<T, T> rxSchedulerHelper() {
        return new ObservableTransformer() {
            @Override
            public ObservableSource apply(Observable observable) {
                return observable.subscribeOn(Schedulers.io()) //http请求线程
                        .observeOn(AndroidSchedulers.mainThread()); //回调线程
            }
        };
    }
}
