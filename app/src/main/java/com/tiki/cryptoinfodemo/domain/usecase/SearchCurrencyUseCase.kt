package com.tiki.cryptoinfodemo.domain.usecase

import com.tiki.cryptoinfodemo.domain.CryptoCurrency
import com.tiki.cryptoinfodemo.domain.Currency
import com.tiki.cryptoinfodemo.domain.FiatCurrency

interface SearchCurrencyUseCase {
    suspend fun search(query: String, list: List<Currency>): List<Currency>
}


class SearchCurrencyUseCaseImpl : SearchCurrencyUseCase {
    override suspend fun search(query: String, list: List<Currency>): List<Currency> {
        if (query.isEmpty()) return emptyList()
        val pattern = Regex("""\b$query""", option = RegexOption.IGNORE_CASE)
        return list.filter {
            when (it) {
                is CryptoCurrency -> matchInCryptoCurrencies(pattern, it)
                is FiatCurrency -> matchInFiatCurrencies(pattern, it)
            }
        }
    }

    private fun matchInCryptoCurrencies(pattern: Regex, currency: CryptoCurrency): Boolean {
        return pattern.containsMatchIn(currency.name) || pattern.containsMatchIn(currency.symbol)
    }

    private fun matchInFiatCurrencies(pattern: Regex, currency: FiatCurrency): Boolean {
        return pattern.containsMatchIn(currency.name) || pattern.containsMatchIn(currency.symbol) || pattern.containsMatchIn(
            currency.code
        )
    }
}