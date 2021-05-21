package com.target.targetcasestudy.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TestBaseUrlModule {

    @Provides
    @Singleton
    fun provideUrl(): String = "http://localhost:8080/"
}