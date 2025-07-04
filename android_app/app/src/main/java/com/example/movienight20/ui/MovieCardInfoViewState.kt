package com.example.movienight20.ui

// add @immutable annotation for composable stability
class MovieCardInfoViewState(
    val id: Int,
    val title: String?,
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