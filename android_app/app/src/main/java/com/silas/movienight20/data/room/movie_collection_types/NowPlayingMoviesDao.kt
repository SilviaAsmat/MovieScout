package com.silas.movienight20.data.room.movie_collection_types

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.silas.movienight20.data.room.movie_info_basic.MovieInfoBasicEntity
import retrofit2.http.DELETE

@Dao
interface NowPlayingMoviesDao {
    @Query("SELECT * FROM now_playing_keys INNER JOIN movie_info_basic " +
            "ON movie_info_basic.remote_id = now_playing_keys.remote_id")
    fun getNowPlayingMovies(): PagingSource<Int, MovieInfoBasicEntity>

    @Insert(onConflict = 1)
    fun addMovies(movieKeys: List<NowPlayingMoviesEntity>)

    @Query("DELETE FROM now_playing_keys ")
    fun clearMovies()

    @Query("SELECT remote_id FROM now_playing_keys")
    fun getMovieIds(): List<Int>

}