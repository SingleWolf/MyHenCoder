package com.example.walker.myhencoder.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.walker.myhencoder.R;

/**
 * @author Walker
 * @date on 2018/6/13 0013 上午 9:54
 * @email feitianwumu@163.com
 * @desc 翻页效果
 */
public class DrawFlipboardView extends View {

    Paint paint;
    Bitmap bitmap;
    Camera camera;
    int degree;
    ObjectAnimator animator;

    public DrawFlipboardView(Context context) {
        super(context);
        init();
    }

    public DrawFlipboardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawFlipboardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @SuppressLint("ObjectAnimatorBinding")
    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        camera = new Camera();
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_batman);

        animator = ObjectAnimator.ofInt(this, "degree", 0, 180);
        animator.setDuration(2500);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        animator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        animator.end();
    }

    @SuppressWarnings("unused")
    public void setDegree(int degree) {
        this.degree = degree;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(1200, 1200);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(1200, heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, 1200);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int x = centerX - bitmapWidth / 2;
        int y = centerY - bitmapHeight / 2;

        // 第一遍绘制：上半部分
        canvas.save();
        canvas.clipRect(0, 0, getWidth(), centerY);
        canvas.drawBitmap(bitmap, x, y, paint);
        canvas.restore();

        // 第二遍绘制：下半部分
        canvas.save();

        if (degree < 90) {
            canvas.clipRect(0, centerY, getWidth(), getHeight());
        } else {
            canvas.clipRect(0, 0, getWidth(), centerY);
        }
        camera.save();
        camera.rotateX(degree);
        canvas.translate(centerX, centerY);//旋转之后把投影移动回来
        camera.applyToCanvas(canvas);//把旋转投影到 Canvas
        canvas.translate(-centerX, -centerY);//旋转之前把绘制内容移动到轴心（原点）
        //Canvas 的几何变换顺序是反的，所以要把移动到轴心的代码写在下面，把从中心移动回来的代码写在上面。
        camera.restore();

        canvas.drawBitmap(bitmap, x, y, paint);
        canvas.restore();
    }
}
