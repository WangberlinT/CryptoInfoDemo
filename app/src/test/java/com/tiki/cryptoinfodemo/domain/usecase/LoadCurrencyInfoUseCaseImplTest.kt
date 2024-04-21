package com.tiki.cryptoinfodemo.domain.usecase

import com.google.common.truth.Truth
import com.tiki.cryptoinfodemo.domain.CryptoCurrency
import com.tiki.cryptoinfodemo.domain.FiatCurrency
import com.tiki.cryptoinfodemo.domain.LocalCurrencyRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class LoadCurrencyInfoUseCaseImplTest {
    @MockK(relaxed = true)
    private lateinit var localCurrencyRepository: LocalCurrencyRepository

    private lateinit var loadCurrencyInfoUseCase: LoadCurrencyInfoUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        loadCurrencyInfoUseCase = LoadCurrencyInfoUseCaseImpl(localCurrencyRepository)
    }

    @Test
    fun testGetAllCurrencyInfoList() = runBlocking {
        val target = listOf(
            CryptoCurrency("BTC", "Bitcoin", "BTC"),
            FiatCurrency("USD", "United States Dollar", "USD", "$")
        )
        coEvery { localCurrencyRepository.getAllCurrencies() } returns target
        val result = loadCurrencyInfoUseCase.getAllCurrencyInfoList()
        Truth.assertThat(result).isEqualTo(target)
    }

    @Test
    fun testGetCryptoCurrencyInfoList() = runBlocking {
        val target = listOf(
            CryptoCurrency("BTC", "Bitcoin", "BTC"),
            CryptoCurrency("ETH", "Ethereum", "ETH")
        )
        coEvery { localCurrencyRepository.getAllCryptoCurrencies() } returns target
        val result = loadCurrencyInfoUseCase.getCryptoCurrencyInfoList()
        Truth.assertThat(result).isEqualTo(target)
    }

    @Test
    fun testGetFiatCurrencyInfoList() = runBlocking {
        val target = listOf(
            FiatCurrency("USD", "United States Dollar", "USD", "$"),
            FiatCurrency("EUR", "Euro", "EUR", "€")
        )
        coEvery { localCurrencyRepository.getAllFiatCurrencies() } returns target
        val result = loadCurrencyInfoUseCase.getFiatCurrencyInfoList()
        Truth.assertThat(result).isEqualTo(target)
    }
}