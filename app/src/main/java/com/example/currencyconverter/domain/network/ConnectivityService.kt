package com.example.currencyconverter.domain.network

import kotlinx.coroutines.flow.Flow

interface ConnectivityService {

    fun isNetworkAvailable(): Flow<Boolean>
}