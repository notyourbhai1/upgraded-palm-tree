package com.genuine.leone.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.genuine.leone.data.entities.FavoriteChannelEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteChannelDao {

    @Query("SELECT * FROM favorite_channels WHERE playlistUrl = :playlistUrl")
    fun getByPlaylist(playlistUrl: String): Flow<List<FavoriteChannelEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: FavoriteChannelEntity): Long

    @Delete
    suspend fun delete(favorite: FavoriteChannelEntity)

    @Query("DELETE FROM favorite_channels WHERE favoriteChannel = :channelName AND playlistUrl = :playlistUrl")
    suspend fun deleteByName(channelName: String, playlistUrl: String)
}
