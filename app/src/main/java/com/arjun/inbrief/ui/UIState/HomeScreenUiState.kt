package com.arjun.inbrief.ui.UIState

import com.arjun.inbrief.domain.model.TopHeadLinesModel

data class HomeScreenUiState(
    val isLoading : Boolean = true,
    val articles : List<TopHeadLinesModel.Article> = emptyList(),
    val error : String? = null
)
