package com.example.exchangerate.ui.sort

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exchangerate.data.cache.SortedState
import com.example.exchangerate.data.repositories.ExchangeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SortFragmentViewModel @Inject constructor(
    private val repository: ExchangeRepository
): ViewModel() {


    init {
        viewModelScope.launch {
           repository.readFlag().collect {
               when(it) {
                   SortedState.ALPHABET_ACS.value -> {
                       _flag.value = SortedState.ALPHABET_ACS
                   }
                   SortedState.ALPHABET_DESC.value -> {
                       _flag.value = SortedState.ALPHABET_DESC
                   }
                   SortedState.VALUE_ACS.value -> {
                       _flag.value = SortedState.VALUE_ACS
                   }
                   SortedState.VALUE_DESC.value -> {
                       _flag.value = SortedState.VALUE_DESC
                   }
               }
           }
        }
    }
    private val _flag = MutableStateFlow<SortedState?>(null)
    val flag = _flag.asStateFlow()

    fun setFlag(sortedState: SortedState) {
        repository.insertFlag(sortedState)
    }
}