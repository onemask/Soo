package com.kakao.kakaosearch.di

import android.app.Application
import com.kakao.kakaosearch.app.KakaoSearchApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class
    ]
)

interface AppComponent : AndroidInjector<KakaoSearchApplication> {
    @Component.Builder
    interface  Builder{
        @BindsInstance
        fun application(application: KakaoSearchApplication) : Builder
        fun build() : AppComponent
    }
    fun inject(app : Application)
}