package com.target.targetcasestudy.di.module

import com.target.targetcasestudy.BuildConfig
import com.target.targetcasestudy.data.repository.network.Api
import com.target.targetcasestudy.data.repository.network.RxErrorHandlingCallAdapterFactory
import com.target.targetcasestudy.data.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun providesRetrofitApi(
        httpClient: OkHttpClient
    ): Api {
        return providesRetrofit(httpClient, BASE_URL).create(Api::class.java)
    }

    private fun providesRetrofit(
        httpClient: OkHttpClient,
        baseUrl: String,
        isRxCallAdapter: Boolean? = true
    ): Retrofit {
        val builder = Retrofit.Builder()
        builder.baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())

        if (isRxCallAdapter != false) {
            builder.addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
        }
        return builder
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient() = OkHttpClient.Builder().run {
        connectTimeout(120, TimeUnit.SECONDS)
        writeTimeout(120, TimeUnit.SECONDS)
        readTimeout(120, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
        }
        return@run build()
    }
}