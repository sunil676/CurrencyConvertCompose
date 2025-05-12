package com.example.currencyconverter.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.currencyconverter.data.model.Rate
import kotlinx.coroutines.flow.Flow

@Dao
interface RateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRate(rate: Rate)

    @Query("SELECT * FROM rate")
    fun getAllRates(): Flow<Rate>

    @Delete
    suspend fun deleteRate(rate: Rate)

    @Query("DELETE FROM rate")
    suspend fun deleteAllRate()

}