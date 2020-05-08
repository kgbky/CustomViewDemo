package com.example.customviewapplication;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.ScrollView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 重写onInterceptTouchEvent()实现嵌套滑动
 */
public class MyScrollView extends ScrollView {

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        super.onInterceptTouchEvent(ev);
        return true;
    }

    private int touch;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean isUp = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //  保存当前touch的纵坐标值
                touch = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                isUp = ev.getRawY() < touch;//上滑
                if (isUp) {
                    //scrollView 滑动完了 再传给 RecyclerView
                    ImageView iv = findViewById(R.id.iv_image);
                    Rect c = new Rect();
                    iv.getLocalVisibleRect(c);
                    if (iv.getHeight() - (c.bottom - c.top) > 20) {
                        RecyclerView re = findViewById(R.id.recyclerView);
                        re.dispatchTouchEvent(ev);
                        return false;
                    } else {
                        return true;
                    }
                } else {
                    //下滑
                    //RecyclerView 滑动完了 再给 ScrollView
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

}