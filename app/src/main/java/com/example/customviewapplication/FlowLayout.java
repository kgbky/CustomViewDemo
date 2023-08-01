package com.example.customviewapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

/**
 * Created by admin on 2023/7/18    16:10
 */
public class FlowLayout extends ViewGroup {

    private static final String TAG = "FlowLayoutTag";

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private final HashMap<Integer, Integer> map = new HashMap<>();
    private final HashMap<Integer, Pair<Integer, Integer>> mapPair = new HashMap<>();

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG, "onMeasure: ");//会执行2次
        assert (MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.UNSPECIFIED);
        map.clear();
        mapPair.clear();

        final int width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        int height = MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop() - getPaddingBottom();
        final int count = getChildCount();

        int xPos = getPaddingLeft();
        int yPos = getPaddingTop();

        int childHeightMeasureSpec;
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {
            childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.AT_MOST);
        } else {
            childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }


        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                child.measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST), childHeightMeasureSpec);
                final int childW = child.getMeasuredWidth();
                final int childH = child.getMeasuredHeight();
                map.put(i, childH);

                if (xPos + childW > width) {//换行逻辑
                    xPos = getPaddingLeft();
                    yPos += map.get(i - 1);
                }
                mapPair.put(i, new Pair<>(xPos, yPos));
                xPos += childW;
            }
        }
        setMeasuredDimension(width, yPos + map.get(count - 1));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(TAG, "onLayout: ");//执行1次
        final int count = getChildCount();
        int xpos;
        int ypos;

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                final int childw = child.getMeasuredWidth();
                final int childh = child.getMeasuredHeight();
                xpos = mapPair.get(i).first;
                ypos = mapPair.get(i).second;
                child.layout(xpos, ypos, xpos + childw, ypos + childh);
            }
        }
    }
}