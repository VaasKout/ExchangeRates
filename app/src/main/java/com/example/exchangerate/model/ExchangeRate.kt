package com.example.exchangerate.model

data class ExchangeRate(
    val currency: String,
    val rate: Double,
    val selected: Boolean
)