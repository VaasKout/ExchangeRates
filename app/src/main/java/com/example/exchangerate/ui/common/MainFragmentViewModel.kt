package com.example.exchangerate.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exchangerate.data.DataState
import com.example.exchangerate.data.repositories.ExchangeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val repository: ExchangeRepository
): ViewModel() {

    var scrollToTopEnabled = false
    private val _currencyValue = MutableStateFlow("USD")
    val currencyValue = _currencyValue.asStateFlow()

    private val _remoteState = MutableStateFlow<DataState?>(null)
    val remoteState = _remoteState.asStateFlow()

    init {
        _currencyValue.value = repository.readCurrency()
    }

    fun setCurrency(currency: String) {
        repository.insertCurrency(currency)
        _currencyValue.value = repository.readCurrency()
    }

    fun loadRemoteExchangeItems() {
        viewModelScope.launch {
            repository.loadRemoteLatestItems().collect {
                _remoteState.value = it
            }
        }
    }

}