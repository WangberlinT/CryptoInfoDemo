package com.tiki.cryptoinfodemo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tiki.cryptoinfodemo.domain.model.Currency
import com.tiki.cryptoinfodemo.domain.model.CurrencyItemUi
import com.tiki.cryptoinfodemo.domain.usecase.CurrencyToItemUiMapperUseCase
import com.tiki.cryptoinfodemo.domain.usecase.SearchCurrencyUseCase
import kotlinx.coroutines.Job
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
    private var searchJob: Job? = null


    fun onSourceCurrencyInfoListChange(currencySource: List<Currency>) {
        sourceCurrencyInfoList = currencySource
        val currencyInfoUiList = currencySource.map {
            currencyToItemUiMapperUseCase(it)
        }
        _currencyInfoUiList.value = currencyInfoUiList
        search(searchQuery)
    }

    fun onSearchQueryChange(query: String) {
        searchQuery = query
        search(query)
    }

    fun onSearchFinished() {
        searchJob?.cancel()
        _searchResult.value = emptyList()
    }

    fun getSearchQuery() = searchQuery

    private fun search(query: String) {
        if (query.isEmpty()) {
            onSearchFinished()
            return
        }

        if (searchJob?.isActive == true) searchJob?.cancel()
        searchJob = viewModelScope.launch {
            val result = searchCurrencyUseCase.search(searchQuery, sourceCurrencyInfoList)
            _searchResult.value = result.map {
                currencyToItemUiMapperUseCase(it)
            }
        }
    }

}