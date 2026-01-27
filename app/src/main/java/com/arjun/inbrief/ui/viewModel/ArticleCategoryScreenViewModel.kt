package com.arjun.inbrief.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arjun.inbrief.data.repository.NewsRepositoryImpl
import com.arjun.inbrief.ui.UIState.ArticleCategoryUiState
import com.arjun.inbrief.ui.UIState.HomeScreenUiState
import com.arjun.inbrief.ui.UIState.SharedUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleCategoryScreenViewModel @Inject constructor(
    sharedUiState: SharedUiState,
    private val newsRepositoryImpl: NewsRepositoryImpl
) : ViewModel() {

        val selectedCategory = sharedUiState.categoryTitle

        private val _data = MutableStateFlow(ArticleCategoryUiState())
        val data : StateFlow<ArticleCategoryUiState> = _data

        init {
            viewModelScope.launch {
                selectedCategory.collect { newValue ->
                    getNewsOfSelectedCategory(newValue)
                }
            }
        }


     fun getNewsOfSelectedCategory(newCategory: String){
         _data.value = _data.value.copy(isLoading = true)
         viewModelScope.launch {
             try {
                 val news = newsRepositoryImpl.getTopHeadLines(newCategory)
                 _data.value = _data.value.copy(data = news.articles, isLoading = false)
             }catch (Ex: Exception){
                _data.value = _data.value.copy(data =  emptyList(), isLoading = false, isError =  Ex.toString())
             }
         }


    }
}