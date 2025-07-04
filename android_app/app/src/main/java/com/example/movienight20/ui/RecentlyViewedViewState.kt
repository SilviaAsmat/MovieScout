package com.example.movienight20.ui

import androidx.compose.runtime.Immutable

@Immutable
sealed class RecentlyViewedViewState {
    // TODO replace List with ImmutableList, see composable docs.
    data class Data(val cards: List<MovieCardInfoViewState>): RecentlyViewedViewState()
    data object Loading : RecentlyViewedViewState()
    data object Empty : RecentlyViewedViewState()
}
