package com.xiayule.commonlibrary.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @Description: java类作用描述
 * @Author: 下雨了
 * @CreateDate: 2021-03-09 16:04
 * @UpdateUser: 更新者
 * @UpdateDate: 2021-03-09 16:04
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public abstract class BaseFragment extends Fragment implements IBaseView{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  初始化上个页面传递的参数
        initParam();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // 绑定 view
        View root = inflater.inflate(initContentView(inflater, container, savedInstanceState), container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 初始化 View
        initView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 初始化ViewModel相关
        initViewModel();
        // 初始化数据
        initData();
        // 初始化监听
        initListener();
        // 初始化界面观察者的监听
        initViewObservable();
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
    public abstract int initContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

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
    public void initViewModel() {}

    /**
     * 初始化界面观察者的监听
     */
    @Override
    public void initViewObservable() {

    }

    //----------------------------------------------------------------------------------------------------------------


    /**
     * 通过id获取当前view控件，需要在onViewCreated()之后的生命周期调用
     *
     * @param id
     * @param <T>
     * @return
     */
    public <T extends View> T findViewById(@IdRes int id) {
        return requireView().findViewById(id);
    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(getContext(), clz));
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(getContext(), clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }



}
