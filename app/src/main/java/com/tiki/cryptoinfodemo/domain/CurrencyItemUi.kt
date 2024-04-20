package com.tiki.cryptoinfodemo.domain

import androidx.annotation.ColorInt


data class CurrencyItemUi(
    @ColorInt val iconColor: Int,
    val iconText: Char,
    val name: String,
    val code: String,
    val isCodeVisible: Boolean,
    val isUnderLineVisible: Boolean,
    val isRightArrowVisible: Boolean,
)
