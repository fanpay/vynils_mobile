package com.uniandes.vynilsmobile.data.repository

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
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

class AlbumRepository(val application: Application, private val albumsDao: AlbumsDao) {
    suspend fun getAllAlbums(): List<Album> {
        return withContext(Dispatchers.IO) {

            //Log.v("AlbumRepository", "No cached albums found. Retrieving from API.")
            /*if (!isNetworkAvailable()) {
                return@withContext emptyList()
            }*/

            try {
                val cached = albumsDao.getAlbums()

                if (cached.isNotEmpty()) {
                    Log.v("AlbumRepository", "Retrieving cached albums. ${cached.size} found.")
                    return@withContext cached
                }
                //Log.i("Albumes", cached.toString())
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


    private suspend fun isNetworkAvailable(): Boolean = withContext(Dispatchers.IO) {
        val cm =
            application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return@withContext cm.activeNetworkInfo?.isConnected == true
    }

    private suspend fun insertAlbumsIntoDatabase(albums: List<Album>) {
        Log.d(
            "AlbumRepository",
            "VinylRoomDatabase instance: ${VinylRoomDatabase.getDatabase(application).hashCode()}"
        )
        albumsDao.insertAll(albums)
        Log.v("AlbumRepository", "Inserted ${albums.size} albums into the local database.")
    }

    suspend fun createAlbum(album: Album) = RetrofitBroker.createAlbum(album)

}