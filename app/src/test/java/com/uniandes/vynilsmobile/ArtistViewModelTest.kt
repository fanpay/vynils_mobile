package com.uniandes.vynilsmobile

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.uniandes.vynilsmobile.data.exceptions.ApiRequestException
import com.uniandes.vynilsmobile.data.model.Album
import com.uniandes.vynilsmobile.data.model.Artist
import com.uniandes.vynilsmobile.data.repository.AlbumRepository
import com.uniandes.vynilsmobile.data.repository.ArtistRepository
import com.uniandes.vynilsmobile.viewmodel.AlbumViewModel
import com.uniandes.vynilsmobile.viewmodel.ArtistViewModel
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

class ArtistViewModelTest {
    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @OptIn(DelicateCoroutinesApi::class)
    private val testDispatcher = newSingleThreadContext("Test thread")

    private val artist1 = Artist(
        artistId = 1,
        name = "Sample Artist 1",
        image = "https://example.com/album_cover.jpg",
        description = "This is a sample Artist",
        birthDate = "19/02/1980"
    )

    private val artist2 = Artist(
        artistId = 2,
        name = "Sample Artist 2",
        image = "https://example.com/album_cover.jpg",
        description = "This is a sample Artist",
        birthDate = "10/02/1980"
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
        val expectedArtists = listOf(artist1, artist2)

        val application = mock(Application::class.java)
        val repository = mock(ArtistRepository::class.java)
        runBlocking {
            `when`(repository.getAllArtists()).thenReturn(expectedArtists)
        }

        val viewModel = ArtistViewModel(application)
        viewModel.artistsRepository = repository

        `when`(runBlocking { repository.getAllArtists() }).thenReturn(expectedArtists)

        Assert.assertEquals(expectedArtists, viewModel.artists.value)
        Assert.assertEquals(false, viewModel.eventNetworkError.value)
        Assert.assertEquals(false, viewModel.isNetworkErrorShown.value)
    }

    @Test
    fun `test refreshDataFromNetwork error`() {
        val application = mock(Application::class.java)
        val repository = mock(ArtistRepository::class.java)
        val viewModel = ArtistViewModel(application)
        viewModel.artistsRepository = repository

        `when`(runBlocking { repository.getAllArtists() }).thenThrow(ApiRequestException("Error"))

        Assert.assertNull(viewModel.artists.value)
        viewModel.onNetworkErrorShown()
        Assert.assertEquals(true, viewModel.isNetworkErrorShown.value)
    }
}