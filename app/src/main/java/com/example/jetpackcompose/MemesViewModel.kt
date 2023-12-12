package com.example.jetpackcompose

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MemesViewModel:ViewModel() {
    private val repo = MemesRepository()
    private val _uiState = MutableLiveData<UiState>(UiState.EmptyList)
    val uiState: LiveData<UiState> = _uiState

    fun getMemes() {
        viewModelScope.launch (Dispatchers.IO) {
            val response = repo.getMemes()

            if (response.isSuccessful) {
               response.body()!!.let {
                   _uiState.postValue(UiState.FilledList(it.data.memes))
               }
            }
        }
    }
}

sealed class UiState {
    data object EmptyList:UiState()
    class FilledList(val memes:List<Memes>):UiState()
}