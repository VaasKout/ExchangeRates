package com.example.exchangerate.data.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rate_table")
data class RateEntity(
    @PrimaryKey val currency: String,
    @ColumnInfo(name = "rate") val rate: Double,
    @ColumnInfo(name = "selected") val selected: Boolean
)