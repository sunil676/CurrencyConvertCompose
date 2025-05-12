package com.example.currencyconverter.data.remote

import com.example.currencyconverter.data.model.ApiResponse
import com.example.currencyconverter.utils.UIState
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface ApiService {

    @GET("latest.json")
    suspend fun getCurrencies(@Query("app_id", encoded = true) apiKey: String = "47bf529317304aa8b3cd5d9c06bbe323"): ApiResponse
}