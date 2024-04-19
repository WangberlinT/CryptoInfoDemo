package com.tiki.cryptoinfodemo.data

import com.tiki.cryptoinfodemo.domain.Currency
import com.tiki.cryptoinfodemo.domain.RemoteCurrencyRepository

class RemoteCurrencyRepositoryImpl : RemoteCurrencyRepository {
    override suspend fun getAllCurrencies(): List<Currency> {
        TODO("Not yet implemented")
    }
}