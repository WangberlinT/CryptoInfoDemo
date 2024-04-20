package com.tiki.cryptoinfodemo.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tiki.cryptoinfodemo.domain.Currency
import com.tiki.cryptoinfodemo.domain.usecase.LoadCurrencyInfoUseCase
import com.tiki.cryptoinfodemo.domain.usecase.UpdateLocalCurrencyInfoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val loadCurrencyInfoUseCase: LoadCurrencyInfoUseCase,
    private val updateLocalCurrencyInfoUseCase: UpdateLocalCurrencyInfoUseCase
) : ViewModel() {

    private val _currencyInfo: MutableStateFlow<List<Currency>> = MutableStateFlow(emptyList())
    val currencyInfo = _currencyInfo.asStateFlow()


    fun onRemoveClick() = viewModelScope.launch {
        _currencyInfo.value = emptyList()
        updateLocalCurrencyInfoUseCase.removeLocalCurrencyInfo()
    }

    fun onInsertClick() = viewModelScope.launch {
        updateLocalCurrencyInfoUseCase.updateLocalCurrencyInfoFromRemote()
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