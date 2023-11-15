package com.uniandes.vynilsmobile.data.repository

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.uniandes.vynilsmobile.R
import com.uniandes.vynilsmobile.data.database.AlbumsDao
import com.uniandes.vynilsmobile.data.exceptions.ApiRequestException
import com.uniandes.vynilsmobile.data.model.Album
import com.uniandes.vynilsmobile.data.service.RetrofitBroker

class AlbumRepository(val application: Application, private val albumsDao: AlbumsDao) {

    suspend fun getAllAlbums(): List<Album> {
        var cached = albumsDao.getAlbums()

        //if(cached.isNotEmpty()){
            Log.v("AlbumRepository", "Retrieving cached albums. ${cached.size} found.}")
        //}

        return cached.ifEmpty {
            Log.v("AlbumRepository", "No cached albums found. Retrieving from API.")
            val cm = application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE) {
                emptyList()
            } else {
                try {
                    var albums: List<Album> = emptyList()

                    RetrofitBroker.getAlbums(
                        onComplete = { response -> albums = response },
                        onError = { error ->
                            throw ApiRequestException(
                                application.resources.getString(
                                    R.string.error_retrieve_albums
                                ), error
                            )
                        }
                    )
                    albums
                } catch (e: Throwable) {
                    throw ApiRequestException("${R.string.error_retrieve_albums} -> ${e.cause}", e)
                }
            }
        }
    }
    suspend fun createAlbum(album: Album) = RetrofitBroker.createAlbum(album)

}