package com.example.walker.myhencoder.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 画圆角矩形
 * Created by Walker on 2017/7/11.
 */

public class DrawRoundRect extends View{
    public DrawRoundRect(Context context) {
        super(context);
    }

    public DrawRoundRect(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawRoundRect(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画圆角矩形
        Paint paint=new Paint();
        paint.setColor(Color.BLUE);
        canvas.drawRoundRect(100, 100, 500, 300, 50, 50, paint);

        paint.reset();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20);
        canvas.drawRoundRect(100, 600, 800, 800, 50, 50, paint);
    }
}
