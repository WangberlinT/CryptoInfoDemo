package com.tiki.cryptoinfodemo.domain.usecase

import com.tiki.cryptoinfodemo.domain.model.CryptoCurrency
import com.tiki.cryptoinfodemo.domain.model.Currency
import com.tiki.cryptoinfodemo.domain.model.CurrencyItemUi
import com.tiki.cryptoinfodemo.domain.model.FiatCurrency

/**
 * map [Currency] to [CurrencyItemUi]
 */
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