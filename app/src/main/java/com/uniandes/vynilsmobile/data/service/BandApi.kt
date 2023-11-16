package com.uniandes.vynilsmobile.data.service

import com.uniandes.vynilsmobile.data.model.Band
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface BandApi {

    @GET("bands")
    suspend fun getAllBands(): Response<List<Band>>

    @POST("bands")
    suspend fun createBand(@Body band: Band): Response<Band>
}
