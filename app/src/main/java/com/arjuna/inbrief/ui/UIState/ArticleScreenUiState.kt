package com.arjuna.inbrief.ui.UIState

import com.arjuna.inbrief.domain.model.TopHeadLinesModel

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
