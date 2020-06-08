package com.kakao.kakaosearch.di.module

import androidx.lifecycle.ViewModelProvider
import com.kakao.utils.aac_view_model_factory.CommonViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: CommonViewModelFactory): ViewModelProvider.Factory
}
