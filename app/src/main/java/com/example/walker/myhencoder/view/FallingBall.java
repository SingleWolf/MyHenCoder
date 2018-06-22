package com.example.walker.myhencoder.view;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.PointFEvaluator;
import android.animation.TypeEvaluator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;

import com.example.walker.myhencoder.model.PointBean;

/**
 * @author Walker
 * @date on 2018/6/14 0014 下午 14:35
 * @email feitianwumu@163.com
 * @desc 落体小球
 */
public class FallingBall extends View {
    private ObjectAnimator animator;
    Paint paint;
    PointBean point;

    public FallingBall(Context context) {
        super(context);
        init(context, null);
    }

    public FallingBall(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FallingBall(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @SuppressLint("ObjectAnimatorBinding")
    private void init(Context context, AttributeSet attrs) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0xffff0000);

        point = new PointBean(5, 5);
        PointBean endValue = new PointBean(800, 1200);
        animator = ObjectAnimator.ofObject(this, "point", new PointEvaluator(), point, endValue);
        animator.setInterpolator(new BounceInterpolator());
        animator.setEvaluator(new PointEvaluator());
        animator.setDuration(5 * 1000);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        animator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        animator.end();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(1200, 1200);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(1200, heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, 1200);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(point.getX(), point.getY(), 50, paint);
    }

    public void setPoint(PointBean point) {
        this.point.setX(point.getX());
        this.point.setY(point.getY());
        invalidate();
    }

    /**
     * 自定义TypeEvaluator
     */
    private class PointEvaluator implements TypeEvaluator<PointBean> {

        @Override
        public PointBean evaluate(float fraction, PointBean startValue, PointBean endValue) {
            float distanceX = endValue.getX() - startValue.getX();
            float distanceY = endValue.getY() - startValue.getY();

            return new PointBean(fraction * distanceX + startValue.getX(), fraction * distanceY + startValue.getX());
        }
    }
}
