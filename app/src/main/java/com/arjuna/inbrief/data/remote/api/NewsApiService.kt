package com.arjuna.inbrief.data.remote.api

import com.arjuna.inbrief.data.dto.TopHeadLinesDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("top-headlines")
    suspend fun getTopHeadLines(
        @Query("category") topic : String = "general",
//        @Query("country") country : String = "in",
        @Query("lang") lang : String = "en",
    ): TopHeadLinesDto

    @GET("search")
    suspend fun getSearchResults(
        @Query("q") searchInput : String,
        @Query("lang") lang : String = "en",
    ) : TopHeadLinesDto
}