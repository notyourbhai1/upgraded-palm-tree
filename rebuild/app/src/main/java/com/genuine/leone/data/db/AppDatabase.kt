package com.genuine.leone.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.genuine.leone.data.dao.FavoriteChannelDao
import com.genuine.leone.data.dao.PlaylistDao
import com.genuine.leone.data.dao.StreamDao
import com.genuine.leone.data.entities.FavoriteChannelEntity
import com.genuine.leone.data.entities.PlaylistEntity
import com.genuine.leone.data.entities.StreamEntity

@Database(
    entities = [StreamEntity::class, PlaylistEntity::class, FavoriteChannelEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun streamDao(): StreamDao
    abstract fun playlistDao(): PlaylistDao
    abstract fun favoriteChannelDao(): FavoriteChannelDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "leone.db"
                ).fallbackToDestructiveMigration()
                    .build()
                    .also { instance = it }
            }
        }
    }
}
