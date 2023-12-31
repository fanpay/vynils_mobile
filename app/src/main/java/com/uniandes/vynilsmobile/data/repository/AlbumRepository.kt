package com.uniandes.vynilsmobile.data.repository

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.uniandes.vynilsmobile.R
import com.uniandes.vynilsmobile.data.database.AlbumsDao
import com.uniandes.vynilsmobile.data.database.VinylRoomDatabase
import com.uniandes.vynilsmobile.data.exceptions.ApiRequestException
import com.uniandes.vynilsmobile.data.model.Album
import com.uniandes.vynilsmobile.data.service.RetrofitBroker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlbumRepository(val application: Application) {
    private val albumsDao: AlbumsDao by lazy {
        VinylRoomDatabase.getDatabase(application).albumsDao()
    }

    suspend fun getAllAlbums(): List<Album> {
        return withContext(Dispatchers.IO) {

            try {
                val cached = albumsDao.getAlbums()

                if (!isNetworkAvailable() && cached.isNotEmpty()) {
                    Log.v("AlbumRepository", "No Internet. Retrieving cached albums. ${cached.size} found.")
                    return@withContext cached
                }

                if (!isNetworkAvailable() && cached.isEmpty()) {
                    Log.v("AlbumRepository", "No Internet. No cached data was found. Retrieving empty list.")
                    return@withContext cached
                }

                if (cached.isNotEmpty()) {
                    Log.v("AlbumRepository", "Retrieving cached albums. ${cached.size} found.")
                    return@withContext cached
                }

                var albums: List<Album> = emptyList()

                RetrofitBroker.getAlbums(
                    onComplete = { response ->
                        albums = response
                        CoroutineScope(Dispatchers.IO).launch {
                            insertAlbumsIntoDatabase(albums)
                        }
                    },
                    onError = { error ->
                        throw ApiRequestException(
                            application.resources.getString(
                                R.string.error_retrieve_albums
                            ), error
                        )
                    }
                )

                return@withContext albums
            } catch (e: Throwable) {
                Log.e("AlbumRepository", "Error retrieving albums from API: ${e.message}")
                throw ApiRequestException(
                    application.resources.getString(R.string.error_retrieve_albums),
                    e
                )
            }
        }
    }

    suspend fun createAlbum(album: Album,
                            onComplete: (resp: Album) -> Unit,
                            onError: (error: Throwable) -> Unit) {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitBroker.createAlbum(
                    album,
                    onComplete = { response ->
                        CoroutineScope(Dispatchers.IO).launch {
                            insertAlbumIntoDatabase(response)
                        }
                        onComplete(response)
                    },
                    onError = { error ->
                        onError(error)
                        throw ApiRequestException(
                            application.resources.getString(
                                R.string.error_retrieve_albums
                            ), error
                        )
                    }
                )

                return@withContext response
            } catch (e: Throwable) {
                Log.e("AlbumRepository", "Error creating album from API: ${e.message}")
                throw ApiRequestException(
                    application.resources.getString(R.string.error_save_album),
                    e
                )
            }
        }
    }

    private suspend fun isNetworkAvailable(): Boolean = withContext(Dispatchers.IO) {
        val cm = application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork
        val capabilities = cm.getNetworkCapabilities(network)
        return@withContext capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }

    private suspend fun insertAlbumsIntoDatabase(albums: List<Album>) {
        albumsDao.insertAll(albums)
        Log.v("AlbumRepository", "Inserted ${albums.size} albums into the local database.")
    }

    private suspend fun insertAlbumIntoDatabase(album: Album) {
        albumsDao.insert(album)
        Log.v("AlbumRepository", "Inserted 1 album into the local database. ID ${album.id}")
    }
}