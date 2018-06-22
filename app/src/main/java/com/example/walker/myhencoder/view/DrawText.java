package com.example.walker.myhencoder.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 绘制文字
 * Created by Walker on 2017/7/11.
 */

public class DrawText extends View {

    public DrawText(Context context) {
        super(context);
    }

    public DrawText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        String text = "yeah , your name is 33 !";
        Paint paint=new Paint();
        paint.setTextSize(18);
        canvas.drawText(text, 100, 25, paint);
        paint.setTextSize(36);
        canvas.drawText(text, 100, 70, paint);
        paint.setTextSize(60);
        canvas.drawText(text, 100, 145, paint);
        paint.setTextSize(84);
        canvas.drawText(text, 100, 240, paint);

    }
}
