package com.arjun.inbrief.ui.viewModel

import androidx.lifecycle.ViewModel
import com.arjun.inbrief.domain.model.TopHeadLinesModel
import com.arjun.inbrief.ui.UIState.SharedUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val sharedUiState: SharedUiState
) : ViewModel() {



    fun updateTitleInActionBar(title:String){
        sharedUiState._categoryTitle.value = title
    }




}