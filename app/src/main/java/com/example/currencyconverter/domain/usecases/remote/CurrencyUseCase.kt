package com.example.currencyconverter.domain.usecases.remote

import com.example.currencyconverter.data.model.Rate
import com.example.currencyconverter.utils.UIState
import kotlinx.coroutines.flow.Flow

interface CurrencyUseCase {

    suspend fun getCurrencies(): Flow<UIState<Rate>>

}