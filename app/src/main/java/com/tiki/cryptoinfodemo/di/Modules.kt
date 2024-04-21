package com.tiki.cryptoinfodemo.di

import androidx.room.Room
import com.google.gson.Gson
import com.tiki.cryptoinfodemo.data.LocalCurrencyRepositoryImpl
import com.tiki.cryptoinfodemo.data.FakeRemoteCurrencyRepository
import com.tiki.cryptoinfodemo.data.database.CurrencyInfoDao
import com.tiki.cryptoinfodemo.data.database.CurrencyInfoDatabase
import com.tiki.cryptoinfodemo.domain.LocalCurrencyRepository
import com.tiki.cryptoinfodemo.domain.RemoteCurrencyRepository
import com.tiki.cryptoinfodemo.domain.usecase.CurrencyToItemUiMapperImpl
import com.tiki.cryptoinfodemo.domain.usecase.CurrencyToItemUiMapperUseCase
import com.tiki.cryptoinfodemo.domain.usecase.LoadCurrencyInfoUseCase
import com.tiki.cryptoinfodemo.domain.usecase.LoadCurrencyInfoUseCaseImpl
import com.tiki.cryptoinfodemo.domain.usecase.SearchCurrencyUseCase
import com.tiki.cryptoinfodemo.domain.usecase.SearchCurrencyUseCaseImpl
import com.tiki.cryptoinfodemo.domain.usecase.UpdateLocalCurrencyInfoUseCase
import com.tiki.cryptoinfodemo.domain.usecase.UpdateLocalCurrencyInfoUseCaseImpl
import com.tiki.cryptoinfodemo.ui.viewmodel.CurrencyListViewModel
import com.tiki.cryptoinfodemo.ui.viewmodel.CurrencySharedViewModel
import com.tiki.cryptoinfodemo.ui.viewmodel.MainActivityViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    factory<LocalCurrencyRepository> { LocalCurrencyRepositoryImpl(get()) }
    factory<RemoteCurrencyRepository> { FakeRemoteCurrencyRepository(get()) }

    single<Gson> { Gson() }
    single<CurrencyInfoDatabase> {
        Room.databaseBuilder(
            this.androidApplication(),
            CurrencyInfoDatabase::class.java, "demo-db"
        ).build()
    }
    single<CurrencyInfoDao> {
        get<CurrencyInfoDatabase>().currencyInfoDao()
    }

    factory<LoadCurrencyInfoUseCase> { LoadCurrencyInfoUseCaseImpl(get()) }
    factory<UpdateLocalCurrencyInfoUseCase> { UpdateLocalCurrencyInfoUseCaseImpl(get(), get()) }
    factory<CurrencyToItemUiMapperUseCase> { CurrencyToItemUiMapperImpl() }
    factory<SearchCurrencyUseCase> { SearchCurrencyUseCaseImpl() }

    viewModel { CurrencySharedViewModel() }
    viewModel { MainActivityViewModel(get(), get()) }
    viewModel { CurrencyListViewModel(get(), get()) }
}