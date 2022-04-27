package com.example.exchangerate.data.cache

import androidx.room.*

@Dao
interface ExchangeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRateList(rateList: List<RateEntity>)

    @Query("UPDATE rate_table SET rate = :rate WHERE currency = :currency")
    suspend fun updateRateEntity(currency: String, rate: Double)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateRateEntity(rateEntity: RateEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRate(rateEntity: RateEntity)

    @Query("SELECT * FROM rate_table")
    suspend fun getAllRates(): List<RateEntity>

    @Query("SELECT * FROM rate_table ORDER BY " +
            "CASE WHEN :flag = 1 THEN currency END ASC, " +
            "CASE WHEN :flag = 2 THEN currency END DESC, " +
            "CASE WHEN :flag = 3 THEN rate END ASC, " +
            "CASE WHEN :flag = 4 THEN rate END DESC")
    suspend fun getAllRatesByFlag(flag: Int): List<RateEntity>

    @Query("SELECT * FROM rate_table WHERE selected = 1 ORDER BY " +
                "CASE WHEN :flag = 1 THEN currency END ASC, " +
                "CASE WHEN :flag = 2 THEN currency END DESC, " +
                "CASE WHEN :flag = 3 THEN rate END ASC, " +
                "CASE WHEN :flag = 4 THEN rate END DESC")
    suspend fun getAllFavoriteRatesByFlag(flag: Int): List<RateEntity>
}