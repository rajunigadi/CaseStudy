package com.target.targetcasestudy.di.module

import com.target.targetcasestudy.data.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class BaseUrlModule {

    @Provides
    @Singleton
    fun provideUrl(): String = BASE_URL
}