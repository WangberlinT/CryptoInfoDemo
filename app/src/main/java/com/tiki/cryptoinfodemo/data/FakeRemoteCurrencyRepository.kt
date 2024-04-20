package com.tiki.cryptoinfodemo.data

import com.google.gson.Gson
import com.tiki.cryptoinfodemo.domain.CryptoCurrency
import com.tiki.cryptoinfodemo.domain.Currency
import com.tiki.cryptoinfodemo.domain.FiatCurrency
import com.tiki.cryptoinfodemo.domain.RemoteCurrencyRepository

class FakeRemoteCurrencyRepository(
    private val gson: Gson
) : RemoteCurrencyRepository {
    override suspend fun getAllCurrencies(): List<Currency> {
        val crypto = gson.fromJson(cryptoSamples, Array<CryptoCurrency>::class.java).toList()
        val fiat = gson.fromJson(fiatSamples, Array<FiatCurrency>::class.java).toList()
        return crypto + fiat
    }
}