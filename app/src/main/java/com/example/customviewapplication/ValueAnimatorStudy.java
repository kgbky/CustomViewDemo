package com.example.customviewapplication;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Point;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

/**
 * Created by admin on 2023/9/5    17:58
 * <p>
 * 学习 Android 属性动画
 */
public class ValueAnimatorStudy {
    //定义：通过修改对象的属性 实现动画
    //特性：时长、插值器、重复次数、反向播放、合并动画
    //默认 10ms 刷新一次，可修改。但是刷新时间最终取决于系统

    //动画工作原理：
    //1、计算 动画分数 = (动画已播放时间 / 动画总时长)。>=0 & <=1。线性的。
    //2、计算 插值分数。使用插值器(TimeInterpolator) 把 动画分数 映射为一个新值
    //3、计算 属性值。调用TypeEvaluator计算！属性值 = startValue + 插值分数 * (endValue - startValue)

    public static void studyValueAnimator() {
        // ValueAnimator 负责计算属性值！把计算后的值设置到属性，需要自己实现。

        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 10);
//        valueAnimator.setRepeatCount(4);
//        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            int count = 1;

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //在测试机上 16ms 执行一次
                Log.d(TAG, "onAnimationUpdate: " + animation.getAnimatedValue() + " execute count = " + count);
                count++;
            }
        });
        valueAnimator.start();
    }

    public static void customValueAnimator(final View target) {
        ValueAnimator animation = ValueAnimator.ofObject(new MyTypeEvaluator(), new Point(100, 100), new Point(600, 600));
        animation.setDuration(10000);
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //在测试机上 16ms 执行一次
                Log.d(TAG, "onAnimationUpdate: " + animation.getAnimatedValue());
                Point p = (Point) animation.getAnimatedValue();
                target.setTranslationX(p.x);
                target.setTranslationY(p.y);
            }
        });
        animation.start();
    }

    public static void studyObjectAnimator(final View target) {
        //第二个参数为属性名称，要更新该属性，target必须具有 setter属性 函数
        //有时候可能需要搭配 invalidate() 使用
        ObjectAnimator oC = ObjectAnimator.ofFloat(target, "translationX", 0f, 800f, 400f, 800f);
        oC.setDuration(10000);
        oC.start();
    }

    public static void studyAnimatorSet() {
        Animator bounceAnim = null;
        Animator squashAnim1 = null;
        Animator squashAnim2 = null;
        Animator stretchAnim1 = null;
        Animator stretchAnim2 = null;
        Animator bounceBackAnim = null;

        AnimatorSet bouncer = new AnimatorSet();
        bouncer.play(bounceAnim).before(squashAnim1);//1、播放 bounceAnim
        bouncer.play(squashAnim1).with(squashAnim2);//2、同时播放 squashAnim1、squashAnim2、stretchAnim1 和 stretchAnim2
        bouncer.play(squashAnim1).with(stretchAnim1);
        bouncer.play(squashAnim1).with(stretchAnim2);
        bouncer.play(bounceBackAnim).after(stretchAnim2);//3、播放 bounceBackAnim

        ValueAnimator fadeAnim = ObjectAnimator.ofFloat(null, "alpha", 1f, 0f);
        fadeAnim.setDuration(250);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(bouncer).before(fadeAnim);//4、播放 fadeAnim。AnimatorSet 对象可相互嵌套
        animatorSet.start();
    }

    public static void studyKeyFrame(final View target) {
        //指定关键帧
        Keyframe kf0 = Keyframe.ofFloat(0f, 0f);
        Keyframe kf1 = Keyframe.ofFloat(.5f, 360f);
        Keyframe kf2 = Keyframe.ofFloat(1f, 0f);
        kf1.setInterpolator(new AccelerateInterpolator());
        kf2.setInterpolator(new AccelerateInterpolator());
        PropertyValuesHolder pvhRotation = PropertyValuesHolder.ofKeyframe("translationX", kf0, kf1, kf2);
        ObjectAnimator rotationAnim = ObjectAnimator.ofPropertyValuesHolder(target, pvhRotation);
        rotationAnim.setDuration(5000);
        rotationAnim.start();
    }

    public static void studyViewPropertyAnimator(final View target) {
        target.animate().x(50f).y(100f);
    }

    private static final String TAG = "ValueAnimatorStudyTag";

    private static class MyTypeEvaluator implements TypeEvaluator<Point> {
        @Override
        public Point evaluate(float fraction, Point startValue, Point endValue) {
            float x = startValue.x + fraction * (endValue.x - startValue.x);
            float y = startValue.y + fraction * (endValue.y - startValue.y);
            return new Point((int) x, (int) y);
        }
    }

}
