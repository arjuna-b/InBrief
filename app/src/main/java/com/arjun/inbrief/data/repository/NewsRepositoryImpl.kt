package com.arjun.inbrief.data.repository

import com.arjun.inbrief.data.local.articleDAO
import com.arjun.inbrief.data.mapper.toDomain
import com.arjun.inbrief.data.mapper.toEntity
import com.arjun.inbrief.data.remote.datasource.NewsDataSource
import com.arjun.inbrief.domain.model.TopHeadLinesModel
import com.arjun.inbrief.domain.repository.NewsRepository
import javax.inject.Inject


/*
Repository is created per FEATURE / DOMAIN, not per API endpoint.
for example in this app news is a feature so only one repo per feature. suppose in a big application there are other modules like auth repository
notification repository, user repository

it will do : Call API
                Receive DTO
                Convert DTO → Domain using mapper
                Return Domain model


 */

class NewsRepositoryImpl @Inject constructor(
    private val dataSource: NewsDataSource,
    private val dao: articleDAO
) : NewsRepository {

    override suspend fun getTopHeadLines(category: String): TopHeadLinesModel {

        val cache = dao.getAllArticles()

        if (cache.isNotEmpty()){
            return TopHeadLinesModel(
                articles = cache.map { it.toDomain() },
                totalArticles = cache.size,
                information = "from cache"
            )
        }



        val remote = dataSource.getHeadLines(category = category)
        val entities = remote.articles?.mapNotNull { dto -> dto?.toEntity(category) } ?: emptyList()

        dao.loadArticles(entities)

        return TopHeadLinesModel(
            articles = entities.map { it.toDomain() },
            information = remote.information?.realTimeArticles?.message.orEmpty(),
            totalArticles = remote.totalArticles ?: 0
        )
//        dao.loadArticles()
//
//        return remote.toDomain()

//        return dto.toDomain()
    }

    override suspend fun getSearchResult(input: String): TopHeadLinesModel {
        val dto = dataSource.getSearchResults(input)
        return dto.toDomain()
    }
}