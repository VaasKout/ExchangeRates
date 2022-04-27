package com.example.exchangerate.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exchangerate.data.repositories.ExchangeRepository
import com.example.exchangerate.model.ExchangeRate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: ExchangeRepository
): ViewModel() {

    init {
        getFavoriteItems()
    }

    private val _favoriteItems = MutableStateFlow<List<ExchangeRate>?>(null)
    val favoriteItems = _favoriteItems.asStateFlow()

    fun getFavoriteItems() {
        viewModelScope.launch {
            repository.getFavoriteRatesByFlag().collect {
                _favoriteItems.value = it
            }
        }
    }

}