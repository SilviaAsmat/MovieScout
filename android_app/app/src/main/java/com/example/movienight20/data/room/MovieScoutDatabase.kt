package com.example.movienight20.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RecentMovieId::class, MovieInfoBasic::class], version = 1)
abstract class MovieScoutDatabase : RoomDatabase() {
    abstract fun recentMovieIds(): RecentMovieIdDao
    abstract fun movieInfoBasic(): MovieInfoBasicDao
}