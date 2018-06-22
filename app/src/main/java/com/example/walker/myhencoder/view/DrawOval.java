package com.example.walker.myhencoder.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 画椭圆
 * Created by Walker on 2017/7/11.
 */

public class DrawOval extends View {

    public DrawOval(Context context) {
        super(context);
    }

    public DrawOval(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawOval(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint=new Paint();
        paint.setStyle(Paint.Style.FILL);
        canvas.drawOval(50, 50, 350, 200, paint);//椭圆的左、上、右、下四个边界点的坐标

        paint.reset();
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawOval(400, 50, 700, 200, paint);

    }
}
