package com.demo.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.demo.tablayout.R
import com.demo.tablayout.databinding.SecondFragmentBinding

class SecondFragment: Fragment() {

    lateinit var binding: SecondFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment

        binding= DataBindingUtil.inflate(inflater, R.layout.second_fragment,container,false);
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.searchEdit.setOnEditorActionListener { v, actionId, event ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                }
                else -> {
                }
            }
            false
        }


        /**
         * 文字监听器(EditText文本内容改变时,会触发对应的回调函数)
         * beforeTextChanged() EditText文本内容更改前触onTextChanged() EditText文本内容更改时触发
         * onTextChanged() EditText文本内容更改时触发
         * afterTextChanged() EditText文本内容更改后触发
         */
        binding.searchEdit.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int) {
                }

                override fun onTextChanged(
                    s: CharSequence,
                    start: Int,
                    before: Int,
                    count: Int) {
                }

                override fun afterTextChanged(s: Editable) {
                    //触发事件
                    if(binding.searchEdit.text.length > 0){
                        binding.delete.visibility = View.VISIBLE
                    }else{
                        binding.delete.visibility = View.INVISIBLE
                    }
                }
            }
        )

        binding.delete.setOnClickListener {
            binding.searchEdit.text = null
        }
    }
}