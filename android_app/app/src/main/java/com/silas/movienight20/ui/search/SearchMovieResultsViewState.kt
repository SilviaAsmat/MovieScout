package com.silas.movienight20.ui.search

import androidx.compose.runtime.Immutable
import com.silas.movienight20.ui.movie_list.MovieListItemViewState

@Immutable
sealed class SearchMovieResultsViewState {
    data class Data(val results: List<MovieListItemViewState>) : SearchMovieResultsViewState()
    data object Loading : SearchMovieResultsViewState()
}

// Reusable grid item
//@Immutable
//data class ResultItemViewState(
//    val id: Int,
//    val title: String,
//    val backdropPath: String,
//    val posterPath: String,
//    val releaseDate: String,
//    val rating: String
//)