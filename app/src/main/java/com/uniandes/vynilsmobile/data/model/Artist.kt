package com.uniandes.vynilsmobile.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable
@Entity(tableName = "artists_table")
data class Artist (
    @PrimaryKey
    @SerializedName("id")
    val artistId:Int,
    val name:String,
    val image:String,
    val description:String,
    val birthDate:String
): Serializable