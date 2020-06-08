package com.kakao.kakaosearch.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity<B : ViewDataBinding>(
    @LayoutRes private val layoutRes: Int
) : DaggerAppCompatActivity() {

    protected lateinit var binding: B
    abstract val viewModel: BaseViewModel

    abstract fun setupViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutRes)
        binding.lifecycleOwner = this
        setupViewModel()
    }
}