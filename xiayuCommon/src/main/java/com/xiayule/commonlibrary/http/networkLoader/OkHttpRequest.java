package com.xiayule.commonlibrary.http.networkLoader;

import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

/**
 * @Description: java类作用描述
 * @Author: 下雨了
 * @CreateDate: 2019/12/16 11:35
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/12/16 11:35
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class OkHttpRequest implements IHttpRequest {
    @Override
    public void get(String url, Map<String, Object> params, ICallBack iCallBack) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        StringBuffer stringBuffer = new StringBuffer(url);
        if (params != null && !params.isEmpty()) {
            stringBuffer.append("?");
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                stringBuffer.append(entry.getKey())
                        .append("=")
                        .append(entry.getValue().toString())
                        .append("&");
            }
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        }

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().get().url(stringBuffer.toString()).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    Log.e("OkHttpRequest", "json--->" + json);
                }
            }
        });
    }

    @Override
    public void post(String url, Map<String, Object> params, ICallBack iCallBack) {

        RequestBody requestBody = new RequestBody() {
            @Override
            public MediaType contentType() {
                return null;
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {

            }
        };
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().post(requestBody).url(url).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    Log.e("OkHttpRequest", "json--->" + json);
                }
            }
        });
    }
}
