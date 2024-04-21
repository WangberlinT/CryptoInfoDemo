package com.tiki.cryptoinfodemo.domain.usecase

import com.tiki.cryptoinfodemo.domain.model.CryptoCurrency
import com.tiki.cryptoinfodemo.domain.model.Currency
import com.tiki.cryptoinfodemo.domain.repository.LocalCurrencyRepository

interface LoadCurrencyInfoUseCase {
    /**
     * Get all currency info list from local storage
     * @return List of [Currency]
     */
    suspend fun getAllCurrencyInfoList(): List<Currency>

    /**
     * Get all crypto currency info list from local storage
     * @return List of [CryptoCurrency]
     */
    suspend fun getCryptoCurrencyInfoList(): List<CryptoCurrency>

    /**
     * Get all fiat currency info list from local storage
     * @return List of [Currency]
     */
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