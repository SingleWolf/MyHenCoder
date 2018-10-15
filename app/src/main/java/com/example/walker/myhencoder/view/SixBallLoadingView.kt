package com.example.walker.myhencoder.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.animation.AccelerateInterpolator
import com.example.walker.myhencoder.R

/**
 *@author Walker
 *
 *@e-mail feitianwumu@163.com
 *
 *@date on 2018/10/15
 *
 *@summary 自定义六球加载
 *
 */
class SixBallLoadingView : View {

    companion object {
        private const val TAG = "SixBallLoadingView"
        private const val DEFAULT_RADIUS: Float = 20f
        private const val DEFAULT_PAINT_COLOR = Color.BLUE
        private const val DEFAULT_DURATION = 2000L
    }

    /**
     * 位移权重矩阵
     * 0,0,0,0,0,0
     * 1,0,0,0,0,0
     * 2,1,0,0,0,0
     * 3,2,1,0,0,0
     * 4,3,2,1,0,0
     * 5,4,3,2,1,0
     * 6,5,4,3,2,1
     * 5,6,5,4,3,2
     * 4,5,6,5,4,3
     * 3,4,5,6,5,4
     * 2,3,4,5,6,5
     * 1,2,3,4,5,6
     * 0,1,2,3,4,5
     * 0,0,1,2,3,4
     * 0,0,0,1,2,3
     * 0,0,0,0,1,2
     * 0,0,0,0,0,1
     * 0,0,0,0,0,0
     *
     */
    private val mMatrix: Array<Int> by lazy { arrayOf(0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 2, 1, 0, 0, 0, 0, 3, 2, 1, 0, 0, 0, 4, 3, 2, 1, 0, 0, 5, 4, 3, 2, 1, 0, 6, 5, 4, 3, 2, 1, 5, 6, 5, 4, 3, 2, 4, 5, 6, 5, 4, 3, 3, 4, 5, 6, 5, 4, 2, 3, 4, 5, 6, 5, 1, 2, 3, 4, 5, 6, 0, 1, 2, 3, 4, 5, 0, 0, 1, 2, 3, 4, 0, 0, 0, 1, 2, 3, 0, 0, 0, 0, 1, 2, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0) }

    private lateinit var mPaint: Paint

    private lateinit var mValueAnimator: ValueAnimator

    private var mProgress: Int = 0

    private var mRadius: Float = DEFAULT_RADIUS

    private var mDuration: Long = DEFAULT_DURATION

    private var mPaintColor: Int = DEFAULT_PAINT_COLOR

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

        attrs?.run {
            val ta = context.obtainStyledAttributes(attrs, R.styleable.SixBallLoadingView)
            mDuration = (ta.getInt(R.styleable.SixBallLoadingView_sDuration, DEFAULT_DURATION.toInt())).toLong()
            mRadius = ta.getDimension(R.styleable.SixBallLoadingView_sRadius, DEFAULT_RADIUS)
            mPaintColor = ta.getColor(R.styleable.SixBallLoadingView_sColor, DEFAULT_PAINT_COLOR)
            ta.recycle()
        }

        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint.run {
            color = mPaintColor
            style = Paint.Style.FILL
        }

        mValueAnimator = ValueAnimator.ofInt(0, 17)
        mValueAnimator.run {
            duration = mDuration
            repeatCount = ValueAnimator.INFINITE
            interpolator = AccelerateInterpolator()
            addUpdateListener {
                mProgress = it.animatedValue as Int
                postInvalidate()
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        mValueAnimator?.run {
            start()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mValueAnimator?.run {
            end()
        }
    }

    fun onStart() {
        mValueAnimator?.run {
            start()
        }
    }

    fun onStop() {
        mValueAnimator?.run {
            end()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val widthSpecMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val widthSpecSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightSpecMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val heightSpecSize = View.MeasureSpec.getSize(heightMeasureSpec)

        if (widthSpecMode == View.MeasureSpec.AT_MOST && heightSpecMode == View.MeasureSpec.AT_MOST) {
            setMeasuredDimension(dp2px(150f).toInt(), dp2px(45f).toInt())
        } else if (widthSpecMode == View.MeasureSpec.AT_MOST) {
            setMeasuredDimension(dp2px(150f).toInt(), heightSpecSize)
        } else if (heightSpecMode == View.MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, dp2px(45f).toInt())
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val widths = width - paddingLeft - paddingRight
        val radius = mRadius
        val baseY = height - paddingBottom - 2 * radius
        val maxProgress = baseY - radius
        val space = (widths - 12 * radius) / 6

        //纵向位移的权重
        val add: Int = (6 * mProgress)
        //横向间隔距离
        val addWeight: Float = space + radius

        //绘制6个圆球
        for (i in 0..5) {
            val x: Float = paddingLeft + (i + 1) * addWeight
            val y = baseY - mMatrix[add + i] * maxProgress / 6
            canvas?.run {
                drawCircle(x, y, radius, mPaint)
            }
        }
    }

    private fun dp2px(dp: Float): Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)

}

