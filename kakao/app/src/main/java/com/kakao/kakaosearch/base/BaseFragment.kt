package com.kakao.kakaosearch.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable

abstract class BaseFragment<B : ViewDataBinding>(@LayoutRes private val layoutRes: Int) :
    DaggerFragment() {

    protected lateinit var binding: B
    protected val disposable = CompositeDisposable()

    abstract val viewModel: BaseViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<B>(inflater, layoutRes, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        setupViewModel()
        return binding.root
    }

    abstract fun setupViewModel()

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}