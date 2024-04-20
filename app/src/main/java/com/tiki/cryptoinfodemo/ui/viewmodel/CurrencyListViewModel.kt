package com.tiki.cryptoinfodemo.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.tiki.cryptoinfodemo.domain.Currency
import com.tiki.cryptoinfodemo.domain.CurrencyItemUi
import com.tiki.cryptoinfodemo.domain.usecase.CurrencyToItemUiMapperUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CurrencyListViewModel(
    private val currencyToItemUiMapperUseCase: CurrencyToItemUiMapperUseCase
) : ViewModel() {

    private val _currencyInfoUiList = MutableStateFlow(emptyList<CurrencyItemUi>())
    val currencyInfoUiList = _currencyInfoUiList.asStateFlow()

    fun onSourceCurrencyInfoListChange(currencySource: List<Currency>) {
        _currencyInfoUiList.value = currencySource.map {
            currencyToItemUiMapperUseCase(it, isSearchState = false)
        }
    }
}