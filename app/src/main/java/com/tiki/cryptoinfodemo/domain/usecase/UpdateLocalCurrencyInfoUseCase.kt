package com.tiki.cryptoinfodemo.domain.usecase

import android.util.Log
import com.tiki.cryptoinfodemo.domain.model.UpdateCurrencyInfoException
import com.tiki.cryptoinfodemo.domain.repository.LocalCurrencyRepository
import com.tiki.cryptoinfodemo.domain.repository.RemoteCurrencyRepository
import java.io.IOException

interface UpdateLocalCurrencyInfoUseCase {
    /**
     * @throws UpdateCurrencyInfoException if there is an error updating the local currency info
     * fetch CurrencyInfo from remote and update the local currency info
     */
    suspend fun updateLocalCurrencyInfoFromRemote()

    /**
     * remove all local currency info
     */
    suspend fun removeLocalCurrencyInfo()
}

class UpdateLocalCurrencyInfoUseCaseImpl(
    private val remoteCurrencyRepository: RemoteCurrencyRepository,
    private val localCurrencyRepository: LocalCurrencyRepository
) : UpdateLocalCurrencyInfoUseCase {
    override suspend fun updateLocalCurrencyInfoFromRemote() {
        try {
            val currencies = remoteCurrencyRepository.getAllCurrencies()
            localCurrencyRepository.insertAllCurrencies(currencies)
        } catch (e: IOException) {
            Log.e(
                "UpdateLocalCurrencyInfoUseCaseImpl",
                "updateLocalCurrencyInfoFromRemote: ${e.message}"
            )
            throw UpdateCurrencyInfoException("Error updating local currency info")
        }
    }

    override suspend fun removeLocalCurrencyInfo() {
        localCurrencyRepository.removeAllCurrencies()
    }
}