package com.example.exchangerate.mappers

import com.example.exchangerate.data.cache.RateEntity
import com.example.exchangerate.data.network.ExchangeDto

fun ExchangeDto.toExchangeEntity(): List<RateEntity> {
    val rateList = mutableListOf<RateEntity>()
    for (r in rates.entries) {
        rateList.add(RateEntity(currency = r.key, rate = r.value, selected = false))
    }
    return rateList
}