package com.arjuna.inbrief.domain.repository

import com.arjuna.inbrief.domain.model.TopHeadLinesModel
import kotlinx.coroutines.flow.Flow


interface NewsRepository {
    suspend fun getTopHeadLines(category: String = "general"): TopHeadLinesModel

    suspend fun getSearchResult(input: String): TopHeadLinesModel

    fun getSavedArticles(): Flow<List<TopHeadLinesModel.Article>>
    suspend fun saveArticle(url: String)
    suspend fun deleteArticle(url: String)
}