package com.uniandes.vynilsmobile.data.repository

import android.app.Application
import com.uniandes.vynilsmobile.data.exceptions.ApiRequestException
import com.uniandes.vynilsmobile.data.model.Album
import com.uniandes.vynilsmobile.data.service.RetrofitBroker

class AlbumRepository(val application: Application) {

    //suspend fun getAllAlbums(): List<Album> = RetrofitBroker.getAllAlbums()
    suspend fun getAllAlbums(): List<Album> {
        return try {
            var albums: List<Album> = emptyList()
            RetrofitBroker.getAlbums(
                onComplete = { response -> albums = response },
                onError = { error -> throw ApiRequestException("Error obteniendo álbumes", error) }
            )
            albums
        } catch (e: Throwable) {
            throw ApiRequestException("Error obteniendo álbumes -> ${e.cause}", e)
        }
    }
    suspend fun createAlbum(album: Album) = RetrofitBroker.createAlbum(album)

}