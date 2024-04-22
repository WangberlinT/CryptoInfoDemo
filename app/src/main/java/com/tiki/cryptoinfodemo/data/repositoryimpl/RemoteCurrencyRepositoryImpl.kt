package com.tiki.cryptoinfodemo.data.repositoryimpl

import com.tiki.cryptoinfodemo.data.network.CurrencyService
import com.tiki.cryptoinfodemo.domain.model.Currency
import com.tiki.cryptoinfodemo.domain.repository.RemoteCurrencyRepository

class RemoteCurrencyRepositoryImpl(
    private val currencyService: CurrencyService
) : RemoteCurrencyRepository {
    override suspend fun getAllCurrencies(): List<Currency> {
        val crypto = currencyService.getCryptoCurrencyInfo()
        val fiat = currencyService.getFiatCurrencyInfo()
        return crypto + fiat
    }
}