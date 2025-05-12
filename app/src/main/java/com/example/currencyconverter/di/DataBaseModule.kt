package com.example.currencyconverter.di

import android.content.Context
import androidx.room.Room
import com.example.currencyconverter.data.local.CurrencyDataBase
import com.example.currencyconverter.data.local.DatabaseService
import com.example.currencyconverter.domain.usecases.local.DataBaseUseCaseImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

     @Provides
     fun provideDatabase(@ApplicationContext context: Context): CurrencyDataBase {
         return Room.databaseBuilder(
             context,
             CurrencyDataBase::class.java,
             "rate_database"
         ).build()
     }

    @Provides
    fun provideDatabaseService(currencyDataBase: CurrencyDataBase): DatabaseService {
        return DataBaseUseCaseImp(currencyDataBase)
    }


}