package com.xiayule.commonlibrary.http.download.interceptor;


import com.xiayule.commonlibrary.http.download.ProgressResponseBody;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * @Description: java类作用描述
 * @Author: 下雨了
 * @CreateDate: 2020/7/8 15:04
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/7/8 15:04
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ProgressInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        return originalResponse.newBuilder()
                .body(new ProgressResponseBody(originalResponse.body()))
                .build();
    }
}
