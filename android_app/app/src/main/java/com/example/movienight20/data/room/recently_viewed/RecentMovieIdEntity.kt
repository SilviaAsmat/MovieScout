package com.example.movienight20.data.room.recently_viewed

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_movie_ids")
data class RecentMovieIdEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "timestamp")
    val timestamp: Long = System.currentTimeMillis()
)