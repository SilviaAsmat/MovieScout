package com.example.movienight20.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RecentMovieIdEntity::class, MovieInfoBasicEntity::class, RemoteKeysEntity::class], version = 1)
abstract class MovieScoutDatabase : RoomDatabase() {
    abstract fun recentMovieIdsDao(): RecentMovieIdDao
    abstract fun movieInfoBasicDao(): MovieInfoBasicDao
    abstract fun getRemoteKeysDao(): RemoteKeysDao
}