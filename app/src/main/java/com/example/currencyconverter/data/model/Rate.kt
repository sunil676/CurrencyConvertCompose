package com.example.currencyconverter.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "rate")
data class Rate(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rate_id")
    val id: Int =0,
    @ColumnInfo(name = "base")
    val base: String ="",
    @TypeConverters(CurrencyRatesConverter::class)
    val currencyRates: Map<String, Float>? = null,
)