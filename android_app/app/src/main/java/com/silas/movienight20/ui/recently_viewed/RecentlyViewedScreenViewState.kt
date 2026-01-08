package com.silas.movienight20.ui.recently_viewed

import androidx.compose.runtime.Immutable
import com.silas.movienight20.ui.movie_list.MovieListItemViewState

@Immutable
sealed class RecentlyViewedScreenViewState {
    @Immutable
    data class Data(val cards: List<MovieListItemViewState>) : RecentlyViewedScreenViewState()
    data object Loading : RecentlyViewedScreenViewState()
    data object Empty: RecentlyViewedScreenViewState()
}