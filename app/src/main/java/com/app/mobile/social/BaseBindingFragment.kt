package com.app.mobile.social

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner

abstract class BaseBindingFragment<T : ViewDataBinding> : Fragment(),LifecycleOwner{
    abstract val layoutRes: Int
    lateinit var viewDataBinding: T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater,layoutRes,container,false)
        viewDataBinding.lifecycleOwner = this
        return viewDataBinding.root
    }

}