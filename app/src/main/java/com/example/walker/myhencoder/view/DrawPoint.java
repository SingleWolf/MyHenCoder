package com.example.walker.myhencoder.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 画点
 * Created by Walker on 2017/7/11.
 */

public class DrawPoint extends View{

    public DrawPoint(Context context) {
        super(context);
    }

    public DrawPoint(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawPoint(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画圆头点
        Paint paint=new Paint();
        paint.setStrokeWidth(20);
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawPoint(50, 500, paint);

        //画平头点
        paint.reset();
        paint.setStrokeWidth(30);
        paint.setStrokeCap(Paint.Cap.BUTT);
        canvas.drawPoint(300, 500, paint);

        //画方头点
        paint.reset();
        paint.setStrokeWidth(50);
        paint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawPoint(600, 500, paint);

        //批量画点
        float[] points = {0, 0, 50, 50, 50, 100, 100, 50, 100, 100, 150, 50, 150, 100};
        // 绘制四个点：(50, 50) (50, 100) (100, 50) (100, 100)
        paint.reset();
        paint.setStrokeWidth(20);
        canvas.drawPoints(points, 2 /* 跳过两个数，即前两个 0 */, 8 /* 一共绘制八个坐标*/, paint);
    }
}
