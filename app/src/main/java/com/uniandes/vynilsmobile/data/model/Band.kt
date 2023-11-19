package com.uniandes.vynilsmobile.data.model

import java.io.Serializable

data class Band (
    val bandId:Int,
    val name:String,
    val image:String,
    val description:String,
    val creationDate:String
) : Serializable
