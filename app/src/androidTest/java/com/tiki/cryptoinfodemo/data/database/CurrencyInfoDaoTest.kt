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
    fun insertCurrencyInfoWithCryptoType() = runBlocking {
        val currencyInfo = CurrencyInfo(
            id = "1",
            name = "Bitcoin",
            symbol = "BTC",
            code = null,
            type = CurrencyType.CRYPTO
        )
        currencyInfoDao.insertAll(listOf(currencyInfo))
        val allCurrencyInfo = currencyInfoDao.getAll()
        Truth.assertThat(allCurrencyInfo[0].type).isEqualTo(CurrencyType.CRYPTO)
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

}