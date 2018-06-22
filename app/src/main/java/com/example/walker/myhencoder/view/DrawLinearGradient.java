package com.example.walker.myhencoder.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Walker
 * @date on 2018/6/11 0011 下午 17:34
 * @email feitianwumu@163.com
 * @desc 线性渐变
 */
public class DrawLinearGradient extends View {
    public DrawLinearGradient(Context context) {
        super(context);
    }

    public DrawLinearGradient(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawLinearGradient(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        //x0 y0 x1 y1：渐变的两个端点的位置 color0 color1 是端点的颜色 tile：端点范围之外的着色规则，类型是 TileMode。
        // TileMode 一共有 3 个值可选： CLAMP, MIRROR 和 REPEAT。CLAMP 会在端点之外延续端点处的颜色；MIRROR 是镜像模式；REPEAT 是重复模式。
        Shader shader = new LinearGradient(0, 0, 400, 400, Color.parseColor("#E91E63"), Color.parseColor("#3F51B5"), Shader.TileMode.CLAMP);
        paint.setShader(shader);
        canvas.drawCircle(200, 200, 200, paint);

        paint.reset();
        Shader shader2 = new LinearGradient(400, 0, 600, 400, Color.parseColor("#E91E63"), Color.parseColor("#3F51B5"), Shader.TileMode.MIRROR);
        paint.setShader(shader2);
        canvas.drawCircle(600, 200, 200, paint);

        paint.reset();
        Shader shader3 = new LinearGradient(1000, 0, 1200, 400, Color.parseColor("#E91E63"), Color.parseColor("#3F51B5"), Shader.TileMode.REPEAT);
        paint.setShader(shader3);
        canvas.drawCircle(1000, 200, 200, paint);
    }
}
