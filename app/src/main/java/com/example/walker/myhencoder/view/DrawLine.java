package com.example.walker.myhencoder.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 画线
 * Created by Walker on 2017/7/11.
 */

public class DrawLine extends View{
    public DrawLine(Context context) {
        super(context);
    }

    public DrawLine(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawLine(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画线
        Paint paint=new Paint();
        paint.setColor(Color.BLUE);
        canvas.drawLine(200, 200, 800, 500, paint);

        paint.reset();
        paint.setColor(Color.RED);
        canvas.drawLine(800,500,1400,200,paint);

        //批量画线
        float[] points = {20, 20, 120, 20, 70, 20, 70, 120, 20, 120, 120, 120, 150, 20, 250, 20, 150, 20, 150, 120, 250, 20, 250, 120, 150, 120, 250, 120};
        paint.reset();
        canvas.drawLines(points, paint);
    }
}
