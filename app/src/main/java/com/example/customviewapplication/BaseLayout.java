package com.example.customviewapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class BaseLayout extends ViewGroup {

    private static final String TAG = "BaseLayoutTag";

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
     * 计算内部布局（计算和保存）会执行多次
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG, "onMeasure: ");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //调用每一个child View 的 measure 计算子View 大小
        int count = getChildCount();
        int height = 0;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            final LayoutParams lp = child.getLayoutParams();
            final int childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec, 0, lp.width);
            final int childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec, 0, lp.height);
            child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
            height += child.getMeasuredHeight();
        }
        heightMeasureSpec = resolveSize(height, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 摆放子View
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(TAG, "onLayout: ");
        int count = getChildCount();
        int startX = l;
        int startY = t;
        int offset = 100;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            int endX = startX + child.getMeasuredWidth();
            child.layout(startX, startY, endX, startY + child.getMeasuredHeight());

            if (endX >= r) {
                //换行
                startX = l;
                startY = startY + child.getMeasuredHeight();
            } else {
                startX = endX;
            }

        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw: ");
        super.onDraw(canvas);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        Log.d(TAG, "dispatchDraw: ");
        canvas.drawColor(Color.BLACK);
        super.dispatchDraw(canvas);
    }

    @Override
    public void onDrawForeground(Canvas canvas) {
        Log.d(TAG, "onDrawForeground: ");
        super.onDrawForeground(canvas);
    }

    /**
     * 分发 Touch 事件
     * <p>
     * View 与 ViewGroup 该方法的实现不同
     * <p>
     * ViewGroup 如果拦截了事件 会调用View类的dispatchTouchEvent() 在里面会调用自己的onTouchEvent()。
     * 此时ViewGroup就是一个普通的View
     * <p>
     * View类中的dispatchTouchEvent()返回的是onTouch()的返回值
     *
     * @return True if the event was handled(处理) by the view, false otherwise.
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d(TAG, "dispatchTouchEvent: " + event.getAction());
        return super.dispatchTouchEvent(event);
    }

    /**
     * 可拦截 Touch 事件
     * <p>
     * ViewGroup 类的方法，View类没有
     * <p>
     * dispatchTouchEvent() 方法内会调用改方法
     * <p>
     *
     * @return false 所有事件会先使用该方法处理，再传给 target's dispatchTouchEvent()。
     * <p>
     * true target view will receive the same(同一个) event but with the action {@link MotionEvent#ACTION_CANCEL}。
     * <p>
     * 后续事件直接通过 viewGroup 的 dispatchTouchEvent() 发给该 ViewGroup 的 onTouchEvent()，不再经过 onInterceptTouchEvent() 方法
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean b = super.onInterceptTouchEvent(ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN://0
                b = false;
                break;
            case MotionEvent.ACTION_MOVE://2
                b = true;
                break;
            case MotionEvent.ACTION_UP://1
                b = false;
                break;
        }
        Log.d(TAG, "onInterceptTouchEvent: " + ev.getAction() + " " + b);
        return b;
    }

    /**
     * dispatchTouchEvent() 方法内会调用改方法
     *
     * @return 如果消耗了事件，则为true，否则为false。
     * <p>
     * 若不消耗，则在同一事件序列中，当前View无法再次接收到事件。
     * <p>
     * 自定义处理，需要监听：down/move/up/cancel 事件
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean b = super.onTouchEvent(event);
        Log.d(TAG, "onTouchEvent: " + event.getAction() + " " + b);
        return b;
    }
}