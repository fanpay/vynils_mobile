package com.uniandes.vynilsmobile.data.service

import com.uniandes.vynilsmobile.data.model.Prize
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PrizeApi {

    @POST("prizes")
    suspend fun createPrize(@Body prize: Prize) : Response<Prize>

}