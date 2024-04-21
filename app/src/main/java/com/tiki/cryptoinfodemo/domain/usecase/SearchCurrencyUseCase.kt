package com.tiki.cryptoinfodemo.domain.usecase

import com.tiki.cryptoinfodemo.domain.CryptoCurrency
import com.tiki.cryptoinfodemo.domain.Currency
import com.tiki.cryptoinfodemo.domain.FiatCurrency

interface SearchCurrencyUseCase {
    suspend fun search(query: String, list: List<Currency>): List<Currency>
}


class SearchCurrencyUseCaseImpl : SearchCurrencyUseCase {
    override suspend fun search(query: String, list: List<Currency>): List<Currency> {
        val pattern = Regex("""\b$query""", option = RegexOption.IGNORE_CASE)
        return list.filter {
            when (it) {
                is CryptoCurrency -> pattern.containsMatchIn(it.name) || pattern.containsMatchIn(it.symbol)
                is FiatCurrency -> pattern.containsMatchIn(it.name) || pattern.containsMatchIn(it.symbol)
            }
        }
    }
}