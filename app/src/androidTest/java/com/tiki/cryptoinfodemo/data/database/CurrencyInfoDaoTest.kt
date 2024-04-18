package com.tiki.cryptoinfodemo.data.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Before

@RunWith(AndroidJUnit4::class)
@SmallTest
class CurrencyInfoDaoTest {
    private lateinit var database: CurrencyInfoDatabase
    private lateinit var currencyInfoDao: CurrencyInfoDao

    @Before
    fun setupDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CurrencyInfoDatabase::class.java
        ).allowMainThreadQueries().build()

        currencyInfoDao = database.currencyInfoDao()
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun insert10CurrencyInfoWith5FiatTypeAnd5CryptoType() = runBlocking {
        val fiat = (1..5).map {
            CurrencyInfo(
                id = "Fiat$it",
                name = "Dollar $it",
                symbol = "F$it",
                code = "F$it",
                type = CurrencyType.FIAT
            )
        }
        val crypto = (1..5).map {
            CurrencyInfo(
                id = "Crypto$it",
                name = "Bitcoin $it",
                symbol = "BTC$it",
                code = "BTC$it",
                type = CurrencyType.CRYPTO
            )
        }
        currencyInfoDao.insertAll(fiat + crypto)
        val fiatCount = currencyInfoDao.getAll().count { it.type == CurrencyType.FIAT }
        val cryptoCount = currencyInfoDao.getAll().count { it.type == CurrencyType.CRYPTO }
        Truth.assertThat(fiatCount).isEqualTo(5)
        Truth.assertThat(cryptoCount).isEqualTo(5)
    }

    @Test
    fun getAllCryptoCurrencies() = runBlocking {
        val fiat = (1..5).map {
            CurrencyInfo(
                id = "Fiat$it",
                name = "Dollar $it",
                symbol = "F$it",
                code = "F$it",
                type = CurrencyType.FIAT
            )
        }
        val crypto = (1..5).map {
            CurrencyInfo(
                id = "Crypto$it",
                name = "Bitcoin $it",
                symbol = "BTC$it",
                code = "BTC$it",
                type = CurrencyType.CRYPTO
            )
        }
        currencyInfoDao.insertAll(fiat + crypto)
        val allCryptoCurrencies = currencyInfoDao.getAllCryptoCurrencies()
        Truth.assertThat(allCryptoCurrencies.size).isEqualTo(5)
        Truth.assertThat(allCryptoCurrencies).isEqualTo(crypto)
    }

    @Test
    fun getAllFiatCurrencies() = runBlocking {
        val fiat = (1..5).map {
            CurrencyInfo(
                id = "Fiat$it",
                name = "Dollar $it",
                symbol = "F$it",
                code = "F$it",
                type = CurrencyType.FIAT
            )
        }
        val crypto = (1..5).map {
            CurrencyInfo(
                id = "Crypto$it",
                name = "Bitcoin $it",
                symbol = "BTC$it",
                code = "BTC$it",
                type = CurrencyType.CRYPTO
            )
        }
        currencyInfoDao.insertAll(fiat + crypto)
        val allFiatCurrencies = currencyInfoDao.getAllFiatCurrencies()
        Truth.assertThat(allFiatCurrencies).isEqualTo(fiat)
    }

    @Test
    fun searchInAllCurrenciesCase1() = runBlocking {
        val target = CurrencyInfo(
            id = "Fiat1",
            name = "Foobar",
            symbol = "F1",
            code = "F1",
            type = CurrencyType.FIAT
        )

        val currencies = listOf(
            target,
            target.copy(id = "Fiat2", name = "BFoo"),
            CurrencyInfo(
                id = "Crypto1",
                name = "Barfoo",
                symbol = "BTC",
                code = "BTC",
                type = CurrencyType.CRYPTO
            ),
        )
        currencyInfoDao.insertAll(currencies)
        val searchResult = currencyInfoDao.search("Foo")
        Truth.assertThat(searchResult).isEqualTo(listOf(target))
    }

    @Test
    fun searchInAllCurrenciesCase2() = runBlocking {
        val pattern = "Foo"
        val target = listOf(
            CurrencyInfo(
                id = "Fiat1",
                name = "Foobar",
                symbol = "F1",
                code = "F1",
                type = CurrencyType.FIAT
            ),
            CurrencyInfo(
                id = "Fiat2",
                name = "BFoo",
                symbol = "Foo2",
                code = "F2",
                type = CurrencyType.FIAT
            ),
            CurrencyInfo(
                id = "Fiat3",
                name = "Not Me",
                symbol = "Not Me",
                code = "Foo2",
                type = CurrencyType.FIAT
            ),
        )

        val currencies = target + listOf(
            CurrencyInfo(
                id = "Crypto1",
                name = "Barfoo",
                symbol = "BTC",
                code = "BTC",
                type = CurrencyType.CRYPTO
            ),
        )
        currencyInfoDao.insertAll(currencies)
        val searchResult = currencyInfoDao.search(pattern)
        Truth.assertThat(searchResult).isEqualTo(target)
    }
}