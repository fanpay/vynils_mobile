package com.uniandes.vynilsmobile.data.model

import java.io.Serializable

data class Album (
    val albumId:Int,
    val name:String,
    val cover:String,
    val releaseDate:String,
    val description:String,
    val genre:String,
    val recordLabel:String
) : Serializable
