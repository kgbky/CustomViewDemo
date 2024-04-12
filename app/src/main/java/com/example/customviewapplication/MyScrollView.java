package com.example.customviewapplication;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 重写onInterceptTouchEvent()实现嵌套滑动
 */
public class MyScrollView extends LinearLayout {
    private static final String TAG = "MyScrollViewTag";

    public MyScrollView(Context context) {
        this(context, null);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private RecyclerView child = null;
    private final int headViewHeight = 1200;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        child = findViewById(R.id.recyclerView);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //头部View隐藏之后，RecyclerView高度要等于屏幕高度
        int newH = MeasureSpec.makeMeasureSpec(headViewHeight + getScreenHeight(), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, newH);
    }

    private int getScreenHeight() {
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = ev.getY();
                startTranslationY = getTranslationY();
                break;
            case MotionEvent.ACTION_MOVE:
                boolean isUp = ev.getY() < downY;//手指向上滑 页面向下滚动
                if (isUp) {
                    intercept = getTranslationY() > -headViewHeight;
                } else {
                    intercept = !child.canScrollVertically(-1);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        Log.d(TAG, "onInterceptTouchEvent: " + ev.getAction() + " intercept = " + intercept);
        return intercept;
    }

    private float startTranslationY, downY;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float offset = ev.getY() - downY;//手指向上滑 页面向下滚动
                if (offset > 0) { // 下滑 减小偏移量
                    if (Math.abs(offset) > 12)
                        setTranslationY(Math.min(startTranslationY + offset, 0));
                } else {//上滑 加大偏移量
                    if (Math.abs(offset) > 12)
                        setTranslationY(Math.max(startTranslationY + offset, -headViewHeight));
                }
                break;
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        Log.d(TAG, "onTouchEvent: " + ev.getAction());
        return true;
    }

}