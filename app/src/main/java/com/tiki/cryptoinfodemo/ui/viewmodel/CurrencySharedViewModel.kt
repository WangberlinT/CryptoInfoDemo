package com.tiki.cryptoinfodemo.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tiki.cryptoinfodemo.domain.Currency
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CurrencySharedViewModel : ViewModel() {

    private val _currencyInfoList = MutableStateFlow<List<Currency>>(emptyList())
    val currencyInfoList = _currencyInfoList.asStateFlow()


    fun onCurrencyInfoSourceChange(currencySource: List<Currency>) = viewModelScope.launch {
        _currencyInfoList.value = currencySource
    }

}