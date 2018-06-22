package com.example.walker.myhencoder.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.walker.myhencoder.R;

/**
 * @author Walker
 * @date on 2018/6/12 0012 下午 16:54
 * @email feitianwumu@163.com
 * @desc Canvas对绘制的辅助(范围裁切和几何变换)
 */
public class DrawCanvasHelp extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;
    Camera camera = new Camera();

    public DrawCanvasHelp(Context context) {
        super(context);
        init();
    }


    public DrawCanvasHelp(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawCanvasHelp(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_batman);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //范围裁切
        //clipRect
        canvas.save();
        canvas.clipRect(200, 200, 600, 400);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        canvas.restore();

        //clipPath
        Path path=new Path();
        path.addCircle(260,100,70, Path.Direction.CW);
        canvas.save();
        canvas.clipPath(path);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        canvas.restore();

        path.reset();
        path.addCircle(440,100,70, Path.Direction.CW);
        canvas.save();
        canvas.clipPath(path);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        canvas.restore();

        //几何变换
        //canvas.translate
        canvas.save();
        canvas.translate(800, 50);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        canvas.restore();

        //matrix
        paint.reset();
        canvas.drawBitmap(bitmap, 0, 600, paint);

        Matrix matrix=new Matrix();
        matrix.preRotate(45, 800, 600);
        canvas.save();
        canvas.concat(matrix);
        canvas.drawBitmap(bitmap, 800, 600, paint);
        canvas.restore();

        //Camera
        paint.reset();
        canvas.drawBitmap(bitmap, 0, 1200, paint);
        canvas.save();

        camera.save(); // 保存 Camera 的状态
//        camera.setLocation(0,0,5);
        camera.rotateX(30); // 旋转 Camera 的三维空间
        canvas.translate(1000, 1400); // 旋转之后把投影移动回来
        camera.applyToCanvas(canvas); // 把旋转投影到 Canvas
        canvas.translate(-1000, -1400); // 旋转之前把绘制内容移动到轴心（原点）
        camera.restore(); // 恢复 Camera 的状态

        canvas.drawBitmap(bitmap, 800, 1200, paint);
        canvas.restore();
    }
}
