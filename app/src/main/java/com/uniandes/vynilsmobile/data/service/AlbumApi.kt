package com.uniandes.vynilsmobile.data.service

import com.uniandes.vynilsmobile.data.model.Album
import retrofit2.Response
import retrofit2.http.GET

interface AlbumApi {

    @GET("albums")
    suspend fun getAllAlbums(): Response<List<Album>>

}