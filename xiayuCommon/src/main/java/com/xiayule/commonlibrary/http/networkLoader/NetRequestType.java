package com.xiayule.commonlibrary.http.networkLoader;

/**
 * @Description: java类作用描述
 * @Author: 下雨了
 * @CreateDate: 2019/12/16 11:36
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/12/16 11:36
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public enum NetRequestType {
    OKHTTP(0), XUTIL(1), VOLLEY(2);
    private int type;

    NetRequestType(int type) {
        this.type = type;
    }


}
