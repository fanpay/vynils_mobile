package com.uniandes.vynilsmobile.data.repository

import android.app.Application
import com.uniandes.vynilsmobile.data.model.Album
import com.uniandes.vynilsmobile.data.service.RetrofitBroker

class AlbumRepository(val application: Application) {

    suspend fun getAllAlbums(): List<Album> = RetrofitBroker.getAllAlbums()
    suspend fun createAlbum(album: Album) = RetrofitBroker.createAlbum(album)

}