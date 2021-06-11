package com.xiayule.commonlibrary.http.networkLoader;

import java.util.Map;


/**
 * @Description: java类作用描述
 * @Author: 下雨了
 * @CreateDate: 2019/12/16 11:31
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/12/16 11:31
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * <p>
 * Map<String,Object> params = new HashMap<>();
 * params.put("key","71e58b5b2f930eaf1f937407acde08fe");
 * params.put("num",20);
 * HttpRequestProxy.getInstance().get("http://api.tianapi.com/social/", params, new HttpCallBack<Personal>() {
 * @Override public void onSuccess(Personal personal) {
 * <p>
 * }
 * @Override public void onFail(Exception e) {
 * super.onFail(e);
 * }
 * });
 */
public class HttpRequestProxy implements IHttpRequest {

    private static IHttpRequest iHttpRequest;

    public HttpRequestProxy() {
    }

    private static class Holder {
        private static final HttpRequestProxy INSTANCE = new HttpRequestProxy();
    }

    public static HttpRequestProxy getInstance() {
        return Holder.INSTANCE;
    }

    public void init(NetRequestType netRequestType) {
        switch (netRequestType) {
            case OKHTTP:
                iHttpRequest = new OkHttpRequest();
                break;
            case XUTIL:
//                iHttpRequest = new XUtilsRequest();
                break;
            case VOLLEY:
//                iHttpRequest = new VolleyRequest();
                break;
        }
    }

    @Override
    public void get(String url, Map<String, Object> params, ICallBack iCallBack) {
        if (iHttpRequest != null) {
            iHttpRequest.get(url, params, iCallBack);
        }
    }

    @Override
    public void post(String url, Map<String, Object> params, ICallBack iCallBack) {
        if (iHttpRequest != null) {
            iHttpRequest.post(url, params, iCallBack);
        }
    }
}
