package com.example.exchangerate.mappers

import com.example.exchangerate.data.cache.RateEntity
import com.example.exchangerate.model.ExchangeRate

fun ExchangeRate.toRateEntity(select: Boolean = selected) = RateEntity(currency, rate, select)