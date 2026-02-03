package com.arjun.inbrief.domain.repository

import com.arjun.inbrief.data.dto.TopHeadLinesDto
import com.arjun.inbrief.data.local.ArticleEntity
import com.arjun.inbrief.domain.model.TopHeadLinesModel
import kotlinx.coroutines.flow.Flow


interface NewsRepository {
    suspend fun getTopHeadLines(category: String = "general"): TopHeadLinesModel

    suspend fun getSearchResult(input : String) : TopHeadLinesModel

     fun getSavedArticles() : Flow<List<TopHeadLinesModel.Article>>
    suspend fun saveArticle(url:String)
    suspend fun deleteArticle(url:String)
}