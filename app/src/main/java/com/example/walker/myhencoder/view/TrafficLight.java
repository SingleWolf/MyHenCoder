package com.example.walker.myhencoder.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * @author Walker
 * @date on 2018/7/26 0026 下午 14:14
 * @email feitianwumu@163.com
 * @desc 红绿灯
 */
public class TrafficLight extends View {
    public static final int STATUS_RED = 1;
    public static final int STATUS_YELLOW = 2;
    public static final int STATUS_GREEN = 3;

    private static final String TAG = "TrafficLight";
    private int mSpace = 50;
    private Paint mPaint;
    private int mTrafficStatus;
    private ObjectAnimator mStatusAnimator;

    public TrafficLight(Context context) {
        super(context);
        init();
    }

    public TrafficLight(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TrafficLight(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);

        mStatusAnimator = ObjectAnimator.ofInt(this, "status", 0, 20);
        mStatusAnimator.setDuration(20 * 1000);
        mStatusAnimator.setInterpolator(new LinearInterpolator());
        mStatusAnimator.setRepeatCount(ObjectAnimator.INFINITE);

        mTrafficStatus = STATUS_RED;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mStatusAnimator != null) {
            mStatusAnimator.start();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mStatusAnimator != null) {
            mStatusAnimator.end();
        }
    }

    public void setStatus(int status) {
        if (status <= 10) {
            mTrafficStatus = STATUS_RED;
        } else if (status <= 13) {
            mTrafficStatus = STATUS_YELLOW;
        } else {
            mTrafficStatus = STATUS_GREEN;
        }
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(400, 1000);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(400, heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, 1000);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int paddingLeft = getPaddingLeft() + mSpace;
        int paddingRight = getPaddingRight() + mSpace;
        int paddingTop = getPaddingTop() + mSpace;
        int paddingBottom = getPaddingBottom() + mSpace;
        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;

        //绘制背景
        mPaint.setColor(Color.BLACK);
        canvas.drawRoundRect(paddingLeft, paddingTop, paddingLeft + width, paddingTop + height, 80, 80, mPaint);

        //绘制三个信号灯(未点亮状态)
        mPaint.setColor(Color.GRAY);
        float radius = (float) (0.3 * width);
        //灯与灯之间的纵向间距
        float topY = ((height - 6 * radius) / 4);
        //第一个信号灯
        canvas.drawCircle(width / 2 + paddingLeft, paddingTop + topY + radius, radius, mPaint);
        //第二个信号灯
        canvas.drawCircle(width / 2 + paddingLeft, paddingTop + 2 * topY + 3 * radius, radius, mPaint);
        //第三个信号灯
        canvas.drawCircle(width / 2 + paddingLeft, paddingTop + 3 * topY + 5 * radius, radius, mPaint);

        switch (mTrafficStatus) {
            case STATUS_RED:
                mPaint.setColor(Color.RED);
                canvas.drawCircle(width / 2 + paddingLeft, paddingTop + topY + radius, radius, mPaint);
                break;
            case STATUS_YELLOW:
                mPaint.setColor(Color.YELLOW);
                canvas.drawCircle(width / 2 + paddingLeft, paddingTop + 2 * topY + 3 * radius, radius, mPaint);
                break;
            case STATUS_GREEN:
            default:
                mPaint.setColor(Color.GREEN);
                canvas.drawCircle(width / 2 + paddingLeft, paddingTop + 3 * topY + 5 * radius, radius, mPaint);
                break;
        }

    }
}
