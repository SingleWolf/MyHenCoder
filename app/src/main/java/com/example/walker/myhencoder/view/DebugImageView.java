package com.example.walker.myhencoder.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * @author Walker
 * @date on 2018/6/13 0013 下午 13:49
 * @email feitianwumu@163.com
 * @desc debug辅助imageView
 */
@SuppressLint("AppCompatCustomView")
public class DebugImageView extends ImageView {
    private TextPaint textPaint;

    public DebugImageView(Context context) {
        super(context);
        init();
    }

    public DebugImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DebugImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        textPaint = new TextPaint();
        textPaint.setTextSize(32);
        textPaint.setColor(Color.WHITE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(400, 400);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(400, heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, 400);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float density = getResources().getDisplayMetrics().density;
        //int weight = (int) (getDrawable().getIntrinsicWidth() / density);
        //int height = (int) (getDrawable().getIntrinsicHeight() / density);
        int weight = (int) (getDrawable().getBounds().width() / density);
        int height = (int) (getDrawable().getBounds().height() / density);
        String size = String.format("size:%ddpx%ddp", weight, height);
        canvas.drawText(size, 28, getHeight()-28, textPaint);
    }
}
