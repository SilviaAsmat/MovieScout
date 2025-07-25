package com.example.movienight20.ui.recently_viewed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.movienight20.ui.components.HeaderLabel
import com.example.movienight20.ui.components.LoadingHomeScreenRow
import com.example.movienight20.ui.components.MovieListRow
import com.example.movienight20.ui.components.SeeMoreButton

@Composable
fun RecentlyViewedMoviesRow(recents: RecentlyViewedViewState, onClickMoviePhoto: (Int) -> Unit, onRecentsScreenClick:() -> Unit) {
    when (recents) {
        is RecentlyViewedViewState.Data -> {
            Row(horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth()
                    .padding(0.dp, 16.dp, 16.dp, 6.dp),) {
                HeaderLabel(header = "Recently Viewed")
                SeeMoreButton(onRecentsScreenClick)
            }

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