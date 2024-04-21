package com.tiki.cryptoinfodemo.domain.model


data class CurrencyItemUi(
    val iconText: Char,
    val name: String,
    val code: String,
    val isCodeVisible: Boolean,
    val isRightArrowVisible: Boolean,
)
