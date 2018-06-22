package com.example.walker.myhencoder.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * @author Walker
 * @date on 2018/6/13 0013 下午 14:50
 * @email feitianwumu@163.com
 * @desc 标记ImageView
 */
@SuppressLint("AppCompatCustomView")
public class MarkImageView extends ImageView {

    public MarkImageView(Context context) {
        super(context);
    }

    public MarkImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MarkImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);

        String mark="New";
        TextPaint textPaint=new TextPaint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(32);
        float textWidth=textPaint.measureText(mark);

        Rect bounds=new Rect();
        textPaint.getTextBounds(mark,0,mark.length(),bounds);

        Paint paint=new Paint();
        paint.setColor(Color.RED);
        canvas.drawRect(0,0,bounds.right+20,bounds.bottom+40,paint);
        canvas.drawText(mark,5,32,textPaint);
    }
}
