package com.example.movienight20.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.paging.PagingSource

import kotlinx.coroutines.flow.Flow

@Dao
interface MovieInfoBasicDao {
    @Query("SELECT * FROM movie_info_basic INNER JOIN recent_movie_ids ON movie_info_basic.remote_id = recent_movie_ids.id " +
            "GROUP BY id ORDER BY recent_movie_ids.timestamp DESC")
    fun getRecentlyViewed(): Flow<List<MovieInfoBasicEntity>>

    @Insert(onConflict = 1)
    fun insertMovie(vararg movieInfoBasic: MovieInfoBasicEntity)

    @Insert(onConflict = 1)
    fun insertAllPopularMovies(movies: List<MovieInfoBasicEntity>)

    @Query("Select * From movie_info_basic")
    fun getMoviesPagingSource(): PagingSource<Int, MovieInfoBasicEntity>
}