package com.example.currencyconverter.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.currencyconverter.data.model.CurrencyRatesConverter
import com.example.currencyconverter.data.model.Rate

@TypeConverters(
    CurrencyRatesConverter::class
)
@Database(
    entities = [Rate::class],
    version = 1,
    exportSchema = false
)
abstract class CurrencyDataBase : RoomDatabase() {

    abstract fun rateDao(): RateDao

    companion object {
        const val DATABASE_NAME = "currency_database"
    }
}