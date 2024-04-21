package com.tiki.cryptoinfodemo.domain.model

data class CryptoCurrency(
    val id: String,
    val name: String,
    val symbol: String,
) : Currency

data class FiatCurrency(
    val id: String,
    val name: String,
    val symbol: String,
    val code: String
) : Currency

sealed interface Currency