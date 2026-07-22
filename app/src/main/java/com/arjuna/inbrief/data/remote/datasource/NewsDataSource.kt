package com.arjuna.inbrief.data.remote.datasource

import com.arjuna.inbrief.BuildConfig
import com.arjuna.inbrief.data.remote.RetrofitClient
import com.arjuna.inbrief.data.remote.api.NewsApiService
import javax.inject.Inject



class NewsDataSource @Inject constructor(){
    private val api: NewsApiService = RetrofitClient.createRetrofitClient(BuildConfig.API_KEY).create(NewsApiService::class.java)
    suspend fun getHeadLines(category: String) = api.getTopHeadLines( category )
    suspend fun getSearchResults (input : String) = api.getSearchResults(input)
}