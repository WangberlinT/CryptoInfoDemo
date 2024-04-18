package com.tiki.cryptoinfodemo.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CurrencyInfoDao {

    @Query("SELECT * FROM currency_info")
    suspend fun getAll(): List<CurrencyInfo>


    @Query("SELECT * FROM currency_info WHERE type = 'CRYPTO'")
    suspend fun getAllCryptoCurrencies(): List<CurrencyInfo>

    suspend fun getAllFiatCurrencies(): List<CurrencyInfo> {
        TODO()
    }

    suspend fun searchInCryptoCurrencies(query: String): List<CurrencyInfo> {
        TODO()
    }

    suspend fun searchInFiatCurrencies(query: String): List<CurrencyInfo> {
        TODO()
    }

    suspend fun search(query: String): List<CurrencyInfo> {
        TODO()
    }


    @Query("DELETE FROM currency_info")
    suspend fun deleteAll()

    @Insert
    suspend fun insertAll(currencyInfo: List<CurrencyInfo>)

}