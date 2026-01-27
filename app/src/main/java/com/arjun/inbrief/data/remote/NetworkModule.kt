package com.arjun.inbrief.data.remote

import com.arjun.inbrief.data.remote.interceptor.AuthInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/*
NetworkModule is a central place where we create and configure networking-related objects.
responsible for creating fully configured okHttpClient  that automatically handles :

    Automatically adds the API key to every request

    Logs request & response data

    Applies timeouts

    Is reusable across the app

    👉 Retrofit will later use this OkHttpClient to make all network calls.
 */

/*


object NetworkModule { // why obj here? because its single ton only one instance for entire proj life cycle
    fun provideLoggingInterceptor(): HttpLoggingInterceptor { //HTTPLoggingInterceptor provides request and response details to log and used for debugging
        return HttpLoggingInterceptor().apply { // executes block on the object and returns same object

            /*
                if (BuildConfig.DEBUG) {
                    level = BODY
                } else {
                    level = NONE
                }
             */

            level =
                HttpLoggingInterceptor.Level.BODY //level controls how much data should be logged in consloe
            //None - no data, basic, body, headers
        }
    }

    fun provideOkHTTPClient(apiKey: String): OkHttpClient { // return fully configured  okhttp client
        return OkHttpClient.Builder() // it builds configuration before creating final object. and OKHTTPClient is immutable
            //order has significance please take care
            .addInterceptor(AuthInterceptor(apiKey)) // adding custom interceptor for authentication
            .addInterceptor(provideLoggingInterceptor()) // adding custom interceptor for logging
            .connectTimeout(
                30,
                TimeUnit.SECONDS
            ) //max time to establishTCP connection, performs DNS lookup and TLS handshake
            .readTimeout(
                30,
                TimeUnit.SECONDS
            ) // max time to wait for the response data after successful connection.
            .build()
    }
}
 */


object NetworkModule {
    fun provideLoggingInterceptor() : HttpLoggingInterceptor{
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    fun provideHttpClient(apiKey:String): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(apiKey))
            .addInterceptor(provideLoggingInterceptor())
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    fun provideGson(): Gson {
        return GsonBuilder() // allows Gson(which is immutable) to configure before its creation
            .setLenient() //by default Gson allows strict json rules like proper quotes, valid commas, no trailing commas and correct data types
                                //but lenient allows gson to parse non standard/ malfunctioned json such as single quotes, unescaped chars, missing values and improper formating
            .create() // create customized Gson 
    }
}
