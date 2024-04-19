package com.tiki.cryptoinfodemo.domain.usecase

import com.tiki.cryptoinfodemo.domain.LocalCurrencyRepository
import com.tiki.cryptoinfodemo.domain.RemoteCurrencyRepository

interface UpdateLocalCurrencyInfoUseCase {
    suspend fun updateLocalCurrencyInfoFromRemote()
    suspend fun removeLocalCurrencyInfo()
}

class UpdateLocalCurrencyInfoUseCaseImpl(
    private val remoteCurrencyRepository: RemoteCurrencyRepository,
    private val localCurrencyRepository: LocalCurrencyRepository
) : UpdateLocalCurrencyInfoUseCase {
    override suspend fun updateLocalCurrencyInfoFromRemote() {
        val currencies = remoteCurrencyRepository.getAllCurrencies()
        localCurrencyRepository.insertAllCurrencies(currencies)
    }

    override suspend fun removeLocalCurrencyInfo() {
        localCurrencyRepository.removeAllCurrencies()
    }
}