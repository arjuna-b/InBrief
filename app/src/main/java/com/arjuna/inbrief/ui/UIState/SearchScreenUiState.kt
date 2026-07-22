package com.arjuna.inbrief.ui.UIState

import com.arjuna.inbrief.domain.model.TopHeadLinesModel

data class SearchScreenUiState(
    val isLoading : Boolean = true,
    val data : List<TopHeadLinesModel.Article> = emptyList(),
    val isError : String? = null,
)
