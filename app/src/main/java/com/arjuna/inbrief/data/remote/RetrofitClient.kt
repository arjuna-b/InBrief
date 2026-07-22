package com.arjuna.inbrief.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/*
RetrofitClient is responsible for creating a configured Retrofit instance that:

Knows the base API URL

Uses your custom OkHttpClient (with interceptors & timeouts)

Uses Gson to convert JSON ↔ Kotlin objects

👉 This is what your API interfaces will use.
    "https://gnews.io/api/v4/top-headlines?category=general&lang=en&country=us&max=10&apikey=17b1ebe130471e6b5cf746462b50dcf8"

 */


object RetrofitClient {
    private val BASE_URL = "https://gnews.io/api/v4/" //base url it must ends with /

    fun createRetrofitClient(apiKey: String): Retrofit{ //retrofit client
        return Retrofit.Builder() //retrofit is immutable so we need builder to configure it before using.
            .baseUrl(BASE_URL) // base url
            .client(NetworkModule.provideHttpClient(apiKey)) //attaching ok http client This client already has:
                                                                                                    //        AuthInterceptor → adds token
                                                                                                    //        LoggingInterceptor → logs request/response
                                                                                                    //        Timeouts configured
            .addConverterFactory(
                GsonConverterFactory.create(NetworkModule.provideGson())
            )
            .build()

    }
}