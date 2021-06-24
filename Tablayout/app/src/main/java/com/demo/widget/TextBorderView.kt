package com.demo.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.widget.TextView
import com.demo.tablayout.R

/**
 * 复写TextView，增加功能
 * 设置文本框边框颜色
 * 设置文本框的弧度
 * 设置文本框的宽度
 */
@SuppressLint("AppCompatCustomView")
class TextBorderView(context: Context, attrs: AttributeSet?) :
    TextView(context, attrs) {
    var mBordderColor = 0
    var mRadius = 0
    var mStrokeWidth = 0
    private val borderPaint: Paint
    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        borderPaint.color = mBordderColor

        val w = this.measuredWidth
        val h = this.measuredHeight
        val r = RectF(1F, 1F, (w - 1).toFloat(),
            (h - 1).toFloat())

        canvas.drawRoundRect(r, mRadius.toFloat(), mRadius.toFloat(), borderPaint)

        super.onDraw(canvas)
    }

    fun setBorderColor(newColor: Int) {
        mBordderColor = newColor
        invalidate()
        requestLayout()
    }

    fun setBorderRadius(newRadius: Int) {
        mRadius = newRadius
        invalidate()
        requestLayout()
    }

    fun setBorderWidth(newWidth: Int){
        mStrokeWidth = newWidth
        invalidate()
        requestLayout()
    }

    init {
        val a = context.theme.obtainStyledAttributes(attrs,
            R.styleable.TextViewBorder, 0, 0)
        try {
            mBordderColor = a.getInteger(R.styleable.TextViewBorder_borderColor, 0)
            mRadius = a.getInteger(R.styleable.TextViewBorder_borderRadius, 0) //  默认为矩形 弧度为0
            mStrokeWidth = a.getInteger(R.styleable.TextViewBorder_borderStrokeWidth, 1)
        } finally {
            a.recycle()
        }
        borderPaint = Paint()
        borderPaint.style = Paint.Style.STROKE  // STROKE 形状轮廓 FILL 填充
        borderPaint.strokeWidth = mStrokeWidth.toFloat()
        borderPaint.isAntiAlias = true
    }
}
