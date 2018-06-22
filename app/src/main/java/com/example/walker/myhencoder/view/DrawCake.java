package com.example.walker.myhencoder.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Walker
 * @date on 2018/6/11 0011 下午 13:48
 * @email feitianwumu@163.com
 * @desc 画饼状图
 */
public class DrawCake extends View {

    public DrawCake(Context context) {
        super(context);
    }

    public DrawCake(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawCake(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(Color.YELLOW);

        int rX = 10;
        int rY = -10;

        canvas.drawArc(200 + rX, 200 + rY, 600 + rX, 600 + rY, -120, 120, true, paint);

        rX = 0;
        rY = 0;
        paint.setColor(Color.BLUE);
        canvas.drawArc(200 + rX, 200 + rY, 600 + rX, 600 + rY, 0, 45, true, paint);

        paint.setColor(Color.RED);
        canvas.drawArc(200 + rX, 200 + rY, 600 + rX, 600 + rY, 45, 60, true, paint);

        paint.setColor(Color.GREEN);
        canvas.drawArc(200 + rX, 200 + rY, 600 + rX, 600 + rY, 105, 30, true, paint);

        paint.setColor(Color.WHITE);
        canvas.drawArc(200 + rX, 200 + rY, 600 + rX, 600 + rY, 135, 105, true, paint);
    }
}
