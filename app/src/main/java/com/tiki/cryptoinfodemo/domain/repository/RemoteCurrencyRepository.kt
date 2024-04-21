package com.tiki.cryptoinfodemo.domain.repository

import com.tiki.cryptoinfodemo.domain.model.Currency

interface RemoteCurrencyRepository {
    suspend fun getAllCurrencies(): List<Currency>
}