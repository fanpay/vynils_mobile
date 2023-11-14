package com.uniandes.vynilsmobile.data.model

import java.io.Serializable
data class Artist (
    val artistId:Int,
    val name:String,
    val image:String,
    val description:String,
    val birthDate:String
): Serializable