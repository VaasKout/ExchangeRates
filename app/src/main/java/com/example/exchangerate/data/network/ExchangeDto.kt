package com.example.exchangerate.data.network

import com.google.gson.annotations.SerializedName

data class ExchangeDto(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("base")
    val base: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("rates")
    val rates: HashMap<String, Double>
)