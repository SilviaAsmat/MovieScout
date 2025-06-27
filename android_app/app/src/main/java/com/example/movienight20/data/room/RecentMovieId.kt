package com.example.movienight20.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_movie_ids")
data class RecentMovieId(
    @PrimaryKey
    val id: Int
)