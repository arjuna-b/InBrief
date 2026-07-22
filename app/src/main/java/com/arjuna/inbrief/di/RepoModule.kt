package com.arjuna.inbrief.di

import com.arjuna.inbrief.data.repository.NewsRepositoryImpl
import com.arjuna.inbrief.domain.repository.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule{
    @Binds
    abstract fun bindRepo(
        impl : NewsRepositoryImpl
    ) : NewsRepository
}

