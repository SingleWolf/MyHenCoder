package com.example.walker.myhencoder.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.walker.myhencoder.R;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

/**
 * @author Walker
 * @e-mail feitianwumu@163.com
 * @date on 2018/9/28
 * @summary 数字跳动显示器
 */
public class NumberTicker extends View {

    private int DEFAULT_GAP = 30;
    private int DEFAULT_ANIMATOR_DURATION = 10000;
    private int DEFAULT_ANIMATOR_START_DELAY = 1000;

    private int mGap;
    private int mDuration = DEFAULT_ANIMATOR_DURATION;
    private int mStartDelay = DEFAULT_ANIMATOR_START_DELAY;
    private int mFromTop = 0;
    private float mTextSize = 48;
    private int mTextColor = Color.BLACK;
    private TextPaint mTextPaint;
    private char[] mChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private int mMoveIndex = 0;
    private ValueAnimator mValueAnimator;

    public NumberTicker(Context context) {
        super(context);
        init();
    }

    public NumberTicker(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public NumberTicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAttr(attrs, defStyleAttr);
        init();
    }

    private void setAttr(AttributeSet attrs, int defStyleAttr) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.NumberTicker, defStyleAttr, 0);
        mGap = a.getInt(R.styleable.NumberTicker_moveGap, DEFAULT_GAP);
        mDuration = a.getInt(R.styleable.NumberTicker_animDuration, DEFAULT_ANIMATOR_DURATION);
        mStartDelay = a.getInt(R.styleable.NumberTicker_startDelay, DEFAULT_ANIMATOR_START_DELAY);
        a.recycle();
    }

    private void init() {
        mTextPaint = new TextPaint(ANTI_ALIAS_FLAG);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
        mValueAnimator = ValueAnimator.ofInt(0, 20);
        mValueAnimator.setDuration(mDuration);
        mValueAnimator.setStartDelay(mStartDelay);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mMoveIndex = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mValueAnimator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mValueAnimator.end();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(50, 100);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(50, heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, 100);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mFromTop = 0;
        int height = getHeight();
        int width = getWidth();

//        if (20 <= mMoveIndex) {
//            String text = "6";
//            float textWidth = mTextPaint.measureText(text);
//            // 文字baseline在y轴方向的位置
//            float baseLineY = Math.abs(mTextPaint.ascent() + mTextPaint.descent()) / 2;
//            canvas.drawText(text, width / 2 - textWidth / 2, height / 2 + baseLineY, mTextPaint);
//            return;
//        }

        canvas.save();

        int moveIndex = mMoveIndex;
        while (mFromTop < height) {
            String text = String.valueOf(mChars[moveIndex % mChars.length]);
            float textWidth = mTextPaint.measureText(text);
            Rect bounds = new Rect();
            mTextPaint.getTextBounds(text, 0, 1, bounds);
            int textHeight = bounds.bottom - bounds.top;
            canvas.drawText(text, width / 2 - textWidth / 2, mFromTop + mGap, mTextPaint);
            mFromTop = mGap + textHeight + mFromTop;
            moveIndex++;
            canvas.translate(0, mGap);
        }
        canvas.restore();
    }
}
