package com.xiayule.commonlibrary.base;

/**
 * @Description: java类作用描述
 * @Author: 下雨了
 * @CreateDate: 2021-04-23 14:05
 * @UpdateUser: 更新者
 * @UpdateDate: 2021-04-23 14:05
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public interface IBaseView {

    /**
     * 初始化界面传递参数
     */
    void initParam();

    /**
     * 初始化 View
     */
    void initView();

    /**
     * 初始化数据
     */
    void initData();

    /**
     * 初始化监听
     */
    void initListener();

    /**
     * 初始化ViewModel相关
     */
    void initViewModel();

    /**
     * 初始化界面观察者的监听
     */
    void initViewObservable();
}
