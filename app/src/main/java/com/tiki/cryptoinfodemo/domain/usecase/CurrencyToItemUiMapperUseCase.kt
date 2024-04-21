package com.tiki.cryptoinfodemo.domain.usecase

import com.tiki.cryptoinfodemo.domain.CryptoCurrency
import com.tiki.cryptoinfodemo.domain.Currency
import com.tiki.cryptoinfodemo.domain.CurrencyItemUi
import com.tiki.cryptoinfodemo.domain.FiatCurrency

interface CurrencyToItemUiMapperUseCase {
    operator fun invoke(currency: Currency): CurrencyItemUi
}

class CurrencyToItemUiMapperImpl : CurrencyToItemUiMapperUseCase {
    override fun invoke(currency: Currency): CurrencyItemUi =
        when (currency) {
            is CryptoCurrency -> {
                CurrencyItemUi(
                    iconText = currency.name.first(),
                    name = currency.name,
                    code = currency.symbol,
                    isCodeVisible = true,
                    isRightArrowVisible = true
                )
            }

            is FiatCurrency -> {
                CurrencyItemUi(
                    iconText = currency.name.first(),
                    name = currency.name,
                    code = "",
                    isCodeVisible = false,
                    isRightArrowVisible = false
                )
            }
        }
}