package com.example.customviewapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class BaseLayout extends ViewGroup {

    public BaseLayout(Context context) {
        super(context);
    }

    public BaseLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 计算内部布局（计算和保存）
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //调用每一个child View 的 measure 计算子View 大小
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            final LayoutParams lp = child.getLayoutParams();
            final int childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec, 0, lp.width);
            final int childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec, 0, lp.height);
            child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
        }
    }

    /**
     * 摆放子View
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        int startX = l;
        int startY = t;
        int offset = 100;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            child.layout(startX, startY, startX + child.getMeasuredWidth() + offset, startY + child.getMeasuredHeight() + offset);

            int endX = startX + child.getMeasuredWidth() + offset;
            if (endX > r) {
                //换行
                startX = l;
                startY = startY + child.getMeasuredHeight() + offset;
            } else {
                startX = endX;
            }

        }
    }

}
