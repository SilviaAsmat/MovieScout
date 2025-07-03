package com.example.movienight20.ui

import androidx.compose.runtime.Immutable
import com.example.movienight20.domain.MovieInfoBasic;


@Immutable
sealed class RecentlyViewedViewState {
    // TODO replace List with ImmutableList, see composable docs.
    data class Data(val recentlyViewedInfo: List<MovieInfoBasic>): RecentlyViewedViewState()
    data object Loading : RecentlyViewedViewState()
    data object Empty : RecentlyViewedViewState()
}
