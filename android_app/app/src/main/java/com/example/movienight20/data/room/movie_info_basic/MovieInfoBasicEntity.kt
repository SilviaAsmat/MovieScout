package com.example.movienight20.data.room.movie_info_basic

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "movie_info_basic",
)
data class MovieInfoBasicEntity(
    @PrimaryKey
    @ColumnInfo(name = "remote_id")
    val remoteId: Int,
    @ColumnInfo(name = "poster_path")
    val posterPath: String,
    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String,
    @ColumnInfo(name = "name")
    val name: String
)
