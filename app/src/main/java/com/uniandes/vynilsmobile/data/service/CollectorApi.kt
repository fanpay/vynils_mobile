package com.uniandes.vynilsmobile.data.service

import com.uniandes.vynilsmobile.data.model.Collector

import retrofit2.Response
import retrofit2.http.GET

interface CollectorApi {

    @GET("collectors")
    suspend fun getAllCollectors(): Response<List<Collector>>

}