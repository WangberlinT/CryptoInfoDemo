package com.tiki.cryptoinfodemo.domain.repository

import com.tiki.cryptoinfodemo.domain.model.CryptoCurrency
import com.tiki.cryptoinfodemo.domain.model.Currency
import com.tiki.cryptoinfodemo.domain.model.FiatCurrency

interface LocalCurrencyRepository {

    suspend fun getAllCryptoCurrencies(): List<CryptoCurrency>

    suspend fun getAllFiatCurrencies(): List<FiatCurrency>

    suspend fun getAllCurrencies(): List<Currency>

    suspend fun insertAllCurrencies(currencies: List<Currency>)

    suspend fun removeAllCurrencies()
}