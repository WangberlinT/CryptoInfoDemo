package com.tiki.cryptoinfodemo.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_info")
data class CurrencyInfo(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "symbol") val symbol: String,
    @ColumnInfo(name = "code") val code: String?,
    @ColumnInfo(name = "type") val type: CurrencyType,
)

enum class CurrencyType {
    CRYPTO,
    FIAT
}