package com.example.movienight20.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "movie_info_basic",
//    foreignKeys = [
//        ForeignKey(
//            entity = RecentMovieId::class,
//            parentColumns = arrayOf("id"),
//            childColumns = arrayOf("id"),
//            onDelete = ForeignKey.CASCADE,
//            onUpdate = ForeignKey.CASCADE
//        )
//    ]
)
data class MovieInfoBasic(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "poster_path")
    val posterPath: String?,
    @ColumnInfo(name = "name")
    val name: String?
)
