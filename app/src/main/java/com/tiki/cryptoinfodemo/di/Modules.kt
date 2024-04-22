package com.tiki.cryptoinfodemo.di

import androidx.room.Room
import com.tiki.cryptoinfodemo.data.repositoryimpl.LocalCurrencyRepositoryImpl
import com.tiki.cryptoinfodemo.data.database.CurrencyInfoDao
import com.tiki.cryptoinfodemo.data.database.CurrencyInfoDatabase
import com.tiki.cryptoinfodemo.data.network.CurrencyService
import com.tiki.cryptoinfodemo.data.repositoryimpl.RemoteCurrencyRepositoryImpl
import com.tiki.cryptoinfodemo.domain.repository.LocalCurrencyRepository
import com.tiki.cryptoinfodemo.domain.repository.RemoteCurrencyRepository
import com.tiki.cryptoinfodemo.domain.usecase.CurrencyToItemUiMapperImpl
import com.tiki.cryptoinfodemo.domain.usecase.CurrencyToItemUiMapperUseCase
import com.tiki.cryptoinfodemo.domain.usecase.LoadCurrencyInfoUseCase
import com.tiki.cryptoinfodemo.domain.usecase.LoadCurrencyInfoUseCaseImpl
import com.tiki.cryptoinfodemo.domain.usecase.SearchCurrencyUseCase
import com.tiki.cryptoinfodemo.domain.usecase.SearchCurrencyUseCaseImpl
import com.tiki.cryptoinfodemo.domain.usecase.UpdateLocalCurrencyInfoUseCase
import com.tiki.cryptoinfodemo.domain.usecase.UpdateLocalCurrencyInfoUseCaseImpl
import com.tiki.cryptoinfodemo.presentation.viewmodel.CurrencyListViewModel
import com.tiki.cryptoinfodemo.presentation.viewmodel.CurrencySharedViewModel
import com.tiki.cryptoinfodemo.presentation.viewmodel.MainActivityViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    factory<LocalCurrencyRepository> { LocalCurrencyRepositoryImpl(get()) }
    factory<RemoteCurrencyRepository> { RemoteCurrencyRepositoryImpl(get()) }
    factory<CurrencyService> { get<Retrofit>().create(CurrencyService::class.java) }

    single { provideHttpClient() }
    single { provideConverterFactory() }
    single { provideRetrofit(get(), get()) }

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

fun provideHttpClient(): OkHttpClient {
    return OkHttpClient
        .Builder()
        .build()
}


fun provideConverterFactory(): GsonConverterFactory =
    GsonConverterFactory.create()


fun provideRetrofit(
    okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory
): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://raw.githubusercontent.com/WangberlinT/Files/main/")
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .build()
}