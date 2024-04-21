package com.tiki.cryptoinfodemo.data

import com.tiki.cryptoinfodemo.data.database.CurrencyInfo
import com.tiki.cryptoinfodemo.data.database.CurrencyInfoDao
import com.tiki.cryptoinfodemo.data.database.CurrencyType
import com.tiki.cryptoinfodemo.domain.CryptoCurrency
import com.tiki.cryptoinfodemo.domain.Currency
import com.tiki.cryptoinfodemo.domain.FiatCurrency
import com.tiki.cryptoinfodemo.domain.LocalCurrencyRepository

class LocalCurrencyRepositoryImpl(
    private val currencyInfoDao: CurrencyInfoDao
) : LocalCurrencyRepository {
    override suspend fun getAllCryptoCurrencies(): List<CryptoCurrency> {
        return currencyInfoDao.getAllCryptoCurrencies().map { it.toCryptoCurrency() }
    }

    override suspend fun getAllFiatCurrencies(): List<FiatCurrency> {
        return currencyInfoDao.getAllFiatCurrencies().map { it.toFiatCurrency() }
    }

    override suspend fun getAllCurrencies(): List<Currency> {
        return currencyInfoDao.getAll().map { it.toCurrency() }
    }

    override suspend fun insertAllCurrencies(currencies: List<Currency>) {
        currencyInfoDao.insertAll(currencies.map { it.toCurrencyInfo() })
    }

    override suspend fun removeAllCurrencies() {
        currencyInfoDao.deleteAll()
    }

    private fun CurrencyInfo.toFiatCurrency(): FiatCurrency {
        return FiatCurrency(id, name, symbol, code ?: "")
    }

    private fun CurrencyInfo.toCryptoCurrency(): CryptoCurrency {
        return CryptoCurrency(id, name, symbol)
    }

    private fun CurrencyInfo.toCurrency(): Currency {
        return when (type) {
            CurrencyType.CRYPTO -> toCryptoCurrency()
            CurrencyType.FIAT -> toFiatCurrency()
        }
    }

    private fun Currency.toCurrencyInfo(): CurrencyInfo {
        return when (this) {
            is CryptoCurrency -> CurrencyInfo(id, name, symbol, null, CurrencyType.CRYPTO)
            is FiatCurrency -> CurrencyInfo(id, name, symbol, code, CurrencyType.FIAT)
        }
    }
}