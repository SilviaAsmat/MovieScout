package com.example.movienight20.ui.search

import androidx.compose.runtime.Immutable

@Immutable
sealed class SearchMovieResultsViewState {
    data class Data(
        val results: List<Results>
    ): SearchMovieResultsViewState()
    data object Loading: SearchMovieResultsViewState()
}
data class Results (
    val id: Int,
    val title: String,
    val backdropPath: String,
    val posterPath: String,
    val releaseDate: String,
    val rating: String
)