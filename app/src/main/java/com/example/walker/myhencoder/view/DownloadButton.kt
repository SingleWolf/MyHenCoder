package com.example.walker.myhencoder.view

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View

/**
 * @author walker zheng
 * @date 2019/2/21
 * @desc 下载进度按钮
 */
class DownloadButton : View, View.OnClickListener {

    private lateinit var backgroundPaint: Paint
    private lateinit var textPaint: Paint
    private lateinit var progressPaint: Paint
    private lateinit var cutPath: Path
    private var progress: Int = 0
    private var downloadState: DownloadState = DownloadState.NEED_DOWNLOAD()
    private var tapDelegate: OnTapClickListener? = null
    private var textSizeValue: Float = 17f
    private var roundRadiusValue: Float = 20f

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
        Paint(Paint.ANTI_ALIAS_FLAG).run {
            backgroundPaint = this
            color = Color.parseColor("#FF9EBD")
            style = Paint.Style.FILL
        }
        Paint(Paint.ANTI_ALIAS_FLAG).run {
            progressPaint = this
            color = Color.parseColor("#FB81A9")
            style = Paint.Style.FILL
        }
        TextPaint(Paint.ANTI_ALIAS_FLAG).run {
            textPaint = this
            color = Color.WHITE
            textSize = dp2px(textSizeValue)
            textAlign = Paint.Align.CENTER
        }
        Path().run {
            cutPath = this
        }
        setOnClickListener(this)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val widthSpecMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val widthSpecSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightSpecMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val heightSpecSize = View.MeasureSpec.getSize(heightMeasureSpec)

        if (widthSpecMode == View.MeasureSpec.AT_MOST && heightSpecMode == View.MeasureSpec.AT_MOST) {
            setMeasuredDimension(dp2px(100f).toInt(), dp2px(40f).toInt())
        } else if (widthSpecMode == View.MeasureSpec.AT_MOST) {
            setMeasuredDimension(dp2px(100f).toInt(), heightSpecSize)
        } else if (heightSpecMode == View.MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, dp2px(40f).toInt())
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val widths = width.toFloat() - paddingLeft - paddingRight
        val heights = height.toFloat() - paddingBottom - paddingTop

        canvas?.run {
            cutPath.addRoundRect(RectF(0f, 0f, widths, heights), dp2px(roundRadiusValue), dp2px(roundRadiusValue), Path.Direction.CW)
            save()
            clipPath(cutPath)
            drawRect(RectF(0f, 0f, widths, heights), backgroundPaint)
            when (downloadState) {
                is DownloadState.DOWNLOADING -> {
                    drawRect(RectF(0f, 0f, widths * progress / 100, heights), progressPaint)
                }
                is DownloadState.COMPLETE_DOWNLOAD -> {
                    drawRect(RectF(0f, 0f, widths * progress / 100, heights), progressPaint)
                }
            }
            val desc = getDescText()
            textPaint.measureText(desc)
            val baseLineY = Math.abs(textPaint.ascent() + textPaint.descent()) / 2
            canvas.drawText(desc, widths / 2, heights / 2 + baseLineY, textPaint)
            restore()
        }
    }

    override fun onClick(v: View?) {
        when (downloadState) {
            is DownloadState.NEED_DOWNLOAD -> {
                setDownloadState(DownloadState.DOWNLOADING())
                tapDelegate?.onStartDownload()
            }
            is DownloadState.DOWNLOADING -> {
                tapDelegate?.onDownloading()
            }
            is DownloadState.COMPLETE_DOWNLOAD -> {
                tapDelegate?.onCompleteDownload()
            }
            is DownloadState.EXTRA -> {
                tapDelegate?.onExtra()
            }
        }
    }

    private fun getDescText() = when (downloadState) {
        is DownloadState.NEED_DOWNLOAD -> {
            (downloadState as DownloadState.NEED_DOWNLOAD).label
        }
        is DownloadState.DOWNLOADING -> {
            (downloadState as DownloadState.DOWNLOADING).label
        }
        is DownloadState.COMPLETE_DOWNLOAD -> {
            (downloadState as DownloadState.COMPLETE_DOWNLOAD).label
        }
        is DownloadState.EXTRA -> {
            (downloadState as DownloadState.EXTRA).label
        }
    }

    private fun dp2px(dp: Float): Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)

    fun setProgress(progressValue: Int) {
        when (downloadState) {
            is DownloadState.DOWNLOADING -> {
                progress = progressValue
                if (progress == 100) {
                    downloadState = DownloadState.COMPLETE_DOWNLOAD()
                }
                postInvalidate()
            }
        }
    }

    fun setDownloadState(state: DownloadState) {
        downloadState = state
        postInvalidate()
    }

    fun setTpaClickListener(listener: OnTapClickListener) {
        tapDelegate = listener
    }

    interface OnTapClickListener {
        fun onStartDownload()
        fun onDownloading()
        fun onCompleteDownload()
        fun onExtra()
    }

    sealed class DownloadState {
        class NEED_DOWNLOAD(var label: String = "下载") : DownloadState()
        class DOWNLOADING(var label: String = "下载中") : DownloadState()
        class COMPLETE_DOWNLOAD(var label: String = "完成") : DownloadState()
        class EXTRA(var label: String) : DownloadState()
    }
}