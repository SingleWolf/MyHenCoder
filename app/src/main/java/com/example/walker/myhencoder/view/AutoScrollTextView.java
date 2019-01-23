package com.example.walker.myhencoder.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * @author walker zheng
 * @date 2019/1/22
 * @desc 自滚动文本
 */
public class AutoScrollTextView extends AppCompatTextView {

    private float textLength = 0f;// 文本长度
    private float step = 0f;// 文本的横坐标
    private float y = 0f;// 文本的纵坐标
    public boolean isStarting = false;// 是否开始滚动
    private Paint paint = null;
    private String text = "";// 文本内容
    private OnScrollCompleteListener onScrollCompleteListener;//滚动结束监听

    public AutoScrollTextView(Context context) {
        this(context, null);
    }

    public AutoScrollTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoScrollTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        paint = getPaint();
        setSingleLine();
    }

    public void setOnScrollCompleteListener(OnScrollCompleteListener onScrollCompleteListener) {
        this.onScrollCompleteListener = onScrollCompleteListener;
    }


    public void setDisplayInfo() {
        text = getText().toString();
        textLength = paint.measureText(text);
        paint.setColor(Color.parseColor("#493B41"));
    }

    public void startScroll() {
        isStarting = true;
        invalidate();
    }

    public void stopScroll() {
        isStarting = false;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            try {
                y = ((float) getHeight() / 2 - getPaint().getFontMetrics().top / 2.0f - getPaint().getFontMetrics().bottom / 2.0f);
            } catch (Exception e) {
                y = getTextSize();
            }
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawText(text, -step, y, paint);
        if (!isStarting) {
            return;
        }
        step += 3.0;//文字的滚动速度
        //判断是否滚动结束
        if (step > textLength) {
            step = -15;
            if (onScrollCompleteListener != null) {
                onScrollCompleteListener.onScrollComplete();
            }
        }
        invalidate();
    }

    public interface OnScrollCompleteListener {
        void onScrollComplete();
    }
}

