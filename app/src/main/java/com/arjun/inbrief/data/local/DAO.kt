package com.arjun.inbrief.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface articleDAO {

    @Query("SELECT * FROM Articles")
    suspend fun getAllArticles() : List<ArticleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun loadArticles(articles : List<ArticleEntity>)
}