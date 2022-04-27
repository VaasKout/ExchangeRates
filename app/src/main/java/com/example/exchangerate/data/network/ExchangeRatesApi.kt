package com.example.exchangerate.data.network

import com.example.exchangerate.utils.API_KEY
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ExchangeRatesApi {

    @Headers("apikey:${API_KEY}")
    @GET("latest")
    suspend fun getLatest(
        @Query("base") base: String = "USD",
    ): ExchangeDto
}