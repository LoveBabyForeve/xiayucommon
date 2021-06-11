package com.xiayule.commonlibrary.http.network.interceptor;

import com.google.gson.Gson;
import com.xiayule.commonlibrary.logcat.network.NetworkErrorManager;
import com.xiayule.commonlibrary.utlis.DateUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;


/**
 * @Description: 错误日志拦截器
 * @Author: 下雨了
 * @CreateDate: 2020-12-24 11:50
 * @UpdateUser: 更新者
 * @UpdateDate: 2020-12-24 11:50
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class NetErrorInterceptor implements Interceptor {

    private static final String TAG = "ParamsLogInterceptor";


    @Override
    public Response intercept(Chain chain) throws IOException {
        // 请求
        Request request = chain.request();
        // 响应
        Response response = chain.proceed(chain.request());

        MediaType mediaType = response.body().contentType();
        String content = response.body().string();

        printInfoToFile(request, response, content);

        return response.newBuilder().body(ResponseBody.create(mediaType, content)).build();
    }

    /**
     * 进行判断是否要写入日志文件
     *
     * @param request  请求
     * @param response 响应
     */
    private void printInfoToFile(Request request, Response response, String content) throws IOException {
        HashMap<String, String> map = new HashMap<>();
        JSONObject object = null;
        try {
            object = new JSONObject(content);
            if (!object.getString("status").equals("200")) {
                map.put("path", request.toString());
                map.put("errorType", object.getString("status"));
                map.put("parameters", getParams(request.body()));
                map.put("errorTime", DateUtils.getCurrentTime());
                map.put("errorReason", object.getString("message"));
                map.put("errorIsRemote", "true");
                String str = new Gson().toJson(map);
                NetworkErrorManager.getInstance().saveNetworkError(str);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private String getParams(RequestBody requestBody) {
        if (requestBody == null) {
            return "";
        }
        Buffer buffer = new Buffer();
        try {
            requestBody.writeTo(buffer);
            Charset charset = StandardCharsets.UTF_8;
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(StandardCharsets.UTF_8);
            }
            String params = buffer.readString(charset);

            return params;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}

