package com.example.movienight20.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RecentMovieIdDao {

    @Query("SELECT * FROM recent_movie_ids")
    fun getAll(): List<RecentMovieIdEntity>

    @Insert(onConflict = 1)
    fun insertMovieId(movie: RecentMovieIdEntity)
}