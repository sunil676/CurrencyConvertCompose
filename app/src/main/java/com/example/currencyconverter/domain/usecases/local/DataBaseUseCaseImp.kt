package com.example.currencyconverter.domain.usecases.local

import com.example.currencyconverter.data.local.CurrencyDataBase
import com.example.currencyconverter.data.local.DatabaseService
import com.example.currencyconverter.data.model.Rate
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataBaseUseCaseImp @Inject constructor(private val currencyDataBase: CurrencyDataBase) : DatabaseService {

    override suspend fun getAllRates(): Flow<Rate> {
        return currencyDataBase.rateDao().getAllRates()
    }

    override suspend fun insertRate(rate: Rate) {
        currencyDataBase.rateDao().insertRate(rate)
    }

    override suspend fun deleteAllRate() {
        currencyDataBase.rateDao().deleteAllRate()
    }

}