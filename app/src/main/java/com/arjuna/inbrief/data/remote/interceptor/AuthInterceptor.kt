package com.arjuna.inbrief.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/*

class AuthInterceptor(private val apiKey: String): Interceptor { //inherited from interceptor lib

    override fun intercept(chain: Interceptor.Chain): Response { // using this method we can do interception.it has chain which contains original request, the ability to modify it continue the request
        val originalRequest = chain.request() // it contains actual request build by retrofit. it includes URL, Http method, headers and body (if any). its immutable we can't modify it

        val newUrl = originalRequest.url.newBuilder() // newBuilder is used to create mutable copy of http url
            .addQueryParameter("token", apiKey) // adding query parms to url. gnews uses token and applying api key to token
                                                                                                  //before : https://gnews.io/api/v4/top-headlines  after :https://gnews.io/api/v4/top-headlines?token=YOUR_API_KEY
            .build() //finalizes the new url and build it

        val newRequest = originalRequest.newBuilder() // creating a new request with the new URL
            .url(newUrl) // attaching new url
            .build() // build it

        return chain.proceed(newRequest) // returning new url . proceed is must without it. new request never executes.
    }

}
 */

class AuthInterceptor (private val apiKey: String): Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalReq = chain.request()

        val addingKey = originalReq.url.newBuilder()
            .addQueryParameter("token",apiKey)
            .build()
        val newReq = originalReq.newBuilder()
            .url(addingKey)
            .build()
        return chain.proceed(newReq)
    }
}
