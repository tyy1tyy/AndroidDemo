package com.demo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.demo.tablayout.R
import com.demo.tablayout.databinding.FirstFragmentBinding
import com.demo.widget.TextBorderView

/**
 * 显示多个不同边框颜色的View
 */
class FirstFragment: Fragment() {

    lateinit var binding: FirstFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater, R.layout.first_fragment,container,false);
        return binding.root

        // 另一写法
        /*return inflater.inflate(
             R.layout.tutor_introduce, container, false
         )*/
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val labelLists = listOf("星期一", "星期二" ,"星期三", "星期四")
        val flowViewLists = setTutorLabel(labelLists)

        for(flowView in flowViewLists){
            binding.flowlayout.addView(flowView)
        }

    }

    /**
     * 设置多个文本的颜色，返回View，颜色不重复
     */
    fun setTutorLabel(labelList: List<String>) : ArrayList<View>{
        val labelView = ArrayList<View>()

        val colorList = mutableListOf(R.color.purple_200, R.color.teal_700, R.color.teal_200,
                                        R.color.gray, R.color.red)

        for (index in 0..labelList.size - 1) {
            val cIndex: Int = (colorList.size * Math.random()).toInt()
            val color = colorList[cIndex]

            val tv = TextBorderView(getContext()!!, null)
            tv.setBorderColor(ContextCompat.getColor(getContext()!!, color))
            tv.setTextColor(ContextCompat.getColor(getContext()!!, color))
            tv.setTextSize(30F)
            tv.setBorderRadius(50)
            tv.setPadding(24, 11, 23, 13)
            tv.text = labelList[index]
            labelView.add(tv)
            colorList.remove(color)

        }
    return labelView
    }

}