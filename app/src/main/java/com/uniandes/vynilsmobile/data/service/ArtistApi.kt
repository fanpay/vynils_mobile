package com.uniandes.vynilsmobile.data.service

import com.uniandes.vynilsmobile.data.model.Artist
import retrofit2.Response
import retrofit2.http.GET

interface ArtistApi {

    @GET("musicians")
    suspend fun getAllArtists(): Response<List<Artist>>

}