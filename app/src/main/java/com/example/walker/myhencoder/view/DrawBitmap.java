package com.example.walker.myhencoder.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.walker.myhencoder.R;



/**
 * 画 Bitmap
 * Created by Walker on 2017/7/11.
 */

public class DrawBitmap extends View {

    private Context mContext;

    public DrawBitmap(Context context) {
        super(context);
        mContext=context;
    }

    public DrawBitmap(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
    }

    public DrawBitmap(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint=new Paint();
        Bitmap bitmap=BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_pld);
        canvas.drawBitmap(bitmap, 200, 100, paint);

    }
}
