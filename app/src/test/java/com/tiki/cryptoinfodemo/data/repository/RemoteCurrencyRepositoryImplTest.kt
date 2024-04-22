package com.tiki.cryptoinfodemo.data.repository

import com.google.common.truth.Truth
import com.tiki.cryptoinfodemo.data.network.CurrencyService
import com.tiki.cryptoinfodemo.data.repositoryimpl.RemoteCurrencyRepositoryImpl
import com.tiki.cryptoinfodemo.domain.model.CryptoCurrency
import com.tiki.cryptoinfodemo.domain.model.FiatCurrency
import com.tiki.cryptoinfodemo.domain.repository.RemoteCurrencyRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class RemoteCurrencyRepositoryImplTest {
    @MockK(relaxed = true)
    private lateinit var currencyService: CurrencyService

    private lateinit var remoteCurrencyRepository: RemoteCurrencyRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        remoteCurrencyRepository = RemoteCurrencyRepositoryImpl(currencyService)
    }

    @Test
    fun testGetAllCurrencies() = runTest {
        val crypto = listOf(
            CryptoCurrency("BTC", "Bitcoin", "BTC"),
            CryptoCurrency("ETH", "Ethereum", "ETH")
        )
        val fiat = listOf(
            FiatCurrency("USD", "United States Dollar", "USD", "$"),
            FiatCurrency("EUR", "Euro", "EUR", "€")
        )
        coEvery { currencyService.getCryptoCurrencyInfo() } returns crypto
        coEvery { currencyService.getFiatCurrencyInfo() } returns fiat
        val all = remoteCurrencyRepository.getAllCurrencies()
        Truth.assertThat(all).isEqualTo(crypto + fiat)
    }
}