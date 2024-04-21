package com.tiki.cryptoinfodemo.data.repository

import com.google.common.truth.Truth
import com.tiki.cryptoinfodemo.data.repositoryimpl.LocalCurrencyRepositoryImpl
import com.tiki.cryptoinfodemo.data.database.CurrencyInfo
import com.tiki.cryptoinfodemo.data.database.CurrencyInfoDao
import com.tiki.cryptoinfodemo.data.database.CurrencyType
import com.tiki.cryptoinfodemo.domain.model.CryptoCurrency
import com.tiki.cryptoinfodemo.domain.model.FiatCurrency
import com.tiki.cryptoinfodemo.domain.repository.LocalCurrencyRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class LocalCurrencyRepositoryImplTest {

    @MockK(relaxed = true)
    private lateinit var localCurrencyDao: CurrencyInfoDao
    private lateinit var localCurrencyRepository: LocalCurrencyRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        localCurrencyRepository = LocalCurrencyRepositoryImpl(localCurrencyDao)
    }

    @Test
    fun testInsertAllCurrencies() = runBlocking {
        val currencies = listOf(
            CryptoCurrency("BTC", "Bitcoin", "BTC"),
            FiatCurrency("USD", "United States Dollar", "USD", "$")
        )
        val currencyDto = listOf(
            CurrencyInfo("BTC", "Bitcoin", "BTC", null, CurrencyType.CRYPTO),
            CurrencyInfo("USD", "United States Dollar", "USD", "$", CurrencyType.FIAT)
        )
        localCurrencyRepository.insertAllCurrencies(currencies)
        coVerify { localCurrencyDao.insertAll(currencyDto) }
    }

    @Test
    fun testRemoveAllCurrencies() = runBlocking {
        localCurrencyRepository.removeAllCurrencies()
        coVerify { localCurrencyDao.deleteAll() }
    }

    @Test
    fun testGetAllCurrencies() = runBlocking {
        val currencyDto = listOf(
            CurrencyInfo("BTC", "Bitcoin", "BTC", null, CurrencyType.CRYPTO),
            CurrencyInfo("USD", "United States Dollar", "USD", "$", CurrencyType.FIAT)
        )
        val currencies = listOf(
            CryptoCurrency("BTC", "Bitcoin", "BTC"),
            FiatCurrency("USD", "United States Dollar", "USD", "$")
        )
        coEvery { localCurrencyDao.getAll() } returns currencyDto
        val result = localCurrencyRepository.getAllCurrencies()
        Truth.assertThat(result).isEqualTo(currencies)
    }

    @Test
    fun testGetAllCryptoCurrencies() = runBlocking {
        val currencyDto = listOf(
            CurrencyInfo("BTC", "Bitcoin", "BTC", null, CurrencyType.CRYPTO),
            CurrencyInfo("USD", "United States Dollar", "USD", "$", CurrencyType.FIAT)
        )
        val currencies = listOf(
            CryptoCurrency("BTC", "Bitcoin", "BTC")
        )
        coEvery { localCurrencyDao.getAllCryptoCurrencies() } returns currencyDto.filter { it.type == CurrencyType.CRYPTO }
        val result = localCurrencyRepository.getAllCryptoCurrencies()
        Truth.assertThat(result).isEqualTo(currencies)
    }

    @Test
    fun testGetAllFiatCurrencies() = runBlocking {
        val currencyDto = listOf(
            CurrencyInfo("BTC", "Bitcoin", "BTC", null, CurrencyType.CRYPTO),
            CurrencyInfo("USD", "United States Dollar", "USD", "$", CurrencyType.FIAT)
        )
        val currencies = listOf(
            FiatCurrency("USD", "United States Dollar", "USD", "$")
        )
        coEvery { localCurrencyDao.getAllFiatCurrencies() } returns currencyDto.filter { it.type == CurrencyType.FIAT }
        val result = localCurrencyRepository.getAllFiatCurrencies()
        Truth.assertThat(result).isEqualTo(currencies)
    }
}