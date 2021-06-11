package com.xiayule.commonlibrary.logcat.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * @Description: JsonObject合并
 * @Author: 下雨了
 * @CreateDate: 2020-12-23 16:42
 * @UpdateUser: 更新者
 * @UpdateDate: 2020-12-23 16:42
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class JsonMergeUtils {

    /**
     * 合并 JsonObject 为一个 JsonObject
     *
     * @param jsonObjects 要合并的 JsonObject
     * @return 返回 JsonObject
     */
    public static JsonObject mergeJsonObject(JsonObject... jsonObjects) {
        JsonObject target = new JsonObject();
        for (JsonObject jsonObject : jsonObjects) {
            for (String key : jsonObject.keySet()) {
                target.add(key, jsonObject.get(key));
            }
        }
        return target;
    }

    /**
     * 合并 Json串 为一个 Json串
     *
     * @param strings 要合并的 Json串
     * @return 返回 Json串
     */
    public static String mergeJsonObject(String... strings) {
        Gson gson = new Gson();
        JsonObject jsonObject = null;
        JsonObject target = new JsonObject();
        for (String str : strings) {
            jsonObject = gson.fromJson(str, JsonObject.class);
            for (String key : jsonObject.keySet()) {
                target.add(key, jsonObject.get(key));
            }
        }
        return new Gson().toJson(target);
    }
}
