package com.uniandes.vynilsmobile.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.uniandes.vynilsmobile.data.model.Prize

@Dao
interface PrizesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(prize: Prize)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(prizes: List<Prize>)
}
