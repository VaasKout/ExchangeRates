package com.example.exchangerate.mappers

import com.example.exchangerate.data.cache.RateEntity
import com.example.exchangerate.model.ExchangeRate


fun List<RateEntity>.toExchangeRate(): List<ExchangeRate> {
    val rateList = mutableListOf<ExchangeRate>()
    forEach { entity ->
        rateList.add(ExchangeRate(currency = entity.currency, rate = entity.rate, selected = entity.selected))
    }
    return rateList
}