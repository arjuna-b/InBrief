package com.arjun.inbrief.data.mapper

import com.arjun.inbrief.data.dto.TopHeadLinesDto
import com.arjun.inbrief.data.local.ArticleEntity
import com.arjun.inbrief.domain.model.TopHeadLinesModel
import com.arjun.inbrief.domain.model.TopHeadLinesModel.Article
import kotlin.String

/*
mapper decouples the api data from domain model.
Mapper Responsibility
Transform network data
Handle nulls safely
Combine / rename fields
No business logic

toDomain function : its similar to domainModel(dto)
whenever api call is done we have the api response in dto not in domain model. inorder to load
dto data to domain model you need to use toDomain conversion function which loads dto data into
domain model data class. simply
dto = what you receive
domain model = what you want
to domain = bridge
 */

fun TopHeadLinesDto.toDomain(): TopHeadLinesModel {
    return TopHeadLinesModel(
        articles = articles.toDomainArticles(),
        information = information?.realTimeArticles?.message.orEmpty(),
        totalArticles = totalArticles ?: 0
    )

}

fun TopHeadLinesDto.Article.toDomain(): Article {
    return Article(
        content = content.orEmpty(),
        description = description.orEmpty(),
        id = id.orEmpty(),
        image = image.orEmpty(),
        lang = lang.orEmpty(),
        publishedAt = publishedAt.orEmpty(),
        source = source?.name.orEmpty(),
        title = title.orEmpty(),
        url = url.orEmpty()
    )
}


fun List<TopHeadLinesDto.Article?>?.toDomainArticles(): List<Article> {
    return this?.filterNotNull()?.map { it.toDomain() } ?: emptyList()
}


fun TopHeadLinesDto.Article.toEntity(category: String) : ArticleEntity {
    return ArticleEntity(
        url = url ?: "",
        title = title,
        description = description,
        content = content,
        image = image,
        source = source?.name,
        publishedAt = publishedAt,
        lang = lang,
        category = category,
        isSaved = false
    )
}

fun ArticleEntity.toDomain() : Article {
    return Article(
        content = content.orEmpty(),
        description = description.orEmpty(),
        image = image.orEmpty(),
        lang = lang.orEmpty(),
        publishedAt = publishedAt.orEmpty(),
        source = source.orEmpty(),
        title = title.orEmpty(),
        url = url
    )
}

