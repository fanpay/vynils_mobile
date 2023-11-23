package com.uniandes.vynilsmobile.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.uniandes.vynilsmobile.data.model.Album

@Dao
interface AlbumsDao {
    @Query("SELECT * FROM albums_table ORDER BY id DESC")
    fun getAlbums(): List<Album>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(album: Album)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(albums: List<Album>)

    @Query("DELETE FROM albums_table")
    suspend fun deleteAll(): Int
}
