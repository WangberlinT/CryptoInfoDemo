package com.tiki.cryptoinfodemo.di

import com.tiki.cryptoinfodemo.data.CurrencyRepositoryImpl
import com.tiki.cryptoinfodemo.domain.CurrencyRepository
import org.koin.dsl.module

val appModule = module {
    single<CurrencyRepository> { CurrencyRepositoryImpl() }
}