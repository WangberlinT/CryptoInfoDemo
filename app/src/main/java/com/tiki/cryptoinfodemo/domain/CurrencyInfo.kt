package com.tiki.cryptoinfodemo.domain

data class CurrencyInfo(
    val id: String,
    val name: String,
    val symbol: String,
    val code: String?,
)