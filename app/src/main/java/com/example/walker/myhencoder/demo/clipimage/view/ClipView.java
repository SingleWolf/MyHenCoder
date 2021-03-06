package com.example.walker.myhencoder.demo.clipimage.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Xfermode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.example.walker.myhencoder.demo.clipimage.Direction;


/**
 * @Author Walker
 * @Date 2019-11-27 15:52
 * @Summary 裁剪框（支持圆、矩形、九宫格），可以按照比例自定义裁剪框的宽高
 */
public class ClipView extends View {

    private static final String TAG = "ClipView";

    public static final int TYPE_ROUND = 1;
    public static final int TYPE_RECT = 2;
    public static final int TYPE_PALACE = 3;

    private static final float DEFAULT_SCALE_HW = 0.6f;

    private Paint paint = new Paint();
    //画裁剪区域边框的画笔
    private Paint borderPaint = new Paint();
    private TextPaint textPaint;

    //裁剪框水平方向间距
    private float mHorizontalPadding;
    //裁剪框边框宽度
    private int clipBorderWidth;
    //裁剪圆框的半径
    private int clipRadiusWidth;
    //裁剪框矩形宽度
    private int clipWidth;
    //裁剪框矩形高度
    private int clipHeight;

    //裁剪框类别，（圆形、矩形），默认为圆形
    private ClipType clipType = ClipType.CIRCLE;
    private Xfermode xfermode;
    private Context context;

    // 九宫格相关
    //裁剪框边框画笔
    private Paint mPalaceBorderPaint;
    //裁剪框九宫格画笔
    private Paint mGuidelinePaint;
    //绘制裁剪边框四个角的画笔
    private Paint mCornerPaint;
    private float mCornerThickness;
    private float mBorderThickness;
    //四个角小短边的长度
    private float mCornerLength;
    //裁剪框的高宽比
    private float mScaleClipHW;

    private String message;
    private boolean isMessagePortrait = true;
    private Direction mMessageDirection;

    public ClipView(Context context) {
        this(context, null);
    }

    public ClipView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClipView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //去锯齿
        paint.setAntiAlias(true);
        paint.setStyle(Style.FILL);
        borderPaint.setStyle(Style.STROKE);
        borderPaint.setColor(Color.WHITE);
        borderPaint.setStrokeWidth(clipBorderWidth);
        borderPaint.setAntiAlias(true);

        textPaint = new TextPaint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(30);
        textPaint.setTextAlign(Paint.Align.CENTER);

        xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
        this.context = context;
        initData();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.i(TAG, "onDraw: clipType =" + clipType);

        //通过Xfermode的DST_OUT来产生中间的透明裁剪区域，一定要另起一个Layer（层）
        canvas.saveLayer(0, 0, this.getWidth(), this.getHeight(), null, Canvas.ALL_SAVE_FLAG);

        //设置背景
        canvas.drawColor(Color.parseColor("#a8000000"));
        paint.setXfermode(xfermode);
        //绘制圆形裁剪框
        if (clipType == ClipType.CIRCLE) {
            //中间的透明的圆
            canvas.drawCircle(this.getWidth() / 2, this.getHeight() / 2, clipRadiusWidth, paint);
            //白色的圆边框
            canvas.drawCircle(this.getWidth() / 2, this.getHeight() / 2, clipRadiusWidth, borderPaint);
        } else if (clipType == ClipType.RECTANGLE) { //绘制矩形裁剪框
            //绘制中间白色的矩形蒙层
            canvas.drawRect(this.getWidth() / 2 - clipWidth / 2, this.getHeight() / 2 - clipHeight / 2,
                    this.getWidth() / 2 + clipWidth / 2, this.getHeight() / 2 + clipHeight / 2, paint);
            //绘制白色的矩形边框
            canvas.drawRect(this.getWidth() / 2 - clipWidth / 2, this.getHeight() / 2 - clipHeight / 2,
                    this.getWidth() / 2 + clipWidth / 2, this.getHeight() / 2 + clipHeight / 2, borderPaint);
        } else if (clipType == ClipType.PALACE) {
            //绘制中间的矩形
            canvas.drawRect(this.getWidth() / 2 - clipWidth / 2, this.getHeight() / 2 - clipHeight / 2,
                    this.getWidth() / 2 + clipWidth / 2, this.getHeight() / 2 + clipHeight / 2, paint);

            Rect clipRect = getClipRect();
            //绘制裁剪边框
            drawBorder(canvas, clipRect);
            //绘制九宫格引导线
            drawGuidelines(canvas, clipRect);
            //绘制裁剪边框的四个角
            drawCorners(canvas, clipRect);
        }
        drawMessage(canvas);
        //出栈，恢复到之前的图层，意味着新建的图层会被删除，新建图层上的内容会被绘制到canvas (or the previous layer)
        canvas.restore();
    }

    /**
     * 获取裁剪区域的Rect
     *
     * @return
     */
    public Rect getClipRect() {
        Rect rect = new Rect();
        if (clipType == ClipType.CIRCLE) {
            //宽度的一半 - 圆的半径
            rect.left = (this.getWidth() / 2 - clipRadiusWidth);
            //宽度的一半 + 圆的半径
            rect.right = (this.getWidth() / 2 + clipRadiusWidth);
            //高度的一半 - 圆的半径
            rect.top = (this.getHeight() / 2 - clipRadiusWidth);
            //高度的一半 + 圆的半径
            rect.bottom = (this.getHeight() / 2 + clipRadiusWidth);
        } else {
            //宽度的一半 - 圆的半径
            rect.left = (this.getWidth() / 2 - clipWidth / 2);
            //宽度的一半 + 圆的半径
            rect.right = (this.getWidth() / 2 + clipWidth / 2);
            //高度的一半 - 圆的半径
            rect.top = (this.getHeight() / 2 - clipHeight / 2);
            //高度的一半 + 圆的半径
            rect.bottom = (this.getHeight() / 2 + clipHeight / 2);
        }
        return rect;
    }

    /**
     * 设置裁剪框边框宽度
     *
     * @param clipBorderWidth
     */
    public void setClipBorderWidth(int clipBorderWidth) {
        this.clipBorderWidth = clipBorderWidth;
        borderPaint.setStrokeWidth(clipBorderWidth);
        invalidate();
    }

    /**
     * 设置裁剪框水平间距
     *
     * @param horizontalPadding 水平间距
     */
    public void setHorizontalPadding(float horizontalPadding) {
        this.mHorizontalPadding = horizontalPadding;
        int screenWidth = getScreenWidth(getContext());
        int screenHeight = getScreenHeight(getContext());
        this.clipRadiusWidth = (int) (Math.min(screenWidth, screenHeight) - 2 * mHorizontalPadding) / 2;
        this.clipWidth = (int) (Math.min(screenWidth, screenHeight) - 2 * mHorizontalPadding);
        this.clipHeight = (int) (clipWidth * mScaleClipHW);
        Log.i(TAG, String.format("screenWidth:%d  screenHeight:%d  clipWidth:%d  clipHeight:%d  clipRadiusWidth:%d ", screenWidth, screenHeight, clipWidth, clipHeight, clipRadiusWidth));
    }

    /**
     * 因为屏幕方向发生变化，重新刷新布局
     *
     * @param isPortrait 是否为竖屏
     */
    public void refreshOrientationChanged(boolean isPortrait) {
        isMessagePortrait = isPortrait;
        if (isPortrait) {
            mMessageDirection.setTop();
        } else {
            mMessageDirection.setRight();
        }
        resetWidthByOrientation(isPortrait);
        postInvalidate();
    }

    private void resetWidthByOrientation(boolean isPortrait) {
        int screenWidth = getScreenWidth(getContext());
        int screenHeight = getScreenHeight(getContext());
        if (isPortrait) {//portrait
            this.clipRadiusWidth = (int) (Math.min(screenWidth, screenHeight) - 2 * mHorizontalPadding) / 2;
            this.clipWidth = (int) (Math.min(screenWidth, screenHeight) - 2 * mHorizontalPadding);
            this.clipHeight = (int) (clipWidth * mScaleClipHW);
        } else {//landscape
            this.clipRadiusWidth = (int) (Math.min(screenWidth, screenHeight) - 2 * mHorizontalPadding) / 2;
            this.clipHeight = (int) (Math.min(screenWidth, screenHeight) - mHorizontalPadding);
            this.clipWidth = (int) (clipHeight * mScaleClipHW);
        }
        Log.i(TAG, String.format("resetWidthByOrientation->screenWidth:%d  screenHeight:%d  clipWidth:%d  clipHeight:%d  clipRadiusWidth:%d ", screenWidth, screenHeight, clipWidth, clipHeight, clipRadiusWidth));

    }

    /**
     * 设置裁剪框水平间距(A4纸效果)
     *
     * @param horizontalPadding 水平间距
     */
    public void setHorizontalPaddingForA4(float horizontalPadding) {
        this.mHorizontalPadding = horizontalPadding;
        int screenWidth = getScreenWidth(getContext());
        int screenHeight = getScreenHeight(getContext());
        this.clipRadiusWidth = (int) (Math.min(screenWidth, screenHeight) - 2 * mHorizontalPadding) / 2;
        this.clipWidth = (int) (Math.min(screenWidth, screenHeight) - 2 * mHorizontalPadding);
        float clipHeightValue = clipWidth / mScaleClipHW;
        //边界保护
        if (screenHeight - 4 * mHorizontalPadding < clipHeightValue) {
            clipHeightValue = screenHeight - 4 * mHorizontalPadding;
        }
        this.clipHeight = (int) clipHeightValue;
        Log.i(TAG, String.format("screenWidth:%d  screenHeight:%d  clipWidth:%d  clipHeight:%d  clipRadiusWidth:%d ", screenWidth, screenHeight, clipWidth, clipHeight, clipRadiusWidth));
    }

    /**
     * 因为屏幕方向发生变化，重新刷新布局(A4纸效果)
     *
     * @param isPortrait 是否为竖屏
     */
    public void refreshOrientationChangedForA4(boolean isPortrait) {
        isMessagePortrait = isPortrait;
        if (isPortrait) {
            mMessageDirection.setTop();
        } else {
            mMessageDirection.setRight();
        }
        postInvalidate();
    }

    /**
     * 因为屏幕方向发生变化，重新刷新布局(A4纸效果)
     *
     * @param isPortrait  是否为竖屏
     * @param orientation 旋转角度
     */
    public void refreshOrientationChangedForA4(boolean isPortrait, int orientation) {
        isMessagePortrait = isPortrait;
        mMessageDirection.setOrientation(orientation);
        postInvalidate();
    }

    public void setMessageTip(String messageTip) {
        message = messageTip;
    }

    public float getScaleClipHW() {
        return mScaleClipHW;
    }

    /**
     * 设置裁剪框的高宽比例
     *
     * @param scaleClipHW 高宽比例
     */
    public void setScaleClipHW(float scaleClipHW) {
        mScaleClipHW = scaleClipHW;
        if (mScaleClipHW <= 0) {
            mScaleClipHW = DEFAULT_SCALE_HW;
        } else if (1 < mScaleClipHW) {
            mScaleClipHW = DEFAULT_SCALE_HW;
        }
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    public void setClipType(int type) {
        switch (type) {
            case TYPE_ROUND:
                this.setClipType(ClipView.ClipType.CIRCLE);
                break;
            case TYPE_RECT:
                this.setClipType(ClipView.ClipType.RECTANGLE);
                break;
            case TYPE_PALACE:
                this.setClipType(ClipView.ClipType.PALACE);
                break;
            default:
                this.setClipType(ClipView.ClipType.PALACE);
                break;
        }
    }


    /**
     * 设置裁剪框类别
     *
     * @param clipType
     */
    public void setClipType(ClipType clipType) {
        this.clipType = clipType;
    }

    /**
     * 裁剪框类别，(圆形、矩形、九宫格）
     */
    public enum ClipType {
        CIRCLE(1), RECTANGLE(2), PALACE(3);

        private int value;

        ClipType(int value) {
            value = value;
        }
    }


    private void initData() {
        mPalaceBorderPaint = new Paint();
        mPalaceBorderPaint.setStyle(Style.STROKE);
        mPalaceBorderPaint.setStrokeWidth(dip2px(context, 1.0f));
        mPalaceBorderPaint.setColor(Color.parseColor("#FFFFFF"));

        mGuidelinePaint = new Paint();
        mGuidelinePaint.setStyle(Style.STROKE);
        mGuidelinePaint.setStrokeWidth(dip2px(context, 1.0f));
        mGuidelinePaint.setColor(Color.parseColor("#FFFFFF"));


        mCornerPaint = new Paint();
        mCornerPaint.setStyle(Style.STROKE);
        mCornerPaint.setStrokeWidth(dip2px(context, 3.5f));
        mCornerPaint.setColor(Color.WHITE);

        mBorderThickness = mPalaceBorderPaint.getStrokeWidth();
        mCornerThickness = mCornerPaint.getStrokeWidth();
        mCornerLength = dip2px(context, 25);

        mScaleClipHW = DEFAULT_SCALE_HW;
        mMessageDirection = new Direction();
    }

    private void drawGuidelines(@NonNull Canvas canvas, Rect clipRect) {

        final float left = clipRect.left;
        final float top = clipRect.top;
        final float right = clipRect.right;
        final float bottom = clipRect.bottom;

        final float oneThirdCropWidth = (right - left) / 3;

        final float x1 = left + oneThirdCropWidth;
        //引导线竖直方向第一条线
        canvas.drawLine(x1, top, x1, bottom, mGuidelinePaint);
        final float x2 = right - oneThirdCropWidth;
        //引导线竖直方向第二条线
        canvas.drawLine(x2, top, x2, bottom, mGuidelinePaint);

        final float oneThirdCropHeight = (bottom - top) / 3;

        final float y1 = top + oneThirdCropHeight;
        //引导线水平方向第一条线
        canvas.drawLine(left, y1, right, y1, mGuidelinePaint);
        final float y2 = bottom - oneThirdCropHeight;
        //引导线水平方向第二条线
        canvas.drawLine(left, y2, right, y2, mGuidelinePaint);
    }

    private void drawBorder(@NonNull Canvas canvas, Rect clipRect) {

        canvas.drawRect(clipRect.left,
                clipRect.top,
                clipRect.right,
                clipRect.bottom,
                mPalaceBorderPaint);
    }


    private void drawCorners(@NonNull Canvas canvas, Rect clipRect) {

        final float left = clipRect.left;
        final float top = clipRect.top;
        final float right = clipRect.right;
        final float bottom = clipRect.bottom;

        //简单的数学计算
        final float lateralOffset = (mCornerThickness - mBorderThickness) / 2f;
        final float startOffset = mCornerThickness - (mBorderThickness / 2f);

        //左上角左面的短线
        canvas.drawLine(left - lateralOffset, top - startOffset, left - lateralOffset, top + mCornerLength, mCornerPaint);
        //左上角上面的短线
        canvas.drawLine(left - startOffset, top - lateralOffset, left + mCornerLength, top - lateralOffset, mCornerPaint);

        //右上角右面的短线
        canvas.drawLine(right + lateralOffset, top - startOffset, right + lateralOffset, top + mCornerLength, mCornerPaint);
        //右上角上面的短线
        canvas.drawLine(right + startOffset, top - lateralOffset, right - mCornerLength, top - lateralOffset, mCornerPaint);

        //左下角左面的短线
        canvas.drawLine(left - lateralOffset, bottom + startOffset, left - lateralOffset, bottom - mCornerLength, mCornerPaint);
        //左下角底部的短线
        canvas.drawLine(left - startOffset, bottom + lateralOffset, left + mCornerLength, bottom + lateralOffset, mCornerPaint);

        //右下角左面的短线
        canvas.drawLine(right + lateralOffset, bottom + startOffset, right + lateralOffset, bottom - mCornerLength, mCornerPaint);
        //右下角底部的短线
        canvas.drawLine(right + startOffset, bottom + lateralOffset, right - mCornerLength, bottom + lateralOffset, mCornerPaint);
    }

    private void drawMessage(Canvas canvas) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        float textWidth = textPaint.measureText(message);
        if (isMessagePortrait) {//横向展示文字
            if (mMessageDirection.getDirection() == Direction.TOP) {//顶部绘制文字
                // 文字baseline在y轴方向的位置
                float baseLineY = Math.abs(textPaint.ascent() + textPaint.descent()) / 2;
                canvas.drawText(message, this.getWidth() / 2, this.getHeight() / 2 - clipHeight / 2 - 30 + baseLineY, textPaint);
            } else {//底部绘制文字
                Path path = new Path();
                path.moveTo(this.getWidth() / 2 + textWidth / 2, this.getHeight() / 2 + clipHeight / 2 + mHorizontalPadding / 2);
                path.lineTo(this.getWidth() / 2 - textWidth / 2, this.getHeight() / 2 + clipHeight / 2 + mHorizontalPadding / 2);
                canvas.drawTextOnPath(message, path, 0, 0, textPaint);
            }
        } else {//纵向展示文字
            Path path = new Path();
            if (mMessageDirection.getDirection() == Direction.RIGHT) {//右侧绘制文字
                path.moveTo(this.getWidth() / 2 + clipWidth / 2 + mHorizontalPadding / 2, this.getHeight() / 2 - textWidth / 2);
                path.lineTo(this.getWidth() / 2 + clipWidth / 2 + mHorizontalPadding / 2, this.getHeight() / 2 + textWidth / 2);
            } else {//左侧绘制文字
                path.moveTo(this.getWidth() / 2 - clipWidth / 2 - mHorizontalPadding / 2, this.getHeight() / 2 + textWidth / 2);
                path.lineTo(this.getWidth() / 2 - clipWidth / 2 - mHorizontalPadding / 2, this.getHeight() / 2 - textWidth / 2);
            }
            canvas.drawTextOnPath(message, path, 0, 0, textPaint);
        }
    }

    public static int dip2px(@Nullable Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
