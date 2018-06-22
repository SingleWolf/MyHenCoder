package com.example.walker.myhencoder.view;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * @author Walker
 * @date on 2018/6/13 0013 下午 17:08
 * @email feitianwumu@163.com
 * @desc 自定义进度条
 */
public class CustomProgressBar extends View {
    private Paint paint;
    private TextPaint textPaint;
    int progress;
    int space = 10;
    private ObjectAnimator objectAnimator;

    public CustomProgressBar(Context context) {
        super(context);
        init();
    }

    public CustomProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @SuppressLint("ObjectAnimatorBinding")
    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20);

        textPaint = new TextPaint();
        textPaint.setColor(Color.BLUE);
        textPaint.setTextSize(48);
        textPaint.setTextAlign(Paint.Align.CENTER);

        objectAnimator = ObjectAnimator.ofInt(this, "progress", 0, 360);
        objectAnimator.setDuration(5 * 1000);
        objectAnimator.setInterpolator(new LinearInterpolator());
        progress = 0;
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

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        objectAnimator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        objectAnimator.end();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int paddingLeft = getPaddingLeft() + space;
        int paddingRight = getPaddingRight() + space;
        int paddingTop = getPaddingTop() + space;
        int paddingBottom = getPaddingBottom() + space;
        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;
        int radius = Math.min(width, height) / 2;
        int x = getWidth() - paddingRight;
        int y = getHeight() - paddingBottom;
        int centerX = paddingLeft + width / 2;
        int centerY = paddingTop + height / 2;

        paint.setColor(Color.GRAY);
        canvas.drawCircle(centerX, centerY, radius, paint);

        paint.setColor(Color.GREEN);
        canvas.drawArc(paddingLeft, paddingTop, x, y, -90, progress, false, paint);

        String progressValue;
        if (progress == 360) {
            progressValue = "100%";
        } else {
            int value = progress * 100 / 360;
            progressValue = value + "%";
        }


        textPaint.measureText(progressValue);
        // 文字baseline在y轴方向的位置
        float baseLineY = Math.abs(textPaint.ascent() + textPaint.descent()) / 2;
        canvas.drawText(progressValue, centerX, centerY + baseLineY, textPaint);
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }
}
