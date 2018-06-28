package com.example.walker.myhencoder.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author Walker
 * @date on 2018/6/28 0028 上午 9:59
 * @email feitianwumu@163.com
 * @desc 签字板
 */
public class SignatureBoard extends View {

    private static final String TAG = "SignatureBoard";

    private Paint mPaint;
    private Path mPath;

    private float mPreX;
    private float mPreY;

    private boolean mIsSmooth;

    public SignatureBoard(Context context) {
        super(context);
        init(context, null);
    }

    public SignatureBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SignatureBoard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(20);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mPath = new Path();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(event.getX(), event.getY());
                if (mIsSmooth) {
                    mPreX = event.getX();
                    mPreY = event.getY();
                } else {
                    invalidate();
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if (mIsSmooth) {
                    float endX = (mPreX + event.getX()) / 2;
                    float endY = (mPreY + event.getY()) / 2;
                    mPath.quadTo(mPreX, mPreY, endX, endY);
                    mPreX = event.getX();
                    mPreY = event.getY();
                } else {
                    mPath.lineTo(event.getX(), event.getY());
                }
                invalidate();
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);

        canvas.drawPath(mPath, mPaint);
    }

    public void clear() {
        mPath.reset();
        postInvalidate();
    }

    public void setSmoothType(boolean isSmooth) {
        mIsSmooth = isSmooth;
    }
}
