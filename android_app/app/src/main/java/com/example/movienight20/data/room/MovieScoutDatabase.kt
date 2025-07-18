package com.example.movienight20.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movienight20.data.room.movie_collection_types.NowPlayingMoviesDao
import com.example.movienight20.data.room.movie_collection_types.NowPlayingMoviesEntity
import com.example.movienight20.data.room.movie_collection_types.PopularMoviesDao
import com.example.movienight20.data.room.movie_collection_types.PopularMoviesEntity
import com.example.movienight20.data.room.movie_collection_types.TopRatedMoviesDao
import com.example.movienight20.data.room.movie_collection_types.TopRatedMoviesEntity
import com.example.movienight20.data.room.movie_collection_types.UpcomingMoviesDao
import com.example.movienight20.data.room.movie_collection_types.UpcomingMoviesEntity
import com.example.movienight20.data.room.movie_info_basic.MovieInfoBasicDao
import com.example.movienight20.data.room.movie_info_basic.MovieInfoBasicEntity
import com.example.movienight20.data.room.recently_viewed.RecentMovieIdDao
import com.example.movienight20.data.room.recently_viewed.RecentMovieIdEntity
import com.example.movienight20.data.room.remote_keys.RemoteKeysDao
import com.example.movienight20.data.room.remote_keys.RemoteKeysEntity

@Database(entities = [
    RecentMovieIdEntity::class, MovieInfoBasicEntity::class, RemoteKeysEntity::class,
    PopularMoviesEntity::class, UpcomingMoviesEntity::class, NowPlayingMoviesEntity::class, TopRatedMoviesEntity::class,], version = 1)
abstract class MovieScoutDatabase : RoomDatabase() {
    abstract fun recentMovieIdsDao(): RecentMovieIdDao
    abstract fun movieInfoBasicDao(): MovieInfoBasicDao
    abstract fun getRemoteKeysDao(): RemoteKeysDao
    abstract fun getPopularMoviesDao(): PopularMoviesDao
    abstract fun getNowPlayingMoviesDao(): NowPlayingMoviesDao
    abstract fun getTopRatedMoviesDao(): TopRatedMoviesDao
    abstract fun getUpcomingMoviesDao(): UpcomingMoviesDao
}