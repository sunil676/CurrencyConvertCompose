package com.example.currencyconverter.data.local

import com.example.currencyconverter.data.model.Rate
import kotlinx.coroutines.flow.Flow

interface DatabaseService {

    suspend fun getAllRates(): Flow<Rate>

    suspend fun insertRate(rate: Rate)

    suspend fun deleteAllRate()
}