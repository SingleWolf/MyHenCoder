package com.example.walker.myhencoder.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * @author Walker
 * @date on 2018/6/13 0013 下午 14:33
 * @email feitianwumu@163.com
 * @desc 在绘制主体前强制绘制背景
 */
public class BackTextView extends android.support.v7.widget.AppCompatTextView {
    public BackTextView(Context context) {
        super(context);
    }

    public BackTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BackTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.YELLOW);
        super.onDraw(canvas);
    }
}
