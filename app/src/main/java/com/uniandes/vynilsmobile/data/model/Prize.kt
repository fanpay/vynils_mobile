package com.uniandes.vynilsmobile.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "prizes_table")
data class Prize (
    @PrimaryKey
    val id:Int? = null,
    val name:String,
    val description:String,
    val organization:String
) : Serializable

