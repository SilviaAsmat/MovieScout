package com.example.movienight20.data.room.movie_collection_types

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.movienight20.data.room.movie_info_basic.MovieInfoBasicEntity

@Dao
interface PopularMoviesDao {
    @Query("SELECT * FROM popular_movies_keys INNER JOIN movie_info_basic " +
            "ON movie_info_basic.remote_id = popular_movies_keys.remote_id")
    fun getPopularMovies(): PagingSource<Int, MovieInfoBasicEntity>

    @Query("SELECT remote_id FROM movie_info_basic")
    fun getMovieIds(): List<Int>

    @Insert(onConflict = 1)
    fun addMovies(movieKeys: List<PopularMoviesEntity>)

    @Query("DELETE FROM popular_movies_keys ")
    fun clearMovies()

}