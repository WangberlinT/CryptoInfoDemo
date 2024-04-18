package com.tiki.cryptoinfodemo.domain

interface LocalCurrencyRepository {

    suspend fun getAllCryptoCurrencies(): List<CryptoCurrency>

    suspend fun getAllFiatCurrencies(): List<FiatCurrency>

    suspend fun getAllCurrencies(): List<Currency>

    suspend fun searchInCryptoCurrencies(query: String): List<CryptoCurrency>

    suspend fun searchInFiatCurrencies(query: String): List<FiatCurrency>

    suspend fun search(query: String): List<Currency>

    suspend fun insertAllCurrencies(currencies: List<Currency>)

    suspend fun removeAllCurrencies()
}