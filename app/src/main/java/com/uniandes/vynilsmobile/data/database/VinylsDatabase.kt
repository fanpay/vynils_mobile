package com.uniandes.vynilsmobile.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.uniandes.vynilsmobile.data.model.Album
import com.uniandes.vynilsmobile.data.model.Artist
import com.uniandes.vynilsmobile.data.model.Collector
import com.uniandes.vynilsmobile.data.model.Comment
import com.uniandes.vynilsmobile.data.model.Prize


@Database(
    entities = [Album::class, Artist::class, Collector::class, Comment::class, Prize::class],
    version = 2,
    exportSchema = false
)
abstract class VinylRoomDatabase : RoomDatabase() {
    abstract fun albumsDao(): AlbumsDao
    abstract fun artistsDao(): ArtistsDao
    abstract fun collectorsDao(): CollectorsDao
    abstract fun commentsDao(): CommentsDao
    abstract fun prizesDao(): PrizesDao

    companion object {
        @Volatile
        private var INSTANCE: VinylRoomDatabase? = null

        private const val DATABASE_NAME = "vinyls_database"

        fun getDatabase(context: Context
        ): VinylRoomDatabase{
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VinylRoomDatabase::class.java,
                    DATABASE_NAME
                )
                // Wipes and rebuilds instead of migrating if no Migration object.
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}