package com.tiki.cryptoinfodemo.presentation.viewmodel

import app.cash.turbine.test
import com.google.common.truth.Truth
import com.tiki.cryptoinfodemo.MainDispatcherRule
import com.tiki.cryptoinfodemo.domain.model.CryptoCurrency
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CurrencySharedViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var currencySharedViewModel: CurrencySharedViewModel

    @Before
    fun setUp() {
        currencySharedViewModel = CurrencySharedViewModel()
    }

    @Test
    fun testOnCurrencyInfoSourceChange() = runTest {
        val currencies = listOf(
            CryptoCurrency("BTC", "Bitcoin", "BTC"),
            CryptoCurrency("ETH", "Ethereum", "ETH")
        )
        currencySharedViewModel.currencyInfoList.test {
            Truth.assertThat(awaitItem()).isEmpty()
            currencySharedViewModel.onCurrencyInfoSourceChange(currencies)
            Truth.assertThat(awaitItem()).isEqualTo(currencies)
        }
    }
}