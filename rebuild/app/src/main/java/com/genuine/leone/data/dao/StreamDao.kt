package com.genuine.leone.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.genuine.leone.data.entities.StreamEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StreamDao {

    @Query("SELECT * FROM streams ORDER BY createdAt DESC")
    fun getAll(): Flow<List<StreamEntity>>

    @Query("SELECT * FROM streams WHERE id = :id")
    suspend fun getById(id: Long): StreamEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(stream: StreamEntity): Long

    @Update
    suspend fun update(stream: StreamEntity)

    @Delete
    suspend fun delete(stream: StreamEntity)

    @Query("DELETE FROM streams")
    suspend fun clearAll()
}
