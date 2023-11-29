package com.uniandes.vynilsmobile.data.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://heroku-vinyls-g8-d9e277b35953.herokuapp.com/"
//private const val BASE_URL = "http://10.0.2.2:3000/"

object ApiClient {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val albums : AlbumApi by lazy {
        retrofit.create(AlbumApi::class.java)
    }

    val artists: ArtistApi by lazy {
        retrofit.create(ArtistApi::class.java)
    }

    val comments: CommentApi by lazy {
        retrofit.create(CommentApi::class.java)
    }

    val collectors: CollectorApi by lazy {
        retrofit.create(CollectorApi::class.java)
    }

    val prizes: PrizeApi by lazy {
        retrofit.create(PrizeApi::class.java)
    }

}