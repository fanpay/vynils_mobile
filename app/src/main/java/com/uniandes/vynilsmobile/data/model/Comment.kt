package com.uniandes.vynilsmobile.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "comments_table")
data class Comment (
    @PrimaryKey
    val id:Int? = null,
    val description:String,
    val rating:Int,
    @Embedded(prefix = "collector_")
    val collector:Collector? = Collector(
        collectorId = 100
    )
): Serializable
