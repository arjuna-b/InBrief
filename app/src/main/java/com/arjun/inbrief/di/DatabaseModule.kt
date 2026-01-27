package com.arjun.inbrief.di

import android.content.Context
import androidx.room.Room
import com.arjun.inbrief.data.local.DAO
import com.arjun.inbrief.data.local.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDataBase(
        @ApplicationContext context: Context
    ): Database =
        Room.databaseBuilder(
            context,
            Database::class.java,
            "NewsDataBase"
        ).build()

    @Provides
    fun providesArticleDao(db: Database): DAO = db.articleDAO()

}
