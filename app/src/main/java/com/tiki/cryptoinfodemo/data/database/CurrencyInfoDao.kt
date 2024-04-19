package com.tiki.cryptoinfodemo.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Data Access Object for the currency_info table.
 * suspend functions are used to make sure the operations are done in Room Coroutine.
 */
@Dao
interface CurrencyInfoDao {

    @Query("SELECT * FROM currency_info")
    suspend fun getAll(): List<CurrencyInfo>


    @Query("SELECT * FROM currency_info WHERE type = 'CRYPTO'")
    suspend fun getAllCryptoCurrencies(): List<CurrencyInfo>

    @Query("SELECT * FROM currency_info WHERE type = 'FIAT'")
    suspend fun getAllFiatCurrencies(): List<CurrencyInfo>

    @Query(
        "SELECT * FROM currency_info " +
                "WHERE (name LIKE :query || '%' OR name LIKE '% ' || :query || '%'" +
                "OR symbol LIKE :query || '%' OR symbol LIKE '% ' || :query || '%'" +
                "OR code LIKE :query || '%' OR code LIKE '% ' || :query || '%')" +
                "AND type = 'CRYPTO'"
    )
    suspend fun searchInCryptoCurrencies(query: String): List<CurrencyInfo>

    @Query(
        "SELECT * FROM currency_info " +
                "WHERE (name LIKE :query || '%' OR name LIKE '% ' || :query || '%'" +
                "OR symbol LIKE :query || '%' OR symbol LIKE '% ' || :query || '%'" +
                "OR code LIKE :query || '%' OR code LIKE '% ' || :query || '%')" +
                "AND type = 'FIAT'"
    )
    suspend fun searchInFiatCurrencies(query: String): List<CurrencyInfo>

    @Query(
        "SELECT * FROM currency_info " +
                "WHERE name LIKE :pattern || '%' OR name LIKE '% ' || :pattern || '%'" +
                "OR symbol LIKE :pattern || '%' OR symbol LIKE '% ' || :pattern || '%'" +
                "OR code LIKE :pattern || '%' OR code LIKE '% ' || :pattern || '%'"
    )
    suspend fun search(pattern: String): List<CurrencyInfo>


    @Query("DELETE FROM currency_info")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(currencyInfo: List<CurrencyInfo>)

}