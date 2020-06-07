package com.kakao.kakaosearch.di.module

import com.kakao.kakaosearch.repository.KaKaoApiService
import com.kakao.kakaosearch.repository.KaKaoRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {
    @Provides
    @Singleton
    fun provideKaKaoRepository(apiService : KaKaoApiService) : KaKaoRepositoryImpl = KaKaoRepositoryImpl(apiService)
}