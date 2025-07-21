package com.example.movienight20.ui.recently_viewed

import androidx.compose.runtime.Composable
import com.example.movienight20.ui.components.MovieListRow

@Composable
fun RecentlyViewedMoviesRow(recents: RecentlyViewedViewState, onClickMoviePhoto: (Int) -> Unit) {
    when (recents) {
            is RecentlyViewedViewState.Data -> {
                MovieListRow(
                    movieInfo = recents.cards,
                    onClickMoviePhoto = onClickMoviePhoto
                )
            }

            is RecentlyViewedViewState.Empty -> {
                // TODO create empty message composable
//                    val emptyMovieInfo = MoviesCollectionInfo(pass in empty info)
//                    HorizontalMovieListDisplay()
            }

            is RecentlyViewedViewState.Loading -> {
                // TODO create loading state for section
            }
        }
    }