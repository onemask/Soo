package com.kakao.kakaosearch.di

import android.app.Application
import com.kakao.kakaosearch.app.KakaoSearchApplication
import com.kakao.kakaosearch.di.module.ActivityBinder
import com.kakao.kakaosearch.di.module.DataModule
import com.kakao.kakaosearch.di.module.NetworkModule
import com.kakao.kakaosearch.di.module.ViewModelFactoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        NetworkModule::class,
        DataModule::class,
        ActivityBinder::class,
        ViewModelFactoryModule::class
    ]
)

interface AppComponent : AndroidInjector<KakaoSearchApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: KakaoSearchApplication): Builder
        fun build(): AppComponent
    }

    fun inject(app: Application)
}