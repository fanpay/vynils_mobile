package com.uniandes.vynilsmobile.data.service

import com.uniandes.vynilsmobile.data.model.Artist
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ArtistApi {

    @GET("musicians")
    suspend fun getAllArtists(): Response<List<Artist>>

    @POST("musicians")
    suspend fun createArtist(@Body album: Artist) : Response<Artist>

}