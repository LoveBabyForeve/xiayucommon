package com.xiayule.commonlibrary.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;


import com.xiayule.commonlibrary.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Description: 自定义小头像相互叠加
 * @Author: 下雨了
 * @CreateDate: 2021-09-06 11:05
 * @UpdateUser: 更新者
 * @UpdateDate: 2021-09-06 11:05
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class PileView extends ViewGroup {
    /**
     * 两个子控件之间的垂直间隙
     */
    private float vertivalSpace = 0;
    /**
     * 重叠宽度
     */
    private float pileWidth = 10;
    /**
     * false 表示从右边增加，true表示从左边增加
     */
    private boolean flag = false;

    public float getVertivalSpace() {
        return vertivalSpace;
    }

    public PileView(Context context) {
        super(context);
    }

    public PileView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PileView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
    }


    private void initAttr(Context context, AttributeSet attrs) {
        @SuppressLint("CustomViewStyleable") TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PileLayout);
        vertivalSpace = ta.getDimension(R.styleable.PileLayout_PileLayout_vertivalSpace, dp2px(4));
        pileWidth = ta.getDimension(R.styleable.PileLayout_PileLayout_pileWidth, dp2px(10));
        ta.recycle();
    }

    /**
     * 测量视图及其内容以确定测量的宽度和高度。
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        //wrap_content
        int width = 0;
        int height = 0;

        // 每一行的 宽 高
        int lineWidth = 0;
        int lineHeight = 0;

        int rawWidth = 0;//当前行总宽度
        int rawHeight = 0;// 当前行高
        int rowIndex = 0;//当前行位置


        // 获取所有的子 View
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                if (i == childCount - 1) {
                    // 最后一个 child
                    height += rawHeight;
                    width = Math.max(width, rawWidth);
                }
                continue;
            }

            // 测量子view
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
            // 得到layoutparams
            MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            int childHeight = child.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
            if (lineWidth + childWidth - (rowIndex > 0 ? pileWidth : 0) > sizeWidth - getPaddingLeft() - getPaddingRight()) {
                // 换行判断
                height += lineHeight;
                // 新的行高
                lineHeight = childHeight;
                width = Math.max(width, lineWidth);
                lineWidth = childWidth;
                rowIndex = 0;
            } else {
                lineWidth += childWidth;
                if (rowIndex > 0) {
                    rawWidth -= pileWidth;
                }
                lineHeight = Math.max(lineHeight, childHeight);
            }

            if (i == childCount - 1) {
                width = Math.max(lineWidth, width);
                lineHeight += lineHeight;
            }

            rowIndex++;

        }
        setMeasuredDimension(modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width + getPaddingLeft() + getPaddingRight(),
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height + getPaddingTop() + getPaddingBottom());

    }

    /**
     * 存储所有的View
     */
    private List<List<View>> mAllViews = new ArrayList<List<View>>();
    /**
     * 每一行的高度
     */
    private List<Integer> mLineHeight = new ArrayList<Integer>();

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int width = getWidth();
        int lineWidth = 0;
        int lineHeight = 0;

        int rowIndex = 0; // 当前行位置
        @SuppressLint("DrawAllocation") List<View> lineViews = new ArrayList<>();
        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();
            MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();
            // 如果加上当前子View的宽度后超过了ViewGroup的宽度，就换行
            if (childWidth + lineWidth + lp.leftMargin + lp.rightMargin > width - getPaddingRight() - getPaddingLeft()) {
                mLineHeight.add(lineWidth);
                mAllViews.add(lineViews);
                lineViews = new ArrayList<>();
                lineWidth = 0;
                lineHeight = childHeight + lp.topMargin + lp.bottomMargin;
            }
            lineWidth += childWidth + lp.leftMargin + lp.rightMargin - pileWidth;
            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin + lp.bottomMargin);
            lineViews.add(childView);
        }

        // 处理最后一行
        mLineHeight.add(lineWidth);
        mAllViews.add(lineViews);


        // 设置子View的位置

        int leftOffset = getPaddingLeft();
        int topOffset = getPaddingTop();
        // 行数
        int lineNum = mAllViews.size();

        for (int i = 0; i < lineNum; i++) {
            lineViews = mAllViews.get(i);
            lineHeight = mLineHeight.get(i);

            //反序显示
            if (flag) {
                Collections.reverse(lineViews);
            }
            // Collections.reverse(lineViews);

            for (int j = 0; j < lineViews.size(); j++) {
                View child = lineViews.get(j);
                if (child.getVisibility() == View.GONE) {
                    continue;
                }

                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                int lc = leftOffset + lp.leftMargin;
                int tc = topOffset + lp.topMargin;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();

                child.layout(lc, tc, rc, bc);

                left += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin - pileWidth;
            }
            topOffset = getPaddingLeft();
            topOffset += lineHeight;
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    public float dp2px(float dpValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }

    // =================================================================================================================

    /**
     * 设置 两个子控件之间的垂直间隙
     *
     * @param vertivalSpace 垂直间隙
     */
    public void setVertivalSpace(float vertivalSpace) {
        this.vertivalSpace = vertivalSpace;
    }

    /**
     * 设置 重叠宽度
     *
     * @param pileWidth 宽度
     */
    public void setPileWidth(float pileWidth) {
        this.pileWidth = pileWidth;
    }

    /**
     * 设置 从左或者从右开始显示
     *
     * @param flag false 表示从右边增加，true表示从左边增加
     */
    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
