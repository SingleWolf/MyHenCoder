package com.example.walker.myhencoder.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * @date on 2018/6/13 0013 下午 14:38
 * @author Walker
 * @email feitianwumu@163.com
 * @desc  重写ViewGroup的dispatchDraw（）方法
 */
public class SpottedLinearLayout extends LinearLayout {
    public SpottedLinearLayout(Context context) {
        super(context);
    }

    public SpottedLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SpottedLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        int width=getWidth();
        int height=getHeight();

        Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);

        paint.setColor(Color.YELLOW);
        canvas.drawCircle(20,50,20,paint);

        paint.reset();
        paint.setColor(Color.RED);
        canvas.drawCircle(width/2+40,height/2+60,30,paint);

        paint.reset();
        paint.setColor(Color.GREEN);
        canvas.drawCircle(60,height-100,25,paint);
    }
}
