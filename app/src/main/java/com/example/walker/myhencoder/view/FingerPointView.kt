package com.example.walker.myhencoder.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.walker.myhencoder.R

/**
 *@author Walker
 *
 *@e-mail feitianwumu@163.com
 *
 *@date on 2018/10/15
 *
 *@summary 指纹跟踪显示
 *
 */

class FingerPointView : View {

    private lateinit var mPaint: Paint

    private var mCurrentX: Float = 0f

    private var mCurrentY: Float = 0f

    private val bitmapFingerPoint: Bitmap by lazy { BitmapFactory.decodeResource(context.resources, R.drawable.ic_fingerpoint) }

    private val bitmapFingerPointPress: Bitmap by lazy { BitmapFactory.decodeResource(context.resources, R.drawable.ic_fingerpoint_press) }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
            context,
            attrs,
            defStyleAttr
    ) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                mCurrentX = event.x
                mCurrentY = event.y
                postInvalidate()
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                mCurrentX = event.x
                mCurrentY = event.y
                postInvalidate()
            }
            else -> {
                mCurrentX = 0f
                mCurrentY = 0f
                postInvalidate()
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val widthSpecMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val widthSpecSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightSpecMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val heightSpecSize = View.MeasureSpec.getSize(heightMeasureSpec)

        if (widthSpecMode == View.MeasureSpec.AT_MOST && heightSpecMode == View.MeasureSpec.AT_MOST) {
            setMeasuredDimension(400, 400)
        } else if (widthSpecMode == View.MeasureSpec.AT_MOST) {
            setMeasuredDimension(400, heightSpecSize)
        } else if (heightSpecMode == View.MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, 400)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.run {
            if (mCurrentX == 0f && mCurrentY == 0f) {
                drawBitmap(bitmapFingerPoint, (width / 2).toFloat() - bitmapFingerPoint.width / 2, (height / 2).toFloat() - bitmapFingerPoint.height, mPaint)
            } else {
                drawBitmap(bitmapFingerPointPress, mCurrentX - bitmapFingerPointPress.width, mCurrentY - bitmapFingerPointPress.height, mPaint)
            }
        }
    }
}