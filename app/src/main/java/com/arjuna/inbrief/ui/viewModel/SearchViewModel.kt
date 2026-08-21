package com.arjuna.inbrief.ui.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arjuna.inbrief.data.repository.NewsRepositoryImpl
import com.arjuna.inbrief.ui.UIState.SearchScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val newsRepositoryImpl: NewsRepositoryImpl
) : ViewModel() {
    var searchInput = mutableStateOf("")

    private val _result = MutableStateFlow(SearchScreenUiState())
    val result: StateFlow<SearchScreenUiState> = _result

//    fun onTextChanged(){
//        if (searchInput.value.length > 4){
//            viewModelScope.launch {
//                getSearchResults(searchInput.value)
//            }
//        }
//
//    }

    suspend fun getSearchResults(input: String) {
        try {
            _result.value = _result.value.copy(isLoading = true)
            Log.e("TAG",input )
            val res = newsRepositoryImpl.getSearchResult(input)
            _result.value = _result.value.copy(isLoading = false, data = res.articles)
        }catch (Ex: Exception){
            Log.e("TAG",  Ex.toString())
            _result.value = _result.value.copy(isLoading = false, isError = Ex.localizedMessage)
        }
    }

}