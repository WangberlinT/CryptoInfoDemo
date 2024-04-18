package com.tiki.cryptoinfodemo.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [CurrencyInfo::class], version = 1)
@TypeConverters(Converters::class)
abstract class CurrencyInfoDatabase : RoomDatabase() {
    abstract fun currencyInfoDao(): CurrencyInfoDao
}