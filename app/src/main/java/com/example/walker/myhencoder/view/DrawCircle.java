package com.example.walker.myhencoder.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 画圆
 * Created by Walker on 2017/7/11.
 */

public class DrawCircle extends View {

    Paint mPaint = new Paint();

    public DrawCircle(Context context) {
        super(context);
    }

    public DrawCircle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawCircle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(200, 200, 100, mPaint);

        // 设置为红色
        mPaint.setColor(Color.RED);
        canvas.drawCircle(600, 200, 120, mPaint);
        mPaint = new Paint();

        // Style 修改为画线模式
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(200, 600, 100, mPaint);
        mPaint = new Paint();

        // Style 修改为线条宽度
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(20); // 线条宽度为 20 像素
        canvas.drawCircle(600, 600, 120, mPaint);
        mPaint = new Paint();

        //抗锯齿  也可以Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        canvas.drawCircle(200, 900, 100, mPaint);
    }
}
