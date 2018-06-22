package com.example.walker.myhencoder.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Walker
 * @date on 2018/6/12 0012 下午 14:08
 * @email feitianwumu@163.com
 * @desc 更多文字绘制技巧
 */
public class DrawTextMore extends View {
    public DrawTextMore(Context context) {
        super(context);
    }

    public DrawTextMore(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawTextMore(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        TextPaint paint = new TextPaint();
        paint.setTextSize(48);
        String text1 = "StaticLayout 并不是一个View或ViewGroup而是 android.text.Layout的子类，它是纯粹用来绘制文字的。此处为文字设置宽度上限来让文字自动换行";
        StaticLayout staticLayout1 = new StaticLayout(text1, paint, 1200, Layout.Alignment.ALIGN_NORMAL, 1, 0, true);
        String text2 = " 用StaticLayout实现文字换行这里是\n 在换行符处主动换行";
        StaticLayout staticLayout2 = new StaticLayout(text2, paint, 1200, Layout.Alignment.ALIGN_NORMAL, 1, 0, true);

        canvas.save();
        canvas.translate(50, 100);
        staticLayout1.draw(canvas);
        canvas.translate(0, 300);
        staticLayout2.draw(canvas);
        canvas.restore();

        String text="设置文字大小";
        paint.reset();
        paint.setTextSize(18);
        canvas.drawText(text, 100, 600, paint);
        paint.setTextSize(36);
        canvas.drawText(text, 100, 660, paint);
        paint.setTextSize(60);
        canvas.drawText(text, 100, 750, paint);
        paint.setTextSize(84);
        canvas.drawText(text, 100, 900, paint);

        String text_2="设置字体,HenCoder" ;
        paint.reset();
        paint.setTextSize(60);
        paint.setTypeface(Typeface.DEFAULT);
        canvas.drawText(text_2, 100, 1100, paint);
        paint.setTypeface(Typeface.SERIF);
        canvas.drawText(text_2, 100, 1200, paint);
        paint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "Satisfy-Regular.ttf"));
        canvas.drawText(text_2, 100, 1300, paint);

        String text_3="获取文字的显示范围";
        paint.setStyle(Paint.Style.FILL);
        canvas.drawText(text_3, 100, 1500, paint);

        Rect bounds=new Rect();
        paint.getTextBounds(text_3, 0, text_3.length(), bounds);
        bounds.left += 100;
        bounds.top += 1500;
        bounds.right += 100;
        bounds.bottom += 1500;
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(bounds, paint);


        String text_4="测量文字的宽度并返回";
        paint.reset();
        paint.setTextSize(60);
        canvas.drawText(text_4, 100, 1600, paint);
        float textWidth = paint.measureText(text_4);
        canvas.drawLine(100, 1600, 100 + textWidth, 1600, paint);

        // 使用 Paint.measureText 测量出文字宽度，让文字可以相邻绘制
        String text_5="我今年瘦了" ;
        String text_6="40.5";
        String text_7="斤";
        TextPaint paint_2=new TextPaint();
        paint_2.setColor(Color.YELLOW);
        paint_2.setTextSize(90);
        canvas.drawText(text_5,100,1800,paint);
        float textWidth_1 = paint.measureText(text_5);
        canvas.drawText(text_6,100+textWidth_1,1800,paint_2);
        float textWidth_2 = paint_2.measureText(text_6);
        canvas.drawText(text_7,100+textWidth_1+textWidth_2,1800,paint);
    }
}
