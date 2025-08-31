package com.silas.movienight20.data.room.remote_keys

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeysEntity>)

    @Query("SELECT * FROM remote_key WHERE movie_id = :id AND collection_type = :name")
    suspend fun getRemoteKeyByMovieID(id: Int, name: String): RemoteKeysEntity

    @Query("DELETE FROM remote_key WHERE collection_type = :name")
    suspend fun clearRemoteKeys(name: String)

    @Query("SELECT created_at FROM remote_key WHERE collection_type = :name ORDER BY created_at DESC LIMIT 1")
    suspend fun getCreationTime(name: String): Long
}