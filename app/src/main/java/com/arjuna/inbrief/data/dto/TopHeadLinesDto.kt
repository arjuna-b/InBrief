package com.arjuna.inbrief.data.dto


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

/*
why DTO?
DTO stands for data transfer object which is exact replica of json api structure.
DTOs are isolated from Ui and Domain.
DTO contains nullables because there are more chances of API returns null. these nulls are removed in domain model
Why this is correct ✅

DTO (Data Transfer Object) rules:

Mirrors API JSON structure

Used only for network layer

Can change if API changes

Should NOT contain business logic
 */



@Keep
data class TopHeadLinesDto(
    @SerializedName("articles")
    val articles: List<Article?>? = null,
    @SerializedName("information")
    val information: Information? = null,
    @SerializedName("totalArticles")
    val totalArticles: Int? = null
) {
    @Keep
    data class Article(
        @SerializedName("content")
        val content: String? = null,
        @SerializedName("description")
        val description: String? = null,
        @SerializedName("id")
        val id: String? = null,
        @SerializedName("image")
        val image: String? = null,
        @SerializedName("lang")
        val lang: String? = null,
        @SerializedName("publishedAt")
        val publishedAt: String? = null,
        @SerializedName("source")
        val source: Source? = null,
        @SerializedName("title")
        val title: String? = null,
        @SerializedName("url")
        val url: String? = null
    ) {
        @Keep
        data class Source(
            @SerializedName("id")
            val id: String? = null,
            @SerializedName("name")
            val name: String? = null,
            @SerializedName("url")
            val url: String? = null
        )
    }

    @Keep
    data class Information(
        @SerializedName("realTimeArticles")
        val realTimeArticles: RealTimeArticles? = null
    ) {
        @Keep
        data class RealTimeArticles(
            @SerializedName("message")
            val message: String? = null
        )
    }
}