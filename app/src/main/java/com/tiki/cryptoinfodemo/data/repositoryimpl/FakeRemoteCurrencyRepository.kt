package com.tiki.cryptoinfodemo.data.repositoryimpl

import com.google.gson.Gson
import com.tiki.cryptoinfodemo.data.network.cryptoSamples
import com.tiki.cryptoinfodemo.data.network.fiatSamples
import com.tiki.cryptoinfodemo.domain.model.CryptoCurrency
import com.tiki.cryptoinfodemo.domain.model.Currency
import com.tiki.cryptoinfodemo.domain.model.FiatCurrency
import com.tiki.cryptoinfodemo.domain.repository.RemoteCurrencyRepository

class FakeRemoteCurrencyRepository(
    private val gson: Gson
) : RemoteCurrencyRepository {
    override suspend fun getAllCurrencies(): List<Currency> {
        val crypto = gson.fromJson(cryptoSamples, Array<CryptoCurrency>::class.java).toList()
        val fiat = gson.fromJson(fiatSamples, Array<FiatCurrency>::class.java).toList()
        return crypto + fiat
    }
}