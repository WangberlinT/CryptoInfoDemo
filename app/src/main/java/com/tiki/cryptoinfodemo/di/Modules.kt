package com.tiki.cryptoinfodemo.di

import com.tiki.cryptoinfodemo.data.LocalCurrencyRepositoryImpl
import com.tiki.cryptoinfodemo.domain.LocalCurrencyRepository
import org.koin.dsl.module

val appModule = module {
    single<LocalCurrencyRepository> { LocalCurrencyRepositoryImpl() }
}