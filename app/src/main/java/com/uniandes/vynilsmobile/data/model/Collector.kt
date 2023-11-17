package com.uniandes.vynilsmobile.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "collectors_table")
data class Collector (
    @PrimaryKey val collectorId: Int,
    val name:String,
    val telephone:String,
    val email:String
): Serializable
