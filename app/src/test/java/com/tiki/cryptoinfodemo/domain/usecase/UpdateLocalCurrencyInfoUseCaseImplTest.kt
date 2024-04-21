package com.tiki.cryptoinfodemo.domain.usecase

import com.tiki.cryptoinfodemo.domain.repository.LocalCurrencyRepository
import com.tiki.cryptoinfodemo.domain.repository.RemoteCurrencyRepository
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class UpdateLocalCurrencyInfoUseCaseImplTest {
    @MockK(relaxed = true)
    lateinit var remoteCurrencyRepository: RemoteCurrencyRepository

    @MockK(relaxed = true)
    lateinit var localCurrencyRepository: LocalCurrencyRepository
    lateinit var updateLocalCurrencyInfoUseCase: UpdateLocalCurrencyInfoUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        updateLocalCurrencyInfoUseCase = UpdateLocalCurrencyInfoUseCaseImpl(
            remoteCurrencyRepository, localCurrencyRepository
        )
    }

    @Test
    fun testUpdateLocalCurrencyInfoFromRemote() = runBlocking {
        updateLocalCurrencyInfoUseCase.updateLocalCurrencyInfoFromRemote()
        coVerify { remoteCurrencyRepository.getAllCurrencies() }
        coVerify { localCurrencyRepository.insertAllCurrencies(any()) }
    }

    @Test
    fun testRemoveLocalCurrencyInfo() = runBlocking {
        updateLocalCurrencyInfoUseCase.removeLocalCurrencyInfo()
        coVerify { localCurrencyRepository.removeAllCurrencies() }
    }
}