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
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provieHttpLogginInterceptor() : HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
            Timber.d(message)
        })
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    @Singleton
    fun provieOkHttpClient(logger : HttpLoggingInterceptor) : OkHttpClient {
        return  OkHttpClient.Builder().apply {
            addInterceptor(logger)
            connectTimeout(15, TimeUnit.SECONDS)
            readTimeout(20, TimeUnit.SECONDS)
            writeTimeout(15, TimeUnit.SECONDS)
        }.build()
    }

    @Provides
    @Singleton
    fun provideUpbitApiService(client: OkHttpClient) : KaKaoApiService {
        return Retrofit.Builder()
            .baseUrl(SERVER_HOST)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(KaKaoApiService::class.java)
    }
}
