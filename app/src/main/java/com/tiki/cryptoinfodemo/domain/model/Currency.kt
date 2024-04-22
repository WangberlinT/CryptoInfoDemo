package com.tiki.cryptoinfodemo.domain.model

import com.google.gson.annotations.SerializedName

data class CryptoCurrency(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("symbol")
    val symbol: String,
) : Currency

data class FiatCurrency(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("code")
    val code: String
) : Currency

sealed interface Currency