package com.example.walker.myhencoder.view;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.walker.myhencoder.R;

/**
 * @author Walker
 * @date on 2018/6/14 0014 下午 15:12
 * @email feitianwumu@163.com
 * @desc PropertyValuesHolder 同一个动画中改变多个属性
 */
public class PropertyValuesHolderLayout extends RelativeLayout {
    View view;
    ObjectAnimator animator;

    public PropertyValuesHolderLayout(Context context) {
        super(context);
        init();
    }

    public PropertyValuesHolderLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PropertyValuesHolderLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        view = findViewById(R.id.viewShow);
        PropertyValuesHolder holder1 = PropertyValuesHolder.ofFloat("scaleX", 0, 1);
        PropertyValuesHolder holder2 = PropertyValuesHolder.ofFloat("scaleY", 0, 1);
        PropertyValuesHolder holder3 = PropertyValuesHolder.ofFloat("alpha", 0, 1);
        animator = ObjectAnimator.ofPropertyValuesHolder(view, holder1, holder2, holder3);
        animator.setDuration(3 * 1000);
        animator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        animator.end();
    }

}
