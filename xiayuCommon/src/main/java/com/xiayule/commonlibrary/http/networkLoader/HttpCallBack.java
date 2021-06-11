package com.xiayule.commonlibrary.http.networkLoader;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @Description: java类作用描述
 * @Author: 下雨了
 * @CreateDate: 2019/12/24 16:35
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/12/24 16:35
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public abstract class HttpCallBack<Result> implements ICallBack {
    @Override
    public void onSuccess(String json) {
        Gson gson = new Gson();
        Class<?> clazz = getClassType(this);
        Result obj = (Result) gson.fromJson(json, clazz);
        onSuccess(obj);
    }

    @Override
    public void onFail(Exception e) {

    }

    public abstract void onSuccess(Result obj);

    private Class<?> getClassType(Object object) {
        if (object == null) {
            return null;
        }
        Type type = object.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) type).getActualTypeArguments();
        return (Class<?>) params[0];
    }
}
