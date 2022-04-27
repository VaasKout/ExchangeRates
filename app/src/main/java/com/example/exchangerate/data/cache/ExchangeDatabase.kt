package com.example.exchangerate.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [RateEntity::class],
    version = 1,
    exportSchema = true
)
abstract class ExchangeDatabase: RoomDatabase() {
    abstract fun exchangeDao(): ExchangeDao
}