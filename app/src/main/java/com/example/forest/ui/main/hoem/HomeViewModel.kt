package com.example.forest.ui.main.hoem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    repository: HomeRepository
) : ViewModel() {
    val articleList = repository.getArticleList().asLiveData().cachedIn(viewModelScope)
}