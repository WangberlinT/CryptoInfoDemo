package com.tiki.cryptoinfodemo.data

import androidx.room.Database

@Database(entities = [CurrencyInfo::class], version = 1)
abstract class CurrencyInfoDatabase {
    abstract fun currencyInfoDao(): CurrencyInfoDao
}