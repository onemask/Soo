package com.kakao.kakaosearch.search.di

import androidx.lifecycle.ViewModel
import com.kakao.kakaosearch.di.scope.PerActivity
import com.kakao.kakaosearch.di.scope.key.ViewModelKey
import com.kakao.kakaosearch.search.vm.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainActivityModule {
    @PerActivity
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindMainViewModel(viewModel: MainViewModel) : ViewModel
}