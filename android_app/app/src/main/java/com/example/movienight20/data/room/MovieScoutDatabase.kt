package com.example.movienight20.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

// TODO rename all entity classes "SomethingEntity"
@Database(entities = [RecentMovieId::class, MovieInfoBasic::class, RemoteKeys::class], version = 1)
abstract class MovieScoutDatabase : RoomDatabase() {
    abstract fun recentMovieIds(): RecentMovieIdDao
    abstract fun movieInfoBasic(): MovieInfoBasicDao // TODO rename methods to end with "Dao"
    abstract fun getRemoteKeysDao(): RemoteKeysDao
}