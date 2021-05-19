package com.target.targetcasestudy.di.module

import com.target.targetcasestudy.data.repository.network.Api
import com.target.targetcasestudy.data.repository.network.Repo
import com.target.targetcasestudy.data.repository.network.RepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepoModule {

    @Provides
    @Singleton
    fun provideRepo(api: Api): Repo = RepoImpl(api)
}