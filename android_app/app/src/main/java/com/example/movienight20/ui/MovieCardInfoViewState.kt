package com.example.movienight20.ui

import androidx.compose.runtime.Immutable

@Immutable
class MovieCardInfoViewState(
    val id: Int,
    val title: String,
    val posterPath: String,
    val backdropPath: String
) {
    companion object{
        val NONE = MovieCardInfoViewState(
            id = 0,
            title = "",
            posterPath = "",
            backdropPath = ""
        )
    }
}