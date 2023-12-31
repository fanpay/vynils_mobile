package com.uniandes.vynilsmobile.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.uniandes.vynilsmobile.data.model.Artist

@Dao
interface ArtistsDao {
    @Query("SELECT * FROM artists_table ORDER BY artistId ASC")
    fun getArtists(): List<Artist>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(artist: Artist)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(artists: List<Artist>)

    @Query("DELETE FROM artists_table")
    suspend fun deleteAll(): Int
}
