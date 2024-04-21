package com.tiki.cryptoinfodemo.presentation.viewmodel

import app.cash.turbine.test
import com.google.common.truth.Truth
import com.tiki.cryptoinfodemo.MainDispatcherRule
import com.tiki.cryptoinfodemo.domain.model.CryptoCurrency
import com.tiki.cryptoinfodemo.domain.model.Currency
import com.tiki.cryptoinfodemo.domain.usecase.CurrencyToItemUiMapperImpl
import com.tiki.cryptoinfodemo.domain.usecase.CurrencyToItemUiMapperUseCase
import com.tiki.cryptoinfodemo.domain.usecase.SearchCurrencyUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CurrencyListViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK(relaxed = true)
    private lateinit var searchCurrencyUseCase: SearchCurrencyUseCase

    private lateinit var currencyToItemUiMapper: CurrencyToItemUiMapperUseCase
    private lateinit var currencyListViewModel: CurrencyListViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        currencyToItemUiMapper = CurrencyToItemUiMapperImpl()
        currencyListViewModel = CurrencyListViewModel(currencyToItemUiMapper, searchCurrencyUseCase)
    }

    @Test
    fun testOnSourceInfoListChange() = runTest {
        Truth.assertThat(currencyListViewModel.currencyInfoUiList.value).isEmpty()
        val currencyList = listOf(CryptoCurrency("BTC", "Bitcoin", "BTC"))
        currencyListViewModel.onSourceCurrencyInfoListChange(currencyList)
        Truth.assertThat(currencyListViewModel.currencyInfoUiList.value).isEqualTo(
            currencyList.map { currencyToItemUiMapper(it) }
        )
    }

    @Test
    fun testOnSearchQueryChange() = runTest {
        val target = listOf(
            CryptoCurrency("BTC", "Bitcoin", "BTC"),
        )
        val list = listOf(
            CryptoCurrency("ETH", "Ethereum", "ETH")
        ) + target

        coEvery { searchCurrencyUseCase.search(any(), any()) } returns target

        currencyListViewModel.onSourceCurrencyInfoListChange(list)

        currencyListViewModel.searchResult.test {
            val item1 = awaitItem()
            Truth.assertThat(item1).isEmpty()

            currencyListViewModel.onSearchQueryChange("BTC")
            Truth.assertThat(currencyListViewModel.getSearchQuery()).isEqualTo("BTC")
            val item2 = awaitItem()
            Truth.assertThat(item2).isEqualTo(target.map { currencyToItemUiMapper(it) })
        }
    }

    @Test
    fun testOnSearchFinished() = runTest {
        val target1 = listOf(
            CryptoCurrency("BTC", "Bitcoin", "BTC"),
        )
        val target2 = listOf(
            CryptoCurrency("ETH", "ETH", "ETH")
        )
        val list = target1 + target2

        coEvery { searchCurrencyUseCase.search("BTC", any()) } returns target1
        coEvery { searchCurrencyUseCase.search("ETH", any()) } returns target2

        val delay = 200L
        currencyListViewModel = CurrencyListViewModel(
            currencyToItemUiMapper,
            DelaySearchCurrencyUseCase(searchCurrencyUseCase, delay)
        )

        currencyListViewModel.onSourceCurrencyInfoListChange(list)

        currencyListViewModel.searchResult.test {
            val item1 = awaitItem()
            Truth.assertThat(item1).isEmpty()

            currencyListViewModel.onSearchQueryChange("BTC")
            Truth.assertThat(awaitItem()).isEqualTo(target1.map { currencyToItemUiMapper(it) })
            currencyListViewModel.onSearchQueryChange("ETH")
            currencyListViewModel.onSearchFinished()
            delay(delay + 100)
            Truth.assertThat(awaitItem()).isEmpty()
        }
    }

    class DelaySearchCurrencyUseCase(
        private val searchCurrencyUseCase: SearchCurrencyUseCase,
        private val delay: Long = 200
    ) : SearchCurrencyUseCase {

        override suspend fun search(query: String, list: List<Currency>): List<Currency> {
            delay(delay)
            return searchCurrencyUseCase.search(query, list)
        }
    }
}