package com.example.movienight20.ui.recently_viewed

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.movienight20.R
import com.example.movienight20.ui.components.EmptyRecentlyViewedRow
import com.example.movienight20.ui.components.HeaderLabel
import com.example.movienight20.ui.components.LoadingRecentlyViewedRow
import com.example.movienight20.ui.components.MovieCollectionHeaderRow
import com.example.movienight20.ui.components.MovieListRow
import com.example.movienight20.ui.movie_collection_type.MovieCollectionType
import com.valentinilk.shimmer.shimmer

@Composable
fun RecentlyViewedMoviesRow(recents: RecentlyViewedViewState, onClickMoviePhoto: (Int) -> Unit) {
    when (recents) {
        is RecentlyViewedViewState.Data -> {
//            LoadingRecentlyViewedRow() //  Test code to preview shimmer
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
            LoadingRecentlyViewedRow()
        }
    }
}