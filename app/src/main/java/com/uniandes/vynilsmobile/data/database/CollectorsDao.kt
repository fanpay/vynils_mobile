package com.uniandes.vynilsmobile.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.uniandes.vynilsmobile.data.model.Collector

@Dao
interface CollectorsDao {
    @Query("SELECT * FROM collectors_table")
    fun getCollectors():List<Collector>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(collector: Collector)

    @Query("DELETE FROM collectors_table")
    suspend fun deleteAll():Int
}