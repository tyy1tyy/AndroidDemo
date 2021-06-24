package com.demo.tablayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.content.Intent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.google.android.material.tabs.TabLayout
import com.demo.fragment.FirstFragment
import com.demo.fragment.SecondFragment
import com.demo.tablayout.databinding.DemoActivityBinding

class DemoActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            context.startActivity(
                Intent().apply {
                    setClass(context, DemoActivity::class.java)
                }
            )
        }
    }

    // 获取给定位置对应的Fragment
    class DemoAdapter(var fmList: List<Fragment>, fm: FragmentManager?) : FragmentStatePagerAdapter(
        fm!!
    ) {
        override fun getItem(item: Int) = fmList[item]
        override fun getCount() = fmList.size
    }

    private lateinit var binding: DemoActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.demo_activity)
        binding.lifecycleOwner = this
        // 增加一个监听器，viewPager的Fragment界面随Tablayout改变而改变
        binding.fragmentView.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tablayout))
        init()
    }

    private fun init() {

        val fragment = listOf(FirstFragment(), SecondFragment())
        val tabTitle = listOf("Demo 1", "Demo 2")

        binding.fragmentView.adapter = DemoAdapter(fragment, supportFragmentManager)

        binding.tablayout.setupWithViewPager(binding.fragmentView)
        for(i in 0..fragment.size ){
            binding.tablayout.getTabAt(i)?.text = tabTitle.get(i)
        }
    }

}