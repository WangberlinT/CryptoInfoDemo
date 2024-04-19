package com.tiki.cryptoinfodemo.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tiki.cryptoinfodemo.domain.Currency
import com.tiki.cryptoinfodemo.domain.usecase.LoadCurrencyInfoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val loadCurrencyInfoUseCase: LoadCurrencyInfoUseCase
) : ViewModel() {

    private val _currencyInfo: MutableStateFlow<List<Currency>> = MutableStateFlow(emptyList())
    val currencyInfo = _currencyInfo.asStateFlow()


    fun onRemoveClick() {
        loadCurrencyInfoUseCase
    }

    fun onInsertClick() = viewModelScope.launch {
        _currencyInfo.value = loadCurrencyInfoUseCase.getAllCurrencyInfoList()
    }

    fun onCryptoCurrencyClick() = viewModelScope.launch {
        _currencyInfo.value = loadCurrencyInfoUseCase.getCryptoCurrencyInfoList()
    }

    fun onFiatCurrencyClick() = viewModelScope.launch {
        _currencyInfo.value = loadCurrencyInfoUseCase.getFiatCurrencyInfoList()
    }

    fun onAllCurrencyClick() = viewModelScope.launch {
        _currencyInfo.value = loadCurrencyInfoUseCase.getAllCurrencyInfoList()
    }
}