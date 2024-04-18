package com.tiki.cryptoinfodemo.data

import com.tiki.cryptoinfodemo.domain.CryptoCurrency
import com.tiki.cryptoinfodemo.domain.Currency
import com.tiki.cryptoinfodemo.domain.FiatCurrency
import com.tiki.cryptoinfodemo.domain.LocalCurrencyRepository

class LocalCurrencyRepositoryImpl : LocalCurrencyRepository {
    override suspend fun getAllCryptoCurrencies(): List<CryptoCurrency> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllFiatCurrencies(): List<FiatCurrency> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllCurrencies(): List<Currency> {
        TODO("Not yet implemented")
    }

    override suspend fun searchInCryptoCurrencies(query: String): List<CryptoCurrency> {
        TODO("Not yet implemented")
    }

    override suspend fun searchInFiatCurrencies(query: String): List<FiatCurrency> {
        TODO("Not yet implemented")
    }

    override suspend fun search(query: String): List<Currency> {
        TODO("Not yet implemented")
    }

    override suspend fun insertAllCurrencies(currencies: List<Currency>) {
        TODO("Not yet implemented")
    }

    override suspend fun removeAllCurrencies() {
        TODO("Not yet implemented")
    }
}