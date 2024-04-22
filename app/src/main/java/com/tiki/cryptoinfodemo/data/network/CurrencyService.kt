package com.tiki.cryptoinfodemo.data.network

import com.tiki.cryptoinfodemo.domain.model.CryptoCurrency
import com.tiki.cryptoinfodemo.domain.model.FiatCurrency
import retrofit2.http.GET

interface CurrencyService {
    @GET("crypto.json")
    suspend fun getCryptoCurrencyInfo(): List<CryptoCurrency>

    @GET("fiat.json")
    suspend fun getFiatCurrencyInfo(): List<FiatCurrency>
}