package com.xiayule.commonlibrary.prompt.gesture;

import java.util.List;

/**
 * @Description: GestureListener接口
 * @Author: 下雨了
 * @CreateDate: 2020/7/10 15:02
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/7/10 15:02
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public interface GestureListener {
    void onStart();

    void onDraw(int index);

    void onFinish(List<Integer> list);

    void onError();
}
