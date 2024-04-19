package com.tiki.cryptoinfodemo.domain.usecase

import com.tiki.cryptoinfodemo.domain.CryptoCurrency
import com.tiki.cryptoinfodemo.domain.Currency
import com.tiki.cryptoinfodemo.domain.LocalCurrencyRepository

interface LoadCurrencyInfoUseCase {
    suspend fun getAllCurrencyInfoList(): List<Currency>
    suspend fun getCryptoCurrencyInfoList(): List<CryptoCurrency>
    suspend fun getFiatCurrencyInfoList(): List<Currency>
}

class LoadCurrencyInfoUseCaseImpl(
    private val localCurrencyRepository: LocalCurrencyRepository
) : LoadCurrencyInfoUseCase {
    override suspend fun getAllCurrencyInfoList(): List<Currency> {
        return localCurrencyRepository.getAllCurrencies()
    }

    override suspend fun getCryptoCurrencyInfoList(): List<CryptoCurrency> {
        return localCurrencyRepository.getAllCryptoCurrencies()
    }

    override suspend fun getFiatCurrencyInfoList(): List<Currency> {
        return localCurrencyRepository.getAllFiatCurrencies()
    }
}