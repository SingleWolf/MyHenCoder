package com.example.walker.myhencoder.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 画矩形
 * Created by Walker on 2017/7/11.
 */

public class DrawRect extends View{
    private Paint mPaint;

    public DrawRect(Context context) {
        super(context);
    }

    public DrawRect(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawRect(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //实心矩形
        mPaint=new Paint();
        canvas.drawRect(100,100,200,200,mPaint);

        //实心矩形
        mPaint.reset();
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(400,100,600,200,mPaint);
    }
}
