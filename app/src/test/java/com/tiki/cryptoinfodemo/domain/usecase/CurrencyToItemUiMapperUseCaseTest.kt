package com.tiki.cryptoinfodemo.domain.usecase

import com.google.common.truth.Truth
import com.tiki.cryptoinfodemo.domain.CryptoCurrency
import com.tiki.cryptoinfodemo.domain.CurrencyItemUi
import com.tiki.cryptoinfodemo.domain.FiatCurrency
import org.junit.Before
import org.junit.Test

class CurrencyToItemUiMapperUseCaseTest {
    private lateinit var currencyToItemUiMapperUseCase: CurrencyToItemUiMapperUseCase

    @Before
    fun setUp() {
        currencyToItemUiMapperUseCase = CurrencyToItemUiMapperImpl()
    }

    @Test
    fun testMapToFiatCurrencyUi() {
        val currency = FiatCurrency("USD", "United States Dollar", "USD", "$")
        val target = CurrencyItemUi(
            iconText = currency.name.first(),
            name = currency.name,
            code = "",
            isCodeVisible = false,
            isRightArrowVisible = false
        )
        val currencyItemUi = currencyToItemUiMapperUseCase(currency)
        Truth.assertThat(currencyItemUi).isEqualTo(target)
    }

    @Test
    fun testMapToCryptoCurrencyUi() {
        val currency = CryptoCurrency("BTC", "Bitcoin", "BTC")
        val target = CurrencyItemUi(
            iconText = currency.name.first(),
            name = currency.name,
            code = currency.symbol,
            isCodeVisible = true,
            isRightArrowVisible = true
        )
        val currencyItemUi = currencyToItemUiMapperUseCase(currency)
        Truth.assertThat(currencyItemUi).isEqualTo(target)
    }
}