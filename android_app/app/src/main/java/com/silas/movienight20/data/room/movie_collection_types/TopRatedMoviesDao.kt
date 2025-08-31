package com.silas.movienight20.data.room.movie_collection_types

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.silas.movienight20.data.room.movie_info_basic.MovieInfoBasicEntity

@Dao
interface TopRatedMoviesDao {
    @Query("SELECT * FROM top_rated_keys INNER JOIN movie_info_basic " +
            "ON movie_info_basic.remote_id = top_rated_keys.remote_id")
    fun getTopRatedMovies(): PagingSource<Int, MovieInfoBasicEntity>

    @Insert(onConflict = 1)
    fun addMovies(movieKeys: List<TopRatedMoviesEntity>)

    @Query("DELETE FROM top_rated_keys ")
    fun clearMovies()

    @Query("SELECT remote_id FROM top_rated_keys")
    fun getMovieIds(): List<Int>
}