package com.tiki.cryptoinfodemo.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tiki.cryptoinfodemo.domain.Currency
import com.tiki.cryptoinfodemo.domain.CurrencyItemUi
import com.tiki.cryptoinfodemo.domain.usecase.CurrencyToItemUiMapperUseCase
import com.tiki.cryptoinfodemo.domain.usecase.SearchCurrencyUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CurrencyListViewModel(
    private val currencyToItemUiMapperUseCase: CurrencyToItemUiMapperUseCase,
    private val searchCurrencyUseCase: SearchCurrencyUseCase
) : ViewModel() {

    private val _currencyInfoUiList = MutableStateFlow(emptyList<CurrencyItemUi>())
    val currencyInfoUiList = _currencyInfoUiList.asStateFlow()

    private var sourceCurrencyInfoList = emptyList<Currency>()

    private val _searchResult = MutableStateFlow(emptyList<CurrencyItemUi>())
    val searchResult = _searchResult.asStateFlow()

    private var searchQuery: String = ""


    fun onSourceCurrencyInfoListChange(currencySource: List<Currency>) {
        sourceCurrencyInfoList = currencySource
        _currencyInfoUiList.value = currencySource.map {
            currencyToItemUiMapperUseCase(it)
        }
    }

    fun onSearchQueryChange(query: String) = viewModelScope.launch {
        searchQuery = query
        val result = searchCurrencyUseCase.search(query, sourceCurrencyInfoList)
        _searchResult.value = result.map {
            currencyToItemUiMapperUseCase(it)
        }
    }
}