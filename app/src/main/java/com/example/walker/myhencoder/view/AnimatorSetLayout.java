package com.example.walker.myhencoder.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

import com.example.walker.myhencoder.R;

/**
 * @author Walker
 * @date on 2018/6/14 0014 下午 15:12
 * @email feitianwumu@163.com
 * @desc AnimatorSet 多个动画配合执行
 */
public class AnimatorSetLayout extends RelativeLayout {
    View view;
    AnimatorSet animatorSet;

    public AnimatorSetLayout(Context context) {
        super(context);
        init();
    }

    public AnimatorSetLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AnimatorSetLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        view = findViewById(R.id.viewShow);

        ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "alpha",  0,1);

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(view, "translationX", 0,600);
        animator2.setInterpolator(new DecelerateInterpolator());
        animator2.setDuration(1000);

        animatorSet = new AnimatorSet();
        animatorSet.playSequentially(animator1, animator2);
        animatorSet.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        animatorSet.end();
    }

}
