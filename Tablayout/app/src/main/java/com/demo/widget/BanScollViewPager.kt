package com.demo.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 * 禁止ViewPager左右滑动更滑界面，取消滑动时的动画效果
 */
class BanScollViewPager : ViewPager {
    private var isCanScroll = true

    constructor(context: Context?) : super(context!!)
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs)

    // 事件处理
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(arg0: MotionEvent?): Boolean {
        return if (isCanScroll) {
            false
        } else {
            super.onTouchEvent(arg0)
        }
    }

    //事件拦截
    override fun onInterceptTouchEvent(arg0: MotionEvent?): Boolean {
        return if (isCanScroll) {
            false
        } else {
            super.onInterceptTouchEvent(arg0)
        }
    }

    // 直接切换界面，不要动画
    override fun setCurrentItem(item: Int) {
        super.setCurrentItem(item, false)
    }
}