package com.arjun.inbrief.domain.repository

import com.arjun.inbrief.data.dto.TopHeadLinesDto
import com.arjun.inbrief.domain.model.TopHeadLinesModel



interface NewsRepository {
    suspend fun getTopHeadLines(category: String = "general"): TopHeadLinesModel

    suspend fun getSearchResult(input : String) : TopHeadLinesModel
}