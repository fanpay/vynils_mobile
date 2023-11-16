package com.uniandes.vynilsmobile

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.uniandes.vynilsmobile.data.exceptions.ApiRequestException
import com.uniandes.vynilsmobile.data.model.Album
import com.uniandes.vynilsmobile.data.model.Collector
import com.uniandes.vynilsmobile.data.repository.AlbumRepository
import com.uniandes.vynilsmobile.data.repository.CollectorRepository
import com.uniandes.vynilsmobile.viewmodel.AlbumViewModel
import com.uniandes.vynilsmobile.viewmodel.CollectorViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class CollectorViewModelTest {
    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @OptIn(DelicateCoroutinesApi::class)
    private val testDispatcher = newSingleThreadContext("Test thread")

    private val collector1 = Collector(
        collectorId = 1,
        name = "Sample Collector 1",
        telephone = "1234567890",
        email = "hola@hotmail.com"
    )

    private val collector2 = Collector(
        collectorId = 2,
        name = "Sample Collector 2",
        telephone = "0987654321",
        email = "hola12334@hotmail.com"
    )
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.close()
    }

    @Test
    fun `test refreshDataFromNetwork success`() {
        val expectedCollectors = listOf(collector1, collector2)

        val application = mock(Application::class.java)
        val repository = mock(CollectorRepository::class.java)
        runBlocking {
            `when`(repository.getAllCollectors()).thenReturn(expectedCollectors)
        }

        val viewModel = CollectorViewModel(application)
        viewModel.collectorsRepository = repository

        `when`(runBlocking { repository.getAllCollectors() }).thenReturn(expectedCollectors)

        Assert.assertEquals(expectedCollectors, viewModel.collectors.value)
        Assert.assertEquals(false, viewModel.eventNetworkError.value)
        Assert.assertEquals(false, viewModel.isNetworkErrorShown.value)
    }

    @Test
    fun `test refreshDataFromNetwork error`() {
        val application = mock(Application::class.java)
        val repository = mock(CollectorRepository::class.java)
        val viewModel = CollectorViewModel(application)
        viewModel.collectorsRepository = repository

        `when`(runBlocking { repository.getAllCollectors() }).thenThrow(ApiRequestException("Error"))

        Assert.assertNull(viewModel.collectors.value)
        viewModel.onNetworkErrorShown()
        Assert.assertEquals(true, viewModel.isNetworkErrorShown.value)
    }
}