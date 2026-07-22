package com.arjuna.inbrief.ui.UIState

import com.arjuna.inbrief.domain.model.TopHeadLinesModel

data class ArticleCategoryUiState(
    val isLoading : Boolean = true,
    val data : List<TopHeadLinesModel.Article> = emptyList(),
    val isError : String? = null
)
