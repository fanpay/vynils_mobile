package com.uniandes.vynilsmobile.data.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "http://vynils-back-heroku.herokuapp.co/"
//private const val BASE_URL = "http://10.0.2.2:3000/"

object ApiClient {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    //val albums: AlbumApi = retrofit.create(AlbumApi::class.java)
    val albums : AlbumApi by lazy {
        retrofit.create(AlbumApi::class.java)
    }

    val collectors: CollectorApi = retrofit.create(CollectorApi::class.java)
}