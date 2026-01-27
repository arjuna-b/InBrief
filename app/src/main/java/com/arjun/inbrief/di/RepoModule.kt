package com.arjun.inbrief.di

import com.arjun.inbrief.data.repository.NewsRepositoryImpl
import com.arjun.inbrief.domain.repository.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule{
    @Binds
    abstract fun bindRepo(
        impl : NewsRepositoryImpl
    ) : NewsRepository
}

