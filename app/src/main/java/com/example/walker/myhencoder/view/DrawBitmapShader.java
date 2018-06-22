package com.example.walker.myhencoder.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ComposeShader;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.walker.myhencoder.R;

/**
 * @author Walker
 * @date on 2018/6/12 0012 上午 9:44
 * @email feitianwumu@163.com
 * @desc 用Bitmap来着色
 */
public class DrawBitmapShader extends View {
    public DrawBitmapShader(Context context) {
        super(context);
    }

    public DrawBitmapShader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawBitmapShader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_batman);
        //bitmap：用来做模板的 Bitmap 对象
        //tileX：横向的 TileMode
        //tileY：纵向的 TileMode。
        Shader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(shader);

        canvas.drawCircle(200, 200, 200, paint);

        //ComposeShader 混合着色器
        Shader shader_1 = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        Bitmap bitmap_2 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_batman_logo);
        Shader shader_2 = new BitmapShader(bitmap_2, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        // ComposeShader：结合两个 Shader
        //PorterDuff.Mode 一共有 17 个，可以分为两类：Alpha 合成 (Alpha Compositing)和混合 (Blending)
        Shader composeShader = new ComposeShader(shader_1, shader_2, PorterDuff.Mode.SRC_OVER);
        paint.reset();
        paint.setShader(composeShader);
        canvas.drawCircle(600, 200, 200, paint);

        Shader composeShader_2 = new ComposeShader(shader_1, shader_2, PorterDuff.Mode.DST_OUT);
        paint.reset();
        paint.setShader(composeShader_2);
        canvas.drawCircle(1000, 200, 200, paint);
    }
}
