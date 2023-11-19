package com.uniandes.vynilsmobile.data.service

import com.uniandes.vynilsmobile.data.model.Collector
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CollectorApi {

    @GET("collectors")
    suspend fun getAllCollectors(): Response<List<Collector>>

    @POST("collectors")
    suspend fun createCollector(@Body collector: Collector) : Response<Collector>
}