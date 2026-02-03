package com.arjun.inbrief.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arjun.inbrief.data.dto.TopHeadLinesDto
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

@Dao
interface articleDAO {

    @Query("SELECT * FROM Articles")
    suspend fun getAllArticles() : List<ArticleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun loadArticles(articles : List<ArticleEntity>)

    @Query("SELECT * FROM  articles WHERE isSaved = 1")
    fun getSavedArticles() : Flow<List<ArticleEntity>>

    @Query("UPDATE articles SET isSaved = 0 WHERE url =:url")
    suspend fun deleteArticle(url:String)

    @Query("UPDATE articles SET isSaved = 1 WHERE url =:url ")
    suspend fun saveArticle(url: String)
}