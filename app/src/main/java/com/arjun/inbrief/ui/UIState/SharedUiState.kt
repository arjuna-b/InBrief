package com.arjun.inbrief.ui.UIState

import com.arjun.inbrief.domain.model.TopHeadLinesModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedUiState @Inject constructor() {

     val _selectedArticle = MutableStateFlow(TopHeadLinesModel.Article())
    val selectedArticle : StateFlow<TopHeadLinesModel.Article> = _selectedArticle

    val _categoryTitle = MutableStateFlow("")
    val categoryTitle : StateFlow<String> = _categoryTitle


}