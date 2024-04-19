package com.tiki.cryptoinfodemo.di

import androidx.room.Room
import com.tiki.cryptoinfodemo.data.LocalCurrencyRepositoryImpl
import com.tiki.cryptoinfodemo.data.RemoteCurrencyRepositoryImpl
import com.tiki.cryptoinfodemo.data.database.CurrencyInfoDao
import com.tiki.cryptoinfodemo.data.database.CurrencyInfoDatabase
import com.tiki.cryptoinfodemo.domain.LocalCurrencyRepository
import com.tiki.cryptoinfodemo.domain.RemoteCurrencyRepository
import com.tiki.cryptoinfodemo.domain.usecase.LoadCurrencyInfoUseCase
import com.tiki.cryptoinfodemo.domain.usecase.LoadCurrencyInfoUseCaseImpl
import com.tiki.cryptoinfodemo.ui.viewmodel.CurrencySharedViewModel
import com.tiki.cryptoinfodemo.ui.viewmodel.MainActivityViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    factory<LocalCurrencyRepository> { LocalCurrencyRepositoryImpl(get()) }
    factory<RemoteCurrencyRepository> { RemoteCurrencyRepositoryImpl() }

    single<CurrencyInfoDatabase> {
        Room.databaseBuilder(
            this.androidApplication(),
            CurrencyInfoDatabase::class.java, "demo-db"
        ).build()
    }
    single<CurrencyInfoDao> {
        get<CurrencyInfoDatabase>().currencyInfoDao()
    }

    // TODO change to scope after understand it
    factory<LoadCurrencyInfoUseCase> { LoadCurrencyInfoUseCaseImpl(get()) }

    viewModel { CurrencySharedViewModel() }
    viewModel { MainActivityViewModel(get()) }
}