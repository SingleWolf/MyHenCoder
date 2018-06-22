package com.example.walker.myhencoder.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 用Path画自定义图形
 * Created by Walker on 2017/7/11.
 */

public class DrawPath extends View {
    Paint paint = new Paint();
    Path path = new Path(); // 初始化 Path 对象

    public DrawPath(Context context) {
        super(context);
    }

    public DrawPath(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawPath(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 使用 path 对图形进行描述（这段描述代码不必看懂）
        paint.setStyle(Paint.Style.FILL);
        path.addArc(200, 200, 400, 400, -225, 225);
        path.arcTo(400, 200, 600, 400, -180, 225, false);
        path.lineTo(400, 542);
        canvas.drawPath(path, paint); // 绘制出 path 描述的图形（心形），大功告成

        //Path 方法第一类：直接描述路径。
        //这一类方法还可以细分为两组：添加子图形和画线（直线或曲线）

        //第一组：addXxx() ——添加子图形
        paint.reset();
        path.reset();
        path.addCircle(800, 600, 200, Path.Direction.CW);
        canvas.drawPath(path, paint);

        //第二组：xxxTo() ——画线（直线或曲线）
        paint.reset();
        path.reset();
        paint.setStyle(Paint.Style.STROKE);
        path.lineTo(10, 300); // 由当前位置 (0, 0) 向 (10, 300) 画一条直线
        path.rLineTo(100, 0); // 由当前位置 (100, 100) 向正右方 100 像素的位置画一条直线
        canvas.drawPath(path, paint);

        //可以通过 moveTo(x, y) 或 rMoveTo() 来改变当前位置，从而间接地设置这些方法的起点。
        paint.reset();
        path.reset();
        paint.setStyle(Paint.Style.STROKE);
        path.lineTo(600, 1000); // 画斜线
        path.moveTo(800, 1000); // 我移~~
        path.lineTo(800, 0); // 画竖线
        canvas.drawPath(path, paint);


        //第二组还有两个特殊的方法： arcTo() 和 addArc()。它们也是用来画线的，但并不使用当前位置作为弧线的起点。
        paint.reset();
        path.reset();
        paint.setStyle(Paint.Style.STROKE);
        path.lineTo(100, 100);
        path.arcTo(100, 100, 300, 300, -90, 0, true); // 强制移动到弧形起点（无痕迹）
        canvas.drawPath(path, paint);

        paint.reset();
        path.reset();
        paint.setStyle(Paint.Style.STROKE);
        path.lineTo(100, 100);
        path.arcTo(100, 100, 300, 300, -90, 0, false); // 直接连线连到弧形起点（有痕迹）
        canvas.drawPath(path, paint);

        //又是一个弧形的方法。一个叫 arcTo ，一个叫 addArc()，都是弧形，区别在哪里？
        // 其实很简单： addArc() 只是一个直接使用了 forceMoveTo = true 的简化版 arcTo() 。
        paint.reset();
        path.reset();
        paint.setStyle(Paint.Style.STROKE);
        path.lineTo(100, 100);
        path.addArc(100, 100, 300, 300, -90, 0);
        canvas.drawPath(path, paint);

        //close() 封闭当前子图形
        //close() 和 lineTo(起点坐标) 是完全等价的。
        //不是所有的子图形都需要使用close()来封闭。当需要填充图形时（即 Paint.Style 为 FILL 或 FILL_AND_STROKE），Path 会自动封闭子图形。
        paint.reset();
        path.reset();
        paint.setStyle(Paint.Style.STROKE);
        path.moveTo(100, 1000);
        path.lineTo(200, 1000);
        path.lineTo(150, 1150);
        path.close(); // 使用 close() 封闭子图形。等价于 path.lineTo(100, 100)
        canvas.drawPath(path, paint);

        // 这里只绘制了两条边，但由于 Style 是 FILL ，所以绘制时会自动封口
        paint.reset();
        path.reset();
        paint.setStyle(Paint.Style.FILL);
        path.moveTo(300, 1000);
        path.lineTo(500, 1000);
        path.lineTo(400, 1150);
        canvas.drawPath(path, paint);

        //Path 方法第二类：辅助的设置或计算
        //Path.setFillType(Path.FillType ft) 设置填充方式
        paint.reset();
        path.reset();
        //路径方向有两种：顺时针 (CW clockwise) 和逆时针 (CCW counter-clockwise) 。
        // 对于普通情况，这个参数填 CW 还是填 CCW 没有影响。
        // 它只是在需要填充图形 (Paint.Style 为 FILL 或 FILL_AND_STROKE) ，并且图形出现自相交时，用于判断填充范围的。
        path.setFillType(Path.FillType.EVEN_ODD);
        path.addCircle(400,1400,100,Path.Direction.CW);
        path.addCircle(500,1400,100,Path.Direction.CW);
        canvas.drawPath(path, paint);

        paint.reset();
        path.reset();
        path.setFillType(Path.FillType.INVERSE_EVEN_ODD);
        path.addCircle(800,1400,100,Path.Direction.CW);
        path.addCircle(900,1400,100,Path.Direction.CW);
        canvas.drawPath(path, paint);
    }
}
