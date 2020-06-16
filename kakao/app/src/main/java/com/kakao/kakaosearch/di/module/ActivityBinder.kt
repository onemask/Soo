package com.kakao.kakaosearch.di.module

import com.kakao.kakaosearch.di.scope.PerActivity
import com.kakao.kakaosearch.search.MainActivity
import com.kakao.kakaosearch.search.di.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBinder {
    @PerActivity
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun bindMainActivity(): MainActivity
}