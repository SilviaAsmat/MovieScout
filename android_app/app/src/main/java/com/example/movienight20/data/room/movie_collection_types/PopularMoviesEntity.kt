package com.example.movienight20.data.room.movie_collection_types

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "popular_movies_keys",
    indices = [Index(value = ["remote_id"], unique = true)]
)
data class PopularMoviesEntity(
    @PrimaryKey(autoGenerate = true)
    val localId: Int,
    @ColumnInfo(name = "remote_id")
    val remoteId: Int,
)