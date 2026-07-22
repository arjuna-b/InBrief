package com.arjuna.inbrief.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arjuna.inbrief.domain.model.TopHeadLinesModel
import com.arjuna.inbrief.domain.repository.NewsRepository
import com.arjuna.inbrief.ui.UIState.HomeScreenUiState
import com.arjuna.inbrief.ui.UIState.SharedUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val newsRepo: NewsRepository,
    private val sharedUiState: SharedUiState
) : ViewModel() {

    private val _UiState = MutableStateFlow(HomeScreenUiState())
    val UiState: StateFlow<HomeScreenUiState> = _UiState




    fun loadTopHeadLines() {
        viewModelScope.launch {
            try {
                _UiState.value = _UiState.value.copy(isLoading = true)
                val news = newsRepo.getTopHeadLines()
                _UiState.value = _UiState.value.copy(isLoading = false, articles = news.articles)
            } catch (E: Exception) {
                _UiState.value = _UiState.value.copy(
                    isLoading = false,
                    articles = emptyList(),
                    error = E.toString()
                )
            }
        }
    }

    fun updateSelectedArticle(choosedArticle: TopHeadLinesModel.Article) {
        sharedUiState._selectedArticle.value = choosedArticle
    }

    suspend fun saveArticle(url: String) {
        newsRepo.saveArticle(url)
    }

    val getSavedArticles: StateFlow<List<TopHeadLinesModel.Article>> =
        newsRepo.getSavedArticles().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList(),
        )


}