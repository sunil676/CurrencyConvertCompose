package com.example.currencyconverter.di

import android.content.Context
import com.example.currencyconverter.data.local.DatabaseService
import com.example.currencyconverter.data.remote.ApiService
import com.example.currencyconverter.domain.network.ConnectivityService
import com.example.currencyconverter.domain.network.RealConnectivityService
import com.example.currencyconverter.domain.usecases.remote.CurrencyUseCase
import com.example.currencyconverter.domain.usecases.remote.CurrencyUseCaseImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideCurrencyRepository(
        databaseService: DatabaseService,
        apiService: ApiService
    ): CurrencyUseCase {
        return CurrencyUseCaseImp(databaseService, apiService)
    }

    @Provides
    fun provideRealConnectivityService(
        @ApplicationContext appContext: Context
    ): ConnectivityService {
        return RealConnectivityService(appContext)
    }
}