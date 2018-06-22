package com.example.walker.myhencoder.view;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
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
 * @date on 2018/6/14 0014 下午 16:14
 * @email feitianwumu@163.com
 * @desc 弹性进度
 */
public class SpringProgressBar extends View {
    private Paint paint;
    private TextPaint textPaint;
    float progress;
    int space;

    public SpringProgressBar(Context context) {
        super(context);
        init();
    }

    public SpringProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SpringProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @SuppressLint("ObjectAnimatorBinding")
    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(30);

        textPaint = new TextPaint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(60);
        textPaint.setTextAlign(Paint.Align.CENTER);

        space = 30;
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
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int paddingLeft = getPaddingLeft() + space;
        int paddingRight = getPaddingRight() + space;
        int paddingTop = getPaddingTop() + space;
        int paddingBottom = getPaddingBottom() + space;
        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;
        int x = getWidth() - paddingRight;
        int y = getHeight() - paddingBottom;
        int centerX = paddingLeft + width / 2;
        int centerY = paddingTop + height / 2;

        canvas.drawArc(paddingLeft, paddingTop, x, y, -225, progress * 2.7f, false, paint);

        String progressValue = (int)progress + "%";
        textPaint.measureText(progressValue);
        // 文字baseline在y轴方向的位置
        float baseLineY = Math.abs(textPaint.ascent() + textPaint.descent()) / 2;
        canvas.drawText(progressValue, centerX, centerY + baseLineY, textPaint);
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }
}
