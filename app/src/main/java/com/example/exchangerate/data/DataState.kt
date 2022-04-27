package com.example.exchangerate.data

sealed class DataState {
    object Loading : DataState()
    data class Success<out T : Any>(val body: T) : DataState()
    data class Error(val msg: String) : DataState()
}