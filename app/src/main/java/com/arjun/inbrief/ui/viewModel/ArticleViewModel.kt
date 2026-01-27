package com.arjun.inbrief.ui.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.arjun.inbrief.domain.model.TopHeadLinesModel
import com.arjun.inbrief.ui.UIState.ArticleScreenUiState
import com.arjun.inbrief.ui.UIState.SharedUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
     sharedUiState: SharedUiState
) : ViewModel() {

   val selectedArticle  = sharedUiState.selectedArticle



}