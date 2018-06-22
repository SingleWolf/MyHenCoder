package com.example.walker.myhencoder.view;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.walker.myhencoder.R;

/**
 * @author Walker
 * @date on 2018/6/14 0014 下午 15:12
 * @email feitianwumu@163.com
 * @desc PropertyValuesHolders.ofKeyframe() 把同一个属性拆分
 */
public class SpringProgressView extends RelativeLayout {
    SpringProgressBar view;
    ObjectAnimator animator;

    public SpringProgressView(Context context) {
        super(context);
        init();
    }

    public SpringProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SpringProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        view = (SpringProgressBar) findViewById(R.id.progressBar);
        Keyframe keyframe1 = Keyframe.ofFloat(0, 0); // 开始：progress 为 0
        Keyframe keyframe2 = Keyframe.ofFloat(0.5f, 100); // 进行到一半是，progres 为 100
        Keyframe keyframe3 = Keyframe.ofFloat(1, 80); // 结束时倒回到 80
        PropertyValuesHolder holder = PropertyValuesHolder.ofKeyframe("progress", keyframe1, keyframe2, keyframe3);

        animator = ObjectAnimator.ofPropertyValuesHolder(view, holder);
        animator.setDuration(2000);
        //animator.setInterpolator(new FastOutSlowInInterpolator());
        animator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        animator.end();
    }

}
