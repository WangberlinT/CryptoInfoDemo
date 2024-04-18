package com.tiki.cryptoinfodemo.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CurrencyInfoDao {

    @Query("SELECT * FROM currency_info")
    fun getAll(): List<CurrencyInfo>

    @Insert
    fun insertAll(currencyInfo: List<CurrencyInfo>)
}