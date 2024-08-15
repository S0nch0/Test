package com.example.test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito

class ViewModelTest {
    @get:Rule
    val rule: TestRule =InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup(){
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @Test
    fun getSuccessResponse() {
        val repository= Mockito.mock(Repository::class.java)
        val successfulResponse = CoinResponse(CoinRate("0"))
        val viewModel = CoinViewModel(repository)
        val eventList = mutableListOf<CoinViewModel.CoinState>()
        viewModel.coinState.observeForever{
            eventList.add(it)
        }
        runBlocking {
            Mockito.`when`(repository.getCoinRate()).thenReturn(successfulResponse)
        }
        viewModel.getRate()
        Assert.assertEquals(CoinViewModel.CoinState.NoData, eventList[0])
        Assert.assertEquals(CoinViewModel.CoinState.Processing, eventList[1])
        val coinRate = eventList[2] as CoinViewModel.CoinState.UpdateData
        Assert.assertEquals("0", coinRate.rate)
    }

    @Test
    fun getNullResponse(){
        val repository = Mockito.mock(Repository::class.java)
        val nullResponse = CoinResponse(null)
        val viewModel = CoinViewModel(repository)
        val eventList = mutableListOf<CoinViewModel.CoinState>()
        viewModel.coinState.observeForever{
            eventList.add(it)
        }
        runBlocking {
            Mockito.`when`(repository.getCoinRate()).thenReturn(nullResponse)
        }
        viewModel.getRate()
        Assert.assertEquals(CoinViewModel.CoinState.NoData, eventList[0])
        Assert.assertEquals(CoinViewModel.CoinState.Processing, eventList[1])
        Assert.assertEquals(CoinViewModel.CoinState.Error, eventList[2])
    }
}