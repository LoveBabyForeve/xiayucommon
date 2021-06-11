package com.xiayule.commonlibrary.http.networkLoader;

import java.util.Map;

/**
 * @Description: 请求抽象出来
 * @Author: 下雨了
 * @CreateDate: 2019/12/16 11:28
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/12/16 11:28
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public interface IHttpRequest {

    void get(String url, Map<String, Object> params, ICallBack iCallBack);

    void post(String url, Map<String, Object> params, ICallBack iCallBack);

}
