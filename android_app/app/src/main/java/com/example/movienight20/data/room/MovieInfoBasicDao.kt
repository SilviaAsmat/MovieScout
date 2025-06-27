package com.example.movienight20.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieInfoBasicDao {
    @Query("SELECT * FROM movie_info_basic")
    fun getAll(): List<MovieInfoBasic>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg movieInfoBasic: MovieInfoBasic)
}