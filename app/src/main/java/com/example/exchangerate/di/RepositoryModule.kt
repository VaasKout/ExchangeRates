package com.example.exchangerate.di

import android.content.Context
import com.example.exchangerate.data.cache.ExchangeDao
import com.example.exchangerate.data.network.ExchangeRatesApi
import com.example.exchangerate.data.repositories.ExchangeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideExchangeRepository(
        exchangeDao: ExchangeDao,
        exchangeRatesApi: ExchangeRatesApi,
        @ApplicationContext context: Context
    ) = ExchangeRepository(exchangeDao, exchangeRatesApi, context)
}