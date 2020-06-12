package com.kakao.kakaosearch.di.module

import com.kakao.constants.Constants.SERVER_HOST
import com.kakao.kakaosearch.repository.KaKaoApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideHttpLogginInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(logger: HttpLoggingInterceptor) =
        OkHttpClient.Builder().apply { addInterceptor(logger) }.build()

    @Provides
    @Singleton
    fun provideRxJavaAdapter(): RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create()

    @Provides
    @Singleton
    fun provideGsonConverter(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    @Named("KaKaoHost")
    fun provideUpbitApiService(
        client: OkHttpClient,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(SERVER_HOST)
        .addCallAdapterFactory(rxJava2CallAdapterFactory)
        .addConverterFactory(gsonConverterFactory)
        .client(client)
        .build()


    @Provides
    @Singleton
    fun provideKaKaoApiService(@Named("KaKaoHost") retrofit: Retrofit): KaKaoApiService =
        retrofit.create(KaKaoApiService::class.java)


}
