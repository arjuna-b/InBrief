package com.arjun.inbrief.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.arjun.inbrief.domain.model.TopHeadLinesModel.Article


@Entity(tableName = "articles")
data class ArticleEntity(

    @PrimaryKey
    val url: String,

    val title: String?,
    val description: String?,
    val content: String?,
    val image: String?,
    val source: String?,
    val publishedAt: String?,
    val lang: String?,

    val category: String?,
    val isSaved: Boolean = false
)
