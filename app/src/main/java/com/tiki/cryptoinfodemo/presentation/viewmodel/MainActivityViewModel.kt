package com.tiki.cryptoinfodemo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tiki.cryptoinfodemo.domain.model.Currency
import com.tiki.cryptoinfodemo.domain.model.UpdateCurrencyInfoException
import com.tiki.cryptoinfodemo.domain.usecase.LoadCurrencyInfoUseCase
import com.tiki.cryptoinfodemo.domain.usecase.UpdateLocalCurrencyInfoUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val loadCurrencyInfoUseCase: LoadCurrencyInfoUseCase,
    private val updateLocalCurrencyInfoUseCase: UpdateLocalCurrencyInfoUseCase
) : ViewModel() {

    private val _currencyInfo: MutableStateFlow<List<Currency>> = MutableStateFlow(emptyList())
    val currencyInfo = _currencyInfo.asStateFlow()

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()


    fun onRemoveClick() = viewModelScope.launch {
        _currencyInfo.value = emptyList()
        updateLocalCurrencyInfoUseCase.removeLocalCurrencyInfo()
        _event.emit(Event.DatabaseRemoved())
    }

    fun onInsertClick() = viewModelScope.launch {
        try {
            updateLocalCurrencyInfoUseCase.updateLocalCurrencyInfoFromRemote()
            _event.emit(Event.DatabaseInserted())
        } catch (e: UpdateCurrencyInfoException) {
            e.printStackTrace()
            _event.emit(Event.Error(e.message ?: "Unknown error"))
        }
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

    sealed interface Event {
        class DatabaseInserted : Event
        class DatabaseRemoved : Event
        class Error(val message: String) : Event
    }
}