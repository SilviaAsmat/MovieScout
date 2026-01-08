package com.silas.movienight20.ui.recently_viewed

import androidx.compose.runtime.Immutable
import com.silas.movienight20.ui.MovieCardInfoViewState

@Immutable
sealed class RecentlyViewedViewState {
    data class Data(val cards: List<MovieCardInfoViewState>): RecentlyViewedViewState()
    data object Loading : RecentlyViewedViewState()
    data object Empty : RecentlyViewedViewState()
}