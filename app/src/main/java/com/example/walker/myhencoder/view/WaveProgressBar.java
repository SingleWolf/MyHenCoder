package com.example.walker.myhencoder.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * @author Walker
 * @date on 2018/6/27 0027 下午 15:21
 * @email feitianwumu@163.com
 * @desc 波纹进度条
 */
public class WaveProgressBar extends View {
    private static final String TAG = "WaveProgressBar";
    private Paint mPaint;
    private TextPaint mTextPaint;
    int progress;
    int mSpace = 10;
    private ObjectAnimator mProgressAnimator;

    private Path mWavePath;
    private Paint mWavePaint;
    private int mItemWaveLength;
    private int mWaveOriginX;
    private int mWaveOriginY;
    private Path mCutPath;

    private ValueAnimator mWaveAnimator;

    public WaveProgressBar(Context context) {
        super(context);
        init();
    }

    public WaveProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WaveProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @SuppressLint("ObjectAnimatorBinding")
    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(20);

        mTextPaint = new TextPaint();
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(60);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mProgressAnimator = ObjectAnimator.ofInt(this, "progress", 0, 100);
        mProgressAnimator.setDuration(5 * 1000);
        mProgressAnimator.setInterpolator(new LinearInterpolator());
        progress = 0;

        mWavePath = new Path();
        mWavePaint = new Paint();
        mWavePaint.setColor(Color.parseColor("#87CEEB"));
        mWavePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mItemWaveLength = 200;
        setWaveAnimator();

        mCutPath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(400, 400);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(400, heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, 400);
        }
    }


    private void setWaveAnimator() {
        mWaveAnimator = ValueAnimator.ofInt(0, mItemWaveLength);
        mWaveAnimator.setDuration(2000);
        mWaveAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mWaveAnimator.setInterpolator(new LinearInterpolator());
        mWaveAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mWaveOriginX = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mProgressAnimator.start();
        mWaveAnimator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mProgressAnimator.end();
        mWaveAnimator.end();
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
        int radius = Math.min(width, height) / 2;
        int x = getWidth() - paddingRight;
        int y = getHeight() - paddingBottom;
        int centerX = paddingLeft + width / 2;
        int centerY = paddingTop + height / 2;

        //裁切
        mCutPath.addCircle(centerX, centerY, radius, Path.Direction.CW);
        canvas.save();
        canvas.clipPath(mCutPath);

        canvas.drawColor(Color.parseColor("#483D8B"));
        //绘制水波纹
        mWaveOriginY = height - height * progress / 100;
        drawWave(canvas);

        mPaint.setColor(Color.BLUE);
        canvas.drawCircle(centerX, centerY, radius, mPaint);

        mPaint.setColor(Color.WHITE);
        canvas.drawArc(paddingLeft, paddingTop, x, y, -90, progress * 3.6f, false, mPaint);

        String progressValue = progress + "%";
        mTextPaint.measureText(progressValue);
        // 文字baseline在y轴方向的位置
        float baseLineY = Math.abs(mTextPaint.ascent() + mTextPaint.descent()) / 2;
        canvas.drawText(progressValue, centerX, centerY + baseLineY, mTextPaint);

        canvas.restore();

        if(progress==100){
            mWaveAnimator.end();
        }
    }

    private void drawWave(Canvas canvas) {
        mWavePath.reset();
        int halfWaveLen = mItemWaveLength / 2;
        mWavePath.moveTo(-mItemWaveLength + mWaveOriginX, mWaveOriginY);
        for (int i = -mItemWaveLength; i <= getWidth() + mItemWaveLength; i += mItemWaveLength) {
            mWavePath.rQuadTo(halfWaveLen / 2, -50, halfWaveLen, 0);
            mWavePath.rQuadTo(halfWaveLen / 2, 50, halfWaveLen, 0);
        }
        mWavePath.lineTo(getWidth(), getHeight());
        mWavePath.lineTo(0, getHeight());
        mWavePath.close();

        canvas.drawPath(mWavePath, mWavePaint);
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }
}
