package com.tiki.cryptoinfodemo.presentation.viewmodel

import app.cash.turbine.test
import com.google.common.truth.Truth
import com.tiki.cryptoinfodemo.MainDispatcherRule
import com.tiki.cryptoinfodemo.domain.model.CryptoCurrency
import com.tiki.cryptoinfodemo.domain.model.FiatCurrency
import com.tiki.cryptoinfodemo.domain.model.UpdateCurrencyInfoException
import com.tiki.cryptoinfodemo.domain.usecase.LoadCurrencyInfoUseCase
import com.tiki.cryptoinfodemo.domain.usecase.UpdateLocalCurrencyInfoUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainActivityViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    private lateinit var mainActivityViewModel: MainActivityViewModel

    @MockK(relaxed = true)
    private lateinit var loadCurrencyInfoUseCase: LoadCurrencyInfoUseCase

    @MockK(relaxed = true)
    private lateinit var updateLocalCurrencyInfoUseCase: UpdateLocalCurrencyInfoUseCase


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mainActivityViewModel =
            MainActivityViewModel(loadCurrencyInfoUseCase, updateLocalCurrencyInfoUseCase)
    }

    @Test
    fun testButtonEvents() = runTest {
        mainActivityViewModel.event.test {
            mainActivityViewModel.onRemoveClick()
            Truth.assertThat(awaitItem() is MainActivityViewModel.Event.DatabaseRemoved).isTrue()
            mainActivityViewModel.onInsertClick()
            Truth.assertThat(awaitItem() is MainActivityViewModel.Event.DatabaseInserted).isTrue()
        }
    }

    @Test
    fun testMultipleIdenticalButtonEvents() = runTest {
        mainActivityViewModel.event.test {
            mainActivityViewModel.onRemoveClick()
            Truth.assertThat(awaitItem() is MainActivityViewModel.Event.DatabaseRemoved).isTrue()
            mainActivityViewModel.onRemoveClick()
            Truth.assertThat(awaitItem() is MainActivityViewModel.Event.DatabaseRemoved).isTrue()
        }
    }

    @Test
    fun testOnErrorEvent() = runTest {
        coEvery { updateLocalCurrencyInfoUseCase.updateLocalCurrencyInfoFromRemote() } throws UpdateCurrencyInfoException(
            "Error"
        )
        mainActivityViewModel.event.test {
            mainActivityViewModel.onInsertClick()
            Truth.assertThat(awaitItem() is MainActivityViewModel.Event.Error).isTrue()
        }
    }

    @Test
    fun testOnCryptoCurrencyClick() = runTest {
        val currencies = listOf(
            CryptoCurrency("BTC", "Bitcoin", "BTC"),
            CryptoCurrency("ETH", "Ethereum", "ETH")
        )
        coEvery { loadCurrencyInfoUseCase.getCryptoCurrencyInfoList() } returns currencies
        mainActivityViewModel.currencyInfo.test {
            Truth.assertThat(awaitItem()).isEmpty()
            mainActivityViewModel.onCryptoCurrencyClick()
            Truth.assertThat(awaitItem()).isEqualTo(currencies)
        }
    }

    @Test
    fun testOnFiatCurrencyClick() = runTest {
        val currencies = listOf(
            CryptoCurrency("BTC", "Bitcoin", "BTC"),
            CryptoCurrency("ETH", "Ethereum", "ETH")
        )
        coEvery { loadCurrencyInfoUseCase.getFiatCurrencyInfoList() } returns currencies
        mainActivityViewModel.currencyInfo.test {
            Truth.assertThat(awaitItem()).isEmpty()
            mainActivityViewModel.onFiatCurrencyClick()
            Truth.assertThat(awaitItem()).isEqualTo(currencies)
        }
    }

    @Test
    fun testOnAllCurrencyClick() = runTest {
        val currencies = listOf(
            CryptoCurrency("BTC", "Bitcoin", "BTC"),
            CryptoCurrency("ETH", "Ethereum", "ETH"),
            FiatCurrency("USD", "US Dollar", "$", "USD")
        )
        coEvery { loadCurrencyInfoUseCase.getAllCurrencyInfoList() } returns currencies
        mainActivityViewModel.currencyInfo.test {
            Truth.assertThat(awaitItem()).isEmpty()
            mainActivityViewModel.onAllCurrencyClick()
            Truth.assertThat(awaitItem()).isEqualTo(currencies)
        }
    }

}