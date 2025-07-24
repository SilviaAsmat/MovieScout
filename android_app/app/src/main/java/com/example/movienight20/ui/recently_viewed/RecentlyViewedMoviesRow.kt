package com.example.movienight20.ui.recently_viewed

import androidx.compose.runtime.Composable
import com.example.movienight20.ui.components.HeaderLabel
import com.example.movienight20.ui.components.LoadingHomeScreenRow
import com.example.movienight20.ui.components.MovieListRow
@Composable
fun RecentlyViewedMoviesRow(recents: RecentlyViewedViewState, onClickMoviePhoto: (Int) -> Unit) {
    when (recents) {
        is RecentlyViewedViewState.Data -> {
            HeaderLabel(header = "Recently Viewed")
            MovieListRow(
                movieInfo = recents.cards,
                onClickMoviePhoto = onClickMoviePhoto
            )
        }
        is RecentlyViewedViewState.Empty -> {
        // Show nothing
        }
        is RecentlyViewedViewState.Loading -> {
            HeaderLabel(header = "Recently Viewed")
            LoadingHomeScreenRow()
        }
    }
}