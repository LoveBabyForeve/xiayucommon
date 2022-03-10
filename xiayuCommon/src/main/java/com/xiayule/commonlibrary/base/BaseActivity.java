package com.xiayule.commonlibrary.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.xiayule.commonlibrary.logcat.route.ActivityRouteManager;
import com.xiayule.commonlibrary.utlis.DateUtils;
import com.xiayule.commonlibrary.utlis.SPUtils;

import java.util.HashMap;

/**
 * @Description: java类作用描述
 * @Author: 下雨了
 * @CreateDate: 2021-01-04 10:47
 * @UpdateUser: 更新者
 * @UpdateDate: 2021-01-04 10:47
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public abstract class BaseActivity extends AppCompatActivity implements IBaseView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化上个页面传递的参数
        initParam();
        // 绑定 view
        setContentView(initContentView());
        // 初始化 View
        initView();
        // 初始化 ViewModel
        initViewModel();
        // 初始化 页面数据
        initData();
        // 初始化监听
        initListener();
        // 界面观察者的监听
        initViewObservable();
    }

    @Override
    protected void onResume() {
        super.onResume();
        HashMap<String, String> map = new HashMap<>();
        map.put("newRoute", this.getClass().getName());
        map.put("uid", SPUtils.getInstance().contains("userId") ? SPUtils.getInstance().getString("userId") : "");
        map.put("time", DateUtils.getCurrentTime());
        String str = new Gson().toJson(map);

        ActivityRouteManager.getInstance().saveActivityRoute(str);
    }

    /**
     * 初始化上个页面传递的参数
     */
    @Override
    public void initParam() {

    }

    /**
     * 绑定 view
     */
    public abstract int initContentView();

    /**
     * 初始化 View
     */
    @Override
    public void initView() {

    }

    /**
     * 初始化 页面数据
     */
    @Override
    public void initData() {

    }

    /**
     * 初始化监听
     */
    @Override
    public void initListener() {
    }

    /**
     * 初始化ViewModel相关
     */
    @Override
    public void initViewModel() {

    }

    /**
     * 初始化界面观察者的监听
     */
    @Override
    public void initViewObservable() {

    }

    /**
     * 创建ViewModel
     *
     * @param cls
     * @param <T>
     * @return
     */
    public <T extends ViewModel> T createViewModel(Class<T> cls) {
        return new ViewModelProvider(this).get(cls);
    }
    //----------------------------------------------------------------------------------------------------------------

    /**
     * 通过Class跳转界面
     *
     * @param cls 所跳转的目的Activity类
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 通过Class跳转界面
     *
     * @param cls 所跳转的目的Activity类
     * @param flags @Flags  Intent.FLAG_ACTIVITY_LAUNGH_ADJACENT
     *                      Intent.FLAG_ACTIVITY_NEW_TASK
     **/
    public void startActivity(Class<?> cls, int flags) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        intent.setFlags(flags);
        startActivity(intent);
    }

    /**
     * 含有Bundle通过Class跳转界面
     *
     * @param cls    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


    /**
     * 通过Class跳转界面
     *
     * @param cls         所跳转的目的Activity类
     * @param requestCode 请求 返回标识
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     *
     * @param cls         所跳转的目的Activity类
     * @param bundle      跳转所携带的信息
     * @param requestCode 请求 返回标识
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }
}
