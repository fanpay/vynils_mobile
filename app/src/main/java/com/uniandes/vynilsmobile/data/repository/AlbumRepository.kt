package com.uniandes.vynilsmobile.data.repository

import android.app.Application
import com.uniandes.vynilsmobile.R
import com.uniandes.vynilsmobile.data.exceptions.ApiRequestException
import com.uniandes.vynilsmobile.data.model.Album
import com.uniandes.vynilsmobile.data.service.RetrofitBroker

class AlbumRepository(val application: Application) {

    suspend fun getAllAlbums(): List<Album> {
        return try {
            var albums: List<Album> = emptyList()
            RetrofitBroker.getAlbums(
                onComplete = { response -> albums = response },
                onError = { error -> throw ApiRequestException(application.resources.getString(R.string.error_retrieve_albums), error) }
            )
            albums
        } catch (e: Throwable) {
            throw ApiRequestException("${R.string.error_retrieve_albums} -> ${e.cause}", e)
        }
    }
    suspend fun createAlbum(album: Album) = RetrofitBroker.createAlbum(album)

}