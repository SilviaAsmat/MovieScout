package com.silas.movienight20.data.room.movie_collection_types

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.silas.movienight20.data.room.movie_info_basic.MovieInfoBasicEntity

@Dao
interface UpcomingMoviesDao {
    @Query("SELECT * FROM upcoming_movies_keys INNER JOIN movie_info_basic " +
            "ON movie_info_basic.remote_id = upcoming_movies_keys.remote_id")
    fun getUpcomingMovies(): PagingSource<Int, MovieInfoBasicEntity>

    @Insert(onConflict = 1)
    fun addMovies(movieKeys: List<UpcomingMoviesEntity>)

    @Query("DELETE FROM upcoming_movies_keys ")
    fun clearMovies()

    @Query("SELECT remote_id FROM upcoming_movies_keys")
    fun getMovieIds(): List<Int>

}