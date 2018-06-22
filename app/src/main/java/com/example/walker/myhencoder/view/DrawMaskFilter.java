package com.example.walker.myhencoder.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.walker.myhencoder.R;

/**
 * @date on 2018/6/12 0012 上午 10:22
 * @author Walker
 * @email feitianwumu@163.com
 * @desc  附加效果
 */
public class DrawMaskFilter extends View {

    public DrawMaskFilter(Context context) {
        super(context);
    }

    public DrawMaskFilter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawMaskFilter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(), R.drawable.ic_what_the_fuck);
        Paint paint=new Paint();

        //模糊效果的 MaskFilter
        //radius 参数是模糊的范围， style 是模糊的类型。
        // 一共有四种：
        //NORMAL: 内外都模糊绘制
        //SOLID: 内部正常绘制，外部模糊
        //INNER: 内部模糊，外部不绘制
        //OUTER: 内部不绘制，外部模糊（）
        paint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.NORMAL));
        canvas.drawBitmap(bitmap, 100, 100, paint);

        //EmbossMaskFilter 浮雕效果的 MaskFilter
        // direction 是一个 3 个元素的数组，指定了光源的方向；
        // ambient 是环境光的强度，数值范围是 0 到 1；
        // specular 是炫光的系数； blurRadius 是应用光线的范围。
        paint.reset();
        paint.setMaskFilter(new EmbossMaskFilter(new float[]{0, 1, 1}, 0.2f, 8, 10));
        canvas.drawBitmap(bitmap, 700, 100, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(1200, 400);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(1200, heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, 400);
        }
    }
}
