package com.arjun.inbrief.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arjun.inbrief.data.repository.NewsRepositoryImpl
import com.arjun.inbrief.domain.model.TopHeadLinesModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class SavedScreenViewModel @Inject constructor(
    val repositoryImpl: NewsRepositoryImpl
) : ViewModel() {

    init {

    }



//    private val _savedArticles = StateFlow(HomeScreenUiState())
//    val savedArticles : StateFlow<HomeScreenUiState> = _savedArticles
//
//
    val getSavedArticles :  StateFlow<List<TopHeadLinesModel.Article>> =
         repositoryImpl.getSavedArticles().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList(),
        )

    suspend fun deleteArticle(url:String){
        repositoryImpl.deleteArticle(url)
    }
}