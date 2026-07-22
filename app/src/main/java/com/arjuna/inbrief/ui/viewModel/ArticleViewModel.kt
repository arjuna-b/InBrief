package com.arjuna.inbrief.ui.viewModel

import androidx.lifecycle.ViewModel
import com.arjuna.inbrief.ui.UIState.SharedUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
     sharedUiState: SharedUiState
) : ViewModel() {

   val selectedArticle  = sharedUiState.selectedArticle



}