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

    @Query("SELECT * FROM currency_info WHERE type = 'FIAT'")
    suspend fun getAllFiatCurrencies(): List<CurrencyInfo>


    suspend fun searchInCryptoCurrencies(query: String): List<CurrencyInfo> {
        TODO()
    }

    suspend fun searchInFiatCurrencies(query: String): List<CurrencyInfo> {
        TODO()
    }

    @Query(
        "SELECT * FROM currency_info " +
                "WHERE name LIKE :pattern || '%' OR name LIKE '% ' || :pattern || '%'" +
                "OR symbol LIKE :pattern || '%' OR symbol LIKE '% ' || :pattern || '%'" +
                "OR code LIKE :pattern || '%' OR code LIKE '% ' || :pattern || '%'"
    )
    suspend fun search(pattern: String): List<CurrencyInfo>


    @Query("DELETE FROM currency_info")
    suspend fun deleteAll()

    @Insert
    suspend fun insertAll(currencyInfo: List<CurrencyInfo>)

}