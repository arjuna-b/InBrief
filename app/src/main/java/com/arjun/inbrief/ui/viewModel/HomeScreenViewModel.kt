package com.arjun.inbrief.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arjun.inbrief.domain.model.TopHeadLinesModel
import com.arjun.inbrief.domain.repository.NewsRepository
import com.arjun.inbrief.ui.UIState.HomeScreenUiState
import com.arjun.inbrief.ui.UIState.SharedUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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