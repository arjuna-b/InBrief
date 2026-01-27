package com.arjun.inbrief.ui.UIState

import com.arjun.inbrief.domain.model.TopHeadLinesModel
import com.arjun.inbrief.ui.screens.Article

data class ArticleScreenUiState(
    val article : TopHeadLinesModel.Article = TopHeadLinesModel.Article(
        content = "",
        description = "",
        id = "",
        image = "",
        lang = "",
        publishedAt = "",
        source = "",
        title = "",
        url = "",
    )
)
