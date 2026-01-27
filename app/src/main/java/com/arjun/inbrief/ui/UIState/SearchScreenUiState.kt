package com.arjun.inbrief.ui.UIState

import com.arjun.inbrief.domain.model.TopHeadLinesModel

data class SearchScreenUiState(
    val isLoading : Boolean = true,
    val data : List<TopHeadLinesModel.Article> = emptyList(),
    val isError : String? = null,
)
