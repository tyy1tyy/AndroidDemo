package com.demo.widget

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import kotlin.math.max

class FlowLayout : ViewGroup {

    private val mHorizontalSpacing = 20
    private val mVerticalSpacing = 10

    private var allLines: ArrayList<ArrayList<View>>? = null
    private var lineHeights: ArrayList<Int>? = null

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int,
    ) : super(context, attrs, defStyleAttr, defStyleRes) {}


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        initMeasureParams()
        //度量孩子
        val childCount = childCount

        val paddingLeft = paddingLeft
        val paddingRight = paddingRight
        val paddingTop = paddingTop
        val paddingBottom = paddingBottom

        val selfWidth = MeasureSpec.getSize(widthMeasureSpec)   //ViewGroup解析的宽度
        val selfHeight = MeasureSpec.getSize(heightMeasureSpec) //ViewGroup解析的高度

        var lineViews = ArrayList<View>()   //保存一行中所有的view

        var lineWidthUsed = 0   //记录这行已经使用了多宽的size
        var lineHeight = 0  //一行的行高

        var parentNeededWidth = 0   //measure过程中，子view要求的父ViewGroup的宽
        var parentNeededHeight = 0  //measure过程中，子view要求的父ViewGroup的高

        for(i in 0 until childCount){
            val childView = getChildAt(i)
            val childP = childView.layoutParams

            val childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec, paddingLeft + paddingRight, childP.width)
            val childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec, paddingTop + paddingBottom, childP.height)

            childView.measure(childWidthMeasureSpec, childHeightMeasureSpec)

            //获取子view的宽高
            val childMeasureWidth = childView.measuredWidth
            val childMeasureHeight = childView.measuredHeight

            //通过宽度来判断是否需要换行，通过换行后的每行的行高来获取整个ViewGroup的行高
            //如果需要换行
            if(childMeasureWidth + lineWidthUsed + mHorizontalSpacing > selfWidth){
                allLines!!.add(lineViews)
                lineHeights!!.add(lineHeight)

                //一旦换行，我们就可以判断当前行需要的宽和高了，所以此时要记录下来
                parentNeededHeight += lineHeight + mVerticalSpacing
                parentNeededWidth = max(parentNeededWidth, lineWidthUsed + mHorizontalSpacing)

                lineViews = ArrayList()
                lineWidthUsed = 0
                lineHeight = 0
            }

            //view是分行layout的，所以要记录每一行有哪些View，这样可以方便layout布局
            lineViews.add(childView)

            lineWidthUsed += childMeasureWidth + mHorizontalSpacing
            lineHeight = max(lineHeight, childMeasureHeight)
        }
        if(lineViews.size > 0){
            allLines!!.add(lineViews)
            lineHeights!!.add(lineHeight)
            parentNeededHeight += lineHeight + mVerticalSpacing
            parentNeededWidth = max(parentNeededWidth, lineWidthUsed + mHorizontalSpacing)
        }

        //根据子View的度量结果，来重新度量自己ViewGroup
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        val realWidth = if (widthMode == MeasureSpec.EXACTLY) selfWidth else parentNeededWidth
        val realHeight = if (heightMode == MeasureSpec.EXACTLY) selfHeight else parentNeededHeight

        setMeasuredDimension(realWidth, realHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val lineCount = allLines!!.size

        var curL = paddingLeft
        var curT = paddingTop

        for(i in 0 until lineCount){
            val lineViews = allLines!![i]
            val lineHeight = lineHeights!![i]
            for(j in 0 until lineViews.size){
                val view = lineViews[j]
                val left = curL
                val top = curT
                val right = left + view.measuredWidth
                val bottom = top + view.measuredHeight
                view.layout(left, top, right, bottom)
                curL = right + mHorizontalSpacing
            }
            curL = paddingLeft
            curT += lineHeight + mVerticalSpacing
        }

    }

    private fun initMeasureParams(){
        if (allLines == null) {
            allLines = ArrayList()
        }else{
            allLines!!.clear()
        }
        if (lineHeights == null) {
            lineHeights = ArrayList()
        } else {
            lineHeights!!.clear()
        }
    }


}