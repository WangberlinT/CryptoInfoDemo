package com.tiki.cryptoinfodemo.domain

interface RemoteCurrencyRepository {
    suspend fun getAllCurrencies(): List<Currency>
}