package com.tiki.cryptoinfodemo.domain

interface LocalCurrencyRepository {

    suspend fun getAllCryptoCurrencies(): List<CryptoCurrency>

    suspend fun getAllFiatCurrencies(): List<FiatCurrency>

    suspend fun getAllCurrencies(): List<Currency>

    suspend fun insertAllCurrencies(currencies: List<Currency>)

    suspend fun removeAllCurrencies()
}