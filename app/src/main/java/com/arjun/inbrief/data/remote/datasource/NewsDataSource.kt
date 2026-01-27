package com.arjun.inbrief.data.remote.datasource

import com.arjun.inbrief.BuildConfig
import com.arjun.inbrief.data.remote.RetrofitClient
import com.arjun.inbrief.data.remote.api.NewsApiService
import javax.inject.Inject



class NewsDataSource @Inject constructor(){
    private val api: NewsApiService = RetrofitClient.createRetrofitClient(BuildConfig.API_KEY).create(NewsApiService::class.java)
    suspend fun getHeadLines(category: String) = api.getTopHeadLines( category )
    suspend fun getSearchResults (input : String) = api.getSearchResults(input)
}