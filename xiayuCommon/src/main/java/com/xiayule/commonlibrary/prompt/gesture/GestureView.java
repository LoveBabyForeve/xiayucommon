package com.xiayule.commonlibrary.prompt.gesture;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.xiayule.commonlibrary.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Description: 自定义 手势 View
 * @Author: 下雨了
 * @CreateDate: 2020/7/10 14:58
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/7/10 14:58
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class GestureView extends View {
    public GestureView(Context context) {
        super(context);
    }

    public GestureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private static final String TAG = "GestureView";

    /**
     * 格子数
     */
    private static final int POINT_COUNT = 9;

    /**
     * 中心点的半径
     */
    private static final int POINT_RADIUS = 25;

    /**
     * 外圈半径
     */
    private static final int CIRCLE_RADIUS = 85;

    /**
     * 最少连接的点数，否则提示错误
     */
    private static final int MIN_POINT_COUNT = 4;

    /**
     * 中心点颜色
     */
    private int pointColor = R.color.point_color;

    /**
     * 中心点颜色
     */
    private int pointCircleColor = R.color.point_color;

    /**
     * 中心点选择态颜色
     */
    private int pointSelectColor = R.color.point_select_color;

    /**
     * 圆圈的颜色
     */
    private int pointSelectCircleColor = R.color.point_select_circle_color;

    private Paint mPaint = new Paint();
    private Map<Integer, int[]> pointMap = new HashMap<>(10);
    /**
     * 记录选择过的点下标
     */
    private List<Integer> selectList = new ArrayList();
    private GestureListener listener;

    public void setListener(GestureListener listener) {
        this.listener = listener;
    }

    /**
     * 保存每个点对应的坐标到Map中
     */
    private void initPointMap() {
        if (pointMap.size() > 0) {
            return;
        }
        for (int i = 1; i <= POINT_COUNT; i++) {
            int[] location = getLocation(i);
            pointMap.put(i, location);
        }
    }

    /**
     * 获取某个点的具体坐标
     */
    private int[] getLocation(int index) {
        int width = getWidth();
        int height = getHeight();
        int[] location = new int[2];
        switch (index) {
            case 1:
                location[0] = CIRCLE_RADIUS;
                location[1] = CIRCLE_RADIUS;
                break;

            case 2:
                location[0] = width / 2;
                location[1] = CIRCLE_RADIUS;
                break;

            case 3:
                location[0] = width - CIRCLE_RADIUS;
                location[1] = CIRCLE_RADIUS;
                break;

            case 4:
                location[0] = CIRCLE_RADIUS;
                location[1] = height / 2;
                break;

            case 5:
                location[0] = width / 2;
                location[1] = height / 2;
                break;

            case 6:
                location[0] = width - CIRCLE_RADIUS;
                location[1] = height / 2;
                break;

            case 7:
                location[0] = CIRCLE_RADIUS;
                location[1] = height - CIRCLE_RADIUS;
                break;

            case 8:
                location[0] = width / 2;
                location[1] = height - CIRCLE_RADIUS;
                break;

            case 9:
                location[0] = width - CIRCLE_RADIUS;
                location[1] = height - CIRCLE_RADIUS;
                break;

            default:
                break;
        }
        return location;
    }

    /**
     * 设置点，圈，和连接线的颜色
     */
    public void setColor(int pointColor, int pointSelectColor, int circleColor) {
        if (pointColor > 0) {
            this.pointColor = pointColor;
        }
        if (pointSelectColor > 0) {
            this.pointSelectColor = pointSelectColor;
        }
        if (circleColor > 0) {
            this.pointSelectCircleColor = circleColor;
        }
    }

    /**
     * 恢复初始未选择的状态
     */
    public void reset() {
        setColor(0, R.color.point_select_color, R.color.point_select_circle_color);
        selectList.clear();
        invalidate();
    }

    /**
     * 显示错误，比如连结的点过少
     */
    public void showError() {
        setColor(0, R.color.error_point_select_color, R.color.error_circle_color);
        invalidate();
        postDelayed(new Runnable() {
            @Override
            public void run() {
                reset();
            }
        }, 500);
    }

    /**
     * 获取当前触摸的点
     */
    private int getSelectPoint(float touchX, float touchY) {
        Set<Map.Entry<Integer, int[]>> entries = pointMap.entrySet();
        for (Map.Entry<Integer, int[]> entry : entries) {
            int[] value = entry.getValue();
            boolean xOk = touchX > value[0] - CIRCLE_RADIUS && touchX < value[0] + CIRCLE_RADIUS;
            boolean yOk = touchY > value[1] - CIRCLE_RADIUS && touchY < value[1] + CIRCLE_RADIUS;
            if (xOk && yOk) {
                entry.getKey();
                return entry.getKey();
            }
        }
        return 0;
    }

    /**
     * 获取已经选择的点数量
     */
    private int getSelectPointCount() {
        if (null == selectList || selectList.isEmpty()) {
            return 0;
        }
        List<Integer> newList = new ArrayList<>();
        for (int i : selectList) {
            if (!newList.contains(i)) {
                newList.add(i);
            }
        }
        return newList.size();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (getSelectPointCount() < MIN_POINT_COUNT) {
                    showError();
                    if (null != listener) {
                        listener.onError();
                    }
                } else {
                    if (null != listener) {
                        listener.onFinish(selectList);
                    }
                }
                break;

            case MotionEvent.ACTION_DOWN:
                if (null != listener) {
                    listener.onStart();
                }

            case MotionEvent.ACTION_MOVE:
                int selectIndex = getSelectPoint(event.getX(), event.getY());
                if (selectIndex > 0 && !selectList.contains(selectIndex)) {
                    selectList.add(selectIndex);
                    invalidate();
                }
                break;

            default:
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initPointMap();

        for (int i = 1; i <= POINT_COUNT; i++) {
            int[] location = getLocation(i);

            if (selectList.contains(i)) {
                //画圆圈
                mPaint.setColor(getResources().getColor(pointSelectCircleColor));
                canvas.drawCircle(location[0], location[1], POINT_RADIUS, mPaint);

                //画圆圈边框
                mPaint.setColor(getResources().getColor(pointSelectColor));
                mPaint.setStrokeWidth(3);
                mPaint.setStyle(Paint.Style.STROKE);
                canvas.drawCircle(location[0], location[1], CIRCLE_RADIUS - 2, mPaint);
                mPaint.setStyle(Paint.Style.FILL);

                //画中心选择态的点
                canvas.drawCircle(location[0], location[1], POINT_RADIUS, mPaint);

                if (null != listener) {
                    listener.onDraw(i);
                }
            } else {
                //画未选择的点
                mPaint.setColor(getResources().getColor(pointColor));
                canvas.drawCircle(location[0], location[1], POINT_RADIUS, mPaint);
                // 绘制外圆
                mPaint.setColor(getResources().getColor(pointCircleColor));
                mPaint.setStrokeWidth(3);
                mPaint.setStyle(Paint.Style.STROKE);
                canvas.drawCircle(location[0], location[1], CIRCLE_RADIUS - 2, mPaint);
                mPaint.setStyle(Paint.Style.FILL);

            }
        }

        //画连线
        if (selectList.size() > 1) {
            mPaint.setStrokeWidth(4);
            mPaint.setColor(getResources().getColor(pointSelectColor));
            for (int i = 0; i + 1 < selectList.size(); i++) {
                float startX = getLocation(selectList.get(i))[0];
                float startY = getLocation(selectList.get(i))[1];
                float endX = getLocation(selectList.get(i + 1))[0];
                float endY = getLocation(selectList.get(i + 1))[1];
                canvas.drawLine(startX, startY, endX, endY, mPaint);
            }
        }
    }
}
