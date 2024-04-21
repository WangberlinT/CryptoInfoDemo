package com.tiki.cryptoinfodemo.domain.usecase

import com.google.common.truth.Truth
import com.tiki.cryptoinfodemo.domain.model.CryptoCurrency
import com.tiki.cryptoinfodemo.domain.model.FiatCurrency
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SearchCurrencyUseCaseImplTest {
    private lateinit var searchCurrencyUseCase: SearchCurrencyUseCase

    @Before
    fun setUp() {
        searchCurrencyUseCase = SearchCurrencyUseCaseImpl()
    }


    @Test
    fun testCoinNameStartsWithSearchTerm() = runBlocking {
        val query = "Foo"
        val target = listOf(CryptoCurrency("Foo", "Foobar", "F"))
        val list = listOf(
            CryptoCurrency("Bar", "Barfoo", "B"),
            CryptoCurrency("Baz", "Bazfoo", "B")
        ) + target

        val result = searchCurrencyUseCase.search(query, list)
        Truth.assertThat(result).isEqualTo(target)
    }

    @Test
    fun testCoinNameMatchedWithSpacePrefix() = runBlocking {
        val query = "Ethereum"
        val target = listOf(
            CryptoCurrency("ETC", "Ethereum", "ETH"),
            CryptoCurrency("ETHC", "Ethereum Classic", "ETHC"),
        )
        val list = listOf(
            FiatCurrency("Bar", "Barfoo", "B", ""),
            FiatCurrency("Baz", "Bazfoo", "B", "")
        ) + target

        val result = searchCurrencyUseCase.search(query, list)
        Truth.assertThat(result).isEqualTo(target)
    }

    @Test
    fun testCoinNameSearchCaseInsensitive() = runBlocking {
        val query = "foo"
        val target = listOf(CryptoCurrency("Foo", "Foobar", "F"))
        val list = listOf(
            CryptoCurrency("Bar", "Barfoo", "B"),
            CryptoCurrency("Baz", "Bazfoo", "B")
        ) + target

        val result = searchCurrencyUseCase.search(query, list)
        Truth.assertThat(result).isEqualTo(target)
    }

    @Test
    fun testCoinSymbolStartsWithTheSearchTerm() = runBlocking {
        val query = "ET"
        val target = listOf(
            CryptoCurrency("ETC", "Ethereum", "ETH"),
            CryptoCurrency("ETC", "Ethereum Classic", "ETC"),
            CryptoCurrency("ETN", "", "ETN")
        )
        val list = listOf(
            CryptoCurrency("BTC", "Bitcoin", "BTC"),
            CryptoCurrency("BTE", "", "BTE")
        ) + target

        val result = searchCurrencyUseCase.search(query, list)
        Truth.assertThat(result).isEqualTo(target)
    }

}