package com.arjun.inbrief.ui.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arjun.inbrief.domain.model.TopHeadLinesModel
import com.arjun.inbrief.domain.repository.NewsRepository
import com.arjun.inbrief.ui.UIState.ArticleScreenUiState
import com.arjun.inbrief.ui.UIState.HomeScreenUiState
import com.arjun.inbrief.ui.UIState.SharedUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val newsRepo: NewsRepository,
    private val sharedUiState: SharedUiState
    ) : ViewModel() {

    private val _UiState = MutableStateFlow(HomeScreenUiState())
    val UiState: StateFlow<HomeScreenUiState> = _UiState


    init {
        loadTopHeadLines()
    }

    fun loadTopHeadLines() {
        viewModelScope.launch {
            _UiState.value = _UiState.value.copy(isLoading = true)

            try {
                val news = newsRepo.getTopHeadLines()
                _UiState.value = _UiState.value.copy(isLoading = false, articles = news.articles)
            } catch (E: Exception) {
                _UiState.value = _UiState.value.copy(isLoading = false, error = E.toString())
            }
        }
    }

    fun updateSelectedArticle(choosedArticle: TopHeadLinesModel.Article) {
        sharedUiState._selectedArticle.value = choosedArticle
    }
}