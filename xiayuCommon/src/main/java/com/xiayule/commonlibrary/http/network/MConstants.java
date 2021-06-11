package com.xiayule.commonlibrary.http.network;


/**
 * @Description: 请求常量 通用版 可根据业务进行修改
 * @Author: 下雨了
 * @CreateDate: 2020/6/11 10:55
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/6/11 10:55
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

public class MConstants {


    public static final int CODE_SUCCESS = 200;
    public static final int CODE_ERROR = 500;

    //请求成功, 消息提示
    public static final int CODE_220 = 220;
    //请求失败，不打印Message
    public static final int CODE_300 = 300;
    //请求失败，打印Message
    public static final int CODE_330 = 330;
    //服务器内部异常
    public static final int CODE_500 = 500;
    //参数为空
    public static final int CODE_503 = 503;
    //没有数据
    public static final int CODE_502 = 502;
    //无效的Token
    public static final int CODE_510 = 510;
    //未登录
    public static final int CODE_530 = 530;
    //请求的操作异常终止：未知的页面类型
    public static final int CODE_551 = 551;

    public static final int CODE_501 = 501;

}
