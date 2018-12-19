package com.example.walker.myhencoder.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.FrameLayout;

import com.example.walker.myhencoder.R;

/**
 * @author walker zheng
 * @date 2018/12/15
 * @desc 带有镂空效果的遮盖层控件
 */
public class ShadowHoleView extends FrameLayout {
    public static final int SHPAE_CIRCLE = 1;
    public static final int SHPAE_ROUND_RECT = 2;
    private final int DEFAULT_SHAPE_TYPE = SHPAE_CIRCLE;
    private Bitmap mEraserBitmap;
    private Canvas mEraserCanvas;
    private Paint mEraser;
    private Context mContext;
    private int mShapeType;
    private int mBackgroundColor;
    private int mHoleColor;
    private int mRoundRadius;

    public ShadowHoleView(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    public ShadowHoleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ShadowHoleView);
        mBackgroundColor = ta.getColor(R.styleable.ShadowHoleView_sh_background_color, -1);
        mHoleColor = ta.getColor(R.styleable.ShadowHoleView_sh_hole_color, -1);
        mShapeType = ta.getInt(R.styleable.ShadowHoleView_sh_shape_type, DEFAULT_SHAPE_TYPE);
        mRoundRadius = ta.getDimensionPixelSize(R.styleable.ShadowHoleView_sh_round_radius, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 0, getResources().getDisplayMetrics()));
        init(null, 0);
        ta.recycle();
    }

    public ShadowHoleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ShadowHoleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    private void init(AttributeSet attrs, int defStyle) {
        setWillNotDraw(false);
        Point size = new Point();
        size.x = mContext.getResources().getDisplayMetrics().widthPixels;
        size.y = mContext.getResources().getDisplayMetrics().heightPixels;

        mBackgroundColor = mBackgroundColor != -1 ? mBackgroundColor : Color.parseColor("#55000000");
        mHoleColor = mHoleColor != -1 ? mHoleColor : Color.parseColor("#00000000");

        mEraserBitmap = Bitmap.createBitmap(size.x, size.y, Bitmap.Config.ARGB_8888);
        mEraserCanvas = new Canvas(mEraserBitmap);


        mEraser = new Paint();
        mEraser.setColor(mHoleColor);
        mEraser.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        mEraser.setFlags(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mEraserBitmap.eraseColor(Color.TRANSPARENT);
        mEraserCanvas.drawColor(mBackgroundColor);

        switch (mShapeType) {
            case SHPAE_ROUND_RECT:
                drawRoundRect(canvas);
                break;
            case SHPAE_CIRCLE:
            default:
                drawCircle(canvas);
                break;
        }
    }

    private void drawRoundRect(Canvas canvas) {
        int x = getWidth();
        int y = getHeight();
        mEraserCanvas.drawRoundRect(new RectF(0, 0, x, y), mRoundRadius, mRoundRadius, mEraser);
        canvas.drawBitmap(mEraserBitmap, 0, 0, null);
    }

    private void drawCircle(Canvas canvas) {
        int x = getWidth() / 2;
        int y = getHeight() / 2;
        int radius = Math.min(x, y);
        mEraserCanvas.drawCircle(x, y, radius, mEraser);
        canvas.drawBitmap(mEraserBitmap, 0, 0, null);
    }
}