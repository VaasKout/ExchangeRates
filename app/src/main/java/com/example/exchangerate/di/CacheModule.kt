package com.example.exchangerate.di

import android.content.Context
import androidx.room.Room
import com.example.exchangerate.data.cache.ExchangeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {
    @Provides
    fun provideExchangeDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            ExchangeDatabase::class.java,
            "exchange_db"
        ).build()


    @Provides
    fun provideExchangeDao(exchangeDatabase: ExchangeDatabase) = exchangeDatabase.exchangeDao()
}