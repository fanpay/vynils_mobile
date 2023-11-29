package com.uniandes.vynilsmobile.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "albums_table")
data class Album (
    @PrimaryKey
    val id:Int? = null,
    val name:String,
    val cover:String,
    val releaseDate:String,
    val description:String,
    val genre:String,
    val recordLabel:String
) : Serializable
