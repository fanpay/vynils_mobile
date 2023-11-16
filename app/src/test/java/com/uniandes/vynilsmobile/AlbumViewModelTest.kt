package com.uniandes.vynilsmobile

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.uniandes.vynilsmobile.data.exceptions.ApiRequestException
import com.uniandes.vynilsmobile.data.model.Album
import com.uniandes.vynilsmobile.data.repository.AlbumRepository
import com.uniandes.vynilsmobile.viewmodel.AlbumViewModel
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

class AlbumViewModelTest {
    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @OptIn(DelicateCoroutinesApi::class)
    private val testDispatcher = newSingleThreadContext("Test thread")

    private val album1 = Album(
        id = 1,
        name = "Sample Album 1",
        cover = "https://example.com/album_cover.jpg",
        releaseDate = "2023-01-01",
        description = "This is a sample album",
        genre = "Rock",
        recordLabel = "Sample Records"
    )

    private val album2 = Album(
        id = 2,
        name = "Sample Album 2",
        cover = "https://example.com/album_cover.jpg",
        releaseDate = "2023-01-01",
        description = "This is a sample album",
        genre = "Rock",
        recordLabel = "Sample Records"
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
        val expectedAlbums = listOf(album1, album2)

        val application = mock(Application::class.java)
        `when`(application.applicationContext).thenReturn(application)

        val repository = mock(AlbumRepository::class.java)
        runBlocking {
            `when`(repository.getAllAlbums()).thenReturn(expectedAlbums)
        }

        val viewModel = AlbumViewModel(application)
        viewModel.albumsRepository = repository

        `when`(runBlocking { repository.getAllAlbums() }).thenReturn(expectedAlbums)

        Assert.assertEquals(expectedAlbums, viewModel.albums.value)
        Assert.assertEquals(false, viewModel.eventNetworkError.value)
        Assert.assertEquals(false, viewModel.isNetworkErrorShown.value)
    }

    @Test
    fun `test refreshDataFromNetwork error`() {
        val application = mock(Application::class.java)
        `when`(application.applicationContext).thenReturn(application)

        val repository = mock(AlbumRepository::class.java)
        val viewModel = AlbumViewModel(application)
        viewModel.albumsRepository = repository

        `when`(runBlocking { repository.getAllAlbums() }).thenThrow(ApiRequestException("Error"))

        Assert.assertNull(viewModel.albums.value)
        viewModel.onNetworkErrorShown()
        Assert.assertEquals(true, viewModel.isNetworkErrorShown.value)
    }
}