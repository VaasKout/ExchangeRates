package com.example.exchangerate.ui.popular

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
class PopularViewModel @Inject constructor(
    private val repository: ExchangeRepository,
) : ViewModel() {

    private val _exchangeList = MutableStateFlow<List<ExchangeRate>?>(null)
    val exchangeList = _exchangeList.asStateFlow()

    init {
        firstLoad()
    }

    private fun firstLoad() {
        viewModelScope.launch {
            repository.getAllRatesByFlag().collect {
                if (it.isNotEmpty()) {
                    _exchangeList.value = it
                }
            }
        }
    }


    fun getExchangeItems() {
        viewModelScope.launch {
            repository.getAllRatesByFlag().collect {
                _exchangeList.value = it
            }
        }
    }

    fun setItemSelected(item: ExchangeRate) {
        viewModelScope.launch {
            repository.insertPopularItem(item)
            getExchangeItems()
        }
    }

}