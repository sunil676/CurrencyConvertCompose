package com.example.currencyconverter.domain.usecases.remote

import com.example.currencyconverter.data.local.DatabaseService
import com.example.currencyconverter.data.model.Rate
import com.example.currencyconverter.data.model.toRateEntity
import com.example.currencyconverter.data.remote.ApiService
import com.example.currencyconverter.utils.UIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyUseCaseImp @Inject constructor(
    private val databaseService: DatabaseService,
    private val apiService: ApiService
) : CurrencyUseCase {

    // Implement the methods from CurrencyUseCase interface here
    override suspend fun getCurrencies(): Flow<UIState<Rate>> {
        return flow {
            emit(UIState.Loading)
            try {
                val response = apiService.getCurrencies()
//                databaseService.getAllRates()
//                    .collect { rate ->
//                        emit(UIState.Success(rate))
//                    }
                databaseService.deleteAllRate()
                val rateEntity = response.toRateEntity()
                databaseService.insertRate(rateEntity)
                emit(UIState.Success(rateEntity))
            } catch (e: Exception) {
                emit(UIState.Error("Exception: ${e.message}"))
            }
        }
    }
}