package com.arjuna.inbrief.data.repository

import android.util.Log
import com.arjuna.inbrief.data.local.articleDAO
import com.arjuna.inbrief.data.mapper.toDomain
import com.arjuna.inbrief.data.mapper.toEntity
import com.arjuna.inbrief.data.remote.datasource.NewsDataSource
import com.arjuna.inbrief.domain.model.TopHeadLinesModel
import com.arjuna.inbrief.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

        return try {
            val remote = dataSource.getHeadLines(category = category)

            val entities =
                remote.articles?.mapNotNull { dto -> dto?.toEntity(category) } ?: emptyList()
            if (entities.isNotEmpty()){
            dao.loadArticles(entities)
            }

            Log.e("RepoImpl - network", cache.size.toString())


            TopHeadLinesModel(
                articles = cache.map { it.toDomain() },
                totalArticles = cache.size,
                information = "from internet"
            )


        } catch (E: Exception) {
//            val cache = dao.getAllArticles()
            Log.e("RepoImpl - cache", cache.size.toString())

            TopHeadLinesModel(
                articles = cache.map { it.toDomain() },
                totalArticles = cache.size,
                information = "from cache"
            )


        }

//        if (cache.isNotEmpty()){

//        }
//        return TopHeadLinesModel(
//            articles = entities.map { it.toDomain() },
//            information = remote.information?.realTimeArticles?.message.orEmpty(),
//            totalArticles = remote.totalArticles ?: 0
//        )
//        dao.loadArticles()
//
//        return remote.toDomain()

//        return dto.toDomain()
    }

    override suspend fun getSearchResult(input: String): TopHeadLinesModel {
        val dto = dataSource.getSearchResults(input)
        return dto.toDomain()
    }

    override fun getSavedArticles(): Flow<List<TopHeadLinesModel.Article>> {
        val dao = dao.getSavedArticles()
        return dao.map { list -> list.map { it.toDomain() } }
    }

    override suspend fun saveArticle(url: String) {
        dao.saveArticle(url)
    }

    override suspend fun deleteArticle(url: String) {
        dao.deleteArticle(url)
    }
}