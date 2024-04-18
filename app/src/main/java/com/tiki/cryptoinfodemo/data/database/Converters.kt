package com.tiki.cryptoinfodemo.data.database

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromCurrencyType(value: CurrencyType): String {
        return value.name
    }

    @TypeConverter
    fun toCurrencyType(value: String): CurrencyType {
        return CurrencyType.valueOf(value)
    }
}
