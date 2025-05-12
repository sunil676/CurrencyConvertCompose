package com.example.currencyconverter.data.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CurrencyRatesConverter {
    @TypeConverter
    fun fromCurrencyRatesMap(map: Map<String, Float>?): String {
        return Gson().toJson(map)
    }

    @TypeConverter
    fun toCurrencyRatesMap(json: String): Map<String, Float>? {
        val type = object : TypeToken<Map<String, Float>>() {}.type
        return Gson().fromJson(json, type)
    }
}