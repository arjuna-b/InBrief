package com.arjun.inbrief.ui.UIState

import com.arjun.inbrief.domain.model.TopHeadLinesModel

data class ArticleCategoryUiState(
    val isLoading : Boolean = true,
    val data : List<TopHeadLinesModel.Article> = emptyList(),
    val isError : String? = null
)
