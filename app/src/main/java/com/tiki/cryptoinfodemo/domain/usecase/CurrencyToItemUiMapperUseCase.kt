package com.tiki.cryptoinfodemo.domain.usecase

import android.app.Application
import com.tiki.cryptoinfodemo.R
import com.tiki.cryptoinfodemo.domain.CryptoCurrency
import com.tiki.cryptoinfodemo.domain.Currency
import com.tiki.cryptoinfodemo.domain.CurrencyItemUi
import com.tiki.cryptoinfodemo.domain.FiatCurrency

interface CurrencyToItemUiMapperUseCase {
    operator fun invoke(currency: Currency, isSearchState: Boolean): CurrencyItemUi
}

class CurrencyToItemUiMapperImpl(
    private val applicationContext: Application
) : CurrencyToItemUiMapperUseCase {
    override fun invoke(currency: Currency, isSearchState: Boolean): CurrencyItemUi =
        when (currency) {
            is CryptoCurrency -> {
                CurrencyItemUi(
                    iconText = currency.name.first(),
                    iconColor = applicationContext.getColor(if (isSearchState) R.color.neutral_dark else R.color.black),
                    name = currency.name,
                    code = currency.id,
                    isCodeVisible = true,
                    isUnderLineVisible = isSearchState,
                    isRightArrowVisible = true
                )
            }

            is FiatCurrency -> {
                CurrencyItemUi(
                    iconText = currency.name.first(),
                    iconColor = applicationContext.getColor(if (isSearchState) R.color.neutral_dark else R.color.black),
                    name = currency.name,
                    code = "",
                    isCodeVisible = false,
                    isUnderLineVisible = isSearchState,
                    isRightArrowVisible = false
                )
            }
        }
}