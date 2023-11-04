package com.uniandes.vynilsmobile.data.service

import com.uniandes.vynilsmobile.data.model.Album
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AlbumApi {

    @GET("albums")
    suspend fun getAllAlbums(): Response<List<Album>>

    @POST("albums")
    suspend fun createAlbum(@Body album: Album) : Response<Album>

}